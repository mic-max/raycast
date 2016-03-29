package ca.mkmxwl.wolf.entities;

import ca.mkmxwl.wolf.level.*;

public class Ray {

	Vector2 vector;
	public double range;
	public int vOrHWall, wallID, hitOffset;

	public Ray cast(Level level) {
		int vXGrid, vYGrid;
		int hXGrid, hYGrid;
		double vX, vY, vXDelta, vYDelta;
		double hX, hY, hXDelta, hYDelta;
		double vDist, hDist;
		boolean hit = false;

		hY = Math.floor(vector.y / Level.SIZE) * Level.SIZE;
		vX = Math.floor(vector.x / Level.SIZE) * Level.SIZE;
		vXDelta = hYDelta = Level.SIZE;
		vYDelta = Level.SIZE * Math.tan(vector.dir);
		hXDelta = Level.SIZE / Math.tan(vector.dir);

		if (vector.dir < Math.PI) {
			hY--;
			hYDelta *= -1;
			vYDelta *= -1;
			hXDelta *= -1;
		} else {
			hY += Level.SIZE;
			vYDelta *= -1;
		}
		if (Math.PI / 2 < vector.dir && vector.dir < Math.PI * 1.5) {
			vX--;
			vXDelta *= -1;
			vYDelta *= -1;
			hXDelta *= -1;
		} else {
			vX += Level.SIZE;
			hXDelta *= -1;
		}

		vY = vector.y + (vector.x - vX) * Math.tan(vector.dir);
		hX = vector.x + (vector.y - hY) / Math.tan(vector.dir);
		vXGrid = (int) vX >> Level.SHIFT;
		vYGrid = (int) vY >> Level.SHIFT;
		hXGrid = (int) hX >> Level.SHIFT;
		hYGrid = (int) hY >> Level.SHIFT;
		vDist = Math.sqrt(Math.pow(vector.x - vX, 2) + Math.pow(vector.y - vY, 2));
		hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));

		// add vision limitation?
		while (!hit) {
			// put h and v values in an array like dist[] and then dist[0] = vDist and dist[0] is hDist
			// beneficial because the only vDist < hDist will just set the array index to perform
			// math operations to
			if (vDist < hDist) {
				if (level.map[vXGrid][vYGrid] == 0) {
					vX += vXDelta;
					vY += vYDelta;
					vXGrid = (int) vX >> Level.SHIFT;
					vYGrid = (int) vY >> Level.SHIFT;
					vDist = Math.sqrt(Math.pow(vector.x - vX, 2) + Math.pow(vector.y - vY, 2));
				} else {
					vOrHWall = 0;
					wallID = (int) level.map[vXGrid][vYGrid];
					hitOffset = (int) (vY % Level.SIZE);
					range = vDist;
					hit = true;
				}
			} else {
				if (level.map[hXGrid][hYGrid] == 0) {
					hX += hXDelta;
					hY += hYDelta;
					hXGrid = (int) hX >> Level.SHIFT;
					hYGrid = (int) hY >> Level.SHIFT;
					hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));
				} else {
					vOrHWall = 1;
					wallID = (int) level.map[hXGrid][hYGrid];
					hitOffset = (int) (hX % Level.SIZE);
					range = hDist;
					hit = true;
				}
			}
		}
		return this;
	}

	public Ray(Vector2 vector) {
		this.vector = vector;
	}
}
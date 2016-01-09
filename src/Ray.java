public class Ray {

	Vector2 vector;

	public double cast(Map map) {
		int hYGrid, hXGrid;
		int vYGrid, vXGrid;
		double hX, hY, hXDelta, hYDelta;
		double vX, vY, vXDelta, vYDelta;
		double vDist, hDist;

		// the delta values are the distance to the next intersection of the grid

		boolean hit = false;

		hY = Math.floor(vector.y / Map.SIZE) * Map.SIZE;
		vX = Math.floor(vector.x / Map.SIZE) * Map.SIZE;
		hYDelta = vXDelta = Map.SIZE;

		vYDelta = Map.SIZE * Math.tan(vector.dir);
		hXDelta = Map.SIZE / Math.tan(vector.dir);

		// what if angle has no vertical or horizontal component?
		if (vector.dir < Math.PI) { // looking up
			hY--;
			vYDelta *= -1;
			hYDelta *= -1;
		} else { // looking down
			hY += Map.SIZE;
		}
		if (Math.PI / 2 < vector.dir && vector.dir < Math.PI * 1.5) { // looking left
			vX--;
			vXDelta *= -1;
			hXDelta *= -1;
		} else { // looking right
			vX += Map.SIZE;
		}

		hX = vector.x + (vector.y - hY) / Math.tan(vector.dir);
		hXGrid = (int) hX >> Map.SHIFT;
		hYGrid = (int) hY >> Map.SHIFT;
		vY = vector.y + (vector.x - vX) * Math.tan(vector.dir);
		vXGrid = (int) vX >> Map.SHIFT;
		vYGrid = (int) vY >> Map.SHIFT;

		while (!hit) {
			vDist = Math.sqrt(Math.pow(vector.x - vX, 2) + Math.pow(vector.y - vY, 2));
			hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));
			if (vDist < hDist) {
				vX += vXDelta;
				vY += vYDelta;
				vXGrid = (int) vX >> Map.SHIFT;
				vYGrid = (int) vY >> Map.SHIFT;
				if (map.map[vXGrid][vYGrid] > 0)
					return vDist;
			} else {
				hX += hXDelta;
				hY += hYDelta;
				hXGrid = (int) hX >> Map.SHIFT;
				hYGrid = (int) hY >> Map.SHIFT;
				if (map.map[hXGrid][hYGrid] > 0)
					return hDist;
			}
		}
		return 0;
	}

	public Ray(Vector2 vector) {
		this.vector = vector;
	}

	public String toString() {
		return "Ray: " + vector;
	}
}
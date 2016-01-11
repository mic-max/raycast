public class Ray {

	Vector2 vector;

	public double cast(Map map) {
		int vXGrid, vYGrid;
		int hXGrid, hYGrid;
		double vX, vY, vXDelta, vYDelta;
		double hX, hY, hXDelta, hYDelta;
		double vDist, hDist;
		boolean hit = false;

		hY = Math.floor(vector.y / Map.SIZE) * Map.SIZE;
		vX = Math.floor(vector.x / Map.SIZE) * Map.SIZE;
		vXDelta = hYDelta = Map.SIZE;
		vYDelta = Map.SIZE * Math.tan(vector.dir);
		hXDelta = Map.SIZE / Math.tan(vector.dir);

		if (vector.dir < Math.PI) {
			hY--;
			hYDelta *= -1;
			vYDelta *= -1;
			hXDelta *= -1;
		} else {
			hY += Map.SIZE;
			vYDelta *= -1;
		}
		if (Math.PI / 2 < vector.dir && vector.dir < Math.PI * 1.5) {
			vX--;
			vXDelta *= -1;
			vYDelta *= -1;
			hXDelta *= -1;
		} else {
			vX += Map.SIZE;
			hXDelta *= -1;
		}

		vY = vector.y + (vector.x - vX) * Math.tan(vector.dir);
		hX = vector.x + (vector.y - hY) / Math.tan(vector.dir);
		vXGrid = (int) vX >> Map.SHIFT;
		vYGrid = (int) vY >> Map.SHIFT;
		hXGrid = (int) hX >> Map.SHIFT;
		hYGrid = (int) hY >> Map.SHIFT;
		vDist = Math.sqrt(Math.pow(vector.x - vX, 2) + Math.pow(vector.y - vY, 2));
		hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));

		// add vision limitation?
		while (!hit) {
			// put h and v values in an array like dist[] and then dist[0] = vDist and dist[0] is hDist
			// beneficial because the only vDist < hDist will just set the array index to perform
			// math operations to
			if (vDist < hDist) {
				if (map.map[vXGrid][vYGrid] == 0) {
					vX += vXDelta;
					vY += vYDelta;
					vXGrid = (int) vX >> Map.SHIFT;
					vYGrid = (int) vY >> Map.SHIFT;
					vDist = Math.sqrt(Math.pow(vector.x - vX, 2) + Math.pow(vector.y - vY, 2));
				} else
					return vDist;
			} else {
				if (map.map[hXGrid][hYGrid] == 0) {
					hX += hXDelta;
					hY += hYDelta;
					hXGrid = (int) hX >> Map.SHIFT;
					hYGrid = (int) hY >> Map.SHIFT;
					hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));
				} else
					return hDist;
			}
		}
		return 0;
	}

	public Ray(Vector2 vector) {
		this.vector = vector;
	}
}
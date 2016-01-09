public class Ray {

	Vector2 vector;

	public double cast(Map map) {
		int hYGrid, hXGrid;
		int vYGrid, vXGrid;
		double hX, hY, hXDelta, hYDelta;
		double vX, vY, vXDelta, vYDelta;

		boolean hit = false;

		hY = Math.floor(vector.y / Map.SHIFT) * Map.SIZE;
		vX = Math.floor(vector.x / Map.SIZE) * Map.SIZE;
		hYDelta = vXDelta = Map.SIZE;

		// what if angle has no vertical or horizontal component?
		if (vector.dir < Math.PI) {
			hY--;
			hYDelta *= -1;
		} else {
			hY += Map.SIZE;
		}
		if (Math.PI / 2 < vector.dir && vector.dir < Math.PI * 1.5) {
			vX--;
			vXDelta *= -1;
		} else {
			vX += Map.SIZE;
		}

		hXDelta = Map.SIZE / Math.tan(vector.dir);
		hX = vector.x + (vector.y - hY) / Math.tan(vector.dir);
		hXGrid = (int) hX >> Map.SHIFT;
		hYGrid = (int) hY >> Map.SHIFT;

		vYDelta = Map.SIZE * Math.tan(vector.dir);
		vY = vector.y + (vector.x - vX) * Math.tan(vector.dir);
		vXGrid = (int) vX >> Map.SHIFT;
		vYGrid = (int) vY >> Map.SHIFT;

		while (!hit) {
			double vDist = Math.sqrt(Math.pow(vector.x - vX, 2) + Math.pow(vector.y - vY, 2));
			double hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));
			if (vDist < hDist) {
				vX += vXDelta;
				vY += vYDelta;
				vXGrid = (int) vX >> Map.SHIFT;
				vYGrid = (int) vY >> Map.SHIFT;
				if (map.map[vXGrid][vYGrid] > 0) {
					return vDist;
				}
			} else {
				hX += hXDelta;
				hY += hYDelta;
				hXGrid = (int) hX >> Map.SHIFT;
				hYGrid = (int) hY >> Map.SHIFT;
				if (map.map[hXGrid][hYGrid] > 0) {
					return hDist;
				}
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
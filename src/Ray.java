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
		hYDelta = vXDelta = Map.SIZE;

		vYDelta = Map.SIZE * Math.tan(vector.dir);
		hXDelta = Map.SIZE / Math.tan(vector.dir);

		// cause of random spikes coming out of wall? add the other else if?
		if (vector.dir < Math.PI) { // up
			hY--;
			hYDelta *= -1;
			vYDelta *= -1;
		} else { // down
			hY += Map.SIZE;
		}

		// cause of random spikes coming out of wall? add the other else if?
		if (Math.PI / 2 < vector.dir && vector.dir < Math.PI * 1.5) { // left
			vX--;
			vXDelta *= -1;
		} else { // right
			vX += Map.SIZE;
		}

		hX = vector.x + (vector.y - hY) / Math.tan(vector.dir);
		hXGrid = (int) hX >> Map.SHIFT;
		hYGrid = (int) hY >> Map.SHIFT;
		vY = vector.y + (vector.x - vX) * Math.tan(vector.dir);
		vXGrid = (int) vX >> Map.SHIFT;
		vYGrid = (int) vY >> Map.SHIFT;

		vDist = Math.sqrt(Math.pow(vector.x - vX, 2) + Math.pow(vector.y - vY, 2));
		hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));

		// System.out.println("vector.x : " + vector.x);
		// System.out.println("vector.y : " + vector.y);
		// System.out.println("vector.dir : " + vector.dir);
		//
		// System.out.println("\nvXGrid : " + vXGrid);
		// System.out.println("vYGrid : " + vYGrid);
		// System.out.println("hXGrid : " + hXGrid);
		// System.out.println("hYGrid : " + hYGrid);
		//
		// System.out.println("\nvX : " + vX);
		// System.out.println("vY : " + vY);
		// System.out.println("hX : " + hX);
		// System.out.println("hY : " + hY);
		//
		// System.out.println("\nvXDelta : " + vXDelta);
		// System.out.println("vYDelta : " + vYDelta);
		// System.out.println("hXDelta : " + hXDelta);
		// System.out.println("hYDelta : " + hYDelta);
		//
		// System.out.println("\nvDist : " + vDist);
		// System.out.println("hDist : " + hDist);
		//
		// System.out.println("\nhit : " + hit);

		while (!hit) {
			if (vDist < hDist) {
				// System.out.println("vertical");
				if (map.map[vXGrid][vYGrid] == 0) { // floor
					vX += vXDelta;
					vY += vYDelta;
					vXGrid = (int) vX >> Map.SHIFT;
					vYGrid = (int) vY >> Map.SHIFT;
					vDist = Math.sqrt(Math.pow(vector.x - vX, 2) + Math.pow(vector.y - vY, 2));
				} else { // wall
					// System.out.println("\nreturn : " + vDist);
					hit = true;
					return vDist;
				}
			} else {
				// System.out.println("horizontal");
				// check if it was a hit, if not increase the horizontal parts
				if (map.map[hXGrid][hYGrid] == 0) { // floor
					hX += hXDelta;
					hY += hYDelta;
					hXGrid = (int) hX >> Map.SHIFT;
					hYGrid = (int) hY >> Map.SHIFT;
					hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));
				} else { // wall
					// System.out.println("\nreturn : " + hDist);
					return hDist;
				}
			}
		}
		return 0;
	}

	public Ray(Vector2 vector) {
		this.vector = vector;
	}
}
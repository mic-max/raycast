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

		// cause of array index out of bounds when looking to the right? no else if that is
		if (vector.dir < Math.PI) { // up
			hY--;
			hYDelta *= -1;
			vYDelta *= -1;
			hXDelta *= -1;
		} else { // down
			hY += Map.SIZE;
			vYDelta *= -1;
		}

		// cause of array index out of bounds when looking to the right? no else if that is
		if (Math.PI / 2 < vector.dir && vector.dir < Math.PI * 1.5) { // left
			vX--;
			vXDelta *= -1;
			vYDelta *= -1;
			hXDelta *= -1;
		} else { // right
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
		// System.out.println("\nnext vX : " + (vX + vXDelta));
		// System.out.println("next vY : " + (vY + vYDelta));
		// System.out.println("next hX : " + (hX + hXDelta));
		// System.out.println("next hY : " + (hY + hYDelta));
		//
		// System.out.println("\n2next vX : " + (vX + vXDelta * 2));
		// System.out.println("2next vY : " + (vY + vYDelta * 2));
		// System.out.println("2next hX : " + (hX + hXDelta * 2));
		// System.out.println("2next hY : " + (hY + hYDelta * 2));
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
		//
		// if (vector.dir == Math.PI / 6) {
		// System.out.println("vx1: " + (vX == 256.0));
		// System.out.println("vy1: " + (vY == 187.66838492538096));
		// System.out.println("hx1: " + (hX == 250.22947341949745));
		// System.out.println("hy1: " + (hY == 191.0));
		//
		// System.out.println("\nvx1: " + ((vX + vXDelta) == 320.0));
		// System.out.println("vy1: " + ((vY + vYDelta) == 150.71796769724492));
		// System.out.println("hx1: " + ((hX + hXDelta) == 361.0807251039056));
		// System.out.println("hy1: " + ((hY + hYDelta) == 127.0));
		//
		// System.out.println("\nvx1: " + ((vX + vXDelta * 2) == 384.0));
		// System.out.println("vy1: " + ((vY + vYDelta * 2) == 113.76755046910887));
		// System.out.println("hx1: " + ((hX + hXDelta * 2) == 471.93197678831376));
		// System.out.println("hy1: " + ((hY + hYDelta * 2) == 63.0));
		//
		// } else if (vector.dir == 5 * Math.PI / 6) {
		// System.out.println("vx1: " + (vX == 191.0));
		// System.out.println("vy1: " + (vY == 214.80384757729337));
		// System.out.println("hx1: " + (hX == 149.77052658050255));
		// System.out.println("hy1: " + (hY == 191.0));
		//
		// System.out.println("\nvx1: " + ((vX + vXDelta) == 127.0));
		// System.out.println("vy1: " + ((vY + vYDelta) == 177.85343034915732));
		// System.out.println("hx1: " + ((hX + hXDelta) == 38.91927489609439));
		// System.out.println("hy1: " + ((hY + hYDelta) == 127.0));
		//
		// System.out.println("\nvx1: " + ((vX + vXDelta * 2) == 63.0));
		// System.out.println("vy1: " + ((vY + vYDelta * 2) == 140.90301312102127));
		// System.out.println("hx1: " + ((hX + hXDelta * 2) == -71.93197678831376));
		// System.out.println("hy1: " + ((hY + hYDelta * 2) == 63.0));
		// } else if (vector.dir == 7 * Math.PI / 6) {
		// System.out.println("vx1: " + (vX == 191.0));
		// System.out.println("vy1: " + (vY == 225.19615242270663));
		// System.out.println("hx1: " + (hX == 137.6461709275204));
		// System.out.println("hy1: " + (hY == 256.0));
		//
		// System.out.println("\nvx1: " + ((vX + vXDelta) == 127.0));
		// System.out.println("vy1: " + ((vY + vYDelta) == 262.14656965084265));
		// System.out.println("hx1: " + ((hX + hXDelta) == 26.794919243112176));
		// System.out.println("hy1: " + ((hY + hYDelta) == 320.0));
		//
		// System.out.println("\nvx1: " + ((vX + vXDelta * 2) == 63.0));
		// System.out.println("vy1: " + ((vY + vYDelta * 2) == 299.09698687897867));
		// System.out.println("hx1: " + ((hX + hXDelta * 2) == -84.05633244129604));
		// System.out.println("hy1: " + ((hY + hYDelta * 2) == 384.0));
		// } else if (vector.dir == 11 * Math.PI / 6) {
		// System.out.println("vx1: " + (vX == 256.0));
		// System.out.println("vy1: " + (vY == 252.3316150746191));
		// System.out.println("hx1: " + (hX == 262.3538290724795));
		// System.out.println("hy1: " + (hY == 256.0));
		//
		// System.out.println("\nvx1: " + ((vX + vXDelta) == 320.0));
		// System.out.println("vy1: " + ((vY + vYDelta) == 289.28203230275517));
		// System.out.println("hx1: " + ((hX + hXDelta) == 373.2050807568876));
		// System.out.println("hy1: " + ((hY + hYDelta) == 320.0));
		//
		// System.out.println("\nvx1: " + ((vX + vXDelta * 2) == 384.0));
		// System.out.println("vy1: " + ((vY + vYDelta * 2) == 326.23244953089124));
		// System.out.println("hx1: " + ((hX + hXDelta * 2) == 484.0563324412956));
		// System.out.println("hy1: " + ((hY + hYDelta * 2) == 384.0));
		// }

		// System.exit(0);

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
				} else {
					return vDist;
				}
			} else {
				if (map.map[hXGrid][hYGrid] == 0) {
					hX += hXDelta;
					hY += hYDelta;
					hXGrid = (int) hX >> Map.SHIFT;
					hYGrid = (int) hY >> Map.SHIFT;
					hDist = Math.sqrt(Math.pow(vector.x - hX, 2) + Math.pow(vector.y - hY, 2));
				} else {
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
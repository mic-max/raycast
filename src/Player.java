public class Player {

	Vector2 vector;
	double height = Map.SIZE / 2;
	double fov = Math.PI / 3;
	double ppd = Game.WIDTH / 2 / Math.tan(fov / 2);
	double innerRayAngle = fov / Game.WIDTH;

	double[] rays = new double[Game.WIDTH];

	public void castRays(Map map) {
		for (int i = 0; i < rays.length; i++) {
			double rayDir = vector.dir + fov / 2 - i * innerRayAngle;
			if (rayDir > Math.PI * 2)
				rayDir -= Math.PI * 2;
			else if (rayDir < 0)
				rayDir += Math.PI * 2;
			rays[i] = new Ray(new Vector2(vector.x, vector.y, rayDir)).cast(map);
			rays[i] *= Math.cos(vector.dir - rayDir);
		}
	}

	public Player(Vector2 vector) {
		this.vector = vector;
	}
}
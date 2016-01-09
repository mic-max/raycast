public class Player {

	Vector2 vector;
	double height = 32; // half the height of a wall
	double fov = Math.PI / 3; // 60 degrees
	double ppd = Game.WIDTH / 2 / Math.tan(fov / 2); // distance to projection plane
	double innerRayAngle = fov / Game.WIDTH; // angle between subsequent rays

	double[] rays = new double[Game.WIDTH];

	public void castRays(Map map) {
		for (int i = 0; i < rays.length; i++) {
			double rayDir = vector.dir - fov / 2 + i * innerRayAngle;
			rays[i] = new Ray(new Vector2(vector.x, vector.y, rayDir)).cast(map);
			// System.out.println(i + " - " + rays[i]);
		}
	}

	public Player(Vector2 vector) {
		this.vector = vector;
	}

	public String toString() {
		return "Player: " + vector;
	}
}
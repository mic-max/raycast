public class Vector2 {

	double x, y, dir;

	public Vector2(double x, double y, double dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}

	public String toString() {
		return "(" + x + ", " + y + ") [" + dir + "]";
	}
}
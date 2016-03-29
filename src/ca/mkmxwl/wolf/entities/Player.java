package ca.mkmxwl.wolf.entities;

import java.awt.*;

import ca.mkmxwl.wolf.*;
import ca.mkmxwl.wolf.gfx.*;
import ca.mkmxwl.wolf.level.*;

public class Player extends Entity {

	public Vector2 vector;
	private Level level;

	double height = Level.SIZE / 2;
	public double fov = Math.PI / 2;
	public double ppd = Wolf.WIDTH / 2 / Math.tan(fov / 2);
	public double innerRayAngle = fov / Wolf.WIDTH;

	public void update(boolean w, boolean s, boolean a, boolean d, boolean lk, boolean rk) {
		if (w) {
			vector.x += Math.cos(vector.dir) * 1.5;
			vector.y -= Math.sin(vector.dir) * 1.5;
		} else if (s) {
			vector.x -= Math.cos(vector.dir);
			vector.y += Math.sin(vector.dir);
		}
		if (a) {
			vector.x -= Math.sin(vector.dir);
			vector.y -= Math.cos(vector.dir);
		} else if (d) {
			vector.x += Math.sin(vector.dir);
			vector.y += Math.cos(vector.dir);
		}
		if (lk) {
			vector.dir += innerRayAngle * 30;
		} else if (rk) {
			vector.dir -= innerRayAngle * 30;
		}
	}

	public void render(Graphics g) {
		g.drawImage(SpriteSheet.weapons.sheet, Wolf.WIDTH / 2 - 32 * 3, Wolf.HEIGHT - 64 * 3, Wolf.WIDTH / 2 + 32 * 3, Wolf.HEIGHT, 129, 0, 191, 63, null);
	}

	public Player(Vector2 vector, Level level) {
		this.vector = vector;
		this.level = level;
		level.player = this;
		level.addEntity(this);
	}

	public void activate() {

	}
}
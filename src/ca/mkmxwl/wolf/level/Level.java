package ca.mkmxwl.wolf.level;

import java.awt.*;
import java.io.*;
import java.util.*;

import ca.mkmxwl.wolf.*;
import ca.mkmxwl.wolf.entities.*;
import ca.mkmxwl.wolf.gfx.*;

public class Level {

	public static final int SIZE = 64;
	public static final int SHIFT = (int) (Math.log(SIZE) / Math.log(2));
	int width, height;
	public int[][] map;
	public Vector2 spawn;
	public Ray[] rays = new Ray[Wolf.WIDTH];

	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Player player;

	public static void clear() {
		// loaded.clear();
	}

	public Level(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("res/level/" + file + ".txt"));

			width = Integer.parseInt(br.readLine());
			height = Integer.parseInt(br.readLine());
			map = new int[width][height];
			for (int j = 0; j < height; j++) {
				String[] split = br.readLine().split("\\s+");
				for (int i = 0; i < width; i++)
					map[i][j] = Integer.parseInt(split[i]);
			}
			br.close();
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}

	public void update() {
		for (Entity e : entities) {
			e.update();
		}
	}

	public void render(Graphics g) {
		g.setColor(new Color(56, 56, 56));
		g.fillRect(0, 0, Wolf.WIDTH, Wolf.HEIGHT / 2);
		g.setColor(new Color(112, 112, 112));
		g.fillRect(0, Wolf.HEIGHT / 2, Wolf.WIDTH, Wolf.HEIGHT);
		castRays();
		for (int i = 0; i < rays.length; i++) {
			int sliceHeight = (int) (Level.SIZE / rays[i].range * player.ppd);
			int f = Wolf.HEIGHT / 2 - sliceHeight / 2;
			// if (player.rays[i].vOrHWall == 0)
			g.drawImage(SpriteSheet.walls.sheet, i, f, i + 1, f + sliceHeight, rays[i].hitOffset, 0, rays[i].hitOffset + 1, SpriteSheet.walls.spriteHeight, null);
		}
		for (Entity e : entities) {
			e.render(g);
		}
	}

	private void castRays() {
		for (int i = 0; i < rays.length; i++) {
			double rayDir = player.vector.dir + player.fov / 2 - i * player.innerRayAngle;
			// this block lets you spin around an extra time b4 crashing
			if (rayDir < 0)
				rayDir += Math.PI * 2;
			else if (rayDir > Math.PI * 2)
				rayDir -= Math.PI * 2;
			//
			rays[i] = new Ray(new Vector2(player.vector.x, player.vector.y, rayDir)).cast(this);
			rays[i].range *= Math.cos(player.vector.dir - rayDir);
		}
	}

	public static Level load(Game game, String string) {
		return null;
	}

	public void addEntity(Entity e) {
		entities.add(e);
		e.level = this;
		e.update();
	}

	public void removeEntity(Entity e) {
		entities.remove(e);
	}

	public void findSpawn(int id) {
		// find the id in the map
		// then set the player's location part of the vector to it
		// spawn.x = x;
		// spawn.y = y;
		// spawn.dir = this.spawn.dir???;
	}
}
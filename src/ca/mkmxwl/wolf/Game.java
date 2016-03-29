package ca.mkmxwl.wolf;

import java.awt.*;
import java.awt.event.*;

import ca.mkmxwl.wolf.entities.*;
import ca.mkmxwl.wolf.level.*;
import ca.mkmxwl.wolf.menu.*;
import ca.mkmxwl.wolf.menu.Menu;

public class Game {

	public Menu menu;
	public Level level;
	public Player player;

	public Game() {
		setMenu(new MainMenu());
	}

	public void newGame() {
		Level.clear();
		level = new Level("map1");
		player = new Player(new Vector2(200, 220, 0), level);
		// level = Level.load(this, "start");
		// player = new Player(level.spawn, level);
	}

	public void switchLevel(String name, int id) {
		level.removeEntity(player);
		level = Level.load(this, name);
		level.findSpawn(id);
		player.vector = level.spawn;
		level.addEntity(player);
	}

	public void update(boolean[] keys) {
		boolean uk = keys[KeyEvent.VK_UP];
		boolean dk = keys[KeyEvent.VK_DOWN];
		boolean lk = keys[KeyEvent.VK_LEFT];
		boolean rk = keys[KeyEvent.VK_RIGHT];

		boolean w = keys[KeyEvent.VK_W];
		boolean s = keys[KeyEvent.VK_S];
		boolean a = keys[KeyEvent.VK_A];
		boolean d = keys[KeyEvent.VK_D];

		boolean enter = keys[KeyEvent.VK_SPACE];

		if (keys[KeyEvent.VK_ESCAPE]) {
			keys[KeyEvent.VK_ESCAPE] = false;
			if (menu == null) {
				setMenu(new PauseMenu());
			}
		}

		if (enter) {
			keys[KeyEvent.VK_SPACE] = false;
		}

		if (menu != null) {
			keys[KeyEvent.VK_W] = false;
			keys[KeyEvent.VK_S] = false;
			keys[KeyEvent.VK_A] = keys[KeyEvent.VK_LEFT] = false;
			keys[KeyEvent.VK_D] = keys[KeyEvent.VK_RIGHT] = false;

			menu.update(this, uk, dk, enter);
		} else {
			player.update(w, s, a, d, lk, rk);
			if (enter) {
				player.activate();
			}
			level.update();
		}
	}

	public void render(Graphics g) {
		if (menu != null) {
			menu.render(g);
		} else {
			level.render(g);
		}
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}
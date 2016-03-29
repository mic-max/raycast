package ca.mkmxwl.wolf.menu;

import java.awt.*;

import ca.mkmxwl.wolf.*;
import ca.mkmxwl.wolf.gfx.*;

public class MainMenu extends Menu {
	private String[] options = { "Play", "Exit" };
	private byte selected = 0;

	public void update(Game game, boolean up, boolean down, boolean enter) {
		if (up && selected > 0) {
			Wolf.sleep(150);
			Sound.click.play();
			selected--;
		}
		if (down && selected < options.length - 1) {
			Wolf.sleep(150);
			Sound.click.play();
			selected++;
		}

		if (enter) {
			Sound.click.play();
			switch (selected) {
			case 0:
				game.setMenu(null);
				game.newGame();
				break;
			default:
				System.exit(0);
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawImage(SpriteSheet.mainMenu.sheet, 0, 0, Wolf.WIDTH, Wolf.HEIGHT, 0, 264, SpriteSheet.mainMenu.width, SpriteSheet.mainMenu.height, null);
		for (int i = 0; i < options.length; i++) {
			g.setColor(Color.WHITE);
			String msg = options[i];
			if (selected == i) {
				g.setColor(Color.CYAN);
			}
			g.drawString(msg, 40, 60 + i * 40);
		}
	}
}
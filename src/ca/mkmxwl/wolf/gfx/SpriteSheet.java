package ca.mkmxwl.wolf.gfx;

import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

public class SpriteSheet {

	public static SpriteSheet walls = new SpriteSheet("/gfx/misc/walls_single_tone.png", 64);
	public static SpriteSheet mainMenu = new SpriteSheet("/gfx/player/bj_blazkowicz.png", 64);
	public static SpriteSheet weapons = new SpriteSheet("/gfx/player/weapons.png", 64);

	public int width, height;
	public int spriteWidth, spriteHeight;
	public BufferedImage sheet;

	public SpriteSheet(String path, int size) {
		spriteWidth = spriteHeight = size;
		try {
			sheet = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
			// RescaleOp op = new RescaleOp(.8f, 0, null);
			// sheet = op.filter(sheet, null);
			width = sheet.getWidth();
			height = sheet.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
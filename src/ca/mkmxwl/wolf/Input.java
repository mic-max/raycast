package ca.mkmxwl.wolf;

import java.awt.event.*;

public class Input implements KeyListener {
	public boolean[] keys = new boolean[65536];

	public Input(Wolf wolf) {
		wolf.addKeyListener(this);
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
	}
}
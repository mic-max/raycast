package ca.mkmxwl.wolf;

import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Wolf extends Canvas implements Runnable {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 400;
	public static boolean running;

	private Thread thread;
	private JFrame frame;
	private Input input;
	private Game game;

	public Wolf() {
		setSize(WIDTH, HEIGHT);

		frame = new JFrame("Wolf");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setIconImage(null); // add icon
		frame.setVisible(true);

		game = new Game();
		input = new Input(this);
	}

	public synchronized void start() {
		if (!running) {
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}

	public synchronized void stop() {
		if (running) {
			running = false;
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void run() {
		final double NS_PER_UPDATE = 1000000000D / 60D;
		long last = System.nanoTime();
		double delta = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - last) / NS_PER_UPDATE;
			last = now;
			while (delta >= 1) {
				update();
				delta--;
			}
			render();
			sleep(2);
		}
	}

	private void update() {
		game.update(input.keys);
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();
		game.render(g);
		g.dispose();
		bs.show();
	}

	public static void sleep(int n) {
		try {
			Thread.sleep(n);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Wolf().start();
	}
}
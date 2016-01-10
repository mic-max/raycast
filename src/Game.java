import java.awt.*;
import java.awt.image.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class Game extends Canvas implements Runnable {

	public static final String TITLE = "Wolfenstein 3D";
	public static final int WIDTH = 640;
	public static final int HEIGHT = 400;
	public static boolean running = false;

	private JFrame frame;
	private Thread thread;
	private BufferStrategy bs;
	private BufferedImage image;
	private Graphics g;
	private Player player;
	private Map map;

	public Game() {
		frame = new JFrame(TITLE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setIconImage(null); // replace with image
		frame.setVisible(true);

		thread = new Thread(this);

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

		g = image.getGraphics();

		player = new Player(new Vector2(200, 220, Math.PI / 3));

		map = new Map("res/level/map1.txt");
	}

	public synchronized void start() {
		running = true;
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			System.err.println("Error joining thread.");
		}
	}

	@Override
	public void run() {
		final double NS_PER_UPDATE = 1000000000D / 60D;
		long timer = System.currentTimeMillis();
		long last = System.nanoTime();
		double delta = 0;
		int fps = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - last) / NS_PER_UPDATE;
			last = now;
			while (delta > 1) {
				delta--;
				update();
			}
			render();
			fps++;
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				System.err.println("Error sleeping thread.");
			}
			if (System.currentTimeMillis() - timer > 1000) {
				frame.setTitle(TITLE + "    -    " + fps);
				timer += 1000;
				fps = 0;
			}
		}
	}

	public void update() {
		player.vector.dir -= Math.PI / 400; // modulus by 2PI?
		// player.vector.y += .1;
	}

	public void render() {
		bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		g = bs.getDrawGraphics();

		g.setColor(new Color(56, 56, 56)); // ceiling
		g.fillRect(0, 0, getWidth(), getHeight() / 2);
		g.setColor(new Color(112, 112, 112)); // floor
		g.fillRect(0, getHeight() / 2, getWidth(), getHeight());

		player.castRays(map);
		g.setColor(Color.BLACK);
		for (int i = 0; i < player.rays.length; i++) {
			int sliceHeight = (int) (Map.SIZE / player.rays[i] * player.ppd / 2); // random division
			int f = HEIGHT / 2 - sliceHeight / 2;
			g.drawLine(i, f, i, f + sliceHeight);
		}

		g.dispose();
		bs.show();
	}

	public static void main(String[] args) {
		new Game().start();
	}
}
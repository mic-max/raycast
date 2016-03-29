package ca.mkmxwl.wolf;

import javax.sound.sampled.*;

public class Sound {
	private enum Type {
		Music, Effect
	};

	private Clip clip;
	private Type type;
	private boolean loop;

	public static float musicdb = -28;
	public static float effectdb = -14;

	public static Sound click = new Sound("click", Type.Effect, false);

	private Sound(String file, Type type, boolean loop) {
		this.type = type;
		this.loop = loop;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource("/snd/" + file + ".wav"));
			clip = AudioSystem.getClip();
			clip.open(ais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		try {
			new Thread() {
				public void run() {
					synchronized (clip) {
						clip.stop();
						clip.setFramePosition(0);
						FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
						float db = 0;
						switch (type) {
						case Music:
							db = musicdb;
							break;
						case Effect:
							db = effectdb;
							break;
						default:
							break;
						}
						gainControl.setValue(db);
						if (loop)
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						else
							clip.start();
					}
				}
			}.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
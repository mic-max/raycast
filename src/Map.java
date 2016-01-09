import java.io.*;

public class Map {

	static final int SIZE = 64;
	static final int SHIFT = (int) (Math.log(SIZE) / Math.log(2));
	int width, height;
	int[][] map;

	private void loadMap(String path) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			width = Integer.parseInt(br.readLine());
			height = Integer.parseInt(br.readLine());
			map = new int[width][height];
			for (int j = 0; j < height; j++) {
				String[] split = br.readLine().split("\\s+");
				for (int i = 0; i < width; i++)
					map[i][j] = Integer.parseInt(split[i]);
			}
			br.close();
		} catch (FileNotFoundException e) {
			System.err.println("Unable to open file '" + path + "'.");
		} catch (IOException e) {
			System.err.println("Error reading file '" + path + "'.");
		}
	}

	public Map(String path) {
		loadMap(path);
	}
}
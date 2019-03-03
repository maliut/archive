package kernel;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import kernel.serialization.GameObjectDeserializer;
import kernel.util.FileReader;

/** 资源引用的入口  */
public class Resource {
	
	private static Map<String, String> sprites = new HashMap<String, String>();
	private static Map<String, GameObject> prefabs = new HashMap<String, GameObject>();
	private static boolean hasLoaded = false;
	
	public static String get(String key) {
		return sprites.get(key);
	}
	
	public static GameObject getPrefab(String key) {
		return prefabs.get(key);
	}
	
	public static Map<String, GameObject> getPrefabs() {
		return prefabs;
	}
	
	/** 用于场景开始时读取资源<br>
	 *  读取预设、贴图<br>
	 *  只允许在开始时读取一次
	 */
	public static void load() {
		if (hasLoaded) return;
		try {
			// initialize gson
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(GameObject.class, new GameObjectDeserializer());
			Gson gson = gsonBuilder.create();
			// load sprites
			FileReader.readLines("res/sprites.dat").stream().filter(e -> !e.trim().equals("")).forEach(e -> {
				try {
					String x = new String(e.getBytes(), "GBK");
					String[] tmp = x.split("\\s+");
					String tmpv = tmp[1].replace("$s", "   ");  // 处理转义
					sprites.put(tmp[0], tmpv);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			});
			// load prefabs
			Arrays.asList(new File("res/prefab").listFiles()).stream().filter(e -> e.isFile()).forEach(e -> {
				try {
					String name = e.getName().split("\\.")[0];
					GameObject o = gson.fromJson(FileReader.readLines(e.getPath()).get(0), GameObject.class);
					prefabs.put(name, o);
				} catch (Exception e1) {
					System.err.println("载入资源失败！");
					e1.printStackTrace();
					System.exit(1);
				}
			});
		} catch (IOException e) {
			System.err.println("载入资源失败！");
			e.printStackTrace();
			System.exit(1);
		}
		hasLoaded = true;
	}
}

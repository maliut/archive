package kernel.serialization;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import kernel.GameObject;
import kernel.component.Component;
import kernel.util.Direction;
import kernel.util.Point;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GameObjectDeserializer implements JsonDeserializer<GameObject> {

	private static Gson defaultGson = new Gson();

	private static Collection<String> componentTypeList;
	static {
		componentTypeList = new ArrayList<String>();
		Arrays.asList(new File("bin/user/component").listFiles()).stream().filter(e -> e.isFile())
			.forEach(e -> componentTypeList.add("user.component." + e.getName().split("\\.")[0]));
		Arrays.asList(new File("bin/kernel/component").listFiles()).stream().filter(e -> e.isFile()
				&& !e.getName().equals("Component.class") && !e.getName().equals("Menu.class"))
			.forEach(e -> componentTypeList.add("kernel.component." + e.getName().split("\\.")[0]));
	}
	
	@Override
	public GameObject deserialize(JsonElement json, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {
		GameObject ret = new GameObject();  // the return game object
		JsonObject jsonObject = json.getAsJsonObject();
		// deal properties of game object itself (transform)
		ret.setPosition(defaultGson.fromJson(jsonObject.get("position"), Point.class));
		ret.setDirection(defaultGson.fromJson(jsonObject.get("direction"), Direction.class));
		ret.setTag(defaultGson.fromJson(jsonObject.get("tag"), String.class));
		ret.setLayer(defaultGson.fromJson(jsonObject.get("layer"), Integer.class));
		// deal components
		JsonObject jsonComponents = jsonObject.get("components").getAsJsonObject();
		// 对于每一个组件，如果这个 GameObject 有这个组件，那么对组件进行正常反序列化
		componentTypeList.forEach(klassName -> {
			JsonElement jsonKlass = jsonComponents.get(klassName);
			if (jsonKlass == null) return;  // 有这个组件
			try {  // 根据类反序列化
				Component component = (Component) defaultGson.fromJson(jsonKlass, Class.forName(klassName)); 
				component.setGameObject(ret);
				ret.addComponent(component);					
			} catch (SecurityException | ClassNotFoundException e) {
				e.printStackTrace();
				System.exit(1);
			}	
		});
		return ret;
	}

}

package test;

import kernel.GameObject;
import kernel.component.Renderer;
import kernel.serialization.GameObjectDeserializer;
import kernel.util.Point;

import org.junit.Test;

import user.component.FieldBuyMenu;
import user.component.Player;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonGenerate {
	
	@Test
	public void testGson() {
		Gson gson = new Gson();
		GameObject o = new GameObject();
		o.setPosition(new Point(1, 2));
		Player cPlayer = new Player(o);
		cPlayer.setCash(5000);
		Renderer cRenderer = new Renderer(o);
		cRenderer.setContent("A\u3000");
		o.addComponent(cPlayer);
		o.addComponent(cRenderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void testMyGson() {
		String json = "{\"transform\":{\"x\":1,\"y\":2},\"components\":{\"Renderer\":{\"content\":\"A　\",\"contentHeight\":10,\"background\":40,\"foreground\":37},\"Actor\":{\"location\":5,\"direction\":1},\"Player\":{\"cash\":5000,\"deposit\":0,\"coupon\":0}}}";
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(GameObject.class, new GameObjectDeserializer());
		Gson gson = gsonBuilder.create();
		GameObject o = gson.fromJson(json, GameObject.class);
		System.out.println(json);
		System.out.println(gson.toJson(o));
		System.out.println(o);
		System.out.println(o.getComponent("Actor").getGameObject());
	}
	
	@Test
	public void generate() {
		String a = " 日一二三";
		System.out.print(a.charAt(1));
	}
	
	@Test
	public void testGetMenu() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.addComponent(new FieldBuyMenu(o));
		System.out.println(o.getComponent("Menu"));
	}

}

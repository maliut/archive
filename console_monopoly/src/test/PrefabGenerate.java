package test;

import kernel.GameObject;
import kernel.component.Mover;
import kernel.component.Renderer;
import kernel.component.Trigger;
import kernel.util.Point;

import org.junit.Test;

import user.Layer;
import user.component.*;

import com.google.gson.Gson;

public class PrefabGenerate {
	
	public static Gson gson = new Gson();
	
	@Test
	public void player() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.PLAYER.ordinal());
		o.setTag("player");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.p");
		Mover mover = new Mover(o);
		Player player = new Player(o);
		Trigger trigger = new Trigger(o, Trigger.TRIGGER_ON_BEGIN);
		o.addComponent(trigger);
		o.addComponent(player);
		o.addComponent(mover);
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void field() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.MAP.ordinal());
		o.setTag("cell");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.f.lv0");
		Field field = new Field(o);
		Trigger trigger = new Trigger(o, Trigger.TRIGGER_ON_STAY);
		o.addComponent(trigger);
		o.addComponent(field);
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void itemShop() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.MAP.ordinal());
		o.setTag("cell");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.itemShop");
		ItemShop field = new ItemShop(o);
		Trigger trigger = new Trigger(o, Trigger.TRIGGER_ON_STAY);
		o.addComponent(trigger);
		o.addComponent(field);
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void news() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.MAP.ordinal());
		o.setTag("cell");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.itemShop");
		News field = new News(o);
		Trigger trigger = new Trigger(o, Trigger.TRIGGER_ON_STAY);
		o.addComponent(trigger);
		o.addComponent(field);
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void bank() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.MAP.ordinal());
		o.setTag("cell");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.bank");
		Bank field = new Bank(o);
		Trigger trigger = new Trigger(o, Trigger.TRIGGER_ON_PASS);
		o.addComponent(trigger);
		o.addComponent(field);
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void lottery() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.MAP.ordinal());
		o.setTag("cell");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.lottery");
		Lottery field = new Lottery(o);
		Trigger trigger = new Trigger(o, Trigger.TRIGGER_ON_STAY);
		o.addComponent(trigger);
		o.addComponent(field);
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void itemAdd() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.MAP.ordinal());
		o.setTag("cell");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.item");
		ItemAdd field = new ItemAdd(o);
		Trigger trigger = new Trigger(o, Trigger.TRIGGER_ON_STAY);
		o.addComponent(trigger);
		o.addComponent(field);
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void couponAdd() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.MAP.ordinal());
		o.setTag("cell");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.coupon");
		CouponAdd field = new CouponAdd(o);
		Trigger trigger = new Trigger(o, Trigger.TRIGGER_ON_STAY);
		o.addComponent(trigger);
		o.addComponent(field);
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void empty() {
		GameObject o = new GameObject();
		o.setPosition(new Point(0,0));
		o.setLayer(Layer.MAP.ordinal());
		o.setTag("cell");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(2);
		renderer.setContent("icon.empty");
		o.addComponent(renderer);
		System.out.println(gson.toJson(o));
	}
	
	@Test
	public void playerInfo() {
		GameObject o = new GameObject();
		o.setPosition(new Point(85,1));
		o.setLayer(Layer.INFO.ordinal());
		o.setTag("playerInfo");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(30);
		renderer.setFormatter("PlayerInfo.format");
		PlayerInfo pi = new PlayerInfo(o);
		o.addComponent(renderer);
		o.addComponent(pi);
		System.out.println(gson.toJson(o));
	}

	@Test
	public void mainMenu() {
		GameObject o = new GameObject();
		o.setPosition(new Point(3,23));
		o.setLayer(Layer.MENU.ordinal());
		o.setTag("mainMenu");
		Renderer renderer = new Renderer(o);
		renderer.setHeight(10);
		renderer.setContent("menu.main");
		MainMenu mainMenu = new MainMenu(o);
		//mainMenu.setItemNum(5);
		o.addComponent(renderer);
		o.addComponent(mainMenu);
		System.out.println(gson.toJson(o));
	}
	
}

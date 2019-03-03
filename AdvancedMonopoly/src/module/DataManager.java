package module;

import game.*;
import game.System;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import MTool.MIO;


public class DataManager {
	
	private static MIO mio = new MIO();
	private static game.System system = new game.System();   // necessary but not loaded?....need improve
	private static Players players = new Players(); 
	private static Map map = new Map();
	private static Items items = new Items();

	public void loadSystem() throws FileNotFoundException {
		// read system info
		system = (System) mio.loadData("System.mdat", game.System.class); 
	}
	
	public void loadData() throws FileNotFoundException {
		// read system info
		//system = (System) mio.loadData("System.mdat", game.System.class);  useless£¬but need when read saves
		// read map info (cells transient)
		map = (Map) mio.loadData("Map.mdat", Map.class);	
		// read every cell of map (for every has different type)
		ArrayList<String> jsonStrings = mio.loadJsonString("Cells.mdat");
		for (int i = 0; i < map.length(); i++) {
			switch (map.getCellType(i)) {
			default: //java.lang.System.out.println(jsonStrings.get(i));
			case 0: // field
				map.setCellAt(i, (Field) mio.loadDataFromString(jsonStrings.get(i), Field.class));
				break;
			case 1: // start point
				map.setCellAt(i, (StartPoint) mio.loadDataFromString(jsonStrings.get(i), StartPoint.class));
				break;
			case 2: // bank
				map.setCellAt(i, (Bank) mio.loadDataFromString(jsonStrings.get(i), Bank.class));
				break;
			case 3: // item shop
				map.setCellAt(i, (ItemShop) mio.loadDataFromString(jsonStrings.get(i), ItemShop.class));
				break;
			case 4: // lucky point
				map.setCellAt(i, (LuckyPoint) mio.loadDataFromString(jsonStrings.get(i), LuckyPoint.class));
				break;
			}
		}
		// read players info 
		players = (Players) mio.loadData("Players.mdat", Players.class);
		// read items info
		items = (Items) mio.loadData("Items.mdat", Items.class);
	}
	
	public void loadSaves(int location) throws IOException {
		system = (System) mio.loadSaves("System.mdat", location, game.System.class);  
		map = (Map) mio.loadSaves("Map.mdat", location, Map.class);	
		ArrayList<String> jsonStrings = mio.loadSavesJsonString("Cells.mdat", location);
		for (int i = 0; i < map.length(); i++) {
			switch (map.getCellType(i)) {
			default: 
			case 0: // field
				map.setCellAt(i, (Field) mio.loadDataFromString(jsonStrings.get(i), Field.class));
				break;
			case 1: // start point
				map.setCellAt(i, (StartPoint) mio.loadDataFromString(jsonStrings.get(i), StartPoint.class));
				break;
			case 2: // bank
				map.setCellAt(i, (Bank) mio.loadDataFromString(jsonStrings.get(i), Bank.class));
				break;
			case 3: // item shop
				map.setCellAt(i, (ItemShop) mio.loadDataFromString(jsonStrings.get(i), ItemShop.class));
				break;
			case 4: // lucky point
				map.setCellAt(i, (LuckyPoint) mio.loadDataFromString(jsonStrings.get(i), LuckyPoint.class));
				break;
			}
		}
		players = (Players) mio.loadSaves("Players.mdat", location, Players.class);
		items = (Items) mio.loadSaves("Items.mdat", location, Items.class);
	}
	
	public void saveSaves(int location) throws IOException {
		mio.saveSaves("System.mdat", location, system);
		mio.saveSaves("Map.mdat", location, map);
		mio.saveSaves("Cells.mdat", location, map.getCellArray());
		mio.saveSaves("Players.mdat", location, players);
		mio.saveSaves("Items.mdat", location, items);
	}
	
	public System system() {
		return system;
	}
	
	public Players players() {
		return players;
	}
	
	public Map map() {
		return map;
	}
	
	public Items items() {
		return items;
	}
	
}

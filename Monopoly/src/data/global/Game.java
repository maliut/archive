package data.global;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;  
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import data.module.*;
import data.object.*;

public class Game {
	/* WARNING:After changing PLAYER_NUM,you should add 
	 * PlayerIcon and CellIcon in Vocab.java,or other 
	 * players will be invisible */
	public static final int PLAYER_NUM = 2;
	
	// Create global object players[],player
	public static Player[] players = new Player[PLAYER_NUM + 1];
	static {
		for (int i = 0; i < players.length; i++) {
			Game.players[i] = new Player();
		}
	}
	
	public static int currentPlayer = 1;

    // Create global object maps and cells
	public static Map map = new Map();
	public static Map mapWithInfo = new Map();
	static {
		for (int i = 0; i < Map.length; i++) {
			map.route[i] = new Cell();
			mapWithInfo.route[i] = new Cell();
		}
	}
	
	// Create global object bank
	public static Bank bank = new Bank();
	
	// Create date info
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static DateFormat dateShowFormat = new SimpleDateFormat(Vocab.DateShowFormat);
    public static Date date ;
  	public static Calendar dateCalendar = Calendar.getInstance();
  	
  	// Set debug mode
  	public static boolean debugMode = false;
  	static {
  		File f = new File("data/global/Debug.class");
  		if (f.exists()) {
            debugMode = true;
        } 
  	}
  	
  	/** Save game */
  	public static void save() throws IOException {
  		File f = new File("save.mono");
  		if (f.exists()) {
            f.delete();
        } // or saves will be old one
  		RandomAccessFile saves = new RandomAccessFile(f, "rw");  
  		// Save maps
 		for (int i = 0; i < Map.WIDTH; i++ ) {
 			for (int j = 0; j < Map.HEIGHT; j++ ) {
 				saves.writeUTF(map.image[i][j]);
 				saves.writeUTF(mapWithInfo.image[i][j]);
 			}
		}
 		// Save cells 
 		for (int i = 0; i < Map.length; i++) {
 			saves.writeInt(mapWithInfo.route[i].type);
 			saves.writeUTF(mapWithInfo.route[i].icon);
 			saves.writeInt(mapWithInfo.route[i].street);
 			saves.writeInt(mapWithInfo.route[i].streetNo);
 			saves.writeInt(mapWithInfo.route[i].x);
 			saves.writeInt(mapWithInfo.route[i].y);
 			saves.writeInt(mapWithInfo.route[i].owner);
 			saves.writeInt(mapWithInfo.route[i].level);
 			saves.writeInt(mapWithInfo.route[i].price);
 			saves.writeBoolean(mapWithInfo.route[i].isBarrier); 			
 		 }
 		// Save players
 		for (int i = 0; i < players.length; i++) {
 			saves.writeUTF(players[i].name);
 			saves.writeUTF(players[i].icon);
 			saves.writeInt(players[i].location);
 			saves.writeInt(players[i].direction);
 			saves.writeInt(players[i].cash);
 			saves.writeInt(players[i].deposit);
 			saves.writeInt(players[i].coupon);
 			saves.writeInt(players[i].property);
 			saves.writeInt(players[i].totalAssets);
 			saves.writeInt(players[i].slowRound);
 			saves.writeInt(players[i].fineFreeRound);
 			//saves.writeInt(players[i].cellRobRound);
 			for (int j = 0; j < players[i].cells.length; j++) {
 				saves.writeBoolean(players[i].cells[j]);
 			}
 			for (int j = 0; j < players[i].items.length; j++) {
 				saves.writeInt(players[i].items[j]);
 			}
 		}
 		// Save current info
 		saves.writeInt(currentPlayer);
 		saves.writeUTF(Time.getFormattedDate());
 		saves.close();
  	}
  	
  	/** Load game */
  	public static void load() throws IOException {
  		File f = new File("save.mono");
  		if (f.exists()) {
  			RandomAccessFile saves = new RandomAccessFile(f, "r"); 
  			// Load maps
  	 		for (int i = 0; i < Map.WIDTH; i++ ) {
  	 			for (int j = 0; j < Map.HEIGHT; j++ ) {
  	 				map.image[i][j] = saves.readUTF();
  	 				mapWithInfo.image[i][j] = saves.readUTF();
  	 			}
  			}
  	 		// Load cells 
  	 		for (int i = 0; i < Map.length; i++) {
  	 			mapWithInfo.route[i].type = saves.readInt();
  	 			mapWithInfo.route[i].icon = saves.readUTF();
  	 			mapWithInfo.route[i].street = saves.readInt();
  	 			mapWithInfo.route[i].streetNo = saves.readInt();
  	 			mapWithInfo.route[i].x = saves.readInt();
  	 			mapWithInfo.route[i].y = saves.readInt();
  	 			mapWithInfo.route[i].owner = saves.readInt();
  	 			mapWithInfo.route[i].level = saves.readInt();
  	 			mapWithInfo.route[i].price = saves.readInt();
  	 			mapWithInfo.route[i].isBarrier = saves.readBoolean(); 			
  	 		 }
  	 		// Save players
  	 		for (int i = 0; i < players.length; i++) {
  	 			players[i].name = saves.readUTF();
  	 			players[i].icon = saves.readUTF();
  	 			players[i].location = saves.readInt();
  	 			players[i].direction = saves.readInt();
  	 			players[i].cash = saves.readInt();
  	 			players[i].deposit = saves.readInt();
  	 			players[i].coupon = saves.readInt();
  	 			players[i].property = saves.readInt();
  	 			players[i].totalAssets = saves.readInt();
  	 			players[i].slowRound = saves.readInt();
  	 			players[i].fineFreeRound = saves.readInt();
  	 			//players[i].cellRobRound = saves.readInt();
  	 			for (int j = 0; j < players[i].cells.length; j++) {
  	 				players[i].cells[j] = saves.readBoolean();
  	 			}
  	 			for (int j = 0; j < players[i].items.length; j++) {
  	 				players[i].items[j] = saves.readInt();
  	 			}
  	 		}
  	 		// Load current info
  	 		currentPlayer = saves.readInt();
  	 		Time.nowStr = saves.readUTF();
  	 		saves.close();
        } else { // saves do not exist
        	Window.showErrorInfo(Vocab.NoSavesError);
        	Helper.getEnter();
        	System.exit(0);
        }
  	}
}

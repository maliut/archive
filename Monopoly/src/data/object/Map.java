package data.object;

import data.global.Game;
import data.module.Helper;
import data.module.Vocab;
import data.module.Window;

public class Map {

	public static final int WIDTH = 20, HEIGHT = 10;
	public static int length = 56; // Design to accept user's DIY map
	
	public String[][] image = new String[WIDTH][HEIGHT];
	public Cell[] route = new Cell[length];
	
	/** Fill image with blank */
	public Map() {
		for (int i = 0; i < WIDTH; i++ ) {
			for (int j = 0; j < HEIGHT; j++ ) {
				image[i][j] = "\u3000\u3000";
			}
		}
	}
	
	/** Generate the map by random */
	public void generateByRandom() {
		generateShape();
		generateSite();
	}
	
	public void showBarrier(int step) {
		int location = Game.players[Game.currentPlayer].location;
		for (int i = 0; i < step; i++) {
			if (Game.mapWithInfo.route[Helper.ensure(location + i + 1)].isBarrier) {
				Window.showBarrier(i + 1);
			}
		}
	}
	
	/** To print the map */
	public void show() {
		// Print image
		for (int i = 0; i < HEIGHT; i++) {
			for (int j = 0; j < WIDTH; j++) {
				System.out.print(image[j][i]);
			}
			System.out.println();
		}
	}
	
	/** To clear the map */
	public void clear() {
		// clear image
		for (int i = 0; i < WIDTH; i++ ) {
			for (int j = 0; j < HEIGHT; j++ ) {
				image[i][j] = "\u3000\u3000";
			}
		}
		// clear route
		for (int i = 0; i < route.length; i++) {
			route[i] = new Cell();
		}
	}
	
	private void generateShape() {
		// Get three random turning points
		int[] x = new int[3];
		int[] y = new int[3];
		for (int i=0; i<3; i++) {
			x[i] = 6 * i + Helper.rand(6);
			if ((i != 0) && (x[i] == x[i-1] + 1)) {
				x[i]+=1;
			}
			y[i] = 3 * i + Helper.rand(3);  
			if ((i != 0) && (y[i] == y[i-1] + 1)) {
				y[i]+=1;
			}
		}
		// Walk the map and set the x,y for cell on route 
		int step = 0;
		for (int i = 0; i < y[2]; i++) {
			route[step].x = 0;
			route[step].y = i;
			step++;
		}		
		for (int i = 0; i < x[0]; i++) {
			route[step].x = i;
			route[step].y = y[2];
			step++;
		}
		for (int i = y[2]; i < HEIGHT - 1; i++) {
			route[step].x = x[0];
			route[step].y = i;
			step++;
		}
		for (int i = x[0]; i < x[1]; i++) {
			route[step].x = i;
			route[step].y = HEIGHT - 1;
			step++;
		}
		for (int i = HEIGHT - 1; i > y[1]; i--) {
			route[step].x = x[1];
			route[step].y = i;
			step++;
		}
		for (int i = x[1]; i < WIDTH - 1; i++)	{
			route[step].x = i;
			route[step].y = y[1];
			step++;
		}
		for (int i = y[1]; i > y[0]; i--) {
			route[step].x = WIDTH - 1;
			route[step].y = i;
			step++;
		}
		for (int i = WIDTH - 1; i > x[2]; i--) {
			route[step].x = i;
			route[step].y = y[0];
			step++;
		}
		for (int i = y[0]; i > 0; i--) {
			route[step].x = x[2];
			route[step].y = i;
			step++;
		}
		for (int i = x[2]; i > 0; i--) {
			route[step].x = i;
			route[step].y = 0;
			step++;
		}
	}

	private void generateSite() {
		int street = 1, step = 0;
		int[] siteNum = new int[] {0,4,2,2,3,2,3};  // Refer to Vocab.CellTypeName
		// create 8 streets, 4*((4+6)||(5+5)||(6+4))
		int randStMaxNo = 0, randSiteType = 0;
		for (int i = 0; i < 4; i++) {
			randStMaxNo = Helper.rand(3) + 4;
			// create a street
			for (int j = 0; j < randStMaxNo; j++) {
				route[step].street = street;
				route[step].streetNo = j + 1;
				route[step].price = randPrice();
				step++;
			}
			street++;
			// create 1 or 2 special site
			for (int j = 0; j < Helper.rand(2) + 1; j++) {
				do {
					randSiteType = Helper.rand(6) + 1;
				} while (siteNum[randSiteType] == 0); 
				route[step].type = randSiteType;
				route[step].icon = Vocab.CellIcon[randSiteType];
				step++;
			}
			// create a street
			for (int j = 0; j < (10 - randStMaxNo); j++) {
				route[step].street = street;
				route[step].streetNo = j + 1;
				route[step].price = randPrice();
				step++;
			}
			street++;
			// create 1 or 2 special site
			for (int j = 0; j < Helper.rand(2) + 1; j++) {
				do {
					randSiteType = Helper.rand(6) + 1;
				} while (siteNum[randSiteType] == 0); 
				route[step].type = randSiteType;
				route[step].icon = Vocab.CellIcon[randSiteType];
				step++;
			}
		}
		// create remaining special site
		for (int j = step; j < route.length; j++) {
			do {
				randSiteType = Helper.rand(6) + 1;
			} while (siteNum[randSiteType] == 0); 
			route[step].type = randSiteType;
			route[step].icon = Vocab.CellIcon[randSiteType];
			step++;
		}
	}
	
	/** Update image by route.
	 * For map, use only when generate.
	 * For mapWithInfo, use every time to show.(Info is changing.) */
	public void update() {
		for (int i = 0; i < route.length; i++) {
			//route[i].icon = Vocab.CellIcon[6 + route[i].owner];
			image[route[i].x][route[i].y] = route[i].icon;
		}
	}
	
	/** Add players' icon on mapWithInfo */
	public void addPlayersInfo() {
		for (int i = 1; i < Game.players.length; i++) {
			if ( i != Game.currentPlayer) {
				image[route[Game.players[i].location].x][route[Game.players[i].location].y] = Vocab.PlayerIcon[i];
			}
		}
		// ensure current player's icon is displayed on the front when two players on the same cell
		image[route[Game.players[Game.currentPlayer].location].x][route[Game.players[Game.currentPlayer].location].y] = Vocab.PlayerIcon[Game.currentPlayer];
	}
	
	private int randPrice() {
		return 100 * (Helper.rand(13) + 8);
	}
}

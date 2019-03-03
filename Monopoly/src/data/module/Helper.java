package data.module;

import java.io.IOException;
import java.util.Scanner;

import data.global.Debug;
import data.global.Game;
import data.object.Map;
import data.object.Player;

public class Helper {
	
	/** Get a random integer from 0 to x-1 */
	public static int rand(int x) {
		return (int)(Math.random() * x);
	}
	
	/** Correct the location when !0<=location<MAX_STEP */
	public static int ensure(int location) {
		while (location >= Map.length) {
			location -= Map.length;
		} 
		while (location < 0) {
			location += Map.length;
		}
		return location;
	}
	
	/** Ensure the user to input an Integer. */
	@SuppressWarnings("resource")
	public static int getInt() {
		Scanner input =  new Scanner(System.in);	
		int n = 0; 
		boolean isInt = false;
		while (!isInt) {
			String str_n = input.next();
			try {
				n = Integer.parseInt(str_n);
				isInt = true;
            } catch (NumberFormatException e) {
                System.out.print(Vocab.InputError);
            }
		}
		return n;
	}
	
	/** Ensure the user to input an Integer of a certain range. */
	public static int getInt(int min, int max) {
		int n = getInt();
		while (!(min <= n && n <= max)) {
			System.out.print(Vocab.InputError);
			n = getInt();
		}
		return n;
	}
	
	@SuppressWarnings("resource")
	public static String getStr() {
		Scanner input =  new Scanner(System.in);
		return input.next();
	}
	
	public static void getEnter() {
		//System.out.print("<回车以继续>\n");
		try {  
			System.in.read();                          
        } catch(IOException e){   
        }  
	}
	
	/** Switch player
	 *  rewrite it if you change the player number */
	public static void switchPlayer() {
		Game.currentPlayer = 3 - Game.currentPlayer;
	}
	
	public static void commitFail() {
		switchPlayer();
		endGameWithWinOf(Game.players[Game.currentPlayer]);
	}
	
	public static void endGameWithWinOf(Player p) {
		Window.showEndGameWithWinOf(p);
		getEnter();
		System.exit(0);
	}
	
	public static void debug() {
  		if (Game.debugMode) {
            Debug.run();
        } 
	}
	
	public static void autoDebug() {
		if (Game.debugMode) {
            Debug.auto();
        } 
	}
}

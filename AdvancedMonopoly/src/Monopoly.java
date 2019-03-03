import game.Actor;
import game.Cell;
import game.Field;
import game.Items;
import game.Map;
import game.Players;

import java.io.IOException;

import module.SceneManager;
import MTool.MIO;

public class Monopoly {

	/* 注：
	 * 每一个玩家需要一个主题颜色
	 * 颜色在外部使用类似vx的skin，即使用整数作为某个预先定义的颜色的索引。
	 */
	
	
	
	public static void main(String[] args) throws IOException {
		new SceneManager().run();
		//Items a = new Items();
		//new MIO().saveSaves("Items.mdat", 0, a);
		//game.System a = new game.System();
		//new MIO().saveSaves("System.mdat", 0, a);
		//Players a = new Players();
		//new MIO().saveSaves("Players.mdat", 0, a);
		//Actor a = new Actor();
		//new MIO().saveSaves("Satsuki.mdat", 0, a);
		//new MIO().saveSaves("test.mdat", 0, a);
		//new MIO().saveSaves("Yayoi.mdat", 0, a);
		//Map a = new Map();
		//a.cell[0] = new Cell(1,1,1,1);
		//a.cell[1] = new Field(2,2,2,2,2);
		//new MIO().saveSaves("Map.mdat", 0, a);
		//Map b = (Map) new MIO().loadSaves("test.txt", 0,Map.class);
		//System.out.print((Field)(b.cell[1]));
		/*int[] x = {3,2,2,2,2,2,2,3,4,5,5,6,7,8,9,10,11,11,11,12,13,14,14,14,15,16,17,18,19,19,19,19,19,19,19,19,19,18,18,18,17,16,15,14,13,13,13,12,11,10,10,10,9,8,7,6,5,4,4,4,4,4};
		int[] y = {8,8,7,6,5,4,3,3,3,3,2,2,2,2,2,2,2,3,4,4,4,4,3,2,2,2,2,2,2,3,4,5,6,7,8,9,10,10,11,12,12,12,12,12,12,11,10,10,10,10,11,12,12,12,12,12,12,12,11,10,9,8};
		//System.out.println(x.length);
		//System.out.println(y.length);
		int[] iconX = {3,1,1,1,1,1,1,3,4,5,5,6,7,8,9,10,12,12,11,12,13,14,14,14,15,16,17,18,19,20,18,20,20,20,20,20,20,18,18,19,17,16,15,14,13,13,13,12,11,10,11,11,9,8,7,6,5,3,3,3,3,4};
		int[] iconY = {7,8,7,6,5,4,3,3,2,3,1,1,1,1,1,1,2,3,4,3,3,4,3,1,1,1,1,1,1,3,4,5,6,7,8,9,10,10,11,12,13,13,13,13,13,11,9,9,9,10,11,12,13,13,13,13,13,12,11,10,9,8};
		//System.out.println(iconX.length);
		//System.out.println(iconY.length);
		int[] type = {1,0,0,0,0,0,0,4,2,4,0,0,0,0,0,0,4,4,4,0,0,4,4,0,0,0,0,0,0,3,1,0,0,0,0,0,0,4,4,2,0,0,0,0,0,4,0,0,0,4,4,4,0,0,0,0,3,0,0,0,0,4};	
		Cell[] a = new Cell[62];
		{
			for(int i = 0; i <62;i++) {
				if (type[i]==0) {
					a[i] = new Field(x[i],y[i],iconX[i],iconY[i],1);
				} else {
					a[i] = new Cell(x[i],y[i],iconX[i],iconY[i]);
				}
			}
		}
		new MIO().saveSaves("Cells.mdat", 0, a);*/
	}
}

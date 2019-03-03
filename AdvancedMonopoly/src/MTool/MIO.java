package MTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

import module.DataManager;

import com.google.gson.Gson;

public class MIO {
	
	static DataManager data = new DataManager();
	//String theme = data.system.getTheme();
	Gson gson = new Gson();
	
	public ImageIcon readImageIcon(String filename) {
		String extendFilename = String.format("theme/%s/Graphics/%s", data.system().getTheme(), filename);
		//System.out.println(extendFilename);
		if (!(new File(extendFilename).exists())) {
			extendFilename = String.format("theme/default/Graphics/%s", filename);
		} 
		//System.out.println(extendFilename);
		return new ImageIcon(extendFilename);
	}
	
	/** read image from file */
	public Image readImage(String filename) {
		return readImageIcon(filename).getImage();
	}
	
	/** read buffered image from file */
	public BufferedImage readBufferedImage(String filename) throws FileNotFoundException, IOException {
		String extendFilename = String.format("theme/%s/Graphics/%s", data.system().getTheme(), filename);
		if (!(new File(extendFilename).exists())) {
			extendFilename = String.format("theme/default/Graphics/%s", filename);
		} 
		return ImageIO.read(new FileInputStream(extendFilename));
	}

	/** load json file */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object loadData(String filename, Class classT) throws FileNotFoundException {
		String extendFilename = String.format("theme/%s/Data/%s", data.system().getTheme(), filename);
		Scanner input = new Scanner(new File(extendFilename));
		String jsonData = input.nextLine();
		input.close();
		return gson.fromJson(jsonData, classT);		
	}
	
	// for array object
	public ArrayList<String> loadJsonString(String filename) throws FileNotFoundException {
		String extendFilename = String.format("theme/%s/Data/%s", data.system().getTheme(), filename);
		Scanner input = new Scanner(new File(extendFilename));
		ArrayList<String> jsonStrings = new ArrayList<String>();
		while (input.hasNextLine()) {
			String jsonData = input.nextLine();
			jsonStrings.add(jsonData);
		}
		input.close();
		return jsonStrings;		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object loadDataFromString(String jsonString, Class classT) {
		return gson.fromJson(jsonString, classT);		
	}
	
	/** save saves json file 
	 * @throws IOException */
	public void saveSaves(String filename, int saveLocation, Object obj) throws IOException {
		String extendFilename = String.format("theme/default/Saves/%d/%s", saveLocation, filename);
		File f = new File(extendFilename);
		if (f.exists()) {	f.delete();} 
		f.createNewFile();
		PrintWriter output = new PrintWriter(f);
		output.print(gson.toJson(obj));
		output.close();
	}
	
	public void saveSaves(String filename, int saveLocation, Object[] objs) throws IOException {
		String extendFilename = String.format("theme/default/Saves/%d/%s", saveLocation, filename);
		File f = new File(extendFilename);
		if (f.exists()) {	f.delete();} 
		f.createNewFile();
		PrintWriter output = new PrintWriter(f);
		for (int i = 0; i < objs.length; i++) {
			output.println(gson.toJson(objs[i]));
		}
		output.close();
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object loadSaves(String filename, int saveLocation, Class classT) throws IOException {
		String extendFilename = String.format("theme/default/Saves/%d/%s", saveLocation, filename);
		Scanner input = new Scanner(new File(extendFilename));
		String jsonData = input.nextLine();
		input.close();
		return gson.fromJson(jsonData, classT);		
	}
	
	public ArrayList<String> loadSavesJsonString(String filename, int saveLocation) throws FileNotFoundException {
		String extendFilename = String.format("theme/default/Saves/%d/%s", saveLocation, filename);
		Scanner input = new Scanner(new File(extendFilename));
		ArrayList<String> jsonStrings = new ArrayList<String>();
		while (input.hasNextLine()) {
			String jsonData = input.nextLine();
			jsonStrings.add(jsonData);
		}
		input.close();
		return jsonStrings;		
	}
}

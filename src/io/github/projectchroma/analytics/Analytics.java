package io.github.projectchroma.analytics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import io.github.projectchroma.analytics.gui.Window;

public class Analytics{
	private static Log log;
	private static Font font;
	private static BufferedImage logo;
	public static void main(String[] args){
		try{
			log = new Log();
		}catch(FileNotFoundException ex){
			System.err.println("Error creating logger");
			ex.printStackTrace();
		}
		try{
			font = Font.createFont(Font.TRUETYPE_FONT, Analytics.class.getResourceAsStream("/assets/fonts/mysteron.ttf"));
		}catch(IOException | FontFormatException ex){
			log.write("Error creating primary font", Log.FATAL_ERROR);
			ex.printStackTrace();
			System.exit(-1);
		}
		try{
			logo = ImageIO.read(Analytics.class.getResourceAsStream("/assets/images/logo.png"));
		}catch(IOException ex){
			log.write("Error loading logo image", Log.FATAL_ERROR);
			ex.printStackTrace();
			System.exit(-1);
		}
		
		Window window = new Window();
		window.show();
	}

	public static Font getFont(float size){
		return font.deriveFont(size);
	}
	public static BufferedImage getLogo(){
		return logo;
	}
	public static Log log(){
		return log;
	}
}

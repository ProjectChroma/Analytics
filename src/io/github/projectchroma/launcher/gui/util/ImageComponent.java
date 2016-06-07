package io.github.projectchroma.launcher.gui.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageComponent extends BaseComponent{
	private static final long serialVersionUID = 1L;
	private BufferedImage image;
	public ImageComponent(BufferedImage image){this(image, image.getWidth(), image.getHeight());}
	public ImageComponent(BufferedImage image, int width){this(image, width, width * image.getHeight() / image.getWidth());}//Maintain aspect ratio
	public ImageComponent(BufferedImage image, int width, int height){
		super(new Dimension(width, height));
		this.image = image;
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.drawImage(image, 0, 0, getWidth(), getHeight(), 0, 0, image.getWidth(), image.getHeight(), null);
	}
}

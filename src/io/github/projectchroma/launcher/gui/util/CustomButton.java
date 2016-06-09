package io.github.projectchroma.launcher.gui.util;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class CustomButton extends JButton implements MouseListener{
	private static final long serialVersionUID = 1L;
	private Runnable click;
	public CustomButton(String text, Font font){this(text, font, null);}
	public CustomButton(String text, Font font, Runnable click){
		super(text);
		this.click = click;
		addMouseListener(this);
	}
	@Override public void mouseClicked(MouseEvent e){}
	@Override public void mousePressed(MouseEvent e){}
	@Override
	public void mouseReleased(MouseEvent e){
		click.run();
	}
	@Override public void mouseEntered(MouseEvent e){}
	@Override public void mouseExited(MouseEvent e){}
}

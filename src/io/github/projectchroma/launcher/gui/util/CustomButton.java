package io.github.projectchroma.launcher.gui.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class CustomButton extends JButton implements MouseListener{
	private static final long serialVersionUID = 1L;
	private static final int HOVER_DELTA = 50, ACTIVE_DELTA = 100;
	private Color bg, bgHover, bgActive;
	private Runnable click;
	public CustomButton(String text, Font font, Color bg, Color fg, Color border){this(text, font, bg, fg, border, null);}
	public CustomButton(String text, Font font, Color bg, Color fg, Color border, Runnable click){
		super(text);
		setFont(font);
		setBackground(this.bg = bg);
		bgHover = new Color(bg.getRed() + HOVER_DELTA, bg.getGreen() + HOVER_DELTA, bg.getBlue() + HOVER_DELTA, bg.getAlpha());
		bgActive = new Color(bg.getRed() + ACTIVE_DELTA, bg.getGreen() + ACTIVE_DELTA, bg.getBlue() + ACTIVE_DELTA, bg.getAlpha());
		setForeground(fg);
		setBorder(new LineBorder(border, 5, false));
		this.click = click;
		addMouseListener(this);
	}
	@Override public void mouseClicked(MouseEvent e){}
	@Override public void mousePressed(MouseEvent e){
		setBackground(bgActive);
		repaint();
	}
	@Override
	public void mouseReleased(MouseEvent e){
		setBackground(bgHover);
		repaint();
		click.run();
	}
	@Override
	public void mouseEntered(MouseEvent e){
		setBackground(bgHover);
		repaint();
	}
	@Override
	public void mouseExited(MouseEvent e){
		setBackground(bg);
		repaint();
	}
}

package io.github.projectchroma.launcher.gui.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;

import javax.swing.JComponent;

public class BaseComponent extends JComponent{
	private static final long serialVersionUID = 1L;
	
	public BaseComponent(){this(null, null, null, null);}
	public BaseComponent(LayoutManager layout){this(layout, null, null, null);}
	public BaseComponent(Color bg, Color fg){this(null, bg, fg, null);}
	public BaseComponent(Dimension size){this(null, null, null, size);}
	public BaseComponent(LayoutManager layout, Color bg, Color fg){this(layout, bg, fg, null);}
	public BaseComponent(LayoutManager layout, Dimension size){this(layout, null, null, size);}
	public BaseComponent(Color bg, Color fg, Dimension size){this(null, bg, fg, size);}
	public BaseComponent(LayoutManager layout, Color bg, Color fg, Dimension size){
		setLayout(layout);
		setBackground(bg);
		setForeground(fg);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
	}

	@Override
	protected void paintComponent(Graphics g){
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(getForeground());
	}
}

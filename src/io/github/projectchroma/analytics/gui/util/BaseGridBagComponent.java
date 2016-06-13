package io.github.projectchroma.analytics.gui.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JTextField;

public class BaseGridBagComponent extends BaseComponent{
	private static final long serialVersionUID = 1L;
	protected GridBagConstraints c = initConstraints();
	public BaseGridBagComponent(){super(new GridBagLayout());}
	public BaseGridBagComponent(Color bg, Color fg){super(new GridBagLayout(), bg, fg);}
	public BaseGridBagComponent(Dimension size){super(new GridBagLayout(), size);}
	public BaseGridBagComponent(Color bg, Color fg, Dimension size){super(new GridBagLayout(), bg, fg, size);}
	private GridBagConstraints initConstraints(){
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		c.gridx = c.gridy = 0;
		c.gridwidth = c.gridheight = 1;
		c.weightx = c.weighty = 1;
		return c;
	}
	protected void right(){c.gridx += c.gridwidth;}
	protected void down(){c.gridy += c.gridheight;}
	protected void nextRow(){nextRow(0);}
	protected void nextRow(int startX){down(); c.gridx = startX;}
	public Component add(Component comp){
		add(comp, c);
		System.out.printf("(%d, %d) <%d, %d>: %s%n", c.gridx, c.gridy, c.gridwidth, c.gridheight, toString(comp));
		return comp;
	}
	private String toString(Component comp){
		if(comp instanceof JTextField) return '"' + ((JTextField)comp).getText() + '"';
		else if(comp instanceof JButton) return '[' + ((JButton)comp).getText() + ']';
		else return comp.getClass().getSimpleName();
	}
}

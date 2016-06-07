package io.github.projectchroma.launcher.gui.util;

import java.awt.Dimension;

public class Spacer extends BaseComponent{
	private static final long serialVersionUID = 1L;
	public Spacer(int width){this(width, 1);}
	public Spacer(int width, boolean max){this(width, 1, true, true, max);}
	public Spacer(int width, boolean min, boolean pref, boolean max){this(width, 1, min, max, pref);}
	
	public Spacer(int width, int height){this(width, height, true, true, true);}
	public Spacer(int width, int height, boolean max){this(width, height, true, true, max);}
	public Spacer(int width, int height, boolean min, boolean pref, boolean max){this(new Dimension(width, height), min, pref, max);}
	
	public Spacer(Dimension size){this(size, true, true, true);}
	public Spacer(Dimension size, boolean max){this(size, true, true, max);}
	public Spacer(Dimension size, boolean min, boolean pref, boolean max){
		if(min)  setMinimumSize(size);
		if(pref) setPreferredSize(size);
		if(max)  setMaximumSize(size);
	}
}

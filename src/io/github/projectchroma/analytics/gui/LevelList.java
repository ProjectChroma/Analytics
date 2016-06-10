package io.github.projectchroma.analytics.gui;

import java.awt.Font;

import javax.swing.BoxLayout;

import io.github.projectchroma.analytics.Analytics;
import io.github.projectchroma.analytics.Log;
import io.github.projectchroma.launcher.gui.util.BaseComponent;
import io.github.projectchroma.launcher.gui.util.CustomButton;
import io.github.projectchroma.launcher.gui.util.CustomTextField;

public class LevelList extends BaseComponent{
	private static final long serialVersionUID = 1L;
	private StatsView parent;
	private Font buttonFont;
	public LevelList(StatsView parent){
		this.parent = parent;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new CustomTextField("Levels", Analytics.getFont(36), false, getBackground(), getForeground()));
		buttonFont = Analytics.getFont(24F);
	}
	public void setLevels(Iterable<Integer> levels){
		while(getComponentCount() > 1) remove(getComponentCount()-1);
		for(int level : levels) add(new CustomButton("Level " + level, buttonFont, () -> {parent.showLevel(level);}));
		Analytics.log().write("Displaying levels " + levels, Log.DEBUG);
		revalidate();
		repaint();
	}
}

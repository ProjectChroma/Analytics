package io.github.projectchroma.analytics.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JTextField;

import io.github.projectchroma.analytics.Analytics;
import io.github.projectchroma.analytics.Log;
import io.github.projectchroma.analytics.gui.util.BaseComponent;
import io.github.projectchroma.analytics.gui.util.CustomButton;
import io.github.projectchroma.analytics.gui.util.CustomTextField;

public class StatsArea extends BaseComponent{
	private static final long serialVersionUID = 1L;
	public static final StatsArea instance = new StatsArea();
	private final CustomTextField title;
	private final Graph graph;
	private Map<Integer, List<String>> data = new HashMap<>();
	private int maxLevel = 0, currentLevel = 0;
	private StatsArea(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHEAST;
		c.fill = GridBagConstraints.VERTICAL;
		c.gridwidth = c.gridheight = 1;
		c.gridx = c.gridy = 0;
		c.weightx = c.weighty = 1;
		
		Font buttonFont = Analytics.getFont(24);
		add(new CustomButton("<<", buttonFont, () -> {showLevel(currentLevel - 1);}), c);
		
		c.gridx++;
		c.weightx = 6;
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		add(title = new CustomTextField("Level Data", Analytics.getFont(40F), false), c);
		title.setHorizontalAlignment(JTextField.CENTER);
		
		c.gridx++;
		c.weightx = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.VERTICAL;
		add(new CustomButton(">>", buttonFont, () -> {showLevel(currentLevel + 1);}), c);
		
		c.gridx = 0;
		c.gridy++;
		c.weighty = 4;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.NORTH;
		add(graph = new Graph(), c);
	}
	public void loadData(File[] files){
		data.clear();
		maxLevel = 0;
		for(File file : files){
			Analytics.log().write("Reading file " + file, Log.DEBUG);
			try(Scanner in = new Scanner(file)){
				while(in.hasNextLine()){
					String line = in.nextLine();
					int idx = 0;
					while(Character.isDigit(line.charAt(idx))) ++idx;
					int level = Integer.parseInt(line.substring(0, idx));
					if(level > maxLevel) maxLevel = level;
					String event = line.substring(idx);
					data.computeIfAbsent(level, (key) -> new ArrayList<>()).add(event);
				}
			}catch(FileNotFoundException ex){
				Analytics.log().write("Analytics file " + file + " does not exist", ex, Log.ERROR);
			}
		}
		for(int i=1; i<maxLevel; i++) data.computeIfAbsent(i, (key) -> new ArrayList<>());//Fill all levels as empty lists
		showLevel(1);//First level
		repaint();
	}
	protected void showLevel(int level){
		if(level <= 0 || level > maxLevel){
			Analytics.log().write("No such level " + level, Log.ERROR);
			return;
		}
		title.setText("Level " + level);
		title.repaint();
		graph.showData(data.get(level));
		this.currentLevel = level;
	}
}

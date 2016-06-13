package io.github.projectchroma.analytics.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Point;
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
import io.github.projectchroma.analytics.gui.util.BaseGridBagComponent;
import io.github.projectchroma.analytics.gui.util.CustomButton;
import io.github.projectchroma.analytics.gui.util.CustomTextField;

public class StatsArea extends BaseGridBagComponent{
	private static final long serialVersionUID = 1L;
	public static final StatsArea instance = new StatsArea();
	private final CustomTextField title, wpr, lpr, dwpr, dlpr;
	private final Graph graph;
	private Map<Integer, List<String>> data = new HashMap<>();
	private int maxLevel = 0, currentLevel = 0;
	private StatsArea(){
		c.weightx = 0;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		
		Font buttonFont = Analytics.getFont(24);
		add(new CustomButton("<<", buttonFont, () -> {showLevel(currentLevel - 1);}));
		
		right();
		c.weightx = 6;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.NORTH;
		add(title = new CustomTextField("Level Data", Analytics.getFont(40), false));
		title.setHorizontalAlignment(JTextField.CENTER);
		
		right();
		c.weightx = 0;
		c.gridwidth = 1;
		c.anchor = GridBagConstraints.NORTHEAST;
		c.fill = GridBagConstraints.VERTICAL;
		add(new CustomButton(">>", buttonFont, () -> {showLevel(currentLevel + 1);}));
		
		nextRow(1);
		c.weighty = 4;
		c.gridwidth = 2;
		c.gridheight = 5;
		c.anchor = GridBagConstraints.NORTH;
		add(graph = new Graph());
		
		right();
		c.weightx = 1;
		c.weighty = 1;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.BOTH;
		add(new CustomTextField("Statistics", Analytics.getFont(36), false));
		
		Font font = Analytics.getFont(24);
		c.gridwidth = 1;
		
		nextRow(3);
		c.weightx = 1;
		add(new CustomTextField("WPR", font, false));
		c.weightx = 10;
		right();
		add(wpr = new CustomTextField("", font, false));
		
		nextRow(3);
		c.weightx = 1;
		add(new CustomTextField("LPR", font, false));
		c.weightx = 10;
		right();
		add(lpr = new CustomTextField("", font, false));
		
		nextRow(3);
		c.weightx = 1;
		add(new CustomTextField("dWPR", font, false));
		c.weightx = 10;
		right();
		add(dwpr = new CustomTextField("", font, false));
		
		nextRow(3);
		c.weightx = 1;
		add(new CustomTextField("dLPR", font, false));
		c.weightx = 10;
		right();
		add(dlpr = new CustomTextField("", font, false));
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
		writeDerivativeStat(graph.wins, wpr);
		writeDerivativeStat(graph.losses, lpr);
		writeDerivativeStat(graph.dWins, dwpr);
		writeDerivativeStat(graph.dLosses, dlpr);
		this.currentLevel = level;
	}
	protected void writeDerivativeStat(List<Point> data, JTextField text){
		Point first = data.get(0), last = data.get(data.size()-1);
		double dy = last.y - first.y, dx = last.x - first.x;
		text.setText(String.format("%.5f", dy / dx));
	}
}

package io.github.projectchroma.analytics.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import io.github.projectchroma.analytics.Analytics;
import io.github.projectchroma.analytics.Log;
import io.github.projectchroma.launcher.gui.util.BaseComponent;

public class StatsView extends BaseComponent{
	private static final long serialVersionUID = 1L;
	public static final StatsView instance = new StatsView();
	private final LevelList levels;
	private final GraphArea graph;
	private Map<Integer, List<String>> data = new HashMap<>();
	private StatsView(){
		super(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = c.gridheight = 1;
		c.gridx = c.gridy = 0;
		c.weightx = 0.2;
		c.weighty = 1;
		add(levels = new LevelList(this), c);
		
		c.gridx++;
		c.weightx = 0.6;
		add(graph = new GraphArea(), c);
	}
	public void loadData(File[] files){
		data.clear();
		for(File file : files){
			Analytics.log().write("Reading file " + file, Log.DEBUG);
			try(Scanner in = new Scanner(file)){
				while(in.hasNextLine()){
					String line = in.nextLine();
					int idx = 0;
					while(Character.isDigit(line.charAt(idx))) ++idx;
					int level = Integer.parseInt(line.substring(0, idx));
					String event = line.substring(idx);
					data.computeIfAbsent(level, (key) -> new ArrayList<>()).add(event);
				}
			}catch(FileNotFoundException ex){
				Analytics.log().write("Analytics file " + file + " does not exist", ex, Log.ERROR);
			}
		}
		levels.setLevels(data.keySet());
		graph.showData(data.values().iterator().next());//First dataset
		repaint();
	}
	protected void showLevel(int level){
		graph.showData(data.get(level));
	}
}

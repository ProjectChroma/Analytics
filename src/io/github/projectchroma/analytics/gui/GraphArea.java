package io.github.projectchroma.analytics.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import io.github.projectchroma.analytics.Analytics;
import io.github.projectchroma.analytics.Log;
import io.github.projectchroma.launcher.gui.util.BaseComponent;

public class GraphArea extends BaseComponent{
	private static final long serialVersionUID = 1L;
	private static final int AXIS_WIDTH = 5;
	private List<Point> points = new ArrayList<>();
	private int graphWidth = 0, graphHeight = 0;
	public GraphArea(){
		super(Color.white, Color.black, new Dimension(400, 300));
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		g.fillRect(0, 0, AXIS_WIDTH, getHeight());
		g.fillRect(0, getHeight()-AXIS_WIDTH, getWidth(), AXIS_WIDTH);
		
		if(graphWidth == 0 || graphHeight == 0) return;
		
		int height = getHeight() - AXIS_WIDTH, xScale = getWidth() / graphWidth, yScale = height / graphHeight;
		Analytics.log().write("Scale: " + xScale + "x" + yScale, Log.DEBUG);
		
		g.setColor(Color.yellow.darker());
		Point prev = points.get(0);
		for(Point p : points){
			g.drawLine(prev.x * xScale, height - prev.y * yScale, p.x * xScale, height - p.y * yScale);
			prev = p;
		}
	}
	protected void showData(List<String> data){
		points.clear();
		
		graphWidth = data.size();
		graphHeight = 0;
		for(String event : data)
			if(event.equals("W")) graphHeight++;
		
		points.add(new Point(0, 0));
		int x = 0, y = 0;
		for(String event : data){
			x++;
			if(event.equals("W")) y++;
			points.add(new Point(x, y));
			Analytics.log().write("(" + x + ", " + y + ")", Log.DEBUG);
		}
		repaint();
	}
}

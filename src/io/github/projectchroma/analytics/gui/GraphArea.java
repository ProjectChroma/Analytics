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
	private static final int AXIS_WIDTH = 5, KEY_WIDTH = 50;
	private List<Point> points = new ArrayList<>();
	private int graphWidth = 0, graphHeight = 0;
	public GraphArea(){
		super(Color.white, Color.black, new Dimension(400, 300));
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw axes
		g.fillRect(KEY_WIDTH, 0, AXIS_WIDTH, getHeight()-KEY_WIDTH);
		g.fillRect(KEY_WIDTH, getHeight()-AXIS_WIDTH-KEY_WIDTH, getWidth()-KEY_WIDTH, AXIS_WIDTH);
		
		if(graphWidth == 0 || graphHeight == 0) return;
		
		int width = getWidth() - (KEY_WIDTH + AXIS_WIDTH), height = getHeight() - (AXIS_WIDTH + KEY_WIDTH), xScale = width / graphWidth, yScale = height / graphHeight;
		Analytics.log().write("Scale: " + xScale + "x" + yScale, Log.DEBUG);
		
		//Draw vertical key
		for(int y=0; y<=graphHeight; ++y){
			g.drawString(y + "", KEY_WIDTH/2, (graphHeight - y) * yScale + getFontMetrics(getFont()).getHeight()/2);
		}
		//Draw horizontal key
		for(int x=0; x<=graphWidth; ++x){
			g.drawString(x + "", x * xScale - getFontMetrics(getFont()).stringWidth(x + "")/2 + KEY_WIDTH, getHeight() - (KEY_WIDTH + getFontMetrics(getFont()).getHeight()) / 2);
		}
		//Draw grid rows
		for(int y=0; y<=graphHeight; ++y){
			g.drawLine(KEY_WIDTH + AXIS_WIDTH, y * yScale, getWidth(), y * yScale);
		}
		//Draw grid columns
		for(int x=0; x<=graphWidth; ++x){
			g.drawLine(KEY_WIDTH + AXIS_WIDTH + x * xScale, 0, KEY_WIDTH + AXIS_WIDTH + x * xScale, height);
		}
		
		g.setColor(Color.yellow.darker());
		Point prev = points.get(0);
		for(Point p : points){
			for(int i=-4; i<=0; i++){
				g.drawLine(prev.x * xScale + KEY_WIDTH + AXIS_WIDTH, height - prev.y * yScale + i, p.x * xScale + KEY_WIDTH + AXIS_WIDTH, height - p.y * yScale + i);
			}
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

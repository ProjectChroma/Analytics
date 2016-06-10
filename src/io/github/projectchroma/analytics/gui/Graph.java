package io.github.projectchroma.analytics.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import io.github.projectchroma.analytics.Analytics;
import io.github.projectchroma.analytics.Log;
import io.github.projectchroma.analytics.gui.util.BaseComponent;

public class Graph extends BaseComponent{
	private static final long serialVersionUID = 1L;
	private static final int AXIS_WIDTH = 5, KEY_WIDTH = 50, STROKE_WIDTH = 3;
	private List<Point> wins = new ArrayList<>(), dWins = new ArrayList<>(), losses = new ArrayList<>(), dLosses = new ArrayList<>();
	private int graphWidth = 0, graphHeight = 0, width, height, xScale, yScale;
	public Graph(){
		super(Color.white, Color.black, new Dimension(400, 400));
	}
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		//Draw axes
		g.fillRect(KEY_WIDTH, 0, AXIS_WIDTH, getHeight()-KEY_WIDTH);
		g.fillRect(KEY_WIDTH, getHeight()-AXIS_WIDTH-KEY_WIDTH, getWidth()-KEY_WIDTH, AXIS_WIDTH);
		
		if(graphWidth == 0 || graphHeight == 0) return;
		
		width = getWidth() - (KEY_WIDTH + AXIS_WIDTH);
		height = getHeight() - (AXIS_WIDTH + KEY_WIDTH);
		xScale = width / graphWidth;
		yScale = height / graphHeight;
		Analytics.log().write("Scale: " + xScale + "x" + yScale, Log.DEBUG);
		
		//Draw vertical key
		for(int y=0; y<=graphHeight; ++y){
			g.drawString(y + "", KEY_WIDTH/2, (graphHeight - y) * yScale + getFontMetrics(getFont()).getHeight()/2 + AXIS_WIDTH);
		}
		//Draw horizontal key
		for(int x=0; x<=graphWidth; ++x){
			g.drawString(x + "", x * xScale - getFontMetrics(getFont()).stringWidth(x + "")/2 + KEY_WIDTH, getHeight() - (KEY_WIDTH + getFontMetrics(getFont()).getHeight()) / 2);
		}
		//Draw grid rows
		for(int y=0; y<=graphHeight; ++y) drawLine(0, y, graphWidth, y, g);
		//Draw grid columns
		for(int x=0; x<=graphWidth; ++x) drawLine(x, 0, x, graphHeight, g);
		
		drawPoints(wins, Color.yellow, g);
		drawPoints(dWins, Color.orange.darker(), g);
		drawPoints(losses, Color.red, g);
		drawPoints(dLosses, Color.red.darker(), g);
	}
	private void drawPoints(List<Point> points, Color c, Graphics g){
		g.setColor(c);
		Point prev = points.get(0);
		for(Point p : points){
			drawLine(prev.x, prev.y, p.x, p.y, STROKE_WIDTH, g);
			prev = p;
		}
	}
	private void drawLine(int x1, int y1, int x2, int y2, Graphics g){drawLine(x1, y1, x2, y2, 1, g);}
	private void drawLine(int x1, int y1, int x2, int y2, int stroke, Graphics g){
		int rx1 = x1 * xScale + (KEY_WIDTH + AXIS_WIDTH),
				rx2 = x2 * xScale + (KEY_WIDTH + AXIS_WIDTH);
		int ry1 = height - y1 * yScale, ry2 = height - y2 * yScale;
		for(int i=-stroke/2; i<=stroke/2; i++)
			g.drawLine(rx1, ry1 + i, rx2, ry2 + i);
	}
	protected void showData(List<String> data){
		wins.clear();
		dWins.clear();
		losses.clear();
		dLosses.clear();
		
		graphWidth = data.size();
		graphHeight = 0;
		int numWins = 0, numLosses = 0;
		for(String event : data)
			if(event.equals("W")) numWins++;
			else if(event.equals("L")) numLosses++;
		graphHeight = Math.max(numWins, numLosses);
		
		createData(wins, data, "W");
		createData(losses, data, "L");
		createDerivative(wins, dWins);
		createDerivative(losses, dLosses);
		
		repaint();
	}
	private void createData(List<Point> points, List<String> data, String key){
		points.clear();
		points.add(new Point(0, 0));
		int x = 0, y = 0;
		for(String event : data){
			x++;
			if(event.equals(key)) y++;
			points.add(new Point(x, y));
		}
	}
	private void createDerivative(List<Point> data, List<Point> derivative){
		derivative.clear();
		Point prev = data.get(0);
		for(Point p : data){
			derivative.add(new Point(p.x, p.y - prev.y));
			prev = p;
		}
		derivative.remove(0);
	}
}

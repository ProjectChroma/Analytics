package io.github.projectchroma.analytics.gui;

import javax.swing.JFrame;

import io.github.projectchroma.analytics.Analytics;

public class Window{
	private JFrame window;
	public Window(){
		window = new JFrame("Chroma Launcher");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setContentPane(new ContentArea());
		window.setIconImage(Analytics.getLogo());
		window.setSize(800, 600);
	}
	public void show(){
		window.setVisible(true);
	}
	public void hide(){
		window.setVisible(false);
	}
}

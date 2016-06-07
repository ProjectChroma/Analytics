package io.github.projectchroma.analytics.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JTextField;

import io.github.projectchroma.analytics.Analytics;
import io.github.projectchroma.analytics.Log;
import io.github.projectchroma.launcher.gui.util.BaseComponent;
import io.github.projectchroma.launcher.gui.util.Spacer;

public class ContentArea extends BaseComponent{
	private static final long serialVersionUID = 1L;
	private JTextField pathField = new JTextField();
	public ContentArea(){
		super(new GridBagLayout(), Color.white, Color.black);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 2;
		c.gridheight = 1;
		c.gridx = c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		add(new Header(), c);
		
		c.gridy++;
		c.gridwidth = 1;
		c.weightx = 9;
		c.weighty = 1;
		pathField.setFont(Analytics.getFont(24));
		add(pathField, c);
		
		c.gridx++;
		c.weightx = 1;
		JButton browseButton = new JButton("Browse");
		add(browseButton, c);
		browseButton.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				Analytics.log().write("Browsing", Log.DEBUG);
			}
		});
		
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 2;
		c.weightx = 1;
		c.weighty = 19;
		add(new Spacer(1, false), c);
	}
}

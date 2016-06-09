package io.github.projectchroma.analytics.gui;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextField;

import io.github.projectchroma.analytics.Analytics;
import io.github.projectchroma.analytics.Log;
import io.github.projectchroma.launcher.gui.util.BaseComponent;

public class ContentArea extends BaseComponent{
	private static final long serialVersionUID = 1L;
	private JTextField pathField = new JTextField();
	private JFileChooser fileSelector = new JFileChooser(new File("").getAbsoluteFile());
	private File gameDir;
	public ContentArea(){
		super(new GridBagLayout(), Color.white, Color.black);
		fileSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		c.gridheight = 1;
		c.gridx = c.gridy = 0;
		c.weightx = 1;
		c.weighty = 0;
		add(new Header(), c);
		
		c.gridy++;
		c.weighty = 0;
		JTextField fileCaption = new JTextField("Game directory");
		fileCaption.setEditable(false);
		fileCaption.setFocusable(false);
		fileCaption.setBackground(null);
		fileCaption.setBorder(null);
		fileCaption.setFont(fileCaption.getFont().deriveFont(20F));
		add(fileCaption, c);
		
		c.gridy++;
		c.gridwidth = 1;
		c.weightx = 9;
		c.weighty = 1;
		pathField.setFont(pathField.getFont().deriveFont(16F));
		pathField.setEditable(false);
		pathField.setFocusable(false);
		add(pathField, c);
		
		c.gridx++;
		c.weightx = 2;
		JButton browseButton = new JButton("Browse");
		add(browseButton, c);
		browseButton.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				Analytics.log().write("Browsing", Log.DEBUG);
				int code = fileSelector.showOpenDialog(ContentArea.this);
				if(code == JFileChooser.APPROVE_OPTION){
					gameDir = fileSelector.getSelectedFile();
					Analytics.log().write("Selected file " + gameDir, Log.DEBUG);
					pathField.setText(gameDir.toString());
				}else{
					Analytics.log().write("Browsing canceled", Log.DEBUG);
				}
			}
		});
		
		c.gridx++;
		c.weightx = 0.5;
		JButton loadButton = new JButton("Load Data");
		add(loadButton, c);
		loadButton.addMouseListener(new MouseAdapter(){
			public void mouseReleased(MouseEvent e){
				Analytics.log().write("Loading data", Log.DEBUG);
				if(gameDir == null){
					Analytics.log().write("No game dir selected", Log.ERROR);
					return;
				}
				File dataDir = new File(gameDir, "data" + File.separatorChar + "analytics");
				if(!dataDir.exists()){
					Analytics.log().write("Unable to load data: analytics data folder " + dataDir + " does not exist", Log.ERROR);
					return;
				}
				StatsView.instance.loadData(dataDir.listFiles((dir, name) -> name.matches("\\d+\\.dat")));
			}
		});
		
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 3;
		c.weightx = 1;
		c.weighty = 24;
		add(StatsView.instance, c);
	}
}

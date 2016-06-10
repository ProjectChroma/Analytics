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
import io.github.projectchroma.analytics.gui.util.BaseComponent;

public class ContentArea extends BaseComponent{
	private static final long serialVersionUID = 1L;
	private JTextField pathField = new JTextField();
	private JFileChooser fileSelector = new JFileChooser(new File("").getAbsoluteFile());
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
					File dir = fileSelector.getSelectedFile();
					Analytics.log().write("Selected file " + dir, Log.DEBUG);
					pathField.setText(dir.toString());
					
					File dataDir = new File(dir, "data" + File.separatorChar + "analytics");
					if(dataDir.exists()){
						Analytics.log().write("File " + dataDir + " found", Log.ERROR);
						StatsArea.instance.loadData(dataDir.listFiles((dir_, name) -> name.matches("\\d+\\.dat")));
					}else if(dir.exists()){//In case they selected they analytics folder itself
						Analytics.log().write("Backup file " + dir + " found", Log.ERROR);
						StatsArea.instance.loadData(dir.listFiles((dir_, name) -> name.matches("\\d+\\.dat")));
					}else{
						Analytics.log().write("Selected file " + dir + " does not exist", Log.ERROR);
					}
				}else{
					Analytics.log().write("Browsing canceled", Log.DEBUG);
				}
			}
		});
		
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 3;
		c.weightx = 1;
		c.weighty = 24;
		add(StatsArea.instance, c);
	}
}

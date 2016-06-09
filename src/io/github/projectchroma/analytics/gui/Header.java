package io.github.projectchroma.analytics.gui;

import java.awt.Color;

import javax.swing.BoxLayout;

import io.github.projectchroma.analytics.Analytics;
import io.github.projectchroma.launcher.gui.util.BaseComponent;
import io.github.projectchroma.launcher.gui.util.CustomTextField;
import io.github.projectchroma.launcher.gui.util.ImageComponent;
import io.github.projectchroma.launcher.gui.util.Spacer;

public class Header extends BaseComponent{
	private static final long serialVersionUID = 1L;
	public Header(){
		super(Color.white, Color.black);
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		add(new ImageComponent(Analytics.getLogo(), 100));
		add(new Spacer(10));
		add(new CustomTextField("Chroma Analytics", Analytics.getFont(48), false, getBackground(), getForeground()));
	}
}

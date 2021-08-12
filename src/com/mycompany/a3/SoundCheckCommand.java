package com.mycompany.a3;

import com.codename1.ui.CheckBox;
import com.codename1.ui.Command;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;

public class SoundCheckCommand extends Command{
	private GameWorld gw;
	private Toolbar tBar;
	
	
	////pass the toolBar from Game class with the GameWorld object
	public SoundCheckCommand(Object g, Toolbar t) {
		super("Game Sound");
		gw = (GameWorld) g;
		tBar = t; 
	}

	
	//checks the checkbox's current status and set the sound option
	//once the checkbox is pressed, the toolbar will close and revalidate itself
	public void actionPerformed(ActionEvent e) {
		if(((CheckBox)e.getComponent()).isSelected()) {
			 gw.setSoundOption(true);
		}
		else { 
			gw.setSoundOption(false);
		}
		
		tBar.closeSideMenu();
		tBar.revalidate();
	}
}

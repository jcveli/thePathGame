package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class DirectionCommand extends Command {
	private GameWorld gw;
	private String command;
	
	
	//pass the cmd string to the command parent class and save the cmd string in this class
	public DirectionCommand(Object g, String cmd) {
		super(cmd);
		command = cmd;
		gw = (GameWorld) g; 
	}

	
	//When  Left or Right command is pressed, pass the direction string to antDirection method 
	public void actionPerformed(ActionEvent ev) {
		gw.antDirection(command);
	}
	
}

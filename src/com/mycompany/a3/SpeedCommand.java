package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;

public class SpeedCommand extends Command {
	private GameWorld gw;
	private String command; 
	
	
	//pass the cmd string to the command parent class and save the cmd string in this class
	public SpeedCommand(Object g, String cmd) {
		super(cmd);
		gw = (GameWorld) g;
		command = cmd; 
	}
	
	
	//When either Accelerate or Break button is pressed, will invoke to the method in GameWorld
	//to change their speed based on the cmd string passed 
	public void actionPerformed(ActionEvent ev) {
		if(command == "Accelerate")
			gw.changeAntSpeed('a');
		else if(command == "Break")
			gw.changeAntSpeed('b');
	}
}

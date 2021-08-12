package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.util.UITimer;

public class ModeCommand extends Command {
	private String currentCommand; 
	private MapView mv; 
	private Game g; 

	
	public ModeCommand(String command, Object game, Container c) {
		super(command);
		currentCommand = command;
		g = (Game) game;
		mv = (MapView) c; 

		// TODO Auto-generated constructor stub
	}
	
	public static String switchCurrentMode(String c) {
		if(c == "Play") 
			return "Pause";
		else
			return "Play";
	}
	
	public void actionPerformed(ActionEvent ev) {
		currentCommand = switchCurrentMode(currentCommand);

		if(currentCommand == "Play") {
			System.out.println("Pausing game...");
			g.pauseGame();
		}else if(currentCommand == "Pause") {
			System.out.println("resuming game...");
			g.resumeGame();
			
		}
		
	}

	

}

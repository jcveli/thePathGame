package com.mycompany.a3;

import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.events.ActionEvent;


public class PositionCommand extends Command {
	private Game game; 
	private GameWorld gw;
	private MapView mv; 

	
	public PositionCommand(String command, Game ga, Container c, Object g) {
		super(command);
		game = (Game) ga;
		mv = (MapView) c;
		gw = (GameWorld) g; 
		// TODO Auto-generated constructor stub
	}
	

	
	public void actionPerformed(ActionEvent ev) {
		if(game.getMode() == false) {
			mv.setPosMode(true);
			System.out.println("position mode has been invoked. Press the screen again"
					+ " for the new location.");
			IIterator tempIter = gw.getIterator();
			while(tempIter.hasNext()) {
				GameObject temp = (GameObject) tempIter.getNext(); 
				if(temp.isSelected()) {
					System.out.println(temp.getClass() + " has been selected");

				}
			}
		}else {
			System.out.println("The game must be paused in-order for Position mode to be used");
			return;
		}
	}

}

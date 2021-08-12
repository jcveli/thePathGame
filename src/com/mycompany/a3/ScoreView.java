package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Label;


public class ScoreView extends Container implements Observer {
	private GameWorld gw; 
	private Ant ant;


	@Override
	public void update(Observable observable, Object data) {
		// get GameWorld object and ant instance to access their values
		gw = (GameWorld) data;
		ant = Ant.getAnt();
		
		
		Label timeLabel = new Label("Time: " + gw.getGameTick());
		Label livesLabel = new Label(" Lives Left: " + gw.getLifeCount());
		Label lastFlagLabel = new Label(" Last Flag Reached: " + ant.getLastFlagReached());
		Label healthLabel = new Label(" Health Level: " + ant.getHealthLevel());
		Label soundLabel = new Label(" Sound: " + soundOptionString());
		
		
		//set text color to blue for all labels in ScoreView
		timeLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		livesLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		lastFlagLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		healthLabel.getAllStyles().setFgColor(ColorUtil.BLUE);
		soundLabel.getAllStyles().setFgColor(ColorUtil.BLUE);


		
		//remove current labels, add them back with their new values and revalidate
		this.removeAll();
		this.add(timeLabel);
		this.add(livesLabel);
		this.add(lastFlagLabel);
		this.add(healthLabel);
		this.add(soundLabel);
		this.revalidate();
	}
		
	
	//return the current sound option; true for ON and false for OFF (unchecked) 
	public String soundOptionString() {
		boolean temp = gw.getSoundOption();
		if(temp)
			return "ON";
		else
			return "OFF";
	}

}

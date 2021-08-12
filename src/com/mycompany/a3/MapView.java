package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.models.Point;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.geom.*;

public class MapView extends Container implements Observer{
	private GameWorld gw;  
	private String className; 
	private Graphics myGraphics;
	private boolean gameMode = true; 
	private boolean posMode = false; 
	private boolean selectMade = false; 
	private int iPx = 0; 
	private int iPy = 0; 

	public void paint(Graphics g) {
		super.paint(g);
		myGraphics = g; 
		Point p = new Point(getX(),getY());
		IIterator tempIterator = gw.getIterator();
		while(tempIterator.hasNext()) {
			GameObject temp = (GameObject) tempIterator.getNext();
			myGraphics.setColor(temp.getColor());
			if(temp instanceof Movable) { 
				temp.draw(myGraphics, p);
			} else if(temp instanceof Fixed) {
				temp.draw(myGraphics, p);
			}
		}
	}
	
	public void pointerPressed(int x, int y) {
		//if a selection has already been made, move the selected obj
		if(this.getPosMode() == true) {
			if(this.isSelectMade() == true) {
				System.out.println("Setting new location...");
				IIterator tempIter = gw.getIterator();
				x = x - getParent().getAbsoluteX();
				y = y - getParent().getAbsoluteY();
				
				iPx = x; 
				iPy = y; 
				
				while(tempIter.hasNext()) {
					GameObject temp = (GameObject) tempIter.getNext();
					if(temp.isSelected()) {
						
						temp.setLocation(x, y);
						System.out.println("New location at (" + iPx + "," + iPy + ")");
						repaint();
						temp.setSelected(false);
						this.setSelectMade(false);
						this.setPosMode(false);
						return; 
					}
				}
			}
			
		}
		else if(this.getPosMode() == false && this.isSelectMade() == false && this.getGameMode() == false){
			System.out.println("Object Selected");
			x = x - getParent().getAbsoluteX();
			y = y - getParent().getAbsoluteY();
			
			Point pPtrRelPrnt = new Point(x, y);
			Point pCmpRelPrnt = new Point(getX(), getY()); 
			IIterator tempIter2 = gw.getIterator();
			while(tempIter2.hasNext()) {
				GameObject temp = (GameObject) tempIter2.getNext();
				if(temp.contains(pPtrRelPrnt, pCmpRelPrnt)){
					temp.setSelected(true);
					System.out.println(temp.getClass() + " has been selected");
					this.setSelectMade(true);
				}else {
					temp.setSelected(false);
				}
			}
			return;
		}	
		//if position mode isn't on, cannot allow user to change 
		else if(this.getGameMode() == true) {
			System.out.println("Can only use this mode when the game is in pause.");
			return;
		}
		
	}
		
	
	@Override
	public void update(Observable observable, Object data) {
		gw = (GameWorld) data; 
		//create an iterator and loop through all the objects in the collection
		//and also outputting its values by calling their toString() method
		repaint();
		IIterator tempIterator = gw.getIterator();
		if(gw.getMinCount() % 50 == 0) {
			while(tempIterator.hasNext()) {
				GameObject temp = (GameObject) tempIterator.getNext();
				if(temp instanceof Ant)  
					 className = "Ant"; 			
				else if(temp instanceof Spider) 
					className = "Spider";
				else if(temp instanceof Flag)
					className = "Flag";
				else if(temp instanceof FoodStation)
					className = "Food Station";
				System.out.println(className + ": " + temp.toString());	
			}
		}
	}
	
	

	public boolean getPosMode() {
		return this.posMode;
	}
	
	public void setPosMode(boolean b) {
		this.posMode = b; 
	}
	
	public boolean isSelectMade() {
		return this.selectMade;
	}
	
	public void setSelectMade(boolean b) {
		this.selectMade = b; 
	}
	
	public void setGameMode(boolean b) {
		this.gameMode = b;
	}
	
	public boolean getGameMode() {
		return this.gameMode;
	}
	
}

package com.mycompany.a3;

import com.codename1.charts.models.*;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
public abstract class GameObject implements IDrawable, ICollider, ISelectable{
	//Game Object attributes; ALL objects have these fields 
	private int size, color; 
	private Point location; 
	private boolean isSelected; 

	
	//Constructor for GameObjects with no parameters passed 
	public GameObject() { 
		this.size = 1; 
		this.location = new Point(0,0); 
		this.color = ColorUtil.rgb(255, 0, 0);
	}

	
	//Constructor for Game objects with location, size and color passed 
	public GameObject(float x, float y, int s, int color) {
		this.location = new Point(x,y);
		this.size = s; 
		this.color = color; 
	}
	
	
	//Constructor for game objects with size and color passed 
	public GameObject(int size, int color) {
		this.size = size; 
		this.color = color; 
	}		
							
		
	/*
	 * String method to return attribute vales from the parent class
	 * to the child classes that called to this method 
	 */
	public String toString() {
		String myDesc ="loc= (" + this.getX() + ", " + this.getY() + ")" + " size=" + this.getSize() 
						+ " color= [" + ColorUtil.red(this.color) + "," + ColorUtil.green(this.color)
						+ "," + ColorUtil.blue(this.color) + "]";
		return myDesc; 
	} 
	
	
	public abstract void draw(Graphics g, Point pCmpRelPrnt);
	
	public abstract boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	
	public boolean collideWith(GameObject otherObject) {
		return false; 
	}

	
	/*
	 * Moves the object by calculating its speed and heading
	 * and to declare it's new (Point) location based on the calculations 
	 */
	public void move(int speed, int heading) {
		this.location = new Point(Math.round((this.getX() + getDeltaX(speed,heading))),
				Math.round(this.getY() + getDeltaY(speed,heading)));
	}
	
	
	//Returns deltaY for move(speed, heading) method 
	private float getDeltaY(int speed, int heading) {
		return (float) (Math.sin(Math.toRadians(90 - heading))) * speed;	
	}


	//Returns deltaX for move(speed, heading) method 
	private float getDeltaX(int speed, int heading) {	
		return (float) (Math.cos(Math.toRadians(90 - heading))) * speed;	
	}

	
	//Sets the object's location if x and y are passed 
	public void setLocation(float locX, float locY) {
		this.location = new Point(locX, locY);
	}

	
	//Set the color for the object with the r,g, and b values passed 
	public void setColor(int r, int g, int b) {
			this.color = ColorUtil.rgb(r,g,b);
		}
		
	@Override
	public void setSelected(boolean b) {
		// TODO Auto-generated method stub
		isSelected = b; 
	}



	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return isSelected;
	}

	//Setter and getter methods to access GameObject's attributes 
	public int getSize() {	return this.size; }
	
	public int getColor() { return this.color; } 

	public float getX() {	return this.location.getX();	}
	
	
	public float getY() {	return this.location.getY();	}






}

package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class Spider extends Movable{
	
	/*
	 * Spider constructor (when no parameters are passed) 
	 */
	public Spider() {
		//super(speed, heading, size, locX, locY, color) 
		super(getRandomInt(15,25), getRandomInt(0,359), 100, getRandomInt(0,1000), 
				getRandomInt(0,1000), ColorUtil.rgb(0, 0, 0)); 
	}
	
	
	
	public static int getRandomInt(int i, int j) {
		 if (i >= j) {
	            throw new IllegalArgumentException("max must be greater than min");
	        }
		Random r = new Random();
		return r.nextInt((j - i) + 1) + i;
	}
	
	
	
	public void move(int n) { 
		super.move(n);
	}
	
	/*
	//Method used in an event that the object will move out of bounds of the declared game world
	public void setLocation(float locX, float locY) {
		super.setLocation(locX,locY);
	}*/
	
	
	//Spider is not allowed to change colors 
	public void setColor() {}
	
}

package com.mycompany.a3;

import java.util.Random;

import com.codename1.charts.util.ColorUtil;

public class FoodStation extends Fixed {
	//food station's unique attribute, capacity 
	private int capacity; 
	
	
	//food station constructor 
	public FoodStation(){
		super(getRandomInt(200,1000),getRandomInt(200,1000), getRandomInt(10,20),ColorUtil.rgb(0, 153, 0));
		this.capacity = this.getSize();
	}

	
	//food station constructor with given size 
	public FoodStation(int size){
		super(getRandomInt(0,900),getRandomInt(0,900),size,ColorUtil.rgb(0, 255, 0));
		this.capacity = size; 
	}
	
	
	/*
	 * Creates string for Object's unique attributes as-well
	 * as other attributes from parent class
	 */
	public String toString() {
		String parentDesc = super.toString();
		String childDesc = " capacity=" + this.getCapacity() ;
		return parentDesc + childDesc; 
	}


	
	public static int getRandomInt(int i, int j) {
		 if (i >= j) {
	            throw new IllegalArgumentException("max must be greater than min");
	        }
		Random r = new Random();
		return r.nextInt((j - i) + 1) + i;
	}
	
	
	//Setters and Getters to access the class' attribute(s)
	
	public int getCapacity() {	
		return this.capacity; 
	} 
	
	
	
	public void setCapacity(int capacity) {
		this.capacity = capacity; 
	}
	
	
	
}

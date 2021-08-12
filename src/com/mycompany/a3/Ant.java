package com.mycompany.a3;


import com.codename1.charts.util.ColorUtil; 
import java.util.Random;


public class Ant extends Movable implements ISteerable{
	//Unique attributes for ant object 
	private int maximumSpeed, foodLevel, foodConsumptionRate, healthLevel, lastFlagReached;
	private static Ant theAnt;	//single instance of an Ant 
	

	
	//Set constructor to private to make sure outside classes cannot initialize more than one Ant
	private Ant() {
		super(20, getRandomInt(0,359), 120, (float)0.0, (float)0.0, ColorUtil.rgb(255, 0, 0)); 
		this.maximumSpeed = 25;
		this.foodLevel = 50; 
		this.foodConsumptionRate = 1; 
		this.healthLevel = 10; 
		this.lastFlagReached = 1; 
	}
	
	
	
	/*
	 * To make sure there is only one way for an outside class to get an ant  
	 * getAnt() will find if there is an instance of an Ant object; there can ONLY be ONE 
	 */
	public static Ant getAnt() {
		if(theAnt == null) {
			System.out.println("There is no ant. Creating an ant....");
			theAnt = new Ant();
		}
		return theAnt;
	}
	
	
	
	//Reset ant's current values to the default when the ant loses it's life or the game is restarted
	public void resetAnt() {
		this.maximumSpeed = 40;
		this.foodLevel = 30; 
		this.foodConsumptionRate = 2; 
		this.healthLevel = 10; 
		this.lastFlagReached = 1; 
		super.setSpeed(34);
	}
	
	
	
	/*
	 * Increases current  speed of the ant by 1 if 
	 * the ant's current speed is NOT greater than the 
	 * maximum speed set or it's health and/or food level is 0
	 */
	public void increaseSpeed() { 
		if(healthLevel == 0 || foodLevel == 0 || super.getSpeed() == maximumSpeed) {
			return;
		}

		
		super.setSpeed(super.getSpeed() + 1); 
	}
	
	
	
	/*
	 * Decreases current speed of the ant by 1 if
	 * the ant's current speed is not less than or equal to 0
	 * or it's food levels and/or health level is not 0 
	 */
	public void decreaseSpeed() { 
		if(this.healthLevel == 0 || this.foodLevel == 0) {
			return;
		}else if(super.getSpeed() < -1 || super.getSpeed()  ==  0 ) {
			increaseSpeed();
			return;
		}
		/*
		 * if it's current speed is greater than the current maximum
		 * speed with taking health level into account, reduce it's speed 
		 * to the current maximum speed 
		 */
		else if((super.getSpeed() - 1)> ((int)(maximumSpeed * ((double) healthLevel / 10)))) {
			super.setSpeed((int)(maximumSpeed * ((double) healthLevel / 10))); 
			return;
		}
		
		
		super.setSpeed(super.getSpeed() - 1); 
	}
	
	
	
	/*
	 * set location of ant with given x,y
	 * calls for the parent class method setLocation() 
	 */
	public void setLocation(float locX, float locY) {
		super.setLocation(locX,locY);
	}
	
	
	
	/*
	 * Move's the ant's location when the game is ticked 
	 * Also deduct's it's food level based on it's consumption rate 
	 */
	public void move(int n) {
		/*
		 * if health or food level is 0, ant cannot move 
		 */
		if(this.healthLevel == 0 || this.foodLevel <= 0) {
			this.foodLevel = 0; 
			System.out.println("Ant's food and/or health levels are 0. The ant cannot move anymore");
			return;
		}
		super.move(n);
	}
	
	
	
	/*
	 * Change heading of the ant based on user's input direction 
	 */
	public void changeHeading(char direction) {
		if(direction == 'r')
			super.setHeading(super.getHeading() + 25); 
		if(direction == 'l')
			super.setHeading(super.getHeading() - 25); 
	}
	

	/*
	 * Method used to call for a random integer between 2 numbers 
	 */
	public static int getRandomInt(int i, int j) {
		 if (i >= j) {
	            throw new IllegalArgumentException("max must be greater than min");
	        }
		Random r = new Random();
		return r.nextInt((j - i) + 1) + i;
	}
	
	
	
	/*
	 * Creates string for Object's unique attributes as-well
	 * as other attributes from parent class
	 */
	public String toString() {
		String parentDesc = super.toString();
		String childDesc = " MaxSpeed=" + this.getMaxSpeed() + " foodConsumptionRate=" + this.getConsumpRate()
						+ " foodLevel=" + this.getFoodLevel();
		return parentDesc + childDesc; 
	} 
	

	
	/*
	 * Getter and setter methods for class' data fields 
	 */
	public int getMaxSpeed() {	return this.maximumSpeed;	}
	
	
	
	public void setMaxSpeed(int s) {	this.maximumSpeed = s;	}	
	
	
	
	public int getFoodLevel() {	return this.foodLevel;	}
	
	
	
	public void setFoodLevel(int foodLevel)	{	this.foodLevel = foodLevel;	}
	
	
	public void deductFoodLevel()	{	this.foodLevel = this.foodLevel - this.foodConsumptionRate;	}
	
	
	public int getConsumpRate() {	return this.foodConsumptionRate;	}
	
	
	
	public void setConsumpRate(int cRate) {}
	
	
	
	public int getHealthLevel() {	return this.healthLevel;	}
	
	
	
	public void setHealthLevel(int health) {	this.healthLevel = health;	} 
	
	
	
	public int getLastFlagReached() {	return this.lastFlagReached;	}
	
	
	
	public void setLastFlagReached(int flagNo) {	this.lastFlagReached = flagNo;	}
}

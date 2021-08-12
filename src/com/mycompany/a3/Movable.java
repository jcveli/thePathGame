package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;

public class Movable extends GameObject{
	//Movable attributes, speed & heading  
	private int speed; 
	private int heading; 
	private Graphics myGraphics; 
	
	/*
	 * Movable constructor; if no parameters are passed, heading and speed are 0 as default 
	 */
	public Movable() {
		this.heading = 0; 
		this.speed = 0; 
	}
	
	
	/*
	 * Constructor with parameters passed (speed, heading, size, x, y, color)
	 * super(location x, location y, size, color)  
	 */
	public Movable(int speed, int heading, int size, float x, float y,int color)  { 
		super(x, y, size, color); 
		this.heading = heading; 
		this.speed = speed; 
	}
	
	
	/*
	 * Constructor with parameters speed, heading, size and color
	 */
	public Movable(int speed, int heading, int size, int color) {
		super(size, color);
		this.heading = heading; 
		this.speed = speed; 
	}
	
	
	
	@Override
	public void draw(Graphics g, Point pCmpRelPrnt) {
		myGraphics = g; 
		int centerX, centerY;
		
		Point screenOrigin = pCmpRelPrnt; 
		int screenX = (int) screenOrigin.getX();
		int screenY = (int) screenOrigin.getY();
		
		Point objPoint = new Point(super.getX(),super.getY());
		int objX = (int) objPoint.getX() + screenX;
		int objY = (int) objPoint.getY() + screenY;
		
		Class c = this.getClass();
		String className = c.getSimpleName();
		
		myGraphics.setColor(this.getColor());
		if(className.equals("Ant")) {

			myGraphics.fillArc(objX, objY, ((Ant)this).getSize(), ((Ant)this).getSize(), 0, 360);
		} else if(className.equals("Spider")) {
			centerX = ((objX + 150) + objX)/2;
			centerY = objY + 150;
			int xPoints[] = {objX, objX + 150, centerX};
			int yPoints[] = {objY, objY,centerY};
			myGraphics.drawPolygon( xPoints, yPoints , 3);
	
		}
		
	}
	
	
	
	//method to pass objects location to the parent class (GameObject) 
	public void setLocation(float locX, float locY) {
		super.setLocation(locX, locY);
	}
	
	
	/*
	 * Creates string for Object's unique attributes as-well
	 * as other attributes from parent class
	 */
	public String toString() {
		String parentDesc = super.toString();
		String childDesc = " Heading=" + this.getHeading() + " Speed=" + this.getSpeed();
		return parentDesc + childDesc; 
	} 
	 
	
	//method class to pass the objects speed and heading 
	public void move(int n) {
		super.move( (((n/100) * this.speed)), this.heading);
	}
	
	/*
	 * Setter and getter methods for the attributes, speed and heading  
	 */
	public void setHeading(int h) {
		this.heading = h; 
	}
	
	
	
	public void setSpeed(int s) {
		this.speed = s; 
	}
	
	
	
	public int getSpeed() {
		return this.speed; 
	}
	
	
	
	public int getHeading() {
		return this.heading; 
	}

	@Override
	public boolean collidesWith(GameObject otherObject) {
		// TODO Auto-generated method stub
		GameObject othObj = otherObject;
		boolean result = false;
		
		
		
		int thisCenterX = (int) (this.getX() + (this.getSize()/2));
		int thisCenterY = (int) (this.getY() + (this.getSize()/2));
		int otherCenterX = (int) (othObj.getX() + (this.getSize()/2));
		int otherCenterY = (int) (othObj.getY() + (this.getSize()/2));
		
		int dx = thisCenterX - otherCenterX; 
		int dy = thisCenterY - otherCenterY; 
		int distBetweenCenters = ((dx * dx) + (dy * dy));
		
		int thisRadius = this.getSize()/2;
		int otherRadius = this.getSize()/2;
		int radiiSqr = (thisRadius * thisRadius + 2*thisRadius*otherRadius 
				+	otherRadius*otherRadius);
		
		if(distBetweenCenters <= radiiSqr) {
			result = true; 
		}
		return result;
	}


	@Override
	public void handleCollision(GameObject otherObject) {
		// TODO Auto-generated method stub
		GameObject othObj = otherObject; 
		Ant ant = (Ant) this;
		if(othObj instanceof FoodStation) {
			FoodStation tempStation  = (FoodStation) othObj;
			System.out.println("Ant hit with FoodStation");
			ant.setFoodLevel(ant.getFoodLevel() + tempStation.getCapacity());
			tempStation.setCapacity(0);
			tempStation.setColor(229, 255, 204);
		} 
		else if(othObj instanceof Flag) {
			Flag tempFlag = (Flag) othObj; 
			System.out.println("Ant hit with Flag");
			if(tempFlag.getSeqNumber() - ant.getLastFlagReached() == 1) {
				System.out.println("Flag point marked! Ant's last flag reached: " + ant.getLastFlagReached());
				ant.setLastFlagReached(tempFlag.getSeqNumber());
			}
			else if((tempFlag.getSeqNumber() - ant.getLastFlagReached()) >= 2) {
				System.out.println("You can't mark this flag yet. Ant's flag reached: " + ant.getLastFlagReached());
			}else if(tempFlag.getSeqNumber() < ant.getLastFlagReached() || ant.getLastFlagReached() == ant.getLastFlagReached()){					//if ant already marked this current flag
				System.out.println("You already marked this flag. Ant's flag reached: " + ant.getLastFlagReached());
			}
		}
		else if(othObj instanceof Spider) {
			System.out.println("Ant hit with Spider");
			ant.setHealthLevel(ant.getHealthLevel() - 1);
			ant.decreaseSpeed();
			ant.setMaxSpeed(((int)(ant.getMaxSpeed() * ((double) ant.getHealthLevel() / 10))));
			System.out.println("\nAnt hit by spider! Spider deleted, created anew...");
		}
	}


	@Override
	public void setSelected(boolean b) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public boolean contains(Point pPtrRelPRnt, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		return false;
	}



	
}

package com.mycompany.a3;

import com.codename1.charts.models.Point;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;

public class Fixed extends GameObject {
	private Graphics myGraphics; 
	
	
	public Fixed() {
		super(); 
	}
	
	
	
	public Fixed(float locX, float locY, int size, int color) {
		super(locX, locY, size, color); 
	}
	
	
	
	public String toString() {
		String parentDesc = super.toString();
		return parentDesc; 
	}
	


	public void draw(Graphics g, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		myGraphics = g; 
		int centerX, centerY, midY, statSize; 
		int screenX;
		int screenY;
		
		Point point;
		int objX;
		int objY;
		

		
		GameObject c = (GameObject) this; 
	
	 
		screenX = (int) pCmpRelPrnt.getX();
		screenY = (int) pCmpRelPrnt.getY();
		
		point = new Point(c.getX(),c.getY());
	
		objX = (int) point.getX() + screenX;
		objY = (int) point.getY() + screenY;
		
	
			

			
			
		myGraphics.setColor(this.getColor());
		if(c instanceof Flag) {
			midY = objY + c.getSize();
			centerX = objX + (c.getSize()/2);
			centerY = objY + (c.getSize()/2);
			int xPoints[] = {objX, (objX + c.getSize()), centerX};
			int yPoints[] = {objY, objY, midY};
			myGraphics.fillPolygon( xPoints, yPoints , 3);
			String flagNoString =  String.valueOf(((Flag)c).getSeqNumber());
			myGraphics.setColor(ColorUtil.BLACK);
			myGraphics.drawString(flagNoString, centerX , centerY);
		} 
		else if(c instanceof FoodStation) {
			statSize = ((FoodStation) c).getCapacity() * 8;
			centerX = objX + (statSize/2);
			centerY = objY + (statSize/2);
			myGraphics.fillRect(objX,  objY, statSize, statSize);
				int capacityNo = ((FoodStation) c).getCapacity();
			if(capacityNo != 0) {
				String capacityString =  String.valueOf(((FoodStation) c).getCapacity());
				myGraphics.setColor(ColorUtil.BLACK);
				myGraphics.drawString(capacityString, centerX, centerY);
			}
		}
		
			
	}
	



	@Override
	public boolean collidesWith(GameObject otherObject) {
		// TODO Auto-generated method stub	
		boolean result = false; 
		return result;
	}



	@Override
	public void handleCollision(GameObject otherObject) {
		// TODO Auto-generated method stub

	}


	public void setLocation(float locX, float locY) {
		super.setLocation(locX, locY);
	}
	

	@Override
	public void setSelected(boolean b) {
		// TODO Auto-generated method stub
		super.setSelected(b);
	}



	@Override
	public boolean isSelected() {
		// TODO Auto-generated method stub
		return super.isSelected();
	}



	@Override
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt) {
		// TODO Auto-generated method stub
		int iShapeX = (int) this.getX();
		int iShapeY = (int) this.getY();
		int px = (int) pPtrRelPrnt.getX();
		int py = (int) pPtrRelPrnt.getY();
		int xLoc = (int) (pCmpRelPrnt.getX()+ iShapeX);
		int yLoc = (int) (pCmpRelPrnt.getY()+ iShapeY);
		if ( (px >= xLoc) && (px <= xLoc+this.getSize() ) 
				&& (py >= yLoc) && (py <= yLoc+ this.getSize())) {
			return true; 
		} else {
			return false; 
		}
	}



}

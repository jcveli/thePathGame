package com.mycompany.a3;

public class Flag extends Fixed{
	private int sequenceNumber; 
	
	public Flag(){
		super(); 
		this.sequenceNumber = 1;
	}
	

	public Flag(int seqNo, float locX, float locY, int color) {
		super(locX, locY, 150, color);
		this.sequenceNumber = seqNo; 
	}
	
	public String toString() {
		String parentDesc = super.toString();
		String childDesc = " seqNum=" + this.getSeqNumber();
		return parentDesc + childDesc; 
	} 
	
	
	public int getSeqNumber() {
		return this.sequenceNumber;
	}
	
	public void setColor(int r, int g, int b) {}
	

}

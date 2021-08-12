package com.mycompany.a3;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Random;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Dialog;

public class GameWorld extends Observable {
	private double displayWidth, displayHeight; 
	private int gameTick, mscCount, minCount, lives, flagCount;
	private boolean soundOption; 
	private GameObjectCollection gameCollection; 
	private Ant ant;
	private Sound spiderHit, flagCheer, foodCrunch;
	private BGSound bgSound; 
	private ArrayList<GameObject> collidedObjects;
	
	

	public void init() {
		//initialize the game worlds (initial) settings when starting a new game
		this.lives = 3;
		this.gameTick = 0; 
		this.mscCount = 0; 
		this.minCount = 0;
		this.flagCount = 0; 
		this.soundOption = false; 
		
		collidedObjects = new ArrayList<>();
		/*
		 * create an ant instance (singular) and then pass it to createObjectCollection() method
		 * to add it to the collection along with the rest of the game objects. 
		 */
		ant = Ant.getAnt();
		createObjectCollection(ant);
		setAntLocation();
		
		
		
		setChanged();
		notifyObservers(this);
	}
	
	
	public void createSounds() {
		spiderHit = new Sound("spider_hit.wav");
		flagCheer = new Sound("flag_cheer.wav");
		foodCrunch = new Sound("food_crunch.wav");
		bgSound = new BGSound("Retro_BGM.wav");
	}
	
	/*
	 * Create a collection to store all game objects
	 */
	public void createObjectCollection(Ant a) {
		gameCollection = new GameObjectCollection();
		
		gameCollection.add(ant);
		gameCollection.add(new Spider());
		gameCollection.add(new Spider());
		gameCollection.add(new Flag(1, (float) 100, (float) 200, ColorUtil.rgb(0, 204, 204)));
		gameCollection.add(new Flag(2, (float) 200, (float) 800, ColorUtil.rgb(0, 204, 204)));
		gameCollection.add(new Flag(3, (float) 900, (float) 800, ColorUtil.rgb(0, 204, 204)));
		gameCollection.add(new Flag(4, (float) 700, (float) 300, ColorUtil.rgb(0, 204, 204)));
		gameCollection.add(new FoodStation());
		gameCollection.add(new FoodStation());
	}
	
	
	
	/*
	 * ------------------ANT MODIFICATION METHODS---------------------
	 * 
	 * Method to change the ant's speed depending on input passed down 
	 */	
	public void changeAntSpeed(char speed) {
		textBreakLines();
		if (speed == 'a') {
			ant.increaseSpeed();
		} else if (speed == 'b') {
			ant.decreaseSpeed();
		}

		setChanged();
		notifyObservers(this);
	}

	
	//method to change the ant direction with input as parameter
	public void antDirection(String direction) {
		Ant temp = Ant.getAnt();
		textBreakLines();
		if (direction == "Left") {
			temp.changeHeading('l');
			System.out.println("Ant's heading went to the left by 25.");
		} else if (direction == "Right") {
			temp.changeHeading('r');
			System.out.println("Ant's heading went to the right by 25.");
		}
		setChanged();
		notifyObservers(this);
	}

	
	//Method to set the ant's INITIAL location at the first flag
	public void setAntLocation() {	
		IIterator tempList = gameCollection.getIterator();
		while(tempList.hasNext()) {
			GameObject temp = (GameObject) tempList.getNext();
			if(temp instanceof Fixed) {
				if(temp instanceof Flag) {
					Flag tObj = (Flag) temp;
					if(tObj.getSeqNumber() == 1) {
						ant.setLocation(tObj.getX(), tObj.getY());
						System.out.println("Location set at flag 1");
					}
				}
			}
		}
	}


	/*
	 * --------------------CHECK BOUNDS/COLLISION/STATUS METHODS--------------------
	 * 
	 * Checks of objects are within the game's (x,y) grid. If an object's X or y is
	 * above or below 1000 or 0 respectively, Heading will change to turn back right
	 * between 95 to 185 degrees
	 */
	public void checkBounds() {
		IIterator theObjects = gameCollection.getIterator();
		while(theObjects.hasNext() ) {
			GameObject temp = (GameObject) theObjects.getNext();
			if(temp instanceof Spider || temp instanceof Ant) {
				Movable tObj = (Movable) temp;
				if (tObj.getX() >= getWidth() - 100) {
					tObj.setHeading(getRandomInt(170, 355));
				} else if (tObj.getY() >= getHeight() - 150) {
					tObj.setHeading(getRandomInt(91, 269));
				} else if (tObj.getY() <= 200) {
					tObj.setHeading(getRandomInt(80, 90));
				} else if (tObj.getX() <= 200) {
					tObj.setHeading(getRandomInt(160, 170));
				}
			}
		}
		
	}
	




	
	//Method to check the ant's health if it's 0
	public void checkAntHealth() {
		Ant ant = Ant.getAnt();
		//if ant's health is 0, destroy current world objects and restart using createObjects() method
		if (ant.getHealthLevel() == 0) {
			resetWorld(ant);
			this.setLifeCount(1);
			this.checkLifeCount();
		}
	}

		
		//Method to check the life count 
	public void checkLifeCount() {
		//if life count hits 0, program clears objects and sends a Game Over message 
		//if the user wants to restart, game objects will be re-initialized
		if (this.lives == 0) {
			Boolean restartYes = Dialog.show("Game Over", "You failed!\nPress Restart to start a new game", "Restart", null);
			if(restartYes) {
				this.gameTick = 0;
				this.lives = 3;
				resetWorld(ant);
			}
		}
	}
	
	
	
	public void gameFinished() {
		Boolean restartYes = Dialog.show("Victory!", "You won!\nPress Restart to start a new game", "Restart", null);
		if(restartYes) {
			this.gameTick = 0;
			this.lives = 3; 
			resetWorld(ant);
		}
	}


		

	/*
	 * 
	 */
	public void gameTicked(int m) {
		IIterator iter = gameCollection.getIterator();
		Ant ant = Ant.getAnt();
		
		this.mscCount = this.mscCount + m; 
		this.minCount = this.minCount + 1; 
		/*
		 * Move each Movable object in the collection every msc
		 * msc Count * 20 msc 
		 */
		iter = gameCollection.getIterator();
		while(iter.hasNext() ) {
			GameObject temp = (GameObject) iter.getNext();
			if(temp instanceof Movable) {
				((Movable) temp).move(this.mscCount);
			}
		}
		
		
		/*
		 * check collisions 
		 * Have the ant check with each object to see if it has collided
		 * If the ant has collided with the object, call the other object's 
		 * handleCollision method. 
		 */
		iter = gameCollection.getIterator();
		while(iter.hasNext()) {
			GameObject curObj = (GameObject)iter.getNext();
			/*
			 * If ant collides with current object, handle the collision
			 * and pass the current object as a parameter 
			 */
			if(ant.collidesWith(curObj)) {
				boolean found = false; 
				for(int i = 0; i < collidedObjects.size(); i++) {
					GameObject temp = collidedObjects.get(i);
					if(curObj == temp) {
			
						found = true;
						break; 
					}	
				}
				if(found == false) {	
					System.out.println("Collided Object added.");
					collidedObjects.add(curObj);
					ant.handleCollision(curObj);
					if(curObj instanceof FoodStation) {
						playSound("food");
						gameCollection.add(new FoodStation());
					} else if(curObj instanceof Flag) {
						if(((Flag) curObj).getSeqNumber() - ant.getLastFlagReached() == 1) {
							playSound("flag");
							
						}
					}else if(curObj instanceof Spider) {
						playSound("spider");
						gameCollection.remove(curObj);
						gameCollection.add(new Spider());
						checkAntHealth(); 
					}
				}
			}
			
		}
	
		
		if(ant.getLastFlagReached() == countFlags()){
			gameFinished(); 
		}
		//a minute has passed 
		if(this.minCount % 50 == 0) {
			ant.deductFoodLevel();
			gameTick++;		
			checkBounds();
			this.minCount = 0; 
		}
	
		if(this.mscCount % 100 == 0) {
			this.mscCount = 0;
		}
		setChanged();
		notifyObservers(this);
	}
	
	
	
	public void resetWorld(Ant ant) {
		ant.resetAnt();
		gameCollection = null;
		createObjectCollection(ant);
		collidedObjects.clear();
		setAntLocation();
		setChanged();
		notifyObservers(this);
	}
	

	
	//method to return a spider object 
	Spider getSpider() {
		Spider spider = null;
		IIterator theObjects = gameCollection.getIterator();
		while(theObjects.hasNext() ) {
			GameObject temp = (GameObject) theObjects.getNext();
			if(temp instanceof Spider)
				spider = (Spider) temp; 
			else 
				continue; 
		}
		if(spider == null) {
			spider = new Spider(); 
		}
		return spider; 
	}
	
	
	//method to count the number of flags created and return the count 
	public int countFlags() {
		int count = 0; 
		IIterator tempList = gameCollection.getIterator();
		while(tempList.hasNext()) {
			GameObject temp = (GameObject) tempList.getNext();
			if(temp instanceof Flag) {
				count++;
			}
		}

		return count;  
	}

	
	//Method to get a random integer number 
	public int getRandomInt(int i, int j) {
		if (i >= j) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((j - i) + 1) + i;
	}

	
	//Method to return a line of '=' to separate messages easily 
	public void textBreakLines() {
		for (int i = 0; i < 50; i++) {
			System.out.print("=");
		}
		System.out.print("\n");
	}


	
	
	public void playSound(String s) {
		//determine if sound option is on or off
		if(soundOption == true) {
			if(s == "spider") {
				spiderHit.play();
			}else if(s == "flag") {
				flagCheer.play();
			}else if(s == "food") {
				foodCrunch.play();
			}
		}
	}
	
	
	public void playBGSound() {
		if(this.soundOption == true)
			this.bgSound.play();
	}
	
	
	public void pauseBGSound() {
		this.bgSound.pause();
	}
	
	/*
	 * Setters and Getters for GameWorld's unique attributes 
	 */
	public void setDisplayDimension(double h, double w) {
		this.displayHeight = h;
		this.displayWidth = w;
	}
	
	
	public double getHeight() {	return this.displayHeight;	}
	
	
	public double getWidth() {	return this.displayWidth;	}
	
	
	public IIterator getIterator() { return gameCollection.getIterator();	}
	
	
	public int getGameTick() {	return this.gameTick;	}

	public int getMinCount() { return this.minCount;} 
	
	
	public void setLifeCount(int l) {	this.lives = this.lives - l;	}

	
	public int getLifeCount() {	return this.lives;	}
	
	public int getFlagCount() {	return this.flagCount;	}
	
	
	public boolean getSoundOption() {	return this.soundOption;	}
	
	
	public void setSoundOption(boolean b) {
		this.soundOption = b; 
		setChanged();
		notifyObservers(this);		
	}
	
}//end of GameWorld class

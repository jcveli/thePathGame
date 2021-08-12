package com.mycompany.a3;

import java.util.ArrayList;

public class GameObjectCollection implements ICollection{
	private ArrayList<GameObject> objectCollection; 
	
	
	//Constructor
	public GameObjectCollection(){
		objectCollection = new ArrayList<GameObject>();
	}

	
	
	@Override //add Object to the collection 
	public void add(Object newObject) {
		// TODO Auto-generated method stub
		objectCollection.add((GameObject) newObject);
		//System.out.println("Object added to list...");
	}

	
	//Create an iterator
	@Override
	public IIterator getIterator() {
		// TODO Auto-generated method stub
		return new ObjectListIterator();
	}
	
	
	//Remove the object 
	public void remove(Object object) {
		objectCollection.remove((GameObject) object); 
	}
	
	
	//Iterator for the object collection
	private class ObjectListIterator implements IIterator {
		private int currElementIndex; 
		
		
		public ObjectListIterator() {
			currElementIndex = -1; 
		}

		
		
		@Override //check if the next object of the collection exists/not NULL
		public boolean hasNext() {
			// TODO Auto-generated method stub
			if(objectCollection.size() <= 0) return false;
			if(currElementIndex == objectCollection.size() - 1)
				return false;
			return true; 
		}

		
		
		@Override //Returns an object it is pointing to 
		public Object getNext() {
			// TODO Auto-generated method stub
			currElementIndex++;
			return (objectCollection.get(currElementIndex));
		}
	}	//end of private iterator class
}	//end GameObjectCollection class

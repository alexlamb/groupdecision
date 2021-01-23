package original;

import java.util.ArrayList;
import java.util.List;

public class RandomList<T> extends ArrayList<T> {
	
	//CONSTANTS
	private static final long serialVersionUID = 1L;
	
	public RandomList() {
		super();
	}
	
	public RandomList(List<T> list) {
		super(list);
	}
	
	//METHODS
	/**
	 * Selects an element in the list and removes it
	 */
	public T takeRandomElement() {
		int index = (int)(Math.random() * size());
		T result = get(index);
		remove(index);
		return result;
	}

	/**
	 * Selects an element in the list but leaves it there
	 */
	public T selectRandomElement() {
		int index = (int)(Math.random() * size());
		T result = get(index);
		return result;
	}
	
	/**
	 * Performs addition like a Set class. Only unique items are tolerated. 
	 */
	public boolean addElement(T element) {
		boolean result = true;
		if (contains(element)) {
			result = false;
		} else {
			super.add(element);
		}
		return result;
	}


}

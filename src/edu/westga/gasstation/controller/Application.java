/**
 * 
 */
package edu.westga.gasstation.controller;

import edu.westga.gasstation.model.Attendant;
import edu.westga.gasstation.model.Car;
import edu.westga.gasstation.model.Pumps;

/**
 * @author danielburkhart
 *
 */
public class Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Car firstCar = new Car();
		Car secondCar = new Car();
		Car thirdCar = new Car();
		Car fourthCar = new Car();
		
		Pumps pumps = new Pumps(2);
		
		Attendant attendant = new Attendant();
		
	}

}

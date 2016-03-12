package edu.westga.gasstation.controller;

import edu.westga.gasstation.model.Attendant;
import edu.westga.gasstation.model.Car;
import edu.westga.gasstation.model.GasStation;

/**
 * The application class that contains the main method.
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Application {

	/**
	 * Main method
	 * 
	 * @param args
	 *            The args of the method.
	 */
	public static void main(String[] args) {

		GasStation gasStation = new GasStation(2);

		Attendant attendant = new Attendant();

		Car firstCar = new Car(gasStation, attendant);
		Car secondCar = new Car(gasStation, attendant);
		Car thirdCar = new Car(gasStation, attendant);
		Car fourthCar = new Car(gasStation, attendant);

		Thread firstCarThread = new Thread(firstCar);
		Thread secondCarThread = new Thread(secondCar);
		Thread thirdCarThread = new Thread(thirdCar);
		Thread fourthCarThread = new Thread(fourthCar);

		firstCarThread.start();
		secondCarThread.start();
		thirdCarThread.start();
		fourthCarThread.start();

		try {
			Thread.sleep(30 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		firstCar.stop();
		secondCar.stop();
		thirdCar.stop();
		fourthCar.stop();
		gasStation.closeStation();
	}

}

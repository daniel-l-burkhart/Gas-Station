package edu.westga.gasstation.controller;

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

		GasStationController controller = new GasStationController();
		controller.startGasStation();
	}

}

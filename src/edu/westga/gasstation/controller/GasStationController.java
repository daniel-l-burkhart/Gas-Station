package edu.westga.gasstation.controller;

import edu.westga.gasstation.model.Attendant;
import edu.westga.gasstation.model.Car;
import edu.westga.gasstation.model.GasStation;
import edu.westga.gasstation.model.Tank;

/**
 * Controller class for gas station.
 *
 * @author danielburkhart
 * @version Spring 2016
 */
public class GasStationController {

	private GasStation gasStation;
	private Attendant attendant;
	private Car adamCar;
	private Car brianCar;
	private Car jamesCar;
	private Car jonCar;
	private Thread adamThread;
	private Thread brianThread;
	private Thread jamesThread;
	private Thread jonThread;
	private Thread gasStationThread;
	private Tank tank;

	private static final int NUMBER_OF_PUMPS = 2;

	/**
	 * Initializes all the variables
	 */
	public GasStationController() {

		this.tank = new Tank();

		this.attendant = new Attendant(this.tank);
		this.gasStation = new GasStation(NUMBER_OF_PUMPS, this.attendant, this.tank);

		this.adamCar = new Car(this.gasStation, this.attendant, "Adam");
		this.brianCar = new Car(this.gasStation, this.attendant, "Bryan");
		this.jamesCar = new Car(this.gasStation, this.attendant, "James");
		this.jonCar = new Car(this.gasStation, this.attendant, "Jon");

		this.adamThread = new Thread(this.adamCar);
		this.adamThread.setName("Adam");
		this.brianThread = new Thread(this.brianCar);
		this.adamThread.setName("Brian");
		this.jamesThread = new Thread(this.jamesCar);
		this.jamesThread.setName("James");
		this.jonThread = new Thread(this.jonCar);
		this.jonThread.setName("Jon");

	}

	/**
	 * Starts all the threads of the gas station
	 */
	public void startGasStation() {

		System.out.println("Begin");

		this.adamThread.start();
		this.brianThread.start();
		this.jamesThread.start();
		this.jonThread.start();

		try {
			Thread.sleep(15 * 1000);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}

		this.stopGasStation();

	}

	private void stopGasStation() {

		this.adamCar.stop();
		this.brianCar.stop();
		this.jamesCar.stop();
		this.jonCar.stop();

		System.out.println("End");
	}
}

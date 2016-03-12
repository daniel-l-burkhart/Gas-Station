package edu.westga.gasstation.model;

import java.util.ArrayList;

/**
 * The gas station class that controls all the pumps
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class GasStation {

	private ArrayList<Pump> pumps;
	private Tank tank;
	private Thread tankThread;

	/**
	 * Private constructor to ensure use of parameterization
	 */
	private GasStation() {
		this.tank = new Tank();

		this.tankThread = new Thread(this.tank);
		this.pumps = new ArrayList<Pump>();
		this.tankThread.start();
	}

	/**
	 * Constructor that makes a new gas station with number of pumps
	 * 
	 * @param numberOfPumps
	 *            the number of pumps at this gas station.
	 */
	public GasStation(int numberOfPumps) {

		this();

		if (numberOfPumps <= 0) {
			throw new IllegalArgumentException("Cannot have negative pumps");
		}

		for (int i = 0; i < numberOfPumps; i++) {
			this.pumps.add(new Pump(this.tank));
		}

	}

	/**
	 * Checks to see if there are any open pumps
	 * 
	 * @return True if there is an open pump, false otherwise.
	 */
	public boolean areThereAnyOpenPumps() {

		if (findOpenPump() != null) {
			return true;
		}

		return false;
	}

	/**
	 * Finds the open pump, if any exist.
	 * 
	 * @return The open pump if exist.
	 */
	public Pump findOpenPump() {

		Pump openPump = null;

		for (Pump currentPump : this.pumps) {

			if (currentPump.getStatus()) {
				openPump = currentPump;
			}

		}

		return openPump;
	}

	public void closeStation() {
		this.tank.stop();
	}

}

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
	private Attendant attendant;

	/**
	 * Private constructor to ensure use of parameterization
	 */
	private GasStation() {
		this.pumps = new ArrayList<Pump>();
	}

	/**
	 * Constructor that makes a new gas station with number of pumps
	 * 
	 * @param numberOfPumps
	 *            the number of pumps at this gas station.
	 */
	public GasStation(int numberOfPumps, Attendant attendant, Tank tank) {

		this();

		if (attendant == null) {
			throw new IllegalArgumentException("Attendant is null");
		} else if (tank == null) {
			throw new IllegalArgumentException("Tank is null");
		} else if (numberOfPumps <= 0) {
			throw new IllegalArgumentException("Cannot have negative pumps");
		}

		this.attendant = attendant;

		Thread attendantThread = new Thread(attendant);
		attendantThread.start();

		for (int i = 0; i < numberOfPumps; i++) {
			this.pumps.add(new Pump(i, attendant, tank));
		}

	}

	/**
	 * Gets all the pumps
	 * 
	 * @return An arrayList of all the pumps at this station.
	 */
	public ArrayList<Pump> getPumps() {
		return this.pumps;
	}

	/**
	 * Closes the gas station.
	 */
	public void closeGasStation() {
		this.attendant.stop();
	}

}

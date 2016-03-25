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

	private ArrayList<Pump> openPumps;

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

		for (int i = 0; i < numberOfPumps; i++) {
			this.pumps.add(new Pump(i, attendant, tank));
		}

	}

	/**
	 * Checks to see if there are any open pumps
	 * 
	 * @return True if there is an open pump, false otherwise.
	 */
	public synchronized boolean areThereAnyOpenPumps() {

		if (findOpenPumps().size() != 0) {
			return true;
		}

		return false;
	}

	/**
	 * Finds the open pump, if any exist.
	 * 
	 * @return The open pump if exist.
	 */
	public synchronized ArrayList<Pump> findOpenPumps() {

		this.openPumps = new ArrayList<Pump>();

		for (Pump currentPump : this.pumps) {

			if (currentPump.getQueueCount() == 0) {

				this.openPumps.add(currentPump);
			}

		}

		return this.openPumps;

	}

	public ArrayList<Pump> getPumps() {
		return this.pumps;
	}

	public void addCarToQueue(Car car) {

		Pump shortestPump = this.pumps.get(0);

		for (Pump current : this.pumps) {

			if (current.getQueueCount() < shortestPump.getQueueCount()) {
				shortestPump = current;
			}

		}

		shortestPump.addToQueue(car);
	}

	public Pump getOpenPump() {

		Pump openPump;

		if (this.openPumps.size() == 0) {
			return null;
		} else {
			openPump = this.openPumps.get(0);
			this.openPumps.remove(0);
		}

		return openPump;
	}

	public void printPumps() {
		System.out.println("\nPump Status");
		for (Pump currentPump : this.pumps) {

			System.out.println("Pump " + currentPump.getPumpID() + " is open: " + currentPump.getStatus());
			System.out.println("Pump " + currentPump.getPumpID() + " is active: " + currentPump.isPumpActive());
		}
		System.out.println("");
	}
}

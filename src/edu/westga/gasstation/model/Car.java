package edu.westga.gasstation.model;

import java.util.concurrent.ThreadLocalRandom;

import edu.westga.gasstation.controller.GasStationController;

/**
 * The Car class.
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Car implements Runnable {

	private boolean keepWorking;
	private Pump openPump;
	private GasStation gasStation;
	private Attendant attendant;
	private String name;
	private int randomAmount;

	private Car() {
		this.keepWorking = true;
		this.randomAmount = 0;
	}

	/**
	 * Constructor that initializes car object
	 * 
	 * @param gasStation
	 *            The gas station the car is at
	 * @param attendant
	 *            The cashier of the gas station.
	 */
	public Car(GasStation gasStation, Attendant attendant, String name) {

		this();

		if (gasStation == null) {
			throw new IllegalArgumentException("Gas Station is null");
		} else if (attendant == null) {
			throw new IllegalArgumentException("Attendant is null");
		} else if (name == null) {
			throw new IllegalArgumentException("name is null");
		}

		this.gasStation = gasStation;
		this.attendant = attendant;
		this.name = name;

	}

	/**
	 * Run method of this thread.
	 */
	@Override
	public void run() {

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		while (this.keepWorking) {

			this.pullUpToPump();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private synchronized void payCashier() {

		this.attendant.payForGas();
		System.out.println(this.name + " has paid cashier for " + this.randomAmount + " gallon(s) of gas.");

	}

	private synchronized void pullUpToPump() {

		if (this.gasStation.findOpenPumps().size() != 0) {
			this.openPump = this.gasStation.findOpenPumps().get(0);
			this.openPump.claimPump();
			this.pumpGas();
		}
	}

	private synchronized void pumpGas() {

		System.out.println(this.name + " has pulled up to pump " + this.openPump.getPumpID());

		this.randomAmount = ThreadLocalRandom.current().nextInt(1, 3 + 1);

		this.openPump.sendSelectedAmountToAttendant(this.randomAmount);

		if (this.openPump.isPumpActive()) {
			this.openPump.pumpGas(this.randomAmount);

			System.out.println(this.name + " has pumped " + this.randomAmount + " gallon(s) of gas from pump "
					+ this.openPump.getPumpID());

			this.payCashier();
			int pump = this.openPump.leavePump();

			System.out.println(this.name + " has left pump " + pump);

		}

	}

	/**
	 * Stops the thread.
	 */
	public void stop() {
		this.keepWorking = false;
	}

}

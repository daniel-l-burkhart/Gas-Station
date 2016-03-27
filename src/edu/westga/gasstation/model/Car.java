package edu.westga.gasstation.model;

import java.util.concurrent.ThreadLocalRandom;

/**
 * The Car/Customer class.
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Car implements Runnable {

	private boolean keepWorking;
	private GasStation gasStation;
	private Attendant attendant;
	private String name;
	private int randomAmount;

	/**
	 * Private constructor ensuring use of parameterized constructor
	 */
	private Car() {
		this.keepWorking = true;
		this.randomAmount = 0;
	}

	/**
	 * Constructor that initializes car object.
	 *
	 * @param gasStation
	 *            The gas station the car is at
	 * @param attendant
	 *            The cashier of the gas station.
	 * @param name
	 *            the name of the customer
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

		while (this.keepWorking) {

			this.pullUpToPump();

			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private void pullUpToPump() {

		for (Pump currentPump : this.gasStation.getPumps()) {

			if (currentPump.getStatus()) {
				this.claimOpenPump(currentPump);
			}

		}

	}

	private void claimOpenPump(Pump currentPump) {

		Pump openPump;

		if (currentPump.claimPump(this)) {
			openPump = currentPump;
			this.pumpGas(openPump);
			openPump.leavePump();
		}
	}

	private synchronized void pumpGas(Pump openPump) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}

		System.out.println(this.name + " has pulled up to pump " + openPump.getPumpID());

		this.randomAmount = ThreadLocalRandom.current().nextInt(1, 3 + 1);

		openPump.sendSelectedAmountToAttendant(this.randomAmount);

		if (openPump.isPumpActive()) {

			openPump.pumpGas(this.randomAmount);

			System.out.println(this.name + " has pumped " + this.randomAmount + " gallon(s) of gas from pump "
							+ openPump.getPumpID());

			this.payCashier();

			System.out.println(this.name + " has left pump " + openPump.getPumpID());

		}

	}

	private void payCashier() {

		this.attendant.payForGas();
		System.out.println(this.name + " has paid cashier for " + this.randomAmount + " gallon(s) of gas.");

	}

	/**
	 * Stops the thread.
	 */
	public void stop() {
		this.keepWorking = false;
	}

}

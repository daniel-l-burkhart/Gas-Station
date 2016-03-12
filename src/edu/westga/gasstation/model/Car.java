package edu.westga.gasstation.model;

import java.util.concurrent.ThreadLocalRandom;

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

	private Car() {
		this.keepWorking = true;
	}

	/**
	 * Constructor that initializes car object
	 * 
	 * @param gasStation
	 *            The gas station the car is at
	 * @param attendant
	 *            The cashier of the gas station.
	 */
	public Car(GasStation gasStation, Attendant attendant) {

		this();

		if (gasStation == null) {
			throw new IllegalArgumentException("Gas Station is null");
		} else if (attendant == null) {
			throw new IllegalArgumentException("Attendant is null");
		}

		this.gasStation = gasStation;
		this.attendant = attendant;

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

			if (this.checkIfAnyPumpsAreOpen()) {

				this.pumpGas();
				this.payCashier();

			}

		}

	}

	private void payCashier() {

		while (this.attendant.isAttendantBusy()) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		this.notifyAll();
		this.attendant.payForGas(this.openPump);

	}

	private void pumpGas() {

		this.openPump = this.gasStation.findOpenPump();

		int randomAmount = ThreadLocalRandom.current().nextInt(1, 3 + 1);

		this.openPump.pumpGas(randomAmount);

	}

	private boolean checkIfAnyPumpsAreOpen() {
		return this.gasStation.areThereAnyOpenPumps();
	}

	/**
	 * Stops the thread.
	 */
	public void stop() {
		this.keepWorking = false;
	}

}

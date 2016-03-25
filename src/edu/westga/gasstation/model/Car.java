package edu.westga.gasstation.model;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The Car class.
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

			// this.gasStation.printPumps();

			this.pullUpToPump();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

	private synchronized void payCashier(Pump pump) {

		this.attendant.payForGas(pump);
		System.out.println(this.name + " has paid cashier for " + this.randomAmount + " gallon(s) of gas.");

	}

	private synchronized void pullUpToPump() {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Pump openPump = null;

		for (Pump pump : this.gasStation.getPumps()) {
			if (pump.getStatus()) {
				openPump = pump;
			}
		}

		if (openPump == null) {

			openPump = this.gasStation.getPumps()
					.get(ThreadLocalRandom.current().nextInt(0, this.gasStation.getPumps().size()));
		}

		this.pumpGas(openPump);

	}

	private synchronized void pumpGas(Pump openPump) {

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.randomAmount = ThreadLocalRandom.current().nextInt(1, 3 + 1);

		openPump.sendSelectedAmountToAttendant(this.randomAmount);

		if (openPump.isPumpActive()) {

			openPump.pumpGas(this.randomAmount, this.name);

			System.out.println(this.name + " has pumped " + this.randomAmount + " gallon(s) of gas from pump "
					+ openPump.getPumpID());

			this.payCashier(openPump);

			System.out.println(this.name + " has left pump " + openPump.getPumpID());

		}

	}

	/**
	 * Stops the thread.
	 */
	public void stop() {
		this.keepWorking = false;
	}

}

/**
 * 
 */
package edu.westga.gasstation.model;

import java.util.ArrayList;

/**
 * @author danielburkhart
 *
 */
public class Pumps implements Runnable {

	private ArrayList<Pump> pumps;
	private Tank tank;
	private boolean keepWorking;

	private Pumps() {
		this.tank = new Tank();
		this.keepWorking = true;
		this.pumps = new ArrayList<Pump>();
	}

	/**
	 * 
	 */
	public Pumps(int numberOfPumps) {

		this();

		for (int i = 0; i < numberOfPumps; i++) {
			this.pumps.add(new Pump(this.tank));
		}

	}

	@Override
	public void run() {

		while (this.keepWorking) {

		}

	}

	public void stop() {
		this.keepWorking = false;
	}

}

package edu.westga.gasstation.model;

/**
 * The tank class.
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Tank implements Runnable {

	private int amountInTank;
	private boolean keepWorking;

	/**
	 * The tank constructor that initializes variables.
	 */
	public Tank() {

		this.amountInTank = 50;
		this.keepWorking = true;

	}

	/**
	 * Pumps gasoline from the tank.
	 * 
	 * @param amount
	 *            The amount of gasoline being dispensed.
	 */
	public void withdrawGasoline(int amount) {

		if (amount < 0) {
			throw new IllegalArgumentException("Amount cannot be negative");
		}

		if (this.amountInTank - amount < 0) {
			throw new IllegalStateException("Tank is overdrawn. Customer is angry");
		}

		this.amountInTank -= amount;
	}

	/**
	 * The run method of the thread.
	 */
	@Override
	public void run() {

		while (this.keepWorking) {

			if (this.amountInTank < 10) {
				this.amountInTank = 50;
			}
		}

	}

	/**
	 * Stops the thread.
	 */
	public void stop() {
		this.keepWorking = false;
	}

}

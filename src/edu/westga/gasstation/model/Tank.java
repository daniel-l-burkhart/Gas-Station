package edu.westga.gasstation.model;

/**
 * The tank class.
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Tank {

	private int amountInTank;

	/**
	 * The tank constructor that initializes variables.
	 */
	public Tank() {

		this.amountInTank = 50;

	}

	/**
	 * Pumps gasoline from the tank.
	 * 
	 * @param amount
	 *            The amount of gasoline being dispensed.
	 */
	public synchronized void withdrawGasoline(int amount) {

		if (amount < 0) {
			throw new IllegalArgumentException("Amount cannot be negative");
		}

		if ((this.amountInTank - amount) < 0) {
			throw new IllegalStateException("Tank is overdrawn. Customer is angry");
		}

		this.amountInTank -= amount;
	}


	/**
	 * Fills the tank to the appropriate amount
	 * 
	 * @param amountInTank
	 *            The amount in tank
	 */
	public void fillTank() {
		this.amountInTank = 50;
	}

	/**
	 * Gets the amount in the take
	 * 
	 * @return
	 */
	public int getAmount() {

		return this.amountInTank;
	}

}

package edu.westga.gasstation.model;

/**
 * Model class that controls one single pump at the gas station
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Pump {

	private boolean pumpStatus;
	private Tank tank;

	/**
	 * Private constructor ensuring use of parameterized constructor.
	 */
	private Pump() {
		this.pumpStatus = true;
	}

	/**
	 * Constructor that initializes a tank's attributes
	 * 
	 * @param tank
	 *            The tank.
	 */
	public Pump(Tank tank) {

		this();

		if (tank == null) {
			throw new IllegalArgumentException("Tank is null.");
		}

		this.tank = tank;

	}

	/**
	 * Checks to see if pump is open
	 * 
	 * @return True if pump is open false otherwise.
	 */
	public boolean getStatus() {
		return this.pumpStatus;
	}

	/**
	 * Pumps gas from the tank
	 * 
	 * @param amount
	 *            The amount to be withdrawn from the tank.
	 */
	public void pumpGas(int amount) {

		if (amount < 0) {
			throw new IllegalArgumentException("cannot pump negative gas");
		}

		this.tank.withdrawGasoline(amount);

	}

	/**
	 * Called when car pulls up to pump
	 */
	public void pullUpToPump() {
		this.pumpStatus = false;
	}

}

package edu.westga.gasstation.model;

/**
 * Model class that controls one single pump at the gas station
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Pump {

	private boolean pumpStatus;
	private int id;
	private Attendant attendant;
	private boolean pumpActive;
	private Tank tank;

	/**
	 * Private constructor ensuring use of parameterized constructor.
	 */
	private Pump() {
		this.pumpStatus = true;
		this.id = 0;
		this.pumpActive = false;

	}

	/**
	 * Constructor that initializes a tank's attributes
	 * 
	 * @param tank
	 *            The tank.
	 */
	public Pump(int ID, Attendant attendant, Tank tank) {

		this();

		if (attendant == null) {
			throw new IllegalArgumentException("Attendant is null");
		} else if (tank == null) {
			throw new IllegalArgumentException("Tank is null");
		}

		this.attendant = attendant;
		this.tank = tank;
		this.id = ID;

	}

	/**
	 * Checks to see if pump is open
	 * 
	 * @return True if pump is open false otherwise.
	 */
	public synchronized boolean getStatus() {
		return this.pumpStatus;
	}

	/**
	 * Pumps gas from the tank
	 * 
	 * @param amount
	 *            The amount to be withdrawn from the tank.
	 */
	public synchronized void pumpGas(int amount) {

		if (this.pumpActive) {
			if (amount < 0) {
				throw new IllegalArgumentException("cannot pump negative gas");
			}

			this.tank.withdrawGasoline(amount);
		}
	}

	/**
	 * Called when car pulls up to pump
	 */
	public synchronized int pullUpToPump() {

		this.pumpStatus = false;
		return this.id;
	}

	public boolean isPumpActive() {
		return this.pumpActive;
	}

	public synchronized int leavePump() {

		if (this.pumpStatus) {
			throw new IllegalStateException("No one is at pump");
		}

		this.pumpStatus = true;
		this.pumpActive = false;
		return this.id;
	}

	public void sendSelectedAmountToAttendant(int randomAmount) {

		this.attendant.sendSelectedAmount(randomAmount, this);
	}

	public void activatePump(Attendant attendant) {
		this.pumpActive = true;
		System.out.println("Attendant has unlocked pump " + this.id);
	}

	public void claimPump() {

		this.pumpStatus = false;

	}

	public int getPumpID() {
		return this.id;
	}

}

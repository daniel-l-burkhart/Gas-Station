package edu.westga.gasstation.model;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Model class that controls one single pump at the gas station
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Pump {

	private boolean pumpOpenStatus;
	private int id;
	private Attendant attendant;
	private boolean pumpActive;
	private Tank tank;
	private ReentrantLock lock;

	/**
	 * Private constructor ensuring use of parameterized constructor.
	 */
	private Pump() {
		this.pumpOpenStatus = true;
		this.id = 0;
		this.pumpActive = false;
		this.lock = new ReentrantLock(true);

	}

	/**
	 * Constructor that initializes a tank's attributes.
	 *
	 * @param pumpId
	 *            the pump id
	 * @param attendant
	 *            the attendant
	 * @param tank
	 *            The tank.
	 */
	public Pump(int pumpId, Attendant attendant, Tank tank) {

		this();

		if (attendant == null) {
			throw new IllegalArgumentException("Attendant is null");
		} else if (tank == null) {
			throw new IllegalArgumentException("Tank is null");
		}

		this.attendant = attendant;
		this.tank = tank;
		this.id = pumpId;

	}

	/**
	 * Checks to see if pump is open
	 * 
	 * @return True if pump is open false otherwise.
	 */
	public boolean getStatus() {

		return this.pumpOpenStatus;
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

		this.lock.lock();

		if (this.pumpActive) {

			this.tank.withdrawGasoline(amount);
		}

		this.lock.unlock();
	}

	/**
	 * Gets the active status of the pump, set by the attendant
	 * 
	 * @return True if pump is active, false otherwise.
	 */
	public boolean isPumpActive() {
		return this.pumpActive;
	}

	/**
	 * Updates pump's open status after customer leaves.
	 */
	public void leavePump() {

		if (this.pumpOpenStatus) {
			throw new IllegalStateException("No one is at pump");
		}

		this.pumpOpenStatus = true;

	}

	/**
	 * Sends the amount from the customer to the attendant
	 * 
	 * @param amountOfGas
	 *            The amount of gas sent to the attendant through he pump
	 */
	public void sendSelectedAmountToAttendant(int amountOfGas) {

		this.attendant.sendSelectedAmount(amountOfGas, this);
	}

	/**
	 * Activates the pump, allowing the the pump to function.
	 * 
	 * @param attendant
	 *            The attendant that unlocks the pump
	 */
	public void activatePump(Attendant attendant) {
		this.pumpActive = true;
		System.out.println("Attendant has unlocked pump " + this.id);
	}

	/**
	 * Claims the pump
	 * 
	 * @param car
	 *            The car who wants to claim the pump
	 * @return True if pump has been claimed successfully, false otherwise
	 */
	public synchronized boolean claimPump(Car car) {

		if (!this.pumpOpenStatus) {
			return false;
		}

		this.pumpOpenStatus = false;
		return true;

	}

	/**
	 * Returns the id of the pump
	 * 
	 * @return The integer value that identifies each pump uniquely
	 */
	public int getPumpID() {
		return this.id;
	}

	/**
	 * Locks the pump after the paying of a customer.
	 * 
	 * @param attendant
	 *            The attendant who locks the pump
	 */
	public void lockPump(Attendant attendant) {

		if (attendant == null) {
			throw new IllegalArgumentException("Attendant is null");
		}

		this.pumpActive = false;
	}

}

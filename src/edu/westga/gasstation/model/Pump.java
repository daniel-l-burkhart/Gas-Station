package edu.westga.gasstation.model;

import java.util.ArrayList;
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
	private ArrayList<Car> queue;
	private ReentrantLock lock;

	/**
	 * Private constructor ensuring use of parameterized constructor.
	 */
	private Pump() {

		this.lock = new ReentrantLock(true);

		this.pumpOpenStatus = true;
		this.id = 0;
		this.pumpActive = false;
		this.queue = new ArrayList<Car>();

	}

	/*
	 * Reentrant lock(true) fill gas
	 */

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
		return this.lock.hasQueuedThreads();
	}

	/**
	 * Pumps gas from the tank
	 *
	 * @param amount
	 *            The amount to be withdrawn from the tank.
	 * @param name
	 */
	public synchronized void pumpGas(int amount, String name) {

		this.lock.lock();

		System.out.println(name + " has pulled up to pump " + this.id);

		if (this.pumpActive) {
			if (amount < 0) {
				throw new IllegalArgumentException("cannot pump negative gas");
			}

			this.tank.withdrawGasoline(amount);
		}

		this.lock.unlock();
	}

	/**
	 * Called when car pulls up to pump
	 */
	public synchronized int pullUpToPump() {

		this.pumpOpenStatus = false;
		return this.id;
	}

	public boolean isPumpActive() {
		return this.pumpActive;
	}

	public synchronized void leavePump() {

		if (this.pumpOpenStatus) {
			throw new IllegalStateException("No one is at pump");
		}

		this.pumpOpenStatus = true;

	}

	public void sendSelectedAmountToAttendant(int randomAmount) {

		this.attendant.sendSelectedAmount(randomAmount, this);
	}

	public void activatePump(Attendant attendant) {
		this.pumpActive = true;
		System.out.println("Attendant has unlocked pump " + this.id);
	}

	public int getPumpID() {
		return this.id;
	}

	public void lockPump(Attendant attendant) {

		if (attendant == null) {
			throw new IllegalArgumentException("Attendant is null");
		}

		this.pumpActive = false;
	}

	public int getQueueCount() {
		return this.queue.size();
	}

	public void addToQueue(Car car) {
		this.queue.add(car);
	}

}

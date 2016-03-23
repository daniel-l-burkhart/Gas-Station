package edu.westga.gasstation.model;

/**
 * Cashier class.
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Attendant implements Runnable {

	private boolean busy;
	private boolean keepWorking;
	private Tank tank;
	private Pump currentPump;
	private GasStation gasStation;

	/**
	 * Attendant constructor.
	 */
	public Attendant(Tank tank) {

		if (tank == null) {
			throw new IllegalArgumentException("tank is null");
		}

		this.busy = false;
		this.tank = tank;
	}

	/**
	 * Checks if attendant is busy.
	 * 
	 * @return true if attendant is busy, false otherwise.
	 */
	public synchronized boolean isAttendantBusy() {
		return this.busy;
	}

	/**
	 * Pays for gas.
	 */
	public synchronized void payForGas() {

		while (this.busy) {

			try {
				this.wait();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}

		}

		this.notifyAll();
		this.busy = true;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException exception) {
			exception.printStackTrace();
		}

		this.busy = false;

	}

	public void sendSelectedAmount(int randomAmount, Pump pump) {

		pump.activatePump(this);
		this.currentPump = pump;
		System.out.println("Pump " + pump.getPumpID() + " has selected " + randomAmount);
	}

	@Override
	public void run() {

		while (this.keepWorking) {
			if (this.tank.getAmount() <= 10) {
				this.tank.fillTank();
			}

		}

	}

	public void stop() {
		this.keepWorking = false;
	}

	public void dispenseGas(int amount) {

		if (amount < 0) {
			throw new IllegalArgumentException("Amount cannot be negative");
		}

		if (this.tank.getAmount() - amount < 0) {
			throw new IllegalStateException("Tank is overdrawn. Customer is angry");
		}

		this.tank.withdrawGasoline(amount);

	}

}

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

		this.currentPump.lockPump(this);
		this.busy = false;

	}

	/**
	 * Sends selected gas amount from pump to attendant.
	 * 
	 * @param amountOfGas
	 *            The amount of gas selected by user
	 * @param pump
	 *            The pump that is sending the amount of gas.
	 */
	public synchronized void sendSelectedAmount(int amountOfGas, Pump pump) {

		

		if (pump == null) {
			throw new IllegalArgumentException("Pump is null");
		}

		while (this.busy) {
			try {
				this.wait();
			} catch (InterruptedException exception) {
				exception.printStackTrace();
			}
		}

		this.notifyAll();
		this.busy = true;

		this.currentPump = pump;

		System.out.println("Pump " + pump.getPumpID() + " has selected " + amountOfGas + " gallons of gas.");
		this.currentPump.activatePump(this);

		this.busy = false;
	}

	/**
	 * Run method of thread.
	 */
	@Override
	public void run() {

		while (this.keepWorking) {

			if (!this.busy) {

				if (this.tank.getAmount() <= 10) {
					this.tank.fillTank();
				}

			}

		}

	}

	/**
	 * Stop method of thread.
	 */
	public void stop() {
		this.keepWorking = false;
	}

}

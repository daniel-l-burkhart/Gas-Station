package edu.westga.gasstation.model;

/**
 * Cashier class.
 * 
 * @author Daniel Burkhart
 * @version Spring 2016
 */
public class Attendant {

	private boolean busy;

	/**
	 * Attendant constructor.
	 */
	public Attendant() {
		this.busy = false;
	}

	/**
	 * Checks if attendant is busy.
	 * 
	 * @return true if attendant is busy, false otherwise.
	 */
	public boolean isAttendantBusy() {
		return this.busy;
	}

	/**
	 * Pays for gas.
	 * 
	 * @param pump
	 *            The pump that is being used.
	 */
	public void payForGas(Pump pump) {
		this.busy = true;

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

/**
 * 
 */
package edu.westga.gasstation.model;

/**
 * @author danielburkhart
 *
 */
public class Pump {

	private boolean pumpStatus;
	private Tank tank;

	/**
	 * 
	 */
	public Pump(Tank tank) {
		this.pumpStatus = true;
		this.tank = tank;
	}

	public boolean getStatus() {
		return this.pumpStatus;
	}

	public void pumpGas(int amount) {
		this.tank.withdrawGasoline(amount);
	}

	public void pullUpToPump() {
		this.pumpStatus = false;
	}

}

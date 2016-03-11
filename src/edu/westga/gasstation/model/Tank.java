package edu.westga.gasstation.model;

public class Tank implements Runnable {

	private int amountInTank;
	private boolean keepWorking;

	public Tank() {

		this.amountInTank = 50;
		this.keepWorking = true;

	}

	public void withdrawGasoline(int amount) {
		
		if (amount < 0) {
			throw new IllegalArgumentException("Amount cannot be negative");
		}
		
		if(this.amountInTank - amount < 0){
			throw new IllegalStateException("Tank is overdrawn. Customer is angry");
		}

		this.amountInTank -= amount;
	}

	@Override
	public void run() {

		while (this.keepWorking) {
			
			if (this.amountInTank < 10) {
				this.amountInTank = 50;
			}
		}

	}

	public void stop() {
		this.keepWorking = false;
	}

}

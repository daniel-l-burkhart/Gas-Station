package edu.westga.gasstation.model;

public class Car implements Runnable {

	private boolean keepWorking;
	private boolean openPump;

	public Car() {

		this.keepWorking = true;

	}

	@Override
	public void run() {

		while (this.keepWorking) {

			
			if(this.checkIfAnyPumpsAreOpen()){
				
				this.pumpGas();
				this.payCashier();
				
			}
			
		}

	}

	private void payCashier() {
		// TODO Auto-generated method stub
		
	}

	private void pumpGas() {
		// TODO Auto-generated method stub
		
	}

	private boolean checkIfAnyPumpsAreOpen() {
		
		return true;
		
	}

	public void stop() {
		this.keepWorking = false;
	}

}

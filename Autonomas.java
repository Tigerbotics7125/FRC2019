package org.usfirst.frc.team7125.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Autonomas {
	
	private DifferentialDrive chassis;


	public Autonomas() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		
		//if(gameData.charAt(0) == 'L')
		
	}
	public void leftOwnLeftSide(){
		if (timer.get() <= 1.0) {
			chassis.arcadeDrive(-1.0, 0.0); // drive 50% fwd 0% turn
		}
		if (timer.get() > 1.25 && timer.get() < 2.0) {
			chassis.arcadeDrive(0.0,0.65);
		}
		if (timer.get() > 2.25 && timer.get() < 2.45 && !(limitUp.get()) && !(limitDown.get())) {
			armMotor.setSpeed(0.375);
		}
		if (timer.get() > 2.6 && timer.get() < 2.8) {
			pneumatics.reverseSolenoid();
		}
	}
	public void leftOwnMiddle(){
		if (timer.get() <= .75) {
			chassis.arcadeDrive(0.65,0.0);
		}
		if (timer.get() > .75 && timer.get() < 1.25) {
			chassis.arcadeDrive(-1.0, 0.0); // drive 50% fwd 0% turn
		}
		if (timer.get() > 1.35 && timer.get() < 2.1) {
			chassis.arcadeDrive(0.0,0.65);
		}
		if (timer.get() > 2.2 && timer.get() < 3.2) {
			chassis.arcadeDrive(-1.0, 0.0); // drive 50% fwd 0% turn
		}
		if (timer.get() > 3.30 && timer.get() < 3.85) {
			chassis.arcadeDrive(0.0,0.65);
		}
		if (timer.get() > 3.95 && timer.get() < 3.4 && !(limitUp.get()) && !(limitDown.get())) {
			armMotor.setSpeed(0.375);
		}
		if (timer.get() > 3.5 && timer.get() < 3.7) {
			pneumatics.reverseSolenoid();
		}
	}
	public void leftOwnRightSide(){
		if (timer.get() <= 1.0) {
			chassis.arcadeDrive(-1.0, 0.0); // drive 50% fwd 0% turn
		}
		if (timer.get() > 1.25 && timer.get() < 2.0) {
			chassis.arcadeDrive(0.65,0.0);
		}
		if (timer.get() > 2.25 && timer.get() < 2.45 && !(limitUp.get()) && !(limitDown.get())) {
			armMotor.setSpeed(0.375);
		}
		if (timer.get() > 2.6 && timer.get() < 2.8) {
			pneumatics.reverseSolenoid();
		}
	}
	public void rightOwnLeftSide(){
		
	}
	public void rightOwnCenter(){
		
	}
	public void rightOwnRightSide(){
		
	}
	public void noneSelected{
		//ERROR Condition, a Start position was not Selected
		//Attempt to drive straight and pass the switch only
		if (timer.get() <= 1.5) {
			chassis.arcadeDrive(-1.0, 0.0); 
		}
		if (timer.get() > 1.75 && timer.get() < 2.5) {
			chassis.arcadeDrive(0.0,0.65);
		}
	}


}

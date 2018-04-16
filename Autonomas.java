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
	public void leftLeft(){
		//Put left auto code here
		for (int i = 0; i < 4; i++)
		{
			chassis.arcadeDrive(0.5, 0.0); // drive 50% fwd 0% turn
			Timer.delay(3.0); // wait 2 seconds
			chassis.arcadeDrive(0.0, 0.75); // drive 0% fwd, 75% turn
		}
		chassis.arcadeDrive(0.0, 0.0); // drive 0% forward, 0% turn
	}
	public void leftRight(){
		//Put right auto code here
		for (int i = 0; i < 4; i++)
		{
			chassis.arcadeDrive(0.5, 0.0); // drive 50% fwd 0% turn
			Timer.delay(3.0); // wait 2 seconds
			chassis.arcadeDrive(0.0, 0.75); // drive 0% fwd, 75% turn
		}
		chassis.arcadeDrive(0.0, 0.0); // drive 0% forward, 0% turn
	}
	public void middleLeft(){
		//Put left auto code here
				for (int i = 0; i < 4; i++)
				{
					chassis.arcadeDrive(0.5, 0.0); // drive 50% fwd 0% turn
					Timer.delay(3.0); // wait 2 seconds
					chassis.arcadeDrive(0.0, 0.75); // drive 0% fwd, 75% turn
				}
				chassis.arcadeDrive(0.0, 0.0); // drive 0% forward, 0% turn
	}
	public void middleRight(){
		//Put right auto code here
				for (int i = 0; i < 4; i++)
				{
					chassis.arcadeDrive(0.5, 0.0); // drive 50% fwd 0% turn
					Timer.delay(3.0); // wait 2 seconds
					chassis.arcadeDrive(0.0, 0.75); // drive 0% fwd, 75% turn
				}
				chassis.arcadeDrive(0.0, 0.0); // drive 0% forward, 0% turn
	}
	public void rightLeft(){
		//Put left auto code here
				for (int i = 0; i < 4; i++)
				{
					chassis.arcadeDrive(0.5, 0.0); // drive 50% fwd 0% turn
					Timer.delay(3.0); // wait 2 seconds
					chassis.arcadeDrive(0.0, 0.75); // drive 0% fwd, 75% turn
				}
				chassis.arcadeDrive(0.0, 0.0); // drive 0% forward, 0% turn
	}
	public void rightRight(){
		//Put right auto code here
				for (int i = 0; i < 4; i++)
				{
					chassis.arcadeDrive(0.5, 0.0); // drive 50% fwd 0% turn
					Timer.delay(3.0); // wait 2 seconds
					chassis.arcadeDrive(0.0, 0.75); // drive 0% fwd, 75% turn
				}
				chassis.arcadeDrive(0.0, 0.0); // drive 0% forward, 0% turn
	}

}

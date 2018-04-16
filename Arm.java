package org.usfirst.frc.team7125.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm extends IterativeRobot {
	WPI_TalonSRX _armMoter = new WPI_TalonSRX(2);
	private Joystick stick;
	
	public void rotateUp(){
		stick = new Joystick(4);
		if (stick.getRawButton(4)){
			
		}

	}
	public void rotateDown(){
		stick = new Joystick(5);
		if (stick.getRawButton(5)){
			
		}
	}

	public void arm(String[] args) {

		
	}

}

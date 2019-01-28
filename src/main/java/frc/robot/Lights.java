package org.usfirst.frc.team7125.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Lights {
	
	private Joystick stick;
	private DigitalOutput lightsR;
	private DigitalOutput lightsG;
	private DigitalOutput lightsB;
	private double dutyCycleR=0;
	private double dutyCycleG=0;
	private double dutyCycleB=0;
	private int lightColorFlag=0;
	double k = 0.0;
	double x = 0.0;
	
	
	public void lightInit(){
		dutyCycleR=0;
		dutyCycleG=0;
		dutyCycleB=0;
		lightsR = new DigitalOutput(1);
		lightsR.setPWMRate(12000);
		lightsR.enablePWM(0);
		lightsG = new DigitalOutput(2);
		lightsG.setPWMRate(12000);
		lightsG.enablePWM(0);
		lightsB = new DigitalOutput(0);
		lightsB.setPWMRate(12000);
		lightsB.enablePWM(0);
		lightColorFlag=0;
	}//lights are cool
	
	public void lightsPartyMode() {
		x=k%10;
		if (x==0.0)
		{
			double r = Math.random()*.9;
			double g = Math.random()*.9;
			double b = Math.random()*.9;
			dutyCycleR=r;
			dutyCycleG=g;
			dutyCycleB=b;
			lightsR.updateDutyCycle(dutyCycleR);
			lightsG.updateDutyCycle(dutyCycleG);
			lightsB.updateDutyCycle(dutyCycleB);
			lightColorFlag=0;
		}
		k=k+1;
		if (k>100) {
			k=0;
		}
	}
	
	public void lightsSlowDance() {
		lightsR.updateDutyCycle(dutyCycleR);
			lightsG.updateDutyCycle(dutyCycleG);
			lightsB.updateDutyCycle(dutyCycleB);
			if(dutyCycleR>.9) {
				dutyCycleR=0;
				lightColorFlag=1;
			}
			if(dutyCycleG>.9) {
				dutyCycleG=0;
				lightColorFlag=2;
			}
			if(dutyCycleB>.9) {
				dutyCycleB=0;
				lightColorFlag=0;
			}
			
			if (lightColorFlag==0) {
				dutyCycleR+=.1;
			} else if (lightColorFlag==1){
				dutyCycleG+=.1;
			} else if (lightColorFlag==2) {
				dutyCycleB+=.1;
			}
			
	}

	public void lightsRed() {
		dutyCycleR=.9;
		dutyCycleG=0;
		dutyCycleB=0;
		lightsR.updateDutyCycle(dutyCycleR);
		lightsG.updateDutyCycle(dutyCycleG);
		lightsB.updateDutyCycle(dutyCycleB);
		lightColorFlag=0;
	}
	
	public void lightsBlue() {
		dutyCycleR=0;
		dutyCycleG=0;
		dutyCycleB=.9;
		lightsR.updateDutyCycle(dutyCycleR);
		lightsG.updateDutyCycle(dutyCycleG);
		lightsB.updateDutyCycle(dutyCycleB);
		lightColorFlag=2;
	}
	
	public void lightsGreen() {
		dutyCycleR=0;
		dutyCycleG=.9;
		dutyCycleB=0;
		lightsR.updateDutyCycle(dutyCycleR);
		lightsG.updateDutyCycle(dutyCycleG);
		lightsB.updateDutyCycle(dutyCycleB);
		lightColorFlag=1;
	}
	
}

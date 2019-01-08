package org.usfirst.frc.team7125.robot;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Pneumatics {
	//Delete if not being used later for orginization pls

	private boolean enabled;
	private boolean pressureSwitch;
	private double current;
	DoubleSolenoid armSolenoid;
	
	public Pneumatics(){
		
		Compressor c = new Compressor(0);
		armSolenoid = new DoubleSolenoid(2,1);
		
		c.setClosedLoopControl(true);
		//c.setClosedLoopControl(false);
		
		enabled = c.enabled();
		pressureSwitch = c.getPressureSwitchValue();
		current = c.getCompressorCurrent();
		
	}
	public boolean pushOut(){
		return pressureSwitch; 
		
	}
	public void forwardSolenoid() {
		armSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	public void reverseSolenoid() {
		armSolenoid.set(DoubleSolenoid.Value.kReverse);
	}
	public void disableSolenoid() {
		armSolenoid.set(DoubleSolenoid.Value.kOff);
	}
}
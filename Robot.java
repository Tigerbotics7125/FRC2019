/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team7125.robot;
//Trying to save code... delete this later 
// Delete what????????
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;

public class Robot extends IterativeRobot {

	WPI_TalonSRX _leftMotor = new WPI_TalonSRX(0);
	WPI_TalonSRX _rightMotor = new WPI_TalonSRX(1);
	Spark armMotor = new Spark(0);
	private DifferentialDrive chassis;
	private Joystick stick;
	private Joystick control;
	private	boolean driveModeValue;
	private Lights lightEnable;
	DriverStation.Alliance color;
	private boolean partyModeValue;
	private Pneumatics pneumatics;
	private double autonomousStartLocationValue = 0;
	private double loopCount;
	Timer timer = new Timer();
	DigitalInput limitUp = new DigitalInput(3);
	DigitalInput limitDown = new DigitalInput(4);
	
	public void robotInit() { 
		WPI_TalonSRX _leftMotor = new WPI_TalonSRX(0);
		WPI_TalonSRX _rightMotor = new WPI_TalonSRX(1);
		CameraServer.getInstance().startAutomaticCapture();
		// UsbCamera camera = CameraServer.getInstance().startAutomaticCapture("cam0", 0);
		//camera.setResolution(640, 480);
		
		chassis= new DifferentialDrive(_leftMotor,_rightMotor);
		stick= new Joystick(0); 
		control=new Joystick(4);
		driveModeValue = SmartDashboard.getBoolean("DB/Button 1", false);
		SmartDashboard.putString("DB/String 2", "TankMode" + driveModeValue);
		SmartDashboard.putString("DB/String 3", "ArcadeMode" + !driveModeValue);
		autonomousStartLocationValue = SmartDashboard.getNumber("DB/Slider 0", 0.0);
		SmartDashboard.putString("DB/String 4", "StartPosition" + autonomousStartLocationValue);
		lightEnable = new Lights();
		lightEnable.lightInit();
		color = DriverStation.getInstance().getAlliance();
		SmartDashboard.putString("DB/String 0", "Color " + color);
		partyModeValue = SmartDashboard.getBoolean("DB/Button 0", false);
		SmartDashboard.putString("DB/String 1", "PartyMode " + partyModeValue);
		pneumatics = new Pneumatics();
		
	}
	
	public void teleopInit() {
		color = DriverStation.getInstance().getAlliance();
		SmartDashboard.putString("DB/String 0", "Color " + color);
		partyModeValue = SmartDashboard.getBoolean("DB/Button 0", false);
		SmartDashboard.putString("DB/String 1", "PartyMode " + partyModeValue);
		driveModeValue = SmartDashboard.getBoolean("DB/Button 1", false);
		SmartDashboard.putString("DB/String 2", "TankMode " + driveModeValue);
		SmartDashboard.putString("DB/String 3", "ArcadeMode " + !driveModeValue);
		autonomousStartLocationValue = SmartDashboard.getNumber("DB/Slider 0", 0.0);
		SmartDashboard.putString("DB/String 4", "StartPosition " + autonomousStartLocationValue);
	}
	
	public void autonomousInit() {
		color = DriverStation.getInstance().getAlliance();
		SmartDashboard.putString("DB/String 0", "Color " + color);
		partyModeValue = SmartDashboard.getBoolean("DB/Button 0", false);
		SmartDashboard.putString("DB/String 1", "PartyMode " + partyModeValue);
		driveModeValue = SmartDashboard.getBoolean("DB/Button 1", false);
		SmartDashboard.putString("DB/String 2", "TankMode" + driveModeValue);
		SmartDashboard.putString("DB/String 2", "ArcadeMode" + !driveModeValue);
		autonomousStartLocationValue = SmartDashboard.getNumber("DB/Slider 0", 0.0);
		SmartDashboard.putString("DB/String 4", "StartPosition" + autonomousStartLocationValue);
		loopCount = 0.0;
		timer.start();
	}

	public void autonomousPeriodic() {
		    	String gameData;
		SmartDashboard.putString("DB/String 5", "In Autonomous");
		// we really need to fix this....
		//NEED THIS BACK, HARD CODE FOR TESTING
		//gameData = DriverStation.getInstance().getGameSpecificMessage();
		gameData = "LLR";
		SmartDashboard.putString("DB/String 6", "GameData " + gameData.charAt(0));
		
		//Light Modes
		if (partyModeValue == true) {
			lightEnable.lightsPartyMode();
		} else if(color == DriverStation.Alliance.Blue){
			lightEnable.lightsBlue();
		} else if (color == DriverStation.Alliance.Red) {
			lightEnable.lightsRed();
		} else {
			lightEnable.lightsPartyMode();
		}
		
		if(gameData.charAt(0) == 'L')
		{
			//Put left auto code here when switch is on left side of owned (close) switch
			if (autonomousStartLocationValue == 1) {
				// Owned switch left side, starting position left side
				// Robot needs to drive straight, turn right towards switch, drop cube in switch, back up, turn left, drive out of way
				Autonomas.leftOwnLeftSide();
				/*if (timer.get() <= 1.0) {
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
				}*/
			}	
			else if (autonomousStartLocationValue == 2) {
				// Owned switch left side, starting position center
				// Robot needs to drive straight, turn left, drive straight past switch, turn right, drive straight past line, 
				// turn right drive straight towards switch, turn right, drop cube in switch 
				Autonomas.leftOwnMiddle();
				/*if (timer.get() <= .75) {
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
				}*/
			}
			else if (autonomousStartLocationValue == 3) {
				// Owned switch left side, starting position right side
				// Drive straight past switch, turn left, drive to left side of switch, turn left, drop cube
				Autonomas.leftOwnRightSide();
				/*if (timer.get() <= 1.0) {
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
				*/
			}
			
			else {
				//ERROR Condition, a Start position was not Selected
				//Attempt to drive straight and pass the switch only
				if (timer.get() <= 1.5) {
					chassis.arcadeDrive(-1.0, 0.0); // drive 50% fwd 0% turn
				}
				if (timer.get() > 1.75 && timer.get() < 2.5) {
					chassis.arcadeDrive(0.0,0.65);
				}
			}
			
		} else {
			// put code here for owning of switches or game field locations
			}
		}
		SmartDashboard.putString("DB/String 7", "timer" + timer.get());
		*
	} 

	public void teleopPeriodic() {
		//Variable Initialization
		double tankright = stick.getY()*1;
		double tankZ = stick.getZ()*1;
		double tankleft = stick.getRawAxis(3)*1;
		double arcadeleft = stick.getY() *1;
		double arcaderight = stick.getX()*1;
		
		//Light Modes
		if (partyModeValue == true) {
			lightEnable.lightsPartyMode();
		} else if(color == DriverStation.Alliance.Blue){
			lightEnable.lightsBlue();
		} else if (color == DriverStation.Alliance.Red) {
			lightEnable.lightsRed();
		} else {
			lightEnable.lightsPartyMode();
		}
		
		//Drive Modes
		if (driveModeValue) {
			chassis.tankDrive(tankleft,tankright);
		} 
		else {
			chassis.arcadeDrive(arcadeleft, arcaderight);
		}
		
		SmartDashboard.putString("DB/String 5", "arcadeleft " + arcadeleft);
		SmartDashboard.putString("DB/String 6", "arcaderight " + arcaderight);
		SmartDashboard.putString("DB/String 7", "tankleft " + tankleft);
		SmartDashboard.putString("DB/String 8", "tankright " + tankZ);
		SmartDashboard.putString("DB/String 9", "tanktwist " + tankright);
		
		//Arm Control Up and Down
		if(stick.getRawButton(1)/*&& (limitUp.get())*//* && (limitDown.get())*/) {
			armMotor.setSpeed(0.3);
		}
		else if(stick.getRawButton(4)/*&& (limitDown.get())*/ /*&& (limitDown.get())*/) {
			armMotor.setSpeed(-0.375);
		}
		else {
			armMotor.setSpeed(0);
		}
			
		//Pneumatic Control for Arm to Open and Close
		if (stick.getRawButton(2)) {
			pneumatics.forwardSolenoid();
		}
		else if (stick.getRawButton(3)){
			pneumatics.reverseSolenoid();
		}
		else {
			pneumatics.disableSolenoid();
		}

	}

	public void testPeriodic() {


	}
}

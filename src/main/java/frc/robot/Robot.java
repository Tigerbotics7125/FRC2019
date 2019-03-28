/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Timer;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private WPI_VictorSPX leftMotor;
  private WPI_VictorSPX rightMotor;
  private TalonSRX talonOne;
  private TalonSRX talonTwo;
  private DifferentialDrive chassis;
  private Joystick joyCon;
  private Spark intake;
  private Spark hatch;
  private int loop;
  private int skipOffset;
  private int absolutePosition;

  private int resting;
  private int lHatch;
  private int mHatch;
  private int hHatch;
  private int lCargo;
  private int mCargo;
  private int hCargo;
  private int goal;
  private boolean hitBottomFlag;

  private DigitalInput limitUp;
  private DigitalInput limitDown;

  /**
   * This function is run when the robot is first started up and should be used
   * for any initialization code.
   */
  @Override
  public void robotInit() {
    hitBottomFlag = false;
    updatePositions(0);
    goal = resting;
    CameraServer.getInstance().startAutomaticCapture();
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    leftMotor = new WPI_VictorSPX(1);
    rightMotor = new WPI_VictorSPX(2);
    talonOne = new TalonSRX(3);
    talonTwo = new TalonSRX(4);
    talonTwo.set(ControlMode.Follower, talonOne.getDeviceID());
    talonTwo.setInverted(true);
    talonOne.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, Constants.kPIDLoopIdx,
        Constants.kTimeoutMs);
    talonOne.setSensorPhase(Constants.kSensorPhase);
    talonOne.setInverted(Constants.kMotorInvert);
    talonOne.configNominalOutputForward(0, Constants.kTimeoutMs);
    talonOne.configNominalOutputReverse(0, Constants.kTimeoutMs);
    talonOne.configPeakOutputForward(1, Constants.kTimeoutMs);
    talonOne.configPeakOutputReverse(-1, Constants.kTimeoutMs);
    talonOne.config_kF(Constants.kPIDLoopIdx, Constants.kGains.kF, Constants.kTimeoutMs);
    talonOne.config_kP(Constants.kPIDLoopIdx, Constants.kGains.kP, Constants.kTimeoutMs);
    talonOne.config_kI(Constants.kPIDLoopIdx, Constants.kGains.kI, Constants.kTimeoutMs);
    talonOne.config_kD(Constants.kPIDLoopIdx, Constants.kGains.kD, Constants.kTimeoutMs);

    // Grab the 360 degree position of the MagEncoder's Absolute position and set
    // the relative
    // sensor to match
    absolutePosition = talonOne.getSensorCollection().getPulseWidthPosition();
    // Mask out overflows, keep bottom 12 bits
    absolutePosition &= 0xFFF;

    if (Constants.kSensorPhase) {
      absolutePosition *= -1;
    }
    if (Constants.kMotorInvert) {
      absolutePosition *= -1;
    }

    talonOne.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    chassis = new DifferentialDrive(leftMotor, rightMotor);
    joyCon = new Joystick(0);
    intake = new Spark(0);
    hatch = new Spark(1);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString line to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional comparisons to the
   * switch structure below with additional strings. If using the SendableChooser
   * make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    teleopInit();
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    teleopPeriodic();
    switch (m_autoSelected) {
    case kCustomAuto:
      // Put custom auto code here
      break;
    case kDefaultAuto:
    default:
      // Put default auto code here
      break;
    }
  }

  @Override
  public void teleopInit() {
    super.teleopInit();
    // Grab the 360 degree position of the MagEncoder's Absolute position and set
    // the relative
    // sensor to match
    /*
     * absolutePosition = talonOne.getSensorCollection().getPulseWidthPosition();
     * 
     * // Mask out overflows, keep bottom 12 bits absolutePosition &= 0xFFF;
     * 
     * if (Constants.kSensorPhase) { absolutePosition *= -1; } if
     * (Constants.kMotorInvert) { absolutePosition *= -1; }
     */
    limitDown = new DigitalInput(1);
    limitUp = new DigitalInput(0);

    talonOne.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    /* get gamepad stick values */
    int oldGoal = goal;
    double forw = -1 * joyCon.getRawAxis(1); /* positive is forward */
    double turn = -1 * joyCon.getRawAxis(0); /* positive is right */
    double push = +1 * joyCon.getRawAxis(2);
    boolean btn1 = joyCon.getRawButton(1); /* is button is down, print joystick values */
    boolean btn2 = joyCon.getRawButton(2);
    boolean btn3 = joyCon.getRawButton(3);
    boolean btn4 = joyCon.getRawButton(4);
    boolean btn5 = joyCon.getRawButton(5);
    boolean btn8 = joyCon.getRawButton(8);
    boolean btn7 = joyCon.getRawButton(7);
    boolean btn9 = joyCon.getRawButton(9);
    boolean btn10 = joyCon.getRawButton(10);
    boolean btn11 = joyCon.getRawButton(11);
    boolean btn12 = joyCon.getRawButton(12);
    int arm = joyCon.getPOV();

    /* deadband gamepad 10% */
    if (Math.abs(forw) < 0.10) {
      forw = 0;
    }
    if (Math.abs(push) < 0.50) {
      push = 0;
    }
    if (Math.abs(turn) < 0.10) {
      turn = 0;
    }
    /*
     * if (arm == 0&&limitUp.get()){ talonOne.set(ControlMode.PercentOutput, .5); }
     * else if (arm==180&&limitDown.get()){ talonOne.set(ControlMode.PercentOutput,
     * -.25); } /*else { talonOne.set(ControlMode.PercentOutput, 0); }
     * 
     * else
     */
    if (!limitUp.get()) { // if we hit the top
      goal = talonOne.getSelectedSensorPosition() + 100; // go down
    } else if (!limitDown.get()) { // if we hit the bottom
      goal = mCargo; // go to middle cargo
                     // and reset the current position
      talonOne.setSelectedSensorPosition(0, Constants.kPIDLoopIdx, Constants.kTimeoutMs);
    } else if (arm == 0) {
      goal++;
    } else if (arm == 180) {
      goal--;
    } else if (btn8)
      goal = lCargo;// low Cargo
    else if (btn7)
      goal = lHatch;// low Hatch
    else if (btn10)
      goal = mCargo;// middle cargo
    else if (btn9)
      goal = mHatch;// middle hatch
    else if (btn12)
      goal = hCargo;// high cargo
    else if (btn11)
      goal = hHatch;// high hatch;
    else if (btn3)
      goal = 1000;// reset to bottom

    if (oldGoal != goal) // if the goal has changed this loop
      talonOne.set(ControlMode.Position, goal);
    // }

    /* drive robot */
    chassis.arcadeDrive(forw, turn);
    if (btn1)
      intake.set(-.5);
    else if (btn2)
      intake.set(.5);
    else
      intake.set(0);

    if (push > 0)
      hatch.set(-.5);
    else if (push < 0)
      hatch.set(.5);
    else
      hatch.set(0);

  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }

  public void updatePositions(int pos) {
    skipOffset = absolutePosition - pos;
    resting = -1500 + skipOffset;
    lHatch = -3000 + skipOffset;
    mHatch = -5000 + skipOffset;
    hHatch = -6300 + skipOffset;
    lCargo = -4000 + skipOffset;
    mCargo = -2400 + skipOffset;
    hCargo = -2400 + skipOffset;

  }
}

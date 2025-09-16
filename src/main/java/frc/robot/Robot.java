// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.hal.FRCNetComm.tResourceType;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.CANAlgaeSubsystem;
import frc.robot.subsystems.CANAlgaeIntakeSubsystem;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;

import static edu.wpi.first.units.Units.Meters;
import static edu.wpi.first.units.Units.MetersPerSecond;

import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  
  private AddressableLED m_Led;
  private AddressableLEDBuffer m_LedBuffer;
  private final LEDPattern gradient = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kBlue, Color.kDarkOrange);
  private static final Distance kLedSpacing = Meters.of(1 / 120.0);
   private final LEDPattern m_scrollingRainbow =
   gradient.scrollAtAbsoluteSpeed(MetersPerSecond.of(.6), kLedSpacing);


  double deadzone = 0.2;	//variable for amount of deadzone

  private final CANBus kCANBus = new CANBus();

  CANAlgaeIntakeSubsystem intakeSubsystem;
  
  CANAlgaeSubsystem algaeSubsystem;

  public final TalonFX leftLeader = new TalonFX(8, kCANBus);
  public final TalonFX leftFollower = new TalonFX(9, kCANBus);
  public final TalonFX rightLeader = new TalonFX(7, kCANBus);
  public final TalonFX rightFollower = new TalonFX(6, kCANBus);
  

  public final DutyCycleOut leftOut = new DutyCycleOut(0);
  public final DutyCycleOut rightOut = new DutyCycleOut(0);


  public int printCount = 0;

  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private final XboxController joystick = new XboxController(0);

  @Override
  public void robotInit() {

    var leftConfiguration = new TalonFXConfiguration();
    var rightConfiguration = new TalonFXConfiguration();

    /* User can optionally change the configs or leave it alone to perform a factory default */
    leftConfiguration.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
    rightConfiguration.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;

    leftLeader.getConfigurator().apply(leftConfiguration);
    leftFollower.getConfigurator().apply(leftConfiguration);
    rightLeader.getConfigurator().apply(rightConfiguration);
    rightFollower.getConfigurator().apply(rightConfiguration);

    /* Set up followers to follow leaders */
    leftFollower.setControl(new Follower(leftLeader.getDeviceID(), false));
    rightFollower.setControl(new Follower(rightLeader.getDeviceID(), false));
  
    leftLeader.setSafetyEnabled(true);
    rightLeader.setSafetyEnabled(true);


    m_robotContainer = new RobotContainer();

    // Used to track usage of the KitBot code, please do not remove
    HAL.report(tResourceType.kResourceType_Framework, 9);
    
    m_Led = new AddressableLED(5);
    m_LedBuffer = new AddressableLEDBuffer(142);
    m_Led.setLength(m_LedBuffer.getLength());

    m_Led.setData(m_LedBuffer);
    m_Led.start();


  }


  @Override
  public void robotPeriodic() {
    CommandScheduler.getInstance().run();

      m_scrollingRainbow.applyTo(m_LedBuffer);
      m_Led.setData(m_LedBuffer);
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  // private final Timer timer;
  // private final double seconds = 2.0;
  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    
    // timer = new Timer();

  //    double fwd = 0.2; // WAS -0.5 
  //    double rot = 0;
  //   if (fwd >= -deadzone && fwd <= deadzone) {
  //      fwd = 0;
  //   }
  // if (rot >= -deadzone && rot <= deadzone) {
  //   rot = 0;
  //   }

  //  // Timer of 2 seconds
  //   leftOut.Output = (fwd + rot);
  //   rightOut.Output = (fwd - rot);
  //   /* And set them to the motors */
  //   if (!joystick.getAButton()) {
  //     leftLeader.setControl(leftOut);
  //     rightLeader.setControl(rightOut);
  //   }

  }
  // leftOut.Output = fwd + rot;
 // rightOut.Output 
 // 60degree blue ID = 17 
 // 120degree blue ID = 18 
 // 180degree blue ID = 19
 // 240degree blue ID = 20
 // 300degree blue ID = 21
 // 360degree blue ID = 22

 // 60degree red ID = 11


 //if (tid = // whatever april 20 is
 // ) {
  
// }
  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
       /* Get forward and rotational throttle from joystick */

    /* invert the joystick Y because forward Y is negative */
   
    double fwd = joystick.getLeftY(); // -1 and 1, want a deadzone of -0.1 and 0.1 
    double rot = -(joystick.getRightX());
    /* Set output to control frames */
    if (fwd >= -deadzone && fwd <= deadzone) {
      fwd = 0;

  }
  if (rot >= -deadzone && rot <= deadzone) {
    rot = 0;
}
    leftOut.Output = (fwd + rot)*.65;
    rightOut.Output = (fwd - rot)*.65; // WAS .55
    /* And set them to the motors */
    if (!joystick.getAButton()) {
      leftLeader.setControl(leftOut);
      rightLeader.setControl(rightOut);
    }

  }

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
    
  }
  
}

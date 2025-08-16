// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RollerConstants;


/** Class to run the rollers over CAN */
public class CANRollerSubsystem extends SubsystemBase {
  private final Spark rollerMotor;

  public CANRollerSubsystem() {

    // Set up the roller motor as a brushed motor
    rollerMotor = new Spark(RollerConstants.ROLLER_MOTOR_ID); // Change to sparkmax, add "brushless" as a paramater
    
    // Set can timeout. Because this project only sets parameters once on
    // construction, the timeout can be long without blocking robot operation. Code
    // which sets or gets parameters during operation may need a shorter timeout.
    //rollerMotor.setCANTimeout(250);
    

    // Create and apply configuration for roller motor. Voltage compensation helps
    // the roller behave the same as the battery
    // voltage dips. The current limit helps prevent breaker trips or burning out
    // the motor in the event the roller stalls.
    //SparkMaxConfig rollerConfig = new SparkMaxConfig();
    
    //rollerConfig.voltageCompensation(RollerConstants.ROLLER_MOTOR_VOLTAGE_COMP);
    //rollerConfig.smartCurrentLimit(RollerConstants.ROLLER_MOTOR_CURRENT_LIMIT);
    //rollerMotor.configure(rollerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
  }

  /** This is a method that makes the roller spin */
  public void runRoller(double forward, double reverse) {
    rollerMotor.set(forward*0.5 - reverse*0.50);
  }
}

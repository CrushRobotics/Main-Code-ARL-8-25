// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMTalonFX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

// Class to drive the robot over CAN
public class CANDriveSubsystem extends SubsystemBase {
  private final PWMTalonFX leftLeader;
  private final PWMTalonFX leftFollower;
  private final PWMTalonFX rightLeader;
  private final PWMTalonFX rightFollower;

  private final DifferentialDrive drive;

  public CANDriveSubsystem() {
    // create brushed motors for drive
    leftLeader = new PWMTalonFX(DriveConstants.LEFT_LEADER_ID);
    leftFollower = new PWMTalonFX(DriveConstants.LEFT_FOLLOWER_ID);
    rightLeader = new PWMTalonFX(DriveConstants.RIGHT_LEADER_ID);
    rightFollower = new PWMTalonFX(DriveConstants.RIGHT_FOLLOWER_ID);

    // set up differential drive class
    drive = new DifferentialDrive(leftLeader, rightLeader);
    
    leftLeader.addFollower(leftFollower);
    rightLeader.addFollower(rightFollower);


    // Set can timeout. Because this project only sets parameters once on
    // construction, the timeout can be long without blocking robot operation. Code
    // which sets or gets parameters during operation may need a shorter timeout.
    // leftLeader.setCANTimeout(250);
    // rightLeader.setCANTimeout(250);
    // leftFollower.setCANTimeout(250);
    // rightFollower.setCANTimeout(250);

    // Create the configuration to apply to motors. Voltage compensation
    // helps the robot perform more similarly on different
    // battery voltages (at the cost of a little bit of top speed on a fully charged
    // battery). The current limit helps prevent tripping
    // breakers.  
    // config.voltageCompensation(12);
    // config.smartCurrentLimit(DriveConstants.DRIVE_MOTOR_CURRENT_LIMIT);
    
    // Set configuration to follow leader and then apply it to corresponding
    // follower. Resetting in case a new controller is swapped
    // in and persisting in case of a controller reset due to breaker trip
    // config.follow(leftLeader);
    // leftFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // config.follow(rightLeader);
    // rightFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Remove following, then apply config to right leader
    // config.disableFollowerMode();
    // rightLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    // // Set conifg to inverted and then apply to left leader. Set Left side inverted
    // // so that postive values drive both sides forward
    // config.inverted(true);
    // leftLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
  }

  // sets the speed of the drive motors
  public void driveArcade(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed, zRotation*0.50);
  }
}

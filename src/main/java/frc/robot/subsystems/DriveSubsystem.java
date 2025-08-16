// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

// Updated drive class with distance tracking for autonomous
public class DriveSubsystem extends SubsystemBase {
  private final SparkMax leftLeader;
  private final SparkMax leftFollower;
  private final SparkMax rightLeader;
  private final SparkMax rightFollower;

  private final DifferentialDrive drive;
  private final RelativeEncoder leftEncoder;
  private final RelativeEncoder rightEncoder;

  public DriveSubsystem() {
    // create brushless motors for drive
    leftLeader = new SparkMax(DriveConstants.LEFT_LEADER_ID, MotorType.kBrushless);
    leftFollower = new SparkMax(DriveConstants.LEFT_FOLLOWER_ID, MotorType.kBrushless);
    rightLeader = new SparkMax(DriveConstants.RIGHT_LEADER_ID, MotorType.kBrushless);
    rightFollower = new SparkMax(DriveConstants.RIGHT_FOLLOWER_ID, MotorType.kBrushless);

    // Get encoders from the motors
    leftEncoder = leftLeader.getEncoder();
    rightEncoder = rightLeader.getEncoder();

    // set up differential drive class
    drive = new DifferentialDrive(leftLeader, rightLeader);

    // Set can timeout
    leftLeader.setCANTimeout(250);
    rightLeader.setCANTimeout(250);
    leftFollower.setCANTimeout(250);
    rightFollower.setCANTimeout(250);

    // Create motor configuration
    SparkMaxConfig config = new SparkMaxConfig();
    config.voltageCompensation(12);
    config.smartCurrentLimit(DriveConstants.DRIVE_MOTOR_CURRENT_LIMIT);

    // Configure followers
    config.follow(leftLeader);
    leftFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    config.follow(rightLeader);
    rightFollower.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Configure leaders
    config.disableFollowerMode();
    rightLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    config.inverted(true);
    leftLeader.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // Set encoder conversion factors to get distance in meters
    double wheelCircumferenceMeters = Math.PI * DriveConstants.WHEEL_DIAMETER_METERS;
    double distancePerRotation = wheelCircumferenceMeters / DriveConstants.GEAR_RATIO;
    
    leftEncoder.setPositionConversionFactor(distancePerRotation);
    rightEncoder.setPositionConversionFactor(distancePerRotation);
    
    // Reset encoders
    resetEncoders();
  }

  @Override
  public void periodic() {
  }

  // Sets the speed of the drive motors (for teleop)
  public void driveArcade(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed, zRotation * 0.50);
  }

  // Sets the speed of the drive motors (for autonomous - no rotation scaling)
  public void arcadeDrive(double xSpeed, double zRotation) {
    drive.arcadeDrive(xSpeed, zRotation);
  }

  // Stop the drive motors
  public void stop() {
    drive.stopMotor();
  }

  // Reset encoders to zero
  public void resetEncoders() {
    leftEncoder.setPosition(0);
    rightEncoder.setPosition(0);
  }

  // Get average distance traveled in meters
  public double getAverageDistanceMeters() {
    return (leftEncoder.getPosition() + rightEncoder.getPosition()) / 2.0;
  }

  // Get left encoder distance
  public double getLeftDistanceMeters() {
    return leftEncoder.getPosition();
  }

  // Get right encoder distance
  public double getRightDistanceMeters() {
    return rightEncoder.getPosition();
  }
}
// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DriveConstants {
    public static final int LEFT_LEADER_ID = 2;
    public static final int LEFT_FOLLOWER_ID = 1;
    public static final int RIGHT_LEADER_ID = 12;
    public static final int RIGHT_FOLLOWER_ID = 14;

    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
    
    // Wheel diameter and gear ratio for distance calculations
    public static final double WHEEL_DIAMETER_METERS = 0.1524; // 6 inches
    public static final double GEAR_RATIO = 10.71; // Adjust based on your gearbox
  }

  public static final class RollerConstants {
    public static final int ROLLER_MOTOR_ID = 8;
    public static final int ROLLER_MOTOR_CURRENT_LIMIT = 60;
    public static final double ROLLER_MOTOR_VOLTAGE_COMP = 10;
    public static final double ROLLER_EJECT_VALUE = 0.44;
  }

  public static final class OperatorConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
  }

  // Added constants for AprilTag autonomous
  public static final class ArmConstants {
    public static final int ARM_MOTOR_ID = 15; // Change to your actual arm motor ID
    public static final double MIN_BOUND = -45.0; // degrees
    public static final double MAX_BOUND = 90.0;  // degrees
    public static final double SHOOTING_POSITION = 30.0; // degrees
  }

  public static final class ShooterConstants {
    public static final int SHOOTER_MOTOR_ID = 16; // Change to your actual shooter motor ID
    public static final double SHOOTER_SPEED = 0.8;
  }
}
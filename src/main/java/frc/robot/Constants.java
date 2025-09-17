package frc.robot;

public final class Constants {
  public static final class DriveConstants {
    // CHANGE TO KRAKEN X60 MOTOR LIBRARY IMPORTS
    public static final int DRIVE_MOTOR_CURRENT_LIMIT = 60;
    
    // Wheel diameter and gear ratio for distance calculations
    public static final double WHEEL_DIAMETER_METERS = 0.1524; // 6 inches
    public static final double GEAR_RATIO = 10.71; // Adjust based on your gearbox
  }

  public static final class RollerConstants {
    // public static final int ROLLER_MOTOR_ID = 7;
    public static final int ROLLER_MOTOR_CURRENT_LIMIT = 60;
    public static final double ROLLER_MOTOR_VOLTAGE_COMP = 10;
    public static final double ROLLER_EJECT_VALUE = 0.44;
  }

  public static final class ElevatorConstants {
    public static final int ELEVATOR_LEADER_ID = 2;
    public static final int ELEVATOR_FOLLOWER_ID = 4;
  }

  public static final class OperatorConstants {
    public static final int DRIVER_CONTROLLER_PORT = 0;
    public static final int OPERATOR_CONTROLLER_PORT = 1;
  }
<<<<<<< HEAD
  public static final class ArmConstants {
    public static final double MIN_BOUND = -13;
    public static final double MAX_BOUND = -.5;
    public static final double SHOOTING_POSITION = -7.1;
    public static final double SOURCE_POSITION = -3.57;
    public static final int ARM_MOTOR_ID = 15; // Arm motor
    public static final int ARM_GEAR_ID = 14;  // motor to pivot arm 

  }
  public static class ClimberConstants{
    public static final double MIN_BOUND = 0.1;
    public static final double MAX_BOUND = 85;
  }
  public static class PIDConstants{
    public static final double P = 0.1; // Controls how fast movement is 
    public static final double I = 0; // 
    public static final double D = 0; // 
    public static final double G = 9.8; // based on how heavy intake is 
  }
  public static class PIDElevatorConstants{
    public static final double P = 0.15; // Controls how fast movement is 
    public static final double I = 0; // 
    public static final double D = 0; // 
    public static final double G = 23; // based on how heavy intake is 
  }
  
}
=======

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
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.util.Units;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kShooterControllerPort = 1;
  }

  public static class DriveConstants {
    public static final double encoderConversionFactor = (1/5.0) * (Units.inchesToMeters(4) * Math.PI);
  }

  public static class ArmConstants {
    public static final double MIN_BOUND = -13;
    public static final double MAX_BOUND = -.5;
    public static final double SHOOTING_POSITION = -7.1;
    public static final double SOURCE_POSITION = -3.57;
  } 

  public static class ClimberConstants {
    public static final double MIN_BOUND = .1;
    public static final double MAX_BOUND = 85;
  }
}
//april tag constants for limelight vision auto
public static class VisionConstants {
    private static final int BLUE_REEF_TAG_1 = 12;
    private static final int BLUE_REEF_TAG_2 = 13;
    private static final int BLUE_REEF_TAG_3 = 14;
    private static final int BLUE_REEF_TAG_4 = 15;
    private static final int BLUE_REEF_TAG_5 = 16;
    private static final int BLUE_REEF_TAG_6 = 17;
    private static final int BLUE_REEF_TAG_7 = 18;
    private static final int BLUE_REEF_TAG_8 = 19;
    private static final int BLUE_REEF_TAG_9 = 20;
    private static final int BLUE_REEF_TAG_10 = 21;
    private static final int BLUE_REEF_TAG_11 = 22;
    private static final int RED_REEF_TAG_1 = 1;
    private static final int RED_REEF_TAG_2 = 2;
    private static final int RED_REEF_TAG_3 = 3;
    private static final int RED_REEF_TAG_4 = 4;
    private static final int RED_REEF_TAG_5 = 5;
    private static final int RED_REEF_TAG_6 = 6;
    private static final int RED_REEF_TAG_7 = 7;
    private static final int RED_REEF_TAG_8 = 8;
    private static final int RED_REEF_TAG_9 = 9;
    private static final int RED_REEF_TAG_10 = 10;
    private static final int RED_REEF_TAG_11 = 11;
}

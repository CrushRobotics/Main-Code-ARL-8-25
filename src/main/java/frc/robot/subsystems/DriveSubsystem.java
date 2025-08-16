package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;

import com.kauailabs.navx.frc.AHRS;

public class DriveSubsystem extends SubsystemBase {
    CANSparkMax leftLeader;
    CANSparkMax rightLeader;
    CANSparkMax leftFollower;
    CANSparkMax rightFollower;

    RelativeEncoder leftEncoder;
    RelativeEncoder rightEncoder;

    DifferentialDrive diffDrive;
    DifferentialDriveOdometry odometry;
    DifferentialDriveKinematics kinematics;

    AHRS ahrs;

    Rotation2d rotation2d;
    private boolean isDrivingDistance;
    private double driveDistanceTarget;

    public DriveSubsystem() { 
        leftLeader = new CANSparkMax(11, MotorType.kBrushless);
        rightLeader = new CANSparkMax(02, MotorType.kBrushless);

        leftFollower = new CANSparkMax(12, MotorType.kBrushless);
        rightFollower = new CANSparkMax(01, MotorType.kBrushless);

        leftLeader.restoreFactoryDefaults();
        leftFollower.restoreFactoryDefaults();
        rightLeader.restoreFactoryDefaults();
        rightFollower.restoreFactoryDefaults();

        leftLeader.setInverted(false);
        leftFollower.setInverted(false);
        rightLeader.setInverted(true);
        rightFollower.setInverted(true);

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        leftEncoder = leftLeader.getEncoder();
        rightEncoder = rightLeader.getEncoder();
        
        diffDrive = new DifferentialDrive(leftLeader, rightLeader);

        // FIXED: Use the constant from Constants.java instead of hardcoded value
        double conversionFactor = Constants.DriveConstants.encoderConversionFactor;
        leftEncoder.setPositionConversionFactor(conversionFactor);
        rightEncoder.setPositionConversionFactor(conversionFactor);
        
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);

        ahrs = new AHRS();
        
        // ADDED: Call init() to set up odometry
        init();
    } 

    public void init() {    
        rotation2d = new Rotation2d(Units.degreesToRadians(ahrs.getAngle()));

        kinematics =
            new DifferentialDriveKinematics(Units.inchesToMeters(27.0));

        odometry = new DifferentialDriveOdometry(
            rotation2d,
            0, 0,
            new Pose2d(0, 0, new Rotation2d()));
    }

    @Override
    public void periodic() {
        // Update dashboard with encoder measurements
        SmartDashboard.putNumber("Left Drive Encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Drive Encoder", rightEncoder.getPosition());

        // Update dashboard with angle
        SmartDashboard.putNumber("Angle", ahrs.getAngle());
        
        if (isDrivingDistance)
        {
            // Check if we've moved our intended distance
            if (rightEncoder.getPosition() >= driveDistanceTarget)
            {
                diffDrive.arcadeDrive(0, 0);
                isDrivingDistance = false;
            }
        }
    }
    
    public void drive(double leftOutput, double rightOutput) {
        diffDrive.tankDrive(leftOutput, rightOutput);
    }

    public void arcadeDrive(double leftOutput, double rightOutput) {
        diffDrive.arcadeDrive(leftOutput, rightOutput);
    }

    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    public void resetPose() {
        odometry.resetPosition(rotation2d, 0, 0, new Pose2d(0, 0, new Rotation2d()));
    }

    public ChassisSpeeds getCurrentSpeeds() {
        var leftVelocity = leftEncoder.getVelocity();
        var rightVelocity = rightEncoder.getVelocity();

        var wheelSpeeds = new DifferentialDriveWheelSpeeds(leftVelocity, rightVelocity);

        return kinematics.toChassisSpeeds(wheelSpeeds);
    }

    // Distance in meters
    public void driveDistance (double distance)
    {
        isDrivingDistance = true;
        driveDistanceTarget = distance;
        diffDrive.arcadeDrive(distance > 0 ? 0.2 : -0.2, 0);
    }

    // ========== ADDED METHODS FOR APRILTAG AUTONOMOUS ==========
    
    /**
     * Reset both drive encoders to zero
     * Called at start of autonomous
     */
    public void resetEncoders() {
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }
    
    /**
     * Get average distance traveled by both sides
     * Used by autonomous to track how far robot has moved
     */
    public double getAverageDistanceMeters() {
        return (leftEncoder.getPosition() + rightEncoder.getPosition()) / 2.0;
    }
    
    /**
     * Stop all drive motors immediately
     * Used when autonomous command ends
     */
    public void stop() {
        diffDrive.stopMotor();
    }
}
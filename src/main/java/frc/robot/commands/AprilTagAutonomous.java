package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.util.Units;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
// ADDED: Import for alliance detection
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;


public class AprilTagAutonomous {
    
    // FIXED: Limelight configuration - CHANGE THIS TO YOUR ACTUAL LIMELIGHT NAME
    private static final String LIMELIGHT_NAME = "limelight"; // CHANGE TO YOUR LIMELIGHT NAME!
    
    // Reefscape 2025 AprilTag IDs
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
    
    // Control parameters for tuning
    private static final double OPTIMAL_SCORING_DISTANCE = 1.8; // meters from reef
    private static final double POSITION_TOLERANCE = 0.15; // meters
    private static final double ANGLE_TOLERANCE = 5.0; // degrees
    private static final double MAX_DRIVE_SPEED = 0.5;
    private static final double MAX_TURN_SPEED = 0.4;
    
    // Control gains (tune these)
    private static final double DRIVE_KP = 1.0;
    private static final double TURN_KP = 0.02;

    // ADDED: Alliance detection
    public static boolean isBlueAlliance() {
        return DriverStation.getAlliance().orElse(Alliance.Blue) == Alliance.Blue;
    }

    /**
     * Main AprilTag autonomous command
     * This is the complete autonomous routine using AprilTag navigation
     */
    public static Command aprilTagRankingPointAuto(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem, ShooterSubsystem shooterSubsystem) {
        return Commands.sequence(
            // Initialize systems
            Commands.runOnce(() -> {
                SmartDashboard.putString("Auto Status", "Starting AprilTag Auto");
                setupLimelightForAprilTags();
                driveSubsystem.resetEncoders();
            }),
            
            // Phase 1: Leave starting zone (required for ranking point)
            Commands.parallel(
                leaveStartingZone(driveSubsystem),
                Commands.runOnce(() -> SmartDashboard.putString("Auto Status", "Leaving starting zone"))
            ),
            
            // Phase 2: Find and navigate to reef using AprilTags
            Commands.sequence(
                Commands.runOnce(() -> SmartDashboard.putString("Auto Status", "Searching for reef AprilTags")),
                findAndNavigateToReef(driveSubsystem)
            ),
            
            // Phase 3: Position precisely for scoring
            Commands.sequence(
                Commands.runOnce(() -> SmartDashboard.putString("Auto Status", "Positioning for scoring")),
                positionForScoring(driveSubsystem, armSubsystem)
            ),
            
            // Phase 4: Score coral
            Commands.sequence(
                Commands.runOnce(() -> SmartDashboard.putString("Auto Status", "Scoring coral")),
                scoreCoral(shooterSubsystem)
            ),
            
            // Phase 5: Finish
            Commands.runOnce(() -> {
                driveSubsystem.stop();
                SmartDashboard.putString("Auto Status", "AprilTag auto complete");
            })
        );
    }

    /**
     * Leave the starting zone - drives forward until past the line
     */
    private static Command leaveStartingZone(DriveSubsystem driveSubsystem) {
        return Commands.run(() -> {
            // Simple drive forward to cross the line
            driveSubsystem.arcadeDrive(0.4, 0);
            
            // Update dashboard
            SmartDashboard.putNumber("Distance Traveled", driveSubsystem.getAverageDistanceMeters());
        }, driveSubsystem)
        .until(() -> driveSubsystem.getAverageDistanceMeters() > 2.5) // 2.5 meters should clear starting zone
        .withTimeout(4.0)
        .finallyDo(() -> {
            driveSubsystem.stop();
            SmartDashboard.putString("Auto Status", "Starting zone cleared");
        });
    }

    /**
     * IMPROVED: Find reef AprilTags and navigate to scoring position with better error handling
     */
    private static Command findAndNavigateToReef(DriveSubsystem driveSubsystem) {
        return Commands.run(() -> {
            var results = LimelightHelpers.getLatestResults(LIMELIGHT_NAME);
            
            if (results.targetingResults.valid && results.targetingResults.targets_Fiducials.length > 0) {
                var reefTag = findReefAprilTag(results.targetingResults.targets_Fiducials);
                
                if (reefTag != null) {
                    // Get robot's current position from AprilTag localization
                    Pose2d robotPose = getRobotPose();
                    
                    if (robotPose != null) {
                        // Use robot space coordinates for more reliable navigation
                        Pose3d reefPose3d = reefTag.getTargetPose_RobotSpace();
                        double distanceToReef = Math.sqrt(
                            Math.pow(reefPose3d.getX(), 2) + Math.pow(reefPose3d.getY(), 2)
                        );
                        
                        // Simple approach: drive forward if too far, turn if misaligned
                        double angleError = Math.atan2(reefPose3d.getY(), reefPose3d.getX());
                        
                        double driveSpeed = 0;
                        double turnSpeed = 0;
                        
                        // First align with target
                        if (Math.abs(angleError) > Math.toRadians(ANGLE_TOLERANCE)) {
                            turnSpeed = Math.max(-MAX_TURN_SPEED, 
                                       Math.min(MAX_TURN_SPEED, angleError * TURN_KP));
                        }
                        // Then approach target
                        else if (Math.abs(distanceToReef - OPTIMAL_SCORING_DISTANCE) > POSITION_TOLERANCE) {
                            double distanceError = distanceToReef - OPTIMAL_SCORING_DISTANCE;
                            driveSpeed = Math.max(-MAX_DRIVE_SPEED,
                                       Math.min(MAX_DRIVE_SPEED, distanceError * DRIVE_KP));
                        }
                        
                        driveSubsystem.arcadeDrive(driveSpeed, turnSpeed);
                        
                        // Update dashboard
                        SmartDashboard.putNumber("Distance to Reef", distanceToReef);
                        SmartDashboard.putNumber("Angle Error (deg)", Math.toDegrees(angleError));
                        SmartDashboard.putNumber("AprilTag ID", reefTag.fiducialID);
                    } else {
                        // Fallback if pose estimation fails
                        driveSubsystem.arcadeDrive(0, 0.2);
                        SmartDashboard.putString("Navigation Status", "Pose estimation failed, searching");
                    }
                } else {
                    driveSubsystem.arcadeDrive(0, 0.2);
                    SmartDashboard.putString("Navigation Status", "No reef tags found, searching");
                }
            } else {
                driveSubsystem.arcadeDrive(0, 0.3);
                SmartDashboard.putString("Navigation Status", "No AprilTags detected");
            }
            
        }, driveSubsystem)
        .until(() -> {
            // Improved end condition
            var results = LimelightHelpers.getLatestResults(LIMELIGHT_NAME);
            if (!results.targetingResults.valid) return false;
            
            var reefTag = findReefAprilTag(results.targetingResults.targets_Fiducials);
            if (reefTag == null) return false;
            
            Pose3d reefPose3d = reefTag.getTargetPose_RobotSpace();
            double distanceToReef = Math.sqrt(
                Math.pow(reefPose3d.getX(), 2) + Math.pow(reefPose3d.getY(), 2)
            );
            double angleError = Math.atan2(reefPose3d.getY(), reefPose3d.getX());
            
            boolean inPosition = Math.abs(distanceToReef - OPTIMAL_SCORING_DISTANCE) < POSITION_TOLERANCE;
            boolean aligned = Math.abs(angleError) < Math.toRadians(ANGLE_TOLERANCE);
            
            return inPosition && aligned;
        })
        .withTimeout(10.0) // Increased timeout
        .finallyDo(() -> {
            driveSubsystem.stop();
            SmartDashboard.putString("Navigation Status", "Navigation complete");
        });
    }

    /**
     * Fine-tune position and set arm angle for scoring
     */
    private static Command positionForScoring(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem) {
        return Commands.parallel(
            // Fine-tune robot position
            Commands.run(() -> {
                var results = LimelightHelpers.getLatestResults(LIMELIGHT_NAME);
                if (results.targetingResults.valid) {
                    var reefTag = findReefAprilTag(results.targetingResults.targets_Fiducials);
                    if (reefTag != null) {
                        // Make small adjustments to stay aligned
                        double tx = reefTag.tx; // Horizontal offset
                        double turnAdjustment = tx * 0.01; // Small correction
                        turnAdjustment = Math.max(-0.1, Math.min(0.1, turnAdjustment));
                        
                        driveSubsystem.arcadeDrive(0, -turnAdjustment);
                        SmartDashboard.putNumber("TX Offset", tx);
                    }
                } else {
                    driveSubsystem.stop();
                }
            }, driveSubsystem).withTimeout(2.0),
            
            // Set arm angle based on AprilTag data
            Commands.runOnce(() -> {
                var results = LimelightHelpers.getLatestResults(LIMELIGHT_NAME);
                if (results.targetingResults.valid) {
                    var reefTag = findReefAprilTag(results.targetingResults.targets_Fiducials);
                    if (reefTag != null) {
                        // Calculate arm angle based on distance and height
                        Pose3d reefPose = reefTag.getTargetPose_RobotSpace();
                        double distance = Math.sqrt(Math.pow(reefPose.getX(), 2) + Math.pow(reefPose.getY(), 2));
                        double height = reefPose.getZ();
                        
                        double armAngle = calculateOptimalArmAngle(distance, height);
                        
                        // Clamp to safe bounds
                        armAngle = Math.max(frc.robot.Constants.ArmConstants.MIN_BOUND,
                                  Math.min(frc.robot.Constants.ArmConstants.MAX_BOUND, armAngle));
                        
                        armSubsystem.setTarget(armAngle);
                        
                        SmartDashboard.putNumber("Target Distance", distance);
                        SmartDashboard.putNumber("Target Height", height);
                        SmartDashboard.putNumber("Calculated Arm Angle", armAngle);
                    }
                } else {
                    // Fallback to default angle
                    armSubsystem.setTarget(frc.robot.Constants.ArmConstants.SHOOTING_POSITION);
                }
            }, armSubsystem)
        );
    }

    /**
     * Score the coral
     */
    private static Command scoreCoral(ShooterSubsystem shooterSubsystem) {
        return Commands.sequence(
            // Wait for arm to get in position
            Commands.waitSeconds(0.5),
            
            // Run shooter
            new ShootCommand(shooterSubsystem).withTimeout(1.2),
            
            // Stop shooter
            Commands.runOnce(() -> shooterSubsystem.stop(), shooterSubsystem)
        );
    }

    /**
     * Backup autonomous without AprilTags (if vision fails)
     */
    public static Command backupAutoNoVision(DriveSubsystem driveSubsystem, ArmSubsystem armSubsystem, ShooterSubsystem shooterSubsystem) {
        return Commands.sequence(
            Commands.runOnce(() -> SmartDashboard.putString("Auto Status", "Running backup auto (no vision)")),
            
            // Leave starting zone
            Commands.run(() -> driveSubsystem.arcadeDrive(0.4, 0), driveSubsystem)
                .until(() -> driveSubsystem.getAverageDistanceMeters() > 2.5)
                .withTimeout(4.0),
            
            // Stop and prepare to score
            Commands.runOnce(() -> {
                driveSubsystem.stop();
                armSubsystem.setTarget(frc.robot.Constants.ArmConstants.SHOOTING_POSITION);
            }),
            
            // Wait and score
            Commands.waitSeconds(1.0),
            new ShootCommand(shooterSubsystem).withTimeout(1.0),
            Commands.runOnce(() -> shooterSubsystem.stop()),
            
            Commands.runOnce(() -> SmartDashboard.putString("Auto Status", "Backup auto complete"))
        );
    }

    // Helper Methods

    /**
     * Setup Limelight for AprilTag detection
     */
    private static void setupLimelightForAprilTags() {
        LimelightHelpers.setLEDMode_ForceOn(LIMELIGHT_NAME);
        LimelightHelpers.setCameraMode_Processor(LIMELIGHT_NAME);
        LimelightHelpers.setPipelineIndex(LIMELIGHT_NAME, 0); // Use pipeline 0 for AprilTags
        SmartDashboard.putString("Limelight Status", "Configured for AprilTags");
    }

    /**
     * IMPROVED: Find reef AprilTag from detected tags with alliance awareness
     */
    private static LimelightHelpers.LimelightTarget_Fiducial findReefAprilTag(
            LimelightHelpers.LimelightTarget_Fiducial[] detectedTags) {
        
        boolean isBlue = isBlueAlliance();
        
        for (var tag : detectedTags) {
            int tagId = (int) tag.fiducialID;
            
            if (isBlue) {
                // Blue alliance reef tags (12-22)
                if (tagId >= BLUE_REEF_TAG_1 && tagId <= BLUE_REEF_TAG_11) {
                    return tag;
                }
            } else {
                // Red alliance reef tags (1-11)
                if (tagId >= RED_REEF_TAG_1 && tagId <= RED_REEF_TAG_11) {
                    return tag;
                }
            }
        }
        return null; // No reef tag found for our alliance
    }

    /**
     * Calculate optimal arm angle based on distance and height to target
     */
    private static double calculateOptimalArmAngle(double distance, double height) {
        // Simple ballistic calculation for coral trajectory
        // You'll need to tune these constants for your specific robot
        
        // Basic angle calculation based on distance
        double baseAngle = frc.robot.Constants.ArmConstants.SHOOTING_POSITION;
        
        // Adjust based on distance
        if (distance < 1.5) {
            // Close shot - lower angle
            baseAngle += 1.0;
        } else if (distance > 2.5) {
            // Far shot - higher angle  
            baseAngle -= 1.0;
        }
        
        // Adjust based on height difference
        baseAngle += height * 0.5; // Tune this multiplier
        
        return baseAngle;
    }

    /**
     * IMPROVED: Get current robot pose from AprilTags with alliance awareness
     */
    public static Pose2d getRobotPose() {
        var results = LimelightHelpers.getLatestResults(LIMELIGHT_NAME);
        if (results.targetingResults.valid) {
            if (isBlueAlliance()) {
                return results.targetingResults.getBotPose2d_wpiBlue();
            } else {
                return results.targetingResults.getBotPose2d_wpiRed();
            }
        }
        return null;
    }

    /**
     * Check if AprilTags are being detected
     */
    public static boolean areAprilTagsVisible() {
        var results = LimelightHelpers.getLatestResults(LIMELIGHT_NAME);
        return results.targetingResults.valid && 
               results.targetingResults.targets_Fiducials.length > 0;
    }
}
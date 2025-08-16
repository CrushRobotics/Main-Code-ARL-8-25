// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.RollerConstants;
import frc.robot.commands.AprilTagAutonomous;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.BlinkCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.LimeLightCommand;
import frc.robot.commands.RollerCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.subsystems.DriveSubsystem; // Updated import
import frc.robot.subsystems.CANRollerSubsystem;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems
  private final DriveSubsystem driveSubsystem = new DriveSubsystem(); // Updated to new subsystem
  private final CANRollerSubsystem rollerSubsystem = new CANRollerSubsystem();
  private final ArmSubsystem armSubsystem = new ArmSubsystem(); // Added
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem(); // Added

  // The driver's controller
  private final CommandXboxController driverController = new CommandXboxController(
      OperatorConstants.DRIVER_CONTROLLER_PORT);
  
  // Optional operator controller for arm/shooter control
  private final CommandXboxController operatorController = new CommandXboxController(
      OperatorConstants.OPERATOR_CONTROLLER_PORT);

  // The autonomous chooser
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  private final BlinkCommand blinkCommand = new BlinkCommand();
  private final LimeLightCommand LIMELIGHT = new LimeLightCommand();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Set up command bindings
    configureBindings();

    // Set the options to show up in the Dashboard for selecting auto modes
    autoChooser.setDefaultOption("Simple Auto", new AutoCommand(driveSubsystem));
    autoChooser.addOption("AprilTag Auto", 
        AprilTagAutonomous.aprilTagRankingPointAuto(driveSubsystem, armSubsystem, shooterSubsystem));
    autoChooser.addOption("Backup Auto (No Vision)", 
        AprilTagAutonomous.backupAutoNoVision(driveSubsystem, armSubsystem, shooterSubsystem));
    
    // Put the chooser on the dashboard
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }

  /**
   * Use this method to define your trigger->command mappings.
   */
  private void configureBindings() {
    // Set the default command for the drive subsystem to an instance of the
    // DriveCommand with the values provided by the joystick axes on the driver
    // controller. The Y axis of the controller is inverted so that pushing the
    // stick away from you (a negative value) drives the robot forwards (a positive
    // value). Similarly for the X axis where we need to flip the value so the
    // joystick matches the WPILib convention of counter-clockwise positive
    driveSubsystem.setDefaultCommand(new DriveCommand(
        () -> -driverController.getLeftY() *
            (driverController.getHID().getRightBumperButton() ? 1 : 0.5),
        () -> -driverController.getRightX(),
        driveSubsystem));

    // Execute the blink command 
    driverController.b().onTrue(blinkCommand);
    
    // LimeLight command
    driverController.a().whileTrue(LIMELIGHT);

    // Set the default command for the roller subsystem
    rollerSubsystem.setDefaultCommand(new RollerCommand(
        () -> driverController.getRightTriggerAxis(),
        () -> driverController.getLeftTriggerAxis(),
        rollerSubsystem));

    // DRIVER CONTROLLER BINDINGS
    // Manual shoot command (X button)
    driverController.x().whileTrue(new ShootCommand(shooterSubsystem));
    
    // Manual arm control with D-pad (override automatic positioning)
    driverController.povUp().whileTrue(
        armSubsystem.run(() -> armSubsystem.setSpeed(0.3))
    );
    driverController.povDown().whileTrue(
        armSubsystem.run(() -> armSubsystem.setSpeed(-0.3))
    );

    // OPERATOR CONTROLLER BINDINGS (if you want separate arm/shooter control)
    // Preset arm positions
    operatorController.a().onTrue(
        armSubsystem.runOnce(() -> armSubsystem.setTarget(0)) // Home position
    );
    operatorController.b().onTrue(
        armSubsystem.runOnce(() -> armSubsystem.setTarget(Constants.ArmConstants.SHOOTING_POSITION)) // Shooting position
    );
    operatorController.y().onTrue(
        armSubsystem.runOnce(() -> armSubsystem.setTarget(45)) // High position
    );

    // Shooter control
    operatorController.rightBumper().whileTrue(new ShootCommand(shooterSubsystem));
    
    // Manual arm control with operator joystick
    operatorController.leftY().negate().onTrue(
        armSubsystem.run(() -> {
            double speed = -operatorController.getLeftY() * 0.3;
            armSubsystem.setSpeed(speed);
        })
    );
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return autoChooser.getSelected();
  }
}
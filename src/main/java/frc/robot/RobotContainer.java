// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
import frc.robot.Constants.RollerConstants;
import frc.robot.commands.AutoCommand;
import frc.robot.commands.BlinkCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.LimeLightCommand; // Import the file first 
import frc.robot.commands.RollerCommand;
import frc.robot.commands.ElevatorCommand.ElevatorDirection;
import frc.robot.commands.ElevatorCommand;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANRollerSubsystem;
import frc.robot.subsystems.CANElevatorSubsystem;

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
  private final CANDriveSubsystem driveSubsystem = new CANDriveSubsystem();
  private final CANRollerSubsystem rollerSubsystem = new CANRollerSubsystem();
  private final CANElevatorSubsystem elevatorSubsystem = new CANElevatorSubsystem();

  // The driver's controller
  private final CommandXboxController driverController = new CommandXboxController(
      OperatorConstants.DRIVER_CONTROLLER_PORT);

  // The autonomous chooser
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  private final BlinkCommand blinkCommand = new BlinkCommand();
  private final LimeLightCommand LIMELIGHT = new LimeLightCommand(); // Creating a nametag to refer to 

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Set up command bindings
    configureBindings();

    // Set the options to show up in the Dashboard for selecting auto modes. If you
    // add additional auto modes you can add additional lines here with
    // autoChooser.addOption
    autoChooser.setDefaultOption("Autonomous", new AutoCommand(driveSubsystem));
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be
   * created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with
   * an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for
   * {@link
   * CommandXboxController
   * Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or
   * {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Set the A button to run the "RollerCommand" command with a fixed
    // value ejecting the gamepiece while the button is held


  
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
    
    driverController.a().whileTrue(LIMELIGHT);

    driverController.y().whileTrue(new ElevatorCommand(elevatorSubsystem, ElevatorDirection.Up));
    driverController.x().whileTrue(new ElevatorCommand(elevatorSubsystem, ElevatorDirection.Down));

    // Set the default command for the roller subsystem to an instance of
    // RollerCommand with the values provided by the triggers on the operator
    // controller
    rollerSubsystem.setDefaultCommand(new RollerCommand(
        () -> driverController.getRightTriggerAxis(),
        () -> driverController.getLeftTriggerAxis(),
        rollerSubsystem));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return autoChooser.getSelected();
  }
}

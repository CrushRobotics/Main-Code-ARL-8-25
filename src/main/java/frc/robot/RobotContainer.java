// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.Autos;
import frc.robot.commands.ClimberClimbCommand;
import frc.robot.commands.ClimberLowerCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.FeedShooterCommand;
import frc.robot.commands.LowerAndRunIntakeCommand;
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.ShootingPositionCommand;
import frc.robot.commands.MoveArmCommand.ArmDirection;
import frc.robot.commands.ShootingPositionCommand.ArmAngle;
import frc.robot.subsystems.ArmSubsystem;
import frc.robot.subsystems.ClimberSubsystem;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.DefaultDriveCommand;
import frc.robot.commands.LimelightCommand;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.commands.AprilTagAutonomous;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveSubsystem m_driveSubsystem = new DriveSubsystem();
 // private final DigitalInput limitSwitch = new DigitalInput(8);
  //private final DigitalInput beamBreak = new DigitalInput(0);
  private final ArmSubsystem armSubsystem = new ArmSubsystem();
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem();
  private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
  //private final IntakeSubsystem intakeSubsystem = new IntakeSubsystem();

  // ADDED: Auto chooser field
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  private final CommandXboxController m_armShooterController = 
      new CommandXboxController(OperatorConstants.kShooterControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

      //m_driveSubsystem.register();
      m_driveSubsystem.setDefaultCommand(new DefaultDriveCommand(m_driverController, m_driveSubsystem));

      // ADDED: Configure auto chooser
      configureAutoChooser();

      // Configure the trigger bindings
      configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    // Schedule `ExampleCommand` when `exampleCondition` changes to `true`

    // Schedule `exampleMethodCommand` when the Xbox controller's B button is pressed,
    // cancelling on release.
    //m_driverController.b().whileTrue(m_exampleSubsystem.exampleMethodCommand());
    //m_driverController.a().whileTrue(new LimelightCommand(m_driverController, m_driveSubsystem));
    m_driverController.leftBumper().whileTrue(new ClimberLowerCommand(climberSubsystem));
    m_driverController.rightBumper().whileTrue(new ClimberClimbCommand(climberSubsystem));
 
    m_armShooterController.rightBumper().whileTrue(new ShootCommand(shooterSubsystem));
    //m_armShooterController.a().whileTrue(new ShootingPositionCommand(armSubsystem));
    m_armShooterController.b().whileTrue(new FeedShooterCommand(shooterSubsystem));
    m_armShooterController.y().whileTrue(new MoveArmCommand(armSubsystem, ArmDirection.Up));
    m_armShooterController.x().whileTrue(new MoveArmCommand(armSubsystem, ArmDirection.Down));
    //m_armShooterController.rightBumper().whileTrue(new LowerAndRunIntakeCommand(intakeSubsystem));
    m_armShooterController.povLeft().whileTrue(new ShootingPositionCommand(armSubsystem, ArmAngle.ShootAngle));
    m_armShooterController.povRight().whileTrue(new ShootingPositionCommand(armSubsystem, ArmAngle.SourceAngle));
  }

  // ADDED: Auto chooser configuration method
  private void configureAutoChooser() {
    // AprilTag autonomous (primary option)
    autoChooser.setDefaultOption("AprilTag Ranking Point Auto", 
        AprilTagAutonomous.aprilTagRankingPointAuto(m_driveSubsystem, armSubsystem, shooterSubsystem));
    
    // Backup if AprilTags fail
    autoChooser.addOption("Backup Auto (No Vision)", 
        AprilTagAutonomous.backupAutoNoVision(m_driveSubsystem, armSubsystem, shooterSubsystem));
    
    // Simple mobility
    autoChooser.addOption("Mobility Only", 
        Autos.mobilityOnlyAuto(m_driveSubsystem));
        
    // Put chooser on dashboard
    SmartDashboard.putData("Auto Chooser", autoChooser);
  }
        
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // CHANGED: Return the selected autonomous command instead of hardcoded one
    return autoChooser.getSelected();
  }
}
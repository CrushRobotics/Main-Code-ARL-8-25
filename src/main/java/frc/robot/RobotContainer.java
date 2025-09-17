// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.ctre.phoenix6.CANBus;
// import com.ctre.phoenix6.controls.ControlRequest;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.controls.Follower;
// import com.ctre.phoenix6.controls.StrictFollower;
import com.ctre.phoenix6.hardware.TalonFX;
// import com.revrobotics.spark.SparkLowLevel.MotorType;
// import com.revrobotics.spark.SparkMax;

// import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
// import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
<<<<<<< HEAD
// import frc.robot.Constants.RollerConstants;
import frc.robot.commands.AutoCommand;
=======
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
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235

// import frc.robot.commands.BlinkCommand;
// import frc.robot.commands.LimeLightCommand; // Import the file first 
import frc.robot.commands.MoveArmCommand;
import frc.robot.commands.AlgaeCommand;
import frc.robot.commands.AlgaeCommand.AlgaeDirection;
import frc.robot.commands.ElevatorCommand.ElevatorDirection;
import frc.robot.commands.ElevatorCommand;
import frc.robot.commands.MoveArmCommand.ArmDirection;
import frc.robot.subsystems.CANDriveSubsystem;
import frc.robot.subsystems.CANAlgaeSubsystem;
import frc.robot.subsystems.CANElevatorSubsystem;

import frc.robot.subsystems.CANArmSubsystem;

import frc.robot.subsystems.CANAlgaeIntakeSubsystem;
import frc.robot.commands.AlgaeIntakeCommand;
import frc.robot.commands.AlgaeIntakeCommand.AlgaeIntakeDirection;

import frc.robot.subsystems.CANCoralIntakeSubsystem;
import frc.robot.commands.CoralIntakeCommand;
import frc.robot.commands.CoralIntakeCommand.CoralIntakeDirection;

import frc.robot.subsystems.ClimberSubsystem;
// import frc.robot.commands.ClimberClimbCommand;
// import frc.robot.commands.ClimberLowerCommand;


//import frc.robot.subsystems;;
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
  // CANBus - MOVED TO TOP (mandatory fix)
  private final CANBus kCANBus = new CANBus();

  // The robot's subsystems
<<<<<<< HEAD
  private final CANDriveSubsystem driveSubsystem = new CANDriveSubsystem();
  private final CANAlgaeSubsystem algaeSubsystem = new CANAlgaeSubsystem();
  private final CANElevatorSubsystem elevatorSubsystem = new CANElevatorSubsystem();
  private final CANArmSubsystem armSubsystem = new CANArmSubsystem();
  // FIXED: Pass kCANBus to algae intake subsystem
  private final CANAlgaeIntakeSubsystem algaeIntakeSubsystem = new CANAlgaeIntakeSubsystem(kCANBus);
  private final CANCoralIntakeSubsystem coralIntakeSubsystem = new CANCoralIntakeSubsystem();

  Timer timer = new Timer();

  // private final ClimberClimbCommand coralIntakeSubsystem = new ClimberClimbCommand();
  //private final ClimberLowerCommand coralIntakeSubsystem = new ClimberLowerCommand();
  private final ClimberSubsystem climberSubsystem = new ClimberSubsystem();
=======
  private final DriveSubsystem driveSubsystem = new DriveSubsystem(); // Updated to new subsystem
  private final CANRollerSubsystem rollerSubsystem = new CANRollerSubsystem();
  private final ArmSubsystem armSubsystem = new ArmSubsystem(); // Added
  private final ShooterSubsystem shooterSubsystem = new ShooterSubsystem(); // Added
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235

  // The driver's controller
  private final CommandXboxController driverController = new CommandXboxController(
      OperatorConstants.DRIVER_CONTROLLER_PORT);
  
  // Optional operator controller for arm/shooter control
  private final CommandXboxController operatorController = new CommandXboxController(
      OperatorConstants.OPERATOR_CONTROLLER_PORT);

  private final CommandXboxController operatorController = new CommandXboxController(OperatorConstants.OPERATOR_CONTROLLER_PORT);
  // The autonomous chooser
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  
  // Drive motors (REMOVED the duplicate algaeIntakeMotor line)
  public final TalonFX leftLeader = new TalonFX(8, kCANBus);
  public final TalonFX leftFollower = new TalonFX(9, kCANBus);
  public final TalonFX rightLeader = new TalonFX(7, kCANBus);
  public final TalonFX rightFollower = new TalonFX(6, kCANBus);

<<<<<<< HEAD
  //private final BlinkCommand blinkCommand = new BlinkCommand();

  
  // private final LimeLightCommand LIMELIGHT = new LimeLightCommand(); // Creating a nametag to refer to 
 //  private final AlgaeCommand algae = new AlgaeCommand();
=======
  private final BlinkCommand blinkCommand = new BlinkCommand();
  private final LimeLightCommand LIMELIGHT = new LimeLightCommand();
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {
    // Set up command bindings
    configureBindings();
    
    // ENHANCED CAN DEVICE SCANNING
    System.out.println("=== SCANNING CAN DEVICES ===");

    // Test different TalonFX IDs to see which ones respond - EXPANDED RANGE
    int[] testIds = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25};

    for (int id : testIds) {
        try {
            TalonFX testMotor = new TalonFX(id, kCANBus);
            if (testMotor.isAlive()) {
                System.out.println("FOUND TalonFX at CAN ID: " + id);
            }
        } catch (Exception e) {
            // Ignore errors for non-existent devices
        }
    }

    System.out.println("=== CAN SCAN COMPLETE ===");

    // SPECIFIC TEST FOR CAN ID 10 (Algae Intake Motor)
    System.out.println("=== TESTING ALGAE INTAKE MOTOR (CAN ID 10) ===");
    try {
        TalonFX testMotor10 = new TalonFX(10, kCANBus);
        System.out.println("CAN ID 10 isAlive: " + testMotor10.isAlive());
        System.out.println("CAN ID 10 Device ID: " + testMotor10.getDeviceID());
        
        // Test a small movement to verify motor response
        System.out.println("Testing small motor movement...");
        testMotor10.set(0.1);
        try { Thread.sleep(200); } catch (Exception e) {}
        testMotor10.set(0);
        System.out.println("CAN ID 10 basic movement test completed");
        
        // Check for faults
        System.out.println("CAN ID 10 Faults: " + testMotor10.getFaultField().toString());
        
    } catch (Exception e) {
        System.out.println("ERROR with CAN ID 10: " + e.getMessage());
        e.printStackTrace();
    }
    System.out.println("=== ALGAE INTAKE TEST COMPLETE ===");

    // Configure follower motors
    leftFollower.setControl(new Follower(8, false));
    rightFollower.setControl(new Follower(7, false));

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
<<<<<<< HEAD
  
    // OPERATOR CONTROLLER
    
    operatorController.y().whileTrue(new ElevatorCommand(elevatorSubsystem, ElevatorDirection.Up));
    operatorController.a().whileTrue(new ElevatorCommand(elevatorSubsystem, ElevatorDirection.Down));

    operatorController.rightTrigger().whileTrue(new MoveArmCommand(armSubsystem, ArmDirection.Up));
    operatorController.leftTrigger().whileTrue(new MoveArmCommand(armSubsystem, ArmDirection.Down));
    
    operatorController.leftBumper().whileTrue(new CoralIntakeCommand(coralIntakeSubsystem, CoralIntakeDirection.Up));
    operatorController.rightBumper().whileTrue(new CoralIntakeCommand(coralIntakeSubsystem, CoralIntakeDirection.Down));

    // driverController.x().whileTrue(new ClimberClimbCommand(climberSubsystem.Raise));
    // driverController.a().whileTrue(new ClimberLowerCommand(climberSubsystem.Lower));

    // DRIVER CONTROLLER
    driverController.rightTrigger().onTrue(new AlgaeCommand(algaeSubsystem, AlgaeDirection.Down, algaeIntakeSubsystem));
    driverController.rightTrigger().onFalse(new AlgaeCommand(algaeSubsystem, AlgaeDirection.Up, algaeIntakeSubsystem));

    // ORIGINAL ALGAE INTAKE CONTROLS (using commands)
    driverController.leftBumper().whileTrue(new AlgaeIntakeCommand(algaeIntakeSubsystem, AlgaeIntakeDirection.Up));
    driverController.rightBumper().whileTrue(new AlgaeIntakeCommand(algaeIntakeSubsystem, AlgaeIntakeDirection.Down));
    
    // ENHANCED DIRECT TEST CONTROLS - FOR DEBUGGING ALGAE INTAKE
    // X button - Test right direction with debug output
    driverController.x().whileTrue(Commands.run(() -> {
        System.out.println("X BUTTON: Testing algae intake RIGHT direction");
        algaeIntakeSubsystem.right();
    }, algaeIntakeSubsystem));
    
    // Y button - Test left direction with debug output  
    driverController.y().whileTrue(Commands.run(() -> {
        System.out.println("Y BUTTON: Testing algae intake LEFT direction");
        algaeIntakeSubsystem.left();
    }, algaeIntakeSubsystem));
    
    // ADDED: B button for emergency stop
    driverController.b().onTrue(Commands.runOnce(() -> {
        System.out.println("B BUTTON: EMERGENCY STOP algae intake");
        algaeIntakeSubsystem.stop();
    }, algaeIntakeSubsystem));
    
    // EMERGENCY MOTOR TEST - START button
    driverController.start().onTrue(Commands.runOnce(() -> {
        System.out.println("=== EMERGENCY MOTOR TEST ===");
        algaeIntakeSubsystem.algaeIntakeMotor.set(0.3);
        try { Thread.sleep(1000); } catch (Exception e) {}
        System.out.println("Motor output: " + algaeIntakeSubsystem.algaeIntakeMotor.get());
        algaeIntakeSubsystem.algaeIntakeMotor.set(0);
        System.out.println("=== TEST COMPLETE ===");
    }));

    // QUICK MOTOR TEST - BACK button
    driverController.back().onTrue(Commands.runOnce(() -> {
        System.out.println("=== QUICK MOTOR TEST ===");
        TalonFX testMotor = new TalonFX(10, kCANBus);
        
        System.out.println("Motor Alive: " + testMotor.isAlive());
        System.out.println("Supply Voltage: " + testMotor.getSupplyVoltage().getValue() + "V");
        System.out.println("Before - Current: " + testMotor.getSupplyCurrent().getValue() + "A");
        
        // Test movement
        testMotor.set(0.3);
        try { Thread.sleep(500); } catch (Exception e) {}
        System.out.println("During Move - Current: " + testMotor.getSupplyCurrent().getValue() + "A");
        testMotor.set(0);
        
        System.out.println("=== TEST DONE ===");
    }));
     
=======
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
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
<<<<<<< HEAD
    // An example command will be run in autonomous
    // return autoChooser.getSelected();

    // AUTONOMOUS CODE
    return new FunctionalCommand(()->{
      timer.reset();
      timer.start();
    }, ()->{
      leftLeader.setControl(new DutyCycleOut(.25));
      rightLeader.setControl(new DutyCycleOut(.25));
    }, (oob)->{
      rightLeader.setControl(new DutyCycleOut(0));
      leftLeader.setControl(new DutyCycleOut(0));
      // FIXED: Use runIntake method instead of direct motor access
      coralIntakeSubsystem.runIntake(0.2);

      timer.stop();
      
    }, ()->{if(timer.get() > 3) {
      return true;
    }else{
      return false;
    }}, driveSubsystem);
=======
    return autoChooser.getSelected();
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235
  }
}
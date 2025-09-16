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
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.FunctionalCommand;
// import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.OperatorConstants;
// import frc.robot.Constants.RollerConstants;
import frc.robot.commands.AutoCommand;

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

  // The driver's controller
  private final CommandXboxController driverController = new CommandXboxController(
      OperatorConstants.DRIVER_CONTROLLER_PORT);

  private final CommandXboxController operatorController = new CommandXboxController(OperatorConstants.OPERATOR_CONTROLLER_PORT);
  // The autonomous chooser
  private final SendableChooser<Command> autoChooser = new SendableChooser<>();
  
  // Drive motors (REMOVED the duplicate algaeIntakeMotor line)
  public final TalonFX leftLeader = new TalonFX(8, kCANBus);
  public final TalonFX leftFollower = new TalonFX(9, kCANBus);
  public final TalonFX rightLeader = new TalonFX(7, kCANBus);
  public final TalonFX rightFollower = new TalonFX(6, kCANBus);

  //private final BlinkCommand blinkCommand = new BlinkCommand();

  
  // private final LimeLightCommand LIMELIGHT = new LimeLightCommand(); // Creating a nametag to refer to 
 //  private final AlgaeCommand algae = new AlgaeCommand();

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
     
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
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
  }
}
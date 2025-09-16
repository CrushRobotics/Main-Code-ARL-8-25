// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;



// import com.ctre.phoenix6.CANBus;

// import com.ctre.phoenix6.configs.TalonFXConfiguration;
// import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.controls.DutyCycleOut;
// import com.ctre.phoenix6.hardware.TalonFX;
// import com.ctre.phoenix6.signals.InvertedValue;


import edu.wpi.first.wpilibj.XboxController;

// Class to drive the robot over CAN
public class CANDriveSubsystem extends SubsystemBase {
  // private final CANBus kCANBus = new CANBus();

  // private final TalonFX leftLeader = new TalonFX(8, kCANBus);
  // private final TalonFX leftFollower = new TalonFX(9, kCANBus);
  // private final TalonFX rightLeader = new TalonFX(7, kCANBus);
  // private final TalonFX rightFollower = new TalonFX(6, kCANBus);

  public final DutyCycleOut leftOut = new DutyCycleOut(0);
  public final DutyCycleOut rightOut = new DutyCycleOut(0);

  public final XboxController joystick = new XboxController(0);

  public int printCount = 0;
  

  public CANDriveSubsystem() {
    /* Configure the devices */

  }

}

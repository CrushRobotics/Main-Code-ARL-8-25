package frc.robot.subsystems;

import com.ctre.phoenix6.CANBus;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.signals.NeutralModeValue;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANAlgaeIntakeSubsystem extends SubsystemBase {
    public final WPI_TalonSRX algaeIntakeMotor;  // Changed to public for debugging

    // TalonFX control object for percent output
    private final DutyCycleOut dutyCycleOut = new DutyCycleOut(0);

    public CANAlgaeIntakeSubsystem(CANBus canBus) {  // FIXED: Added CANBus parameter
        algaeIntakeMotor = new WPI_TalonSRX(10, canBus);  // FIXED: Use CANBus
        algaeIntakeMotor.setNeutralMode(NeutralMode.Brake);
    }
    

    @Override
    public void periodic() {
        // SmartDashboard.putNumber("Algae Intake Position", algaeIntakeMotor.getPosition().getValueAsDouble());
        
        // // Added debug values
        // SmartDashboard.putNumber("Algae Intake Current", algaeIntakeMotor.getSupplyCurrent().getValueAsDouble());
        // SmartDashboard.putNumber("Algae Intake Output", algaeIntakeMotor.get());
        // SmartDashboard.putBoolean("Algae Intake Connected", algaeIntakeMotor.isAlive());
    }

    public void right() {
        System.out.println("RIGHT: Setting motor to 60%");
        algaeIntakeMotor.set(ControlMode.PercentOutput, 0.6);
    }

    public void left() {
        System.out.println("LEFT: Setting motor to -60%");
        algaeIntakeMotor.set(ControlMode.PercentOutput, -0.6);
    }

    public void stop() {
        System.out.println("STOP: Setting motor to 0%");
        algaeIntakeMotor.set(ControlMode.PercentOutput,0);
    }
}
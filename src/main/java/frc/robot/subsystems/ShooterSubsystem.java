package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class ShooterSubsystem extends SubsystemBase {
    private final SparkMax shooterMotor;
    
    private boolean isRunning = false;

    public ShooterSubsystem() {
        shooterMotor = new SparkMax(ShooterConstants.SHOOTER_MOTOR_ID, MotorType.kBrushless);
        
        // Configure motor
        SparkMaxConfig config = new SparkMaxConfig();
        config.voltageCompensation(12);
        config.smartCurrentLimit(40); // Adjust based on your motor
        shooterMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    @Override
    public void periodic() {
        SmartDashboard.putBoolean("Shooter Running", isRunning);
        SmartDashboard.putNumber("Shooter Speed", shooterMotor.get());
    }

    /**
     * Run the shooter at the specified speed
     * @param speed Speed from 0.0 to 1.0
     */
    public void setSpeed(double speed) {
        shooterMotor.set(Math.abs(speed)); // Ensure positive speed
        isRunning = speed > 0;
    }

    /**
     * Run the shooter at default shooting speed
     */
    public void shoot() {
        setSpeed(ShooterConstants.SHOOTER_SPEED);
    }

    /**
     * Stop the shooter
     */
    public void stop() {
        shooterMotor.stopMotor();
        isRunning = false;
    }

    /**
     * Check if the shooter is running
     * @return True if shooter speed > 0
     */
    public boolean isRunning() {
        return isRunning;
    }
}
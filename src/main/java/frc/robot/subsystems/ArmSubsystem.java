package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;

public class ArmSubsystem extends SubsystemBase {
    private final SparkMax armMotor;
    private final RelativeEncoder armEncoder;
    private final PIDController pidController;
    
    private double targetAngle = 0.0;
    
    // PID Constants - tune these for your robot
    private static final double kP = 0.05;
    private static final double kI = 0.0;
    private static final double kD = 0.001;
    
    // Conversion factor from encoder rotations to degrees
    private static final double GEAR_RATIO = 100.0; // Adjust based on your gearbox
    private static final double DEGREES_PER_ROTATION = 360.0 / GEAR_RATIO;

    public ArmSubsystem() {
        armMotor = new SparkMax(ArmConstants.ARM_MOTOR_ID, MotorType.kBrushless);
        armEncoder = armMotor.getEncoder();
        
        // Configure motor
        SparkMaxConfig config = new SparkMaxConfig();
        config.voltageCompensation(12);
        config.smartCurrentLimit(30);
        armMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        // Set encoder conversion factor
        armEncoder.setPositionConversionFactor(DEGREES_PER_ROTATION);
        
        // Initialize PID controller
        pidController = new PIDController(kP, kI, kD);
        pidController.setTolerance(2.0); // 2 degree tolerance
        
        // Reset encoder (assume starting position is 0 degrees)
        armEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        // Calculate PID output
        double pidOutput = pidController.calculate(getAngle(), targetAngle);
        
        // Apply safety bounds
        double currentAngle = getAngle();
        if ((currentAngle <= ArmConstants.MIN_BOUND && pidOutput < 0) ||
            (currentAngle >= ArmConstants.MAX_BOUND && pidOutput > 0)) {
            pidOutput = 0;
        }
        
        // Set motor output
        armMotor.set(pidOutput);
        
        // Update dashboard
        SmartDashboard.putNumber("Arm Angle", currentAngle);
        SmartDashboard.putNumber("Arm Target", targetAngle);
        SmartDashboard.putBoolean("Arm At Target", atTarget());
    }

    /**
     * Set the target angle for the arm
     * @param angle Target angle in degrees
     */
    public void setTarget(double angle) {
        // Clamp angle to safe bounds
        targetAngle = Math.max(ArmConstants.MIN_BOUND, 
                      Math.min(ArmConstants.MAX_BOUND, angle));
    }

    /**
     * Get the current arm angle
     * @return Current angle in degrees
     */
    public double getAngle() {
        return armEncoder.getPosition();
    }

    /**
     * Check if the arm is at the target position
     * @return True if at target within tolerance
     */
    public boolean atTarget() {
        return pidController.atSetpoint();
    }

    /**
     * Manually set arm speed (for manual control)
     * @param speed Speed from -1.0 to 1.0
     */
    public void setSpeed(double speed) {
        double currentAngle = getAngle();
        
        // Safety bounds
        if ((currentAngle <= ArmConstants.MIN_BOUND && speed < 0) ||
            (currentAngle >= ArmConstants.MAX_BOUND && speed > 0)) {
            speed = 0;
        }
        
        armMotor.set(speed);
    }

    /**
     * Stop the arm motor
     */
    public void stop() {
        armMotor.stopMotor();
    }

    /**
     * Reset the encoder to a specific angle
     * @param angle Angle in degrees to reset to
     */
    public void resetEncoder(double angle) {
        armEncoder.setPosition(angle);
    }
}
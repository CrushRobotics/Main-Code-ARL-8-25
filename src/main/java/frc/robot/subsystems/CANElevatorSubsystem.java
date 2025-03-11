package frc.robot.subsystems;

import edu.wpi.first.wpilibj.motorcontrol.Spark;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.servohub.ServoHub.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.ElevatorFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
// import static frc.robot.Constants.ElevatorConstants.*;

public class CANElevatorSubsystem extends SubsystemBase {

    private SparkMax leftElevatorMotor;
    private SparkMax rightElevatorMotor;

    private SparkMaxConfig leftElevatorConfig;
    private SparkMaxConfig rightElevatorConfig;

    

    private RelativeEncoder encoder;

    private Constraints elevatorProfileConstraints;
    private ProfiledPIDController elevatorPIDController;

    private double lastSpeed = 0;
    private double lastTime = Timer.getFPGATimestamp(); 
    
    private final double minHeight = -10;
    private final double maxHeight = -140; // This is the highest it can possibly go, about 45 inches. We might want to change this to a smaller value for safety's sake?

    // ElevatorFeedforward feedforward = new ElevatorFeedforward(elevatorKS, elevatorKG, elevatorKV);

    public CANElevatorSubsystem() {

        // elevatorProfileConstraints = new Constraints(elevatorMaxVel, elevatorMaxAccel);
        // elevatorPIDController = new ProfiledPIDController(elevatorKP, elevatorKI, elevatorKD, elevatorProfileConstraints);

        // elevatorPIDController.setTolerance(elevatorPIDTolerance);

        leftElevatorMotor = new SparkMax(5, MotorType.kBrushless);
        rightElevatorMotor = new SparkMax(6, MotorType.kBrushless);

        leftElevatorConfig = new SparkMaxConfig();
        rightElevatorConfig = new SparkMaxConfig();

        rightElevatorConfig.follow(leftElevatorMotor, true);

        rightElevatorMotor.configure(rightElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


        encoder = leftElevatorMotor.getEncoder();

        

        // When the match starts we should assume that the elevator is either at the
        // minimum or the maximum position and reset the encoder accordingly. 
        encoder.setPosition(0);

        // We need to set the position conversion factor which says how many meters
        // the elevator moves with one full rotation of the motor. To do this, we must
        // know the gear ratio and the diameter of the gear/wheel driving the elevator.
        /*
         * gear ratio: 1:27
         * gear diameter: 22 teeth / 20 for diametral pitch? 
         * I am unsure if this is what we're looking for
         * 
         * 22 teeth
         * .25 inches per teeth
         */
        
        // encoder.setPositionConversionFactor(1);
        // leftElevatorMotor.burnFlash();
    }
    
    @Override 
    public void periodic()
    {
        // Log dashboard values
        SmartDashboard.putNumber("Elevator Position", encoder.getPosition());
    }

    public void raise() 
    {
        leftElevatorMotor.set(.3); 
    }


    // /**
    //  * Set the goal for the elevator PID controller. This must be called periodically.
    //  * @param goal
    //  */

    // public void setPosition(double positionGoal) {
        
    //     double pidVal = elevatorPIDController.calculate(getPosition(), positionGoal);
    //     double acceleration = (elevatorPIDController.getSetpoint().velocity - lastSpeed) / (Timer.getFPGATimestamp() - lastTime);

    //     double motorVoltsOutput = pidVal + elevatorKF; // Arbitrary FeedForward


    //     // TODO Check signs on these
    //     // Motor safety. Might want to add limit switches at some point.
    //     if(motorVoltsOutput > 0 && isAtBottom()){

    //         motorVoltsOutput = 0;
    //     }
    //     else if(motorVoltsOutput < 0 && isAtTop()){

    //         motorVoltsOutput = 0;
    //     } else {

    //         setElevatorVolts(motorVoltsOutput);
    //     }

    //     lastSpeed = elevatorPIDController.getSetpoint().velocity;
    //     lastTime = Timer.getFPGATimestamp();
    // }


    // public double getPositionError() {
    //     return elevatorPIDController.getPositionError();
    // }
    
    // public boolean isAtGoal() {
    //     return elevatorPIDController.atGoal();
    // }

    // public void setElevatorVolts(double volts) {
    //     leftElevatorMotor.setVoltage(volts);
    // }

    // public void disable() {
    //     leftElevatorMotor.set(0);
    //     rightElevatorMotor.set(0);
    // }

    // public boolean isAtTop()
    // {
    //     var position = encoder.getPosition();
    //     return position <= maxHeight; // TODO: Might want to add a fudge factor here for safety?
        
    //     //SmartDashboard.putData("elevator", elevatorDrive);
    // }

    // public boolean isAtBottom()
    // {
    //     var position = encoder.getPosition();
    //     return position >= minHeight; // TODO: Might want to add a fudge factor here for safety?
    // }

    // public void resetEncoder(double position) {
    //     encoder.setPosition(position);
    // }
    // public void resetEncoder() {
    //     resetEncoder(0);
    // }

    // public double getPosition()
    // {
    //     return encoder.getPosition() * encoderConversionFactor;
    // }

    // public double getMaxPosition()
    // {
    //     return maxHeight;
    // }

    // public void setSpeed(double speed)
    // {
    //     if(speed > 0 && isAtBottom()){

    //         speed = 0;
    //     }
    //     else if(speed < 0 && isAtTop()){

    //         speed = 0;
    //     }
        
    //     leftElevatorMotor.set(speed);
    // }

}
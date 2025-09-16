package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// import edu.wpi.first.math.controller.ElevatorFeedforward;
// import edu.wpi.first.math.controller.ProfiledPIDController;
// import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
// import edu.wpi.first.math.util.Units;
// import edu.wpi.first.wpilibj.Timer;
// import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants;

public class CANElevatorSubsystem extends SubsystemBase {

    private SparkMax leftElevatorMotor;
    private SparkMax rightElevatorMotor;

    private SparkMaxConfig leftconfig;
    private SparkMaxConfig rightconfig;

    private RelativeEncoder leftEncoder;
    private RelativeEncoder rightEncoder;
    private final SparkClosedLoopController pidLeftElevatorMotor;

    // private Constraints elevatorProfileConstraints;
    // private ProfiledPIDController elevatorPIDController;

    // private double lastSpeed = 0;
    // private double lastTime = Timer.getFPGATimestamp(); 
    
   //  private final double minHeight = -10;
    // private final double maxHeight = -140; // This is the highest it can possibly go, about 45 inches. We might want to change this to a smaller value for safety's sake?

    // ElevatorFeedforward feedforward = new ElevatorFeedforward(elevatorKS, elevatorKG, elevatorKV);

    public CANElevatorSubsystem() {

        leftElevatorMotor = new SparkMax(ElevatorConstants.ELEVATOR_LEADER_ID, MotorType.kBrushless);
        rightElevatorMotor = new SparkMax(ElevatorConstants.ELEVATOR_FOLLOWER_ID, MotorType.kBrushless);
        
        pidLeftElevatorMotor = leftElevatorMotor.getClosedLoopController();
        leftconfig = new SparkMaxConfig();
        rightconfig = new SparkMaxConfig();

        leftconfig.idleMode(IdleMode.kBrake);
        rightconfig.idleMode(IdleMode.kBrake);

        //leftElevatorMotor.configure(leftconfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        

        //rightElevatorMotor.configure(rightconfig, ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);
        
        leftEncoder = leftElevatorMotor.getEncoder();

        leftconfig.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(Constants.PIDElevatorConstants.P, Constants.PIDElevatorConstants.I, Constants.PIDElevatorConstants.D);


        leftEncoder = leftElevatorMotor.getEncoder();
        rightEncoder = rightElevatorMotor.getEncoder();

        rightconfig.follow(leftElevatorMotor, true);
        // When the match starts we should assume that the elevator is either at the
        // minimum or the maximum position and reset the encoder accordingly. 
        leftEncoder.setPosition(0); // sets initial encoder position to zero
        rightEncoder.setPosition(0);

        leftElevatorMotor.configure(leftconfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
        rightElevatorMotor.configure(rightconfig, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);

    }
    
     public void setMotorPostion(double targetPosition){
      pidLeftElevatorMotor.setReference(targetPosition, SparkBase.ControlType.kPosition);
      
    }

    @Override 
    public void periodic()
    {
        // Log dashboard values
        SmartDashboard.putNumber("Elevator Position", leftEncoder.getPosition());

        double currentleftPosition = leftEncoder.getPosition();
        double currentrightPosition = rightEncoder.getPosition();
    }

    public void raise() 
    {
        leftElevatorMotor.set(0.4); // Raise speed. Reduce from 0.9  
    }

    public void lower() 
    {
        leftElevatorMotor.set(-.3);
    }

    public void stop() 
    {
        leftElevatorMotor.setVoltage(0);
    }

    // /**
    //  * Set the goal for the elevator PID controller. This must be called periodically.
    //  * @param goal
    //  */

    // public void setPosition(double positionGoal) {
        
    //     double pidVal = elevatorPIDController.calculate(getPosition(), positionGoal);
    //     double acceleration = (elevatorPIDController.getSetpoint().velocity - lastSpeed) / (Timer.getFPGATimestamp() - lastTime);

    //     double motorVoltsOutput = pidVal + elevatorKF; // Arbitrary FeedForward


    //     // Check signs on these
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
    //     return position <= maxHeight; // Might want to add a fudge factor here for safety?
        
    //     //SmartDashboard.putData("elevator", elevatorDrive);
    // }

    // public boolean isAtBottom()
    // {
    //     var position = encoder.getPosition();
    //     return position >= minHeight; // Might want to add a fudge factor here for safety?
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
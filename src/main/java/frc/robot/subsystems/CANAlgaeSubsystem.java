package frc.robot.subsystems;


// import com.ctre.phoenix6.jni.CtreJniWrapper;
import com.revrobotics.RelativeEncoder;
// import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.ClosedLoopConfig.FeedbackSensor;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkClosedLoopController;

public class CANAlgaeSubsystem extends SubsystemBase {
    private final SparkMax algaeMotor;
    private final SparkMaxConfig config;
    public RelativeEncoder encoder;
    private double remainPosition;
    private final SparkClosedLoopController pidAlgaeMotor;

    public CANAlgaeSubsystem() {
        algaeMotor = new SparkMax(10, MotorType.kBrushless); // placeholder ID
        
        pidAlgaeMotor = algaeMotor.getClosedLoopController();
        config = new SparkMaxConfig();
        config.idleMode(IdleMode.kBrake);
        //algaeMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        encoder = algaeMotor.getEncoder();
        config.closedLoop.feedbackSensor(FeedbackSensor.kPrimaryEncoder).pid(Constants.PIDConstants.P, Constants.PIDConstants.I, Constants.PIDConstants.D);

        algaeMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kNoPersistParameters);
    }
    public void setMotorPostion(double targetPosition){
      pidAlgaeMotor.setReference(targetPosition, SparkBase.ControlType.kPosition);
      
    }
    

    @Override
    public void periodic() {
      remainPosition = encoder.getPosition();
        SmartDashboard.putNumber("Algae Subsystem Position", encoder.getPosition());

    }

    public void right() {
        algaeMotor.set(0.1);
    }
    public void moveToShootPosition(){
        //setTarget(frc.robot.Constants.ArmConstants.SHOOTING_POSITION);
    }

    public void left() {
        algaeMotor.set(-0.1);
    }
    public void stop(){
        algaeMotor.setVoltage(0);
    }
}


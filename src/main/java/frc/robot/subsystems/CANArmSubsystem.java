package frc.robot.subsystems;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANArmSubsystem extends SubsystemBase {
    private final SparkMax armMotor;
    private final SparkMaxConfig config;
    private final RelativeEncoder encoder;

    public CANArmSubsystem() {
        armMotor = new SparkMax(3, MotorType.kBrushless); // placeholder ID
        
        config = new SparkMaxConfig();

        config.idleMode(IdleMode.kBrake);
        armMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        encoder = armMotor.getEncoder();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Arm Position", encoder.getPosition());
    }

    public void right() {
        armMotor.set(0.3);
    }
    public void moveToShootPosition(){
        //setTarget(frc.robot.Constants.ArmConstants.SHOOTING_POSITION);
    }

    public void left() {
        armMotor.set(-0.3);
    }
    public void stop(){
        armMotor.setVoltage(0);
    }
}


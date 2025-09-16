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

public class CANCoralIntakeSubsystem extends SubsystemBase {
    public final SparkMax coralIntakeMotor;
    private final SparkMaxConfig config;
    private final RelativeEncoder encoder;

    public CANCoralIntakeSubsystem() {
        coralIntakeMotor = new SparkMax(16, MotorType.kBrushless); // placeholder ID
        
        config = new SparkMaxConfig();

        config.idleMode(IdleMode.kBrake);
        coralIntakeMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        encoder = coralIntakeMotor.getEncoder();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Arm Position", encoder.getPosition());
    }

    public void right() {
        coralIntakeMotor.set(0.5);
    }
    public void moveToShootPosition(){
        //setTarget(frc.robot.Constants.ArmConstants.SHOOTING_POSITION);
    }

    public void left() {
        coralIntakeMotor.set(-0.5);
    }
    public void stop(){
        coralIntakeMotor.setVoltage(0);
    }
    public void runIntake(double speed) {
        coralIntakeMotor.set(speed);
    }
}
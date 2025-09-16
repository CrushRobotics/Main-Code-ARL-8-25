package frc.robot.subsystems;


import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class CANCoralSubsystem extends SubsystemBase {
    private final SparkMaxConfig config;
    private final RelativeEncoder encoder;
    private final SparkMax CoralMotor;

    public CANCoralSubsystem() {
        CoralMotor = new SparkMax(15, MotorType.kBrushless); // SET TO 15
        
        config = new SparkMaxConfig();

        config.idleMode(IdleMode.kBrake);
        CoralMotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        encoder = CoralMotor.getEncoder();

    }
        public void right() {
            CoralMotor.set(0.1);
        }
        
    
        public void left() {
            CoralMotor.set(-0.1);
        }
    
    }
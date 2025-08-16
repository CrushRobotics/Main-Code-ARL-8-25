package frc.robot.subsystems;

import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {
    
    private final DigitalInput ringDetector;
    private final CANSparkMax shooterMotor;

    public ShooterSubsystem () {
        
        shooterMotor = new CANSparkMax(05, MotorType.kBrushless);
        ringDetector = new DigitalInput(1);

        // Reset to factory defaults
        shooterMotor.restoreFactoryDefaults();

        // Keep shooters in coast mode
        shooterMotor.setIdleMode(IdleMode.kCoast);
    }
    public void runIntake ()
    {
        shooterMotor.set(0.3);
    }

    public void shoot ()
    {
        shooterMotor.set(-.6);
    }

    public void stop() {
        shooterMotor.set(0);
    }

    public boolean hasRing ()
    {
        return ringDetector.get();
    }
}

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ClimberSubsystem extends SubsystemBase {
    
    private CANSparkMax leftClimber;
    private CANSparkMax rightClimber;

    RelativeEncoder leftEncoder;
    RelativeEncoder rightEncoder;

    public ClimberSubsystem() {
        leftClimber = new CANSparkMax(04, MotorType.kBrushless);
        rightClimber = new CANSparkMax(14, MotorType.kBrushless);

        leftClimber.restoreFactoryDefaults();
        rightClimber.restoreFactoryDefaults();

        leftClimber.setInverted(true);
        
        leftClimber.setIdleMode(IdleMode.kBrake);
        rightClimber.setIdleMode(IdleMode.kBrake);

        leftEncoder = leftClimber.getEncoder();
        rightEncoder = rightClimber.getEncoder();
        leftEncoder.setPosition(0);
        rightEncoder.setPosition(0);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("left climber encoder", leftEncoder.getPosition());
        SmartDashboard.putNumber("right climber encoder", rightEncoder.getPosition());
    }

    public void Climb(){
        if(isInUpperBound()) {
            leftClimber.set(1);
            rightClimber.set(1);
        }
        else {
            leftClimber.set(0);
            rightClimber.set(0);
        }
    }

    public void Lower() {
        if(isInLowerBound()) {    
            leftClimber.set(-1);
            rightClimber.set(-1);
        }
        else {
            leftClimber.set(0);
            rightClimber.set(0);
        }
    }

    public void stop() {
        leftClimber.set(0);
        rightClimber.set(0);
    }

    public boolean isInUpperBound() {
        
        return leftEncoder.getPosition() < frc.robot.Constants.ClimberConstants.MAX_BOUND;

    }

    public boolean isInLowerBound() {
        return leftEncoder.getPosition() > frc.robot.Constants.ClimberConstants.MIN_BOUND;
    }
}

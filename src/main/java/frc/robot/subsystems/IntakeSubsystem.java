package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {
    private final DigitalInput beamBreak = new DigitalInput(0);
    private final RelativeEncoder encoder;
    private double position = 0;
    
    private final CANSparkMax intakeArm;
    private final CANSparkMax intakeWheels;
    private final double LOW_POSITION = 0;


    public IntakeSubsystem(){
        intakeArm = new CANSparkMax(7, MotorType.kBrushless);
        intakeWheels = new CANSparkMax(05, MotorType.kBrushless);
        encoder = intakeArm.getEncoder();

        // Reset to factory defaults
        intakeArm.restoreFactoryDefaults();
        intakeWheels.restoreFactoryDefaults();

        // Setup brake mode to keep them from coasting
        intakeArm.setIdleMode(IdleMode.kBrake);
        intakeWheels.setIdleMode(IdleMode.kBrake);

        encoder.setPosition(0);

        
    }

    /*
    @Override
    public void periodic(){
        
        double currentPosition = encoder.getPosition();

        if (currentPosition == position) {
            intakeArm.set(0);

            if (currentPosition > 0) {
                if (hasRing()) {
                    inTakeoff();
                } else {
                    intTakeOn();
                }
            }

        } else if (currentPosition < position) {
            inTakeoff();
            intakeArm.set(0.3);
        } else {
            inTakeoff();
            intakeArm.set(-0.3);
        }

    }
    */

    public void intTakeOn(){
        intakeWheels.set(0.3);

    }
    public void inTakeoff(){
        intakeWheels.set(0);
    }

    public boolean hasRing(){
        return beamBreak.get();
    }

    public void setIntakePostion(double position){
        this.position = position;
    }

    public void lowerIntake()
    {
        setIntakePostion(LOW_POSITION);
    }

    public void raiseIntake()
    {
        setIntakePostion(0);
    }

    public void toggleIntake()
    {
        if (position != 0)
        {
            setIntakePostion(LOW_POSITION);
        }
        else 
        {
            setIntakePostion(0);
        }
    }
}

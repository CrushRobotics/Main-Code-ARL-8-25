package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkBase.IdleMode;

public class ArmSubsystem extends SubsystemBase {
    
    private final CANSparkMax armLeader;
    private final CANSparkMax armFollower;
    private final RelativeEncoder encoder;
    private double target;
    private boolean autoPosition;

    public ArmSubsystem()
    {
        //super(new PIDController(0, 0, 0));
        
        armLeader = new CANSparkMax(03, MotorType.kBrushless);
        armFollower = new CANSparkMax(13, MotorType.kBrushless);

        // Reset to factory defaults
        armLeader.restoreFactoryDefaults();
        armFollower.restoreFactoryDefaults();

        armFollower.setInverted(true);

        // Setup brake mode to keep them from coasting
        armLeader.setIdleMode(IdleMode.kBrake);
        armFollower.setIdleMode(IdleMode.kBrake);

        encoder = armLeader.getEncoder();
        encoder.setPosition(0);

        autoPosition = false;
    }

    @Override
    public void periodic()
    {
        double currentPosition = encoder.getPosition();
        SmartDashboard.putNumber("arm encoder", encoder.getPosition());
        double targetSpeed = 0.1;
        double absDiff = Math.abs(currentPosition - target);

        if (autoPosition) {
            /* 
            if (Math.abs(currentPosition - target) < 5)
            {
                targetSpeed = -0.2;
            }
            */

            if (currentPosition < target && absDiff > 0.5)
            {
                armLeader.set(targetSpeed);
                armFollower.set(targetSpeed);
            } 
            else if (currentPosition > target && absDiff > 0.5)
            {
                armLeader.set(targetSpeed * -1.0);
                armFollower.set(targetSpeed * -1.0);
            }
            else 
            {
                armLeader.set(0);
                armFollower.set(0);
            }
        }
    }

    public double getPosition ()
    {
        return encoder.getPosition();
    }

    // Set target for PID control. This target value maps to an encoder
    // position.
    public void setTarget(double target)
    {
       autoPosition = true;
       this.target = target; 
    }

    public void stop()
    {
        armLeader.setVoltage(0);
        armFollower.setVoltage(0);
    }

    public void moveToShootPosition()
    {
        setTarget(frc.robot.Constants.ArmConstants.SHOOTING_POSITION);
    }

    public void moveUp()
    {
        if(isInUpperBound()) {    
            armLeader.set(0.1);
            armFollower.set(0.1);
            
        }
        else{
            armLeader.set(0);
            armFollower.set(0);
        }
        autoPosition = false;
    }

    public void moveDown()
    {   
        if(isInLowerBound()) {
            armLeader.set(-0.1);
            armFollower.set(-0.1);
            
        }
        else{
            armLeader.set(0);
            armFollower.set(0);
        }
        autoPosition = false;
    }

    public boolean isInUpperBound() {
        
        return encoder.getPosition() < frc.robot.Constants.ArmConstants.MAX_BOUND;

    }

    public boolean isInLowerBound() {
        return encoder.getPosition() > frc.robot.Constants.ArmConstants.MIN_BOUND;
    }

    /* 
    @Override
    protected void useOutput(double output, double setpoint) {
        // Use voltage from PID controller
        armLeader.setVoltage(output);
    }

    @Override
    protected double getMeasurement() {
        // Return position measurement from encoder. We're not 
        // currently doing any calculations to change the units.
        return encoder.getPosition();
    }
    */
}

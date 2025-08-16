package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.ArmSubsystem;

public class ShootingPositionCommand extends Command {
        
    public enum ArmAngle {
        SourceAngle,
        ShootAngle
    }

    private ArmSubsystem armSubsystem;
    private ArmAngle armAngle;

    public ShootingPositionCommand (ArmSubsystem armSubsystem, ArmAngle armAngle)
    {
        this.armSubsystem = armSubsystem;
        this.armAngle = armAngle;
        addRequirements(armSubsystem);
    }

    @Override
    public void execute()
    {

        if (armAngle == ArmAngle.ShootAngle)
        {
            armSubsystem.setTarget(Constants.ArmConstants.SHOOTING_POSITION);
        } else if (armAngle == ArmAngle.SourceAngle)
        {
            armSubsystem.setTarget(Constants.ArmConstants.SOURCE_POSITION);
        }
    }

    @Override
    public void end(boolean isInterrupted)
    {
        armSubsystem.setTarget(0);
    }
}

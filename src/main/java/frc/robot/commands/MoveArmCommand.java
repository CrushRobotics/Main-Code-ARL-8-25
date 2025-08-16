package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ArmSubsystem;

public class MoveArmCommand extends Command {
    public static enum ArmDirection  {
        Up,
        Down
    }

    private final ArmSubsystem armSubsystem;
    private ArmDirection direction;

    public MoveArmCommand (ArmSubsystem armSubsystem, ArmDirection direction)
    {
        this.direction = direction;
        this.armSubsystem = armSubsystem;
        addRequirements(armSubsystem);
    }

    @Override
    public void execute()
    {
        if (direction == ArmDirection.Up){
            armSubsystem.moveUp();
        } else {
            armSubsystem.moveDown();
        }
    }

    @Override
    public void end (boolean isInterrupted)
    {
        armSubsystem.stop();
    }
    
}

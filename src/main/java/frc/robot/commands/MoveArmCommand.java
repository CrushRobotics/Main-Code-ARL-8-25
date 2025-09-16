package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANArmSubsystem;

public class MoveArmCommand extends Command {
    public static enum ArmDirection  {
        Up,
        Down
    }

    private final CANArmSubsystem armSubsystem;
    private ArmDirection direction;

    public MoveArmCommand (CANArmSubsystem armSubsystem, ArmDirection direction)
    {
        this.direction = direction;
        this.armSubsystem = armSubsystem;
        addRequirements(armSubsystem);
    }

    @Override
    public void execute()
    {
        if (direction == ArmDirection.Up){
            armSubsystem.left();
        } else {
            armSubsystem.right();
        }
    }

    @Override
    public void end (boolean isInterrupted)
    {
        armSubsystem.stop();
    }
    
}

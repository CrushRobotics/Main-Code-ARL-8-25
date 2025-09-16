package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANCoralIntakeSubsystem;

public class CoralIntakeCommand extends Command {
    public static enum CoralIntakeDirection  {
        Up,
        Down
    }

    private final CANCoralIntakeSubsystem CoralIntakeSubsystem;
    private CoralIntakeDirection direction;

    public CoralIntakeCommand (CANCoralIntakeSubsystem CoralIntakeSubsystem, CoralIntakeDirection direction)
    {
        this.direction = direction;
        this.CoralIntakeSubsystem = CoralIntakeSubsystem;
        addRequirements(CoralIntakeSubsystem);
    }

    @Override
    public void execute()
    {
        if (direction == CoralIntakeDirection.Up){
           CoralIntakeSubsystem.left();
        } else {
            CoralIntakeSubsystem.right();
        }
    }

    @Override
    public void end (boolean isInterrupted)
    {
        CoralIntakeSubsystem.stop();
    }
  }
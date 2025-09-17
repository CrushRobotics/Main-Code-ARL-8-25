package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANAlgaeIntakeSubsystem;

public class AlgaeIntakeCommand extends Command {
    public static enum AlgaeIntakeDirection  {
        Up,
        Down,
        nuetral;
    }

    private final CANAlgaeIntakeSubsystem algaeIntakeSubsystem;
    private AlgaeIntakeDirection direction;

    public AlgaeIntakeCommand (CANAlgaeIntakeSubsystem AlgaeIntakeSubsystem, AlgaeIntakeDirection direction)
    {
        this.direction = direction;
        this.algaeIntakeSubsystem = AlgaeIntakeSubsystem;
        addRequirements(AlgaeIntakeSubsystem);
    }

    @Override
    public void execute()
    {
        if (direction == AlgaeIntakeDirection.Up){
          algaeIntakeSubsystem.left();
        } else if(direction == AlgaeIntakeDirection.Down){
          algaeIntakeSubsystem.right();
        } else {
          algaeIntakeSubsystem.stop();
        }
    }

    @Override
    public void end (boolean isInterrupted)
    {
      algaeIntakeSubsystem.stop();
    }
  }

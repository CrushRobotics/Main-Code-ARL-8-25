package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.CANElevatorSubsystem;


public class ElevatorCommand extends Command {
    public static enum ElevatorDirection {
        Up,
        Down
    }
    
    private final CANElevatorSubsystem elevatorSubsystem;
    private ElevatorDirection direction;

    public ElevatorCommand(CANElevatorSubsystem elevatorSubsystem, ElevatorDirection direction) {
        this.elevatorSubsystem = elevatorSubsystem;
        this.direction = direction;

        addRequirements(this.elevatorSubsystem);
    }

    @Override
    public void execute() {

        // switch (direction) {
           // case Up:
            //elevatorSubsystem.setMotorPostion(8); /// DETERMINE ENCODER VALUES
              //  break;
            //case Down:
            //elevatorSubsystem.setMotorPostion(0.2);
            //default:

              //  break;
        // }
         if (direction == ElevatorDirection.Up) {
             elevatorSubsystem.raise();
         } else {
             elevatorSubsystem.lower();
         }
    }

    @Override
    public void end (boolean isInterrupted) 
    {
        elevatorSubsystem.stop();
    }
    
}

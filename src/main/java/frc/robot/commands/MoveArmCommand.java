package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
<<<<<<< HEAD
import frc.robot.subsystems.CANArmSubsystem;
=======
import frc.robot.subsystems.ArmSubsystem;
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235

public class MoveArmCommand extends Command {
    public static enum ArmDirection  {
        Up,
        Down
    }

<<<<<<< HEAD
    private final CANArmSubsystem armSubsystem;
    private ArmDirection direction;

    public MoveArmCommand (CANArmSubsystem armSubsystem, ArmDirection direction)
=======
    private final ArmSubsystem armSubsystem;
    private ArmDirection direction;

    public MoveArmCommand (ArmSubsystem armSubsystem, ArmDirection direction)
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235
    {
        this.direction = direction;
        this.armSubsystem = armSubsystem;
        addRequirements(armSubsystem);
    }

    @Override
    public void execute()
    {
        if (direction == ArmDirection.Up){
<<<<<<< HEAD
            armSubsystem.left();
        } else {
            armSubsystem.right();
=======
            armSubsystem.moveUp();
        } else {
            armSubsystem.moveDown();
>>>>>>> f1e26b2868ddc2c2395ab53f1a100b5108855235
        }
    }

    @Override
    public void end (boolean isInterrupted)
    {
        armSubsystem.stop();
    }
    
}

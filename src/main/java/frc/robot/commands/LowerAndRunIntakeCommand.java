package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class LowerAndRunIntakeCommand extends Command {
    private IntakeSubsystem intakeSubsystem;

    public LowerAndRunIntakeCommand (IntakeSubsystem intakeSubsystem)
    {
        addRequirements(intakeSubsystem);
        this.intakeSubsystem = intakeSubsystem;
    }


    @Override
    public void execute()
    {
        intakeSubsystem.lowerIntake();
    }
    
    @Override
    public void end(boolean interrupted)
    {
        intakeSubsystem.raiseIntake();
    }
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class ToggleIntakeCommand extends Command {
    private IntakeSubsystem intakeSubsystem;

    public ToggleIntakeCommand(IntakeSubsystem intakeSubsystem)
    {
        this.intakeSubsystem = intakeSubsystem;
        addRequirements(intakeSubsystem);
    }

    @Override
    public void execute()
    {
        intakeSubsystem.toggleIntake();
    }
}

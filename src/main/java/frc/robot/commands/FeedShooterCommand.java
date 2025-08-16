package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

public class FeedShooterCommand extends Command {
    private ShooterSubsystem shooterSubsystem;

    public FeedShooterCommand(ShooterSubsystem shooterSubsystem)
    {
        addRequirements(shooterSubsystem);
        
        this.shooterSubsystem = shooterSubsystem;
    }

    @Override
    public void execute()
    {
        //intakeSubsystem.intTakeOn();
        shooterSubsystem.runIntake();
    }

    @Override
    public void end(boolean isInterrupted) {

        shooterSubsystem.stop();
    }

    @Override
    public boolean isFinished()
    {
        return false;
        //return shooterSubsystem.hasRing();
    }
}

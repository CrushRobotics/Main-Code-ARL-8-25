package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends Command {
    private ShooterSubsystem shooterSubsystem;

    public ShootCommand (ShooterSubsystem shooterSubsystem)
    {
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void execute() {

        shooterSubsystem.shoot();

    }

    @Override
    public void end(boolean isInterrupted) {
        shooterSubsystem.stop();
    }
}

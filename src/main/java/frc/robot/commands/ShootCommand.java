package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends Command {
    private final ShooterSubsystem shooterSubsystem;

    public ShootCommand(ShooterSubsystem shooterSubsystem) {
        this.shooterSubsystem = shooterSubsystem;
        addRequirements(shooterSubsystem);
    }

    @Override
    public void initialize() {
        // Start shooting
        shooterSubsystem.shoot();
    }

    @Override
    public void execute() {
        // Keep shooting
        shooterSubsystem.shoot();
    }

    @Override
    public void end(boolean interrupted) {
        // Stop shooting when command ends
        shooterSubsystem.stop();
    }

    @Override
    public boolean isFinished() {
        // This command runs until interrupted or timed out
        return false;
    }
}
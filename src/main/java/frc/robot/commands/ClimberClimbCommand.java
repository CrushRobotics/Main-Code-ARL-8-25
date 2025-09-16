package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberClimbCommand extends Command {
    
    private ClimberSubsystem climberSubsystem;
    
    public ClimberClimbCommand(ClimberSubsystem climberSubsystem) {
        
        this.climberSubsystem = climberSubsystem;
        addRequirements(climberSubsystem);

    }

    @Override
    public void execute() {
        
        climberSubsystem.Climb();
    }

    @Override
    public void end(boolean isInterrupted) {
        climberSubsystem.stop();
    }
}
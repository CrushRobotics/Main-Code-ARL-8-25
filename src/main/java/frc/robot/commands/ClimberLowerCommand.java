package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ClimberSubsystem;

public class ClimberLowerCommand extends Command {
    
    private ClimberSubsystem climberSubsystem;
    
    public ClimberLowerCommand(ClimberSubsystem climberSubsystem) {
        
        this.climberSubsystem = climberSubsystem;
        addRequirements(climberSubsystem);

    }

    @Override
    public void execute() {
        
        climberSubsystem.Lower();
    }

    @Override
    public void end(boolean isInterrupted) {
        climberSubsystem.stop();
    }
}

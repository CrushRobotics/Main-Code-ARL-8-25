package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;

public class BlinkCommand extends Command {

    private boolean isBlinking;

    public BlinkCommand()
    {
        isBlinking = false;
    }

    @Override
    public void execute() {
        super.execute();

        
        if (isBlinking)
        {
            LimelightHelpers.setLEDMode_ForceOff("");
            isBlinking = false;
        }
        else 
        {
            LimelightHelpers.setLEDMode_ForceBlink("");
            isBlinking = true;
        }
        
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    
    
}

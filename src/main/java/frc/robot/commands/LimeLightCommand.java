package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;

public class LimeLightCommand extends Command{

    @Override
    public void end(boolean interrupted) {
        LimelightHelpers.setLEDMode_ForceOff("");
    }

    @Override
    public void execute() {
        LimelightHelpers.setLEDMode_ForceBlink("");

    }
    
}

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
//import frc.robot.subsystems.LimelightSubsystem;
import frc.robot.LimelightHelpers;
import frc.robot.subsystems.DriveSubsystem;

public class LimelightCommand extends Command {
    
    final private DriveSubsystem driveSubsystem;
    final CommandXboxController controller;
    //boolean limelightMode = false;
    boolean button;

    public LimelightCommand(CommandXboxController controller, DriveSubsystem driveSubsystem) {
        this.controller = controller;
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(driveSubsystem);
    }

    @Override
    public void execute() {
        
        boolean hasTargets = LimelightHelpers.getTV("");
        double area = LimelightHelpers.getTA("");
        double tx = LimelightHelpers.getTX("");
        double ty = LimelightHelpers.getTY("");
        
        if (hasTargets == true) {
            if (area < 10){
                driveSubsystem.drive(.1, .1);
            }
            else {
                driveSubsystem.drive(0, 0);
            }
        }
    }


}

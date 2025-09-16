package frc.robot.subsystems;

import edu.wpi.first.units.measure.Distance;
import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.LEDPattern;
import edu.wpi.first.wpilibj.util.Color;
import static edu.wpi.first.units.Units.Meters;
import frc.robot.LimelightHelpers;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {
    private AddressableLED m_led;
    private AddressableLEDBuffer m_ledBuffer;
    double limeLightPerc;
    private LEDPattern gradient;
    private LEDPattern progressBar;
    // private Distance kLedSpacing;
    
    public LEDSubsystem() {
        limeLightPerc = LimelightHelpers.getTA("");
        gradient = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kCyan, Color.kOrange);
        progressBar = gradient.mask(LEDPattern.progressMaskLayer(()-> limeLightPerc));
        // Create an LED pattern that will display a rainbow across
    
    }

    @Override
    public void periodic() {
        // Update the buffer with the rainbow animation
        //m_scrollingRainbow.applyTo(m_ledBuffer);
        progressBar.applyTo(m_ledBuffer);
        // Set the LEDs
        m_led.setData(m_ledBuffer);
    }
}

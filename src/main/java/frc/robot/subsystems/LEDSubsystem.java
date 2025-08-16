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
    private Distance kLedSpacing;
    
    public LEDSubsystem() {
        limeLightPerc = LimelightHelpers.getTA("");
        gradient = LEDPattern.gradient(LEDPattern.GradientType.kContinuous, Color.kCyan, Color.kOrange);
        progressBar = gradient.mask(LEDPattern.progressMaskLayer(()-> limeLightPerc));
        // Create an LED pattern that will display a rainbow across
        // all hues at maximum saturation and half brightness
        // private final LEDPattern m_rainbow = LEDPattern.gradient(LEDPattern.GradientType.kDiscontinuous, Color.kDarkOrange, Color.kBlue);

        // Our LED strip has a density of 120 LEDs per meter
        kLedSpacing = Meters.of(1 / 120.0);

        // Create a new pattern that scrolls the rainbow pattern across the LED strip, moving at a speed
        // of 1 meter per second.
        // private final LEDPattern m_scrollingRainbow =
            // m_rainbow.scrollAtAbsoluteSpeed(MetersPerSecond.of(.4), kLedSpacing);
        // .4 
        /** Called once at the beginning of the robot program. */
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

package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class LimelightConfig {
    private Limelight3A limelight;

    public LimelightConfig(HardwareMap hardwareMap) {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(6); // Set pipeline (adjust as needed)
        limelight.start();
    }

    public double getDetectedAngle() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.getPythonOutput().length > 5) {
            return result.getPythonOutput()[5]; // Extract angle
        }
        return Double.NaN; // Return NaN if no valid detection
    }

    public void stop() {
        limelight.stop();
    }
}

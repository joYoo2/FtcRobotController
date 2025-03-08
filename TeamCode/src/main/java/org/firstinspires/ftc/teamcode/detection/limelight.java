package org.firstinspires.ftc.teamcode.detection;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import java.util.List;

@TeleOp(name="limelight test", group="yooniverse")
public class limelight extends LinearOpMode {
    private Limelight3A limelight;

    @Override
    public void runOpMode() throws InterruptedException{
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.setPollRateHz(100);
        limelight.pipelineSwitch(0);

        limelight.start();

        while(opModeIsActive()){
            LLResult result = limelight.getLatestResult();
            List<LLResultTypes.DetectorResult> detections = result.getDetectorResults();

            if(result != null){
                if(result.isValid()){
                    telemetry.addData("tx", result.getTx());
                    telemetry.addData("ty", result.getTy());

                    List<List<Double>> corners = detections.get(0).getTargetCorners();
                    double dx = corners.get(1).get(0) - corners.get(0).get(0);
                    double dy = corners.get(1).get(1) - corners.get(0).get(1);
                    telemetry.addData("angle", Math.toDegrees(Math.atan2(dy, dx)));
                }
            }
            telemetry.update();
        }
    }
}

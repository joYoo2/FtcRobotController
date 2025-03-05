//package org.firstinspires.ftc.teamcode.detection;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//
////@TeleOp(name = "Camera Test", group = "Concept")
//public class cameratest extends LinearOpMode {
//    private OpenCvCamera webcam;
//    private indubitably pipeline;
//
//    @Override
//    public void runOpMode() {
//        // Get camera ID
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName()
//        );
//
//        // Initialize webcam
//        webcam = OpenCvCameraFactory.getInstance().createWebcam(
//                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId
//        );
//
//        // Open and start streaming
//        webcam.openCameraDevice();
//        webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPRIGHT);
//
//
//        // Initialize pipeline and pass telemetry
//        pipeline = new indubitably(telemetry);
//        webcam.setPipeline(pipeline);
//
//
//
//
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            // Display detected angle
//            telemetry.addData("Detected Angle", pipeline.getSelectedStoneDegrees());
//            telemetry.update();
//        }
//
//        // Stop streaming on exit
//        webcam.stopStreaming();
//    }
//}

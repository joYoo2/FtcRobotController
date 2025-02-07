package org.firstinspires.ftc.teamcode.yooniverse;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.*;

import java.util.ArrayList;
import java.util.List;

public class camera {
    private OpenCvCamera webcam;
    private static final int CAMERA_WIDTH = 1280;
    private static final int CAMERA_HEIGHT = 720;
    private Mat latestFrame = new Mat();

    private Servo clawRotateServo;
    private static final double oneDegree = 0.0039; // Servo step per degree

    public camera(HardwareMap hardwareMap){
        clawRotateServo = hardwareMap.get(Servo.class, "clawRotateServo");
        //initOpenCV();

        FtcDashboard dashboard = FtcDashboard.getInstance();
        FtcDashboard.getInstance().startCameraStream(webcam, 10);
    }


    public double getAngle() {
        MatOfPoint largestContour = getLargestContour();
        if (largestContour != null) {
            double detectedAngle = getAngle(largestContour);
            return(0.5 - detectedAngle * oneDegree);
        }
        webcam.stopStreaming();
        return(0.5);
    }

    private void initOpenCV(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
        webcam.setPipeline(new BlueBlobDetectionPipeline());
        webcam.openCameraDevice();
        webcam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
    }

    class BlueBlobDetectionPipeline extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            latestFrame = input.clone();
            Mat BlueMask = preprocessFrame(input);

            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(BlueMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            return input;
        }

        private Mat preprocessFrame(Mat frame) {
            Mat hsvFrame = new Mat();
            Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);
            Scalar lowerBlue = new Scalar(0, 100, 100);
            Scalar upperBlue = new Scalar(50, 255, 255);
            Mat BlueMask = new Mat();
            Core.inRange(hsvFrame, lowerBlue, upperBlue, BlueMask);
            return BlueMask;
        }
    }

    private MatOfPoint getLargestContour() {
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        if (latestFrame.empty()) {
            return null;
        }
        Mat processedFrame = new Mat();
        Imgproc.cvtColor(latestFrame, processedFrame, Imgproc.COLOR_BGR2HSV);
        Scalar lowerBlue = new Scalar(0, 100, 100);
        Scalar upperBlue = new Scalar(50, 255, 255);
        Mat BlueMask = new Mat();
        Core.inRange(processedFrame, lowerBlue, upperBlue, BlueMask);
        Imgproc.findContours(BlueMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        return findLargestContour(contours);
    }

    private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
        double maxArea = 0;
        MatOfPoint largestContour = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largestContour = contour;
            }
        }
        return largestContour;
    }

    public double getAngle(MatOfPoint largestContour) {
        if (largestContour == null || largestContour.toArray().length == 0) {
            return Double.NaN;
        }
        RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(largestContour.toArray()));
        double angle = rotatedRect.angle;
        if (rotatedRect.size.width < rotatedRect.size.height) {
            angle += 90;
        }
        return angle - 90;
    }
}

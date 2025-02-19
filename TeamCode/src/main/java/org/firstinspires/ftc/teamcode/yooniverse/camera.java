package org.firstinspires.ftc.teamcode.yooniverse;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.*;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;
import java.util.List;

public class camera {
    private Telemetry telemetry;
    private OpenCvCamera webcam;
    private boolean isCameraOpen = false; // Prevent multiple openings
    private static final int CAMERA_WIDTH = 1280;
    private static final int CAMERA_HEIGHT = 720;
    private Mat latestFrame = new Mat();
    private Servo clawRotateServo;
    private double lastDetectedAngle = 0;
    private static final double oneDegree = 0.0039;

    public camera(HardwareMap hardwareMap, Telemetry telemetry) {
        this.telemetry = telemetry;
        clawRotateServo = hardwareMap.get(Servo.class, "clawRotateServo");
        initOpenCV(hardwareMap);
    }

    private boolean isCameraReady = false; // New variable to track readiness

    private void initOpenCV(HardwareMap hardwareMap) {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(
                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);

        webcam.setPipeline(new BlueBlobDetectionPipeline());

        // Open the camera asynchronously and set isCameraReady when done
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                isCameraReady = true;
                webcam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {
                isCameraReady = false;
            }
        });
    }

    // New method to check if the camera is ready
    public boolean isCameraReady() {
        return isCameraReady;
    }


    public double getAngle() {
        if (webcam == null) return Double.NaN;

        if (!isCameraOpen) {
            webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
                @Override
                public void onOpened() {
                    isCameraOpen = true;
                    webcam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
                }
                @Override
                public void onError(int errorCode) {
                    isCameraOpen = false;
                }
            });
        }

        MatOfPoint largestContour = getLargestContour();
        double detectedAngle = getAngle(largestContour);

        stopCamera(); // Stop camera after processing

        if (largestContour != null) {
            if (detectedAngle == 0) {
                return 0.5 - lastDetectedAngle * oneDegree;
            } else {
                lastDetectedAngle = detectedAngle;
                return 0.5 - detectedAngle * oneDegree;
            }
        } else {
            return 0.5 - lastDetectedAngle * oneDegree;
        }
    }

    private void stopCamera() {
        if (isCameraOpen) {
            webcam.stopStreaming();
            webcam.closeCameraDevice();
            isCameraOpen = false;
        }
    }


    class BlueBlobDetectionPipeline extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            latestFrame = input.clone();
            Mat yellowMask = preprocessFrame(input);
            List<MatOfPoint> contours = new ArrayList<>();
            Mat hierarchy = new Mat();
            Imgproc.findContours(yellowMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
            return input;
        }

        private Mat preprocessFrame(Mat frame) {
            Mat hsvFrame = new Mat();
            Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);

            Scalar lowerYellow = new Scalar(50, 100, 100);
            Scalar upperYellow = new Scalar(150, 255, 255);

            Mat yellowMask = new Mat();
            Core.inRange(hsvFrame, lowerYellow, upperYellow, yellowMask);
            return yellowMask;
        }

    }

    private MatOfPoint getLargestContour() {
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        if (latestFrame.empty()) return null;

        Mat processedFrame = new Mat();
        Imgproc.cvtColor(latestFrame, processedFrame, Imgproc.COLOR_BGR2HSV);
        Scalar lowerYellow = new Scalar(50, 100, 100);
        Scalar upperYellow = new Scalar(150, 255, 255);

        Mat yellowMask = new Mat();
        Core.inRange(processedFrame, lowerYellow, upperYellow, yellowMask);
        Imgproc.findContours(yellowMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
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

    private double getAngle(MatOfPoint largestContour) {
        if (largestContour == null || largestContour.toArray().length == 0) {
            return Double.NaN;
        }

        RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(largestContour.toArray()));
        double angle = rotatedRect.angle;

        if (rotatedRect.size.width < rotatedRect.size.height) {
            angle += 90;
        }
        angle+=90;
        if (angle > 90) {
            angle -= 180;
        } else if (angle < -90) {
            angle += 180;
        }

        return angle;
    }

    class DebugPipeline extends OpenCvPipeline {
        @Override
        public Mat processFrame(Mat input) {
            Mat hsvFrame = new Mat();
            Imgproc.cvtColor(input, hsvFrame, Imgproc.COLOR_BGR2HSV);

            // Get HSV values from the center pixel
            int centerX = input.cols() / 2;
            int centerY = input.rows() / 2;
            double[] hsvValues = hsvFrame.get(centerY, centerX); // Get HSV values

            // Print HSV values to telemetry (now available)
            if (hsvValues != null) {
                telemetry.addData("Hue (H)", hsvValues[0]);
                telemetry.addData("Saturation (S)", hsvValues[1]);
                telemetry.addData("Value (V)", hsvValues[2]);
                telemetry.update();
            }

            return input;
        }
    }
}

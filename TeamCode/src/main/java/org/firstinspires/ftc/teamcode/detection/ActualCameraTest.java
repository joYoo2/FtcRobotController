//package org.firstinspires.ftc.teamcode.detection;
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfPoint;
//import org.opencv.core.MatOfPoint2f;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
//import org.opencv.core.RotatedRect;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.imgproc.Moments;
//import org.openftc.easyopencv.OpenCvCamera;
//import org.openftc.easyopencv.OpenCvCameraFactory;
//import org.openftc.easyopencv.OpenCvCameraRotation;
//import org.openftc.easyopencv.OpenCvPipeline;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@TeleOp(name = "ActualCameraTest", group = "Concept")
//public class ActualCameraTest extends LinearOpMode {
//    double cX = 0;
//    double cY = 0;
//
//    double width = 0;
//    double angle = 0;
//    private OpenCvCamera controlHubCam;
//    private static final int CAMERA_WIDTH = 1280;
//    private static final int CAMERA_HEIGHT = 720;
//
//    public static final double objectWidthInRealWorldUnits = 3.5;
//    public static final double focalLength = 800;
//
//    private Mat latestFrame = new Mat();
//
//    @Override
//    public void runOpMode() {
//        initOpenCV();
//        FtcDashboard dashboard = FtcDashboard.getInstance();
//        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
//        FtcDashboard.getInstance().startCameraStream(controlHubCam, 30);
//
//        // **PID Constants (Tuned for smoother motion)**
//        double Kp = 0.5;  // Reduce Proportional gain
//        double Ki = 0.0001; // Small integral to reduce steady-state error
//        double Kd = 0.002;  // Add derivative to slow down aggressive changes
//
//        double previousError = 0;
//        double integral = 0;
//        double maxIntegral = 15; // **Prevent integral windup**
//
//
//        waitForStart();
//
//        while (opModeIsActive()) {
//            MatOfPoint largestContour = getLargestContour(); // Get detected object
//
//            if (largestContour != null) {
//                double detectedAngle = -1.0*getAngle(largestContour);
//                double targetAngle = 0;  // Assume claw should align with 90 degrees
//                double error = targetAngle - detectedAngle;
//
//                // **PID Calculation**
//                integral += error;
//                integral = Math.max(-maxIntegral, Math.min(maxIntegral, integral)); // **Clamp integral term**
//                double derivative = error - previousError;
//                previousError = error;
//
//                double adjustment = (Kp * error);
//
//                // **Convert to servo position**
//                double oneDegree = 0.0039;
//
//
//                telemetry.addData("Object Angle", detectedAngle);
//
//                telemetry.addData("Error", error);
//                telemetry.addData("adjustment", adjustment);
//                telemetry.update();
//            } else {
//                telemetry.addData("Status", "No object detected, holding position.");
//                telemetry.update();
//            }
//        }
//
//        controlHubCam.stopStreaming();
//    }
//
//
//    private void initOpenCV() {
//        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
//                "cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
//
//        controlHubCam = OpenCvCameraFactory.getInstance().createWebcam(
//                hardwareMap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);
//
//        controlHubCam.setPipeline(new RedBlobDetectionPipeline());
//        controlHubCam.openCameraDevice();
//        controlHubCam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
//    }
//
//    class RedBlobDetectionPipeline extends OpenCvPipeline {
//        @Override
//        public Mat processFrame(Mat input) {
//            latestFrame = input.clone();
//            Mat BlueMask = preprocessFrame(input);
//
//            List<MatOfPoint> contours = new ArrayList<>();
//            Mat hierarchy = new Mat();
//            Imgproc.findContours(BlueMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//
//            MatOfPoint largestContour = findLargestContour(contours);
//            if (largestContour != null) {
//                Imgproc.drawContours(input, contours, contours.indexOf(largestContour), new Scalar(0, 255, 255), 2);
//                width = calculateWidth(largestContour);
//
//                // Display info on screen
//                String distanceLabel = "Distance: " + String.format("%.2f", getDistance(width)) + " inches";
//                Imgproc.putText(input, distanceLabel, new Point(cX + 10, cY + 40), Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 2);
//
//                Moments moments = Imgproc.moments(largestContour);
//                cX = moments.get_m10() / moments.get_m00();
//                cY = moments.get_m01() / moments.get_m00();
//                Imgproc.circle(input, new Point(cX, cY), 5, new Scalar(0, 255, 0), -1);
//            }
//
//            return input;
//        }
//
//        private Mat preprocessFrame(Mat frame) {
//            Mat hsvFrame = new Mat();
//            Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);
//
//            Scalar lowerBlue = new Scalar(0, 100, 100);
//            Scalar upperBlue = new Scalar(50, 255, 255);
//
//            Mat BlueMask = new Mat();
//            Core.inRange(hsvFrame, lowerBlue, upperBlue, BlueMask);
//
//            Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));
//            Imgproc.morphologyEx(BlueMask, BlueMask, Imgproc.MORPH_OPEN, kernel);
//            Imgproc.morphologyEx(BlueMask, BlueMask, Imgproc.MORPH_CLOSE, kernel);
//
//            return BlueMask;
//        }
//    }
//
//    private MatOfPoint getLargestContour() {
//        List<MatOfPoint> contours = new ArrayList<>();
//        Mat hierarchy = new Mat();
//
//        if (latestFrame.empty()) {
//            return null;  // No valid frame yet
//        }
//
//        Mat processedFrame = new Mat();
//        Imgproc.cvtColor(latestFrame, processedFrame, Imgproc.COLOR_BGR2HSV);
//
//        Scalar lowerBlue = new Scalar(0, 100, 100);
//        Scalar upperBlue = new Scalar(50, 255, 255);
//        Mat BlueMask = new Mat();
//        Core.inRange(processedFrame, lowerBlue, upperBlue, BlueMask);
//
//        Imgproc.findContours(BlueMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//        return findLargestContour(contours);
//    }
//
//    private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
//        double maxArea = 0;
//        MatOfPoint largestContour = null;
//
//        for (MatOfPoint contour : contours) {
//            double area = Imgproc.contourArea(contour);
//            if (area > maxArea) {
//                maxArea = area;
//                largestContour = contour;
//            }
//        }
//
//        return largestContour;
//    }
//
//    private double calculateWidth(MatOfPoint contour) {
//        Rect boundingRect = Imgproc.boundingRect(contour);
//        return boundingRect.width;
//    }
//
//    public static double getDistance(double width) {
//        return (objectWidthInRealWorldUnits * focalLength) / width;
//    }
//
//    public double getAngle(MatOfPoint largestContour) {
//        if (largestContour == null || largestContour.toArray().length == 0) {
//            return Double.NaN;
//        }
//
//        RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(largestContour.toArray()));
//        double angle = rotatedRect.angle;
//
//        if (rotatedRect.size.width < rotatedRect.size.height) {
//            angle += 90;
//        }
//
//        return angle - 90;
//    }
//}

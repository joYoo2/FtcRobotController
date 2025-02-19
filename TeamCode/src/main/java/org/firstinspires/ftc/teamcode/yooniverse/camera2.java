/*
package org.firstinspires.ftc.teamcode.yooniverse;

import android.util.Size;
import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.openftc.easyopencv.OpenCvPipeline;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import java.util.ArrayList;
import java.util.List;

public class camera2 {
    private VisionPortal visionPortal;
    private BlueBlobDetectionPipeline pipeline;

    private double lastDetectedAngle = 0;
    private static final double oneDegree = 0.0039; // Servo step per degree

    public camera2(HardwareMap hardwareMap) {
        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");

        pipeline = new BlueBlobDetectionPipeline();

        // ✅ Create VisionPortal with OpenCV Pipeline using `setProcessor()`
        visionPortal = new VisionPortal.Builder()
                .setCamera(webcamName)
                .setCameraResolution(new Size(1280, 720)) // ✅ Corrected
                .setStreamFormat(VisionPortal.StreamFormat.MJPEG) // Force MJPEG
                .enableLiveView(true)
                .build();

        // ✅ Attach the OpenCV pipeline to the VisionPortal
        visionPortal.setProcessorEnabled(pipeline,true); // ✅ This works with OpenCV pipelines!

        // ✅ Stream to FTC Dashboard
        FtcDashboard.getInstance().startCameraStream(visionPortal, 30);
    }

    public double getAngle() {
        MatOfPoint largestContour = pipeline.getLargestContour();
        double detectedAngle = calculateAngle(largestContour);

        if (largestContour != null) {
            lastDetectedAngle = detectedAngle;
            return 0.5 - detectedAngle * oneDegree;
        } else {
            return 0.5 - lastDetectedAngle * oneDegree;
        }
    }

    public void stopCamera() {
        if (visionPortal != null) {
            visionPortal.close();
        }
    }

    private double calculateAngle(MatOfPoint largestContour) {
        if (largestContour == null || largestContour.toArray().length == 0) {
            return Double.NaN;
        }

        RotatedRect rotatedRect = Imgproc.minAreaRect(new MatOfPoint2f(largestContour.toArray()));
        double angle = rotatedRect.angle;

        if (rotatedRect.size.width < rotatedRect.size.height) {
            angle += 90;
        }
        if (angle > 90) {
            angle -= 180;
        } else if (angle < -90) {
            angle += 180;
        }

        return angle;
    }
}
*/
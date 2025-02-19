package org.firstinspires.ftc.teamcode.yooniverse;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;
import java.util.ArrayList;
import java.util.List;

public class BlueBlobDetectionPipeline extends OpenCvPipeline {
    private Mat latestFrame = new Mat();
    private MatOfPoint largestContour = null;

    @Override
    public Mat processFrame(Mat input) {
        latestFrame = input.clone();
        Mat blueMask = preprocessFrame(input);

        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        Imgproc.findContours(blueMask, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        largestContour = findLargestContour(contours);
        return input; // ✅ Return unchanged frame for visualization
    }

    private Mat preprocessFrame(Mat frame) {
        Mat hsvFrame = new Mat();
        Imgproc.cvtColor(frame, hsvFrame, Imgproc.COLOR_BGR2HSV);

        // ✅ Define blue color range
        Scalar lowerBlue = new Scalar(100, 100, 50);  // Adjusted for better detection
        Scalar upperBlue = new Scalar(140, 255, 255); // Adjusted for better detection

        Mat blueMask = new Mat();
        Core.inRange(hsvFrame, lowerBlue, upperBlue, blueMask);
        return blueMask;
    }

    private MatOfPoint findLargestContour(List<MatOfPoint> contours) {
        double maxArea = 0;
        MatOfPoint largest = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largest = contour;
            }
        }
        return largest;
    }

    /** ✅ Get the largest detected blue contour */
    public MatOfPoint getLargestContour() {
        return largestContour;
    }
}

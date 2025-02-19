
package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.yooniverse.camera;

import org.firstinspires.ftc.teamcode.yooniverse.crane;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;
@TeleOp(name="TESTER", group="yooniverse")
public class TESTER extends LinearOpMode {
    private DcMotor frontLeft, frontRight, backRight, backLeft;
    public DcMotorEx leftDrawerSlide, rightDrawerSlide;
    private Servo clawRotateServo;
    double detectedAngle = 0.5;



    @Override
    public void runOpMode() {
        boolean pressed = false;

        clawRotateServo = hardwareMap.get(Servo.class, "clawRotateServo");

        camera camera = new camera(hardwareMap, telemetry); // Initialize the camera

// Ensure the camera is ready before starting
        while (!isStopRequested() && !camera.isCameraReady()) {
            telemetry.addData("Status", "Waiting for camera...");
            telemetry.update();
            sleep(50);
        }

        telemetry.addData("Status", "Camera Ready! Press A to detect angle.");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.triangle && !pressed) { // Detect new press
                pressed = true; // Lock input until button is released

                detectedAngle = camera.getAngle(); // Capture the camera angle

                telemetry.addData("Detected Angle", detectedAngle);
            } else if (!gamepad1.triangle) { // Reset the pressed flag once button is released
                pressed = false;
            }
            clawRotateServo.setPosition(detectedAngle);
            telemetry.update();

        }


    }
}


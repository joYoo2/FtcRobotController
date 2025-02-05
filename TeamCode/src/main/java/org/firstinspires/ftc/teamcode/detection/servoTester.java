package org.firstinspires.ftc.teamcode.detection;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
@TeleOp(name = "servoTester", group = "Concept")
public class servoTester extends LinearOpMode{
    Servo clawRotate;
    @Override

    public void runOpMode() {
        FtcDashboard dashboard = FtcDashboard.getInstance();
        telemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        clawRotate = hardwareMap.get(Servo.class, "clawRotateServo");

        waitForStart();
        while(opModeIsActive()) {
            telemetry.addData("positon", clawRotate.getPosition());
            telemetry.update();
        }
    }
}


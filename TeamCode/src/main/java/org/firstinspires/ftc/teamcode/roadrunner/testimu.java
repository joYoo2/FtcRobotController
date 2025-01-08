package org.firstinspires.ftc.teamcode.roadrunner;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.yooniverse.iAmYoo;


//@TeleOp(name="testimu", group="bruh")
//@Disabled
public class testimu extends LinearOpMode{
    @Override
    public void runOpMode() {
        iAmYoo imu = new iAmYoo(hardwareMap);

        waitForStart();
        while (opModeIsActive()) {
            telemetry.addData("yaw", imu.getYaw());
            telemetry.addData("pitch", imu.getPitch());
            telemetry.addData("roll", imu.getRoll());
            telemetry.update();
        }
    }
    }

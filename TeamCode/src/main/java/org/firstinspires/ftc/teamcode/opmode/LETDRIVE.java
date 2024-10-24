package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;

public class LETDRIVE extends yooniversalOpMode{
    @Override
    public void runOpMode(){
        setup();
        telemetry.addData("status", "initialized");
        telemetry.update();

        train.setPower(1);

        setOpModeType(2);

        telemetry.update();


        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("Status", "Running");

            train.manualDrive(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x,
                    -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                    -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x,
                    -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);

        }

    }
}

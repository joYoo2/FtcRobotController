package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;
@TeleOp(name="TESTER", group="yooniverse")
public class TESTER extends LinearOpMode {
    private DcMotor frontLeft, frontRight, backRight, backLeft;
    @Override
    public void runOpMode(){

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");

        backRight = hardwareMap.get(DcMotor.class, "backRight");

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("frontRight", frontRight.getCurrentPosition());
        telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
        telemetry.addData("backRight", backRight.getCurrentPosition());
        telemetry.addData("backLeft", backLeft.getCurrentPosition());
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("frontRight", frontRight.getCurrentPosition());
            telemetry.addData("frontLeft", frontLeft.getCurrentPosition());
            telemetry.addData("backRight", backRight.getCurrentPosition());
            telemetry.addData("backLeft", backLeft.getCurrentPosition());
            telemetry.update();
        }


    }


}

package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.teamcode.yooniverse.crane;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;
@TeleOp(name="TESTER", group="yooniverse")
public class TESTER extends LinearOpMode {
    private DcMotor frontLeft, frontRight, backRight, backLeft;
    public DcMotorEx leftDrawerSlide, rightDrawerSlide;
    @Override
    public void runOpMode(){

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");

        backRight = hardwareMap.get(DcMotor.class, "backRight");


        backLeft = hardwareMap.get(DcMotor.class, "backLeft");

        leftDrawerSlide = hardwareMap.get(DcMotorEx.class, "leftDrawerSlide");
        rightDrawerSlide = hardwareMap.get(DcMotorEx.class, "rightDrawerSlide");
        rightDrawerSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        rightDrawerSlide.setDirection((DcMotorEx.Direction.REVERSE));

        leftDrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addData("left", leftDrawerSlide.getCurrentPosition());
        telemetry.addData("right", rightDrawerSlide.getCurrentPosition());
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("left", leftDrawerSlide.getCurrentPosition());
            telemetry.addData("right", rightDrawerSlide.getCurrentPosition());
            telemetry.update();
            if(gamepad1.left_bumper){
                leftDrawerSlide.setPower(1);
            }

        }


    }



}


package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;
@TeleOp(name="TESTER", group="yooniverse")
public class TESTER extends LinearOpMode {
    private DcMotor leftDrawerSlide, rightDrawerSlide;
    @Override
    public void runOpMode(){
        leftDrawerSlide = hardwareMap.get(DcMotor.class, "leftDrawerSlide");

        rightDrawerSlide = hardwareMap.get(DcMotor.class, "rightDrawerSlide");

        telemetry.addData("crane left", leftDrawerSlide.getCurrentPosition());
        telemetry.addData("crane right", rightDrawerSlide.getCurrentPosition());
        telemetry.update();
        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("crane left", leftDrawerSlide.getCurrentPosition());
            telemetry.addData("crane right", rightDrawerSlide.getCurrentPosition());
            telemetry.update();
        }


    }


}

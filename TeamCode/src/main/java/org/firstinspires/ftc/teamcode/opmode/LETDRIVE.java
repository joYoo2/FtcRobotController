package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;
@TeleOp(name="LETDRIVE", group="yooniverse")

public class LETDRIVE extends yooniversalOpMode{
    @Override
    public void runOpMode(){
        setup();
        telemetry.addData("status", "initialized");
        telemetry.update();

        train.setPower(1);



        telemetry.update();
        boolean byPower = false;
        boolean reverseDrive = false;
        boolean specimenTimer = false;
        boolean bumperPressed = false;

//        closeClaw();
//        clawUp();

        waitForStart();
//        extenderRight.setPosition(1-values.clawRetract);
//        extenderLeft.setPosition(values.clawRetract);
        ElapsedTime timer = new ElapsedTime();

        while(opModeIsActive()){
            telemetry.addData("Status", "Running");
            telemetry.addData("wheel amps", train.backLeft.getCurrent(CurrentUnit.AMPS));


            //reverse drive code
            if(gamepad1.dpad_up){
                reverseDrive = false;
            }else if(gamepad1.dpad_down){
                reverseDrive = true;
            }

            if (!reverseDrive){
                train.manualDrive(-gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x,
                        -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x,
                        -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                        -gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x);

            } else{
                train.manualDrive(gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                        gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x,
                        gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x,
                        gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x);

            }
            if(gamepad1.left_bumper){
                clawHover();
            }
            if(gamepad1.right_bumper){
                clawDown();
                timer.reset();
                bumperPressed = true;
            }
            if(timer.time() > .2 && timer.time() < .3 && !specimenTimer && bumperPressed){
                closeClaw();
                if(timer.time() > .25){
                    clawUp();
                }

            }

            if(gamepad1.triangle){
                clawUp();
            }
            if(gamepad1.cross){
                openClaw();
                clawDown();
            }
//            if(gamepad2.triangle){
//
//            }
            if(gamepad1.circle){
                openClaw();
            }
            if(gamepad1.square){
                closeClaw();
            }


            if(gamepad2.right_trigger > 0.05 || gamepad2.left_trigger > 0.05) {
                byPower = true;
                slides.move(gamepad2.right_trigger - gamepad2.left_trigger, true);
            }else if(gamepad2.right_bumper){
                slides.setTargetPosition(values.craneHighChamber);
            }else if(gamepad2.left_bumper){
                slides.setTargetPosition(values.craneHighBasket);
                //Code to automatically raise the slides after releasing the closeSpecimen button VVV
            }else if(timer.time() > 0.1 && specimenTimer){
                if(slides.getCurrentLeftPosition() > 350 || slides.getCurrentRightPosition() > 350){
                    specimenTimer = false;
                }
                slides.setTargetPosition(400);
            }else{
                slides.move(slides.getCurrentRightPosition(), false);
            }


            if(gamepad2.dpad_up){
                extendClaw();
            }
            if(gamepad2.dpad_down){
                retractClaw();
            }

            if(gamepad2.circle){
                specimenOpen();
            }

            if(gamepad2.square){
                specimenClose();
                timer.reset();
                specimenTimer = true;
            }





            slides.craneMaintenance();

            telemetry.addData("Left Crane Motor Position", slides.getCurrentLeftPosition());
            telemetry.addData("Right Crane Motor Position", slides.getCurrentRightPosition());
            telemetry.addData("left crane amps", slides.getAmpsLeft());
            telemetry.addData("right crane amps", slides.getAmpsRight());
            telemetry.update();
        }

    }
}

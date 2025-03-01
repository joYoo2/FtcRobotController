package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
//import org.firstinspires.ftc.teamcode.yooniverse.camera;
import org.firstinspires.ftc.teamcode.yooniverse.values;
@TeleOp(name="LETDRIVE", group="yooniverse")

public class LETDRIVE extends yooniversalOpMode {
    @Override
    public void runOpMode() {
        setup();
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        train.setPower(1);
        double detectedAngle = 0;
        //double cameraangle = 0;
        boolean emergencySlides = false;
        //camera camera = new camera(hardwareMap, telemetry);


        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        ElapsedTime transferTime = new ElapsedTime();
        ElapsedTime matchTime = new ElapsedTime();

        clawRotateServo.setPosition(0.5);
        transferDown();

        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");
            matchTime.startTime();


            //just in case code (can delete if not necessary ask miguel)
//            if(slides.getCurrentLeftPosition() > 2500 || slides.getCurrentRightPosition() > 2500){
//                train.setPower(0.5);
//            }else{
//                train.setPower(1);
//            }

            //reverse drive code

            if (gamepad1.left_trigger > 0.1) {
                testRotateBackward();
            } else if (gamepad1.right_trigger > 0.1) {
                testRotate();
            }

            train.manualDrive(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x,
                        -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                        -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x,
                        -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);


            if (gamepad1.triangle) {
                clawUp();
            }

            if (gamepad1.left_bumper) {
                //extendClaw();
                openClaw();
                clawHover();
            }

            if (gamepad1.right_bumper) {
                //retractClaw();
                closeClaw();
                timer.reset();
            }else if(timer.time() > .3 && timer.time() < .5) {
                clawUp();
                //retractClaw();
                clawRotateServo.setPosition(0.5);
            }
//            if (timer.time() > .2 && timer.time() < .35) {
////                    closeClaw();
//
//
//
//            }

            if (gamepad1.circle) {
                openClaw();
            }
            if (gamepad1.square) {
                closeClaw();
            }


            //TRANSFER
            if(gamepad1.cross || gamepad1.dpad_up){
                transferClawClose();
                transferTime.reset();
            }else if(transferTime.time() > .3 && transferTime.time() < .5){
                openClaw();
                transferUp();
                //highBasket();
            }

            if(gamepad1.dpad_down){
                transferClawOpen();
                transferDown();
                //slidesResting();
            }


            ///old slide code
//            if(gamepad2.right_trigger > 0.05 || gamepad2.left_trigger > 0.05) {
//                byPower = true;
//                slides.move(gamepad2.right_trigger - gamepad2.left_trigger, true);
//
//            }else if(gamepad2.right_bumper){
//                highChamberSpecimenClaw();
//
//            }else if(gamepad2.left_bumper){
//                highBasket();
//                clawVertical();
//
//            //Code to automatically raise the slides after releasing the closeSpecimen button VVV
//            }else if(timer.time() > 0.3 && specimenTimer){
//                if(slides.getCurrentLeftPosition() > 350 || slides.getCurrentRightPosition() > 350){
//                    specimenTimer = false;
//                }else{
//                    slides.setTargetPosition(400);
//                }
//
//            }else{
//                slides.move(slides.getCurrentRightPosition(), false);
//            }



//            if (gamepad2.dpad_up) {
//                extendClaw();
//            }
//            if (gamepad2.dpad_down) {
//                retractClaw();
//            }


/*
            if (gamepad2.triangle && !pressed) { // Detect new press
                pressed = true; // Lock input until button is released9oo8o

                detectedAngle = camera.getAngle(); // Capture the camera angle
                clawRotateServo.setPosition(detectedAngle);

            } else if (!gamepad2.triangle) { // Reset the pressed flag once button is released
                pressed = false;
            }

            telemetry.addData("Detected Angle", detectedAngle);
*/

            if (gamepad2.options) {
                slides.resetEncoders();
            } else if (gamepad2.share) {
                //EMERGENCY SLIDES DOWN
                emergencySlides = true;
                slides.setTargetPosition(-3000);
            } else if (!gamepad2.share && emergencySlides) {
                //resets encoders after emergency slides
                slides.resetEncoders();
                emergencySlides = false;
            }

            //rumble code
            //1 minute left
            if (matchTime.seconds() > 60 && matchTime.seconds() < 60.1) {
                gamepad1.rumbleBlips(1);
                gamepad2.rumbleBlips(1);
                //end game
            } else if (matchTime.seconds() > 90 && matchTime.seconds() < 90.1) {
                gamepad1.rumbleBlips(3);
                gamepad2.rumbleBlips(3);
                //last 10 seconds
            } else if (matchTime.seconds() > 110 && matchTime.seconds() < 120) {
                //constant rumble
                gamepad1.rumble(50);
                gamepad2.rumble(50);
            } else if (matchTime.seconds() > 120) {
                //not necessary but here just in case
                gamepad1.stopRumble();
                gamepad2.stopRumble();
            }

            if ((slides.getAmpsLeft() > 5 || slides.getAmpsRight() > 5) && matchTime.seconds() < 120) {
                gamepad2.rumble(50);
            }


            slides.craneMaintenance();
            //telemetry.addData("camera angle",cameraangle);
            telemetry.addData("Left Crane Motor Position", slides.getCurrentLeftPosition());
            telemetry.addData("Right Crane Motor Position", slides.getCurrentRightPosition());
            telemetry.addData("left crane amps", slides.getAmpsLeft());
            telemetry.addData("right crane amps", slides.getAmpsRight());
            telemetry.addData("rotate", clawRotateServo.getPosition());
            telemetry.update();
        }

    }
}


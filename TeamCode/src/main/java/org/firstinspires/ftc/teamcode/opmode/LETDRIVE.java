package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
//import org.firstinspires.ftc.teamcode.yooniverse.camera;
import org.firstinspires.ftc.teamcode.yooniverse.values;

import dalvik.system.DelegateLastClassLoader;

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
        boolean movingSlides = false;
        int slidesTarget = 0;
        //camera camera = new camera(hardwareMap, telemetry);


        waitForStart();
        ///REMOVE AFTER TESTING
        retractClaw();

        ElapsedTime timer = new ElapsedTime();
        ElapsedTime transferTime = new ElapsedTime();
        ElapsedTime matchTime = new ElapsedTime();
        ElapsedTime rotateTime = new ElapsedTime();

        clawRotateServo.setPosition(0.5);
        transferDown();

        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");
            matchTime.startTime();

            //move horz slides a custom amount
            if(gamepad2.right_trigger > 0.1){
                clawMove(extenderLeft.getPosition() + 0.001);
            }else if(gamepad2.left_trigger > 0.1){
                clawMove(extenderLeft.getPosition() - 0.001);
            }


            if (gamepad1.left_trigger > 0.1 && rotateTime.time() > .5) {
                rotateClawReverse();
                rotateTime.reset();
            } else if (gamepad1.right_trigger > 0.1 && rotateTime.time() > .5) {
                rotateClaw();
                rotateTime.reset();
            }

            train.manualDrive(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x,
                    -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                    -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x,
                    -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);




            //just in case claw up w/ nothing else
            if (gamepad1.triangle) {
                clawUp();
            }

            //just in case claw open
            if (gamepad1.circle) {
                openClawLarge();
            }
            //just in case claw close
            if (gamepad1.square) {
                closeClaw();
            }




            //CLAW OUT
            if (gamepad1.left_bumper) {
                extendClaw();
                openClaw();
                clawHover();
            }

            //CLAW IN
            if (gamepad1.right_bumper) {
                clawDown();
                transferClawOpen();
                timer.reset();
            }else if((timer.time() > .2 && timer.time() < .4) && matchTime.time() > 1) {
                closeClawTighet();
            }else if((timer.time() > .6 && timer.time() < .8) /*&& matchTime.time() > 1*/){
                clawUp();
                retractClaw();
                clawRotateServo.setPosition(0.5);
            }else if((timer.time() > 1 && timer.time() < 1.2) && matchTime.time() > 1.5){
                closeClaw();
            }



            //TRANSFER
            if(gamepad2.right_bumper){
                transferClawClose();
                transferTime.reset();
            }else if((transferTime.time() > .3 && transferTime.time() < .5) && matchTime.time() > 1){
                openClawLarge();
                if(transferTime.time() > .4){
                    slidesTarget = values.craneHighBasket;
                    movingSlides = true;
                }
            }

            //BRING TRANSFER AND SLIDES DOWN
            if(gamepad2.left_bumper){
                transferClawOpen();
                transferDown();
                slidesTarget = values.craneResting;
                //might be necessary idk
                transferClawClose();

                movingSlides = true;
            }


            //auto transfer arm up when slides are close to reaching high basket
            if(slides.getCurrentLeftPosition() > values.craneHighBasket - 50 || slides.getCurrentRightPosition() > values.craneHighBasket - 50){
                transferUp();
            }



            //scuffed slide code
            if(movingSlides){
                slides.setTargetPosition(slidesTarget);
                if(slides.getCurrentLeftPosition() < slidesTarget || slides.getCurrentLeftPosition() < slidesTarget){
                    if(slides.getCurrentRightPosition() >= slidesTarget - 5 || slides.getCurrentLeftPosition() >= slidesTarget - 5){
                        movingSlides = false;
                    }
                }
                if(slides.getCurrentLeftPosition() > slidesTarget || slides.getCurrentLeftPosition() > slidesTarget){
                    if(slides.getCurrentRightPosition() <= slidesTarget + 5 || slides.getCurrentLeftPosition() <= slidesTarget + 5){
                        movingSlides = false;
                    }
                }
            }else{
                slides.move(slides.getCurrentRightPosition(), false);
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
                slides.setTargetPosition(-2000);
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
            telemetry.addData("extenderL", extenderLeft.getPosition());
            telemetry.addData("extenderR", extenderRight.getPosition());
            telemetry.update();
        }

    }
}


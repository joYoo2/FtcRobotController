package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

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
        boolean movingSlides = false;
        boolean transferIn = false;
        boolean specimenIntake = false;
        int slidesTarget = 0;

        double drive;
        double strafe;
        double rotate;
        //camera camera = new camera(hardwareMap, telemetry);


        waitForStart();
        //retractClaw();

        ElapsedTime timer = new ElapsedTime();
        ElapsedTime transferTime = new ElapsedTime();
        ElapsedTime matchTime = new ElapsedTime();
        ElapsedTime buttonTime = new ElapsedTime();


        clawRotateServo.setPosition(0.5);
        //transferUp();

        while (opModeIsActive()) {

            telemetry.addData("Status", "Running");
            matchTime.startTime();

            //move horz slides a custom amount
            if (gamepad2.right_trigger > 0.1) {
                clawMove(extenderLeft.getPosition() + 0.05);
            } else if (gamepad2.left_trigger > 0.1) {
                clawMove(extenderLeft.getPosition() - 0.05);
            }


            //clawrotate
            if (gamepad1.left_trigger > 0.1 && buttonTime.time() > .4) {
                rotateClawReverse();
                buttonTime.reset();
            } else if (gamepad1.right_trigger > 0.1 && buttonTime.time() > .4) {
                rotateClaw();
                buttonTime.reset();
            }


            //drive train code
            drive = -gamepad1.left_stick_y;
            strafe = gamepad1.left_stick_x;
            rotate = gamepad1.right_stick_x;

            train.manualDrive(drive + strafe + rotate,
                    drive - strafe - rotate,
                    drive - strafe + rotate,
                    drive + strafe - rotate);


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

            //just in case transfer up and down (IMPORTANT!)
            if (gamepad2.dpad_left) {
                transferDown();
            } else if (gamepad2.dpad_right) {
                transferClawClose();
                transferUp();
            }


            //CLAW OUT
            if (gamepad1.left_bumper) {
                transferUp();
                extendClaw();
                openClaw();
                clawHover();
            }

            //CLAW IN
            if (gamepad1.right_bumper) {
                clawDown();
                timer.reset();
            } else if ((timer.time() > .2 && timer.time() < .4) && matchTime.time() > 1) {
                closeClawTighet();
            }else if(timer.time() > .4 && timer.time() < .6 && matchTime.time() > 1){
                clawHover();
                retractClaw();
            }else if((timer.time() > .8 && timer.time() < 1) /*&& matchTime.time() > 1*/) {
                clawUp();
                transferUp();
                transferClawOpen();
                clawRotateServo.setPosition(0.5);
            }else if(timer.time() > 1 && timer.time() < 1.2 && matchTime.time() > 1.5){
                transferDown();
            }


            if(gamepad2.touchpad && buttonTime.time() > .5){
                if(!specimenIntake){
                    specimenIntake = true;
                }else{
                    specimenIntake = false;
                }
                buttonTime.reset();
            }

            if(!specimenIntake){
                //TRANSFER
                if(gamepad2.left_bumper){
                    transferClawClose();
                    transferIn = true;
                    transferTime.reset();
                }else if((transferTime.time() > .3 && transferTime.time() < .5) && matchTime.time() > 1 && transferIn){
                    openClawLarge();
                    if(transferTime.time() > .4){
                        slidesTarget = values.craneHighBasket;
                        movingSlides = true;
                    }
                }else if(transferTime.time() > 1 && transferTime.time() < 1.2 && matchTime.time() > 1 && transferIn){
                    transferUp();
                    transferIn = false;
                }

                //BRING TRANSFER AND SLIDES DOWN
                if(gamepad2.right_bumper){
                    transferClawOpen();
                    transferTime.reset();
                }else if(transferTime.time() > .3 && transferTime.time() < .5 && matchTime.time() > 1 && !transferIn){
                    slidesTarget = values.craneResting;
                    movingSlides = true;
                    transferClawClose();
                    transferMid();
                }
            }else if(specimenIntake){
                ///REALLLYYY WIP LIKE NONE OF THIS WILL PROBABLY WORK JUST WRITTEN AS LIKE PLACEHOLDER AND STUFF
                //TRANSFER specimen edition
                if(gamepad2.right_bumper && transferTime.time() < .2){
                    //YOU MUST HOLD DOWN THE BUTTON IN ORDER TO HOLD THE INTAKE POSITION
                    clawSpecimen();
                    transferIn = true;
                    transferTime.reset();
                }else if(transferTime.time() > .2 && transferTime.time() < .4 && transferIn){
                    closeClawTighet();
                    transferDown();
                }else if(transferTime.time() > .4 && transferTime.time() < .6 && transferIn){
                    clawUp();
                }else if(transferTime.time() > .6 && transferTime.time() < .8 && transferIn){
                    transferClawClose();
                }else if(transferTime.time() > .8 && transferTime.time() < 1 && transferIn){
                    openClawLarge();
                    transferUp();
                }

                //BRING TRANSFER AND SLIDES DOWN specimen edition
                if(gamepad2.left_bumper && transferTime.time() < .2){
                    //HOLD THE BUTTON DOWN ish kinda
                    transferDown();
                    transferTime.reset();
                }else if(transferTime.time() > .3 && transferTime.time() < .5 && matchTime.time() > 1 && !transferIn){
                    transferClawOpen();
                }
            }




            //scuffed slide code
            if(movingSlides){
                slides.setTargetPosition(slidesTarget);
                if(slides.getCurrentLeftPosition() < slidesTarget || slides.getCurrentLeftPosition() < slidesTarget){
                    if(slides.getCurrentRightPosition() >= slidesTarget - 15 || slides.getCurrentLeftPosition() >= slidesTarget - 15){
                        movingSlides = false;
                    }
                }
                if(slides.getCurrentLeftPosition() > slidesTarget || slides.getCurrentLeftPosition() > slidesTarget){
                    if(slides.getCurrentRightPosition() <= slidesTarget + 15 || slides.getCurrentLeftPosition() <= slidesTarget + 15){
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
            telemetry.update();
        }

    }
}


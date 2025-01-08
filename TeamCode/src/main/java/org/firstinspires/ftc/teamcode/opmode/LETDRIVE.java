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
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        train.setPower(1);




        telemetry.update();
        boolean byPower = false;
        boolean reverseDrive = false;
        boolean specimenTimer = false;



        waitForStart();
        ElapsedTime timer = new ElapsedTime();
        ElapsedTime matchTime = new ElapsedTime();


        while(opModeIsActive()){
            telemetry.addData("Status", "Running");
            matchTime.startTime();



            //just in case code (can delete if not necessary ask miguel)
            if(slides.getCurrentLeftPosition() > 3000 || slides.getCurrentRightPosition() > 3000){
                train.setPower(0.5);
            }else{
                train.setPower(1);
            }

            //reverse drive code
            if(gamepad1.dpad_up){
                reverseDrive = false;
            }else if(gamepad1.dpad_down){
                reverseDrive = true;
            }

            if(gamepad1.options){
                test();
            }else if(gamepad1.share){
                test2();
            }else if(gamepad1.touchpad){
                test3();
            }

            if (!reverseDrive){
                train.manualDrive(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x,
                        -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                        -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x,
                        -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);

            } else{
                train.manualDrive(gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x,
                        gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x,
                        gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x,
                        gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x);

            }

            if(gamepad1.left_bumper){
                openClaw();
                clawHover();
            }
            if(gamepad1.right_bumper){
                clawDown();
                timer.reset();
            }
            if(timer.time() > .2 && timer.time() < .35 && !specimenTimer){
                closeClaw();
                if(timer.time() > .3){
                    clawHoverUp();
                }
            }

            if(gamepad1.triangle){
                clawUp();
            }
            if(gamepad1.cross){
                openClaw();
                clawDown();
            }

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
                highChamberSpecimenClaw();

            }else if(gamepad2.left_bumper){
                slides.setTargetPosition(values.craneHighBasket);

            //Code to automatically raise the slides after releasing the closeSpecimen button VVV
            }else if(timer.time() > 0.3 && specimenTimer){
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

            if(gamepad2.options){
                slides.resetEncoders();
            }

            //rumble code
            //1 minute left
            if(matchTime.seconds() > 60 && matchTime.seconds() < 60.1){
                gamepad1.rumbleBlips(1);
                gamepad2.rumbleBlips(1);
            //end game
            }else if(matchTime.seconds() > 90 && matchTime.seconds() < 90.1){
                gamepad1.rumbleBlips(3);
                gamepad2.rumbleBlips(3);
            //last 10 seconds
            }else if(matchTime.seconds() > 110 && matchTime.seconds() < 120){
                //constant rumble
                gamepad1.rumble(50);
                gamepad2.rumble(50);
            }else if(matchTime.seconds() > 120){
                //not necessary but here just in case
                gamepad1.stopRumble();
                gamepad2.stopRumble();
            }

            if((slides.getAmpsLeft() > 5 || slides.getAmpsRight() > 5) && matchTime.seconds() < 120){
                gamepad2.rumbleBlips(5);
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

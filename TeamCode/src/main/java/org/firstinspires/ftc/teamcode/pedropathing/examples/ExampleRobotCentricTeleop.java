package org.firstinspires.ftc.teamcode.pedropathing.examples;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedropathing.configs.Subsystem;
import org.firstinspires.ftc.teamcode.pedropathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedropathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.yooniverse.values;

/**
 * This is an example teleop that showcases movement and robot-centric driving.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 12/30/2024
 */

@TeleOp(name = "Example Robot-Centric Teleop", group = "Examples")
public class ExampleRobotCentricTeleop extends OpMode {
    private Follower follower;
    private int pathState;
    private Timer pathTimer;
    public Subsystem actions;
    private boolean autoCycleSample = false;
    private final Pose startPose = values.teleopStart;
    boolean emergencySlides = false;
    boolean movingSlides = false;
    boolean transferIn = false;
    boolean specimenIntake = false;
    int slidesTarget = 0;

    private final Pose grabBackPose = new Pose(20, 33, Math.toRadians(0));
    private final Pose grabPose = new Pose(10, 33, Math.toRadians(0));

    ElapsedTime timer = new ElapsedTime();
    ElapsedTime transferTime = new ElapsedTime();
    ElapsedTime matchTime = new ElapsedTime();
    ElapsedTime buttonTime = new ElapsedTime();



    private PathChain scoreSample;

    public void buildPaths(){

        scoreSample = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(follower.getPose()), new Point(60, 140), new Point(values.basketPose)))
                .setLinearHeadingInterpolation(follower.getPose().getHeading(), values.basketPose.getHeading())
                .build();

    }

    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {
        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);

        follower.setStartingPose(startPose);
        pathTimer = new Timer();
        buildPaths();


    }

    /** This method is called continuously after Init while waiting to be started. **/
    @Override
    public void init_loop() {
    }

    /** This method is called once at the start of the OpMode. **/
    @Override
    public void start() {
        follower.startTeleopDrive();
        timer.startTime();
    }
    //TODO: if robot gets stuck, redo the follower.startTeleopDrive(); somewhere at the end of the path

    /** This is the main loop of the opmode and runs continuously after play **/
    @Override
    public void loop() {


        /* Update Pedro to move the robot based on:
        - Forward/Backward Movement: -gamepad1.left_stick_y
        - Left/Right Movement: -gamepad1.left_stick_x
        - Turn Left/Right Movement: -gamepad1.right_stick_x
        - Robot-Centric Mode: true
        */

        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y, -gamepad1.left_stick_x, -gamepad1.right_stick_x, true);
        follower.update();

        if(gamepad2.x && timer.seconds() > 1){
            autoCycleSample = true;
        }
        if (gamepad2.right_trigger > 0.1) {
            actions.clawMove(actions.extenderLeft.getPosition() + 0.05);
        } else if (gamepad2.left_trigger > 0.1) {
            actions.clawMove(actions.extenderLeft.getPosition() - 0.05);
        }


        //clawrotate
        if (gamepad1.left_trigger > 0.1 && buttonTime.time() > .4) {
            actions.rotateClawReverse();
            buttonTime.reset();
        } else if (gamepad1.right_trigger > 0.1 && buttonTime.time() > .4) {
            actions.rotateClawTeleop();
            buttonTime.reset();
        }


        if (gamepad1.triangle) {
            actions.clawUp();
        }

        //just in case claw open
        if (gamepad1.circle) {
            actions.openClawLarge();
        }
        //just in case claw close
        if (gamepad1.square) {
            actions.closeClaw();
        }

        //just in case transfer up and down (IMPORTANT!)
        if (gamepad2.dpad_left) {
            actions.transferDown();
        } else if (gamepad2.dpad_right) {
            actions.transferClawClose();
            actions.transferUp();
        }

//CLAW OUT
        if (gamepad1.left_bumper) {
            actions.transferUp();
            actions.extendClaw();
            actions.openClaw();
            actions.clawHover();
        }

        //CLAW IN
        if (gamepad1.right_bumper) {
            actions.clawDown();
            timer.reset();
        } else if ((timer.time() > .2 && timer.time() < .4) && matchTime.time() > 1) {
            actions.closeClawTighet();
        }else if(timer.time() > .4 && timer.time() < .6 && matchTime.time() > 1){
            actions.clawHover();
            actions.retractClaw();
        }else if((timer.time() > .8 && timer.time() < 1) /*&& matchTime.time() > 1*/) {
            actions.clawUp();
            actions.transferUp();
            actions.transferClawOpen();
            actions.clawRotateServo.setPosition(0.5);
        }else if(timer.time() > 1 && timer.time() < 1.2 && matchTime.time() > 1.5){
            actions.transferDown();
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
                actions.transferClawClose();
                transferIn = true;
                transferTime.reset();
            }else if((transferTime.time() > .3 && transferTime.time() < .5) && matchTime.time() > 1 && transferIn){
                actions.openClawLarge();
                if(transferTime.time() > .4){
                    slidesTarget = values.craneHighBasket;
                    movingSlides = true;
                }
            }else if(transferTime.time() > 1 && transferTime.time() < 1.2 && matchTime.time() > 1 && transferIn){
                actions.transferUp();
                transferIn = false;
            }

            //BRING TRANSFER AND SLIDES DOWN
            if(gamepad2.right_bumper){
                actions.transferClawOpen();
                transferTime.reset();
            }else if(transferTime.time() > .3 && transferTime.time() < .5 && matchTime.time() > 1 && !transferIn){
                slidesTarget = values.craneResting;
                movingSlides = true;
                actions.transferClawClose();
                actions.transferMid();
            }
        }else if(specimenIntake){
            ///REALLLYYY WIP LIKE NONE OF THIS WILL PROBABLY WORK JUST WRITTEN AS LIKE PLACEHOLDER AND STUFF
            //TRANSFER specimen edition
            if(gamepad2.right_bumper && transferTime.time() < .2){
                //YOU MUST HOLD DOWN THE BUTTON IN ORDER TO HOLD THE INTAKE POSITION
                actions.clawSpecimen();
                transferIn = true;
                transferTime.reset();
            }else if(transferTime.time() > .2 && transferTime.time() < .4 && transferIn){
                actions.closeClawTighet();
                actions.transferDown();
            }else if(transferTime.time() > .4 && transferTime.time() < .6 && transferIn){
                actions.clawUp();
            }else if(transferTime.time() > .6 && transferTime.time() < .8 && transferIn){
                actions.transferClawClose();
            }else if(transferTime.time() > .8 && transferTime.time() < 1 && transferIn){
                actions.openClawLarge();
                actions.transferUp();
            }

            //BRING TRANSFER AND SLIDES DOWN specimen edition
            if(gamepad2.left_bumper && transferTime.time() < .2){
                //HOLD THE BUTTON DOWN ish kinda
                actions.transferDown();
                transferTime.reset();
            }else if(transferTime.time() > .3 && transferTime.time() < .5 && matchTime.time() > 1 && !transferIn){
                actions.transferClawOpen();
            }
        }



//scuffed slide code
        if(movingSlides){
            actions.slides.setTargetPosition(slidesTarget);
            if(actions.slides.getCurrentLeftPosition() < slidesTarget || actions.slides.getCurrentLeftPosition() < slidesTarget){
                if(actions.slides.getCurrentRightPosition() >= slidesTarget - 15 || actions.slides.getCurrentLeftPosition() >= slidesTarget - 15){
                    movingSlides = false;
                }
            }
            if(actions.slides.getCurrentLeftPosition() > slidesTarget || actions.slides.getCurrentLeftPosition() > slidesTarget){
                if(actions.slides.getCurrentRightPosition() <= slidesTarget + 15 || actions.slides.getCurrentLeftPosition() <= slidesTarget + 15){
                    movingSlides = false;
                }
            }
        }else{
            actions.slides.move(actions.slides.getCurrentRightPosition(), false);
        }








        if(autoCycleSample){
                switch(pathState){
                    case 1:
                        pathTimer.resetTimer();
                        follower.followPath(scoreSample);

                        if(!follower.isBusy()){
                            follower.startTeleopDrive();
                            autoCycleSample = false;
                        }

                }

            if(gamepad2.triangle){
                follower.startTeleopDrive();
            }

        }

        /* Telemetry Outputs of our Follower */
        telemetry.addData("X", follower.getPose().getX());
        telemetry.addData("Y", follower.getPose().getY());
        telemetry.addData("Heading in Degrees", Math.toDegrees(follower.getPose().getHeading()));

        /* Update Telemetry to the Driver Hub */
        telemetry.update();

    }

    /** We do not use this because everything automatically should disable **/
    @Override
    public void stop() {
    }
}
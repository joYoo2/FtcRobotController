package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import android.provider.SyncStateContract;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.pedroPathing.configs.Subsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
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
    ElapsedTime timer = new ElapsedTime();
    private int pathState;
    private Timer pathTimer;
    public Subsystem actions;
    private boolean autoCycleSpecimen = false;
    private final Pose startPose = values.teleopStart;

    private final Pose grabBackPose = new Pose(20, 33, Math.toRadians(0));
    private final Pose grabPose = new Pose(10, 33, Math.toRadians(0));



    private PathChain grabSpecimen, scoreSpecimen, scoreSample;

    public void buildPaths(){
        grabSpecimen = follower.pathBuilder()
                .addPath(new BezierLine(new Point(follower.getPose()), new Point(grabBackPose)))
                .setLinearHeadingInterpolation(follower.getPose().getHeading(), grabPose.getHeading(), 0.5)
                .addPath(new BezierLine(new Point(grabBackPose), new Point(grabPose)))
                .setLinearHeadingInterpolation(grabBackPose.getHeading(), grabPose.getHeading())
                .setZeroPowerAccelerationMultiplier(2)
                .build();

        //this isnt rly gonna work tho bc is just gonna place at the same spot all the time but whatever og
        scoreSpecimen = follower.pathBuilder()
                .addPath(new BezierLine(new Point(grabPose), new Point(values.basketPose)))
                .setLinearHeadingInterpolation(grabPose.getHeading(), values.basketPose.getHeading(), 0.5)
                .setPathEndTimeoutConstraint(200)
                .setPathEndHeadingConstraint(.007)
                .build();

        scoreSample = follower.pathBuilder()
                .addPath(new BezierCurve(new Point(follower.getPose()), new Point(60, 140), new Point(values.basketPose)))
                .setLinearHeadingInterpolation(follower.getPose().getHeading(), values.basketPose.getHeading())
                .build();

    }

    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {
        Constants.setConstants(FConstants.class,LConstants.class);
        follower = new Follower(hardwareMap);
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

        if(gamepad2.circle && timer.seconds() > 1){
            if(!autoCycleSpecimen){
                autoCycleSpecimen = true;
            }
        }else if(gamepad2.x && timer.seconds() > 1){
            follower.followPath(scoreSample);
        }

        if(autoCycleSpecimen){

                switch(pathState){
                    case 1:
                        pathTimer.resetTimer();
                        if(gamepad2.triangle){
                            follower.startTeleopDrive();
                            autoCycleSpecimen = false;
                        }
                        if(!follower.isBusy()) {
                            if (pathTimer.getElapsedTimeSeconds() > 2.2) {
                            }
                            if (pathTimer.getElapsedTimeSeconds() > 2.4) {
                                if (actions.slides.getCurrentRightPosition() > 100) {
                                    follower.followPath(scoreSpecimen, true);
                                    pathState = 2;
                                }
                            }
                        }
                            break;
                    case 2:
                        pathTimer.resetTimer();
                        if(gamepad2.triangle){
                            follower.startTeleopDrive();
                            autoCycleSpecimen = false;
                        }
                        if(!follower.isBusy()) {
                            /* Grab Sample */
                            if (pathTimer.getElapsedTimeSeconds() > 3) {
                            }
                            if (pathTimer.getElapsedTimeSeconds() > 4) {
                                follower.followPath(grabSpecimen, true);
                                pathState = 1;
                            }

                        }
                        break;

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
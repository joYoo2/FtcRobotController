package org.firstinspires.ftc.teamcode.pedroPathing.autons;


import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Timer;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import org.firstinspires.ftc.teamcode.pedroPathing.configs.Subsystem;


@Autonomous(name = "Samples PEDRO", group = "Pedro")
public class sample extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;

    /** This is our subsystem.
     * We call its methods to manipulate the stuff that it has within the subsystem. */
    public Subsystem actions;

    /** Create and Define Poses + Paths
     * Poses are built with three constructors: x, y, and heading (in Radians).
     * Pedro uses 0 - 144 for x and y, with 0, 0 being on the bottom left.
     * (For Into the Deep, this would be Blue Observation Zone (0,0) to Red Observation Zone (144,144).)
     * Even though Pedro uses a different coordinate system than RR, you can convert any roadrunner pose by adding +72 both the x and y.
     * This visualizer is very easy to use to find and create paths/pathchains/poses: <https://pedro-path-generator.vercel.app/>
     * Lets assume our robot is 18 by 18 inches
     * Lets assume the Robot is facing the human player and we want to score in the bucket */

    /** Start Pose of our robot */
    private final Pose startPose = new Pose(9, 111, Math.toRadians(90));

    /** Scoring Pose of our robot. It is facing the basket at a 135 degree angle. */
    private final Pose scorePose = new Pose(21, 122, Math.toRadians(140));

    /** Lowest (First) Sample from the Spike Mark */
    private final Pose pickup1Pose = new Pose(29, 121, Math.toRadians(0));

    /** Middle (Second) Sample from the Spike Mark */
    private final Pose pickup2Pose = new Pose(29, 129, Math.toRadians(0));

    /** Highest (Third) Sample from the Spike Mark */
    private final Pose pickup3Pose = new Pose(29, 129, Math.toRadians(40));

    /** Park Pose for our robot, after we do all of the scoring. */
    //private final Pose parkPose = new Pose(60, 98, Math.toRadians(-90));
    private final Pose parkPose = new Pose(70, 105, Math.toRadians(-90));

    /** Park Control Pose for our robot, this is used to manipulate the bezier curve that we will create for the parking.
     * The Robot will not go to this pose, it is used a control point for our bezier curve. */
    private final Pose parkControlPose = new Pose(70, 105, Math.toRadians(-90));

    /* These are our Paths and PathChains that we will define in buildPaths() */
    private Path scorePreload, park;
    private PathChain grabPickup1, grabPickup2, grabPickup3, scorePickup1, scorePickup2, scorePickup3;

    /** Build the paths for the auto (adds, for example, constant/linear headings while doing paths)
     * It is necessary to do this so that all the paths are built before the auto starts. **/
    public void buildPaths() {

        /* There are two major types of paths components: BezierCurves and BezierLines.
         *    * BezierCurves are curved, and require >= 3 points. There are the start and end points, and the control points.
         *    - Control points manipulate the curve between the start and end points.
         *    - A good visualizer for this is [this](https://pedro-path-generator.vercel.app/).
         *    * BezierLines are straight, and require 2 points. There are the start and end points.
         * Paths have can have heading interpolation: Constant, Linear, or Tangential
         *    * Linear heading interpolation:
         *    - Pedro will slowly change the heading of the robot from the startHeading to the endHeading over the course of the entire path.
         *    * Constant Heading Interpolation:
         *    - Pedro will maintain one heading throughout the entire path.
         *    * Tangential Heading Interpolation:
         *    - Pedro will follows the angle of the path such that the robot is always driving forward when it follows the path.
         * PathChains hold Path(s) within it and are able to hold their end point, meaning that they will holdPoint until another path is followed.
         * Here is a explanation of the difference between Paths and PathChains <https://pedropathing.com/commonissues/pathtopathchain.html> */

        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(scorePose)));
        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), scorePose.getHeading());

        /* Here is an example for Constant Interpolation
        scorePreload.setConstantInterpolation(startPose.getHeading()); */

        /* This is our grabPickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup1Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading())
                .build();

        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading())
                .build();

        /* This is our scorePickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading())
                .build();

        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our park path. We are using a BezierCurve with 3 points, which is a curved line that is curved based off of the control point */
        park = new Path(new BezierCurve(new Point(scorePose), /* Control Point */ new Point(parkControlPose), new Point(parkPose)));
        park.setLinearHeadingInterpolation(scorePose.getHeading(), parkPose.getHeading());
    }

    /** This switch is called continuously and runs the pathing, at certain points, it triggers the action state.
     * Everytime the switch changes case, it will reset the timer. (This is because of the setPathState() method)
     * The followPath() function sets the follower to run the specific path, but does NOT wait for it to finish before moving on. */
    public void autonomousPathUpdate() {
        switch (pathState) {
            case 0:
                follower.followPath(scorePreload);
                setPathState(1);
                actions.closeClaw();
                actions.clawUp();
                actions.highBasket();
                break;
            case 1:

                /* You could check for
                - Follower State: "if(!follower.isBusy() {}" (Though, I don't recommend this because it might not return due to holdEnd?
                - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
                - Robot Position: "if(follower.getPose().getX() > 36) {}"
                - other robot position: "if(follower.getPose().getX() > (pickup3Pose.getX() - 1) && follower.getPose().getY() > (pickup3Pose.getY() - 1)) {}"
                */

                if(!follower.isBusy()) {
                    /* Score Preload */

                    if(actions.slides.getCurrentRightPosition() > 3750 || pathTimer.getElapsedTimeSeconds() > 5){
                        if(actions.slides.getCurrentRightPosition() == 3770 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)){
                            pathTimer.resetTimer();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            actions.retractClaw();
                            actions.slidesResting();
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            follower.followPath(grabPickup1,true);
                            setPathState(2);
                        }
                    }

                }
                break;
            case 2:
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    actions.clawDown();
                    if(actions.slides.getCurrentLeftPosition() < 40 || pathTimer.getElapsedTimeSeconds() >= 5){
                        if(actions.slides.getCurrentLeftPosition() == 30 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) {
                            pathTimer.resetTimer();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2.5){
                            actions.closeClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.clawUp();
                            actions.slides.resetEncoders();
                            actions.highBasket();

                            /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                            follower.followPath(scorePickup1,true);
                            setPathState(3);
                        }
                    }


                }
                break;
            case 3:
                if(!follower.isBusy()) {
                    /* Score Sample */
                    if(actions.slides.getCurrentRightPosition() > 3750 || pathTimer.getElapsedTimeSeconds() > 5){
                        if(actions.slides.getCurrentRightPosition() == 3770 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)){
                            pathTimer.resetTimer();

                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5 ){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            actions.retractClaw();
                            actions.slidesResting();
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            follower.followPath(grabPickup2, true);
                            setPathState(4);
                        }
                    }
                }
                break;
            case 4:
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    actions.clawDown();
                    if(actions.slides.getCurrentLeftPosition() < 40 || pathTimer.getElapsedTimeSeconds() >= 5){
                        if(actions.slides.getCurrentLeftPosition() == 30 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) {
                            pathTimer.resetTimer();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2.5){
                            actions.closeClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.clawUp();
                            actions.slides.resetEncoders();
                            actions.highBasket();

                            /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                            follower.followPath(scorePickup2,true);
                            setPathState(5);
                        }
                    }

                }
                break;
            case 5:
                if(!follower.isBusy()) {
                    /* Score Sample */
                    if(actions.slides.getCurrentRightPosition() > 3750 || pathTimer.getElapsedTimeSeconds() > 5){
                        if(actions.slides.getCurrentRightPosition() == 3770 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)){
                            pathTimer.resetTimer();

                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            actions.retractClaw();
                            actions.slidesResting();
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            follower.followPath(grabPickup3, true);
                            setPathState(6);
                        }
                    }
                }
                break;
            case 6:
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    actions.clawDown();
                    if(actions.slides.getCurrentLeftPosition() < 40 || pathTimer.getElapsedTimeSeconds() >= 5){
                        if(actions.slides.getCurrentLeftPosition() == 30 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) {
                            pathTimer.resetTimer();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.closeClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            actions.clawUp();
                            actions.retractClaw();
                            actions.slides.resetEncoders();
                            actions.highBasket();

                            /* Since this is a pathChain, we can have Pedro hold the end point while we are scoring the sample */
                            follower.followPath(scorePickup3,true);
                            setPathState(7);
                    }

                    }
                }
                break;
            case 7:
                if(!follower.isBusy()) {
                    /* Score Sample */
                    if(actions.slides.getCurrentRightPosition() > 3750 || pathTimer.getElapsedTimeSeconds() > 5){
                        if(actions.slides.getCurrentRightPosition() == 3770 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)){
                            pathTimer.resetTimer();

                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            actions.retractClaw();
                            actions.highChamber();
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            follower.followPath(park, true);
                            setPathState(8);
                        }
                    }
                }
                break;
            case 8:
                if(!follower.isBusy()) {
                    /* Put the claw in position to get a level 1 ascent */
                    actions.closeClaw();
                    actions.clawDown();

                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
                break;
        }
    }

    /** These change the states of the paths and actions
     * It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
    }

    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("x", follower.getPose().getX());
        telemetry.addData("y", follower.getPose().getY());
        telemetry.update();


    }

    /** This method is called once at the init of the OpMode. **/
    @Override
    public void init() {
        pathTimer = new Timer();
        opmodeTimer = new Timer();
        opmodeTimer.resetTimer();

        Constants.setConstants(FConstants.class, LConstants.class);

        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
        buildPaths();

        actions = new Subsystem(hardwareMap);

        // Sets the max power to the drive train
        follower.setMaxPower(0.8);

        // Set the claw to positions for init
        actions.closeClaw();
        actions.retractClaw();
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {}

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    /** We do not use this because everything should automatically disable **/
    @Override
    public void stop() {
    }
}

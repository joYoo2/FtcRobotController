package org.firstinspires.ftc.teamcode.pedroPathing.autons;


import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.teamcode.pedroPathing.configs.Subsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;
import org.firstinspires.ftc.teamcode.yooniverse.values;


@Autonomous(name = "Samples BIGGER PEDRO", group = "Pedro")
public class samplemore extends OpMode {

    private Follower follower;
    private Timer pathTimer, actionTimer, opmodeTimer;

    /** This is the variable where we store the state of our auto.
     * It is used by the pathUpdate method. */
    private int pathState;
    private boolean timerReset;


    ///FOR thingy
    boolean posePressed = false;
    int sampleCounter = 0;
    double xCoordinate = 72;
    double yCoordinate = 100;
    double angle = 0;

    double rotationAngle;
    double rotationAngleMOOOOOOORE;

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
    private final Pose startPose = new Pose(9, 110, Math.toRadians(90));

    /** Scoring Pose of our robot. It is facing the basket at a 135 degree angle. */
    private final Pose scorePose = new Pose(18, 123, Math.toRadians(135));
    /** Lowest (First) Sample from the Spike Mark */
    private final Pose pickup1Pose = new Pose(30, 118.5, Math.toRadians(0));

    /** Middle (Second) Sample from the Spike Mark */
    private final Pose pickup2Pose = new Pose(30.5, 127.5, Math.toRadians(0));

    /** Highest (Third) Sample from the Spike Mark */
    private final Pose pickup3Pose = new Pose(31, 127.5, Math.toRadians(35));
    private final Pose pickup4Pose = new Pose(12, 110, Math.toRadians(-90));

    //extra submersible samples
    private Pose subBackPose = new Pose(60, 110, Math.toRadians(-90));
    private Pose subPose = new Pose(0, 0, 0);
    private Pose subMOOOOOOOREPose = new Pose(0, 0, 0);

    /** Park Pose for our robot, after we do all of the scoring. */
    //private final Pose parkPose = new Pose(60, 98, Math.toRadians(-90));
    private final Pose parkPose = new Pose(63, 98, Math.toRadians(-90));

    /** Park Control Pose for our robot, this is used to manipulate the bezier curve that we will create for the parking.
     * The Robot will not go to this pose, it is used a control point for our bezier curve. */
    private final Pose parkControlPose = new Pose(63, 131, Math.toRadians(-90));

    /* These are our Paths and PathChains that we will define in buildPaths() */
    private Path scorePreload, park;
    private PathChain grabPickup1, grabPickup2, grabPickup3, grabPickup4, grabSubmersible, grabSubmersible2, scorePickup1, scorePickup2, scorePickup3, scorePickup4, scoreSubmersible, scoreSubmersible2;

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
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup1Pose.getHeading(), .7)
                //.setPathEndHeadingConstraint(Math.toRadians(.5))
                .setPathEndTranslationalConstraint(0.1)
                .setPathEndTimeoutConstraint(200)
                .build();

        /* This is our scorePickup1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup1 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup1Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup1Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup2Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup2Pose.getHeading(), .7)
                //.setPathEndHeadingConstraint(Math.toRadians(.5))
                .setPathEndTranslationalConstraint(0.1)
                .setPathEndTimeoutConstraint(200)
                .build();

        /* This is our scorePickup2 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup2Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup2Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup3Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup3Pose.getHeading(), .7)
                .setPathEndHeadingConstraint(Math.toRadians(.5))
                .setPathEndTranslationalConstraint(0.1)
                .setPathEndTimeoutConstraint(200)
                .build();

        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup3 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup3Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup3Pose.getHeading(), scorePose.getHeading())
                .build();

        /* This is our grabPickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        grabPickup4 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(pickup4Pose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), pickup4Pose.getHeading(), .7)
                .setPathEndHeadingConstraint(Math.toRadians(.5))
                .setPathEndTranslationalConstraint(0.1)
                .setPathEndTimeoutConstraint(200)
                .build();

        /* This is our scorePickup3 PathChain. We are using a single path with a BezierLine, which is a straight line. */
        scorePickup4 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(pickup4Pose), new Point(scorePose)))
                .setLinearHeadingInterpolation(pickup4Pose.getHeading(), scorePose.getHeading())
                .build();

        ///NEW SUB PATHS
        grabSubmersible = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(subBackPose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), subPose.getHeading(), .7)
                .addPath(new BezierLine(new Point(subBackPose), new Point(subPose)))
                .setConstantHeadingInterpolation(subPose.getHeading())
                .build();
        scoreSubmersible = follower.pathBuilder()
                .addPath(new BezierLine(new Point(subPose), new Point(subBackPose)))
                .setLinearHeadingInterpolation(subPose.getHeading(), scorePose.getHeading())
                .addPath(new BezierLine(new Point(subBackPose), new Point(scorePose) ))
                .setConstantHeadingInterpolation(scorePose.getHeading())
                .build();


        grabSubmersible2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(scorePose), new Point(subBackPose)))
                .setLinearHeadingInterpolation(scorePose.getHeading(), subPose.getHeading(), .7)
                .addPath(new BezierLine(new Point(subBackPose), new Point(subMOOOOOOOREPose)))
                .setConstantHeadingInterpolation(subPose.getHeading())
                .build();
        scoreSubmersible2 = follower.pathBuilder()
                .addPath(new BezierLine(new Point(subMOOOOOOOREPose), new Point(subBackPose)))
                .setLinearHeadingInterpolation(subPose.getHeading(), scorePose.getHeading())
                .addPath(new BezierLine(new Point(subBackPose), new Point(scorePose) ))
                .setConstantHeadingInterpolation(scorePose.getHeading())
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
                actions.clawVertical();
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

                    if(actions.slides.getCurrentRightPosition() > 2850 || pathTimer.getElapsedTimeSeconds() > 5){
                        if((actions.slides.getCurrentRightPosition() == 2870 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset){
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                            actions.retractClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            actions.slidesResting();
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            follower.followPath(grabPickup1,true);
                            setPathState(2);
                        }
                    }

                }
                break;
            case 2:
                if(pathTimer.getElapsedTimeSeconds() > 1){
                    actions.clawDown();
                }
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    if(actions.slides.getCurrentLeftPosition() < 40 || pathTimer.getElapsedTimeSeconds() >= 5){
                        if((actions.slides.getCurrentLeftPosition() == 30 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset) {
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2){
                            actions.closeClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2.1){
                            actions.clawVertical();
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
                    if(actions.slides.getCurrentRightPosition() > 2850 || pathTimer.getElapsedTimeSeconds() > 5){
                        if((actions.slides.getCurrentRightPosition() == 2870 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset){
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5 ){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                            actions.retractClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            actions.slidesResting();
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            follower.followPath(grabPickup2, true);
                            setPathState(4);
                        }
                    }
                }
                break;
            case 4:
                if(pathTimer.getElapsedTimeSeconds() > 1){
                    actions.clawDown();
                }
                if(!follower.isBusy()) {
                    /* Grab Sample */
                    if(actions.slides.getCurrentLeftPosition() < 40 || pathTimer.getElapsedTimeSeconds() >= 5){
                        if((actions.slides.getCurrentLeftPosition() == 30 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset) {
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2){
                            actions.closeClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2.1){
                            actions.clawVertical();
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
                    if(actions.slides.getCurrentRightPosition() > 2850 || pathTimer.getElapsedTimeSeconds() > 5){
                        if((actions.slides.getCurrentRightPosition() == 2870 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset){
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                            actions.retractClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
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
                    if(actions.slides.getCurrentLeftPosition() < 40 || pathTimer.getElapsedTimeSeconds() >= 5){
                        if((actions.slides.getCurrentLeftPosition() == 30 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset) {
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 1.5){
                            actions.extendClaw();
                            actions.clawRotateServo.setPosition(0.65);
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2){
                            actions.clawDown();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2.4){
                            actions.closeClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 2.5){
                            actions.clawVertical();
                            actions.retractClaw();
                            actions.slides.resetEncoders();
                            actions.highBasket();
                            actions.clawRotateServo.setPosition(0.5);

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
                    if(actions.slides.getCurrentRightPosition() > 2850 || pathTimer.getElapsedTimeSeconds() > 5){
                        if((actions.slides.getCurrentRightPosition() == 2870 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset){
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                            actions.retractClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            if(sampleCounter == 0){
                                follower.followPath(grabPickup4, true);
                                actions.slidesResting();
                                setPathState(8);
                            }else if(sampleCounter == 1){
                                follower.followPath(grabSubmersible, true);
                                actions.slidesResting();
                                //SKIPS TO 11
                                setPathState(11);
                            }


                        }
                    }
                }
                break;
            case 8:
                if(!follower.isBusy()) {
                    /* Put the claw in position to get a level 1 ascent */
                    actions.clawDown();
                    if(pathTimer.getElapsedTimeSeconds() > 2.5){
                        actions.closeClaw();

                    }
                    if(pathTimer.getElapsedTimeSeconds() > 2.6){
                        actions.clawVertical();
                        actions.highBasket();
                        follower.followPath(scorePickup4, true);
                        setPathState(9);

                    }

                }
                break;
            case 9:
                if(!follower.isBusy()) {
                    /* Score Sample */
                    if(actions.slides.getCurrentRightPosition() > 2850 || pathTimer.getElapsedTimeSeconds() > 5){
                        if((actions.slides.getCurrentRightPosition() == 2870 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset){
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                            actions.retractClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            follower.followPath(park, true);
                            actions.highChamber();
                            setPathState(10);
                        }
                    }
                }
                break;
            case 10:
                if(!follower.isBusy()) {
                    /* Put the claw in position to get a level 1 ascent */
                    actions.closeClaw();
                    actions.clawHoverUp();

                    /* Set the state to a Case we won't use or define, so it just stops running an new paths */
                    setPathState(-1);
                }
                break;
                //first sample sub
            case 11:
                if(!follower.isBusy()) {
                    if(pathTimer.getElapsedTimeSeconds() > 0.1){
                        actions.extendClaw();
                        actions.clawHover();
                        //0.0039 is one degree per tick of rotation servo
                        actions.rotateClaw(0.5-(0.0039*(int)angle));
                    }
                    if(pathTimer.getElapsedTimeSeconds() > 1){
                        actions.clawDown();

                    }
                    if(pathTimer.getElapsedTimeSeconds() > 2){
                        actions.closeClaw();
                    }
                    if(pathTimer.getElapsedTimeSeconds() > 2.2){
                        actions.clawVertical();
                        follower.followPath(scoreSubmersible, true);
                        setPathState(12);
                    }

                }
                break;
            case 12:
                if(!follower.isBusy()) {
                    /* Score Sample */
                    if(actions.slides.getCurrentRightPosition() > 2850 || pathTimer.getElapsedTimeSeconds() > 5){
                        if((actions.slides.getCurrentRightPosition() == 2870 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset){
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                            actions.retractClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            if(sampleCounter == 1){
                                follower.followPath(park, true);
                                actions.highChamber();
                                setPathState(10);
                            }else if(sampleCounter == 2){
                                follower.followPath(scoreSubmersible2, true);
                                actions.slidesResting();
                                setPathState(13);
                            }

                        }
                    }
                }
                break;
                //2nd sample sub
            case 13:
                if(!follower.isBusy()) {
                    if(pathTimer.getElapsedTimeSeconds() > 0.1){
                        actions.extendClaw();
                        actions.clawHover();
                        actions.rotateClaw(0.5-(0.0039*(int)angle));
                    }
                    if(pathTimer.getElapsedTimeSeconds() > 1){
                        actions.clawDown();

                    }
                    if(pathTimer.getElapsedTimeSeconds() > 2){
                        actions.closeClaw();
                    }
                    if(pathTimer.getElapsedTimeSeconds() > 2.2){
                        actions.clawVertical();
                        follower.followPath(scoreSubmersible2, true);
                        setPathState(14);
                    }

                }
                break;
            case 14:
                if(!follower.isBusy()) {
                    /* Score Sample */
                    if(actions.slides.getCurrentRightPosition() > 2850 || pathTimer.getElapsedTimeSeconds() > 5){
                        if((actions.slides.getCurrentRightPosition() == 2870 || (pathTimer.getElapsedTimeSeconds() > 5 && pathTimer.getElapsedTimeSeconds() < 5.2)) && !timerReset){
                            pathTimer.resetTimer();
                            timerReset = true;
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 0.5){
                            actions.extendClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3){
                            actions.openClawLarge();
                            actions.retractClaw();
                        }
                        if(pathTimer.getElapsedTimeSeconds() > 3.5){
                            /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
                            if(sampleCounter == 1){
                                follower.followPath(park, true);
                                actions.highChamber();
                                setPathState(10);
                            }

                        }
                    }
                }
                break;
        }
    }

    /** These change the states of the paths and actions
     * It will also reset the timers of the individual switches **/
    public void setPathState(int pState) {
        pathState = pState;
        pathTimer.resetTimer();
        timerReset = false;
    }

    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
    @Override
    public void loop() {

        // These loop the movements of the robot
        follower.update();
        autonomousPathUpdate();

        // Feedback to Driver Hub
        telemetry.addData("path state", pathState);
        telemetry.addData("path timer", pathTimer.getElapsedTimeSeconds());
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
        //follower.setMaxPower(0.8);

        // Set the claw to positions for init
        actions.closeClaw();
        actions.retractClaw();
        actions.clawRotateServo.setPosition(0.5);
    }

    /** This method is called continuously after Init while waiting for "play". **/
    @Override
    public void init_loop() {
        if(!posePressed){
            if(sampleCounter == 0){
                telemetry.addData("If there no set pose, defaults to grabbing from other robot", "Currently defaulting to other robot");
            }
            telemetry.addData("Press Circle To Set Sample Pose", " (Gamepad 1)");
            telemetry.addData("Press X to cancel last sample", " (Gamepad 1)");
            telemetry.addData("Current sample count: ", sampleCounter);
            telemetry.addData("WIP", "Only capable of 2 extra sub samples");


            if(gamepad1.a){
                posePressed = true;
            }

            telemetry.update();

        }

        if(posePressed){
            telemetry.addData("X (DPAD Up & Down) and Y (DPAD Left & Right) Coordinates", " (Gamepad 1)");
            telemetry.addData("X Coordinate (VERTICAL from player perspective)", xCoordinate);
            telemetry.addData("Y Coordinate (HORIZONTAL from player perspective)", yCoordinate);
            //used for claw rotation
            telemetry.addData("Triggers Angle (left trigger - angle, right trigger + angle)", angle);
            telemetry.addData("Min X: 50, Max X: 94", "");
            telemetry.addData("Max Y: 86 Min Y: 73", "Only up to the middle");

            telemetry.addData("Press Circle to Confirm", "Press X To Cancel");
            if(gamepad1.dpad_up){
                //UP VERTICALLY from player's perspective (further away from player)
                xCoordinate += 0.1;
                if(xCoordinate > 94){
                    xCoordinate = 94;
                }
            }else if(gamepad1.dpad_down){
                //DOWN VERTICALLY from player's perspective (closer to player)
                xCoordinate -= 0.1;
                if(xCoordinate < 50){
                    xCoordinate = 50;
                }
            }

            if(gamepad1.dpad_right){
                //CLOSER to submersible
                yCoordinate -= 0.1;
                if(yCoordinate < 73){
                    yCoordinate = 73;
                }
            }else if(gamepad1.dpad_left){
                //FURTHER AWAY from submersible
                yCoordinate += 0.1;
                if(yCoordinate > 86){
                    yCoordinate = 86;
                }
            }

            if(gamepad1.circle){
                newSample(xCoordinate, yCoordinate, angle);
                //set all values back to default
                xCoordinate = 72;
                yCoordinate = 100;
                angle = 0;

                //increment counter for amount of samples defined
                sampleCounter += 1;

                posePressed = false;
            }else if(gamepad1.x){
                posePressed = false;
            }

            telemetry.update();
        }
    }

    /** This method is called once at the start of the OpMode.
     * It runs all the setup actions, including building paths and starting the path system **/
    @Override
    public void start() {
        opmodeTimer.resetTimer();
        setPathState(0);
    }

    @Override
    public void stop() {
        values.teleopStart = follower.getPose();
    }


    public void newSample(double x, double y, double angle){
        //shifts the y up because of the reach the robot has'
        if(sampleCounter == 0){
            subPose = new Pose(x, y+15, Math.toRadians(-90));
            rotationAngle = angle;

        }
        if(sampleCounter == 1){
            subMOOOOOOOREPose = new Pose(x, y+15, Math.toRadians(-90));
            rotationAngleMOOOOOOORE = angle;
        }
    }
}
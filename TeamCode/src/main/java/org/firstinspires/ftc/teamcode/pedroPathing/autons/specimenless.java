//package org.firstinspires.ftc.teamcode.pedropathing.autons;
//
//import com.pedropathing.util.Constants;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
//
//import com.pedropathing.follower.Follower;
//import com.pedropathing.localization.Pose;
//import com.pedropathing.pathgen.BezierCurve;
//import com.pedropathing.pathgen.BezierLine;
//import com.pedropathing.pathgen.Path;
//import com.pedropathing.pathgen.PathChain;
//import com.pedropathing.pathgen.Point;
//import com.pedropathing.util.Timer;
//
//import org.firstinspires.ftc.teamcode.pedropathing.constants.FConstants;
//import org.firstinspires.ftc.teamcode.pedropathing.constants.LConstants;
//
//import org.firstinspires.ftc.teamcode.pedropathing.configs.Subsystem;
//
//
//@Autonomous(name = "Specimens but less :( PEDRO", group = "Pedro")
//public class specimenless extends OpMode {
//
//    private Follower follower;
//
//    private Timer pathTimer, opmodeTimer;
//
//    /** This is the variable where we store the state of our auto.
//     * It is used by the pathUpdate method. */
//    private int pathState;
//
//    /** This is our subsystem.
//     * We call its methods to manipulate the stuff that it has within the subsystem. */
//    public Subsystem actions;
//
//    private final Pose startPose = new Pose(9, 63, Math.toRadians(0));
//    private final Pose score1Pose = new Pose(38, 63, Math.toRadians(0));
//    private final Pose score2Pose = new Pose(39, 70, Math.toRadians(180));
//
//
//    /** Grabbing the specimen from the observation zone */
//    private final Pose grabBackPose = new Pose(20, 33, Math.toRadians(0));
//    private final Pose grabPose = new Pose(10.5, 33, Math.toRadians(0));
//
//    private final Pose parkPose = new Pose(12, 30, Math.toRadians(0));
//
//
//    /* These are our Paths and PathChains that we will define in buildPaths() */
//    private Path scorePreload, maneuver, park;
//    private PathChain moveBlocks, grabSpecimen1, grabSpecimen2, grabSpecimen3, grabSpecimen4, scoreSpecimen1, scoreSpecimen2, scoreSpecimen3, scoreSpecimen4;
//
//    /** Build the paths for the auto (adds, for example, constant/linear headings while doing paths)
//     * It is necessary to do this so that all the paths are built before the auto starts. **/
//    public void buildPaths() {
//
//        /* There are two major types of paths components: BezierCurves and BezierLines.
//         *    * BezierCurves are curved, and require >= 3 points. There are the start and end points, and the control points.
//         *    - Control points manipulate the curve between the start and end points.
//         *    - A good visualizer for this is [this](https://pedro-path-generator.vercel.app/).
//         *    * BezierLines are straight, and require 2 points. There are the start and end points.
//         * Paths have can have heading interpolation: Constant, Linear, or Tangential
//         *    * Linear heading interpolation:
//         *    - Pedro will slowly change the heading of the robot from the startHeading to the endHeading over the course of the entire path.
//         *    * Constant Heading Interpolation:
//         *    - Pedro will maintain one heading throughout the entire path.
//         *    * Tangential Heading Interpolation:
//         *    - Pedro will follows the angle of the path such that the robot is always driving forward when it follows the path.
//         * PathChains hold Path(s) within it and are able to hold their end point, meaning that they will holdPoint until another path is followed.
//         * Here is a explanation of the difference between Paths and PathChains <https://pedropathing.com/commonissues/pathtopathchain.html> */
//
//        /* This is our scorePreload path. We are using a BezierLine, which is a straight line. */
//        scorePreload = new Path(new BezierLine(new Point(startPose), new Point(score1Pose)));
//        scorePreload.setZeroPowerAccelerationMultiplier(3);
//        scorePreload.setLinearHeadingInterpolation(startPose.getHeading(), score1Pose.getHeading());
//
//
//
//        /* Here is an example for Constant Interpolation
//        scorePreload.setConstantInterpolation(startPose.getHeading()); */
//
//        /* This is our grabSpecimen1 PathChain. We are using a single path with a BezierLine, which is a straight line. */
//        grabSpecimen1 = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(score1Pose), new Point(grabBackPose)))
//                .setLinearHeadingInterpolation(score1Pose.getHeading(), grabPose.getHeading(), 0.5)
//                .addPath(new BezierLine(new Point(grabBackPose), new Point(grabPose)))
//                .setLinearHeadingInterpolation(grabBackPose.getHeading(), grabPose.getHeading())
//                .setZeroPowerAccelerationMultiplier(1)
//                .build();
//
//        scoreSpecimen1 = follower.pathBuilder()
//                .addPath(new BezierLine(new Point(grabPose), new Point(score2Pose)))
//                .setLinearHeadingInterpolation(grabPose.getHeading(), score2Pose.getHeading(), 0.5)
//                .setPathEndTimeoutConstraint(200)
//                .setPathEndHeadingConstraint(.007)
//                .build();
//
//
//        park = new Path(new BezierLine(new Point(score2Pose), new Point(parkPose)));
//        park.setTangentHeadingInterpolation();
//    }
//
//    /** This switch is called continuously and runs the pathing, at certain points, it triggers the action state.
//     * Everytime the switch changes case, it will reset the timer. (This is because of the setPathState() method)
//     * The followPath() function sets the follower to run the specific path, but does NOT wait for it to finish before moving on. */
//    public void autonomousPathUpdate() {
//        switch (pathState) {
//            case 0:
//                follower.followPath(scorePreload, true);
//                setPathState(1);
//                actions.closeClaw();
//                actions.clawVertical();
//                actions.highChamber();
//                break;
//            case 1:
//
//                /* You could check for
//                - Follower State: "if(!follower.isBusy() {}"
//                - Time: "if(pathTimer.getElapsedTimeSeconds() > 1) {}"
//                - Robot Position: "if(follower.getPose().getX() > 36) {}"
//                - other robot position: "if(follower.getPose().getX() > (pickup3Pose.getX() - 1) && follower.getPose().getY() > (pickup3Pose.getY() - 1)) {}"
//
//                */
//
//                if(!follower.isBusy()) {
//                    /* Score Preload */
//
//                    if (pathTimer.getElapsedTimeSeconds() > 2) {
//                        actions.highChamberDown();
//                    }
//                    if (pathTimer.getElapsedTimeSeconds() > 3) {
//                        actions.openClaw();
//                        actions.specimenOpen();
//                    }
//                    if (pathTimer.getElapsedTimeSeconds() > 3.1) {
//                        /* Since this is a pathChain, we can have Pedro hold the end point while we are grabbing the sample */
//                        follower.followPath(grabSpecimen1, true);
//                        actions.slidesResting();
//                        actions.clawEvenMoreVertical();
//                        setPathState(2);
//                    }
//
//
//                }
//                break;
//            case 2:
//                if(!follower.isBusy()) {
//                    if(pathTimer.getElapsedTimeSeconds() > 3){
//                        actions.specimenClose();
//                    }
//                    if(pathTimer.getElapsedTimeSeconds() > 3.5){
//                        actions.highChamberSpecimenClaw();
//                        if(actions.slides.getCurrentRightPosition() > 100) {
//                            follower.followPath(scoreSpecimen1, true);
//                            setPathState(3);
//                        }
//                    }
//                }
//                break;
//            case 3:
//                if(!follower.isBusy()) {
//                    /* Grab Sample */
//                    if (pathTimer.getElapsedTimeSeconds() > 3) {
//                        actions.highChamberDownSpecimenClaw();
//                    }
//                    if (pathTimer.getElapsedTimeSeconds() > 3.5) {
//                        actions.specimenOpen();
//                        follower.followPath(park, true);
//                        actions.slidesResting();
//                    }
//                    if (pathTimer.getElapsedTimeSeconds() > 5) {
//                        setPathState(-1);
//                        requestOpModeStop();
//                    }
//                }
//                break;
//
//
//
//
//        }
//    }
//
//    /** These change the states of the paths and actions
//     * It will also reset the timers of the individual switches **/
//    public void setPathState(int pState) {
//        pathState = pState;
//        pathTimer.resetTimer();
//    }
//
//    /** This is the main loop of the OpMode, it will run repeatedly after clicking "Play". **/
//    @Override
//    public void loop() {
//
//        // These loop the movements of the robot
//        follower.update();
//        autonomousPathUpdate();
//
//        // Feedback to Driver Hub
//        telemetry.addData("path state", pathState);
//        telemetry.addData("x", follower.getPose().getX());
//        telemetry.addData("y", follower.getPose().getY());
//        telemetry.addData("heading", Math.toDegrees(follower.getPose().getHeading()));
//        telemetry.addData("slide", actions.slides.getCurrentRightPosition());
//        telemetry.update();
//
//
//
//    }
//
//    /** This method is called once at the init of the OpMode. **/
//    @Override
//    public void init() {
//        pathTimer = new Timer();
//        opmodeTimer = new Timer();
//        opmodeTimer.resetTimer();
//
//        follower = new Follower(hardwareMap, FConstants.class, LConstants.class);
//
//        follower.setStartingPose(startPose);
//        buildPaths();
//
//        actions = new Subsystem(hardwareMap);
//
//        // Sets the max power to the drive train
//        follower.setMaxPower(1);
//
//        // Set the claw to positions for init
//        actions.closeClaw();
//        actions.retractClaw();
//    }
//
//    /** This method is called continuously after Init while waiting for "play". **/
//    @Override
//    public void init_loop() {}
//
//    /** This method is called once at the start of the OpMode.
//     * It runs all the setup actions, including building paths and starting the path system **/
//    @Override
//    public void start() {
//        opmodeTimer.resetTimer();
//        setPathState(0);
//    }
//
//    /** We do not use this because everything should automatically disable **/
//    @Override
//    public void stop() {
//    }
//}

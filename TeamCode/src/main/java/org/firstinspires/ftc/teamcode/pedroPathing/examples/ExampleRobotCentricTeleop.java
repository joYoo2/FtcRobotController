package org.firstinspires.ftc.teamcode.pedroPathing.examples;

import android.provider.SyncStateContract;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.pathgen.BezierLine;
import com.pedropathing.pathgen.Path;
import com.pedropathing.pathgen.PathChain;
import com.pedropathing.pathgen.Point;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import  com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

/**
 * This is an example teleop that showcases movement and robot-centric driving.
 *
 * @author Baron Henderson - 20077 The Indubitables
 * @version 2.0, 12/30/2024
 */

@TeleOp(name = "Example Robot-Centric Teleop", group = "Examples")
public class ExampleRobotCentricTeleop extends OpMode {
    private Follower follower;
    private final Pose startPose = new Pose(63, 98, Math.toRadians(-90));

    private final Pose grabBackPose = new Pose(20, 33, Math.toRadians(0));
    private final Pose grabPose = new Pose(10, 33, Math.toRadians(0));

    private final Pose scorePose = new Pose(39, 60, Math.toRadians(180));


    private PathChain grabSpecimen, scoreSpecimen;

    public void buildPaths(){
        grabSpecimen = follower.pathBuilder()
                .addPath(new BezierLine(new Point(follower.getPose()), new Point(grabBackPose)))
                .setLinearHeadingInterpolation(follower.getPose().getHeading(), grabPose.getHeading(), 0.5)
                .addPath(new BezierLine(new Point(grabBackPose), new Point(grabPose)))
                .setLinearHeadingInterpolation(grabBackPose.getHeading(), grabPose.getHeading())
                .setZeroPowerAccelerationMultiplier(1)
                .build();

        //this isnt rly gonna work tho bc is just gonna place at the same spot all the time but whatever og
        scoreSpecimen = follower.pathBuilder()
                .addPath(new BezierLine(new Point(grabPose), new Point(scorePose)))
                .setLinearHeadingInterpolation(grabPose.getHeading(), scorePose.getHeading(), 0.5)
                .setPathEndTimeoutConstraint(200)
                .setPathEndHeadingConstraint(.007)
                .build();
    }

    /** This method is call once when init is played, it initializes the follower **/
    @Override
    public void init() {
        Constants.setConstants(FConstants.class,LConstants.class);
        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);
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

        if(gamepad2.touchpad){
            follower.followPath(grabSpecimen);
        }else if(gamepad2.share){
            follower.followPath(scoreSpecimen);
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
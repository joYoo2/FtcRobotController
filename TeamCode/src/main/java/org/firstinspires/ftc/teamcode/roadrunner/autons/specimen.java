package org.firstinspires.ftc.teamcode.roadrunner.autons;

// RR-specific imports
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

// Non-RR imports
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.hardware.RRActions;

@Config
@Autonomous(name = "Specimen RR", group = "Autonomous")
public class specimen extends LinearOpMode {

    @Override
    public void runOpMode()  {


        Pose2d initialPose = new Pose2d(0, -70, Math.toRadians(90));
        Pose2d Pose1 = new Pose2d(0, 0, Math.toRadians(0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        RRActions actions = new RRActions(hardwareMap);



        TrajectoryActionBuilder path1 = drive.actionBuilder(initialPose)
                .strafeToConstantHeading(new Vector2d(0, -33))
                .waitSeconds(2)
                .lineToY(-50)
                .splineToLinearHeading(new Pose2d(35, -35, 0), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(45, -10), 0)
                .turn(Math.toRadians(90))
                .strafeTo(new Vector2d(45, -60))
                .strafeTo(new Vector2d(45, -10))
                .strafeTo(new Vector2d(58, -10))
                .strafeTo(new Vector2d(58, -70))
                .endTrajectory()

                .splineToConstantHeading(new Vector2d(42, -67), Math.toRadians(90))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(0, -33), Math.toRadians(270))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(55, -67), Math.toRadians(90))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(5, -33), Math.toRadians(270))
                .waitSeconds(2)
                .strafeToLinearHeading(new Vector2d(55, -67), Math.toRadians(90));


        Action trajectoryAction;


        trajectoryAction = path1.build();


        telemetry.addData("LINEUP:", "0 degrees, left wheels touching the middle row of tiles");
        telemetry.addData("NOTE:", "This auton starts with a specimen");
        telemetry.update();

        waitForStart();

        while(opModeIsActive() && !isStopRequested()){

            if (isStopRequested()) return;

            Actions.runBlocking(
                    new SequentialAction(
                            trajectoryAction

                    )
                    //alternative for code similar to past:
                    //drive.actionBuilder(new Pose2d(initialPose))
                            //.waitSeconds(2)
                            //lineToX(33)
                            //.waitSeconds(2)
                            //.lineToYSplineHeading(33,Math.toRadians(0));
                            //etc...
            );





            break;

        }
    }


}


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


        Pose2d initialPose = new Pose2d(11.8, 61.7, Math.toRadians(90));
        Pose2d Pose1 = new Pose2d(0, 0, Math.toRadians(0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        RRActions actions = new RRActions(hardwareMap);



        TrajectoryActionBuilder path1 = drive.actionBuilder(initialPose)
                .waitSeconds(2)
                .lineToY(-34);

        TrajectoryActionBuilder path2 = drive.actionBuilder(initialPose)
                .waitSeconds(2)
                .lineToY(-38)
                .splineToConstantHeading(new Vector2d(30, -38) ,Math.toRadians(0))
                .strafeToSplineHeading(new Vector2d(50, -10), Math.toRadians(90));

        TrajectoryActionBuilder path3 = drive.actionBuilder(initialPose)
                .waitSeconds(0.5)

                .lineToY(-60)
//
//                .splineToSplineHeading(new Pose2d(35, -65, Math.toRadians(90)),Math.toRadians(90))

                .lineToY(-10)
                .strafeTo(new Vector2d(55, -10))

                .waitSeconds(0.5)

                .lineToY(-60)

                .waitSeconds(0.5)

                .lineToY(-60)

                .strafeTo(new Vector2d(35, -55))

                .waitSeconds(0.5)

                .lineToY(-65)

                .waitSeconds(0.5)

                .splineToConstantHeading(new Vector2d(5, -34) ,Math.toRadians(0));
        // too lazy to separate into different paths

        Action trajectoryAction;
        Action trajectoryAction2;
        Action trajectoryAction3;


        trajectoryAction = path1.build();
        trajectoryAction2 = path2.build();
        trajectoryAction3 = path3.build();



        while(opModeIsActive() && !isStopRequested()){
            telemetry.addData("LINEUP:", "0 degrees, left wheels touching the middle row of tiles");
            telemetry.addData("NOTE:", "This auton starts with a specimen");
            telemetry.update();

            waitForStart();

            if (isStopRequested()) return;

            Actions.runBlocking(
                    new SequentialAction(
                            actions.openClaw(),
                            trajectoryAction,
                            new SleepAction(2),
                            trajectoryAction2,
                            trajectoryAction3
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


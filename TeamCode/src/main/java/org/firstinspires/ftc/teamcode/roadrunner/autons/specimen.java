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

        TrajectoryActionBuilder path2 = path1.endTrajectory().fresh()
                .waitSeconds(2)
                .lineToY(-38)
                .splineToConstantHeading(new Vector2d(30, -38) ,Math.toRadians(0))
                .strafeToSplineHeading(new Vector2d(50, -10), Math.toRadians(90));

        TrajectoryActionBuilder path3 = path2.endTrajectory().fresh()
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

        Action trajectoryActionChosen;
        Action trajectoryActionChosen2;
        Action trajectoryActionChosen3;


        trajectoryActionChosen = path1.build();
        trajectoryActionChosen2 = path2.build();
        trajectoryActionChosen3 = path3.build();



        while(opModeIsActive() && !isStopRequested()){

            waitForStart();

            if (isStopRequested()) return;

            Actions.runBlocking(
                    new SequentialAction(
                            actions.openClaw(),
                            trajectoryActionChosen,
                            new SleepAction(2),
                            trajectoryActionChosen2,
                            trajectoryActionChosen3
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


package org.firstinspires.ftc.teamcode.roadrunner.autons;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Rotation2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.hardware.RRActions;

@Config
@Autonomous(name = "Sample RR", group = "Autonomous")
public class samples extends LinearOpMode {

    @Override
    public void runOpMode()  {

        Pose2d initialPose = new Pose2d(0, -70, Math.toRadians(90));
        //Pose2d initialPose = new Pose2d(-34, -62, Math.toRadians(180));
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
                //.strafeToLinearHeading(new Vector2d(-50, -50), Math.toRadians(135));
//                .strafeTo(new Vector2d(-50, -50))
//                //.turn(Math.toRadians(-45)
//                //.turn
//                .turnTo(Math.toRadians(-225));
//                //.turn(Math.toRadians(135))

//                .strafeTo(new Vector2d(48, 39))
//                .turn(Math.toRadians(-135)
//                .strafeTo(new Vector2d(-50, -50))
//                .turn(Math.toRadians(135))
//                .strafeTo(new Vector2d(58, 39))
//                .turn(Math.toRadians(-135))
//                .strafeTo(new Vector2d(-50, -50))
//                .turn(Math.toRadians(135))
//                .strafeTo(new Vector2d(55, 39))
//                .turn(Math.toRadians(-25))
//                .strafeTo(new Vector2d(-50, -50))
//                .turn(Math.toRadians(-65));

//        Action trajectoryActionCloseOut = path1.endTrajectory().fresh()
//                .build();
        //
                //strafetolinearheading is so weird
        TrajectoryActionBuilder path2 = drive.actionBuilder(initialPose)
                .strafeTo(new Vector2d(0, 0));

                //.strafeToLinearHeading(new Vector2d(-48, -39), Math.toRadians(90));

        TrajectoryActionBuilder path3 = drive.actionBuilder(initialPose)
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-50, -50));

                //.strafeToLinearHeading(new Vector2d(-52, -54), Math.toRadians(-140));

        TrajectoryActionBuilder path4 = drive.actionBuilder(initialPose)
                .turn(Math.toRadians(135))
                .splineToLinearHeading(new Pose2d(-58, -39, Math.toRadians(45)), 0)
                .strafeTo(new Vector2d(-58, -39));

                //.strafeToLinearHeading(new Vector2d(-58, -39), Math.toRadians(90));

        TrajectoryActionBuilder path5 = drive.actionBuilder(initialPose)
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-50, -50));

                //.strafeToLinearHeading(new Vector2d(-52, -54), Math.toRadians(-140));

        TrajectoryActionBuilder path6 = path5.endTrajectory().fresh()
                .turn(Math.toRadians(135))
                .strafeTo(new Vector2d(-55, -39));

                //.strafeToLinearHeading(new Vector2d(-55, -39), Math.toRadians(135));

        TrajectoryActionBuilder path7 = path6.endTrajectory().fresh()
                .turn(Math.toRadians(-135))
                .strafeTo(new Vector2d(-50, -50));

                //.strafeToLinearHeading(new Vector2d(-52, -54), Math.toRadians(-140));

        TrajectoryActionBuilder path8 = path7.endTrajectory().fresh()
                .strafeTo(new Vector2d(-52, -40))
                //.strafeTo(new Vector2d(-52, -40))
                .splineToLinearHeading(new Pose2d(-25, -5, Math.toRadians(0)), 0);



        Action trajectoryAction;
        Action trajectoryAction2;
        Action trajectoryAction3;
        Action trajectoryAction4;
        Action trajectoryAction5;
        Action trajectoryAction6;
        Action trajectoryAction7;
        Action trajectoryAction8;



        trajectoryAction = path1.build();
        trajectoryAction2 = path2.build();
        trajectoryAction3 = path3.build();
        trajectoryAction4 = path4.build();
        trajectoryAction5 = path5.build();
        trajectoryAction6 = path6.build();
        trajectoryAction7 = path7.build();
        trajectoryAction8 = path8.build();

        Actions.runBlocking(actions.closeClaw());
        Actions.runBlocking(actions.retractClaw());

        waitForStart();


        while(opModeIsActive() && !isStopRequested()){

            telemetry.addData("LINEUP:", "-90 degrees, back wheels touching the right of the second row of tiles");
            telemetry.addData("NOTE:", "This auton starts with a sample");
            telemetry.update();

            if (isStopRequested()) return;

            Actions.runBlocking(
                    //SequentialAction runs the actions one after the other
                    new SequentialAction(
//                            new ParallelAction(
////                                    trajectoryAction
////                                    actions.clawUp(),
////                                    actions.highBasket()
//                            )
                            trajectoryAction,
                            trajectoryAction2
                            //trajectoryActionCloseOut
//                            actions.extendClaw(),
//                            new SleepAction(0.5),
//                            actions.openClaw(),
//                            new SleepAction(0.5),
//                            actions.retractClaw(),
//                            new SleepAction(0.1),
//                            actions.slidesResting(),
//                            new SleepAction(1)
                            //trajectoryAction2

                            //actions.extendClaw()
//                            //ParallelAction runs actions at the same time
//                            new ParallelAction(
//                                    trajectoryAction,
//                                    actions.clawUp(),
//                                    actions.highBasket()
//                            ),
//
//                            actions.extendClaw(),
//                            new SleepAction(0.5),
//                            actions.openClaw(),
//                            //SleepAction runs a timer that sleeps for a "dt" amount of seconds
//                            new SleepAction(2),
//
//                            new ParallelAction(
//                                    actions.retractClaw(),
//                                    actions.slidesResting(),
//                                    trajectoryAction2,
//                                    new SleepAction(0.5),
//                                    actions.clawDown()
//                            ),
//
//
//                            actions.closeClaw(),
//
//
//                            new ParallelAction(
//                                    trajectoryAction3,
//                                    actions.clawUp(),
//                                    actions.highBasket()
//                            ),
//
//                            actions.extendClaw(),
//                            new SleepAction(0.5),
//                            actions.openClaw(),
//
//
//                            new ParallelAction(
//                                    actions.retractClaw(),
//                                    actions.slidesResting(),
//                                    trajectoryAction4,
//                                    new SleepAction(0.5),
//                                    actions.clawDown()
//                            ),
//
//
//                            actions.closeClaw(),
//
//
//                            new ParallelAction(
//                                    trajectoryAction5,
//                                    actions.clawUp(),
//                                    actions.highBasket()
//                            ),
//
//                            actions.extendClaw(),
//                            new SleepAction(0.5),
//                            actions.openClaw(),
//
//
//                            new ParallelAction(
//                                    actions.retractClaw(),
//                                    actions.slidesResting(),
//                                    trajectoryAction6,
//                                    new SleepAction(0.5),
//                                    actions.clawDown()
//                            ),
//
//                            actions.extendClaw(),
//                            new SleepAction(0.5),
//                            actions.closeClaw(),
//
//
//                            new ParallelAction(
//                                    trajectoryAction7,
//                                    actions.clawUp(),
//                                    actions.highBasket()
//                            ),
//
//                            actions.extendClaw(),
//                            new SleepAction(0.5),
//                            actions.openClaw(),
//
//
//                            new ParallelAction(
//                                    trajectoryAction8,
//                                    new SleepAction(0.5),
//                                    actions.clawUp(),
//                                    actions.highChamber()
//                            )
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


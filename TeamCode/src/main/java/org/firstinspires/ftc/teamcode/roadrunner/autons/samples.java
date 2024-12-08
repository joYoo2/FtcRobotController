package org.firstinspires.ftc.teamcode.roadrunner.autons;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
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


        Pose2d initialPose = new Pose2d(-34, -62, Math.toRadians(180));
        Pose2d Pose1 = new Pose2d(0, 0, Math.toRadians(0));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        RRActions actions = new RRActions(hardwareMap);



        TrajectoryActionBuilder path1 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-52, -54, Math.toRadians(-140)), Math.toRadians(0));

        TrajectoryActionBuilder path2 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-48, -39, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder path3 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-52, -54, Math.toRadians(-140)), Math.toRadians(0));

        TrajectoryActionBuilder path4 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-58, -39, Math.toRadians(90)), Math.toRadians(0));

        TrajectoryActionBuilder path5 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-52, -54, Math.toRadians(-140)), Math.toRadians(0));

        TrajectoryActionBuilder path6 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-55, -39, Math.toRadians(135)), Math.toRadians(0));

        TrajectoryActionBuilder path7 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-52, -54, Math.toRadians(-140)), Math.toRadians(0));

        TrajectoryActionBuilder path8 = drive.actionBuilder(initialPose)
                .splineToLinearHeading(new Pose2d(-25, -5, Math.toRadians(0)), Math.toRadians(0));



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

        while(opModeIsActive() && !isStopRequested()){
            telemetry.addData("LINEUP:", "-90 degrees, back wheels touching the right of the second row of tiles");
            telemetry.addData("NOTE:", "This auton starts with a sample");
            telemetry.update();

            waitForStart();

            if (isStopRequested()) return;

            Actions.runBlocking(
                    //SequentialAction runs the actions one after the other
                    new SequentialAction(

                            //ParallelAction runs actions at the same time
                            new ParallelAction(
                                    trajectoryAction,
                                    actions.clawUp(),
                                    actions.highBasket()
                            ),

                            actions.extendClaw(),
                            actions.openClaw(),
                            //SleepAction runs a timer that sleeps for a "dt" amount of seconds
                            new SleepAction(2),

                            new ParallelAction(
                                    trajectoryAction2,
                                    actions.retractClaw(),
                                    new SleepAction(0.5),
                                    actions.slidesResting(),
                                    actions.clawDown()
                            ),


                            actions.closeClaw(),


                            new ParallelAction(
                                    trajectoryAction3,
                                    actions.clawUp(),
                                    actions.highBasket()
                            ),

                            actions.extendClaw(),
                            actions.openClaw(),


                            new ParallelAction(
                                    trajectoryAction4,
                                    actions.retractClaw(),
                                    new SleepAction(0.5),
                                    actions.slidesResting(),
                                    actions.clawDown()
                            ),


                            actions.closeClaw(),


                            new ParallelAction(
                                    trajectoryAction5,
                                    actions.clawUp(),
                                    actions.highBasket()
                            ),

                            actions.extendClaw(),
                            actions.openClaw(),


                            new ParallelAction(
                                    trajectoryAction6,
                                    actions.retractClaw(),
                                    new SleepAction(0.5),
                                    actions.slidesResting(),
                                    actions.clawDown()
                            ),


                            actions.closeClaw(),


                            new ParallelAction(
                                    trajectoryAction7,
                                    actions.clawUp(),
                                    actions.highBasket()
                            ),

                            actions.extendClaw(),
                            actions.openClaw(),


                            new ParallelAction(
                                    trajectoryAction8,
                                    new SleepAction(0.5),
                                    actions.clawUp(),
                                    actions.highChamber()
                            )
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


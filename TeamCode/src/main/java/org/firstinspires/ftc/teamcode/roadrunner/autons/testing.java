package org.firstinspires.ftc.teamcode.roadrunner.autons;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.roadrunner.hardware.RRActions;

@Config

//@Disabled
//@Autonomous(name = "Testing RR", group = "Autonomous")
public class testing extends LinearOpMode {

    @Override
    public void runOpMode()  {

        Pose2d initialPose = new Pose2d(-36, -50, Math.toRadians(90));

        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        RRActions actions = new RRActions(hardwareMap);



        TrajectoryActionBuilder path1 = drive.actionBuilder(initialPose)

                .strafeTo(new Vector2d(-55, 50))
                .strafeTo(new Vector2d(-35, 50))
                .strafeTo(new Vector2d(-55, -50))
                .strafeTo(new Vector2d(-35, 50));




        Action trajectoryAction;



        trajectoryAction = path1.build();
        waitForStart();


        while(opModeIsActive() && !isStopRequested()){

            telemetry.addData("help", "me");
            telemetry.update();

            if (isStopRequested()) return;

            Actions.runBlocking(
                    //SequentialAction runs the actions one after the other
                    new SequentialAction(
                            trajectoryAction
                    )


            );





            break;

        }
    }


}


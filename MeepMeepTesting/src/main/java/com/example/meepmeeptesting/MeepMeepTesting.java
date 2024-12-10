package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.VelConstraint;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 14.2)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-34, -62, Math.toRadians(180)))
                //.strafeTo(new Vector2d(-, -10))

                .strafeToLinearHeading(new Vector2d(-52, -54), Math.toRadians(-140))
                //.splineToLinearHeading(new Pose2d(-52, -54, Math.toRadians(-140)), Math.toRadians(270))

                .strafeToLinearHeading(new Vector2d(-48, -39), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(-52, -54), Math.toRadians(-140))

                .strafeToLinearHeading(new Vector2d(-58, -39), Math.toRadians(90))

                .strafeToLinearHeading(new Vector2d(-52, -54), Math.toRadians(-140))

                .strafeToLinearHeading(new Vector2d(-55, -39), Math.toRadians(135))

                .strafeToLinearHeading(new Vector2d(-52, -54), Math.toRadians(-140))
//
                .strafeTo(new Vector2d(-52, -40))
                .splineToLinearHeading(new Pose2d(-25, -5, Math.toRadians(0)), 0)

                //.splineToConstantHeading(new Vector2d(50, -10) ,Math.toRadians(-90))

//                .lineToY(-38)
//                .splineToConstantHeading(new Vector2d(30, -38) ,Math.toRadians(0))
//                .strafeToSplineHeading(new Vector2d(50, -10), Math.toRadians(90))
//
//
//                .waitSeconds(0.5)
//
//                .lineToY(-60)
////
////                .splineToSplineHeading(new Pose2d(35, -65, Math.toRadians(90)),Math.toRadians(90))
//
//                .lineToY(-10)
//                .strafeTo(new Vector2d(55, -10))
//
//                .waitSeconds(0.5)
//
//                .lineToY(-60)
//
//                .waitSeconds(0.5)
//
//                .lineToY(-60)
//
//                .strafeTo(new Vector2d(35, -55))
//
//                .waitSeconds(0.5)
//
//                .lineToY(-65)
//
//                .waitSeconds(0.5)
//
//                .splineToLinearHeading(new Pose2d(0, -34, Math.toRadians(-90)), Math.toRadians(0))
//
//                .waitSeconds(0.5)
//
//                .splineToSplineHeading(new Pose2d(35, -65, Math.toRadians(90)),Math.toRadians(0))

                //.splineToConstantHeading(new Vector2d(5, -34) ,Math.toRadians(0))





                .build());

        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}
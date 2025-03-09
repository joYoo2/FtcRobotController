package org.firstinspires.ftc.teamcode.yooniverse;

import com.pedropathing.localization.Pose;

public class values {

    //Claw Positions
//    public static double clawOpen = 1;
//    public static double clawLessOpen = 0.9;
//    public static double clawClsoed = 0.77;
    public static double clawClsoedTighet = 0;
    public static double clawClsoed = 0.03;
    public static double clawLessOpen = 0.17;
    public static double clawOpen = 0.28;

    public static double clawExtend = .40;
    public static double clawRetract = 0;





    //Crane Arm Positions (REDO?)
    public static int craneResting = 0;
    public static int craneMax = 800;


    public static int craneLowChamber;
    public static int craneHighChamber = 1200;
    public static int craneHighChamberSpecimenClaw = 1300;
    public static int craneHighBasket = 795;



    ///PEDRO PATHS
    public static Pose teleopStart = new Pose(9, 110, Math.toRadians(90));
    //starts at start of sample auto unless auto is ran beforehand
    public static Pose basketPose = new Pose(18, 123, Math.toRadians(135));

}
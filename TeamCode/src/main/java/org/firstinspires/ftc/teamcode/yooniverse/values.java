package org.firstinspires.ftc.teamcode.yooniverse;

import com.pedropathing.localization.Pose;

public class values {

    //Claw Positions
//    public static double clawOpen = 1;
//    public static double clawLessOpen = 0.9;
//    public static double clawClsoed = 0.77;
    public static double clawClsoedTighet = 0;
    public static double clawClsoed = 0.07;
    public static double clawLessOpen = 0.17;
    public static double clawOpen = 0.28;

    public static double clawExtend = 1;
    public static double clawRetract = 0;





    //Crane Arm Positions (REDO?)
    public static int craneResting = 0;
    public static int craneMax = 2100;


    public static int craneLowChamber;
    public static int craneHighChamber = 1200;
    public static int craneHighChamberSpecimenClaw = 1300;
    public static int craneHighBasket = 2100;



    ///PEDRO PATHS
    public static Pose teleopStart = new Pose(0, 0, 0);
    public static Pose basketPose = new Pose(18, 123, Math.toRadians(135));

}
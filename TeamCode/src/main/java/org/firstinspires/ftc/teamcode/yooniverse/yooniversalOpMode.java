package org.firstinspires.ftc.teamcode.yooniverse;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
public abstract class yooniversalOpMode extends LinearOpMode{
    public yooniversalInit train;
    public Servo leftClawServo, rightClawServo;
    public Servo rightExtender, leftExtender;
    public crane slides;
    public void setup(){
        setup(0.5);
    }
    public void setup(int cranePower){
        slides = new crane(hardwareMap, cranePower, false);
        train = new yooniversalInit(hardwareMap, this);
            leftClawServo = hardwareMap.get(Servo.class, "leftClawServo");
            rightClawServo = hardwareMap.get(Servo.class, "rightClawServo");
    }
    private int opModeType = 2;
    public void setOpModeType(int type){
        opModeType = type;
    }
    public void foward(int distance){
        train.foward()
    }
}

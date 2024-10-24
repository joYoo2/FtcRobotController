package org.firstinspires.ftc.teamcode.yooniverse;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
public abstract class yooniversalOpMode extends LinearOpMode{
    public yooniversalInit train;
    public Servo clawServo;
    public Servo rightExtender, leftExtender;
    public crane slides;
    public void setup(){
        setup(0.5);
    }
    public void setup(double cranePower){
        slides = new crane(hardwareMap, cranePower, false);
        train = new yooniversalInit(hardwareMap, this);
        clawServo = hardwareMap.get(Servo.class, "clawServo");
    }
    private int opModeType = 2;
    public void setOpModeType(int type){
        opModeType = type;
    }
    public void foward(int distance){
        train.foward(distance);
    }

    public void side(int distance){
        train.side(distance);
    }

    public void openClaw(){
        clawServo.setPosition(values.clawOpen);
    }
    public void closeClaw(){
        clawServo.setPosition(values.clawClsoed);
    }

    public void extendClaw(){
        rightExtender.setPosition(1-values.clawExtend);
        leftExtender.setPosition(values.clawExtend);
    }

    public void retractClaw(){
        rightExtender.setPosition(1-values.clawRetract);
        leftExtender.setPosition(values.clawRetract);
    }
}

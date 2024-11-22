package org.firstinspires.ftc.teamcode.yooniverse;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
public abstract class yooniversalOpMode extends LinearOpMode{
    public yooniversalInit train;
    public Servo clawServo;
    public Servo extenderRight, extenderLeft;
    public Servo clawTurnLeft, clawTurnRight;
    public crane slides;
    public void setup(){
        setup(0.1);
    }
    public void setup(double cranePower){
        slides = new crane(hardwareMap, cranePower, false);
        train = new yooniversalInit(hardwareMap, this);
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        extenderRight = hardwareMap.get(Servo.class, "extenderRight");
        extenderLeft = hardwareMap.get(Servo.class, "extenderLeft");
        clawTurnLeft = hardwareMap.get(Servo.class, "clawTurnLeft");
        clawTurnRight = hardwareMap.get(Servo.class, "clawTurnRight");
    }

    public void foward(int distance){
        train.foward(distance);
    }

    public void encoderTest() {
        train.moveByEncoder(train.frontRight, 10000, 0.5);
        train.moveByEncoder(train.frontLeft, 10000, 0.5);
        train.moveByEncoder(train.backRight, 10000, 0.5);
        train.moveByEncoder(train.backLeft, 10000, 0.5);
    }


    public void side(int distance){
        train.side(distance);
    }

    public void rotate(int angle) { train.rotate(angle); }

    public void openClaw(){
        clawServo.setPosition(values.clawOpen);
    }
    public void closeClaw(){
        clawServo.setPosition(values.clawClsoed);
    }

    public void extendClaw(){
        extenderRight.setPosition(1-values.clawExtend);
        extenderLeft.setPosition(values.clawExtend);
    }

    public void retractClaw(){
        extenderRight.setPosition(1-values.clawRetract);
        extenderLeft.setPosition(values.clawRetract);
    }

    //for down all the way
    //1,0 , left right

    public void clawDown(){
        clawTurnLeft.setPosition(0.45);
        clawTurnRight.setPosition(0.55);

    }

    public void clawUp(){
        clawTurnLeft.setPosition(0.20);
        clawTurnRight.setPosition(0.8);

    }

    public void clawVertical(){
        clawTurnLeft.setPosition(0.05);
        clawTurnRight.setPosition(0.85);
    }

    public void lowChamber(){
        slides.setTargetPosition(0);
        //figure out how low chamber works ig idk
    }

    public void highChamber(){
        slides.setTargetPosition(1420);
    }

    public void highChamberDown(){
        slides.setTargetPosition(790);
    }

    public void highBasket(){
        slides.setTargetPosition(3800);
    }

    public void slidesResting(){
        slides.setTargetPosition(0);
    }


//    public void clawStriaght(){
//        clawTurnLeft.setPosition(0.3);
//        clawTurnRight.setPosition(0.7);
//
//    }
}

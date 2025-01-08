package org.firstinspires.ftc.teamcode.yooniverse;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class yooniversalOpMode extends LinearOpMode{
    public yooniversalInit train;
    public Servo clawServo;
    public CRServo clawRotateServo;
    public Servo clawTestServo;
    public Servo extenderRight, extenderLeft;
    public Servo clawTurnLeft, clawTurnRight;
    public Servo specimenLeft, specimenRight;
    public crane slides;
    public void setup(){
        setup(.1, false);
    }
    public void setup(double cranePower,boolean auton){
        slides = new crane(hardwareMap, cranePower, false, false);
        train = new yooniversalInit(hardwareMap, this);
        train.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //comment out the train brake if the power gets too low (which it probably shouldnt but whatever)
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawTestServo = hardwareMap.get(Servo.class, "clawTestServo");
        //clawRotateServo = hardwareMap.get(CRServo.class, "clawServo");
        extenderRight = hardwareMap.get(Servo.class, "extenderRight");
        extenderLeft = hardwareMap.get(Servo.class, "extenderLeft");
        clawTurnLeft = hardwareMap.get(Servo.class, "clawTurnLeft");
        clawTurnRight = hardwareMap.get(Servo.class, "clawTurnRight");
        specimenLeft = hardwareMap.get(Servo.class, "specimenLeft");
        specimenRight = hardwareMap.get(Servo.class, "specimenRight");
        specimenLeft.setDirection(Servo.Direction.REVERSE);
        specimenRight.setDirection(Servo.Direction.REVERSE);
    }

    public void test(){
        clawTestServo.setPosition(0.22);
    }
    public void test2(){
        clawTestServo.setPosition(0.3);
    }

    public void test3(){
        clawTestServo.setPosition(0.35);
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
        clawServo.setPosition(values.clawLessOpen);
    }
    public void openClawLarge(){clawServo.setPosition(values.clawOpen);}
    public void closeClaw(){
        clawServo.setPosition(values.clawClsoed);
    }

    public void extendClaw(){
        extenderRight.setPosition(1-values.clawExtend);
        extenderLeft.setPosition(values.clawExtend);
    }

    public void clawMove(double amount){
        extenderRight.setPosition(1-amount);
        extenderLeft.setPosition(amount);
    }

    public void clawMoveThing(){
        clawRotateServo.setPower(0.1);
    }

    public void clawMoveThingOther(){
        clawRotateServo.setPower(-0.1);
    }

    public void retractClaw(){
        extenderRight.setPosition(1-values.clawRetract);
        extenderLeft.setPosition(values.clawRetract);
    }

    public void specimenOpen(){
        specimenLeft.setPosition(0.65);
        specimenRight.setPosition(0.7);
    }


    public void specimenClose(){
        specimenLeft.setPosition(0.5);
        specimenRight.setPosition(0.8);
    }

    public void clawHover(){
        clawTurnLeft.setPosition(0.36);
        clawTurnRight.setPosition(0.64);
    }

    public void clawHoverUp(){
        clawTurnLeft.setPosition(0.30);
        clawTurnRight.setPosition(0.7);
    }


    public void clawDown(){
        clawTurnLeft.setPosition(0.42);
        clawTurnRight.setPosition(0.58);

    }

    public void clawUp(){
        clawTurnLeft.setPosition(0.20);
        clawTurnRight.setPosition(0.8);

    }
    public void clawEvenMoreVertical(){
        clawTurnLeft.setPosition(0);
        clawTurnRight.setPosition(1);
    }

    public void clawVertical(){
        clawTurnLeft.setPosition(0.1);
        clawTurnRight.setPosition(0.9);
    }

    public void lowChamber(){
        slides.setTargetPosition(0);
        //figure out how low chamber works ig idk
    }

    public void highChamber(){
        slides.setTargetPosition(1300);
    }

    public void highChamberSpecimenClaw(){
        slides.setTargetPosition(1875);
    }

    public void highChamberDown(){
        slides.setTargetPosition(730);
    }

    public void highChamberDownSpecimenClaw(){
        slides.setTargetPosition(1200);
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

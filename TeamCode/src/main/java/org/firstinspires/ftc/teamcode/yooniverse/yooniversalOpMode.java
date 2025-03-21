package org.firstinspires.ftc.teamcode.yooniverse;

import com.pedropathing.pathgen.BezierCurve;
import com.pedropathing.pathgen.Point;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class yooniversalOpMode extends LinearOpMode{
    public yooniversalInit train;
    public Servo armRotateLeft, armRotateRight, clawMountRotate;
    public Servo clawServo, clawRotateServo;
    public Servo transferLeft, transferClaw;
    public Servo extenderLeft, extenderRight;

    public crane slides;
    public void setup(){
        setup(1, false);
    }
    public void setup(double cranePower,boolean auton){
        slides = new crane(hardwareMap, cranePower, false, false);

        train = new yooniversalInit(hardwareMap, this);
        train.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //comment out the train brake if the power gets too low (which it probably shouldnt but whatever)
        armRotateLeft = hardwareMap.get(Servo.class, "test");
        armRotateRight = hardwareMap.get(Servo.class, "armRotateRight");
        clawMountRotate = hardwareMap.get(Servo.class, "clawRotate");

        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawRotateServo = hardwareMap.get(Servo.class, "clawRotateServo");

        transferLeft = hardwareMap.get(Servo.class, "transferLeft");
        transferClaw = hardwareMap.get(Servo.class, "transferClaw");

        extenderLeft = hardwareMap.get(Servo.class, "extenderLeft");
        extenderRight = hardwareMap.get(Servo.class, "extenderRight");
    }

    public void rotateClaw(){clawRotateServo.setPosition(clawRotateServo.getPosition() - 0.1755); }
    //moves 45 degrees is 0.1755, old continuous rotation was + 0.05

    public void rotateClawReverse(){clawRotateServo.setPosition(clawRotateServo.getPosition() + 0.1755); }

    public void openClaw(){
        clawServo.setPosition(values.clawLessOpen);
    }
    public void openClawLarge(){clawServo.setPosition(values.clawOpen);}
    public void closeClaw(){
        clawServo.setPosition(values.clawClsoed);
    }
    public void closeClawTighet(){clawServo.setPosition(values.clawClsoedTighet);
    }

    public void extendClaw(){
        extenderRight.setPosition(values.clawExtend+0.01);
        extenderLeft.setPosition(values.clawExtend);
    }

    public void clawMove(double amount){
        extenderRight.setPosition(amount+0.01);
        extenderLeft.setPosition(amount);
    }

    public void retractClaw(){
        extenderRight.setPosition(values.clawRetract+0.01);
        extenderLeft.setPosition(values.clawRetract);
    }


    public void clawSpecimen(){
        armRotateLeft.setPosition(0.5);
        armRotateRight.setPosition(0.5);
        clawMountRotate.setPosition(0.2);
        //idk real values tbh lmao
    }

    public void clawSpecimenUp(){
        armRotateLeft.setPosition(0.25);
        armRotateRight.setPosition(0.25);
        clawMountRotate.setPosition(0.25);
    }

    public void clawHover(){
        armRotateLeft.setPosition(0.77);
        armRotateRight.setPosition(0.77);
        clawMountRotate.setPosition(0.08);
        //idk real values tbh
    }
    public void clawDown(){
        armRotateLeft.setPosition(0.9);
        armRotateRight.setPosition(0.9);
        clawMountRotate.setPosition(0.13);
    }
    public void transferDown(){
        transferLeft.setPosition(0.45);
    }
    public void specimenIntake(){transferLeft.setPosition(0.48);}
    public void transferMid(){
        transferLeft.setPosition(0.70);
    }
    public void transferUp(){
        transferLeft.setPosition(0.85);
    }

    public void transferUpMore(){transferLeft.setPosition(0.9);}
    public void clawUp(){
        armRotateLeft.setPosition(0.5);
        armRotateRight.setPosition(0.5);
        clawMountRotate.setPosition(.74);
    }

    public void transferClawClose(){
        transferClaw.setPosition(.30);
    }

    public void transferClawOpen(){
        transferClaw.setPosition(.55);
    }









}

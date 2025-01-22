package org.firstinspires.ftc.teamcode.pedroPathing.configs;

import android.os.HardwarePropertiesManager;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.yooniverse.crane;
import org.firstinspires.ftc.teamcode.yooniverse.values;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalInit;

public class Subsystem {
    public Servo clawServo;
    public Servo clawRotateServo;
    public Servo extenderRight, extenderLeft;
    public Servo clawTurnLeft, clawTurnRight;
    public Servo specimenLeft, specimenRight;
    public crane slides;
    public Subsystem(HardwareMap hardwareMap){
        slides = new crane(hardwareMap, .1, false, true);
        slides.resetEncoders();
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        clawRotateServo = hardwareMap.get(Servo.class, "clawRotateServo");
        clawRotateServo.setDirection(Servo.Direction.REVERSE);

        extenderRight = hardwareMap.get(Servo.class, "extenderRight");
        extenderLeft = hardwareMap.get(Servo.class, "extenderLeft");

        clawTurnLeft = hardwareMap.get(Servo.class, "clawTurnLeft");
        clawTurnRight = hardwareMap.get(Servo.class, "clawTurnRight");

        specimenLeft = hardwareMap.get(Servo.class, "specimenLeft");
        specimenRight = hardwareMap.get(Servo.class, "specimenRight");
        specimenLeft.setDirection(Servo.Direction.REVERSE);
        specimenRight.setDirection(Servo.Direction.REVERSE);
    }

    public void rotateClaw(double amount){clawRotateServo.setPosition(amount); }

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
        clawRotateServo.setPosition(0.5);
        clawTurnLeft.setPosition(0.36);
        clawTurnRight.setPosition(0.64);
    }

    public void clawHoverUp(){
        clawRotateServo.setPosition(0.5);
        clawTurnLeft.setPosition(0.30);
        clawTurnRight.setPosition(0.7);
    }


    public void clawDown(){
        clawTurnLeft.setPosition(0.42);
        clawTurnRight.setPosition(0.58);

    }

    public void clawUp(){
        clawRotateServo.setPosition(0.5);
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
        slides.setTargetPosition(1700);
    }

    public void highChamberSpecimenClaw(){
        slides.setTargetPosition(1950);
    }

    public void highChamberDown(){
        slides.setTargetPosition(1000);
    }

    public void highChamberDownSpecimenClaw(){
        slides.setTargetPosition(1300);
    }


    public void highBasket(){
        slides.setTargetPosition(4050);
    }

    public void slidesResting(){
        slides.setTargetPosition(0);
    }



}

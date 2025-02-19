package org.firstinspires.ftc.teamcode.pedroPathing.configs;

import android.os.HardwarePropertiesManager;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.yooniverse.crane;
import org.firstinspires.ftc.teamcode.yooniverse.values;
import org.firstinspires.ftc.teamcode.yooniverse.camera;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalInit;

public class Subsystem {
    public camera camera;
    public Servo clawServo;
    public Servo clawRotateServo;
    public Servo extenderRight, extenderLeft;
    public Servo clawTurnLeft, clawTurnRight;
    public Servo specimenLeft, specimenRight;
    public crane slides;
    public Telemetry telemetry;
    public Subsystem(HardwareMap hardwareMap){
        slides = new crane(hardwareMap, .1, false, true);
        slides.resetEncoders();
        camera = new camera(hardwareMap, telemetry);
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
        clawTurnLeft.setPosition(0.36);
        clawTurnRight.setPosition(0.64);
    }

    public void clawHoverUp(){
        clawRotateServo.setPosition(0.5);
        clawTurnLeft.setPosition(0.33);//.3
        clawTurnRight.setPosition(0.67);//.7
    }



    public void clawDown(){
        clawTurnLeft.setPosition(0.42);
        clawTurnRight.setPosition(0.58);

    }
    public void clawDownMore(){
        clawTurnLeft.setPosition(0.44);
        clawTurnRight.setPosition(0.56);

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
        slides.setTargetPosition(values.craneHighChamber);
    }

    public void highChamberUp(){
        slides.setTargetPosition(values.craneHighChamber + 600);
    }


    public void highChamberSpecimenClaw(){
        slides.setTargetPosition(values.craneHighChamberSpecimenClaw);
    }

    public void highChamberDown(){
        slides.setTargetPosition(values.craneHighChamber - 600);
    }

    public void highChamberDownSpecimenClaw(){
        slides.setTargetPosition(values.craneHighChamberSpecimenClaw - 600);
    }


    public void highBasket(){
        slides.setTargetPosition(values.craneHighBasket);
    }

    public void slidesResting(){
        slides.setTargetPosition(0);
    }

    public void detectBlue(){
        clawRotateServo.setPosition(camera.getAngle());
    }


}

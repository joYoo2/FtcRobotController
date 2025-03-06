package org.firstinspires.ftc.teamcode.yooniverse;

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
        setup(.1, false);
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

    public void rotateClaw(){clawRotateServo.setPosition(clawRotateServo.getPosition() + 0.1755); }
    //moves 45 degrees is 0.1755, old continuous rotation was + 0.05

    public void rotateClawReverse(){clawRotateServo.setPosition(clawRotateServo.getPosition() - 0.1755); }

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
        extenderRight.setPosition(values.clawExtend);
        extenderLeft.setPosition(values.clawExtend);
    }

    public void clawMove(double amount){
        extenderRight.setPosition(amount);
        extenderLeft.setPosition(amount);
    }

    public void retractClaw(){
        extenderRight.setPosition(values.clawRetract);
        extenderLeft.setPosition(values.clawRetract);
    }


    public void clawHover(){
        armRotateLeft.setPosition(0.8);
        armRotateRight.setPosition(0.8);
        clawMountRotate.setPosition(0.05);
        //idk real values tbh lmao
    }
    public void clawDown(){
        armRotateLeft.setPosition(0.9);
        armRotateRight.setPosition(0.9);
        clawMountRotate.setPosition(0.13);
    }
    public void transferDown(){
        transferLeft.setPosition(0.50);
    }

    public void transferUp(){
        transferLeft.setPosition(0.83);
    }

    public void clawUp(){
        armRotateLeft.setPosition(0.5);
        armRotateRight.setPosition(0.5);
        clawMountRotate.setPosition(.73);
    }

    public void transferClawClose(){
        transferClaw.setPosition(values.clawClsoed);
    }

    public void transferClawOpen(){
        transferClaw.setPosition(values.clawLessOpen);
    }




    public void highChamber(){
        slides.setTargetPosition(1300);
    }

    public void highChamberSpecimenClaw(){
        slides.setTargetPosition(values.craneHighChamberSpecimenClaw);
    }

    public void highChamberDown(){
        slides.setTargetPosition(730);
    }

    public void highChamberDownSpecimenClaw(){
        slides.setTargetPosition(1200);
    }


    public void highBasket(){
        slides.setTargetPosition(values.craneHighBasket);
    }

    public void slidesResting(){
        slides.setTargetPosition(0);
    }


}

package org.firstinspires.ftc.teamcode.detection;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.yooniverse.crane;
import org.firstinspires.ftc.teamcode.yooniverse.values;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalInit;

public class intake {
    public yooniversalInit train;
    public Servo armRotateLeft, armRotateRight, clawMountRotate;
    public Servo clawServo, clawRotateServo;
    public Servo transferLeft, transferClaw;
    public Servo extenderLeft, extenderRight;

    public crane slides;

    public intake(HardwareMap hardwareMap, Telemetry telemetry){
        slides = new crane(hardwareMap, .1, false, false);

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

    public void rotateClaw(double rotation){
        clawRotateServo.setPosition(0.5-(0.0039*(int)rotation));
    }
    public void rotateClawTeleop(){clawRotateServo.setPosition(clawRotateServo.getPosition() - 0.1755); }
    //moves 45 degrees is 0.1755, old continuous rotation was + 0.05

    public void rotateClawReverse(){clawRotateServo.setPosition(clawRotateServo.getPosition() + 0.1755); }

    public void openClaw(){
        clawServo.setPosition(values.clawLessOpen);
    }
    public void open(){clawServo.setPosition(values.clawOpen);}
    public void closeClaw(){
        clawServo.setPosition(values.clawClsoed);
    }
    public void close(){clawServo.setPosition(values.clawClsoedTighet);
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
        clawMountRotate.setPosition(0.08);
        //idk real values tbh lmao
    }

    public void clawSpecimenUp(){
        armRotateLeft.setPosition(0.25);
        armRotateRight.setPosition(0.25);
        clawMountRotate.setPosition(0.25);
    }

    public void hover(){
        armRotateLeft.setPosition(0.77);
        armRotateRight.setPosition(0.77);
        clawMountRotate.setPosition(0.08);
        //idk real values tbh
    }
    public void ground(){
        armRotateLeft.setPosition(0.9);
        armRotateRight.setPosition(0.9);
        clawMountRotate.setPosition(0.13);
    }
    public void transferDown(){
        transferLeft.setPosition(0.45);
    }
    public void transferMid(){
        transferLeft.setPosition(0.70);
    }
    public void transferUp(){
        transferLeft.setPosition(0.83);
    }
    public void transferUpMore(){transferLeft.setPosition(0.9);}

    public void clawUp(){
        armRotateLeft.setPosition(0.5);
        armRotateRight.setPosition(0.5);
        clawMountRotate.setPosition(.74);
    }

    public void transferClawClose(){
        transferClaw.setPosition(.4);
    }

    public void transferClawOpen(){
        transferClaw.setPosition(.65);
    }




    public void highChamber(){
        slides.setTargetPosition(1300);
    }


    public void highChamberDown(){
        slides.setTargetPosition(730);
    }


    public void highBasket(){
        slides.setTargetPosition(values.craneHighBasket);
    }

    public void slidesResting(){
        slides.setTargetPosition(0);
    }
}
package org.firstinspires.ftc.teamcode.pedropathing.configs;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.yooniverse.crane;
import org.firstinspires.ftc.teamcode.yooniverse.values;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalInit;

public class Subsystem {
    public yooniversalInit train;
    public Servo armRotateLeft, armRotateRight, clawMountRotate;
    public Servo clawServo, clawRotateServo;
    public Servo transferLeft, transferClaw;
    public Servo extenderLeft, extenderRight;

    public crane slides;

    public Subsystem(HardwareMap hardwareMap){
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

    public void rotateClaw(double rotation){clawRotateServo.setPosition(rotation);}
    public void rotateClawTeleop(){clawRotateServo.setPosition(clawRotateServo.getPosition() - 0.1755); }
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
        clawMountRotate.setPosition(0.08);
        //idk real values tbh lmao
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
    public void transferMid(){
        transferLeft.setPosition(0.70);
    }
    public void transferUp(){
        transferLeft.setPosition(0.83);
    }

    public void clawUp(){
        armRotateLeft.setPosition(0.52);
        armRotateRight.setPosition(0.52);
        clawMountRotate.setPosition(.74);
    }

    public void transferClawClose(){
        transferClaw.setPosition(.25);
    }

    public void transferClawOpen(){
        transferClaw.setPosition(.42);
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

package org.firstinspires.ftc.teamcode.roadrunner.hardware;


import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.yooniverse.crane;
import org.firstinspires.ftc.teamcode.yooniverse.values;

public class RRActions {
    public Servo clawServo;
    public Servo extenderRight, extenderLeft;
    public Servo clawTurnLeft, clawTurnRight;
    public Servo specimenLeft, specimenRight;
    public crane slides;


    private double cranePower = 0.1;

    private double extendAmount;


    public RRActions(HardwareMap hardwareMap){
        slides = new crane(hardwareMap, cranePower, false);
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        extenderRight = hardwareMap.get(Servo.class, "extenderRight");
        extenderLeft = hardwareMap.get(Servo.class, "extenderLeft");
        clawTurnLeft = hardwareMap.get(Servo.class, "clawTurnLeft");
        clawTurnRight = hardwareMap.get(Servo.class, "clawTurnRight");
        specimenLeft = hardwareMap.get(Servo.class, "specimenLeft");
        specimenRight = hardwareMap.get(Servo.class, "specimenRight");

        specimenLeft.setDirection(Servo.Direction.REVERSE);
        specimenRight.setDirection(Servo.Direction.REVERSE);
    }

    public class openClaw implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            clawServo.setPosition(values.clawOpen);
            return false;
        }


    }

    public Action openClaw() {
        return new RRActions.openClaw();
    }



    public class closeClaw implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {
            clawServo.setPosition(values.clawClsoed);
            return false;
        }


    }

    public Action closeClaw() {
        return new RRActions.closeClaw();
    }



    public class extendClaw implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            extenderRight.setPosition(1-values.clawExtend);
            extenderLeft.setPosition(values.clawExtend);
            return false;
        }


    }

    public Action extendClaw() {
        return new RRActions.extendClaw();
    }


    public class moveClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            extenderRight.setPosition(1-extendAmount);
            extenderLeft.setPosition(extendAmount);
            return false;
        }


    }

    public Action moveClaw(double length) {
        extendAmount = length;
        return new RRActions.moveClaw();
    }



    public class retractClaw implements Action {
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            extenderRight.setPosition(1-values.clawRetract);
            extenderLeft.setPosition(values.clawRetract);
            return false;
        }


    }

    public Action retractClaw() {
        return new RRActions.retractClaw();
    }



    public class clawUp implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            clawTurnLeft.setPosition(0.20);
            clawTurnRight.setPosition(0.8);
            return false;
        }


    }

    public Action clawUp() {
        return new RRActions.clawUp();
    }



    public class clawDown implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            clawTurnLeft.setPosition(0.42);
            clawTurnRight.setPosition(0.58);
            return false;
        }


    }

    public Action clawDown() {
        return new RRActions.clawDown();
    }



    public class clawVertical implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            clawTurnLeft.setPosition(0.1);
            clawTurnRight.setPosition(0.9);
            return false;
        }


    }

    public Action clawVertical() {
        return new RRActions.clawVertical();
    }




    public class highBasket implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            slides.setTargetPosition(3850);
            return false;
        }


    }

    public Action highBasket() {
        return new RRActions.highBasket();
    }



    public class highChamber implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            slides.setTargetPosition(1300);
            return false;
        }


    }

    public Action highChamber() {
        return new RRActions.highChamber();
    }



    public class slidesResting implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            slides.setTargetPosition(0);
            return false;
        }


    }

    public Action slidesResting() {
        return new RRActions.slidesResting();
    }



    public class specimenOpen implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            specimenLeft.setPosition(0.65);
            specimenRight.setPosition(0.7);
            return false;
        }


    }

    public Action specimenOpen() {
        return new RRActions.specimenOpen();
    }



    public class specimenClose implements Action {

        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket) {

            specimenLeft.setPosition(0.5);
            specimenRight.setPosition(0.8);
            return false;
        }


    }

    public Action specimenClose() {
        return new RRActions.specimenClose();
    }











}

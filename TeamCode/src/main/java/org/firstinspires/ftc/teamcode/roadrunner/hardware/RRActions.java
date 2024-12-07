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


}

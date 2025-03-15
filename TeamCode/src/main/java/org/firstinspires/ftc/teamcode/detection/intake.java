package org.firstinspires.ftc.teamcode.detection;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;


/** @author Baron Henderson
 * @version 2.0 | 1/4/25
 */

public class intake {
    public static double intakeGrabClose = 0;
    public static double intakeGrabOpen = 0.28;
    public static double intakeRotateHoverVertical = 0.81;
    public static double intakeRotateGroundVertical = 0.9;
    public static double intakePivotGround = 0.67;
    public static double intakePivotHover = 0.43;
    public static double intakeRotatePerDegree = 0.0011;



    public enum GrabState {
        CLOSED, OPEN
    }

    public enum RotateState {
        TRANSFER, GROUND, HOVER, CLOUD, SPECIMEN, DRAG
    }

    public enum PivotState {
        TRANSFER, GROUND, HOVER, CLOUD, SPECIMEN, DRAG
    }

    public Servo grab, leftRotate, rightRotate, pivot;
    public GrabState grabState;
    public RotateState rotateState;
    public PivotState pivotState;
    private Telemetry telemetry;
    private double rotateDegrees = 0;

    public intake(HardwareMap hardwareMap, Telemetry telemetry) {
        grab = hardwareMap.get(Servo.class, "clawServo");
        leftRotate = hardwareMap.get(Servo.class, "test");
        rightRotate = hardwareMap.get(Servo.class, "armRotateRight");
        pivot = hardwareMap.get(Servo.class, "clawRotateServo");
        this.telemetry = telemetry;
    }

    public void setRotateState(RotateState state) {
        if (state == RotateState.GROUND) {
            leftRotate.setPosition(intakeRotateGroundVertical + (rotateDegrees * intakeRotatePerDegree));
            rightRotate.setPosition(intakeRotateGroundVertical - (rotateDegrees * intakeRotatePerDegree));
            this.rotateState = RotateState.GROUND;
        } else if (state == RotateState.HOVER) {
            leftRotate.setPosition(intakeRotateHoverVertical + (rotateDegrees * intakeRotatePerDegree));
            rightRotate.setPosition(intakeRotateHoverVertical - (rotateDegrees * intakeRotatePerDegree));
            this.rotateState = RotateState.HOVER;
        }
    }

}
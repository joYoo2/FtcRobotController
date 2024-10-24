package org.firstinspires.ftc.teamcode.yooniverse;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class yooniversalInit {
    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;
    private double fowardSpeed;
    private double speed = 1;
    private boolean auton = false;
    private final yooniversalOpMode opMode;

    public yooniversalInit(HardwareMap hardwareMap, yooniversalOpMode opmode){
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        backRight = hardwareMap.get(DcMotor.class, "backRight");
        opMode = opMode;
    }

    public void manualDrive(double frontLeftPower, double frontRightPower, double backLeftPower, double jarmyPower){
        frontLeft.setPower(frontLeftPower * speed);
        frontRight.setPower(frontRightPower * speed);
        backLeft.setPower(backLeftPower * speed);
        backRight.setPower(jarmyPower * speed);
    }

    public void setPower(double newPower){
        if(auton){
            frontLeft.setPower(newPower);
            frontRight.setPower(newPower);
            backLeft.setPower(newPower);
            backRight.setPower(newPower);
        }else{
            speed = newPower;
        }
    }

    public void setFowardSpeed(double newPower){
        fowardSpeed = newPower;
    }

    public void setMode(DcMotor.RunMode mode){
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior){
        frontLeft.setZeroPowerBehavior(behavior);
        frontRight.setZeroPowerBehavior(behavior);
        backLeft.setZeroPowerBehavior(behavior);
        backRight.setZeroPowerBehavior(behavior);
    }

    public void setTargetPosition(int target){
        frontLeft.setTargetPosition(target);
        frontRight.setTargetPosition(target);
        backLeft.setTargetPosition(target);
        backRight.setTargetPosition(target);
    }

    public void isAuton(){
        auton = true;
        this.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.setTargetPosition(0);
        this.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //changed from zeropowerbehavior.float
        //brake makes it brake on zero power, resisting any change
    }
    public void moveByEncoder(DcMotor mot, int target, double power){
        mot.setPower(0);
        mot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mot.setTargetPosition(0);
        mot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mot.setPower(0);

        mot.setTargetPosition(target);
        mot.setPower(power);
    }
}

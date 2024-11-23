package org.firstinspires.ftc.teamcode.yooniverse;

import androidx.annotation.NonNull;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

public class crane {
    public DcMotorEx leftDrawerSlide, rightDrawerSlide;
    public int targetPosition;

    public double power = 0;

    //public crane(HardwareMap hardwareMap){ this(hardwareMap, -0.5, false); }

    public crane(@NonNull HardwareMap hardwareMap, double power, boolean craneByPower){
        leftDrawerSlide = hardwareMap.get(DcMotorEx.class, "leftDrawerSlide");
        leftDrawerSlide.setDirection(DcMotorEx.Direction.REVERSE);

        leftDrawerSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);

        rightDrawerSlide = hardwareMap.get(DcMotorEx.class, "rightDrawerSlide");
        rightDrawerSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        resetEncoders();


        targetPosition = 0;

        setPower(power);
        this.power = power;
        setTargetPosition(0);
        setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public void setTargetPosition(int target){
        setPower(0.8);
        leftDrawerSlide.setTargetPosition(target);
        rightDrawerSlide.setTargetPosition(target);
        targetPosition = target;
    }

    public void setTargetPosition(int target, double power){
        setPower(power);
        leftDrawerSlide.setTargetPosition(target);
        rightDrawerSlide.setTargetPosition(target);
        targetPosition = target;
    }

    public void resetEncoders(){
        leftDrawerSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
        rightDrawerSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);

        setTargetPosition(0);
        setPower(power);

        leftDrawerSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        rightDrawerSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
    }

    public void craneMaintenance(){
        if(leftDrawerSlide.getCurrentPosition() < 50 && leftDrawerSlide.getCurrent(CurrentUnit.AMPS) > 0.5 && leftDrawerSlide.getTargetPosition() == 0){
            leftDrawerSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            leftDrawerSlide.setTargetPosition(0);
            leftDrawerSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            leftDrawerSlide.setPower(0);
        }
        if(rightDrawerSlide.getCurrentPosition() < 50 && rightDrawerSlide.getCurrent(CurrentUnit.AMPS)  > 0.5 && rightDrawerSlide.getTargetPosition() == 0){
            rightDrawerSlide.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
            rightDrawerSlide.setTargetPosition(0);
            rightDrawerSlide.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
            rightDrawerSlide.setPower(0);
        }
    }

    public void move(double movement, boolean byPower){
        if(movement > 0 && byPower){
            setTargetPosition(values.craneMax, movement);
        }else if(movement < 0 && byPower){
            setTargetPosition(values.craneResting, -movement);
        }else if(byPower){
            setPower(0);
        }else if(movement > values.craneMax){
            setTargetPosition(values.craneMax);
        }else if(movement < values.craneResting){
            setTargetPosition(values.craneResting);
        }else{
            setTargetPosition((int)movement);
        }
    }
    public void setPower(double power){
        leftDrawerSlide.setPower(power);
        rightDrawerSlide.setPower(power);
    }

    public int getCurrentLeftPosition() { return leftDrawerSlide.getCurrentPosition(); }

    public int getCurrentRightPosition() { return rightDrawerSlide.getCurrentPosition(); }

    public boolean offCheck(){ return getCurrentLeftPosition() < 0 || getCurrentRightPosition() < 0; }


    public void setMode(DcMotorEx.RunMode mode){
        leftDrawerSlide.setMode(mode);
        rightDrawerSlide.setMode(mode);
    }


    public double getAmpsLeft(){return leftDrawerSlide.getCurrent(CurrentUnit.AMPS);}
    public double getAmpsRight(){return rightDrawerSlide.getCurrent(CurrentUnit.AMPS);}
}
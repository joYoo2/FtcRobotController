package org.firstinspires.ftc.teamcode.yooniverse;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class crane {
    private DcMotor leftDrawerSlide, rightDrawerSlide;
    public int targetPosition;

    public double power = 0;

    public crane(HardwareMap hardwareMap){ this(hardwareMap, 0.5, false); }

    public crane(HardwareMap hardwareMap, double power, boolean craneByPower){
        leftDrawerSlide = hardwareMap.get(DcMotor.class, "leftDrawerSlide");
        leftDrawerSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rightDrawerSlide = hardwareMap.get(DcMotor.class, "rightDrawerSlide");
        rightDrawerSlide.setDirection(DcMotorSimple.Direction.REVERSE);
        rightDrawerSlide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        resetEncoders();


        targetPosition = 0;

        setPower(power);
        this.power = power;
        setTargetPosition(0);
        setMode(DcMotor.RunMode.RUN_TO_POSITION);
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
        leftDrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        setTargetPosition(0);
        setPower(power);

        leftDrawerSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrawerSlide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void craneMaintenance(){
        if(offCheck() && targetPosition == 0){
            resetEncoders();
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


    public void setMode(DcMotor.RunMode mode){
        leftDrawerSlide.setMode(mode);
        rightDrawerSlide.setMode(mode);
    }
}
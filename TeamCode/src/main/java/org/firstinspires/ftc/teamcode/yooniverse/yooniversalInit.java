package org.firstinspires.ftc.teamcode.yooniverse;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class yooniversalInit {
    public DcMotorEx frontLeft;
    public DcMotorEx frontRight;
    public DcMotorEx backLeft;
    public DcMotorEx backRight;

    public crane crane;
    private double fowardSpeed = 0.5;
    //was 0.3

    private double lastAngle;
    private double currAngle = 0;
    private double speed = 0.8;
    private double headingOffset = 0;
    public iAmYoo imu;
    public double targetHeading;
    private boolean auton = false;
    private yooniversalOpMode opMode;

    public yooniversalInit(HardwareMap hardwareMap, yooniversalOpMode opMode){
        frontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");


        frontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        frontRight.setDirection(DcMotorSimple.Direction.REVERSE);

        backLeft = hardwareMap.get(DcMotorEx.class, "backLeft");


        backRight = hardwareMap.get(DcMotorEx.class, "backRight");
        backRight.setDirection(DcMotorSimple.Direction.REVERSE);

        this.opMode = opMode;

        imu = new iAmYoo(hardwareMap);

        lastAngle = imu.getYaw();

        targetHeading = 0;

        crane = opMode.slides;

    }

    public void manualDrive(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower){
        frontLeft.setPower(frontLeftPower * speed);
        frontRight.setPower(frontRightPower * speed);
        backLeft.setPower(backLeftPower * speed);
        backRight.setPower(backRightPower * speed);
    }

    public void setPower(double newPower){
        if(auton){
            frontLeft.setPower(newPower);
            frontRight.setPower(newPower);
            backLeft.setPower(newPower);
            backRight.setPower(newPower);

//            speed = newPower;
//            fowardSpeed = newPower;
            //idk if this is good
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

    public void foward(int target){
        resetAngle();
        targetHeading = 0-headingOffset;
        waitForWheels(target, true);
    }

    private void continueFoward(){
        double leftPower = fowardSpeed - fowardSpeed * (Math.max(imu.getYaw(), 0) / 90);
        double rightPower = fowardSpeed - fowardSpeed * (Math.max(-imu.getYaw(), 0) / 90 );
        frontLeft.setPower(leftPower);
        frontRight.setPower(rightPower);
        backLeft.setPower(leftPower);
        backRight.setPower(rightPower);
    }

    public void side(int target){
        resetAngle();
        targetHeading = 0 - headingOffset;
        waitForWheels(target, false);
    }
    private void continueSide(){
        double sideSpeed = 0.7;
        //was 0.5
        double leftPower = sideSpeed - sideSpeed * (-imu.getYaw() / 90);
        double rightPower = sideSpeed - sideSpeed * (imu.getYaw() / 90);
        frontLeft.setPower(leftPower);
        frontRight.setPower(rightPower);
        backLeft.setPower(-leftPower);
        backRight.setPower(-rightPower);
    }

    public void rotate(int target) {
        resetAngle();
        setPower(0);
        setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        turnTo(-target);
        targetHeading = target;
        resetEncoders();
        opMode.sleep(100);

    }



    public void waitForWheels(int target, boolean foward) {
        if(foward){
            this.moveByEncoder(frontLeft, target, 0);
            this.moveByEncoder(frontRight, target, 0);
            this.moveByEncoder(backLeft, target, 0);
            this.moveByEncoder(backRight,target, 0);
        }else{
            this.moveByEncoder(frontLeft, target, 0);
            this.moveByEncoder(frontRight, -target, 0);
            this.moveByEncoder(backLeft, -target, 0);
            this.moveByEncoder(backRight, target, 0);
        }
        double margin = 10;
        while((frontLeft.getCurrentPosition() > frontLeft.getTargetPosition() + margin ||
                frontRight.getCurrentPosition() > frontRight.getTargetPosition() + margin ||
                backLeft.getCurrentPosition() > backLeft.getTargetPosition() + margin ||
                backRight.getCurrentPosition() > backRight.getTargetPosition() + margin ||
                frontLeft.getCurrentPosition() < frontLeft.getTargetPosition() - margin ||
                frontRight.getCurrentPosition() < frontRight.getTargetPosition() - margin ||
                backLeft.getCurrentPosition() < backLeft.getTargetPosition() - margin ||
                backRight.getCurrentPosition() < backRight.getTargetPosition() - margin) &&
                opMode.opModeIsActive()
        ) {
            crane.craneMaintenance();
            if(foward){
                continueFoward();
            }else {
                continueSide();
            }
            opMode.telemetry.addData("frontLeft: ",frontLeft.getCurrentPosition());
            opMode.telemetry.addData("frontRight: ",frontRight.getCurrentPosition());
            opMode.telemetry.addData("backLeft: ",backLeft.getCurrentPosition());
            opMode.telemetry.addData("backRight: ", backRight.getCurrentPosition());


            opMode.telemetry.addData("frontLeftPower: ",frontLeft.getPower());
            opMode.telemetry.addData("frontRightPower: ",frontRight.getPower());
            opMode.telemetry.addData("backLeftPower: ",backLeft.getPower());
            opMode.telemetry.addData("backRightPower: ", backRight.getPower());


            opMode.telemetry.addData("left draw slide", crane.getCurrentLeftPosition());
            opMode.telemetry.addData("left crane amps", crane.getAmpsLeft());
            opMode.telemetry.addData("right crane amps", crane.getAmpsRight());

            opMode.telemetry.update();
            crane.craneMaintenance();
        }
        resetEncoders();
    }

    public void resetEncoders(){
        frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void turnTo(double degrees){
        resetAngle();
        double yaw = imu.getYaw();

        System.out.println(yaw);
        double error = degrees - yaw - headingOffset;
        boolean one80 = false;

        if (error > 180){
            one80 = true;
            error -= 90;
        }else if(error < -180) {
            one80 = true;
            error += 90;
        }

        turn(error, one80);
    }

    public void turn(double degrees, boolean one80){

        double error = degrees;

        while (opMode.opModeIsActive() && Math.abs(error) > 0.9) {
            crane.craneMaintenance();
            double motorPower = (error < 0 ? -0.5 : 0.5);
            motorPower *= Math.min(1, Math.abs(error / 20));
            if(Math.abs(motorPower) < 0.1){
                motorPower = (error < 0 ? -0.1: 0.1);
            }
            frontLeft.setPower(motorPower);
            frontRight.setPower(-motorPower);
            backLeft.setPower(motorPower);
            backRight.setPower(-motorPower);
            if(error < degrees - getAngle() + 0.025 && error > degrees - getAngle() - 0.025 && error < 10 && error > -10){
                break;
            }
            error = degrees - getAngle();
            if(one80 && Math.abs(error) < 80){
                error += (error < 0 ? -90 : 90);
                one80 = false;
            }
            opMode.telemetry.addData("error", error);
            opMode.telemetry.addData("raw yaw", imu.getYaw());
            opMode.telemetry.update();
            crane.craneMaintenance();
        }

        frontLeft.setPower(0);
        frontRight.setPower(0);
        backLeft.setPower(0);
        backRight.setPower(0);
    }

    public double getAngle() {

        // Get current orientation
        double yaw = imu.getYaw();
        // Change in angle = current angle - previous angle
        double deltaAngle = yaw - lastAngle;

        // Gyro only ranges from -179 to 180
        // If it turns -1 degree over from -179 to 180, subtract 360 from the 359 to get -1
        if (deltaAngle < -180) {
            deltaAngle += 360;
        } else if (deltaAngle > 180) {
            deltaAngle -= 360;
        }

        // Add change in angle to current angle to get current angle
        lastAngle = currAngle;
        currAngle += deltaAngle;
        opMode.telemetry.addData("gyro", yaw);
        return currAngle;
    }

    public void resetAngle() {
        headingOffset = currAngle;
        lastAngle = 0;
        currAngle = 0;
        imu.resetYaw();
    }
}

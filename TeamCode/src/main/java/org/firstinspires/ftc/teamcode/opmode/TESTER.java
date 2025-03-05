package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.yooniverse.crane;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;
@TeleOp(name="TESTER", group="yooniverse")
public class TESTER extends LinearOpMode {
//    private DcMotor frontLeft, frontRight, backRight, backLeft;
//    public DcMotorEx leftDrawerSlide, rightDrawerSlide;
    public Servo axon, armRotateRight, clawRotate;
    public Servo clawServo;
    public Servo transferLeft, transferClaw;
    public DcMotorEx leftDrawerSlide, rightDrawerSlide;
    @Override
    public void runOpMode(){
        ElapsedTime timer = new ElapsedTime();

//        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
//
//        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        boolean clawDown = false;
        axon = hardwareMap.get(Servo.class, "test");
        armRotateRight = hardwareMap.get(Servo.class, "armRotateRight");
        clawRotate = hardwareMap.get(Servo.class, "clawRotate");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
        transferLeft = hardwareMap.get(Servo.class, "transferLeft");
        transferClaw = hardwareMap.get(Servo.class, "transferClaw");
//
//        backRight = hardwareMap.get(DcMotor.class, "backRight");
//
//
//        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
//
        leftDrawerSlide = hardwareMap.get(DcMotorEx.class, "leftDrawerSlide");
        rightDrawerSlide = hardwareMap.get(DcMotorEx.class, "rightDrawerSlide");
        rightDrawerSlide.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        leftDrawerSlide.setDirection((DcMotorEx.Direction.REVERSE));

        leftDrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrawerSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        axon.setPosition(0.5);
        armRotateRight.setPosition(0.5);
        //base servo
        clawRotate.setPosition(0.5);
        transferLeft.setPosition(0.5);
        transferClaw.setPosition(0.5);
        clawServo.setPosition(values.clawOpen);

        waitForStart();
        while(opModeIsActive()){
            telemetry.addData("left", leftDrawerSlide.getCurrentPosition());
            telemetry.addData("right", rightDrawerSlide.getCurrentPosition());
            telemetry.update();
            if(gamepad1.left_trigger > 0.1){
                transferClaw.setPosition(transferClaw.getPosition() - 0.0005);
            }else if(gamepad1.right_trigger > 0.1){
                transferClaw.setPosition(transferClaw.getPosition() + 0.0005);
            }
            if(gamepad1.cross){
                axon.setPosition(0.9);
                armRotateRight.setPosition(0.9);
                clawRotate.setPosition(0.13);
                clawServo.setPosition(values.clawLessOpen);
            }else if(gamepad1.circle){
                timer.reset();
                clawDown = true;
                clawServo.setPosition(values.clawClsoed);
            }
            if(timer.time() > .5 && clawDown){
                axon.setPosition(0.5);
                armRotateRight.setPosition(0.5);
                clawRotate.setPosition(.73);
                clawDown = false;
            }

            if(gamepad1.left_bumper){
                transferLeft.setPosition(0.42);

            }
            if(gamepad1.right_bumper){
                transferClaw.setPosition(values.clawClsoed);
                clawServo.setPosition(values.clawOpen);
                transferLeft.setPosition(0.83);
            }
            telemetry.addData("test", transferClaw.getPosition());
//            telemetry.addData("left", leftDrawerSlide.getCurrentPosition());
//            telemetry.addData("right", rightDrawerSlide.getCurrentPosition());
//            telemetry.update();
//            if(gamepad1.left_bumper){
//                leftDrawerSlide.setPower(1);
//            }

            telemetry.update();

        }


    }



}


//package org.firstinspires.ftc.teamcode.auton;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//package org.firstinspires.ftc.yooniverse;
//
//@Autonomous(name="blue back \uD83D\uDD35")
//public clas

package org.firstinspires.ftc.teamcode.auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;



//@Disabled
@Autonomous(name="blueOb \uD83D\uDD35")
public class auton1 extends yooniversalOpMode {

    public DcMotor frontLeft;
    public DcMotor frontRight;
    public DcMotor backLeft;
    public DcMotor backRight;


    double startTime;
    @Override
    public void runOpMode() throws InterruptedException {



        setup();

        closeClaw();
        



        while (opModeInInit()) {
            telemetry.addLine("ready");
            telemetry.update();
        }


        waitForStart();
        while(opModeIsActive() && !isStopRequested()){



            frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
            frontRight = hardwareMap.get(DcMotor.class, "frontRight");
            backLeft = hardwareMap.get(DcMotor.class, "backLeft");
            backRight = hardwareMap.get(DcMotor.class, "backRight");
            startTime = System.currentTimeMillis();



            frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontRight.setTargetPosition(0);
            frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontLeft.setTargetPosition(0);
            frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backRight.setTargetPosition(0);
            backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            backLeft.setTargetPosition(0);
            backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            frontLeft.setDirection(DcMotorSimple.Direction.REVERSE);
            backLeft.setDirection(DcMotorSimple.Direction.REVERSE);



            train.resetEncoders();

                    telemetry.addData("Prop","IS NO MORE!!!");
                    telemetry.update();

                    sidish(2000);


//                    train.manualDrive(200,-200,-200,200);
                    sleep(2000);
                    telemetry.addData("done","yes");
                    telemetry.update();
                    break;

            }


        }

    private void moveVertically(DcMotor mot, int position, double power){
        mot.setPower(0);
        mot.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        mot.setTargetPosition(0);
        mot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        mot.setPower(0);

        mot.setTargetPosition(position);
        mot.setPower(power);
    }
    private void sidish(int distance){
        moveVertically(frontLeft, distance, 0.5);
        moveVertically(frontRight, -distance, 0.5);
        moveVertically(backRight, distance, 0.5);
        moveVertically(backLeft, -distance, 0.5);
    }


    }


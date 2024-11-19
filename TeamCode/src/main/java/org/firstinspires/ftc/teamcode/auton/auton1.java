//package org.firstinspires.ftc.teamcode.auton;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//package org.firstinspires.ftc.yooniverse;
//
//@Autonomous(name="blue back \uD83D\uDD35")
//public clas

package org.firstinspires.ftc.teamcode.auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;



//@Disabled
@Autonomous(name="blueOb \uD83D\uDD35")
public class auton1 extends yooniversalOpMode {



    double startTime;
    @Override
    public void runOpMode() throws InterruptedException {
        setup();
        

        setOpModeType(0);
        train.isAuton();

        closeClaw();
        



        while (opModeInInit()) {
            telemetry.addLine("ready");
            telemetry.update();
        }


        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            startTime = System.currentTimeMillis();
            train.resetEncoders();

                    telemetry.addData("Prop","IS NO MORE!!!");
                    telemetry.update();

//                    train.manualDrive(200,-200,-200,20
//
//                    );
                    //sleep(2000);
                    foward(550);
                    //rotate(-90);
                    highChamber();
                    foward(500);

                    openClaw();

                    //rotate(values.turn90DegreesClockwise);


//
//                    rotate(values.turn90DegreesCounterClockwise);
//
//                    foward(-900);
//                    side(-400);
//
//                    clawServo.setPosition(values.clawOpen);
//                    sleep(500);
//
//                    foward(-300);
//
//                    clawDown();
//                    rotate(values.turn90DegreesClockwise);
//                    sleep(50);
//                    rotate(values.turn90DegreesClockwise);
//
//
//                    side(-600);
//                    foward(400);
//
//                    clawServo.setPosition(values.clawOpen);
//                    sleep(100);
//
//                    foward(-200);
//                    clawDown();
//
//                    side(-1050);

                    break;

            }

        }


    }


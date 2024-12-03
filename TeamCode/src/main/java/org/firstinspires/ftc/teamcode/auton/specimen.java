//package org.firstinspires.ftc.teamcode.auton;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//package org.firstinspires.ftc.yooniverse;
//
//@Autonomous(name="blue back \uD83D\uDD35")
//public clas

package org.firstinspires.ftc.teamcode.auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;


//@Disabled
@Autonomous(name="Specimen \uD83C\uDF4A \uD83E\uDD6D")
public class specimen extends yooniversalOpMode {



    double startTime;
    @Override
    public void runOpMode() {
        setup();

        


        train.isAuton();

        closeClaw();
        retractClaw();
        specimenClose();



        while (opModeInInit()) {
            telemetry.addLine("ready");
            telemetry.update();
        }


        waitForStart();
        while(opModeIsActive() && !isStopRequested()){
            startTime = System.currentTimeMillis();
            train.resetEncoders();

                    telemetry.addData("Prop","IS NO!!!");
                    telemetry.update();

                    //the sleeps are necessary!!!!!!!!!!!!!!!


                    clawVertical();

                    highChamber();

                    foward(1250);

                    sleep(200);

                    highChamberDown();
                    specimenOpen();

                    sleep(400);
                    train.setPower(1);

                    openClaw();

                    foward(-200);

                    slidesResting();

                    rotate(-90);

                    side(-1200);

                    foward(-2500);

                    specimenClose();



                    break;

            }
        }


    }


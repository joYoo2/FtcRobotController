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
@Autonomous(name="Specimen \uD83E\uDD6D")
public class specimen extends yooniversalOpMode {



    double startTime;
    @Override
    public void runOpMode() {
        setup();

        


        train.isAuton();

        closeClaw();
        retractClaw();


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

                    foward(1300);

                    sleep(200);

                    highChamberDown();
                    specimenOpen();

                    sleep(400);
                    train.setPower(1);

                    openClaw();

                    foward(-200);
                    train.setFowardSpeed(0.7);

                    slidesResting();

                    rotate(-90);

                    side(-1200);

                    foward(-2200);

                    train.setFowardSpeed(0.3);
                    foward(-100);

                    specimenClose();
                    sleep(1000);

                    train.setFowardSpeed(0.7);

                    highChamber();

                    foward(2500);

                    side(1100);

                    rotate(-90);

                    highChamberSpecimenClaw();

                    sleep(500);

                    highChamberDownSpecimenClaw();

                    specimenOpen();
                    sleep(200);

                    slidesResting();

                    foward(1000);

                    side(-2000);







                    break;

            }
        }


    }


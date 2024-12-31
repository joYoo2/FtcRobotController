//package org.firstinspires.ftc.teamcode.auton;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//package org.firstinspires.ftc.yooniverse;
//
//@Autonomous(name="blue back \uD83D\uDD35")
//public clas

package org.firstinspires.ftc.teamcode.old_auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;


//@Disabled
@Autonomous(name="Specimen \uD83E\uDD6D")
public class specimen extends yooniversalOpMode {



    double startTime;
    @Override
    public void runOpMode() {
        setup();
        slides.resetEncoders();

        


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

                    train.setFowardSpeed(0.3);
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
                    clawEvenMoreVertical();

                    foward(-300);

                    side(2000);

                    train.setFowardSpeed(0.3);

                    foward(-750);

                    specimenClose();
                    sleep(500);

                    highChamberSpecimenClaw();

                    foward(800);

                    side(-2300);


                    rotate(90);
                    rotate(90);

                    foward(-670);

                    highChamberDownSpecimenClaw();


                    sleep(400);

                    specimenOpen();
                    sleep(200);

                    slidesResting();

                    train.setFowardSpeed(0.5);

                    foward(1500);

                    foward(-200);

                    side(-2500);

//                    rotate(-90);
//
//                    side(-2000);
//
//                    side(200);
//
//                    foward(-2200);
//
//                    train.setFowardSpeed(0.3);
//                    foward(-100);
//
//                    specimenClose();
//                    sleep(1000);
//
//                    train.setFowardSpeed(0.7);
//
//                     highChamberSpecimenClaw();
//
//                    foward(2500);
//
//                    side(1300);
//
//                    rotate(-90);
//
//                    sleep(500);
//
//                    train.setFowardSpeed(0.3);
//
//                    foward(-300);
//
//                    highChamberDownSpecimenClaw();
//                    sleep(400);
//
//                    train.setFowardSpeed(0.7);
//
//                    specimenOpen();
//                    sleep(200);
//
//                    foward(500);
//
//                    slidesResting();
//
//                    foward(1000);
//
//                    side(-2000);







                    break;

            }
        }


    }


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
@Autonomous(name="Sample \uD83C\uDF4A \uD83E\uDD6D")
public class block extends yooniversalOpMode {



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

                    foward(1250);

                    sleep(200);

                    highChamberDown();

                    sleep(400);
                    train.setPower(1);

                    openClaw();

                    foward(-200);

                    slidesResting();

                    train.setFowardSpeed(0.6);


                    side(-2000);

                    extendClaw();
                    foward(-340);
                    slides.resetEncoders();



                    clawDown();
                    sleep(500);

                    closeClaw();
                    sleep(500);

                    clawUp();
                    sleep(500);

                    retractClaw();

                    rotate(-140);

                    foward(200);


                    highBasket();
                    sleep(2500);

                    extendClaw();
                    sleep(500);

                    openClaw();
                    sleep(200);

                    retractClaw();
                    sleep(500);

                    slidesResting();
                    sleep(2000);



                    rotate(140);

                    side(-480);

                    foward(150);

                    extendClaw();
                    sleep(500);

                    clawDown();
                    sleep(500);

                    closeClaw();
                    sleep(500);

                    clawVertical();
                    sleep(500);

                    retractClaw();

                    side(200);

                    rotate(-140);

                    clawDown();
                    sleep(200);


                    openClaw();
                    sleep(200);

                    clawEvenMoreVertical();
                    sleep(3000);

/*//this is for second high basket
                    side(-200);

                    highBasket();
                    sleep(2500);

                    extendClaw();
                    sleep(500);

                    openClaw();
                    sleep(200);

                    retractClaw();
                    sleep(500);

                    slidesResting();
                    sleep(5000);



*/





                    //added just in case yk
/*
                    clawDown();
                    sleep(500);

                    closeClaw();
                    sleep(500);

                    clawUp();
                    sleep(200);

                    side(200);

                    rotate(-140);

                    side(-300);

                    foward(300);

                    highBasket();
                    sleep(2000);

                    foward(300);

                    openClaw();

                    foward(-400);

                    slidesResting();
                    sleep(2000);

                    rotate(140);

                    //foward(800);

                    clawDown();
                    sleep(500);

                    closeClaw();
                    sleep(500);

                    clawUp();
                    sleep(500);

                    rotate(-140);

                    highBasket();

                    foward(300);

                    openClaw();
                    sleep(200);

                    foward(-300);

                    slidesResting();



*/


                    break;

            }
        }


    }


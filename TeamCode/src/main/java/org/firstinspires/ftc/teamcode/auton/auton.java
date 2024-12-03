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
@Autonomous(name="Block old \uD83C\uDF4A")
public class auton extends yooniversalOpMode {



    double startTime;
    @Override
    public void runOpMode() {
        setup();

        


        train.isAuton();

        closeClaw();
        retractClaw();
        



        while (opModeInInit()) {
            telemetry.addLine("line up in the middle!!!");
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

                    sleep(700);
//                    train.setPower(1);
//                    train.setFowardSpeed(0.3);

                    openClaw();

                    foward(-200);

                    slidesResting();


                    side(-3050);
                    slides.resetEncoders();

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
                    sleep(5000);






                    break;

            }
        }


    }


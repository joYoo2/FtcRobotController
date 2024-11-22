//package org.firstinspires.ftc.teamcode.auton;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//package org.firstinspires.ftc.yooniverse;
//
//@Autonomous(name="blue back \uD83D\uDD35")
//public clas

package org.firstinspires.ftc.teamcode.auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;



//@Disabled
@Autonomous(name="Specimen \uD83C\uDF4A \uD83E\uDD6D")
public class auton1 extends yooniversalOpMode {



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

                    telemetry.addData("Prop","IS NO ded!!!");
                    telemetry.update();

                    //the sleeps are necessary!!!!!!!!!!!!!!!
                    clawVertical();


                    highChamber();

                    foward(1250);

                    sleep(200);

                    highChamberDown();

                    sleep(700);

                    openClaw();

                    foward(-200);

                    slidesResting();


                    side(-3050);
                    slides.resetEncoders();
                    //added just in case yk

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






                    break;

            }
        }


    }


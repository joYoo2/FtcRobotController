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
//@Autonomous(name="Sample \uD83C\uDF4A \uD83E\uDD6D")
public class block extends yooniversalOpMode {



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

                    clawVertical();

                    highChamber();

                    foward(1250);

                    highChamberDown();

                    sleep(300);
                    train.setPower(1);

                    openClawLarge();

                    train.setFowardSpeed(0.7);

                    foward(-200);

                    slidesResting();


                    side(-2130);

                    extendClaw();
                    foward(-280);
                    slides.resetEncoders();



                    clawDown();
                    sleep(500);

                    closeClaw();
                    sleep(500);

                    clawUp();
                    sleep(500);

                    retractClaw();

                    rotate(-130);

                    foward(200);


                    highBasket();
                    //2500
                    for(long start = System.currentTimeMillis(); System.currentTimeMillis() < start + 2500;){
                        telemetry.addData("left crane amps", slides.getAmpsLeft());
                        telemetry.addData("right crane amps", slides.getAmpsRight());
                        telemetry.update();;
                    }

                    extendClaw();
            for(long start = System.currentTimeMillis(); System.currentTimeMillis() < start + 500;){
                telemetry.addData("left crane amps", slides.getAmpsLeft());
                telemetry.addData("right crane amps", slides.getAmpsRight());
                telemetry.update();;
            }

                    openClawLarge();
            for(long start = System.currentTimeMillis(); System.currentTimeMillis() < start + 200;){
                telemetry.addData("left crane amps", slides.getAmpsLeft());
                telemetry.addData("right crane amps", slides.getAmpsRight());
                telemetry.update();;
            }

                    retractClaw();
            for(long start = System.currentTimeMillis(); System.currentTimeMillis() < start + 200;){
                telemetry.addData("left crane amps", slides.getAmpsLeft());
                telemetry.addData("right crane amps", slides.getAmpsRight());
                telemetry.update();;
            }

                    slidesResting();
                    sleep(1700);



                    rotate(131);

                    side(-350);

                    foward(160);

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

                    rotate(-130);

                    side(-220);

                    clawUp();

                    highBasket();
                    sleep(2500);

                    extendClaw();
                    sleep(500);

                    openClawLarge();
                    sleep(200);

                    retractClaw();
                    sleep(300);

                    slidesResting();
                    sleep(5000);

//                    openClaw();
//                    sleep(200);
//
//                    clawEvenMoreVertical();
//                    sleep(3000);

/*//this is for second high basket
                    side(-200);





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


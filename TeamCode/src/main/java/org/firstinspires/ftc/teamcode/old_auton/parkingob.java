//package org.firstinspires.ftc.teamcode.auton;
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//package org.firstinspires.ftc.yooniverse;
//
//@Autonomous(name="blue back \uD83D\uDD35")
//public clas

package org.firstinspires.ftc.teamcode.old_auton;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;


@Disabled
@Autonomous(name="Parking Observation ⛴\uFE0F")
public class parkingob extends yooniversalOpMode {



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

                    clawVertical();
                    foward(50);

                    side(3000);




                    break;

            }
        }


    }


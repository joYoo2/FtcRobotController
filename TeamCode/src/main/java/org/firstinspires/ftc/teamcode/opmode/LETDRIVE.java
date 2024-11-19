package org.firstinspires.ftc.teamcode.opmode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.yooniverse.yooniversalOpMode;
import org.firstinspires.ftc.teamcode.yooniverse.values;
@TeleOp(name="LETDRIVE", group="yooniverse")

public class LETDRIVE extends yooniversalOpMode{
    @Override
    public void runOpMode(){
        setup();
        telemetry.addData("status", "initialized");
        telemetry.update();

        train.setPower(1);

        setOpModeType(2);

        telemetry.update();
        boolean byPower = false;


        telemetry.update();
        waitForStart();
        extenderRight.setPosition(1-values.clawRetract);
        extenderLeft.setPosition(values.clawRetract);

        clawStriaght();
        while(opModeIsActive()){
            telemetry.addData("Status", "Running");
            telemetry.addData("lef:", slides.getCurrentLeftPosition());


            train.manualDrive(-gamepad1.left_stick_y + gamepad1.left_stick_x + gamepad1.right_stick_x,
                    -gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x,
                    -gamepad1.left_stick_y - gamepad1.left_stick_x + gamepad1.right_stick_x,
                    -gamepad1.left_stick_y + gamepad1.left_stick_x - gamepad1.right_stick_x);

            if(gamepad2.circle){
                extendClaw();
            }
            if(gamepad2.square){
                retractClaw();
            }

            if(gamepad1.triangle){
                clawUp();
            }
            if(gamepad1.cross){
                clawDown();
            }
//            if(gamepad2.triangle){
//
//            }
            if(gamepad1.circle){
                openClaw();
            }
            if(gamepad1.square){
                closeClaw();
            }


            if(gamepad1.left_bumper){
                clawVertical();
            }

            if(gamepad2.right_trigger > 0.05 || gamepad2.left_trigger > 0.05) {
                byPower = true;
                slides.move(gamepad2.right_trigger - gamepad2.left_trigger, true);
            }else{
                slides.move(slides.getCurrentRightPosition(), false);
            }

            slides.craneMaintenance();

            telemetry.addData("Left Crane Motor Position", slides.getCurrentLeftPosition());
            telemetry.addData("Right Crane Motor Position", slides.getCurrentRightPosition());
            telemetry.addData("Trigger total:", gamepad2.right_trigger + gamepad2.left_trigger);
            telemetry.addData("Left Crane Amps", slides.getAmpsLeft());
            telemetry.addData("Right Crane Amps", slides.getAmpsRight());
            telemetry.update();
        }

    }
}

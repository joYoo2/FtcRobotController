package org.firstinspires.ftc.teamcode.detection;

import com.pedropathing.follower.Follower;
import com.pedropathing.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.detection.ManualInput;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import org.firstinspires.ftc.teamcode.detection.Vision;
import org.firstinspires.ftc.teamcode.yooniverse.values;

@TeleOp(group = "Tests", name = "Vision Test")
public class VisionTest extends OpMode {
    Vision v;
    Follower f;
    intake i;
    ManualInput manualInput;
    Gamepad g1,p1;
    public int sState;
    public Timer sTimer, timer;


    @Override
    public void init() {
        sTimer = new Timer();
        timer = new Timer();
        i = new intake(hardwareMap,telemetry);
        f = new Follower(hardwareMap);
        manualInput = new ManualInput(telemetry, gamepad1, 0, true);
        v = new Vision(hardwareMap, telemetry, new int[]{1, 2}, f, manualInput);

        f.setStartingPose(values.teleopStart);

        g1 = new Gamepad();
        p1 = new Gamepad();

        sTimer = new Timer();
        timer = new Timer();
    }

    @Override
    public void init_loop() {
        manualInput.update(gamepad2);
        telemetry.update();
    }
    @Override
    public void loop() {
        p1.copy(g1);
        g1.copy(gamepad1);

        if (g1.y && !p1.y) {
            v.find();
            f.followPath(v.toTarget());
        }
        if (g1.dpad_left) {
            if (timer.getElapsedTimeSeconds() > 0.25) {
                v.find();
                f.followPath(v.toTarget());
                timer.resetTimer();
            }
        }
        if(g1.right_bumper && !p1.right_bumper) {
            f.followPath(v.toTarget());
        }

        f.update();
        telemetry.update();
    }
    private void submersible() {
        telemetry.addData("Submersible State", sState);

        switch (sState) {
            case 0:
                i.ground();
                i.open();
                setSubmersibleState(1);
                break;
            case 1:
                if(sTimer.getElapsedTimeSeconds() > 0.1) {
                    i.close();
                    setSubmersibleState(2);
                }
                break;
            case 2:
                if (sTimer.getElapsedTimeSeconds() > 0.15) {
                    i.hover();
                    setSubmersibleState(-1);
                }
                break;
        }
    }

    public void setSubmersibleState(int x) {
        sState = x;
        sTimer.resetTimer();
    }

    public void startSubmersible() {
        setSubmersibleState(0);
    }
}


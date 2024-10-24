package org.firstinspires.ftc.teamcode.yooniverse;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

public class easierYoo {
    IMU imu;
    public easierYoo(HardwareMap hardwareMap){
        imu = hardwareMap.get(IMU.class, "imu");
        imu.initialize(new IMU.Parameters(
                new RevHubOrientationOnRobot(
                        RevHubOrientationOnRobot.LogoFacingDirection.UP,
                        RevHubOrientationOnRobot.UsbFacingDirection.RIGHT)
        ));
    }

    public double getYaw(){
        return imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES);
    }
    public double getPitch(){
        return imu.getRobotYawPitchRollAngles().getPitch(AngleUnit.DEGREES);
    }
    public double getRoll(){
        return imu.getRobotYawPitchRollAngles().getRoll(AngleUnit.DEGREES);
    }

    public void resetYaw(){
        imu.resetYaw();
    }

    public YawPitchRollAngles getAngles(){
        return imu.getRobotYawPitchRollAngles();
    }
}
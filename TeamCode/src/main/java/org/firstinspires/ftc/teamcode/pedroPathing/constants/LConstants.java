package org.firstinspires.ftc.teamcode.pedroPathing.constants;

import com.pedropathing.localization.*;
import com.pedropathing.localization.constants.*;

public class LConstants {
    static {
        ThreeWheelConstants.forwardTicksToInches = 0.000542385232366520; //.0005
        ThreeWheelConstants.strafeTicksToInches = 0.0005483253064505093; //.00055
        ThreeWheelConstants.turnTicksToInches = 0.0005345773856251268; //.00053507
        ThreeWheelConstants.leftY = 4.475;
        ThreeWheelConstants.rightY = -4.25;
        ThreeWheelConstants.strafeX = 1;
        ThreeWheelConstants.leftEncoder_HardwareMapName = "backLeft";
        ThreeWheelConstants.rightEncoder_HardwareMapName = "frontRight";
        ThreeWheelConstants.strafeEncoder_HardwareMapName = "backRight";
        ThreeWheelConstants.leftEncoderDirection = Encoder.REVERSE;
        ThreeWheelConstants.rightEncoderDirection = Encoder.FORWARD;
        ThreeWheelConstants.strafeEncoderDirection = Encoder.REVERSE;
    }
}





package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class TeleopRobot extends BaseRobot {
    TankDrive drive;
    Ultrasonic frontSensor;

    public TeleopRobot(RoboWar game) {
        super(game);

        drive = new TankDrive(2);
        addFeature(drive);

        frontSensor = new Ultrasonic(Ultrasonic.Direction.FRONT);
        addFeature(frontSensor);

        featureComplete();
    }

    @Override
    public void update() {
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            drive.setLeftAcc(-1);
            drive.setRightAcc(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            drive.setLeftAcc(1);
            drive.setRightAcc(-1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            drive.setLeftAcc(1);
            drive.setRightAcc(1);
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            drive.setLeftAcc(-1);
            drive.setRightAcc(-1);
        } else {
            drive.setLeftAcc(0);
            drive.setRightAcc(0);
        }

        Gdx.app.log("Range", String.valueOf(frontSensor.getReading()));

        super.update();
    }
}

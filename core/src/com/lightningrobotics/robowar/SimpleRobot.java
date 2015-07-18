package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.ContactListener;

public class SimpleRobot extends BaseRobot {
    TankDrive drive;
    Ultrasonic frontSensor;

    public SimpleRobot(RoboWar game) {
        super(game);

        drive = new TankDrive(2);
        addFeature(drive);

        frontSensor = new Ultrasonic(Ultrasonic.Direction.FRONT);
        addFeature(frontSensor);

        featureComplete();
    }

    int counter = 0;
    @Override
    public void update() {
        if (++counter % 60 * 3 == 0) {
            drive.setLeftAcc(RoboWar.rand.nextFloat() * 10f - 5f);
            drive.setRightAcc(RoboWar.rand.nextFloat() * 10f - 5f);
        }
        Gdx.app.log("Range", String.valueOf(frontSensor.getReading()));

        super.update();
    }
}

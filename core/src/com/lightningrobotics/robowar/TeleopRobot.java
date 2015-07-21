package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class TeleopRobot extends BaseRobot {
    TankDrive drive;
    LaserWeapon laser;
    Ultrasonic forwardUltrasonic;
    LIDAR forwardLIDAR;

    public TeleopRobot(RoboWar game) {
        super(game);

        joinBlueAlliance();
        setTexture(Assets.blueRobot, 0.5f, 1);

        drive = new TankDrive(6);
        addFeature(drive);

        forwardUltrasonic = new Ultrasonic(Ultrasonic.Direction.FRONT);
        addFeature(forwardUltrasonic);

        forwardLIDAR = new LIDAR(LIDAR.Direction.FRONT);
        addFeature(forwardLIDAR);

        laser = new LaserWeapon(LaserWeapon.Direction.FRONT);
        addFeature(laser);

        featureComplete();
    }

    float maxx = 0;
    float maxy = 0;

    @Override
    public void update() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
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
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            if (laser.isOn())
                laser.setOff();
            else
                laser.setOn();
        } else {
            drive.setLeftAcc(0);
            drive.setRightAcc(0);
        }

//        Gdx.app.log("pos", String.valueOf(getBody().getPosition()));
//        Gdx.app.log("zone", getAlliance().toString());

        super.update();
    }
}

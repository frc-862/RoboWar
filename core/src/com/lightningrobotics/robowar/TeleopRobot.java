package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class TeleopRobot extends FullFeaturedRobot {

    public TeleopRobot(RoboWar game) {
        super(game);
        featureComplete();
    }

    @Override
    public void update() {
        TankDrive drive = getDrive();

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
            if (getLaser().isOn())
                getLaser().setOff();
            else
                getLaser().setOn();
        } else {
            drive.setLeftAcc(0);
            drive.setRightAcc(0);
        }

        if (frontBump.isBumpped())
            Gdx.app.log("Teleop", "Get off the wall");

        super.update();
    }
}

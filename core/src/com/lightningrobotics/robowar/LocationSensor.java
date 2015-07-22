package com.lightningrobotics.robowar;

import com.badlogic.gdx.math.Vector2;

public class LocationSensor extends RobotFeature {
    Vector2 location;

    public LocationSensor() {
        super(0, 0);
    }

    @Override
    public boolean attachToRobot(RobotDefinition def) {
        return super.attachToRobot(def);
    }

    @Override
    public void update(RobotDefinition def) {
        location = def.getBody().getPosition();
    }

    public Vector2 getReading() {
        return location;
    }
}

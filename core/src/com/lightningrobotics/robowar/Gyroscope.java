package com.lightningrobotics.robowar;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

public class Gyroscope extends RobotFeature {
    float gyro;

    public Gyroscope() {
        super(0, 0);
    }

    @Override
    public boolean attachToRobot(RobotDefinition def) {
        return super.attachToRobot(def);
    }

    @Override
    public void update(RobotDefinition def) {
        gyro = (float) -Math.toDegrees(def.getBody().getAngle()) % 360;
    }

    public float getReading() {
        if (gyro < 0) {
           return gyro + 360;
        }

        return gyro;
    }
}

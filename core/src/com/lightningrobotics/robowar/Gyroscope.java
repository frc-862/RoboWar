package com.lightningrobotics.robowar;

public class Gyroscope extends RobotFeature {
    private float gyro;

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

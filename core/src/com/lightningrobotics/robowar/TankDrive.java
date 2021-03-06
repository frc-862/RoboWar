package com.lightningrobotics.robowar;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class TankDrive extends RobotFeature {
    private int motorCount;
    private float leftAcc = 0f;
    private float rightAcc = 0f;
    private Vector2 accel = new Vector2();
    private Vector2 forceLocation = new Vector2();
    private Vector2 tmp = new Vector2();

    public TankDrive(int motors) {
        super(100f * fixMotorCount(motors), 10f * fixMotorCount(motors));
        motorCount = fixMotorCount(motors);
    }

    private static int fixMotorCount(int motors) {
        return Math.min(Math.max(motors / 2, 1), 3) * 2;
    }

    public float getLeftAcc() {
        return leftAcc;
    }

    public void setLeftAcc(float leftAcc) {
        this.leftAcc = Math.min(Math.max(leftAcc, -1), 1);
    }

    public float getRightAcc() {
        return rightAcc;
    }

    public void setRightAcc(float rightAcc) {
        this.rightAcc = Math.min(Math.max(rightAcc, -1), 1);
    }

    @Override
    public void update(RobotDefinition def) {
        float rot = (float) (def.getBody().getTransform().getRotation() + Math.PI / 2);
        float x = MathUtils.cos(rot);
        float y = MathUtils.sin(rot);

        accel.set(leftAcc * x * motorCount, leftAcc * y * motorCount);
        def.getBody().applyForce(accel, def.getBody().getWorldPoint(forceLocation.set(-def.getWidth() / 2, 0)), true);
        accel.set(rightAcc * x * motorCount, rightAcc * y * motorCount);
        def.getBody().applyForce(accel, def.getBody().getWorldPoint(forceLocation.set(def.getWidth() / 2, 0)), true);

        updateFriction(def.getBody());
    }

    private Vector2 getLateralVelocity(Body body) {
        Vector2 currentRightNormal = body.getWorldVector(tmp.set(1, 0));
        return currentRightNormal.scl(currentRightNormal.dot(body.getLinearVelocity()));
    }

    private void updateFriction(Body body) {
        Vector2 impulse = getLateralVelocity(body).scl(-body.getMass() * 0.02f);
        body.applyLinearImpulse(impulse, body.getWorldCenter(), true);
    }
}

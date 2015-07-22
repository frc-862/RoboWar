package com.lightningrobotics.robowar;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by phurley on 7/18/15.
 */
public class LaserWeapon extends RobotFeature {
    private RayCastCallback callback;
    private Vector2 sensorDirection = new Vector2();
    private Vector2 sensorBegin = new Vector2();
    private Vector2 sensorEnd = new Vector2();
    private float sensorMaxRange;
    private ShapeRenderer shapeRenderer;
    private float headingOffset;
    private boolean on;
    private float reading;
    private Fixture target;

    public LaserWeapon(Direction dir) {
        super(0, 0);
        shapeRenderer = new ShapeRenderer();
        sensorMaxRange = 30;
        on = false;

        sensorDirection.set(0, sensorMaxRange);
        switch (dir) {
            case FRONT:
                headingOffset = (float) Math.PI / 2;
                break;
            case BACK:
                headingOffset = 180;
                break;
            case LEFT:
                headingOffset = -90;
                break;
            case RIGHT:
                headingOffset = 90;
                break;
        }

        Object self = this;

        callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                reading = fraction * sensorMaxRange;
                sensorEnd.set(point);
                target = fixture;
                return 0;
            }
        };
    }

    @Override
    public boolean attachToRobot(RobotDefinition def) {
        Body body = def.getBody();
        return super.attachToRobot(def);
    }

    public boolean isOn() {
        return on;
    }

    public void setOff() {
        this.on = false;
    }

    public void setOn() {
        this.on = true;
    }

    @Override
    public void update(RobotDefinition def) {
        if (isOn()) {
            Body body = def.getBody();
            World world = body.getWorld();

            reading = -1;
            target = null;

            sensorBegin.set(body.getPosition());
            sensorDirection.setAngleRad(body.getAngle() + headingOffset);
            sensorEnd.set(sensorDirection).add(sensorBegin);

            world.rayCast(callback, sensorBegin, sensorEnd);

            if ((target != null) && (target.getBody().getUserData() instanceof BaseRobot)) {
                BaseRobot zapped = (BaseRobot) target.getBody().getUserData();
                zapped.damage(0.1f);
            }

            shapeRenderer.setProjectionMatrix(def.getGame().getCamera().combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(1, 0, 0, 1);
            shapeRenderer.line(sensorBegin, sensorEnd);
            shapeRenderer.end();
        }
    }

    public float getSensorMaxRange() {
        return sensorMaxRange;
    }

    public float getReading() {
        return reading;
    }

    enum Direction {
        FRONT, BACK, LEFT, RIGHT
    }

}

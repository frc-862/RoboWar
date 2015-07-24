package com.lightningrobotics.robowar;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.RayCastCallback;
import com.badlogic.gdx.physics.box2d.World;

public class Ultrasonic extends RobotFeature {
    private final float sweep = (float) Math.toRadians((double) 30f);
    private RayCastCallback callback;
    private Vector2 sensorDirection = new Vector2();
    private Vector2 sensorBegin = new Vector2();
    private Vector2 sensorEnd = new Vector2();
    private float sensorMaxRange;
    private ShapeRenderer shapeRenderer;
    private float headingOffset;
    private float reading;
    private Fixture target;

    public Ultrasonic(Direction dir) {
        super(0, 0);
        shapeRenderer = new ShapeRenderer();
        sensorMaxRange = 30;

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
                if (fixture.isSensor()) return 1;

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

    @Override
    public void update(RobotDefinition def) {
        Body body = def.getBody();
        World world = body.getWorld();

        reading = -1;
        target = null;

        sensorBegin.set(body.getPosition());

        float[] points = new float[30];
        float angle = sweep / -2;
        float sweepDelta = sweep / points.length;

        shapeRenderer.setProjectionMatrix(def.getGame().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);

        for (int i = 0; i < points.length; ++i) {
            sensorDirection.setAngleRad(body.getAngle() + headingOffset + angle);
            sensorEnd.set(sensorDirection).add(sensorBegin);
            angle += sweepDelta;

            world.rayCast(callback, sensorBegin, sensorEnd);
            points[i] = reading;
//            shapeRenderer.line(sensorBegin, sensorEnd);
        }

        shapeRenderer.end();

        java.util.Arrays.sort(points);
        reading = (points[0] + points[1] + points[2]) / 3;
    }

    public float getSensorMaxRange() {
        return sensorMaxRange;
    }

    public float getReading() {
        return reading;
    }
}

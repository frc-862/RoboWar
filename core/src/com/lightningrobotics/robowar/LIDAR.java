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
public class LIDAR extends RobotFeature {
    enum Direction {
        FRONT, BACK, LEFT, RIGHT
    }

    private RayCastCallback callback;
    private Vector2 sensorDirection = new Vector2();
    private Vector2 sensorBegin = new Vector2();
    private Vector2 sensorEnd = new Vector2();
    private float sensorMaxRange;
    private Body body;
    private ShapeRenderer shapeRenderer;
    private float headingOffset;

    @Override
    public boolean attachToRobot(RobotDefinition def) {
        body = def.getBody();
        return super.attachToRobot(def);
    }

    private float reading;

    public LIDAR(Direction dir) {
        super(0,0);
        shapeRenderer = new ShapeRenderer();
        sensorMaxRange = 30;

        sensorDirection.set(0,sensorMaxRange);
        switch (dir)
        {
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

        callback = new RayCastCallback() {
            @Override
            public float reportRayFixture(Fixture fixture, Vector2 point, Vector2 normal, float fraction) {
                if (fixture.getBody() != body){
                    reading = fraction * sensorMaxRange;
                    return 0;
                }
                return 1;
            }
        };
    }


    @Override
    public void update(RobotDefinition def) {
        Body body = def.getBody();
        World world = body.getWorld();

        sensorBegin.set(body.getPosition());
        sensorDirection.setAngleRad(body.getAngle() + headingOffset);
        sensorEnd.set(sensorDirection).add(sensorBegin);

        world.rayCast(callback, sensorBegin, sensorEnd);

        shapeRenderer.setProjectionMatrix(def.getGame().getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(0, 0, 0, 1);
        shapeRenderer.line(sensorBegin, sensorEnd);
        shapeRenderer.end();
    }

    public float getSensorMaxRange() {
        return sensorMaxRange;
    }

    public float getReading() {
        return reading;
    }

}

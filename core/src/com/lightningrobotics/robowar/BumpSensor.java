package com.lightningrobotics.robowar;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class BumpSensor extends RobotFeature implements ContactListener {
    private Direction direction;
    private boolean bumpped;

    public BumpSensor(Direction direction) {
        super(0, 0);
        this.direction = direction;
        bumpped = false;
    }

    @Override
    public boolean attachToRobot(RobotDefinition def) {
        return true;
    }

    @Override
    public void buildBody(Body body) {
        CircleShape bumpShape = new CircleShape();
        bumpShape.setRadius(0.01f);

        switch (direction) {
            case FRONT:
                bumpShape.setPosition(new Vector2(0f, 0.5f));
                break;
            case BACK:
                bumpShape.setPosition(new Vector2(0f, -0.5f));
                break;
            case LEFT:
                bumpShape.setPosition(new Vector2(-0.25f, 0f));
                break;
            case RIGHT:
                bumpShape.setPosition(new Vector2(0.25f, 0f));
                break;
        }

        FixtureDef fdef = new FixtureDef();
        fdef.shape = bumpShape;
        fdef.isSensor = true;

        Fixture fixture = body.createFixture(fdef);
        fixture.setUserData(this);
    }

    public boolean isBumpped() {
        return bumpped;
    }

    @Override
    public void update(RobotDefinition def) {
    }

    @Override
    public void beginContact(Contact contact) {
        bumpped = true;
    }

    @Override
    public void endContact(Contact contact) {
        bumpped = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}

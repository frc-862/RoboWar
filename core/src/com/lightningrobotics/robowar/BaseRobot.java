package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class BaseRobot extends Entity implements ContactListener {
    RobotDefinition robotDefinition;

    public BaseRobot(RoboWar game) {
        this(game, 0, 0);
        setTexture(Assets.tank, 0.5f, 1f);
//        setRegion(Assets.tank);
    }

    public BaseRobot(RoboWar game, float x, float y) {
        robotDefinition = new RobotDefinition(game);
    }

    public Body getBody() {
        return robotDefinition.getBody();
    }

    public boolean isAlive() {
        return robotDefinition.isAlive();
    }

    public boolean reapIfDead() {
        if (!isAlive()) {
            robotDefinition.kill();
            return true;
        }
        return false;
    }

    public void featureComplete() {
        float x = RoboWar.rand.nextFloat() * Constants.width - Constants.width / 2;
        float y = RoboWar.rand.nextFloat() * Constants.height - Constants.height / 2;

        Body body = robotDefinition.buildBody(x, y, 0.5f, 1);
        body.setUserData(this);
    }

    public void update() {
        robotDefinition.update();
    }

    public void addFeature(RobotFeature feature) {
        robotDefinition.addFeature(feature);
    }

    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("BaseRobot", "Bump");
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (float ni : impulse.getNormalImpulses()) {
//            Gdx.app.log("   normal", String.valueOf(ni));
            // robotDefinition.damage(ni * 3);
        }
    }

    public void damage(float v) {
        robotDefinition.damage(v);
    }
}

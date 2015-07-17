package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.Body;

public class RobotFeature {
    private float cost;
    private float weight;

    public RobotFeature(float _cost, float _weight) {
        cost = _cost;
        weight = _weight;
    }

    @SuppressWarnings("unused")
    public boolean attachToRobot(RobotDefinition def) {
        return true;
    }

    @SuppressWarnings("unused")
    public void update(RobotDefinition def) {
    }

    @SuppressWarnings("unused")
    public void buildBody(Body body) {
//        Fixture fixture = body.getFixtureList().get(0);
//        fixture.setDensity(5f);
//        body.resetMassData();
    }

    public float getCost()
    {
        return cost;
    }

    public float getWeight()
    {
        return weight;
    }
}

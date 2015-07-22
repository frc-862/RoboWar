package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

import java.util.LinkedList;
import java.util.List;

public class RobotDefinition {
    public BaseRobot.Alliance alliance;

    public List<RobotFeature> features = new LinkedList<>();
    public float weightLimit = 120;
    public float cash = 4000;
    public float weight = 10;
    public float maxRotation = 1;
    public float maxAcceleration = 2;
    public float maxVelocity = 10;
    public float width;
    public float length;
    public float health = 10;
    RoboWar game;
    Body body;

    public RobotDefinition(RoboWar game) {
        this.game = game;
        alliance = BaseRobot.Alliance.unset;

        body = null;
    }

    public void joinBlueAlliance() {
        alliance = BaseRobot.Alliance.blue;
    }

    public void joinRedAlliance() {
        alliance = BaseRobot.Alliance.red;
    }

    public BaseRobot.Alliance getAlliance() {
        return alliance;
    }

    public RoboWar getGame() {
        return game;
    }

    @SuppressWarnings("unused")
    public void update() {
//        if (isAlive()) {
        for (RobotFeature feature : features) {
            feature.update(this);
        }
//        }
    }

    public void addFeature(RobotFeature feature) throws InvalidRobot {
        if (feature.getCost() > cash)
            throw new InvalidRobot("You spent too much");
        cash -= feature.getCost();

        if (feature.getWeight() + weight > weightLimit)
            throw new InvalidRobot("Your robot is too heavy");
        weight += feature.getWeight();

        if (feature.attachToRobot(this))
            features.add(feature);
    }

    public float getWidth() {
        return width;
    }

    public float getLength() {
        return length;
    }

    public Body buildBody(float x, float y, float width, float length) {
        this.length = length;
        this.width = width;

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = game.getWorld().createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2, length / 2);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        //TODO base density on robot weight
        fixtureDef.density = 1f;
        fixtureDef.restitution = 0.05f;
        fixtureDef.friction = 0.2f;
        body.createFixture(shape, 1);

        for (RobotFeature feature : features)
            feature.buildBody(body);

        body.setAngularDamping(5);
        body.setLinearDamping(2);

        shape.dispose();
        return body;
    }

    public Body getBody() {
        return body;
    }

    public boolean damage(float damage) {
        health -= damage;
        return isAlive();
    }

    public boolean isAlive() {
        return health > 0f;
    }

    public void kill() {
        if (body != null) {
            game.getWorld().destroyBody(body);
            body = null;
        }
    }
}

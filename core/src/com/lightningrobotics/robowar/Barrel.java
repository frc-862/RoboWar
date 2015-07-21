package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.*;

public class Barrel extends Entity {
    BaseRobot.Alliance alliance;
    RoboWar game;
    Body body;

    public Barrel(RoboWar game, float x, float y)
    {
        setTexture(Assets.barrel, 0.5f, 0.5f);
        this.game = game;
        body = buildBody(x,y);
        alliance = BaseRobot.Alliance.unset;
    }

    public BaseRobot.Alliance otherAlliance() {
        if (getAlliance() == BaseRobot.Alliance.blue)
            return BaseRobot.Alliance.red;

        return BaseRobot.Alliance.blue;
    }

    public BaseRobot.Alliance getAlliance() {
        return alliance;
    }

    void joinRedAlliance() {
        alliance = BaseRobot.Alliance.red;
        setTexture(Assets.redBarrel, 0.5f, 0.5f);
    }

    void joinBlueAlliance() {
        alliance = BaseRobot.Alliance.blue;
        setTexture(Assets.blueBarrel, 0.5f, 0.5f);
    }

    public RoboWar getGame() {
        return game;
    }

    boolean captureBonus = false;
    boolean eogBonus = false;
    public void update() {
        if (!captureBonus)
        {
            if (currentZone() != alliance && currentZone() != BaseRobot.Alliance.unset)
            {
                captureBonus = true;
                getGame().getScore().incrementScore(otherAlliance(), 25);
            }
        }

        if (!eogBonus) {
            if (getGame().remainingSeconds() == 0 && currentZone() == getAlliance()) {
                getGame().getScore().incrementScore(getAlliance(), 25);
                eogBonus = true;
            }
        }
    }

    public Body buildBody(float x, float y) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        body = game.getWorld().createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(0.25f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        body.createFixture(shape, 1);
        body.setUserData(this);

        shape.dispose();
        return body;
    }

    public Body getBody() {
        return body;
    }

}

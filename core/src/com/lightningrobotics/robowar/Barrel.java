package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.*;

public class Barrel extends Entity {
    RoboWar game;
    Body body;

    public Barrel(RoboWar game, float x, float y)
    {
        setTexture(Assets.barrel, 0.5f, 0.5f);
        this.game = game;
        body = buildBody(x,y);
    }

    public RoboWar getGame() {
        return game;
    }

    public void update() {
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

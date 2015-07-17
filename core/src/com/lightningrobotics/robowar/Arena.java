package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;

public class Arena {
    private final Body body;

    public Arena(RoboWar game) {
        final float height = game.getHeight();
        final float width = game.getWidth();

//        buildWall(game, 0, 0, height, width);
        buildWall(game, -width / 2, -height / 2, height, 0);
        buildWall(game, -width / 2, -height / 2, 0, width);
        buildWall(game, width / 2, height / 2, 0, width);
        buildWall(game, width / 2, height / 2, height, 0);

        body = null;
    }

    public Body buildWall(RoboWar game, float x, float y, float h, float w)
    {
        BodyDef wall = new BodyDef();
        wall.type = BodyDef.BodyType.StaticBody;
        wall.position.set(x, y);
        Body body = game.getWorld().createBody(wall);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(w, h);

        body.createFixture(shape, 1);

        return body;
    }
}

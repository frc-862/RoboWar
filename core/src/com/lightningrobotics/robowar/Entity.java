package com.lightningrobotics.robowar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;

class Entity {
    private Sprite sprite;

    void setTexture(Texture texture, float width, float height) {
        sprite = new Sprite(texture);
        sprite.setSize(width, height);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    Body getBody() {
        return null;
    }

    public void update() {
    }

    public boolean reapIfDead() {
        return false;
    }

    public void render(SpriteBatch batch) {
        Body body = getBody();
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }

    SimpleRobot.Alliance currentZone() {
        if (getBody() == null)
            return SimpleRobot.Alliance.unset;

        float x = getBody().getPosition().x;

        if (Math.abs(x) < 7f)
            return SimpleRobot.Alliance.unset;

        if (x > 0)
            return SimpleRobot.Alliance.blue;

        return SimpleRobot.Alliance.red;
    }


    public Sprite getSprite() {
        return sprite;
    }
}

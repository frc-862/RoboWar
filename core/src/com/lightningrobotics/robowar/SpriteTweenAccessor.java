package com.lightningrobotics.robowar;

import aurelienribon.tweenengine.TweenAccessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteTweenAccessor implements TweenAccessor<Sprite> {
    public enum Mode {SCALE, ALPHA, POSITION };

    @Override
    public int getValues(Sprite sprite, int i, float[] floats) {
        switch(Mode.values()[i]) {
            case SCALE:
                floats[0] = sprite.getScaleX();
                return 1;

            case ALPHA:
                floats[0] = sprite.getColor().a;
                return 1;

            case POSITION:
                floats[0] = sprite.getX();
                floats[1] = sprite.getY();
                return 2;

            default:
                assert false;
                return -1;
        }
    }

    @Override
    public void setValues(Sprite sprite, int i, float[] floats) {
        switch(Mode.values()[i]) {
            case SCALE:
                sprite.setScale(floats[0]);
                break;

            case ALPHA:
                Color color = sprite.getColor();
                sprite.setColor(color.r, color.g, color.b, floats[0]);
                break;

            case POSITION:
                sprite.setX(floats[0]);
                sprite.setY(floats[1]);
                break;

            default:
                assert false;
        }
    }
}

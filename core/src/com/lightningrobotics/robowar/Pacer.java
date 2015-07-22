package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class Pacer extends FullFeaturedRobot {

    public Pacer(RoboWar game) {
        super(game);

        joinRedAlliance();
        setTexture(Assets.redRobot, 0.5f, 1);

        featureComplete();
    }

    public boolean rotate(float desired)
    {
        float tolerance = 5;
        float coff = 0.005f;

        float offset = getGyro().getReading() - desired;

        if (offset > 180) offset -= 360;
        if (offset < -180) offset += 360;

        if (Math.abs(offset) <= tolerance)
        {
            getDrive().setLeftAcc(0);
            getDrive().setRightAcc(0);
            return true;
        }

        getDrive().setLeftAcc(offset * -coff);
        getDrive().setRightAcc(offset * coff);
        return false;
    }

    enum State { rotate2blue, red2blue, rotate2red, blue2red };
    State state = State.rotate2blue;

    @Override
    public void update() {
        switch (state) {
            case rotate2blue:
                if (rotate(90))
                    state = State.red2blue;
                break;
            case red2blue:
                if (currentZone() == Alliance.blue) {
                    state = State.rotate2red;
                } else {
                    getDrive().setLeftAcc(1);
                    getDrive().setRightAcc(1);
                }
                break;
            case rotate2red:
                if (rotate(270))
                    state = State.blue2red;
                break;
            case blue2red:
                if (currentZone() == Alliance.red) {
                    state = State.rotate2blue;
                } else {
                    getDrive().setLeftAcc(1);
                    getDrive().setRightAcc(1);
                }
                break;
        }

        super.update();
    }
}

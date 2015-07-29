package com.lightningrobotics.robowar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

import java.util.LinkedList;
import java.util.List;

public class BarrelBounce extends Game {
    public RoboWar getRoboWar() {
        return roboWar;
    }

    RoboWar roboWar;

    @Override
    public void create() {
        roboWar = new RoboWar();
        this.setScreen(new SplashScreen(this));
    }

    public void joinBlueAlliance(Class<BaseRobot> r) {
        if (r != null)
            roboWar.joinBlueAlliance(r);
    }

    public void joinRedAlliance(Class<BaseRobot> r) {
        if (r != null)
            roboWar.joinRedAlliance(r);
    }

    public void selectRobots() {
        this.setScreen(new RoboLoad(this));
    }

    public void playGame() {
        Assets.manager.finishLoading();
        Assets.set();
        this.setScreen(roboWar);
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
    }

    @Override
    public Screen getScreen() {
        return super.getScreen();
    }
}

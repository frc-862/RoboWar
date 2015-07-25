package com.lightningrobotics.robowar;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public class BarrelBounce extends Game {
    RoboWar roboWar;

    @Override
    public void create() {
        this.setScreen(new SplashScreen(this));
        roboWar = new RoboWar();
    }

    public void playGame() {
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

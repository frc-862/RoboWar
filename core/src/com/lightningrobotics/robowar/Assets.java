package com.lightningrobotics.robowar;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public abstract class Assets {

    public static final AssetManager manager = new AssetManager();

    public static Texture frc, tank, tankCannon, gameField,
            blueRobot, redRobot, barrel;

    static {
        manager.load("frc.png", Texture.class);
        manager.load("gameField.png", Texture.class);
        manager.load("tank.png", Texture.class);
        manager.load("blueTank.png", Texture.class);
        manager.load("redTank.png", Texture.class);
        manager.load("tankCannon.png", Texture.class);
        manager.load("barrel.png", Texture.class);
    }

    public static boolean update() {
        if (manager.update()) {
            set();
            return true;
        }
        return false;
    }

    public static void set() {
        String dir = System.getProperty("user.dir");
        System.out.println(dir);
        frc = manager.get("frc.png", Texture.class);
        gameField = manager.get("gameField.png", Texture.class);
        tank = manager.get("tank.png", Texture.class);
        tankCannon = manager.get("tankCannon.png", Texture.class);
        blueRobot = manager.get("blueTank.png", Texture.class);
        redRobot = manager.get("redTank.png", Texture.class);
        barrel = manager.get("barrel.png", Texture.class);
    }

    public static void dispose() {
        frc.dispose();
        gameField.dispose();
        tank.dispose();
        tankCannon.dispose();
        redRobot.dispose();
        blueRobot.dispose();
        barrel.dispose();
    }

}

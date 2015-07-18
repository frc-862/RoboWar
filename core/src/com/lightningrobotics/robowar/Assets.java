package com.lightningrobotics.robowar;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

public abstract class Assets {

    public static final AssetManager manager = new AssetManager();

    public static Texture frc, tank, tankCannon;

    static {
        manager.load("frc.png", Texture.class);
        manager.load("tank.png", Texture.class);
        manager.load("tankCannon.png", Texture.class);
    }

    public static boolean update() {
        if(manager.update()) {
            set();
            return true;
        }
        return false;
    }

    public static void set() {
        String dir = System.getProperty("user.dir");
        System.out.println(dir);
        frc = manager.get("frc.png", Texture.class);
        tank = manager.get("tank.png", Texture.class);
        tankCannon = manager.get("tankCannon.png", Texture.class);
    }

    public static void dispose() {
        frc.dispose();
        tank.dispose();
        tankCannon.dispose();
    }

}

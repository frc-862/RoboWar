package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.World;

class StickyBump {
    private BaseRobot robot;
    private Barrel barrel;

    public StickyBump(BaseRobot r, Barrel b) {
        robot = r;
        barrel = b;
    }

    public StickyBump(BaseRobot r) {
        robot = r;
        barrel = null;
    }

    public void process(World world) {
        if (robot.isJoined())
            robot.release();
        else if (barrel != null)
            robot.join(barrel);
    }
}

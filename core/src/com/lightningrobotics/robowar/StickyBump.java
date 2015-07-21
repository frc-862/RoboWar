package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class StickyBump {
    BaseRobot robot;
    Barrel barrel;

    public StickyBump(BaseRobot r, Barrel b)
    {
        robot = r;
        barrel = b;
    }

    public StickyBump(BaseRobot r)
    {
        robot = r;
        barrel = null;
    }

    public void process(World world)
    {
        if (robot.isJoined())
            robot.release();
        else if (barrel != null)
            robot.join(barrel);
    }
}

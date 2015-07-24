package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;

public class BaseRobot extends Entity implements ContactListener {
    private RobotDefinition robotDefinition;
    private WeldJoint barrelJoint;
    private long lastJoin = -3000;
    private int barrelBonus = 0;
    private Alliance lastZone = Alliance.unset;

    BaseRobot(RoboWar game) {
        this(game, 0, 0);
        barrelJoint = null;
        setTexture(Assets.tank, 0.5f, 1f);
//        setRegion(Assets.tank);
    }

    private BaseRobot(RoboWar game, float x, float y) {
        robotDefinition = new RobotDefinition(game);
    }

    public Body getBody() {
        return robotDefinition.getBody();
    }

    private boolean isAlive() {
        return robotDefinition.isAlive();
    }

    public boolean reapIfDead() {
        if (!isAlive()) {
            robotDefinition.kill();
            return true;
        }
        return false;
    }

    void featureComplete() {
        float x = RoboWar.rand.nextFloat() * Constants.width - Constants.width / 2;
        float y = RoboWar.rand.nextFloat() * Constants.height - Constants.height / 2;

        Body body = robotDefinition.buildBody(x, y, 0.5f, 1);
        body.setUserData(this);
    }

    private int score() {
        int increment = 0;

        if (isJoined()) {
            long seconds = millisecondsJoined() / 1000;

            if (seconds > barrelBonus) {
                increment = 1;
                barrelBonus += 1;
            }
        } else {
            barrelBonus = 0;
        }

        Alliance currentZone = currentZone();
        if (currentZone != Alliance.unset) {
            if (lastZone != Alliance.unset && lastZone != currentZone)
                increment += 2;
            lastZone = currentZone;
        }

        return increment;
    }

    public void update() {
        robotDefinition.update();
        robotDefinition.getGame().getScore().incrementScore(getAlliance(), score());
    }

    void addFeature(RobotFeature feature) {
        robotDefinition.addFeature(feature);
    }

    @Override
    public void beginContact(Contact contact) {
        Body b1 = contact.getFixtureA().getBody();
        Body b2 = contact.getFixtureB().getBody();

        Object o1 = b1.getUserData();
        Object o2 = b2.getUserData();

        if (!isJoined() && canJoin()) {
            if (o1 instanceof Barrel && o2 instanceof BaseRobot) {
                Barrel b = (Barrel) o1;
                if (b.getAlliance() != getAlliance()) {
                    grabBarrel(b);
                }
            } else if (o1 instanceof BaseRobot && o2 instanceof Barrel) {
                Barrel b = (Barrel) o2;
                if (b.getAlliance() != getAlliance()) {
                    grabBarrel(b);
                }
            }
        }
    }

    public void grabBarrel(Barrel b) {
        StickyBump sb = new StickyBump(this, b);
        robotDefinition.getGame().getBumps().add(sb);
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        for (float ni : impulse.getNormalImpulses()) {
            if (Math.abs(ni) >= 1 && isJoined()) {
                Gdx.app.log("   normal", String.valueOf(ni));
                StickyBump sb = new StickyBump(this);
                robotDefinition.getGame().getBumps().add(sb);
            }
//             robotDefinition.damage(ni * 3);
        }
    }

    public void damage(float v) {
        robotDefinition.damage(v);
    }

    private boolean canJoin() {
        return !isJoined() && (System.currentTimeMillis() - lastJoin > 3000);
    }

    public void join(Barrel b) {
        lastJoin = System.currentTimeMillis();

        WeldJointDef wd = new WeldJointDef();
        wd.bodyA = getBody();
        wd.bodyB = b.getBody();
        wd.referenceAngle = wd.bodyB.getAngle() - wd.bodyA.getAngle();
        barrelJoint = (WeldJoint) robotDefinition.getGame().getWorld().createJoint(wd);
    }

    public boolean isJoined() {
        return barrelJoint != null;
    }

    private long getLastJoin() {
        return lastJoin;
    }

    private long millisecondsJoined() {
        if (isJoined())
            return System.currentTimeMillis() - getLastJoin();

        return 0;
    }

    public void release() {
        Barrel b = (Barrel) barrelJoint.getBodyB().getUserData();

        robotDefinition.getGame().getWorld().destroyJoint(barrelJoint);
        barrelJoint = null;

        lastJoin = System.currentTimeMillis();
        b.getBody().applyForce(RoboWar.rand.nextFloat(), RoboWar.rand.nextFloat(), 0, 0, true);
    }

    void joinBlueAlliance() {
        robotDefinition.joinBlueAlliance();
    }

    void joinRedAlliance() {
        robotDefinition.joinRedAlliance();
    }

    private Alliance getAlliance() {
        return robotDefinition.getAlliance();
    }

    enum Alliance {unset, red, blue}
}


package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionDetect implements ContactListener
{

    @Override
    public void beginContact(Contact contact) {
        Object o = contact.getFixtureA().getBody().getUserData();
        if (o instanceof ContactListener)
            ((ContactListener) o).beginContact(contact);

        o = contact.getFixtureB().getBody().getUserData();
        if (o instanceof ContactListener)
            ((ContactListener) o).beginContact(contact);
    }

    @Override
    public void endContact(Contact contact) {
        Object o = contact.getFixtureA().getBody().getUserData();
        if (o instanceof ContactListener)
            ((ContactListener) o).endContact(contact);

        o = contact.getFixtureB().getBody().getUserData();
        if (o instanceof ContactListener)
            ((ContactListener) o).endContact(contact);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Object o = contact.getFixtureA().getBody().getUserData();
        if (o instanceof ContactListener)
            ((ContactListener) o).preSolve(contact, oldManifold);

        o = contact.getFixtureB().getBody().getUserData();
        if (o instanceof ContactListener)
            ((ContactListener) o).preSolve(contact, oldManifold);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Object o = contact.getFixtureA().getBody().getUserData();
        if (o instanceof ContactListener)
            ((ContactListener) o).postSolve(contact, impulse);

        o = contact.getFixtureB().getBody().getUserData();
        if (o instanceof ContactListener)
            ((ContactListener) o).postSolve(contact, impulse);
    }
}

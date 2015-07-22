package com.lightningrobotics.robowar;

import com.badlogic.gdx.physics.box2d.*;

import java.util.function.Consumer;

public class CollisionDetect implements ContactListener {
    private void tryContact(Object o, Consumer<ContactListener> op) {
       if (o != null && o instanceof ContactListener) {
           op.accept((ContactListener) o);
       }
    }

    private void tryAll(Contact contact, Consumer<ContactListener> op) {
        Fixture fixture = contact.getFixtureA();
        tryContact(fixture.getUserData(), op);
        tryContact(fixture.getBody().getUserData(), op);

        fixture = contact.getFixtureB();
        tryContact(fixture.getUserData(), op);
        tryContact(fixture.getBody().getUserData(), op);
    }

    @Override
    public void beginContact(Contact contact) {
        Consumer<ContactListener> op = (o) -> o.beginContact(contact);
        tryAll(contact, op);
    }

    @Override
    public void endContact(Contact contact) {
        Consumer<ContactListener> op = (o) -> o.endContact(contact);
        tryAll(contact, op);
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        Consumer<ContactListener> op = (o) -> o.preSolve(contact, oldManifold);
        tryAll(contact, op);
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        Consumer<ContactListener> op = (o) -> o.postSolve(contact, impulse);
        tryAll(contact, op);
    }
}

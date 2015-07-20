package com.lightningrobotics.robowar;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.lightningrobotics.robowar.Constants.PPM;

public class RoboWar extends ApplicationAdapter {
    private final int gameSeconds = 60 * 3;
    private final int robotCount = 0;
    public static Random rand = new Random();

    Score score;
    World world;
    SpriteBatch batch;
    Box2DDebugRenderer b2dr;
    OrthographicCamera camera;
    List<BaseRobot> robots;
    float width = Constants.defaultPixelWidth / PPM;
    float height = Constants.defaultPixelHeight / PPM;
    private float timeRemaining;
    private boolean DEBUG = false;

    public Score getScore() {
        return score;
    }

    public float getWidth() {
        return width;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public float getHeight() {
        return height;
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);

        this.width = width / PPM;
        this.height = height / PPM;

        camera.viewportWidth = this.width;
        camera.viewportHeight = this.height;

        score.resize(width, height);
    }

    public int remainingSeconds() {
        return (int) timeRemaining;
    }

    @Override
    public void create() {
        timeRemaining = gameSeconds;
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        score = new Score(this);
        world = new World(new Vector2(0, 0), true);
        batch = new SpriteBatch();
//        font.getData().setScale(0.1f, 0.1f);
        world.setContactListener(new CollisionDetect());
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        robots = new LinkedList<BaseRobot>();

        new Arena(this);

        Assets.manager.finishLoading();
        Assets.set();

        for (int i = 0; i < robotCount; ++i)
            robots.add(new SimpleRobot(this));
        robots.add(new TeleopRobot(this));
    }

    public void update() {
        timeRemaining -= Gdx.graphics.getDeltaTime();

        for (BaseRobot r : robots) {
            r.update();
        }
        if (!world.isLocked()) {
            Iterator<BaseRobot> iter = robots.iterator();
            while (iter.hasNext()) {
                if (iter.next().reapIfDead()) {
                    iter.remove();
                }
            }

            if (robots.isEmpty()) {
                Gdx.app.exit();
            }
        }
        world.step(1 / 60f, 6, 2);

    }

    @Override
    public void render() {
        camera.position.set(0, 0, 0);
        camera.position.set(0, 0, 0);
        camera.update();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Assets.gameField, -Constants.width / 2, -Constants.height / 2, Constants.width, Constants.height);
        for (BaseRobot robot : robots) {
            robot.render(batch);
        }
        batch.end();
        score.render(batch);

        if (remainingSeconds() >= 0)
            update();

        if (DEBUG)
            b2dr.render(world, camera.combined);
    }

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        world.dispose();
        batch.dispose();
        b2dr.dispose();
    }
}


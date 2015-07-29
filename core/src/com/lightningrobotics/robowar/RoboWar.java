package com.lightningrobotics.robowar;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.lightningrobotics.robowar.Constants.PPM;

@SuppressWarnings("FieldCanBeLocal")
public class RoboWar implements Screen {
    final int gameSeconds = 60 * 3;
    public static Random rand = new Random();

    private List<Class<BaseRobot>> redAlliance;
    private List<Class<BaseRobot>> blueAlliance;

    public RoboWar() {
        super();
        blueAlliance = new LinkedList<>();
        redAlliance = new LinkedList<>();
    }

    public void joinBlueAlliance(Class<BaseRobot> r) {
        blueAlliance.add(r);
    }

    public void joinRedAlliance(Class<BaseRobot> r) {
        redAlliance.add(r);
    }

    private Barrel blueBarrel;
    private Barrel redBarrel;
    private Score score;
    private World world;
    private SpriteBatch batch;
    private Box2DDebugRenderer b2dr;
    private OrthographicCamera camera;
    private List<Entity> entities;
    private Array<StickyBump> bumps = new Array<>();
    private float width = Constants.defaultPixelWidth / PPM;
    private float height = Constants.defaultPixelHeight / PPM;
    private float timeRemaining;

    public Array<StickyBump> getBumps() {
        return bumps;
    }

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
    public void show() {
        timeRemaining = gameSeconds;
        Gdx.app.setLogLevel(Application.LOG_DEBUG);

        score = new Score(this);
        world = new World(new Vector2(0, 0), true);
        batch = new SpriteBatch();
//        font.getData().setScale(0.1f, 0.1f);
        world.setContactListener(new CollisionDetect());
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        entities = new LinkedList<>();

        new Arena(this);

        int robotCount = 0;
        for (int i = 0; i < robotCount; ++i)
            entities.add(new SimpleRobot(this));

//        entities.add(new Pacer(this));
//        entities.add(new TeleopRobot(this));

        for (Class<BaseRobot> Robot : redAlliance) {
            try {
                BaseRobot br = Robot.getConstructor(RoboWar.class).newInstance(this);
                br.joinRedAlliance();
                entities.add(br);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (Class<BaseRobot> Robot : blueAlliance) {
            try {
                BaseRobot br = Robot.getConstructor(RoboWar.class).newInstance(this);
                br.joinBlueAlliance();
                entities.add(br);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        blueBarrel = new Barrel(this, 9, 0);
        blueBarrel.joinBlueAlliance();
        entities.add(blueBarrel);

        redBarrel = new Barrel(this, -9, 0);
        redBarrel.joinRedAlliance();
        entities.add(redBarrel);
    }

    public List<Entity> getEntities() {
        return entities;
    }

    private void update() {
        timeRemaining -= Gdx.graphics.getDeltaTime();
        entities.forEach((e) -> e.update());

        if (!world.isLocked()) {
            Iterator<Entity> iter = entities.iterator();
            while (iter.hasNext()) {
                if (iter.next().reapIfDead()) {
                    iter.remove();
                }
            }

            if (entities.isEmpty()) {
                Gdx.app.exit();
            }
        }
        world.step(1 / 60f, 6, 2);

        Iterator<StickyBump> iter = bumps.iterator();
        while (iter.hasNext()) {
            iter.next().process(world);
            iter.remove();
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void render(float delta) {
        camera.position.set(0, 0, 0);
        camera.position.set(0, 0, 0);
        camera.update();

        Gdx.gl.glClearColor(0.1f, 0.1f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Assets.gameField, -Constants.width / 2, -Constants.height / 2, Constants.width, Constants.height);
        entities.forEach((e) -> e.render(batch));
        batch.end();
        score.render(batch);

        if (remainingSeconds() >= 0)
            update();

        boolean DEBUG = false;
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


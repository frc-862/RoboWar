package com.lightningrobotics.robowar;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.sun.javafx.geom.Vec2d;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.lightningrobotics.robowar.Constants.PPM;

public class RoboWar extends ApplicationAdapter {
    public static Random rand = new Random();

    private boolean DEBUG = true;

    World world;
    Box2DDebugRenderer b2dr;
	OrthographicCamera camera;

    private final int robotCount = 30;
    List<BaseRobot> robots;

    float width = Constants.defaultPixelWidth / PPM;
    float height = Constants.defaultPixelHeight / PPM;

    public float getWidth() {
        return width;
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
    }

    @Override
    public void create () {
        Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);

        world = new World(new Vector2(0,0), true);
        world.setContactListener(new CollisionDetect());
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        robots = new LinkedList<BaseRobot>();

        for (int i = 0; i < robotCount; ++i)
            robots.add(new SimpleRobot(this));
        new Arena(this);
    }

    public void update()
    {
        for (BaseRobot r : robots)
        {
            r.update();
        }
        if (!world.isLocked())
        {
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
	public void render () {
        update();
        camera.position.set(0, 0, 0);
        camera.update();
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.7f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        b2dr.render(world, camera.combined);
	}

    public World getWorld() {
        return world;
    }

    @Override
    public void dispose() {
        world.dispose();
        b2dr.dispose();
    }
}

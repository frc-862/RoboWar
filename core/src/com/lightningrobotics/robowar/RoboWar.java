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
import net.dermetfan.gdx.assets.AnnotationAssetManager;
import net.dermetfan.gdx.graphics.g2d.Box2DSprite;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static com.lightningrobotics.robowar.Constants.PPM;

public class RoboWar extends ApplicationAdapter {
    public static Random rand = new Random();

    private boolean DEBUG = true;

    World world;
    SpriteBatch batch;

    Box2DDebugRenderer b2dr;
	OrthographicCamera camera;

    private final int robotCount = 1;
    List<BaseRobot> robots;

    float width = Constants.defaultPixelWidth / PPM;
    float height = Constants.defaultPixelHeight / PPM;

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
    }

    @Override
    public void create () {
        Gdx.app.setLogLevel(Gdx.app.LOG_DEBUG);

        world = new World(new Vector2(0,0), true);
        batch = new SpriteBatch();
        world.setContactListener(new CollisionDetect());
        b2dr = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        robots = new LinkedList<BaseRobot>();

        new Arena(this);

        Assets.manager.finishLoading();
        Assets.set();

        for (int i = 0; i < robotCount; ++i)
            robots.add(new TeleopRobot(this));
//            robots.add(new SimpleRobot(this));
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
        camera.position.set(0, 0, 0);
        camera.update();
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.7f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(Assets.frc, -Constants.width / 2, -Constants.height / 2, Constants.width, Constants.height);
        Box2DSprite.draw(batch, world);
        batch.end();

        update();


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

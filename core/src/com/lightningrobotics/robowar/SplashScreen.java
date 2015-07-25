package com.lightningrobotics.robowar;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SplashScreen implements Screen {
    private BarrelBounce game;

    private SpriteBatch batch;
    private Sprite gigawatt;
    private Sprite evilgiga;
    private Sprite background;
    private Sprite title;
    private Sprite team;
    private Sprite logo;
    private TweenManager tweenManager;

    public SplashScreen(BarrelBounce game)
    {
        super();
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        background = new Sprite(new Texture("lightning.jpg"));
        background.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        evilgiga = new Sprite(new Texture("evil_gigawatt.png"));
        evilgiga.setScale(0.3f);
        evilgiga.setOrigin(0, 0);
        evilgiga.setPosition(0, 0);

        gigawatt = new Sprite(new Texture("gigawatt.png"));
        gigawatt.setScale(0.3f);
        gigawatt.setOrigin(0, 0);
        gigawatt.setPosition(Gdx.graphics.getWidth() - gigawatt.getWidth() * 0.3f, 0);

        title = new Sprite(new Texture("heading.png"));
        title.setScale(0.5f);
        title.setOrigin(0, 0);
        title.setPosition(0, Gdx.graphics.getHeight() - title.getHeight() * 0.5f);

        team = new Sprite(new Texture("lightning_robotics.png"));
        team.setScale(0.5f);
        team.setOrigin(0, 0);
        team.setPosition(0, Gdx.graphics.getHeight() - title.getHeight() * 0.5f - team.getHeight() * 0.5f);

        logo = new Sprite(new Texture("logo.png"));
        logo.setScale(0.25f);
        logo.setOrigin(0,0);
        logo.setPosition(Gdx.graphics.getWidth() - logo.getWidth() * 0.25f, Gdx.graphics.getHeight() / 2);

        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteTweenAccessor());
//        Tween.set(gigawatt, SpriteTweenAccessor.Mode.SCALE.ordinal()).target(0.1f).start(tweenManager);
//        Tween.to(gigawatt, SpriteTweenAccessor.Mode.SCALE.ordinal(), 2).target(1).start(tweenManager);
//        Tween.to(gigawatt, SpriteTweenAccessor.Mode.SCALE.ordinal(), 2).target(0.25f).delay(4).start(tweenManager);
//
//        Tween.set(gigawatt, SpriteTweenAccessor.Mode.POSITION.ordinal()).target(0,0).start(tweenManager);
//        Tween.to(gigawatt, SpriteTweenAccessor.Mode.POSITION.ordinal(), 6).target(600,300).start(tweenManager);
    }

    private float elapsedTime = 0;

    @Override
    public void render(float delta) {
        elapsedTime += delta;
        if (elapsedTime > 10)
            game.playGame();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        background.draw(batch);
        gigawatt.draw(batch);
        evilgiga.draw(batch);

        title.draw(batch);
        team.draw(batch);

        logo.draw(batch);

        batch.end();
    }

    @Override
    public void resize(int width, int height) {

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
    public void dispose() {
        batch.dispose();
    }
}

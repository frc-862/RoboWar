package com.lightningrobotics.robowar;

import aurelienribon.tweenengine.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.mdkt.compiler.InMemoryJavaCompiler;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplashScreen implements Screen {
    private BarrelBounce game;

    private SpriteBatch batch;
    private Sprite gigawatt;
    private Sprite evilgiga;
    private Sprite background;
    private Sprite title;
    private Sprite team;
    private Sprite logo;
    private Sprite teamNumber;
    private TweenManager tweenManager;

    public SplashScreen(BarrelBounce game)
    {
        super();
        this.game = game;
    }

    @Override
    public void show() {
        batch = new SpriteBatch();

        setupSprites();
        setupAnimation();
    }

    private void setupAnimation() {
        tweenManager = new TweenManager();
        Tween.registerAccessor(Sprite.class, new SpriteTweenAccessor());

        Timeline.createSequence()
                .push(Tween.set(gigawatt, SpriteTweenAccessor.Mode.ALPHA.ordinal()).target(0))
                .push(Tween.set(evilgiga, SpriteTweenAccessor.Mode.ALPHA.ordinal()).target(0))
                .push(Tween.set(title, SpriteTweenAccessor.Mode.ALPHA.ordinal()).target(0))
                .push(Tween.set(team, SpriteTweenAccessor.Mode.ALPHA.ordinal()).target(0))
                .push(Tween.set(logo, SpriteTweenAccessor.Mode.ALPHA.ordinal()).target(0))
                .push(Tween.set(teamNumber, SpriteTweenAccessor.Mode.ALPHA.ordinal()).target(0))

                .beginParallel()
                .push(Tween.to(title, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 1).target(1))
                .push(Tween.to(teamNumber, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 2).target(1))
                .push(Tween.to(team, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 2).target(1))
                .end()

                .beginParallel()
                .push(Tween.to(teamNumber, SpriteTweenAccessor.Mode.POSITION.ordinal(), 2).target(Gdx.graphics.getWidth() / 2 + teamNumber.getHeight() * 0.15f, Gdx.graphics.getHeight() / 2))
                .push(Tween.to(teamNumber, SpriteTweenAccessor.Mode.SCALE.ordinal(), 2).target(0.15f))
                .end()

                .beginParallel()
                .push(Tween.to(gigawatt, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 1).target(1))
                .push(Tween.to(evilgiga, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 2).target(1))
                .end()

                .pushPause(2)

                .beginParallel()
                .push(Tween.to(gigawatt, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 1.5f).target(0))
                .push(Tween.to(evilgiga, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 1.5f).target(0))
                .push(Tween.to(title, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 1.5f).target(0))
                .push(Tween.to(team, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 1.5f).target(0))
                .push(Tween.to(teamNumber, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 1.5f).target(0))
                .end()

                .push(Tween.to(logo, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 1.5f).target(1))
                .pushPause(2)
                .push(Tween.to(logo, SpriteTweenAccessor.Mode.ALPHA.ordinal(), 2.5f).target(0))

                .repeat(20, 0)

//                .push(Tween.call(new TweenCallback() {
//                    @Override
//                    public void onEvent(int i, BaseTween<?> baseTween) {
//                       game.playGame();
//                    }
//                }))

                .start(tweenManager);

//        Tween.set(gigawatt, SpriteTweenAccessor.Mode.SCALE.ordinal()).target(0.1f).start(tweenManager);
//        Tween.to(gigawatt, SpriteTweenAccessor.Mode.SCALE.ordinal(), 2).target(1).start(tweenManager);
//        Tween.to(gigawatt, SpriteTweenAccessor.Mode.SCALE.ordinal(), 2).target(0.25f).delay(4).start(tweenManager);
//
//        Tween.set(gigawatt, SpriteTweenAccessor.Mode.POSITION.ordinal()).target(0,0).start(tweenManager);
//        Tween.to(gigawatt, SpriteTweenAccessor.Mode.POSITION.ordinal(), 6).target(600,300).start(tweenManager);

//        Tween.set(teamNumber, SpriteTweenAccessor.Mode.ROTATION.ordinal()).target(0).start(tweenManager);
//        Tween.to(teamNumber, SpriteTweenAccessor.Mode.ROTATION.ordinal(), 3).target(360).start(tweenManager);
    }

    private void setupSprites() {
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
        logo.setScale(0.75f);
        logo.setOrigin(0, 0);
//        logo.setPosition(Gdx.graphics.getWidth() - logo.getWidth() * 0.25f, Gdx.graphics.getHeight() / 2);

        teamNumber = new Sprite(new Texture("862.png"));
        teamNumber.setScale(0.5f);
        teamNumber.setOrigin(0,0);
    }

    private float elapsedTime = 0;

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
            EventQueue.invokeLater(() -> {
                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Robot Control Class", "java", "class", "jar");
                chooser.setFileFilter(filter);
                chooser.setCurrentDirectory(new File("/Users/phurley/code/projects/robowar/core/src/com/lightningrobotics/robowar"));
                if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    Gdx.app.log("Open", chooser.getSelectedFile().getAbsolutePath());
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    try {
                        String klass = "";

                        byte[] encoded = Files.readAllBytes(Paths.get(path));
                        String code = new String(encoded);

                        Pattern regex = Pattern.compile("^\\s*package\\s+(\\S+);$", Pattern.MULTILINE);
                        Matcher regexMatcher = regex.matcher(code);
                        if (regexMatcher.find()) {
                            klass = regexMatcher.group(1);
                            klass += ".";
                        }

                        regex = Pattern.compile("^\\s*public\\s+class\\s+(\\S+)\\s+", Pattern.MULTILINE);
                        regexMatcher = regex.matcher(code);
                        if (regexMatcher.find()) {
                            klass += regexMatcher.group(1);
                        }

                        Gdx.app.log("class", klass);

                        InMemoryJavaCompiler imjc = new InMemoryJavaCompiler();
                        Class<BaseRobot> NewRobotClass = (Class<BaseRobot>) imjc.compile(klass, code);
                        game.joinRedAlliance(NewRobotClass);
                    } catch (IOException err) {
                        Gdx.app.log("IOException", err.toString());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } else if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            game.selectRobots();
        }
        elapsedTime += delta;
//        if (elapsedTime > 30)
//            game.playGame();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        batch.begin();
        background.draw(batch);
        gigawatt.draw(batch);
        evilgiga.draw(batch);

        title.draw(batch);
        team.draw(batch);
        teamNumber.draw(batch);

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

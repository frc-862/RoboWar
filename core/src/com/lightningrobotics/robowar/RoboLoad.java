package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.mdkt.compiler.InMemoryJavaCompiler;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RoboLoad implements Screen {
    private BarrelBounce game;

    private Stage stage;
    private Table table;
    private Skin skin;
    private TextButton startButton;
    private TextButton quitButton;

    private ArrayList<Class<BaseRobot>> robots;

    public RoboLoad(BarrelBounce game) {
        super();
        this.game = game;
        robots = new ArrayList<>(6);
        for (int i = 0; i < 6; ++i)
            robots.add(null);
    }

    private void loadRobot(Label label, int index) {
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
                    String name = "(unknown)";

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
                        name = regexMatcher.group(1);
                        klass += name;
                    }

                    InMemoryJavaCompiler imjc = new InMemoryJavaCompiler();
                    Class<?> NewRobotClass = imjc.compile(klass, code);

                    Class<?> superclass = NewRobotClass.getSuperclass();
                    while (superclass != null && superclass != BaseRobot.class)
                        superclass = superclass.getSuperclass();

                    if (superclass == BaseRobot.class) {
                        label.setText(name);
                        robots.set(index, (Class<BaseRobot>) NewRobotClass);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void show() {
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
        table = new Table();
//        table.setFillParent(true);
        table.setWidth(stage.getWidth());
//        table.setDebug(true);
        Gdx.app.log("height", String.valueOf(table.getHeight()));
        Gdx.app.log("height", String.valueOf(stage.getHeight()));

        table.align(Align.center | Align.top);
//        table.setFillParent(true);

        table.setPosition(0, Gdx.graphics.getHeight());

        table.add(new Label("Red Alliance", skin)).expandX().align(Align.center).center();
        table.add(new Label("Blue Alliance", skin)).expandX().align(Align.center).center();
        table.row();
        table.add(new Label(" ", skin)).colspan(2).expandX().align(Align.center).center();
        table.row();

        table.add(new Label("Red 1", skin)).expandX().align(Align.center).center().getActor().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadRobot((Label) event.getTarget(), 0);
            }
        });

        table.add(new Label("Blue 1", skin)).expandX().align(Align.center).center().getActor().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadRobot((Label) event.getTarget(), 3);
            }
        });
        table.row();

        table.add(new Label("Red 2", skin)).expandX().align(Align.center).center().getActor().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadRobot((Label) event.getTarget(), 1);
            }
        });
        table.add(new Label("Blue 2", skin)).expandX().align(Align.center).center().getActor().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadRobot((Label) event.getTarget(), 4);
            }
        });
        table.row();

        table.add(new Label("Red 3", skin)).expandX().align(Align.center).center().getActor().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadRobot((Label) event.getTarget(), 2);
            }
        });
        table.add(new Label("Blue 3", skin)).expandX().align(Align.center).center().getActor().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                loadRobot((Label) event.getTarget(), 5);
            }
        });
        table.row();

        table.add(new Label(" ", skin)).colspan(2).expandX().align(Align.center).center();
        table.row();

        startButton = new TextButton("New Game", skin);
        table.add(startButton).colspan(2);
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // load robots
                game.joinRedAlliance(robots.get(0));
                game.joinRedAlliance(robots.get(1));
                game.joinRedAlliance(robots.get(2));

                game.joinBlueAlliance(robots.get(3));
                game.joinBlueAlliance(robots.get(4));
                game.joinBlueAlliance(robots.get(5));

                game.playGame();
            }
        });

//        quitButton = new TextButton("Quit Game",skin);

//        Label nameLabel = new Label("Name:", skin);
//        TextField nameText = new TextField("", skin);
//        Label addressLabel = new Label("Address:", skin);
//        TextField addressText = new TextField("", skin);
//
//        table.columnDefaults(1).width(150);
//        table.add(nameLabel);
//        table.add(nameText);
//        table.row();
//        table.add(addressLabel);
//        table.add(addressText);

        table.padTop(30);

//        table.add(startButton).padBottom(30);

//        table.row();
//        table.add(quitButton);

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
    }
}

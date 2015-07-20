package com.lightningrobotics.robowar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Score {
    RoboWar game;
    OrthographicCamera fontCamera;
    BitmapFont font;
    int redScore;
    int blueScore;

    public int getRedScore() {
        return redScore;
    }

    public void setRedScore(int redScore) {
        this.redScore = redScore;
    }

    public int getBlueScore() {
        return blueScore;
    }

    public void setBlueScore(int blueScore) {
        this.blueScore = blueScore;
    }

    public Score(RoboWar game)
    {
        this.game = game;

        fontCamera = new OrthographicCamera();
        font = new BitmapFont();
        font.setColor(0,0,0,1f);

        redScore = 0;
        blueScore = 0;

    }


    public void resize(int width, int height) {
        fontCamera.viewportWidth = width;
        fontCamera.viewportHeight = height;
    }

    public void render(Batch batch)
    {
        int remainingSeconds = Math.max(game.remainingSeconds(),0);
        int remainingMinutes = Math.floorDiv(remainingSeconds, 60);
        remainingSeconds -= remainingMinutes * 60;

        fontCamera.position.set(0, 0, 0);
        fontCamera.update();

        batch.setProjectionMatrix(fontCamera.combined);
        batch.begin();
        font.draw(batch, String.format("Time: %d:%02d", remainingMinutes, remainingSeconds), 400, -300);
        font.draw(batch, "Red Alliance: 0", 400, -320);
        font.draw(batch, "Blue Alliance: 0", 400, -340);
        batch.end();
    }

}

package xyz.aikoyori.shapeswap;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import xyz.aikoyori.shapeswap.game.level.LevelBase;
import xyz.aikoyori.shapeswap.game.level.ScoreTargetLevel;
import xyz.aikoyori.shapeswap.game.states.MainMenu;
import xyz.aikoyori.shapeswap.game.states.PlayState;
import xyz.aikoyori.shapeswap.stages.AmongUsStage;
import xyz.aikoyori.shapeswap.stages.SquareStage;

import java.util.ArrayList;

public class ShapeSwap extends Game {
    Camera camera;
    Viewport viewport;
    SpriteBatch batch;
    ShapeRenderer shapeRenderer;
    @Override
    public void create() {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        Gdx.graphics.setTitle("Circle Matcher++");

        shapeRenderer = new ShapeRenderer();
        setScreen(new MainMenu(this));

    }

    @Override
    public void render() {
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.91f, 0.92f, 0.96f, 1);
        shapeRenderer.rect(0, 0, 1280, 720);
        shapeRenderer.end();

        batch.end();
        super.render();

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }
}

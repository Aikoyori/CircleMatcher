package xyz.aikoyori.shapeswap.game.level;

import com.badlogic.gdx.ApplicationAdapter;
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
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import xyz.aikoyori.shapeswap.game.board.BoardBase;
import xyz.aikoyori.shapeswap.game.tile.TileBase;
import xyz.aikoyori.shapeswap.stages.StageBase;
import xyz.aikoyori.shapeswap.stages.TestStage;

import java.util.Objects;

public abstract class LevelBase extends ApplicationAdapter {
    StageBase testStage;
    int currentlySelecting = 1;

    public static int size = 20;
    public static int margin = 8;
    public static int padding = 4;
    public int xOffset = 306;
    public int yOffset = 40;
    int movesDone = 0;
    int scoreTotal = 0;
    ShapeRenderer shapeRenderer;
    FreeTypeFontGenerator generator;
    SpriteBatch batch;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font12;
    Sound one = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/button.wav"));
    Sound goodmatch = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/goodmatch.wav"));
    Sound badmatch = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/badmatch.wav"));

    public void create(StageBase stage) {
        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/CascadiaCode.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 36;
        parameter.color = new Color(0, 0, 0, 1);
        font12 = generator.generateFont(parameter); // font size 12 pixels

        generator.dispose();
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        testStage = stage;
        xOffset = stage.getBoardOffsetX();
        yOffset = stage.getBoardOffsetY();
        testStage.board.setTotalScore(0);
    }

    @Override
    public void render() {
        //Gdx.graphics.getDeltaTime();
        /*
        if(Gdx.input.isTouched())
        {

            System.out.println(Gdx.input.getX() + ", " + (Gdx.input.getY()));


            xOffset = Gdx.input.getX();
            yOffset = Gdx.input.getY();
        }
        //*/
        if (Gdx.input.justTouched()) {


            //*/
            int selectedX = (int) Math.floor(((Gdx.input.getY() - yOffset - padding + size / 2 - margin / 2) / (size * 2 + margin + padding)));
            int selectedY = (int) Math.floor((Gdx.input.getX() - xOffset + padding - size * 2 / 4) / (size * 2 + margin + padding));

            //System.out.println(selectedX+" "+selectedY);
            //System.out.println(testStage.board.getBoardWidth()+" "+testStage.board.getBoardHeight());
            if (selectedY < testStage.board.getBoardWidth() && selectedX < testStage.board.getBoardHeight() &&
                    selectedX >= 0 && selectedY >= 0) {

                if (testStage.board.getTile(selectedX, selectedY).isShapeTile()) {

                    if (testStage.board.getTile(selectedX, selectedY).getIsSelected() == 0) {
                        if (!testStage.board.ifBoardPairHasBeenSelected()) {
                            testStage.board.getTile(selectedX, selectedY).setIsSelected(currentlySelecting);

                            if (currentlySelecting == 1) {
                                currentlySelecting = 2;
                                one.play();
                            } else if (currentlySelecting == 2) {
                                currentlySelecting = 1;
                            }
                        }
                    } else {

                        currentlySelecting = 1;
                        testStage.board.resetTileSelection();
                        badmatch.play();
                    }


                }
            }
            if (testStage.board.ifBoardPairHasBeenSelected()) {
                TileBase tile1 = testStage.board.getTile(testStage.board.getTileThatWasSelected(1)[0], testStage.board.getTileThatWasSelected(1)[1]);
                TileBase tile2 = testStage.board.getTile(testStage.board.getTileThatWasSelected(2)[0], testStage.board.getTileThatWasSelected(2)[1]);
                if (Math.abs(tile1.getTileX() - tile2.getTileX()) + Math.abs(tile1.getTileY() - tile2.getTileY()) == 1) {
                //if (true) {
                    if ((testStage.board.swap(tile1, tile2))) {
                        goodmatch.play();
                        movesDone++;
                    } else {
                        badmatch.play();
                    }
                } else {

                    badmatch.play();
                }

                testStage.board.resetTileSelection();
            } else {

            }
        }
        ScreenUtils.clear(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(0.91f, 0.92f, 0.96f, 1);
        shapeRenderer.rect(0, 0, 1280, 720);
        for (int i = 0; i < testStage.board.getBoardHeight(); i++) {
            for (int j = 0; j < testStage.board.getBoardWidth(); j++) {


                if (!Objects.equals(testStage.board.getTile(i, j).getCurrentShape(), null)) {
                    if (testStage.board.getTile(i, j).getIsSelected() > 0) {
                        shapeRenderer.setColor(0.79f, 0.81f, 0.87f, 0.5f);

                    } else {
                        shapeRenderer.setColor(0.14f, 0.13f, 0.19f, 0.5f);
                    }
                    //shapeRenderer.rect(j*(size*2+margin)+margin*2,(720-(i*(size*2+margin)))-margin*2-size,size*2+margin/2.0f,size*2+margin/2.0f);
                    shapeRenderer.rect(xOffset + j * (size * 2 + margin + padding) + size * 2 / 4 - padding, 720 - ((i + 1) * (size * 2 + margin + padding)) + padding - size / 2 + margin / 2 - yOffset, size * 2 + padding * 2, size * 2 + padding * 2);

                }
            }
        }
        for (int i = 0; i < testStage.board.getBoardHeight(); i++) {
            for (int j = 0; j < testStage.board.getBoardWidth(); j++) {
                //System.out.println(j*5+", "+j*5);

                if (!Objects.equals(testStage.board.getTile(i, j).getCurrentShape(), null)) {
                    switch (testStage.board.getTile(i, j).getCurrentShape().getShapeRepresentName()) {
                        case "D":
                            shapeRenderer.setColor(0.32f, 0.89f, 0.98f, 1);
                            break;
                        case "A":
                            shapeRenderer.setColor(0.93f, 0.39f, 0.86f, 1);
                            break;
                        case "S":
                            shapeRenderer.setColor(0.53f, 0.62f, 1f, 1);
                            break;
                        case "E":
                            shapeRenderer.setColor(0.39f, 0.93f, 0.43f, 1);
                            break;
                        case "T":
                            shapeRenderer.setColor(0.93f, 0.67f, 0.39f, 1);
                            break;
                        case "R":
                            shapeRenderer.setColor(0.99f, 0.38f, 0.4f, 1);
                            break;
                        case "X":
                            shapeRenderer.setColor(1f,1f,1f, 1);
                            break;
                        default:
                            shapeRenderer.setColor(0, 0, 0, 1);
                            break;
                    }
                    shapeRenderer.circle(j * (size * 2 + margin + padding) + size * 3 / 2 + xOffset, (720 - (i * (size * 2 + margin + padding))) - size * 3 / 2 - yOffset, size);
                    if (testStage.board.getTile(i, j).getCurrentShape().getShapeState().getState() == "*") {
                        shapeRenderer.setColor(1,1,1, 1);
                        shapeRenderer.circle(j * (size * 2 + margin + padding) + size * 3 / 2 + xOffset, (720 - (i * (size * 2 + margin + padding))) - size * 3 / 2 - yOffset, size/2);

                    }
                    if (testStage.board.getTile(i, j).getCurrentShape().getShapeState().getState() == "=") {
                        shapeRenderer.setColor(0,0,0, 1);
                        shapeRenderer.circle(j * (size * 2 + margin + padding) + size * 3 / 2 + xOffset, (720 - (i * (size * 2 + margin + padding))) - size * 3 / 2 - yOffset, size/2);

                    }
                } else {

                }


            }
        }


        shapeRenderer.end();
        batch.begin();
        renderText();
        batch.end();
        scoreTotal = testStage.board.getTotalScore();

    }

    public boolean retryConditionMet() {
        return false;
    }

    public boolean nextLevelConditionMet() {
        return false;
    }

    @Override
    public void dispose() {
        batch.dispose();
        shapeRenderer.dispose();
    }

    public void renderText() {

        font12.draw(batch, "Moved " + movesDone + " time" + (movesDone == 1 ? "" : "s"), 20, 720 - 20);
        font12.draw(batch, "Score  : " + scoreTotal, 20, 720 - 70);
        font12.draw(batch, "Press R to Retry", 920, 720 - 20);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

}

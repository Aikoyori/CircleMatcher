package xyz.aikoyori.shapeswap.game.states;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.Align;

import javax.swing.plaf.basic.BasicSliderUI;

public class MainMenu extends MenuBase {

    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter2;
    BitmapFont titleFont;
    BitmapFont buttonFont;
    BitmapFont smallerFont;
    Stage stage;
    FreeTypeFontGenerator generator;
    SpriteBatch batch;
    TextButton playbutton;
    TextButton endlessButton;
    TextButton quitButton;
    TextButton levelSelectbutton;
    TextButton levelAdd;
    TextButton levelMinus;
    TextButton levelSelectEndlessMode;
    TextButton.TextButtonStyle buttonStyle;
    static int selectedLevel = -1;
    Skin skinButton;
    TextureAtlas buttonAtlas;
    static boolean isEndlessToggle = false;
    Sound one = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/button.wav"));

    Game game;
    public MainMenu(Game gameIn){
        this.game = gameIn;


        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/CascadiaCode.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 72;
        parameter.color = new Color(0,0,0,1);
        parameter2 = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter2.size = 36;
        parameter2.color = new Color(0,0,0,1);
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
        titleFont = generator.generateFont(parameter);
        buttonFont = generator.generateFont(parameter2);
        parameter2.size = 24;
        smallerFont = generator.generateFont(parameter2);

        buttonStyle = new TextButton.TextButtonStyle();

        buttonStyle.font = buttonFont;
        buttonAtlas = new TextureAtlas("assets/textures/pixel.txt");
        skinButton = new Skin();
        skinButton.addRegions(buttonAtlas);
        buttonStyle.up = skinButton.getDrawable("pixel");

        ;


        playbutton = new TextButton(" Play", buttonStyle);
        stage.addActor(playbutton);
        playbutton.setHeight(80);
        playbutton.setWidth(500);
        playbutton.setPosition(40,300);
        playbutton.getLabel().setAlignment(Align.left);
        playbutton.align(Align.left);

        playbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                MainMenu.this.playbutton.remove();
                one.play();
                game.setScreen(new PlayState(game,false));
            }

        });
        /*
        playbutton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("Button Pressed");
                super.clicked(event, x, y);
            }
        });
        //*/
        endlessButton = new TextButton(" Infinite Mode", buttonStyle);

        endlessButton.setHeight(80);
        endlessButton.setWidth(500);
        endlessButton.setPosition(40,200);
        endlessButton.getLabel().setAlignment(Align.left);
        endlessButton.align(Align.left);
        endlessButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                MainMenu.this.playbutton.remove();
                one.play();
                game.setScreen(new PlayState(game,true,0,true));
            }

        });


        stage.addActor(endlessButton);

        quitButton = new TextButton(" Quit", buttonStyle);

        quitButton.setHeight(60);
        quitButton.setWidth(200);
        quitButton.setPosition(40,50);
        quitButton.getLabel().setAlignment(Align.left);
        quitButton.align(Align.left);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                one.play();
                Gdx.app.exit();
            }

        });

        stage.addActor(quitButton);
        parameter2.size = 18;
        buttonFont = generator.generateFont(parameter2);
        buttonStyle.font = buttonFont;
        levelSelectbutton = new TextButton(" Play Level "+(selectedLevel+1)+" ", buttonStyle);

        levelSelectbutton.setHeight(60);
        levelSelectbutton.setWidth(240);
        levelSelectbutton.setPosition(1040,50);
        levelSelectbutton.getLabel().setAlignment(Align.center);

        levelSelectbutton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                one.play();
                game.setScreen(new PlayState(game,isEndlessToggle,selectedLevel));
            }

        });

        stage.addActor(levelSelectbutton);

        levelMinus = new TextButton(" - ", buttonStyle);

        levelMinus.setHeight(60);
        levelMinus.setWidth(60);
        levelMinus.setPosition(1040,120);
        levelMinus.getLabel().setAlignment(Align.center);
        levelMinus.align(Align.center);
        levelMinus.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                one.play();
                if(MainMenu.this.selectedLevel>-1)
                MainMenu.this.selectedLevel--;
            }

        });

        stage.addActor(levelMinus);

        levelAdd = new TextButton(" + ", buttonStyle);

        levelAdd.setHeight(60);
        levelAdd.setWidth(60);
        levelAdd.setPosition(1220,120);
        levelAdd.getLabel().setAlignment(Align.center);
        levelAdd.align(Align.center);
        levelAdd.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                one.play();
                if(MainMenu.this.selectedLevel<PlayState.levelCount-1)
                MainMenu.this.selectedLevel++;
            }

        });

        stage.addActor(levelAdd);

        levelSelectEndlessMode = new TextButton(" R x ", buttonStyle);

        levelSelectEndlessMode.setHeight(60);
        levelSelectEndlessMode.setWidth(60);
        levelSelectEndlessMode.setPosition(1130,120);
        levelSelectEndlessMode.getLabel().setAlignment(Align.center);
        levelSelectEndlessMode.align(Align.center);
        levelSelectEndlessMode.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                one.play();
                MainMenu.this.isEndlessToggle=!MainMenu.this.isEndlessToggle;
            }

        });

        stage.addActor(levelSelectEndlessMode);
        batch = new SpriteBatch();

    }
    public void render(float delta){
        batch.begin();
        if(selectedLevel==-1)
        {
            levelSelectbutton.setText("Play Random "+(isEndlessToggle?"\nRemix ":"")+"Level");
        }
        else
        {

            levelSelectbutton.setText(" Play "+(isEndlessToggle?"Remix ":"")+"Level "+(selectedLevel+1));
        }
        levelSelectEndlessMode.setText(" R "+(isEndlessToggle?"/":"x")+" ");
        titleFont.draw(batch,"Circle Matcher++",40,500);
            //System.out.println((Gdx.input.getX()+" "+(720-Gdx.input.getY())));
        if(isEndlessToggle)
        smallerFont.draw(batch,"Remix Mode",954, 209);

        smallerFont.draw(batch,"Level Select", 1106, 209);

        batch.end();
        stage.draw();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

        playbutton.setDisabled(false);
        endlessButton.setDisabled(false);
        quitButton.setDisabled(false);
    }

    @Override
    public void hide() {
        playbutton.setDisabled(true);
        endlessButton.setDisabled(true);
        quitButton.setDisabled(true);

    }

    public void dispose(){
    }

    @Override
    public void show() {

    }


    public void resize(int width, int height)
    {
        super.resize(width,height);
        stage.getViewport().update(width, height);
    }
}

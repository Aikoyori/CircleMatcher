package xyz.aikoyori.shapeswap.game.states;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import xyz.aikoyori.shapeswap.game.level.LevelBase;
import xyz.aikoyori.shapeswap.game.level.ScoreTargetLevel;
import xyz.aikoyori.shapeswap.stages.*;
import xyz.aikoyori.shapeswap.stages.autogenmode.AutoGenStage;

import java.util.Random;

public class PlayState extends MenuBase {
    private final TextButton.TextButtonStyle buttonStyle;
    int endlessMoveStage = 0;
    int currentLevel = 0;
    Camera camera;
    LevelBase currentStage;
    Viewport viewport;
    FreeTypeFontGenerator generator;
    SpriteBatch batch;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font12;

    BitmapFont buttonFont;
    Stage stage;
    TextButton quitButton;
    Game game;
    Skin skinButton;
    TextureAtlas buttonAtlas;
    boolean isEndless;
    Random randomer;
    boolean infiniteMode;

    Sound levelPass = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/levelcomplete.wav"));
    Sound levelFail = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/levelfailed.wav"));
    Sound gameComplete = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/gamecomplete.wav"));

    public int getLevelCount() {
        return levelCount;
    }

    static int levelCount = 24;
    int moveStarting;
    Sound button = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/button.wav"));
    public PlayState(Game gameIn,boolean endless,int currentLevelIn,boolean isInfiniteMode){
        currentLevel = currentLevelIn;
        //level1.create(new SquareStage(),306,40);
        isEndless = endless;
        game = gameIn;
        stage = new Stage();
        batch = new SpriteBatch();
        generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/fonts/CascadiaCode.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 48;
        parameter.color = new Color(0,0,0,1);
        font12 = generator.generateFont(parameter); // font size 12 pixels

        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();

        parameter.size = 24;
        parameter.color = new Color(0,0,0,1);
        buttonStyle = new TextButton.TextButtonStyle();

        buttonFont = generator.generateFont(parameter);
        buttonStyle.font = buttonFont;
        buttonAtlas = new TextureAtlas("assets/textures/pixel.txt");
        skinButton = new Skin();
        skinButton.addRegions(buttonAtlas);
        buttonStyle.up = skinButton.getDrawable("pixel");
        
        camera = new OrthographicCamera();
        viewport = new FitViewport(1280, 720, camera);
        randomer = new Random();
        moveStarting = 5;
        infiniteMode = isInfiniteMode;
        if(currentLevelIn == -1)
        {
            currentLevel = randomLevelID();
        }

        createCurrentLevel(currentLevel,isEndless);


        quitButton = new TextButton(" Quit", buttonStyle);

        quitButton.setHeight(60);
        quitButton.setWidth(200);
        quitButton.setPosition(1100,20);
        quitButton.getLabel().setAlignment(Align.left);
        quitButton.align(Align.left);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

                game.setScreen(new MainMenu(game));
                button.play();
            }

        });

        stage.addActor(quitButton);
        Gdx.input.setInputProcessor(stage);

    }
    public PlayState(Game gameIn,boolean endless,int currentLevelIn){
        this(gameIn,endless,currentLevelIn,false);
    }
    public PlayState(Game gameIn,boolean endless)
    {
        this(gameIn,endless,0);
    }

    public void render(float delta) {
        currentStage.render();
        if(currentStage.retryConditionMet())
        {
            retryLevel();
        }
        if(currentStage.nextLevelConditionMet())
        {
            nextLevel();
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.R)){
            retryLevel();
        }
        batch.begin();
        if(infiniteMode)
        {
            font12.draw(batch,("Infinite Mode Wave "+(endlessMoveStage+1)),20,60);
        }
        else
        {
            font12.draw(batch,(isEndless?"Remix "+(endlessMoveStage+1)+" (from Level "+(currentLevel+1)+")":"Level "+(currentLevel+1)),20,60);
        }
        batch.end();

        stage.draw();
    }
    public void createCurrentLevel(int curLev,boolean endless)
    {
        //System.out.println("SUS");
        int scoreThreshold = (int)(moveStarting*(1.27+0.01*endlessMoveStage)*6);
        currentStage.size = 20;
        if(infiniteMode)
        {
            currentStage.size = 12;
            currentStage = new ScoreTargetLevel();
            ((ScoreTargetLevel)currentStage).create(new AutoGenStage(randomer.nextLong()),scoreThreshold,moveStarting);
            return;
        }
        switch(curLev){
            case 0:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new SquareStage(isEndless? randomer.nextLong():10000),isEndless?scoreThreshold:45,isEndless?moveStarting:7);
                break;
            case 1:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new AmongUsStage(isEndless?randomer.nextLong():10000),isEndless?scoreThreshold:100,isEndless?moveStarting:13);
                break;
            case 2:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new AseanStage(isEndless?randomer.nextLong():6666),isEndless?scoreThreshold:120,isEndless?moveStarting:15);
                break;
            case 3:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new PyramidStage(isEndless?randomer.nextLong():65418563),isEndless?scoreThreshold:150,isEndless?moveStarting:18);
                break;
            case 4:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new XwithHoleInItStage(isEndless?randomer.nextLong():6643),isEndless?scoreThreshold:160,isEndless?moveStarting:22);
                break;
            case 5:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new TiltedHouseStage(isEndless?randomer.nextLong():6958498),isEndless?scoreThreshold:180,isEndless?moveStarting:27);
                break;
            case 6:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new DonutStage(isEndless?randomer.nextLong():8934),isEndless?scoreThreshold:220,isEndless?moveStarting:33);
                break;
            case 7:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new DonuttierDonutStage(isEndless?randomer.nextLong():93847),isEndless?scoreThreshold:250,isEndless?moveStarting:36);
                break;
            case 8:
                currentStage = new ScoreTargetLevel();
                currentStage.size = 10;
                ((ScoreTargetLevel)currentStage).create(new WeirdHandGestureStage(isEndless?randomer.nextLong():9834),isEndless?scoreThreshold:220,isEndless?moveStarting:33);
                break;
            case 9:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new LetterAStage(isEndless?randomer.nextLong():4352),isEndless?scoreThreshold:200,isEndless?moveStarting:28);
                break;
            case 10:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new LetterPStage(isEndless?randomer.nextLong():177013),isEndless?scoreThreshold:250,isEndless?moveStarting:35);
                break;
            case 11:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new LetterTStage(isEndless?randomer.nextLong():645),isEndless?scoreThreshold:150,isEndless?moveStarting:20);
                break;
            case 12:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new LetterIStage(isEndless?randomer.nextLong():312),isEndless?scoreThreshold:160,isEndless?moveStarting:21);
                break;
            case 13:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new ShurikenStage(isEndless?randomer.nextLong():6975),isEndless?scoreThreshold:100,isEndless?moveStarting:15);
                break;
            case 14:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new TwisterStage(isEndless?randomer.nextLong():463),isEndless?scoreThreshold:220,isEndless?moveStarting:30);
                break;
            case 15:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new VaseStage(isEndless?randomer.nextLong():896),isEndless?scoreThreshold:250,isEndless?moveStarting:42);
                break;
            case 16:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new CupStage(isEndless?randomer.nextLong():7835),isEndless?scoreThreshold:230,isEndless?moveStarting:30);
                break;
            case 17:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new CupButItHasHoleStage(isEndless?randomer.nextLong():5079),isEndless?scoreThreshold:246,isEndless?moveStarting:32);
                break;
            case 18:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new Figure8Stage(isEndless?randomer.nextLong():3547),isEndless?scoreThreshold:70,isEndless?moveStarting:10);
                break;
            case 19:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new SmallIStage(isEndless?randomer.nextLong():3457),isEndless?scoreThreshold:80,isEndless?moveStarting:12);
                break;
            case 20:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new DeadpoolMaskStage(isEndless?randomer.nextLong():3587),isEndless?scoreThreshold:98,isEndless?moveStarting:14);
                break;
            case 21:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new GDIconStage(isEndless?randomer.nextLong():4598),isEndless?scoreThreshold:110,isEndless?moveStarting:15);
                break;
            case 22:
                currentStage = new ScoreTargetLevel();
                ((ScoreTargetLevel)currentStage).create(new SuperSetSymbolStage(isEndless?randomer.nextLong():265915),isEndless?scoreThreshold:152,isEndless?moveStarting:19);
                break;
            case 23:
                currentStage = new ScoreTargetLevel();
                currentStage.size = 15;
                ((ScoreTargetLevel)currentStage).create(new BigCStage(isEndless?randomer.nextLong():314159265),isEndless?scoreThreshold:170,isEndless?moveStarting:24);
                break;
            default:
                gameComplete.play();
                game.setScreen(new MainMenu(game));


        }
    }
    public void nextLevel(){

        currentStage.dispose();
        currentLevel++;
        endlessMoveStage++;
        moveStarting+=2;
        levelPass.play();
        if(isEndless){
            currentLevel = randomLevelID();
        }
        createCurrentLevel(currentLevel,isEndless);

    }
    public int randomLevelID(){
        int levelid;
        do
        {
            levelid = randomRawID();
        }
        while(levelid == currentLevel);
        return levelid;
    }
    private int randomRawID(){
        return (int)(randomer.nextInt(levelCount));
    }
    public void retryLevel(){
        levelFail.play();

        currentStage.dispose();
        createCurrentLevel(currentLevel,isEndless);
    }
    @Override
    public void dispose() {
        currentStage.dispose();


    }

    @Override
    public void show() {
        
    }


    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        currentStage.resize(width,height);
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
}

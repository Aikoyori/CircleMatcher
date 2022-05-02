package xyz.aikoyori.shapeswap.game.board;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Timer;
import xyz.aikoyori.shapeswap.game.shape.ShapeBase;
import xyz.aikoyori.shapeswap.game.shape.ShapeBasic;
import xyz.aikoyori.shapeswap.game.shape.ShapeDestroyer;
import xyz.aikoyori.shapeswap.game.shapestate.ExplosiveShapeState;
import xyz.aikoyori.shapeswap.game.shapestate.SuperShapeState;
import xyz.aikoyori.shapeswap.game.tile.BlankTile;
import xyz.aikoyori.shapeswap.game.tile.ShapeTile;
import xyz.aikoyori.shapeswap.game.tile.TileBase;
import xyz.aikoyori.shapeswap.game.utils.BagRandomizer;
import xyz.aikoyori.shapeswap.game.utils.TopTileException;

import java.util.ArrayList;
import java.util.Objects;

public abstract class BoardBase {
    TileBase[][] tiles = new TileBase[1][1];

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    int totalScore = 0;
    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    int boardWidth, boardHeight;
    ArrayList<ShapeBase> possibleShapes;
    BagRandomizer shapePicker;
    Sound bomblol = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/bombshape.wav"));
    Sound doomlol = Gdx.audio.newSound(Gdx.files.internal("assets/sfx/shapedestroyer.wav"));

    private String spaceGenerator(int n)
    {
        String x = "";
        for(int i=0;i<n;i++) x+=" ";
        return x;
    }
    public BoardBase(String levelString, ArrayList<ShapeBase> possibles, long levelSeed, int levelSalt) {
        String[] rows = levelString.split("\n");
        levelString+=spaceGenerator(rows.length-1);

        rows = levelString.split("\n");
        int columntotal = 0;
        boardHeight = rows.length;
        possibleShapes = possibles;
        /*
        possibleShapes.add(ShapeBasic.create("Diamond","D"));
        possibleShapes.add(ShapeBasic.create("Sapphire","S"));
        possibleShapes.add(ShapeBasic.create("Topaz","T"));
        possibleShapes.add(ShapeBasic.create("Emerald","E"));
        */
        shapePicker = new BagRandomizer(possibleShapes,levelSeed);
        shapePicker.setBagSalt(levelSalt);
        for (int i = 0; i < rows.length; i++) {
            if (rows[i].length() > columntotal) columntotal = rows[i].length();
        }

        boardWidth = columntotal;
        //System.out.println(boardWidth);
        tiles = new TileBase[rows.length][columntotal];
        for (int i = 0; i < rows.length; i++) {

            for (int j = 0; j < columntotal; j++) {
                try {
                    switch (Character.toString(rows[i].charAt(j)).toUpperCase()) {
                        case " ":
                            tiles[i][j] = new BlankTile(i, j);
                            break;
                        case "0":
                            tiles[i][j] = new ShapeTile(i, j, false);
                            break;
                    }
                } catch (IndexOutOfBoundsException e) {
                    tiles[i][j] = new BlankTile(i, j);
                }

            }

        }

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (!(tiles[i][j] instanceof BlankTile)) {
                    tiles[i][j].forcePutShape(shapePicker.<ShapeBase>pickThingOut());
                    //System.out.println(shapePicker.<ShapeBase>pickThingOut());
                    //System.out.println(tiles[i][j]);
                }
            }
        }
        doMatches(0);
        resetCalculationValue();

    }
    public BoardBase(String levelString, ArrayList<ShapeBase> possibles, long levelSeed)
    {
        this(levelString,possibles,levelSeed,2);
    }
    private ShapeBase getShapeFromTile(int i, int j) {
        return tiles[i][j].getCurrentShape();
    }

    public TileBase getTile(int i, int j) {
        /*
        System.out.println(boardWidth+", "+boardHeight);
        System.out.println(i+", "+j);*/
        return tiles[i][j];
    }

    public TileBase getTileYX(int i, int j) {
        return tiles[j][i];
    }


    public boolean doesCreateMatch(int row, int column, int dRow, int dColumn) {
        TileBase[][] shapeNow = tiles;
        ShapeBase tempShape;
        tempShape = getShapeFromTile(row + dRow, column + dColumn);
        getTile(row + dRow, column + dColumn).forcePutShape(getShapeFromTile(row, column));
        getTile(row, column).forcePutShape(tempShape);
        int x = doMatches(0);
        //System.out.println(x);
        tiles = shapeNow;
        return x > 0;
    }
    private int matchFiveCheck(int y,int x){
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y, x - 2)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y, x - 1)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y, x + 1)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y, x + 2))
            ){
                getShapeFromTile(y-2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+2,x).setHasBeenCalculatedForSpecial(true);
                return 1;
            }
        }
        catch(Exception ex){}
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y-2, x)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y+2, x))
            ){
                getShapeFromTile(y-2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+2,x).setHasBeenCalculatedForSpecial(true);
                return 2;
            }
        }
        catch(Exception ex){}

        return 0;
    }

    private int matchFourCheck(int x,int y){
        if(Objects.equals(getShapeFromTile(y, x),null)) return 0;
        if(!getTile(y,x).isShapeTile()) return 0;
        try{

            if(Objects.equals(getShapeFromTile(y+ 1, x ),null)) return 0;
            if(Objects.equals(getShapeFromTile(y+ 2, x),null)) return 0;
            if(!getTile(y+1,x).isShapeTile()) return 0;
            if(!getTile(y+2,x).isShapeTile()) return 0;

        }
        catch(Exception ex)
        {
            return 0;
        }
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y, x - 2)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y, x - 1)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y, x + 1))
            ){
                getShapeFromTile(y,x-2).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+1).setHasBeenCalculatedForSpecial(true);
                return 1;
            }
        }
        catch(Exception ex) {}
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y, x - 1)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y, x + 1)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y, x + 2))
            ){
                getShapeFromTile(y,x+2).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+1).setHasBeenCalculatedForSpecial(true);
                return 2;
            }
        }
        catch(Exception ex) {}


        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y-2, x)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x))
            ){
                getShapeFromTile(y-2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                return 3;
            }
        }
        catch(Exception ex){}

        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x)) &&
               getShapeFromTile(y, x).equals(getShapeFromTile(y+2, x))
            ){
                //System.out.println((getShapeFromTile(y+2, x)));
                getShapeFromTile(y+2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                return 4;
            }
        }
        catch(Exception ex){}

        //these are technically not four but they will give out the exploding gem anyway

        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y-2, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+2))
            ){
                getShapeFromTile(y-2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+2).setHasBeenCalculatedForSpecial(true);
                return 5;
            }
        }catch(Exception ex){}
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y+2, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+2))
            ){
                getShapeFromTile(y+2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+2).setHasBeenCalculatedForSpecial(true);
                return 6;
            }
        }catch(Exception ex){}
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y+2, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-2))
            ){
                getShapeFromTile(y+2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-2).setHasBeenCalculatedForSpecial(true);
                return 7;
            }
        }catch(Exception ex){}
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y-2, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-2))
            ){
                getShapeFromTile(y-2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-2).setHasBeenCalculatedForSpecial(true);
                return 8;
            }
        }catch(Exception ex){}
        // these are for Ts
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+2))
            ){
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+2).setHasBeenCalculatedForSpecial(true);
                return 9;
            }
        }catch(Exception ex){}
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-2))
            ){
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-2).setHasBeenCalculatedForSpecial(true);
                return 10;
            }
        }catch(Exception ex){}
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y+2, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+1))
            ){
                getShapeFromTile(y+2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+1).setHasBeenCalculatedForSpecial(true);
                return 11;
            }
        }catch(Exception ex){}
        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y-2, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+1))
            ){
                getShapeFromTile(y-2,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+1).setHasBeenCalculatedForSpecial(true);
                return 12;
            }
        }

        catch(Exception ex){}

        try{
            if(getShapeFromTile(y, x).equals(getShapeFromTile(y+1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y-1, x)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x-1)) &&
                    getShapeFromTile(y, x).equals(getShapeFromTile(y, x+1))
            ){
                getShapeFromTile(y+1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y-1,x).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x-1).setHasBeenCalculatedForSpecial(true);
                getShapeFromTile(y,x+1).setHasBeenCalculatedForSpecial(true);
                return 13;
            }
        }

        catch(Exception ex){}

        return 0;
    }


    public int doMatches(int score) {
        int total = 0;
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 1; j < boardWidth - 1; j++) {
                //:tada::tada::tada::tada::tada::tada::tada::tada:
                try {
                    if (getShapeFromTile(i, j).equals(getShapeFromTile(i, j - 1)) && getShapeFromTile(i, j).equals(getShapeFromTile(i, j + 1))) {
                        getTile(i, j - 1).setGemInsideBeingRemoved(true);
                        getTile(i, j).setGemInsideBeingRemoved(true);
                        getTile(i, j + 1).setGemInsideBeingRemoved(true);
                        total++;
                        score++;
                    }
                } catch (Exception ex) {}
                try {
                    if (getShapeFromTile(i, j).equals(getShapeFromTile(i, j - 1)) && getShapeFromTile(i, j).equals(getShapeFromTile(i, j - 2))) {
                        getTile(i, j - 1).setGemInsideBeingRemoved(true);
                        getTile(i, j).setGemInsideBeingRemoved(true);
                        getTile(i, j - 2).setGemInsideBeingRemoved(true);
                        total++;
                        score++;
                    }
                } catch (Exception ex) {}
                try {
                    if (getShapeFromTile(i, j).equals(getShapeFromTile(i, j + 1)) && getShapeFromTile(i, j).equals(getShapeFromTile(i, j + 2))) {
                        getTile(i, j + 1).setGemInsideBeingRemoved(true);
                        getTile(i, j).setGemInsideBeingRemoved(true);
                        getTile(i, j + 2).setGemInsideBeingRemoved(true);
                        total++;
                        score++;
                    }
                } catch (Exception ex) {}
            }
        }
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                //:tada::tada::tada::tada::tada::tada::tada::tada:
                try {
                    if (getShapeFromTile(i, j).equals(getShapeFromTile(i - 1, j)) && getShapeFromTile(i, j).equals(getShapeFromTile(i + 1, j))) {
                        getTile(i - 1, j).setGemInsideBeingRemoved(true);
                        getTile(i, j).setGemInsideBeingRemoved(true);
                        getTile(i + 1, j).setGemInsideBeingRemoved(true);
                        total++;
                        score++;
                    }
                } catch (Exception ex) {
                }
                try {
                    if (getShapeFromTile(i, j).equals(getShapeFromTile(i - 1, j)) && getShapeFromTile(i, j).equals(getShapeFromTile(i - 2, j))) {
                        getTile(i - 1, j).setGemInsideBeingRemoved(true);
                        getTile(i, j).setGemInsideBeingRemoved(true);
                        getTile(i - 2, j).setGemInsideBeingRemoved(true);
                        total++;
                        score++;
                    }
                } catch (Exception ex) {
                }
                try {
                    if (getShapeFromTile(i, j).equals(getShapeFromTile(i + 1, j)) && getShapeFromTile(i, j).equals(getShapeFromTile(i + 2, j))) {
                        getTile(i + 2, j).setGemInsideBeingRemoved(true);
                        getTile(i, j).setGemInsideBeingRemoved(true);
                        getTile(i + 1, j).setGemInsideBeingRemoved(true);
                        total++;
                        score++;
                    }
                } catch (Exception ex) {
                }

            }
        }

        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if(!Objects.equals(getShapeFromTile(i,j),null) && !getShapeFromTile(i,j).hasBeenCalculatedForSpecial())
                    try{

                        checkForSpecial(i,j);
                    }catch(Exception e)
                    {

                    }
            }

        }

        totalScore+=score;
        //System.out.println("Did create "+total+" matches");
        total+=doesProcessing();

        if (total > 0) return doMatches(score);
        return score;
    }

    public int destroyGems() {
        int total = 0;
        ShapeBase tempShape;
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {




                if (getTile(i, j).isGemInsideBeingRemoved()) {
                    {
                        try{
                            if(getTile(i, j).getCurrentShape().getShapeState().getState() == ("*"))
                            {
                                //System.out.println("ABOUT TO EXPLODE");
                                bomblol.play();
                                try{getTile(i-1, j-1).setGemInsideBeingRemoved(true);}catch(Exception ex){}
                                try{getTile(i-1, j).setGemInsideBeingRemoved(true);}catch(Exception ex){}
                                try{getTile(i-1, j+1).setGemInsideBeingRemoved(true);}catch(Exception ex){}
                                try{getTile(i, j-1).setGemInsideBeingRemoved(true);}catch(Exception ex){}
                                //lol
                                try{getTile(i, j+1).setGemInsideBeingRemoved(true);}catch(Exception ex){}
                                try{getTile(i+1, j-1).setGemInsideBeingRemoved(true);}catch(Exception ex){}
                                try{getTile(i+1, j).setGemInsideBeingRemoved(true);}catch(Exception ex){}
                                try{getTile(i+1, j+1).setGemInsideBeingRemoved(true);}catch(Exception ex){}

                            }
                            else{

                            }
                        }
                        catch(Exception ex){

                        }
                        if(!Objects.equals(getShapeFromTile(i,j),null) && !getShapeFromTile(i, j).isStaying())
                        {

                            getTile(i, j).destroyShape();
                        }
                        getTile(i, j).setGemInsideBeingRemoved(false);
                    }
                    total += 1;
                    try{
                        if(i < boardHeight) moveRowDown(i + 1, j);

                    }
                    catch(Exception ex){
                        //System.out.println(i+" "+j+" error");
                        moveRowDown(i, j);
                    }
                }

            }
        }
        //System.out.println(this);
        return total;
    }

    public boolean ifBoardPairHasBeenSelected(){
        boolean hasOne = false,hasTwo = false;
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (getTile(i, j).getIsSelected() == 1) {
                    hasOne = true;
                    break;
                }
            }
        }
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (getTile(i, j).getIsSelected() == 2) {
                    hasTwo = true;
                    break;
                }
            }
        }
        return hasOne && hasTwo;
    }
    public int[] getTileThatWasSelected(int sel){
        for (int i = 0; i < boardHeight ; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if (getTile(i, j).getIsSelected() == sel) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public void resetTileSelection(){
        for (int i = 0; i < boardHeight ; i++) {
            for (int j = 0; j < boardWidth; j++) {
                getTile(i, j).setIsSelected(0);
            }
        }
    }

    public void resetCalculationValue(){
        for (int i = 0; i < boardHeight ; i++) {
            for (int j = 0; j < boardWidth; j++) {
                if(!Objects.equals(getShapeFromTile(i,j),null))
                {

                    getShapeFromTile(i,j).setStaying(false);
                    getShapeFromTile(i,j).setHasBeenCalculatedForSpecial(false);
                    getTile(i,j).setWillCreateSpecialShape(0);
                    getTile(i,j).setGemInsideBeingRemoved(false);
                }
            }
        }
    }
    public int doesProcessing() {
        int total = 0;

        total += destroyGems();

        while (isThereAnyEmptyTilesLeft()) {
            /*
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(this);*/
            fillEmpty();
        }

        return total;
    }

    public boolean isThereAnyEmptyTilesLeft() {
        for (int j = 0; j < boardHeight - 1; j++) {
            for (int i = 0; i < boardWidth; i++) {
                if (Objects.equals(getTile(j, i).getCurrentShape(), null) && !((getTile(j, i)) instanceof BlankTile)) {
                    return true;
                }

            }
        }
        return false;
    }

    public void fillEmpty() {
        for (int j = 0; j < boardHeight - 1; j++) {
            for (int i = 0; i < boardWidth; i++) {
                //System.out.println("- Checking - x = "+i + ", y = "+j+" it is a "+getTile(j,i).getCurrentShape());
                if (Objects.equals(getTile(j, i).getCurrentShape(), null) && !((getTile(j, i)) instanceof BlankTile)) {
                    //System.out.println(this);
                    moveRowDown(j + 1, i);
                }

            }
        }
    }

    /**
     * @param x which row
     * @param y which column
     */
    public void moveRowDown(int x, int y) {

        ShapeBase tempShape;
        tempShape = getTile(x, y).getCurrentShape();
        //System.out.println("v Moving - x = "+y + ", y = "+x);
        {

            try {
                if ((x <= 0 || (getTile(x - 1, y) instanceof BlankTile)) && Objects.equals(getShapeFromTile(x, y), null)) {
                    //System.out.println("Setting x = "+x + ", y = "+y+" to have "+shapePicker.<ShapeBase>getNext());
                    getTile(x, y).putShape(shapePicker.<ShapeBase>pickThingOut());
                    //throw new TopTileException();
                } else {
                    getTile(x, y).putShape(getTile(x - 1, y).getCurrentShape());
                    getTile(x - 1, y).removeShape();
                    //System.out.println("from x="+y+" y="+(x)+"->"+(x+1));
                    moveRowDown(x - 1, y);
                }
            } catch (Exception e) {

            }
        }

        {
            //System.out.println("x = "+x + ", y = "+y+" is blank tile");
        }

    }


    public String toString() {
        String str = "";
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                str += this.getTile(i, j);
            }
            str += "\n";
        }
        return str;
    }

    public boolean swap(int row, int column, int dRow, int dColumn) {
        //System.out.println(getTile(row, column));
        //System.out.println(getTile(row + dRow, column + dColumn));

        ShapeBase tempShape;
        ShapeBase tempShape2;
        tempShape = getShapeFromTile(row + dRow, column + dColumn);
        tempShape2 = getShapeFromTile(row, column);
        TileBase tile1 = getTile(row + dRow, column + dColumn);
        TileBase tile2 =  getTile(row, column);
        if(!tile1.isShapeTile() || !tile2.isShapeTile())
        {
            return false;
        }
        if(tile1.getCurrentShape().getShapeState().getState()=="=")
        {
            if(tile2.getCurrentShape().getShapeState().getState()=="=")
            {

                for (int i = 0; i < boardHeight; i++) {
                    for (int j = 0; j < boardWidth; j++) {
                        getShapeFromTile(i, j).setAboutToBeDestroyed(true);
                    }
                }
            }
            if(!tile2.isShapeTile()){
                resetTileSelection();
            }
            else
            {
                for (int i = 0; i < boardHeight; i++) {
                    for (int j = 0; j < boardWidth; j++) {
                        if(tile1.getCurrentShape().equals(tile2.getCurrentShape()))
                        {
                            getShapeFromTile(i, j).setAboutToBeDestroyed(true);

                        }
                    }
                }
            }
        }
        tile1.forcePutShape(getShapeFromTile(row, column));
        tile2.forcePutShape(tempShape);
        int x = doMatches(0);
        //System.out.println(x);
        if (x == 0) {
            tile1.forcePutShape(tempShape2);
            tile2.forcePutShape(tempShape);
        }
        return x > 0;

    }
    public boolean swap(TileBase tile1,TileBase tile2) {
        //System.out.println(getTile(row, column));
        //System.out.println(getTile(row + dRow, column + dColumn));
        boolean isMagicUsed = false;
        ShapeBase tempShape = tile1.getCurrentShape();
        ShapeBase tempShape2 = tile2.getCurrentShape();
        if(!tile1.isShapeTile() || !tile2.isShapeTile())
        {
            resetTileSelection();
            return false;
        }
        try{
            tile1.getCurrentShape().getShapeState();
            tile2.getCurrentShape().getShapeState();
        }
        catch(Exception ex)
        {

            resetTileSelection();
            return false;
        }
        tile1.forcePutShape(tile2.getCurrentShape());
        tile2.forcePutShape(tempShape);
        try{
            checkForSpecial(tile2.getTileX(),tile2.getTileY());
            checkForSpecial(tile1.getTileX(),tile1.getTileY());

        }
        catch(Exception ex){

        }
        //System.out.println(tile1);

        if(tempShape.getShapeState().getState().equals("="))
        {
            System.out.println(tile1.getCurrentShape().getShapeState().getState());
            if(tempShape2.getShapeState().getState().equals("="))
            {

                for (int i = 0; i < boardHeight; i++) {
                    for (int j = 0; j < boardWidth; j++) {
                        getTile(i, j).setGemInsideBeingRemoved(true);
                        tile1.setGemInsideBeingRemoved(true);
                        tile2.setGemInsideBeingRemoved(true);
                        doomlol.play();
                        isMagicUsed = true;
                    }
                }
            }
            else
            {
                for (int i = 0; i < boardHeight; i++) {
                    for (int j = 0; j < boardWidth; j++) {
                        if(getTile(i,j).isShapeTile() && tempShape2.equals(getShapeFromTile(i,j)))
                        {
                            getTile(i, j).setGemInsideBeingRemoved(true);
                            tile1.setGemInsideBeingRemoved(true);
                            tile2.setGemInsideBeingRemoved(true);
                            isMagicUsed = true;

                        }
                    }
                }
            }
        }
        else if(tempShape2.getShapeState().getState().equals("="))
        {
            //System.out.println(tile1.getCurrentShape().getShapeState().getState());

            {
                for (int i = 0; i < boardHeight; i++) {
                    for (int j = 0; j < boardWidth; j++) {
                        if(getTile(i,j).isShapeTile() && tempShape.equals(getShapeFromTile(i,j)))
                        {
                            getTile(i, j).setGemInsideBeingRemoved(true);
                            tile1.setGemInsideBeingRemoved(true);
                            tile2.setGemInsideBeingRemoved(true);
                            isMagicUsed = true;

                        }
                    }
                }
            }
        }
        //System.out.println(tile1);
        int x = doMatches(0);
        //System.out.println(x);

        //System.out.println(tile1);
        if (x == 0 && !isMagicUsed) {
            tile1.forcePutShape(tempShape);
            tile2.forcePutShape(tempShape2);
        }
        else if(isMagicUsed)
        {
            x+=doMatches(0);
            doomlol.play();
        }
        resetCalculationValue();
        //System.out.println(this);
        return x > 0;

    }
    private void checkForSpecial(int i,int j)
    {
        //System.out.println(matchFiveCheck(j,i));
        if(matchFiveCheck(j,i)>0)
        {
            //System.out.println("FOUND MATCH 5 at " + j+", "+i+" case "+matchFiveCheck(j,i));
            getTile(j,i).forcePutShape(ShapeDestroyer.create("Destroyer","X"));
            getTile(j,i).getCurrentShape().setShapeState(new SuperShapeState());
            getTile(j,i).setGemInsideBeingRemoved(false);
            getShapeFromTile(i, j).setStaying(true);
        }
        else if(matchFourCheck(j,i)>0)
        {

            //System.out.println("FOUND MATCH 4 at " + j+", "+i+" case "+matchFourCheck(j,i));
            getTile(i, j).getCurrentShape().setShapeState(new ExplosiveShapeState());
            getTile(i, j).setGemInsideBeingRemoved(false);
            getShapeFromTile(i, j).setStaying(true);

        }}


}

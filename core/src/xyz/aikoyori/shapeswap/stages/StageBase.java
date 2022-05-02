package xyz.aikoyori.shapeswap.stages;

import xyz.aikoyori.shapeswap.game.board.BoardBase;
import xyz.aikoyori.shapeswap.game.shape.ShapeBase;

import java.util.ArrayList;

public abstract class StageBase{
    public BoardBase board;

    public int getBoardOffsetX() {
        return boardOffsetX;
    }

    public void setBoardOffsetX(int boardOffsetX) {
        this.boardOffsetX = boardOffsetX;
    }

    public int getBoardOffsetY() {
        return boardOffsetY;
    }

    public void setBoardOffsetY(int boardOffsetY) {
        this.boardOffsetY = boardOffsetY;
    }
    public void setBoardOffset(int boardOffsetX,int boardOffsetY) {
        this.boardOffsetX = boardOffsetX;
        this.boardOffsetY = boardOffsetY;
    }

    int boardOffsetX;
    int boardOffsetY;
    public ArrayList<ShapeBase> possibleShapes;
    public StageBase(){

    }
}

package xyz.aikoyori.shapeswap.game.board;

import xyz.aikoyori.shapeswap.game.shape.ShapeBase;

import java.util.ArrayList;

public class Board extends BoardBase{


    public Board(String levelString, ArrayList<ShapeBase> possibles, long levelSeed) {
        super(levelString, possibles, levelSeed);
    }
}

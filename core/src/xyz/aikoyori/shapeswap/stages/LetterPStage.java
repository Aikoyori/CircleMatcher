package xyz.aikoyori.shapeswap.stages;

import xyz.aikoyori.shapeswap.game.board.Board;
import xyz.aikoyori.shapeswap.game.shape.ShapeBasic;

import java.util.ArrayList;

public class LetterPStage extends StageBase {

    public LetterPStage(long seed)
    {
        possibleShapes = new ArrayList<>();

        possibleShapes.add(ShapeBasic.create("Diamond","D"));

        possibleShapes.add(ShapeBasic.create("Sapphire","S"));
        possibleShapes.add(ShapeBasic.create("Topaz","T"));
        possibleShapes.add(ShapeBasic.create("Emerald","E"));
        possibleShapes.add(ShapeBasic.create("Amethyst","A"));
        possibleShapes.add(ShapeBasic.create("Ruby","R"));

        board = new Board(                "0000000000000000000\n" +
                "0000000000000000000\n" +
                "000000       000000\n" +
                "000000       000000\n" +
                "0000000000000000000\n" +
                "000000             \n" +
                "000000             \n"
                ,possibleShapes,seed
        );
        setBoardOffset(156, 190);
        board.doMatches(0);
        board.resetCalculationValue();
    }
}

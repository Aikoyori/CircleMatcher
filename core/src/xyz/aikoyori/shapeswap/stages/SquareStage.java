package xyz.aikoyori.shapeswap.stages;

import xyz.aikoyori.shapeswap.game.board.Board;
import xyz.aikoyori.shapeswap.game.shape.ShapeBasic;

import java.util.ArrayList;

public class SquareStage extends StageBase {

    public SquareStage(long seed)
    {
        possibleShapes = new ArrayList<>();

        possibleShapes.add(ShapeBasic.create("Diamond","D"));

        possibleShapes.add(ShapeBasic.create("Sapphire","S"));
        possibleShapes.add(ShapeBasic.create("Topaz","T"));
        possibleShapes.add(ShapeBasic.create("Emerald","E"));
        possibleShapes.add(ShapeBasic.create("Amethyst","A"));
        possibleShapes.add(ShapeBasic.create("Ruby","R"));

        board = new Board(
                "00000000\n" +
                "00000000\n" +
                "00000000\n" +
                "00000000\n" +
                "00000000\n" +
                "00000000\n" +
                "00000000\n" +
                "00000000\n"
                ,possibleShapes,seed
        );

        setBoardOffset(421,140);
        board.doMatches(0);
        board.resetCalculationValue();
    }
}

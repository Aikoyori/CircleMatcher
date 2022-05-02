package xyz.aikoyori.shapeswap.stages;

import xyz.aikoyori.shapeswap.game.board.Board;
import xyz.aikoyori.shapeswap.game.shape.ShapeBasic;

import java.util.ArrayList;

public class AmongUsStage extends StageBase {

    public AmongUsStage(long seed)
    {
        possibleShapes = new ArrayList<>();

        possibleShapes.add(ShapeBasic.create("Diamond","D"));
        possibleShapes.add(ShapeBasic.create("Sapphire","S"));
        possibleShapes.add(ShapeBasic.create("Topaz","T"));
        possibleShapes.add(ShapeBasic.create("Emerald","E"));
        possibleShapes.add(ShapeBasic.create("Amethyst","A"));
        possibleShapes.add(ShapeBasic.create("Ruby","R"));

        board = new Board(
                "    00000  \n" +
                         "   0000000 \n" +
                         " 000000000  \n" +
                         "0    0000000\n" +
                         "0    0000000\n" +
                         " 00000000000\n" +
                         "  0000000000\n" +
                         "  0000000000\n" +
                         "  0000000000\n" +
                         "  00000000\n" +
                         "  000  000\n" +
                         "  000  000\n"
                ,possibleShapes,seed
        );
        setBoardOffset(306,40);
        board.resetCalculationValue();
        board.doMatches(0);
        board.resetCalculationValue();
    }
}

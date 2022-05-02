package xyz.aikoyori.shapeswap.stages;

import xyz.aikoyori.shapeswap.game.board.Board;
import xyz.aikoyori.shapeswap.game.shape.ShapeBasic;

import java.util.ArrayList;

public class DeadpoolMaskStage extends StageBase {

    public DeadpoolMaskStage(long seed) {
        possibleShapes = new ArrayList<>();

        possibleShapes.add(ShapeBasic.create("Diamond", "D"));
        possibleShapes.add(ShapeBasic.create("Sapphire", "S"));
        possibleShapes.add(ShapeBasic.create("Topaz", "T"));
        possibleShapes.add(ShapeBasic.create("Emerald", "E"));
        possibleShapes.add(ShapeBasic.create("Amethyst", "A"));
        possibleShapes.add(ShapeBasic.create("Ruby", "R"));

        board = new Board(
                "0000000000000\n" +
                "0000000000000\n" +
                "0   00000   0\n" +
                "0   00000   0\n" +
                "0000000000000\n" +
                "0000000000000\n"
                , possibleShapes, seed
        );
        setBoardOffset(314, 220);
        board.doMatches(0);
        board.resetCalculationValue();
    }
}

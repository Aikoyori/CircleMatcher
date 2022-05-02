package xyz.aikoyori.shapeswap.stages;

import xyz.aikoyori.shapeswap.game.board.Board;
import xyz.aikoyori.shapeswap.game.board.BoardBase;
import xyz.aikoyori.shapeswap.game.shape.ShapeBase;
import xyz.aikoyori.shapeswap.game.shape.ShapeBasic;

import java.util.ArrayList;

public class TestStage extends StageBase {

    public TestStage()
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
                ,possibleShapes,10000
        );
        //*/
        /*
        board = new Board(
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n" +
                "0000000000\n"
                ,possibleShapes,100
        );
        /*
        board.getTile(3,2).forcePutShape(ShapeBasic.create("Diamond","D"));
        board.getTile(3,3).forcePutShape(ShapeBasic.create("Diamond","D"));
        board.getTile(3,4).forcePutShape(ShapeBasic.create("Diamond","D"));*/
        /*
        board.getTile(8,8).forcePutShape(ShapeBasic.create("Diamond","D"));
        board.getTile(7,7).forcePutShape(ShapeBasic.create("Diamond","D"));
        board.getTile(8,6).forcePutShape(ShapeBasic.create("Diamond","D"));*/
        //System.out.println(board.swap(7,7,1,0));
        board.doMatches(0);
    }
}

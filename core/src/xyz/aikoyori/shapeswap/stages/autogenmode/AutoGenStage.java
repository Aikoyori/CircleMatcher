package xyz.aikoyori.shapeswap.stages.autogenmode;

import xyz.aikoyori.shapeswap.game.board.Board;
import xyz.aikoyori.shapeswap.game.shape.ShapeBasic;
import xyz.aikoyori.shapeswap.game.utils.FastNoiseLite;
import xyz.aikoyori.shapeswap.stages.StageBase;

import java.util.ArrayList;
import java.util.Date;

public class AutoGenStage extends StageBase {
    int width = 25;
    int height = 14;
    int noiseScale = 10;
    float threshold = 0.4f;
    public AutoGenStage(long seed)
    {
        possibleShapes = new ArrayList<>();

        FastNoiseLite noise = new FastNoiseLite((int)(new Date().getTime()));
        noise.SetNoiseType(FastNoiseLite.NoiseType.Perlin);
        noise.SetFrequency(0.025f);
        float[][] noiseData = new float[height*noiseScale][width*noiseScale];
        int index = 0;
        for (int y = 0; y < height*noiseScale; y++)
        {
            for (int x = 0; x < width*noiseScale; x++)
            {
                noiseData[y][x] = noise.GetNoise(x, y);
            }
        }

        String levelStringy = "";
        float tempint = 0;
        for(int i=0;i<height;i++)
        {
            for(int j=0;j<width;j++)
            {
                tempint = 0f;

                for(int k=0;k<noiseScale;k++)
                {
                    for(int l=0;l<noiseScale;l++)
                    {
                        tempint +=noiseData[noiseScale*i+k][noiseScale*j+l];
                    }
                }

                tempint=(tempint/(float)(Math.pow(noiseScale,2))+1.0f)/2;
                levelStringy+=(tempint>threshold?"0":" ");
                //System.out.println(tempint);
            }
            levelStringy+="\n";
        }
        levelStringy+="\n";

        possibleShapes.add(ShapeBasic.create("Diamond","D"));
        possibleShapes.add(ShapeBasic.create("Sapphire","S"));
        possibleShapes.add(ShapeBasic.create("Topaz","T"));
        possibleShapes.add(ShapeBasic.create("Emerald","E"));
        possibleShapes.add(ShapeBasic.create("Amethyst","A"));
        possibleShapes.add(ShapeBasic.create("Ruby","R"));

        board = new Board(levelStringy,possibleShapes,seed
        );

        setBoardOffset(300, 105);
        board.doMatches(0);
        board.resetCalculationValue();
    }
}

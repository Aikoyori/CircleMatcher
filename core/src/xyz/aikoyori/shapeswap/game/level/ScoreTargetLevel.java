package xyz.aikoyori.shapeswap.game.level;

import xyz.aikoyori.shapeswap.stages.StageBase;

public class ScoreTargetLevel extends LevelBase{
    int scoreTarget = 1;
    int maxMoves = 1;

    public void create(StageBase stage, int target,int movesLimit) {
        super.create(stage);
        this.scoreTarget = target;
        this.maxMoves = movesLimit;
    }
    @Override
    public boolean retryConditionMet(){
        return (maxMoves-movesDone<=0) && (scoreTotal < scoreTarget);
    }
    @Override
    public boolean nextLevelConditionMet(){
        return (maxMoves-movesDone<=0) && (scoreTotal >= scoreTarget);
    }
    @Override
    public void renderText() {

        font12.draw(batch,"Moves Left : "+(maxMoves-movesDone)+"/"+maxMoves,20,720-20);
        font12.draw(batch,"Score  : " + testStage.board.getTotalScore(),20,720-70);
        font12.draw(batch,"Target : " + scoreTarget,20,720-120);
        font12.draw(batch,"Press R to Retry",920,720-20);
    }
}

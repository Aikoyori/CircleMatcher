package xyz.aikoyori.shapeswap.game.tile;

import xyz.aikoyori.shapeswap.game.shape.ShapeBase;

public class ObjectiveTile extends TileBase {
    int bgLeft;

    public ObjectiveTile(int x, int y, boolean isShit,int timesNeeded) {
        super(x, y, isShit);
        bgLeft = timesNeeded;
    }
    @Override
    public boolean putShape(ShapeBase shapeIn) {

        if (this.currentShape == null){
            this.currentShape = shapeIn;
            return true;
        }
        else
        {
            return false;
        }
    }
    public int getCurrentBackgroundLeft(){return bgLeft;}
    public boolean breakBackground(){
        if(bgLeft>1) {
            bgLeft -= 1;
            return true;
        }else{return false;}
    }
}

package xyz.aikoyori.shapeswap.game.tile;

import xyz.aikoyori.shapeswap.game.shape.ShapeBase;

public class ShapeTile extends TileBase {
    public ShapeTile(int x, int y, boolean isShit) {
        super(x, y, isShit);
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

    @Override
    public boolean isShapeTile() {
        return true;
    }
    /*
    @Override
    public ShapeBase getCurrentShape() {
        return null;
    }*/
}

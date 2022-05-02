package xyz.aikoyori.shapeswap.game.tile;

import xyz.aikoyori.shapeswap.game.shape.ShapeBase;

public class BlankTile extends TileBase {
    public BlankTile(int x, int y) {
        super(x, y, false);
    }

    @Override
    public boolean putShape(ShapeBase shapeIn) {
        return false;
    }

    @Override
    public boolean isShapeTile() {
        return false;
    }



    @Override
    public ShapeBase getCurrentShape() {
        return null;
    }

    @Override
    public int getWillCreateSpecialShape() {
        return 0;
    }
}

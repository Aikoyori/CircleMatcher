package xyz.aikoyori.shapeswap.game.tile;

import xyz.aikoyori.shapeswap.game.shape.ShapeBase;
import xyz.aikoyori.shapeswap.game.shape.ShapeBasic;

import java.util.Objects;

public abstract class TileBase {
    ShapeBase currentShape;
    int tileX, tileY;
    boolean isGemInsideBeingRemoved = false;
    boolean isShapeTile;

    boolean hasBeenCalculatedForSpecial;

    public int getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(int isSelected) {
        this.isSelected = isSelected;
    }

    int isSelected = 0;
    int willCreateSpecialShape = 0;

    public TileBase(int x, int y, boolean isShape) {
        tileX = x;
        tileY = y;
        isShapeTile = isShape;
    }

    public TileBase(int x, int y, boolean isShape, ShapeBase shanpe) {
        this(x,y,isShape);
        currentShape = shanpe;
    }


    public int getTileX() {
        return tileX;
    }

    public int getTileY() {
        return tileY;
    }


    public ShapeBase getCurrentShape() {
        return currentShape;
    }

    public void setGemInsideBeingRemoved(boolean gemInsideBeingRemoved) {
        if(!Objects.equals(currentShape,null))
        {
            currentShape.setAboutToBeDestroyed(gemInsideBeingRemoved);
        }
        isGemInsideBeingRemoved = gemInsideBeingRemoved;
    }

    public boolean isGemInsideBeingRemoved() {

        if(!Objects.equals(currentShape,null))
        {
            return currentShape.isAboutToBeDestroyed() || isGemInsideBeingRemoved;
        }
        return isGemInsideBeingRemoved;
    }

    public void setWillCreateSpecialShape(int willCreateSpecialShape) {
        this.willCreateSpecialShape = willCreateSpecialShape;
    }

    public int getWillCreateSpecialShape() {
        return willCreateSpecialShape;
    }

    public void destroyShape() {
        this.currentShape = null;
    }

    public boolean pullShape() {
        ShapeBase shapelol = currentShape;
        this.currentShape = null;
        return shapelol != null;
    }

    public ShapeBase removeShape() {
        ShapeBase shapelol = currentShape;
        this.currentShape = null;
        return shapelol;
    }

    public boolean putShape(ShapeBase shanpe) {
        if (Objects.equals(currentShape, null)) {
            currentShape = shanpe;
            return true;
        } else {
            return false;
        }
    }

    public void forcePutShape(ShapeBase shanpe) {
        destroyShape();
        //System.out.println("SHAPE PUT IN "+shanpe);
        currentShape = shanpe;
    }

    public boolean isShapeTile() {
        return isShapeTile;
    }

    public String toString() {
        String aa;
        if (!Objects.equals(getCurrentShape(), null))
            aa = getCurrentShape().toString();
        else
            aa = "-";
        if (!(this instanceof BlankTile))
            if(this.isGemInsideBeingRemoved)
                return "(" + aa +getCurrentShape().getShapeState().getState()+ ")";
                else
                return "[" + aa +getCurrentShape().getShapeState().getState()+ "]";
        else
            return "    ";
    }
}

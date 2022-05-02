package xyz.aikoyori.shapeswap.game.shape;

import xyz.aikoyori.shapeswap.game.shapestate.NormalShapeState;
import xyz.aikoyori.shapeswap.game.shapestate.ShapeStateBase;

public abstract class ShapeBase {
    String shapeRegistryName;
    String shapeRepresentName;

    public boolean isAboutToBeDestroyed() {
        return isAboutToBeDestroyed;
    }

    public void setAboutToBeDestroyed(boolean aboutToBeDestroyed) {
        isAboutToBeDestroyed = aboutToBeDestroyed;
    }

    public boolean isStaying() {
        return isStaying;
    }

    public void setStaying(boolean staying) {
        isStaying = staying;
    }
    public boolean hasBeenCalculatedForSpecial() {
        return hasBeenCalculatedForSpecial;
    }

    public void setHasBeenCalculatedForSpecial(boolean hasBeenCalculatedForSpecial) {
        this.hasBeenCalculatedForSpecial = hasBeenCalculatedForSpecial;
    }

    boolean hasBeenCalculatedForSpecial;
    boolean isStaying;
    boolean isAboutToBeDestroyed;
    static long counter = 0;
    long id;
    public ShapeStateBase getShapeState() {
        return shapeState;
    }

    public void setShapeState(ShapeStateBase shapeState) {
        this.shapeState = shapeState;
    }

    public void setShapeRepresentName(String shapeRepresentName) {
        this.shapeRepresentName = shapeRepresentName;
    }

    volatile ShapeStateBase shapeState;

    public ShapeBase(String shapeRegName,String shapeShortHand){
        shapeRegistryName = shapeRegName;
        shapeRepresentName = shapeShortHand;
        shapeState = new NormalShapeState();
        this.id = counter;
        counter++;
        ShapeList.shapes.add(this);
    }
    public ShapeBase(String shapeRegName){
        this(shapeRegName,String.valueOf(shapeRegName.charAt(0)));
    }

    public String getShapeRegistryName() {
        return shapeRegistryName;
    }

    public String getShapeRepresentName() {
        return shapeRepresentName;
    }

    public boolean equals(ShapeBase shapeIn){
        return (this.shapeRegistryName.equals(shapeIn.shapeRegistryName) && this.shapeRepresentName.equals(shapeIn.shapeRepresentName));
    }
    public String toString(){
        return this.shapeRepresentName;
    }
    /*
    public ShapeBase clone()
    {
        return new ShapeBase(shapeRegistryName,shapeRepresentName);
    }*/
    public abstract ShapeBase clone();


}

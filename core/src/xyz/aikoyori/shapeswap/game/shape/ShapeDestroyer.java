package xyz.aikoyori.shapeswap.game.shape;

public class ShapeDestroyer extends ShapeBase{
    public ShapeDestroyer(String shapeRegName, String shapeShortHand) {
        super(shapeRegName, shapeShortHand);
    }
    public static ShapeDestroyer create(String shapeRegName, String shapeShortHand)
    {
        return new ShapeDestroyer(shapeRegName,shapeShortHand);
    }
    public ShapeBase clone()
    {
        return new ShapeDestroyer(shapeRegistryName,shapeRepresentName);
    }
}

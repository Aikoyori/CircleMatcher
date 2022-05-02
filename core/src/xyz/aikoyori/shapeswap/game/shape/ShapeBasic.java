package xyz.aikoyori.shapeswap.game.shape;

public class ShapeBasic extends ShapeBase{
    public ShapeBasic(String shapeRegName, String shapeShortHand) {
        super(shapeRegName, shapeShortHand);
    }
    public static ShapeBasic create(String shapeRegName, String shapeShortHand)
    {
        return new ShapeBasic(shapeRegName,shapeShortHand);
    }
    public ShapeBase clone()
    {
        return new ShapeBasic(shapeRegistryName,shapeRepresentName);
    }
}

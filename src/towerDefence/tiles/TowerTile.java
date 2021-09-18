package towerDefence.tiles;

public class TowerTile extends Tile{
    public boolean towerTile = true;
    public double rotationRad = 0;
    public int level;
    public TowerTile(State state) {
        super.st = state;
    }
}

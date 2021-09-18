package towerDefence.tiles;

import towerDefence.Enemy;

public class BackgroundTile extends Tile{

    public BackgroundTile(State state) {
        super.st = state;
    }

    public Enemy number1(){
        return super.en;
    }

    public boolean isHasEnemy() {
        return this.hasEnemy;
    }

    public void setHasEnemy(boolean hasEnemy) {
        this.hasEnemy = hasEnemy;
    }

    public BackgroundTile() {
        super.st = State.GRASS;
    }

    public void setState(State state) {
        super.st = state;
    }

    public State getState() {
        return super.st;
    }
}

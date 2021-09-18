package towerDefence.tiles;

import towerDefence.Enemy;

public class Tile {
    protected State st;
    protected int x, y;
    public enum State{
        GRASS, SAND, DARK_GRASS, WATER
    }
    private int towerLevel = 0;

    protected boolean hasEnemy = false;

    protected Enemy en;

    public Enemy getEn() {
        return en;
    }

    public void setEn(Enemy en) {
        this.en = en;
    }

    public boolean hasEnemy() {
        return this.hasEnemy;
    }

    public void setHasEnemy(boolean hasEnemy) {
        this.hasEnemy = hasEnemy;
    }

    public int getX() {
        return x;
    }

    public int getTowerLevel() {
        return towerLevel;
    }

    public void setTowerLevel(int towerLevel) {
        this.towerLevel = towerLevel;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public State getSt() {
        return st;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "st=" + st +
                ", x=" + x +
                ", y=" + y +
                '}';
    }
}

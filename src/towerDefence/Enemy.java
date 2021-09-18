package towerDefence;

public class Enemy {
    private int x, y;
    private double health;
    private int pos = 0;
    private type level;

    public enum type{
        BASIC (Config.trooperHealth),
        WOODEN (Config.woodenHealth),
        TERMINATOR (Config.terminatorHealth),
        MEGA (Config.megaHealth);

        private final int health;
        type(int health) {
            this.health = health;
        }
        public int getHealth() {
            return health;
        }
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString() {
        return "Enemy{" +
                ", health=" + health +
                ", pos=" + pos +
                '}';
    }

    public int getX() {
        return x;
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

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.health = Config.health;
    }

    public type getLevel() {
        return level;
    }

    public Enemy(int x, int y, type health) {
        this.x = x;
        this.y = y;
        this.level = health;
        this.health = health.getHealth();
    }
}

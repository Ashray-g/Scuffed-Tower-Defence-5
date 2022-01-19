package towerDefence;

public class DifficultyLevelController {
    private static int level = 1;

    private static long moneys = Config.initMoney;

    public static long getMoneys() {
        return moneys;
    }

    public static void changeMoneys(long moneys) {
        DifficultyLevelController.moneys += moneys;
    }

    public static void resetMoney() {
        DifficultyLevelController.moneys = Config.initMoney;
    }

    public static int getLevel() {
        return level;
    }

    public static void setLevel(int level) {
        DifficultyLevelController.level = level;
    }
}

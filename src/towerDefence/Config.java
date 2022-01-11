package towerDefence;

import java.awt.*;

public class Config {
    public static int width = 11;
    public static int height = 11;

    public static double health = 100;

    public static double damageLevel1 = 15;
    public static double damageLevel2 = 25;
    public static double damageLevel3 = 40;
    public static double damageLevel4 = 75;

    public static int initMoney = 1000;

    public static boolean straightPath = false;

    public static double minPath = 3;
    public static double maxPath = 7;

    public static int trooperHealth = 100;
    public static int woodenHealth = 200;
    public static int terminatorHealth = 600;
    public static int megaHealth = 4000;

    public static int cost1 = 300;
    public static int cost2 = 500;
    public static int cost3 = 1200;
    public static int cost4 = 3000;

    public static int earningTrooper = 50;
    public static int earningWooden = 100;
    public static int earningTerminator = 300;
    public static int earningMega = 1300;

    public static int minLevelMega = 50;
    public static int minLevelTerminator = 15;

    public static double megaConstant = 0.1;
    public static double terminatorConstant = 0.5;
    public static double woodenConstant = 2;

    public static String fontName = "Comic Sans";
    public static Font font = new Font(fontName, Font.BOLD, 15);

    public static int healthBarSizeWidth = 20;
    public static int healthBarSizeHeight = 5;

    public static int levelLocationX = 10;
    public static int moneyLocationX = 360;
    public static int hudY = 20;

    public static int cursorWidth = 25;
    public static int cursorHeight = 25;

    public static String gameName = "Scuffed Tower Defense 5";

    public static int towerAndEnemyUpdateTimeMillis = 600;
    public static int initSpawnTimeMillis = 2500;
    public static int levelTimeMillis = 7000;
    public static int speedDecreaseTimeMillis = 50;


}

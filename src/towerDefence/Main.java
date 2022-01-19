package towerDefence;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    static ScheduledExecutorService[] ses;
    static int endGame;
    static Timer timer2;
    static TimerTask myTask2;
    public static void main(String[] args) throws IOException, InterruptedException {
        while(true) {

            initialize();

            initStart();
            initStartTowers();

            while (true) {
                Thread.sleep(Config.towerAndEnemyUpdateTimeMillis);
                StartScreenTowerCore.updateStartScreenEnemyPosition();
                StartScreenTowerCore.updateStartScreenTowerRotation();
                if (StartScreenControl.startButtonClicked) {
                    break;
                }
            }
            ses[0].shutdown();

            initGame();
            while (true) {
                Thread.sleep(Config.towerAndEnemyUpdateTimeMillis);
                TowerCore.updateEnemyPosition();
                TowerCore.updateTowerRotation();
                if (endGame > 1) break;
            }
            ses[0].shutdown();
            TimeUnit.SECONDS.sleep(5);
            SwingControl.getJFrame().setVisible(false);
            SwingControl.getJFrame().dispose();
            ses[0].shutdown();
            timer2.cancel();
            myTask2.cancel();
        }
    }

    public static void initialize()
    {
        Config.health = Config.healthConstant;
        StartScreenControl.startButtonClicked = false;
        endGame = 0;
        Board.init();
        StartBoard.init();
        DifficultyLevelController.resetMoney();
        DifficultyLevelController.setLevel(Config.startLevel);
    }
    public static void initStart() throws IOException{
        StartBoard.initStartScreen(Config.width, Config.height);
        StartScreenControl.init();

        ses = new ScheduledExecutorService[]{Executors.newScheduledThreadPool(1)};
        ses[0].scheduleAtFixedRate(Main::spawnStart, Config.initStartSpawnTimeMillis, Config.initStartSpawnTimeMillis, TimeUnit.MILLISECONDS);
    }

    public static void initGame() throws IOException, InterruptedException {
        Board.init(Config.width, Config.height);
        SwingControl.init();

        ses = new ScheduledExecutorService[]{Executors.newScheduledThreadPool(1)};
        ses[0].scheduleAtFixedRate(Main::spawnInGame, Config.initSpawnTimeMillis, Config.initSpawnTimeMillis, TimeUnit.MILLISECONDS);


        timer2 = new Timer();
        myTask2 = new TimerTask() {
            @Override
            public void run() {
                DifficultyLevelController.setLevel(DifficultyLevelController.getLevel() + 1);
                ses[0].shutdown();
                ses[0] = Executors.newScheduledThreadPool(1);;
                ses[0].scheduleAtFixedRate(Main::spawnInGame, Math.max(Config.initSpawnTimeMillis - DifficultyLevelController.getLevel() * Config.speedDecreaseTimeMillis, 700),
                        Math.max(Config.initSpawnTimeMillis - DifficultyLevelController.getLevel() * Config.speedDecreaseTimeMillis, 700), TimeUnit.MILLISECONDS);
            }
        };
        timer2.schedule(myTask2, Config.levelTimeMillis, Config.levelTimeMillis);
    }
    public static void initStartTowers(){
        StartScreenTowerCore.initStartTower();
    }

    public static void spawnStart(){ StartScreenTowerCore.spawnStartScreenEnemy(); }
    public static void spawnInGame(){ TowerCore.spawnEnemy(); }
}

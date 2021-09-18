package towerDefence;


import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        init();

        while(true){
            Thread.sleep(Config.towerAndEnemyUpdateTimeMillis);
            TowerCore.updateEnemyPosition();
            TowerCore.updateTowerRotation();
            System.out.println("Money: " + DifficultyLevelController.getMoneys());
        }

    }

    public static void init() throws IOException, InterruptedException {
        Board.init(Config.width, Config.height);
        SwingControl.init();

        final ScheduledExecutorService[] ses = {Executors.newScheduledThreadPool(1)};
        ses[0].scheduleAtFixedRate(Main::spawn, Config.initSpawnTimeMillis, Config.initSpawnTimeMillis, TimeUnit.MILLISECONDS);


        Timer timer2 = new Timer();
        TimerTask myTask2 = new TimerTask() {
            @Override
            public void run() {
                DifficultyLevelController.setLevel(DifficultyLevelController.getLevel() + 1);
                ses[0].shutdown();
                ses[0] = Executors.newScheduledThreadPool(1);;
                ses[0].scheduleAtFixedRate(Main::spawn, Math.max(Config.initSpawnTimeMillis - DifficultyLevelController.getLevel() * Config.speedDecreaseTimeMillis, 700),
                        Math.max(Config.initSpawnTimeMillis - DifficultyLevelController.getLevel() * Config.speedDecreaseTimeMillis, 700), TimeUnit.MILLISECONDS);
            }
        };

        timer2.schedule(myTask2, Config.levelTimeMillis, Config.levelTimeMillis);
    }

    public static void spawn(){
        TowerCore.spawnEnemy();
    }
}

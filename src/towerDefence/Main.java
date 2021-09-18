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
            Thread.sleep(600);
            TowerCore.updateEnemyPosition();
            TowerCore.updateTowerRotation();
            System.out.println("Money: " + DifficultyLevelController.getMoneys());
        }

    }

    public static void init() throws IOException, InterruptedException {
        Board.init(Config.width, Config.height);
        SwingControl.init();

        final ScheduledExecutorService[] ses = {Executors.newScheduledThreadPool(1)};
        ses[0].scheduleAtFixedRate(Main::spawn, 2500, 2500, TimeUnit.MILLISECONDS);


        Timer timer2 = new Timer();
        TimerTask myTask2 = new TimerTask() {
            @Override
            public void run() {
                DifficultyLevelController.setLevel(DifficultyLevelController.getLevel() + 1);
                ses[0].shutdown();
                ses[0] = Executors.newScheduledThreadPool(1);;
                ses[0].scheduleAtFixedRate(Main::spawn, Math.max(2500 - DifficultyLevelController.getLevel() * 50, 700), Math.max(2500 - DifficultyLevelController.getLevel() * 50, 700), TimeUnit.MILLISECONDS);
            }
        };

        timer2.schedule(myTask2, 7000, 7000);
    }

    public static void spawn(){
        TowerCore.spawnEnemy();
    }
}

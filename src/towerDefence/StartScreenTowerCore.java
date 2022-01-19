package towerDefence;

import towerDefence.tiles.BackgroundTile;
import towerDefence.tiles.Tile;
import towerDefence.tiles.TowerTile;

import java.util.ArrayList;

public class StartScreenTowerCore {

    public static void updateStartScreenEnemyPosition(){

        ArrayList<Enemy> remove  = new ArrayList<>();

        ArrayList<Enemy> ens = StartBoard.getStartEnemies(); //stop races

        for(Enemy e : ens){
            if(e.getHealth() <= 0){
                remove.add(e);
            }
            e.setPos(e.getPos()+1);
            if(StartBoard.getStartPath().size() == e.getPos()){
                remove.add(e);
            }else{
                if(e.getX() >= 0){
                    StartBoard.getStartBoard()[e.getY()][e.getX()].setHasEnemy(false);
                    StartBoard.getStartBoard()[e.getY()][e.getX()].setEn(null);
                }
                e.setX(StartBoard.getStartPath().get(e.getPos()).getX());
                e.setY(StartBoard.getStartPath().get(e.getPos()).getY());
                StartBoard.getStartBoard()[e.getY()][e.getX()].setHasEnemy(true);
                StartBoard.getStartBoard()[e.getY()][e.getX()].setEn(e);
            }
        }
        for(Enemy en : remove) {
            if (en.getY() != 0) {
                StartBoard.getStartEnemies().remove(en);
            }
        }
    }

    public static void spawnStartScreenEnemy() {
        Enemy.type t= Enemy.type.BASIC;
        int rand = (int)(Math.random() * 50) + 1;
        if(rand < (int)(DifficultyLevelController.getLevel() * Config.megaConstant) && DifficultyLevelController.getLevel() > Config.minLevelMega) t = Enemy.type.MEGA;
        else if (rand < DifficultyLevelController.getLevel()*Config.terminatorConstant && DifficultyLevelController.getLevel()  > Config.minLevelTerminator) t = Enemy.type.TERMINATOR;
        else if(rand * 3 < DifficultyLevelController.getLevel() * Config.woodenConstant) t = Enemy.type.WOODEN;
        Enemy en = new Enemy(-1, 5, t);
        en.setPos(-1);
        StartBoard.getStartEnemies().add(en);
    }
    public static void initStartTower() {
        int y = Config.height*3/8;
        int x = Config.width*1/2;
        int dy[] = new int[]{y-1, y-1, y+1, y+1};
        int dx[] = new int[]{x-2, x+2, x-3, x+3};
        for (int index = 0; index < 4; index ++) {
            StartBoard.getStartBoard()[dy[index]][dx[index]] = new TowerTile(Tile.State.DARK_GRASS);
            StartBoard.getStartBoard()[dy[index]][dx[index]].setX(dx[index]);
            StartBoard.getStartBoard()[dy[index]][dx[index]].setY(dy[index]);
            StartBoard.getStartBoard()[dy[index]][dx[index]].setTowerLevel(1);
            StartBoard.getStartTowers().add((TowerTile) StartBoard.getStartBoard()[dy[index]][dx[index]]);
        }
    }

    public static void updateStartScreenTowerRotation(){
        int[] dx =        {-1, -1, -1, 0,  0,  1, 1, 1};
        int[] dy =        {-1,  0,  1, 1, -1, -1, 0, 1};
        double[] piRad4 = { 7,  6,  5, 4,  0,  1, 2, 3};
        // 0 4 5
        // 1   6
        // 2 3 7
        for(TowerTile t : StartBoard.getStartTowers()){

            boolean found = false;
            int best = 0;
            for(BackgroundTile bt : StartBoard.getStartPath()){
                if(found) break;
                for(int i = 0;i<8;i++){
                    if(bt.getState() == Tile.State.SAND) {
                        if (bt.getX() == t.getX() + dx[i] && bt.getY() == t.getY() + dy[i]) {
                            best = i;
                            if(bt.isHasEnemy()){
                                found = true;
                                if(t.getTowerLevel() == 1) bt.number1().setHealth(bt.number1().getHealth() - Config.damageLevel1);
                                if(t.getTowerLevel() == 2) bt.number1().setHealth(bt.number1().getHealth() - Config.damageLevel2);
                                if(t.getTowerLevel() == 3) bt.number1().setHealth(bt.number1().getHealth() - Config.damageLevel3);
                                if(t.getTowerLevel() == 4) bt.number1().setHealth(bt.number1().getHealth() - Config.damageLevel4);
                                break;
                            }
                        }
                    }
                }
            }
            t.rotationRad = Math.PI * piRad4[best] / 4;
        }
    }
}

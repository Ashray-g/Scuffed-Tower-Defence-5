package towerDefence;

import towerDefence.tiles.BackgroundTile;
import towerDefence.tiles.Tile;
import towerDefence.tiles.TowerTile;

import java.util.ArrayList;

public class TowerCore {
    public static void updateEnemyPosition(){

        ArrayList<Enemy> remove  = new ArrayList<>();

        ArrayList<Enemy> ens = Board.getEnemies(); //stop races

        for(Enemy e : ens){
            if(e.getHealth() <= 0){
                if(e.getLevel() == Enemy.type.BASIC) DifficultyLevelController.changeMoneys(Config.earningTrooper);
                else if(e.getLevel() == Enemy.type.WOODEN) DifficultyLevelController.changeMoneys(Config.earningWooden);
                else if(e.getLevel() == Enemy.type.TERMINATOR) DifficultyLevelController.changeMoneys(Config.earningTerminator);
                else if(e.getLevel() == Enemy.type.MEGA) DifficultyLevelController.changeMoneys(Config.earningMega);
                remove.add(e);
            }
            e.setPos(e.getPos()+1);
            if(Board.getPath().size() == e.getPos()){
                remove.add(e);
            }else{
                if(e.getX() >= 0){
                    Board.getBoard()[e.getY()][e.getX()].setHasEnemy(false);
                    Board.getBoard()[e.getY()][e.getX()].setEn(null);
                }
                e.setX(Board.getPath().get(e.getPos()).getX());
                e.setY(Board.getPath().get(e.getPos()).getY());
                Board.getBoard()[e.getY()][e.getX()].setHasEnemy(true);
                Board.getBoard()[e.getY()][e.getX()].setEn(e);
            }
        }
        for(Enemy en : remove) {
            if (en.getY() != 0) {
                Board.getEnemies().remove(en);
            }
        }
    }

    public static void spawnEnemy(){
        Enemy.type t= Enemy.type.BASIC;
        int rand = (int)(Math.random() * 50) + 1;
        System.out.println(DifficultyLevelController.getLevel());
        if(rand < (int)(DifficultyLevelController.getLevel() * Config.megaConstant) && DifficultyLevelController.getLevel() > Config.minLevelMega) t = Enemy.type.MEGA;
        else if (rand < DifficultyLevelController.getLevel()*Config.terminatorConstant && DifficultyLevelController.getLevel()  > Config.minLevelTerminator) t = Enemy.type.TERMINATOR;
        else if(rand < DifficultyLevelController.getLevel() * Config.woodenConstant) t = Enemy.type.WOODEN;
        Enemy en = new Enemy(-1, 5, t);
        System.out.println(en.getLevel());
        en.setPos(-1);
        Board.getEnemies().add(en);
    }

    public static void spawnTower(int x, int y){
        if(Board.getBoard()[y][x].getTowerLevel() == 3){
            return;
        }

        if(Board.getBoard()[y][x].getSt() == Tile.State.SAND){
            return;
        }

        if(Board.getBoard()[y][x].getTowerLevel() == 2){
            if(DifficultyLevelController.getMoneys() >= Config.cost3){
                Board.getBoard()[y][x].setTowerLevel(Board.getBoard()[y][x].getTowerLevel() + 1);
                DifficultyLevelController.changeMoneys(-Config.cost3);
            }
            return;
        }

        if(Board.getBoard()[y][x].getTowerLevel() == 1){
            if(DifficultyLevelController.getMoneys() >= Config.cost2){
                Board.getBoard()[y][x].setTowerLevel(Board.getBoard()[y][x].getTowerLevel() + 1);
                DifficultyLevelController.changeMoneys(-Config.cost2);
            }
            return;
        }

        if(DifficultyLevelController.getMoneys() >= Config.cost1) {
            Board.getBoard()[y][x] = new TowerTile(Tile.State.DARK_GRASS);
            Board.getBoard()[y][x].setX(x);
            Board.getBoard()[y][x].setY(y);
            Board.getBoard()[y][x].setTowerLevel(1);
            Board.getTowers().add((TowerTile) Board.getBoard()[y][x]);
            DifficultyLevelController.changeMoneys(-Config.cost1);
        }

    }

    public static void updateTowerRotation(){
        int[] dx =        {-1, -1, -1, 0,  0,  1, 1, 1};
        int[] dy =        {-1,  0,  1, 1, -1, -1, 0, 1};
        double[] piRad4 = { 7,  6,  5, 4,  0,  1, 2, 3};
        // 0 4 5
        // 1   6
        // 2 3 7
        for(TowerTile t : Board.getTowers()){

            boolean found = false;
            int best = 0;
            for(BackgroundTile bt : Board.getPath()){
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

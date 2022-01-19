package towerDefence;

import towerDefence.tiles.BackgroundTile;
import towerDefence.tiles.Tile;
import towerDefence.tiles.TowerTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class StartBoard {
    private static Tile[][] startBoard;
    private static ArrayList<TowerTile> startTowers = new ArrayList<>();
    private static ArrayList<BackgroundTile> startPath = new ArrayList<>();
    private static ArrayList<Enemy> startEnemies = new ArrayList<>();

    public static void init() {
        startTowers = new ArrayList<>();
        startPath = new ArrayList<>();
        startEnemies = new ArrayList<>();
        startBoard = new Tile[Config.width][Config.height];
    }

    public static ArrayList<Enemy> getStartEnemies() { return startEnemies; }
    public static ArrayList<TowerTile> getStartTowers() {
        return startTowers;
    }
    public static ArrayList<BackgroundTile> getStartPath() {
        return startPath;
    }
    public static Tile[][] getStartBoard() {
        return startBoard;
    }


    /**
     * @param width width of the grid to generate
     * @param height height of the grid to generate
     *               This method will initialize the board and draw a randomly generated dfs path through it
     */
    public static void initStartScreen(int width, int height){
        if(width < 3 || height < 3){
            System.out.println("Error: width or height less than 3");
            return;
        }
        startBoard = new Tile[height][width];
        drawStartScreenPath(Config.startScreenStraightPath);
    }

    /**
     * @param straight: if this is true, it will draw a single sand path through the middle of the right to left
     *                otherwise, it will generate a random path from middle of right to middle of left
     */
    public static void drawStartScreenPath(boolean straight) {
        if(straight){
            int y = Config.height*3/8;
            for(int j = 0;j<Config.height;j++) {
                for (int i = 0; i < Config.width; i++) {
                    BackgroundTile bt = new BackgroundTile();
                    startBoard[j][i] = bt;
                    if(j == y){
                        bt.setState(Tile.State.SAND);
                        startPath.add(bt);
                    }
                    if(j == y+1 || j == y-1){
                        bt.setState(Tile.State.DARK_GRASS);
                    }

                    bt.setX(i);
                    bt.setY(j);

                }
            }

        }else{
            //TODO: Add DFS or A* path generation with random factor (seed)
//            proceduralGenerationTest(Config.width, Config.height);
            int[][] path = new int[Config.height][Config.width];
            path = algorithms.dfs(path);
            for(int i = 0;i<path.length;i++){
                for(int j = 0;j<path[0].length;j++){
                    Tile.State st;
                    if(path[i][j] == 0){
                        int h = (int)(Math.random()*2);
                        st = Tile.State.GRASS;
                        if(h == 1 ) st = Tile.State.DARK_GRASS;
                    }else{
                        st = Tile.State.SAND;
                    }

                    BackgroundTile bt = new BackgroundTile(st);
                    startBoard[i][j] = bt;

                    bt.setX(j);
                    bt.setY(i);

                }
            }

            for(Point p : algorithms.pathTaken){
                getStartPath().add((BackgroundTile) startBoard[p.y][p.x]);
            }

        }
    }

    /**
     *
     * @param x x position from left 0 indexed
     * @param y y position from top, 0 indexed
     * @param width width of matrix
     * @param height height of matrix
     * @return list of the BackgroundTile object (not including towers)
     */
    public static ArrayList<BackgroundTile> startNeighbors(int x, int y, int width, int height){
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        ArrayList<BackgroundTile> neighs = new ArrayList<>();

        for(int i = 0;i<4;i++){
            int newX = x + dx[i];
            int newY = y + dy[i];
            if(newX < width && newX >= 0 && newY >= 0 && newY < height){
                if(startBoard[newY][newX] !=null && startBoard[newY][newX].getTowerLevel()<=0){
                    neighs.add((BackgroundTile) startBoard[newY][newX]);
                }
            }
        }

        return neighs;
    }
}

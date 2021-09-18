package towerDefence;

import towerDefence.tiles.BackgroundTile;
import towerDefence.tiles.Tile;
import towerDefence.tiles.TowerTile;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private static Tile[][] board;
    private static ArrayList<TowerTile> towers = new ArrayList<>();
    private static ArrayList<BackgroundTile> path = new ArrayList<>();
    private static ArrayList<Enemy> enemies = new ArrayList<>();

    public static ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public static ArrayList<TowerTile> getTowers() {
        return towers;
    }

    public static ArrayList<BackgroundTile> getPath() {
        return path;
    }

    public static Tile[][] getBoard() {
        return board;
    }


    /**
     * @param width width of the grid to generate
     * @param height height of the grid to generate
     *               This method will initialize the board and draw a randomly generated dfs path through it
     */
    public static void init(int width, int height){
        if(width < 3 || height < 3){
            System.out.println("Error: width or height less than 3");
            return;
        }
        board = new Tile[height][width];
        drawPath(Config.straightPath);
    }

    /**
     * @param straight: if this is true, it will draw a single sand path through the middle of the right to left
     *                otherwise, it will generate a random path from middle of right to middle of left
     */
    public static void drawPath(boolean straight) {
        if(straight){
            int y = Config.height/2;
            for(int j = 0;j<Config.height;j++) {
                for (int i = 0; i < Config.width; i++) {
                    BackgroundTile bt = new BackgroundTile();
                    board[j][i] = bt;
                    if(j == y){
                        bt.setState(Tile.State.SAND);
                        path.add(bt);
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
            int[][] path = new int[11][11];
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
                    board[i][j] = bt;

                    bt.setX(j);
                    bt.setY(i);

                }
            }

            for(Point p : algorithms.pathTaken){
                getPath().add((BackgroundTile) board[p.y][p.x]);
            }

        }
    }

    public static void proceduralGenerationTest(int width, int height) throws InterruptedException {
        int y = height/2;

        for(int i =0;i<width;i++){
            board[y][i] = new BackgroundTile(Tile.State.SAND);
            board[y][i].setX(i);
            board[y][i].setY(y);
        }

        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                if(!(i == y)) {
                    Tile.State st = Tile.State.GRASS;
                    ArrayList<BackgroundTile> neighs = neighbors(j, i, width, height);
                    int sand = 0;
                    int grass = 0;
                    for (BackgroundTile b : neighs) {
                        if (b.getState() == Tile.State.GRASS) {
                            grass++;
                        } else if (b.getState() == Tile.State.SAND) {
                            sand++;
                        }
                    }

                    ArrayList<Tile.State> poss = new ArrayList<>();

                    if (sand == 1 || sand == 2) {
                        for(int g =0;g<7;g++){
                            poss.add(Tile.State.SAND);

                        }
                    }
                    if (grass >= 0) {
                        poss.add(Tile.State.GRASS);
                        poss.add(Tile.State.DARK_GRASS);
                    }

                    Collections.shuffle(poss);

                    if (poss.size() > 0) {
                        st = poss.get(0);
                    }

                    BackgroundTile bt = new BackgroundTile(st);
                    board[i][j] = bt;

                    bt.setX(i);
                    bt.setY(j);
                }

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
    public static ArrayList<BackgroundTile> neighbors(int x, int y, int width, int height){
        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        ArrayList<BackgroundTile> neighs = new ArrayList<>();

        for(int i = 0;i<4;i++){
            int newX = x + dx[i];
            int newY = y + dy[i];
            if(newX < width && newX >= 0 && newY >= 0 && newY < height){
                if(board[newY][newX] !=null && board[newY][newX].getTowerLevel()<=0){
                    neighs.add((BackgroundTile) board[newY][newX]);
                }
            }
        }

        return neighs;
    }
}

package towerDefence;


import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Stack;

public class algorithms {
    public static ArrayList<Point> pathTaken = new ArrayList<>();

    /**
     *
     * @param path
     * @return an int[][] where 1 is a sand tile and 0 is a grass tile
     */
    public static int[][] dfs(int[][] path){
        Stack<Point> st = new Stack<>();
        st.add(new Point(0, path.length/2));
        Point goal = new Point(path[0].length-1, path.length/2);


        Point[][] vis = new Point[path.length][path[0].length];
        int[][] dist = new int[path.length][path[0].length];
        for(int[] j : dist){
            Arrays.fill(j, Integer.MAX_VALUE);
        }

        vis[path.length/2][0] = new Point(-1, -1);
        dist[path.length/2][0] = 0;

        int[] dx = {0, 1, 0, -1};
        int[] dy = {1, 0, -1, 0};

        ArrayList<Integer> r = new ArrayList<>();
        r.add(0);r.add(1);r.add(2);r.add(3);

        while(!st.isEmpty()){
            Point cur = st.pop();
            if(cur.x == goal.x && cur.y == goal.y){
                break;
            }
            Collections.shuffle(r);

            for(int i = 0;i<4;i++){
                int newX = cur.x + dx[r.get(i)];
                int newY = cur.y + dy[r.get(i)];
                if(newX >= 0 && newY >= 0 && newX < path[0].length && newY < path.length){
                    if(vis[newY][newX] == null){
                        dist[newY][newX] = dist[cur.y][cur.x] + 1;
                        if(dist[newY][newX] < path.length * Config.maxPath){
                            vis[newY][newX] = new Point(cur.x, cur.y);
                            st.push(new Point(newX, newY));
                        }
                    }
                }
            }
        }

        Point cur = goal;
        int len  =0;
        pathTaken = new ArrayList<>();
        while(true){
            pathTaken.add(0, new Point(cur));
            path[cur.y][cur.x] = 1;
            cur = vis[cur.y][cur.x];
            len++;

            if(cur == null || cur.x==-1) break;
        }
        if(len < path[0].length*Config.minPath) path = dfs(new int[path.length][path[0].length]);

        return path;
    }
}

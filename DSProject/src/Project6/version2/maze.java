package Project6.version2;

import java.util.ArrayList;
import java.util.List;

public class maze {

    private int[][] maze;
    private int m;
    private int n;
    private int[] start;
    private int[] end;
    public List<List<int[]>> Location = new ArrayList<>();
    public List<List<String>> Direction = new ArrayList<>();
    //private String[] dir = {"right","down","left","up",};

    public int[][] creatMaze(int m,int n,int[] start,int[] end){

        this.m = m;
        this.n = n;
        this.start = start;
        this.end = end;
        this.maze = new int[m+2][n+2];
        int[][] map = new int[m][n];
        for(int i=0;i<m+2;i++){
            for(int j=0;j<n+2;j++){
                if(i==0 || j==0 || i==m+1 || j==n+1){
                    maze[i][j] = 1;//迷宫四周全为障碍
                }
                else if(i == start[0] && j == start[1] || i == end[0] && j == end[1]){
                    maze[i][j] = 0;//入口和出口不能设置障碍
                }
                else{
                    maze[i][j] = ((int)(Math.random()*100)%3 == 0 ? 1 : 0);//三目 运算 随机设置障碍
                }
            }
        }

        for(int i=1;i<=m;i++){
            for(int j=1;j<=n;j++){
                map[i-1][j-1] = maze[i][j];
            }
        }

        return map;
    }


    public void solve(){

        List<int[]> LocList = new ArrayList<>();
        List<String> DirList = new ArrayList<>();
        solve(start[0],start[1],LocList,DirList,maze,null);
    }

    //递归
    public void solve(int i,int j,List<int[]> LocList,List<String> DirList,int[][] map,String direction){

        //递归条件
        if(i<1 || i>m || j<1 || j>n){
            return ;
        }
        if(map[i][j] != 0){
            return;
        }
        else if(map[i][j] == 0){

            //到达终点
            if(i == end[0] && j == end[1]){
                LocList.add(end);
                DirList.add(direction);
                List<int[]> tempLoc = new ArrayList<>(LocList);
                List<String> tempDir = new ArrayList<>(DirList);
                Location.add(tempLoc);
                Direction.add(tempDir);
                return;
            }

            //访问过的点设置为2
            map[i][j] = 2;
            int[] temp = {i,j};
            LocList.add(temp);
            DirList.add(direction);
        }

        //right
        solve(i,j+1,LocList,DirList,map,"right");

        //down
        solve(i+1,j,LocList,DirList,map,"down");

        //left
        solve(i,j-1,LocList,DirList,map,"left");

        //up
        solve(i-1,j,LocList,DirList,map,"up");

        LocList.remove(LocList.size()-1);
        DirList.remove(DirList.size()-1);
    }

    /*public static void main(String[] args){

        Maze.version2.maze maze = new maze();
        int[] start = {1,1};
        int[] end = {6,6};
        int[][] map = maze.creatMaze(6,6,start,end);

        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }
        maze.solve();

        if(maze.Location.size() == 0){
            System.out.println("No way!");
        }
        else{
            for(int i=0;i<maze.Location.size();i++){

                for(int j=0;j<maze.Location.get(i).size();j++){

                    for(int k=0;k<maze.Location.get(i).get(j).length;k++){
                        System.out.print(maze.Location.get(i).get(j)[k]+" ");
                    }
                    System.out.println(maze.Direction.get(i).get(j));
                }
                System.out.println();
            }
        }
    }*/
}

package Project6.version1;

import java.util.ArrayList;
import java.util.List;

public class mazeForStack {

    public int[][] creatMaze(int m,int n,int[] start,int[] end){

        int[][] maze = new int[m+2][n+2];

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
        return maze;
    }


    public int[][] solveMaze(){
        int[] start = {1,1};
        int[] end = {6,6};
        int[][] maze = creatMaze(6,6,start,end);
        int[][] map = maze;

        for(int i=1;i<map.length-1;i++){
            for(int j=1;j<map[i].length-1;j++){
                System.out.print(map[i][j]+" ");
            }
            System.out.println();
        }

        List<int[]> arrayLocation = new ArrayList<>();
        List<String> arrayDirection = new ArrayList<>();

        MyStack<String> direction = new MyStack<>();
        MyStack<int[]> location = new MyStack<>();

        String[] dir = {"right","down","left","up",};

        location.push(start);
        maze[start[0]][start[1]] = 2;//2表示已经访问过的点
        while(!location.isEmpty()){
            int i = location.peek()[0];
            int j = location.peek()[1];

            //right
            if(maze[i][j+1] == 0){
                direction.push(dir[0]);
                j = j+1;
            }
            //down
            else if(maze[i+1][j] == 0){
                direction.push(dir[1]);
                i = i+1;
            }
            //left
            else if(maze[i][j-1] == 0){
                direction.push(dir[2]);
                j = j-1;
            }
            //up
            else if(maze[i-1][j] == 0){
                direction.push(dir[3]);
                i = i-1;
            }
            else{
                int[] p = location.pop();
                if(p[0] != start[0] && p[1] != start[1]){
                    direction.pop();
                }
                continue;
            }
            maze[i][j] = 2;//2表示已经访问过的点
            int[] temp = {i,j};
            location.push(temp);
            if(i == end[0] && j == end[1]){
                break;
            }
        }
        if(location.isEmpty()){
            System.out.println("No Way!");
            return map;
        }

        //toArray
        int size = location.size();
        for(int i1 = 0;i1 < size;i1++){
            arrayLocation.add(location.pop());
            if(i1 != size-1){
                arrayDirection.add(direction.pop());
            }
        }

        for(int i1 = arrayLocation.size()-1;i1 >= 0;i1--){
            for(int j1 = 0;j1 < arrayLocation.get(i1).length;j1++){
                System.out.print(arrayLocation.get(i1)[j1]+" ");
            }
            if(i1 != 0){
                System.out.println(arrayDirection.get(i1-1));
            }
        }
        return  map;
    }

    public static void main(String[] args){
        mazeForStack maze = new mazeForStack();
        int[] start = {1,1};
        int[] end = {6,6};
        int[][] graph = maze.solveMaze();//maze.creatMaze(6,6,start,end);
        /*System.out.println();
        System.out.println();
        for(int i=0;i<graph.length;i++){
            for(int j=0;j<graph[i].length;j++){
                System.out.print(graph[i][j]+" ");
            }
            System.out.println();
        }*/
    }

}

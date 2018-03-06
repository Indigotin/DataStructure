package Project6.version2;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.List;

public class MazePane extends Application{

    private VBox vBox = new VBox();
    private maze Maze = new maze();
    private VBox right = new VBox();
    private Text noWay = new Text("No Way!");
    private int[][] maze;
    private int[] start;
    private int[] end;
    private int m = 8;
    private int n = 8;
    //方案数量
    private int count = 0;
    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        Button create = new Button("随机生成");
        Button show = new Button("寻找出口");
        right.getChildren().addAll(create,show);
        right.setSpacing(10);
        right.setAlignment(Pos.CENTER);
        borderPane.setRight(right);
        vBox.setSpacing(2);
        borderPane.setCenter(vBox);

        create.setOnAction(e->{
            count = 0;
            vBox.getChildren().removeAll(vBox.getChildren());

            createMaze();
        });

        show.setOnAction(e->{
            findSolution();
        });

        primaryStage.setScene(new Scene(borderPane,1200,1000));
        primaryStage.setTitle("Maze");
        primaryStage.show();
    }

    private void createMaze(){

        Maze.Location.clear();
        Maze.Direction.clear();
        right.getChildren().remove(noWay);
        int[] start = {1,1};
        int[] end = {m,n};
        this.end = end;
        maze = Maze.creatMaze(m,m,start,end);
        printMap(maze);
    }

    private void printMap(int[][] map){
        for(int i=0;i<map.length;i++) {
            HBox hBox = new HBox();
            for (int j = 0; j < map[i].length;j++) {

                Image image = new Image("Project6/Pic/1.jpg");

                if(i == end[0]-1 && j == end[1]-1)
                    image = new Image("Project6/Pic/success.jpg");
                else if(map[i][j] == 0)
                    image = new Image("Project6/Pic/1.jpg");
                else if(map[i][j] == 1)
                    image = new Image("Project6/Pic/no.jpg");
                else if (map[i][j] == 2)
                    image = new Image("Project6/Pic/up.jpg");
                else if (map[i][j] == 3)
                    image = new Image("Project6/Pic/down.jpg");
                else if (map[i][j] == 4)
                    image = new Image("Project6/Pic/left.jpg");
                else if (map[i][j] == 5)
                    image = new Image("Project6/Pic/right.jpg");
                ImageView imageView = new ImageView(image);
                hBox.getChildren().add(imageView);
            }
            hBox.setSpacing(2);
            vBox.getChildren().add(hBox);
        }
    }


    private int[][] getNewMap(List<int[]> location,List<String> direction){

        int[][] map = new int[m][n];

        for(int i=1;i<direction.size();i++){
            switch (direction.get(i)){
                case "up":
                    map[location.get(i-1)[0]-1][location.get(i-1)[1]-1] = 2;
                    break;
                case "down":
                    map[location.get(i-1)[0]-1][location.get(i-1)[1]-1] = 3;
                    break;
                case "left":
                    map[location.get(i-1)[0]-1][location.get(i-1)[1]-1] = 4;
                    break;
                case "right":
                    map[location.get(i-1)[0]-1][location.get(i-1)[1]-1] = 5;
                    break;
            }
        }

        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                if(maze[i][j] == 1){
                    map[i][j] = 1;
                }
            }
        }

        return map;
    }

    private void findSolution(){
        Maze.solve();

        //有解
        if(Maze.Location.size() != 0){
            vBox.getChildren().removeAll(vBox.getChildren());
            int[][] solution = getNewMap(Maze.Location.get(count),
                    Maze.Direction.get(count));
            count++;
            if(count >= Maze.Location.size())
                count=0;
            printMap(solution);

        }
        else{
            right.getChildren().add(noWay);
        }
    }

    public static void main(String[] args){
        Application.launch(args);
    }
}

package Project4.Graph.version2;

import Project4.Graph.version1.AbstractGraph;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class pane2 extends Application{

    private UnweightedGraph<String> graph;
    private List<AbstractGraph.Edge> edges = new ArrayList<>();
    private List<String> vertices = new ArrayList<>();
    private List<double[]> verXY = new ArrayList<>();
    private Circle[] VCircle;
    private Text[] Vname;
    private Line[] edgesLines;
    private Pane center = new Pane();
    private Text tips = new Text();
    private TextField start = new TextField();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        HBox hBox = new HBox(center);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(hBox);
        Button showBFS = new Button("查看广度遍历");
        Button showDFS = new Button("查看深度遍历");
        HBox bottom = new HBox(new Text("请输入一个起点"),start,showBFS,showDFS);
        bottom.setSpacing(15);
        bottom.setAlignment(Pos.CENTER);
        borderPane.setBottom(bottom);
        initGraph();
        ShowGraph();
        showBFS.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            showBFS(primaryStage);
        });

        showDFS.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            showDFS(primaryStage);
        });
        Scene scene = new Scene(borderPane,800,500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }

    //BFS
    private void showBFS(Stage primaryStage){
        ShowGraph();
        try{
            if(start.getText().length() == 0)
                throw new NumberFormatException();
            int Start = Integer.parseInt(start.getText());
            graph = new UnweightedGraph<>(edges,vertices);
            AbstractGraph.Tree bfsTree = graph.bfs(Start);
            //画出bfs图
            int[] parents = new int[vertices.size()];
            for(int i=0;i<vertices.size();i++){
                parents[i] = bfsTree.getParent(i);
            }
            drawPath(parents);

        }catch (NumberFormatException ex){
            tips.setText("请输入有效下标！");
            tipsPage(primaryStage);
        }
    }

    //DFS
    private void showDFS(Stage primaryStage){
        ShowGraph();
        try{
            if(start.getText().length() == 0)
                throw new NumberFormatException();
            int Start = Integer.parseInt(start.getText());
            graph = new UnweightedGraph<>(edges,vertices);
            AbstractGraph.Tree dfsTree = graph.dfs(Start);
            //画出dfs图
            int[] parents = new int[vertices.size()];
            for(int i=0;i<vertices.size();i++){
                parents[i] = dfsTree.getParent(i);
            }
            drawPath(parents);

        }catch (NumberFormatException ex){
            tips.setText("请输入有效下标！");
            tipsPage(primaryStage);
        }

    }
    private void drawPath(int[] parents){
        for(int i=0;i<vertices.size();i++){
            VCircle[i].setFill(Color.RED);
            VCircle[i].setStroke(Color.RED);
            if(parents[i] != -1){
                for(int j=0;j<edges.size();j++){
                    if((edges.get(j).u == i && edges.get(j).v == parents[i]) ||
                            (edges.get(j).v == i && edges.get(j).u == parents[i])){
                        edgesLines[j].setStroke(Color.RED);
                        //parent[i]->i
                        if(edges.get(j).u == parents[i] && edges.get(j).v == i)
                            drawArrowLine(edgesLines[j].getStartX(),edgesLines[j].getStartY(),
                                    edgesLines[j].getEndX(),edgesLines[j].getEndY());
                    }
                }
            }
        }
    }

    private void ShowGraph(){

        VCircle = new Circle[vertices.size()];
        Vname = new Text[vertices.size()];
        for(int i=0;i<VCircle.length;i++){
            VCircle[i] = new Circle(verXY.get(i)[0],verXY.get(i)[1],5);
            Vname[i] = new Text(verXY.get(i)[0]-10,verXY.get(i)[1]-10,vertices.get(i)+"("+i+")");
            center.getChildren().addAll(VCircle[i],Vname[i]);
        }
        edgesLines = new Line[edges.size()];
        for(int i=0;i<edges.size();i++){
            edgesLines[i] = new Line(verXY.get(edges.get(i).u)[0],verXY.get(edges.get(i).u)[1],
                    verXY.get(edges.get(i).v)[0],verXY.get(edges.get(i).v)[1]);
            System.out.println("i="+i+"edges.get(i).u="+edges.get(i).u+"  edges.get(i).v="+edges.get(i).v);
            center.getChildren().add(edgesLines[i]);
        }
    }

    //初始化一张图
    private void initGraph(){

        String[] StrTemp = {"Seattle","San Francisco","Los Angeles","Denver",
                            "Kansas City","Chicago","Boston","New York","Atlanta",
                            "Miami","Dallas","Houston"};

        double[][] XYTemp = {{75, 50},{50, 210},{75, 275},
                        {275, 175}, {400, 245}, {450, 100},
                        {700, 80}, {675, 120}, {575, 295},
                        {600, 400},{408, 325},{450, 360}};
        // Edge array for graph in Figure 27.1
        int[][] edgesTemp = {
                {0, 1}, {0, 3}, {0, 5},
                {1, 0}, {1, 2}, {1, 3},
                {2, 1}, {2, 3}, {2, 4}, {2, 10},
                {3, 0}, {3, 1}, {3, 2}, {3, 4}, {3, 5},
                {4, 2}, {4, 3}, {4, 5}, {4, 7}, {4, 8}, {4, 10},
                {5, 0}, {5, 3}, {5, 4}, {5, 6}, {5, 7},
                {6, 5}, {6, 7},
                {7, 4}, {7, 5}, {7, 6}, {7, 8},
                {8, 4}, {8, 7}, {8, 9}, {8, 10}, {8, 11},
                {9, 8}, {9, 11},
                {10, 2}, {10, 4}, {10, 8}, {10, 11},
                {11, 8}, {11, 9}, {11, 10}};

        for(int i=0;i<StrTemp.length;i++)
            vertices.add(StrTemp[i]);

        for(int i=0;i<XYTemp.length;i++)
            verXY.add(XYTemp[i]);

        for(int i=0;i<edgesTemp.length;i++)
            edges.add(new AbstractGraph.Edge(edgesTemp[i][0],edgesTemp[i][1]));

    }

    //画箭头
    public void drawArrowLine(double x1, double y1,double x2, double y2) {

        // find slope of this line
        double slope = (y1 - y2) / (x1 - x2);
        double arctan = Math.atan(slope);

        // This will flip the arrow 45 off of a
        // perpendicular line at pt x2
        double set45 = 1.57 / 2;

        // arrows should always point towards i, not i+1
        if (x1 < x2) {
            // add 90 degrees to arrow lines
            set45 = -1.57 * 1.5;
        }

        // set length of arrows
        int arrlen = 15;

        // draw arrows on line
        Line[] lines = new Line[2];
        lines[0] = new Line(x2, y2, (int) ((x2 + (Math.cos(arctan + set45) * arrlen))),
                (int) (((y2)) + (Math.sin(arctan + set45) * arrlen)));
        lines[1] = new Line(x2, y2, (int) ((x2 + (Math.cos(arctan - set45) * arrlen))),
                (int) (((y2)) + (Math.sin(arctan - set45) * arrlen)));
        lines[0].setStroke(Color.RED);
        lines[1].setStroke(Color.RED);
        center.getChildren().addAll(lines[0],lines[1]);
    }

    //提示界面
    public void tipsPage(Stage primaryStage){
        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            pane2 temp = new pane2();
            try {
                temp.start(primaryStage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        Scene scene = new Scene(vBox,300,100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        Application.launch(args);
    }
}

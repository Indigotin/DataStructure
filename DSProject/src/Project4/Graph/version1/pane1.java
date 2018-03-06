package Project4.Graph.version1;

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

public class pane1 extends Application{

    private WeightedGraph<String> graph;
    private List<WeightedEdge> edges = new ArrayList<>();
    private List<String> vertices = new ArrayList<>();
    private List<double[]> verXY = new ArrayList<>();
    private Circle[] V;
    private Text[] Vname;
    private Text[] WeightText;
    private Line[] edgesLines;
    private Pane center = new Pane();
    private Text tips = new Text();

    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        Text AddVertex = new Text("Add a new Vertex");
        Text VertexName = new Text("Vertex Name");
        TextField vertexNameField = new TextField();
        HBox hBox1 = new HBox(VertexName,vertexNameField);
        Text XOfVertex = new Text("X-Coordinate");
        TextField XInput = new TextField();
        HBox hBox2 = new HBox(XOfVertex,XInput);
        Text YOfVertex = new Text("Y-Coordinate");
        TextField YInput = new TextField();
        HBox hBox3 = new HBox(YOfVertex,YInput);
        Button addV = new Button("Add Vertex");
        VBox footLeft = new VBox(AddVertex,hBox1,hBox2,hBox3,addV);
        footLeft.setSpacing(5);
        footLeft.setAlignment(Pos.CENTER);
        Text AddEdge = new Text("Add a new Edge");
        Text vertexU = new Text("Vertex U (index)");
        TextField UInput = new TextField();
        HBox hBox4 = new HBox(vertexU,UInput);
        Text vertexV = new Text("Vertex V (index)");
        TextField VInput = new TextField();
        HBox hBox5 = new HBox(vertexV,VInput);
        Text WeightTips = new Text("    Weight  (int) ");
        TextField Weight = new TextField();
        HBox hBox6 = new HBox(WeightTips,Weight);
        Button addE = new Button("Add Edge");
        VBox footRight = new VBox(AddEdge,hBox4,hBox5,hBox6,addE);
        footRight.setSpacing(5);
        footRight.setAlignment(Pos.CENTER);
        HBox hBox = new HBox(footLeft,footRight);
        hBox.setSpacing(250);
        hBox.setAlignment(Pos.CENTER);
        Button MinimumSpanningTree = new Button("查看最小生成树");
        Button ShortestPath = new Button("查看最短路径");
        Button initGraph = new Button("使用默认地图");
        Button StartOver = new Button("重新开始");
        HBox footButton = new HBox(MinimumSpanningTree,ShortestPath,initGraph,StartOver);//
        footButton.setSpacing(50);
        footButton.setAlignment(Pos.CENTER);
        VBox buttom = new VBox(hBox,footButton);
        buttom.setAlignment(Pos.CENTER);
        initGraph.setOnAction(e->{
            initGraph();
            ShowGraph();
        });
        if(vertices.size()!=0||edges.size()!=0){
            ShowGraph();
        }
        StartOver.setOnAction(e->{
            tips.setText("此操作将会清除所有内容,请确认");
            vertices.clear();
            edges.clear();
            verXY.clear();
            tipsPage(primaryStage);
        });
        addV.setOnAction(e-> {

            try {
                if(vertexNameField.getText().length()==0||XInput.getText().length()==0||
                        YInput.getText().length()==0)
                    throw new NumberFormatException();
                center.getChildren().removeAll(center.getChildren());
                vertices.add(vertexNameField.getText());
                double[] temp = {Double.parseDouble(XInput.getText()), Double.parseDouble(YInput.getText())};
                verXY.add(temp);
                ShowGraph();
            }catch (NumberFormatException ex){
                tips.setText("请输入有效数据！");
                tipsPage(primaryStage);
            }
        });

        addE.setOnAction(e->{
            try {
                center.getChildren().removeAll(center.getChildren());
                if (Integer.parseInt(UInput.getText()) < 0 || Integer.parseInt(UInput.getText()) >= vertices.size()
                        || Integer.parseInt(VInput.getText()) < 0 || Integer.parseInt(VInput.getText()) >= vertices.size()) {
                    tips.setText("请输入有效的下标！");
                    tipsPage(primaryStage);
                }
                else{
                    edges.add(new WeightedEdge(Integer.parseInt(UInput.getText()),
                            Integer.parseInt(VInput.getText()), Integer.parseInt(Weight.getText())));
                    edges.add(new WeightedEdge(Integer.parseInt(VInput.getText()),
                            Integer.parseInt(UInput.getText()), Integer.parseInt(Weight.getText())));
                    ShowGraph();
                }
            }
             catch (NumberFormatException ex){
                tips.setText("请输入有效数据！");
                tipsPage(primaryStage);
            }
        });

        MinimumSpanningTree.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            getMST(primaryStage);
        });

        ShortestPath.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            getShortestPath(primaryStage);
        });
        borderPane.setCenter(center);
        borderPane.setBottom(buttom);
        primaryStage.setScene(new Scene(borderPane,1000,600));
        primaryStage.setTitle("Graph");
        primaryStage.show();
    }

    private void getShortestPath(Stage primaryStage){
        ShowGraph();
        graph = new WeightedGraph(edges,vertices);
        BorderPane border = new BorderPane();
        border.setCenter(center);
        TextField Start = new TextField();
        TextField End = new TextField();
        Button btOk = new Button("确定");
        HBox hBox = new HBox(new Text("请输入起点"),Start,new Text("请输入终点"),End,btOk);
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(10);
        Button Last = new Button("返回");
        VBox bottom = new VBox(hBox,Last);
        bottom.setAlignment(Pos.CENTER);
        bottom.setSpacing(10);
        Last.setOnAction(e->{
            tips.setText("您是否要返回？");
            tipsPage(primaryStage);
        });

        border.setBottom(bottom);
        try{
            btOk.setOnAction(e->{

                if(Integer.parseInt(End.getText())<0 || Integer.parseInt(End.getText())>=vertices.size()
                        ||Integer.parseInt(Start.getText())<0 || Integer.parseInt(Start.getText())>=vertices.size()){
                    tips.setText("请输入有效的下标！");
                    tipsPage(primaryStage);
                }
                else{
                    center.getChildren().removeAll(center.getChildren());
                    ShowGraph();
                    WeightedGraph.ShortestPathTree tree =
                            graph.getShortestPath(Integer.parseInt(End.getText()));
                    List<Integer> path = tree.getPath(Integer.parseInt(Start.getText()));
                    for(int i=0;i+1<path.size();i++){
                        System.out.println("path.get(i)="+path.get(i)+"  path.get(i+1)="+path.get(i+1));
                        for(int j=0;j<edges.size();j++){

                            if((edges.get(j).u == path.get(i) && edges.get(j).v == path.get(i+1)) ||
                                    (edges.get(j).v == path.get(i) && edges.get(j).u == path.get(i+1))){
                                edgesLines[j].setStroke(Color.RED);
                                if(edges.get(j).u == path.get(i) && edges.get(j).v == path.get(i+1))
                                    drawArrowLine(edgesLines[j].getStartX(),edgesLines[j].getStartY(),
                                            edgesLines[j].getEndX(),edgesLines[j].getEndY());
                            }
                            System.out.println();
                        }
                    }
                }
            });
        }
        catch (NumberFormatException ex){

        }

        Scene scene = new Scene(border,1000,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    private void getMST(Stage primaryStage){
        ShowGraph();
        graph = new WeightedGraph(edges,vertices);
        BorderPane border = new BorderPane();
        TextField inputStart = new TextField();
        Button btOK = new Button("确定");
        HBox hBox = new HBox(new Text("请输入一个起点的下标："),inputStart,btOK);
        hBox.setSpacing(10);
        hBox.setAlignment(Pos.CENTER);
        Button Last = new Button("返回");
        VBox bottom = new VBox(hBox,Last);
        bottom.setSpacing(10);
        bottom.setAlignment(Pos.CENTER);
        Last.setOnAction(e->{
            tips.setText("您是否要返回？");
            tipsPage(primaryStage);
        });
        border.setCenter(center);
        border.setBottom(bottom);

        btOK.setOnAction(e->{

            if(Integer.parseInt(inputStart.getText())<0 || Integer.parseInt(inputStart.getText())>=vertices.size()){
                tips.setText("请输入有效的下标！");
                tipsPage(primaryStage);
            }
            else{
                center.getChildren().removeAll(center.getChildren());
                ShowGraph();
                WeightedGraph.MST Mst = graph.getMinimumSpanningTree(Integer.parseInt(inputStart.getText()));
                int[] parent = new int[vertices.size()];
                for(int i=0;i<vertices.size();i++){
                    V[i].setFill(Color.RED);
                    V[i].setStroke(Color.RED);
                    parent[i] = Mst.getParent(i);
                    if(parent[i] != -1){
                        for(int j=0;j<edges.size();j++){
                            if((edges.get(j).u == i && edges.get(j).v == parent[i]) ||
                                    (edges.get(j).v == i && edges.get(j).u == parent[i])){
                                edgesLines[j].setStroke(Color.RED);
                                if(edges.get(j).u == parent[i] && edges.get(j).v == i)
                                    drawArrowLine(edgesLines[j].getStartX(),edgesLines[j].getStartY(),
                                            edgesLines[j].getEndX(),edgesLines[j].getEndY());
                            }
                        }
                    }
                }
            }
        });

        Scene scene = new Scene(border,1000,600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void ShowGraph(){

        V = new Circle[vertices.size()];
        Vname = new Text[vertices.size()];
        for(int i=0;i<V.length;i++){
            V[i] = new Circle(verXY.get(i)[0],verXY.get(i)[1],5);
            Vname[i] = new Text(verXY.get(i)[0]-10,verXY.get(i)[1]-10,vertices.get(i)+"("+i+")");
            center.getChildren().addAll(V[i],Vname[i]);
        }

        edgesLines = new Line[edges.size()];
        WeightText = new Text[edges.size()];

        for(int i=0;i<edges.size();i++){
            edgesLines[i] = new Line(verXY.get(edges.get(i).u)[0],verXY.get(edges.get(i).u)[1],
                    verXY.get(edges.get(i).v)[0],verXY.get(edges.get(i).v)[1]);
            //System.out.println("i="+i+"edges.get(i).u="+edges.get(i).u+"  edges.get(i).v="+edges.get(i).v);
            //显示权重
            WeightText[i] = new Text((verXY.get(edges.get(i).u)[0]+verXY.get(edges.get(i).v)[0])/2,
                    (verXY.get(edges.get(i).u)[1]+verXY.get(edges.get(i).v)[1])/2,edges.get(i).weight+"");
            center.getChildren().addAll(edgesLines[i],WeightText[i]);
        }
    }

    //提示界面
    public void tipsPage(Stage primaryStage){
        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            pane1 temp = new pane1();
            temp.vertices = new ArrayList<>(vertices);
            temp.edges = new ArrayList<>(edges);
            temp.verXY = new ArrayList<>(verXY);
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
                {0, 1,56}, {0, 3,99}, {0, 5,788},
                {1, 0,56}, {1, 2,455}, {1, 3,76},
                {2, 1,455}, {2, 3,677}, {2, 4,43}, {2, 10,99},
                {3, 0,99}, {3, 1,76}, {3, 2,677}, {3, 4,87}, {3, 5,654},
                {4, 2,43}, {4, 3,87}, {4, 5,987}, {4, 7,54}, {4, 8,32}, {4, 10,31},
                {5, 0,788}, {5, 3,654}, {5, 4,987}, {5, 6,45}, {5, 7,79},
                {6, 5,45}, {6, 7,77},
                {7, 4,54}, {7, 5,79}, {7, 6,77}, {7, 8,94},
                {8, 4,32}, {8, 7,94}, {8, 9,324}, {8, 10,567}, {8, 11,33},
                {9, 8,324}, {9, 11,11},
                {10, 2,99}, {10, 4,31}, {10, 8,567}, {10, 11,200},
                {11, 8,33}, {11, 9,11}, {11, 10,200}};

        for(int i=0;i<StrTemp.length;i++)
            vertices.add(StrTemp[i]);

        for(int i=0;i<XYTemp.length;i++)
            verXY.add(XYTemp[i]);

        for(int i=0;i<edgesTemp.length;i++)
            edges.add(new WeightedEdge(edgesTemp[i][0],edgesTemp[i][1],edgesTemp[i][2]));

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
    public static void main(String[] args) {
        Application.launch(args);
    }
}

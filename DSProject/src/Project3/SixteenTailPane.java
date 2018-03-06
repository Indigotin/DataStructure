package Project3;

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
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class SixteenTailPane extends Application{

    private TextField input = new TextField();
    private Pane pane = new Pane();
    private Text tips = new Text();

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        Button Solve = new Button("Solve");
        Button StartOver = new Button("StartOver");
        HBox foot = new HBox(Solve,StartOver);
        foot.setAlignment(Pos.CENTER);
        foot.setSpacing(10);
        VBox bottom = new VBox(input,foot);
        bottom.setSpacing(10);
        borderPane.setBottom(bottom);
        borderPane.setCenter(pane);
        Solve.setOnAction(e->{
            Solve(primaryStage);
        });
        StartOver.setOnAction(E->{
            input.setText("");
            pane.getChildren().removeAll(pane.getChildren());
        });
        primaryStage.setScene(new Scene(borderPane,1100,200));
        primaryStage.setTitle("SixteenTail");
        primaryStage.show();
    }
    private void Solve(Stage primaryStage){
        String str = input.getText();
        if(!checkInputStr(str)){
            tipsPage(primaryStage);
        }
        else{
            char[] initialNode = str.toCharArray();
            SixteenTailModel model = new SixteenTailModel();
            java.util.List<Integer> path = model.getShortestPath(SixteenTailModel.getIndex(initialNode));

            for (int i = 0; i < path.size(); i++){

                char[] node = SixteenTailModel.getNode(path.get(i).intValue());
                char[] preNode = null;

                if(i>0)
                    preNode = SixteenTailModel.getNode(path.get(i-1).intValue());

                Text[] texts = new Text[16];
                int Widgt = 20;
                int Height = 20;
                int GAP = 120*i;
                int Long = 100;
                for(int j=0;j<node.length;j++){

                    texts[j] = new Text();
                    texts[j].setText(String.valueOf(node[j]));
                    texts[j].setX(25+Widgt*(j%4)+GAP);
                    texts[j].setY(33+Height*(j/4));
                    if(j % 4 == 3){
                        texts[j].setY(texts[j].getY());
                    }
                    if(preNode != null && preNode[j] != node[j]){
                        texts[j].setFill(Color.RED);
                    }
                    pane.getChildren().add(texts[j]);
                }

                Line[] lines1 = new Line[5];
                Line[] lines2 = new Line[5];
                for(int j=0;j<5;j++){
                    lines1[j] = new Line(20+GAP,20*(j+1),Long+GAP,20*(j+1));
                    lines2[j] = new Line(20*(j+1)+GAP,20,20*(j+1)+GAP,Long);
                    pane.getChildren().addAll(lines1[j],lines2[j]);
                }
            }
        }

    }
    private boolean checkInputStr(String str){

        if(str.length() != 16){
            tips.setText("请用输入有效字符！");
            return false;
        }

        String newStr = str.toUpperCase();
        for(int i=0;i<newStr.length();i++) {
            System.out.println(newStr.charAt(i));


            if (newStr.charAt(i) != 'H' && newStr.charAt(i) != 'T') {
                tips.setText("请用‘H’/‘T’表示硬币的正反面！");
                return false;
            }
        }
        return true;
    }
    //提示界面
    public void tipsPage(Stage primaryStage){

        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            SixteenTailPane temp =new SixteenTailPane();
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
    public static void main(String[] args){
        Application.launch(args);
    }
}

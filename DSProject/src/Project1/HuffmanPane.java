package Project1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class HuffmanPane extends Application {
    private Text tips = new Text();
    private Pane pane = new Pane();
    private BorderPane borderPane = new BorderPane();
    private double vGap = 50; // Gap between two levels in a tree
    @Override
    public void start(Stage primaryStage) {

        HuffmanCoding huffmanCoding = new HuffmanCoding();
        //Top
        Text topText = new Text("Enter a text:");
        TextField textField1 = new TextField();
        Button btOK = new Button("Show Huffman Tree");
        HBox Top = new HBox();
        Top.getChildren().addAll(topText, textField1, btOK);
        Top.setAlignment(Pos.CENTER);
        Top.setSpacing(10);
        borderPane.setTop(Top);
        btOK.setOnAction(e -> {
            pane.getChildren().removeAll(pane.getChildren());
            String str = textField1.getText();
            try {
                if(!judge(str)){
                    tips.setText("请至少输入含有两种字符！");
                    tipsPage(primaryStage);
                }
                else{
                    if(str.length()==0){
                        tips.setText("请不要提交空内容！");
                        tipsPage(primaryStage);
                    }
                    else {
                        huffmanCoding.Initialization(str);
                        TreeNode root = huffmanCoding.getHuffmanTreeRoot();
                        TreePrinting(root);

                        //新开弹窗
                        new tipsWindows().display("Coding",huffmanCoding.Coding());
                    }
                }

            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        //Center
        HBox hBox = new HBox(pane);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(hBox);

        //Bottom
        Text bottomText = new Text("Enter a bit str:");
        TextField textField2 = new TextField();
        Button Decode = new Button("Decode Text");
        HBox Bottom = new HBox();
        Bottom.getChildren().addAll(bottomText, textField2, Decode);
        Bottom.setAlignment(Pos.CENTER);
        Bottom.setSpacing(10);
        borderPane.setBottom(Bottom);

        //弹窗test
        Decode.setOnAction(e->{
            String bin = textField2.getText();
            try {
                if(bin.length()==0){
                    tips.setText("请不要提交空内容！");
                    tipsPage(primaryStage);
                }
                else
                    new tipsWindows().display("Decoding",huffmanCoding.Decoding(bin));
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ClassNotFoundException e1) {
                e1.printStackTrace();
            }
        });
        borderPane.setMinHeight(400);
        borderPane.setMinWidth(800);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("Huffman");
        primaryStage.show();
    }

    private boolean judge(String str){
        for(int i=0;i+1<str.length();i++){
            if(str.charAt(i) != str.charAt(i+1)){
                return true;
            }
        }
        return false;
    }
    public void TreePrinting(TreeNode root) {

        if (root != null) {
            TreePrinting(root, borderPane.getWidth()/2,vGap,borderPane.getHeight()/2);
        }
    }

    private void TreePrinting(TreeNode root,
                             double x, double y, double hGap) {
        if (root.left != null) {
            pane.getChildren().add(new Line(x - hGap, y + vGap, x, y));
            TreePrinting(root.left, x - hGap, y + vGap, hGap / 2);
        }

        if (root.right != null) {
            pane.getChildren().add(new Line(x + hGap, y + vGap, x, y));
            TreePrinting(root.right, x + hGap, y + vGap, hGap / 2);
        }

        //显示节点
        Circle circle = new Circle(x, y, 15);
        circle.setFill(Color.YELLOW);
        circle.setStroke(Color.BLACK);
        pane.getChildren().addAll(circle,new Text(x - 4, y + 4, root.weight + ""));

    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    //提示界面
    public void tipsPage(Stage primaryStage){

        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            HuffmanPane temp =new HuffmanPane();
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

    //javafx弹窗
    static class tipsWindows {

        public void display(String title , String message){
            Stage window = new Stage();
            window.setTitle(title);
            //modality要使用Modality.APPLICATION_MODEL
            window.initModality(Modality.APPLICATION_MODAL);
            window.setMinWidth(300);
            window.setMinHeight(150);
            Button button = new Button("确定");
            button.setOnAction(e -> window.close());
            Text text = new Text(message);
            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(text , button);
            vBox.setAlignment(Pos.CENTER);

            Scene scene = new Scene(vBox);
            window.setScene(scene);
            //使用showAndWait()先处理这个窗口，而如果不处理，main中的那个窗口不能响应
            window.showAndWait();
        }
    }
}

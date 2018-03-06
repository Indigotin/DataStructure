package Project4.MyLinkedList;

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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyLinkedListPane extends Application{

    private MyLinkedList list = new MyLinkedList();
    private Text top = new Text();
    private Pane center = new Pane();
    private TextField valueField = new TextField();
    private TextField indexField = new TextField();
    private Text tips = new Text();
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(top);
        borderPane.setCenter(center);
        list.addFirst(5);list.addFirst("5");list.addFirst("5");
        showCenter(list,-1);

        Text EnterValue = new Text("Enter a value");
        Text EnterIndex = new Text("Enter an index");
        Button Search = new Button("Search");
        Button Insert = new Button("Insert");
        Button Delete = new Button("Delete");
        HBox bottom = new HBox(EnterValue,valueField,EnterIndex,indexField,Search,Insert,Delete);
        bottom.setSpacing(5);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(bottom);
        Search.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            Search(primaryStage);
        });
        Insert.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            Insert(primaryStage);
        });
        Delete.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            Delete(primaryStage);
        });
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("MyLinkedList");
        primaryStage.show();
    }

    private void Search(Stage primaryStage){

        try {
            String value = valueField.getText();
            String indexStr = indexField.getText();

            if(value.length() != 0){
                int index = list.indexOf(value);
                if(index != -1){
                    showCenter(list,index);
                }
                else{
                    tips.setText("您输入的数据不存在！");
                    tipsPage(primaryStage);
                }
            }
            else {
                int index = Integer.parseInt(indexStr);
                if(index>=0 && index<list.size())
                    showCenter(list,index);
                else
                    throw new NumberFormatException();
            }
        }
        catch(NumberFormatException ex){
            tips.setText("请输入有效的下标！");
            tipsPage(primaryStage);
        }
    }
    private void Insert(Stage primaryStage){
        try {
            String value = valueField.getText();
            String indexStr = indexField.getText();

            if(value.length() != 0){
                list.addLast(value);
            }
            else {
                int index = Integer.parseInt(indexStr);
                if(index<=0)
                    list.addFirst(value);
                else if(index>=list.size())
                    list.addLast(value);
                else
                    list.set(index,value);
            }
            showCenter(list,-1);
        }
        catch(NumberFormatException ex){
            tips.setText("请输入有效的下标！");
            tipsPage(primaryStage);
        }
    }
    private void Delete(Stage primaryStage){
        try {
            String value = valueField.getText();
            String indexStr = indexField.getText();

            if(value.length() != 0){
                if(list.remove(value)){
                    showCenter(list,-1);
                }
                else{
                    tips.setText("请输入有效的数据！");
                    tipsPage(primaryStage);
                }
            }
            else if(indexStr.length() != 0){
                int index = Integer.parseInt(indexStr);
                if(index>=0 && index<list.size()){
                    list.remove(index);
                    showCenter(list,-1);
                }
                else
                    throw new NumberFormatException();
            }
        }
        catch(NumberFormatException ex){
            tips.setText("请输入有效的下标！");
            tipsPage(primaryStage);
        }
    }

    private void showCenter(MyLinkedList list, int SearchIndex){

        int Width = 40;
        int Height = 30;
        int Gap = 50;

        Rectangle[] rectangles = new Rectangle[list.size()];
        Line[] recLine = new Line[list.size()];
        Line[] lines = new Line[list.size()];
        Line[] lines1 = new Line[list.size()];
        Line[] lines2 = new Line[list.size()];
        Text[] texts = new Text[list.size()];

        for(int i=0;i<list.size();i++){
            rectangles[i] = new Rectangle(Gap+i*(Width+Gap),100,Width,Height);
            rectangles[i].setFill(Color.TRANSPARENT);
            rectangles[i].setStroke(Color.BLACK);
            recLine[i] = new Line(Gap+i*(Width+Gap)+Width/2,100,Gap+i*(Width+Gap)+Width/2,100+Height);
            center.getChildren().addAll(rectangles[i],recLine[i]);
            if(list.get(i) != null){
                texts[i] = new Text(list.get(i)+"");
                if(SearchIndex != -1 && SearchIndex == i)
                    texts[i].setFill(Color.RED);
                texts[i].setX(Gap+i*(Width+Gap)+Width/4);
                texts[i].setY(100+Height/2);
                center.getChildren().add(texts[i]);
            }
            //连接每个矩阵的箭头
            if(i != list.size()-1){
                lines[i] = new Line(Gap+i*Width+Gap*i+Width,100+Height/2,Gap+i*Width+Gap*i+Width+Gap,100+Height/2);
                lines1[i] = new Line(Gap+i*Width+Gap*i+Width+Gap,100+Height/2,Gap+i*Width+Gap*i+Width+Gap-5,100+Height/2-5);
                lines2[i] = new Line(Gap+i*Width+Gap*i+Width+Gap,100+Height/2,Gap+i*Width+Gap*i+Width+Gap-5,100+Height/2+5);
                center.getChildren().addAll(lines[i],lines1[i],lines2[i]);
            }
            //head箭头
            if(i == 0){
                Text head = new Text("head");
                head.setX(Gap+i*(Width+Gap)+Width/4-5);
                head.setY(40);
                Line headLine = new Line(Gap+i*(Width+Gap)+Width/4,40,Gap+i*(Width+Gap)+Width/4,100);
                Line headLine1 = new Line(Gap+i*(Width+Gap)+Width/4,100,Gap+i*(Width+Gap)+Width/4-5,95);
                Line headLine2 = new Line(Gap+i*(Width+Gap)+Width/4,100,Gap+i*(Width+Gap)+Width/4+5,95);
                center.getChildren().addAll(head,headLine,headLine1,headLine2);
            }
            //tail箭头
            if(i == list.size()-1){
                Text tail = new Text("tail");
                tail.setX(Gap+i*(Width+Gap)+Width/4+5);
                tail.setY(40);
                Line tailLine = new Line(Gap+i*(Width+Gap)+Width/4+10,40,Gap+i*(Width+Gap)+Width/4,100);
                Line tailLine1 = new Line(Gap+i*(Width+Gap)+Width/4,100,Gap+i*(Width+Gap)+Width/4-5,95);
                Line tailLine2 = new Line(Gap+i*(Width+Gap)+Width/4,100,Gap+i*(Width+Gap)+Width/4+5,95);
                center.getChildren().addAll(tail,tailLine,tailLine1,tailLine2);
            }
        }
    }


    //提示界面
    public void tipsPage(Stage primaryStage){
        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            MyLinkedListPane temp = new MyLinkedListPane();
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

package Project4.MyArrayList;

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

public class MyArrayListPane extends Application{

    private MyArrayList list = new MyArrayList();
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
        //初始化面板
        showCenter(list,-1);
        Text EnterValue = new Text("Enter a value");
        Text EnterIndex = new Text("Enter an index");
        Button Search = new Button("Search");
        Button Insert = new Button("Insert");
        Button Delete = new Button("Delete");
        Button TrimToSize = new Button("TrimToSize");
        HBox bottom = new HBox(EnterValue,valueField,EnterIndex,indexField,Search,Insert,Delete,TrimToSize);
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

        TrimToSize.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            list.trimToSize();
            showCenter(list,-1);
        });

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("MyArrayList");
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
                list.add(value);
            }
            else {
                int index = Integer.parseInt(indexStr);
                if(index>=0 && index<list.getCapacity())
                    list.set(index,value);
                else
                    throw new NumberFormatException();
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

    private void showCenter(MyArrayList list,int SearchIndex){

        int Width = 40;
        int Height = 30;
        int Gap = 40;
        Rectangle[] rectangles = new Rectangle[list.getCapacity()];
        Line[] lines = new Line[list.getCapacity()];
        Text[] texts = new Text[list.getCapacity()];
        for(int i=0;i<list.getCapacity();i++){
            rectangles[i] = new Rectangle(Gap+i*Width,20,Width,Height);
            rectangles[i].setFill(Color.TRANSPARENT);
            rectangles[i].setStroke(Color.BLACK);
            center.getChildren().add(rectangles[i]);
            if(list.get(i) == null) {
                lines[i] = new Line(Gap+i*Width,20,Gap+i*Width+Width,20+Height);
                center.getChildren().add(lines[i]);
            }
            else{
                texts[i] = new Text(list.get(i)+"");
                if(SearchIndex != -1 && i == SearchIndex){
                    texts[i].setFill(Color.RED);
                }

                texts[i].setX(Gap+i*Width+Width/2);
                texts[i].setY(20+Height/2);
                center.getChildren().add(texts[i]);
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
            MyArrayListPane temp = new MyArrayListPane();
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

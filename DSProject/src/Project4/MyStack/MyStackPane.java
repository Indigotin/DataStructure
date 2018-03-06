package Project4.MyStack;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class MyStackPane extends Application {

    private MyStack stack = new MyStack();
    private TextField valueField = new TextField();
    private Pane center = new Pane();

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(center);
        Text EnterValue = new Text("Enter a value");
        Button Push = new Button("Push");
        Button Pop = new Button("Pop");
        HBox bottom = new HBox(EnterValue,valueField,Push,Pop);
        bottom.setSpacing(5);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(bottom);

        stack.push("5");
        stack.push("4");
        stack.push("3");
        stack.push("2");
        stack.push("1");
        showCenter(stack);
        Push.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            stack.push(valueField.getText());
            showCenter(stack);
        });

        Pop.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            stack.pop();
            showCenter(stack);
        });
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("MyStack");
        primaryStage.show();
    }

    private void showCenter(MyStack stack){
        int Width = 40;
        int Height = 30;
        int Base = 500;
        ArrayList temp = stack.getList();
        Rectangle[] rectangles = new Rectangle[stack.getSize()];
        Text[] texts = new Text[stack.getSize()];
        for(int i=0;i<stack.getSize();i++){
            rectangles[i] = new Rectangle(200,Base-Height*i,Width,Height);
            rectangles[i].setFill(Color.TRANSPARENT);
            rectangles[i].setStroke(Color.BLACK);
            texts[i] = new Text(temp.get(i)+"");
            texts[i].setX(200+Width/2);
            texts[i].setY(Base-Height*i+Height/2);
            center.getChildren().addAll(rectangles[i],texts[i]);
            if(i == stack.getSize()-1){
                Text Top = new Text("Top");
                Top.setX(200+Width/2-100);
                Top.setY(Base-Height*i+Height/2);
                Line TopLine = new Line(200+Width/2-80,Base-Height*i+Height/2,200+Width/2,Base-Height*i+Height/2);
                Line TopLine1 = new Line(200+Width/2,Base-Height*i+Height/2,200+Width/2-5,Base-Height*i+Height/2-5);
                Line TopLine2 = new Line(200+Width/2,Base-Height*i+Height/2,200+Width/2-5,Base-Height*i+Height/2+5);
                center.getChildren().addAll(Top,TopLine,TopLine1,TopLine2);
            }
        }
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

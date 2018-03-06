package Project4.MyQueue;

import Project4.MyLinkedList.MyLinkedList;
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

public class MyQueuePane extends Application {

    private MyQueue queue = new MyQueue();
    private TextField valueField = new TextField();
    private Pane center = new Pane();
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(center);
        Text EnterValue = new Text("Enter a value");
        Button Enqueue = new Button("Enqueue");
        Button Dequeue = new Button("Dequeue");
        HBox bottom = new HBox(EnterValue,valueField,Enqueue,Dequeue);
        bottom.setSpacing(5);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        borderPane.setBottom(bottom);

        queue.enqueue("5");
        queue.enqueue("4");
        queue.enqueue("3");
        queue.enqueue("2");
        queue.enqueue("1");
        showCenter(queue);

        Enqueue.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            queue.enqueue(valueField.getText());
            showCenter(queue);
        });
        Dequeue.setOnAction(e->{
            center.getChildren().removeAll(center.getChildren());
            queue.dequeue();
            showCenter(queue);
        });

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("MyQueue");
        primaryStage.show();
    }

    private void showCenter(MyQueue queue){

        int Width = 40;
        int Height = 30;
        int Gap = 60;
        Rectangle[] rectangles = new Rectangle[queue.getSize()];
        Text[] texts = new Text[queue.getSize()];
        MyLinkedList temp = queue.getList();
        for(int i=0;i<queue.getSize();i++){

            rectangles[i] = new Rectangle(Gap+i*Width,100,Width,Height);
            rectangles[i].setFill(Color.TRANSPARENT);
            rectangles[i].setStroke(Color.BLACK);

            texts[i] = new Text(temp.get(i)+"");
            texts[i].setX(Gap+i*Width+Width/2);
            texts[i].setY(100+Height/2);
            center.getChildren().addAll(rectangles[i],texts[i]);
            //head箭头
            if(i == 0){
                Text head = new Text("head");
                head.setX(Gap+i*(Width+Gap)+Width/4-5);
                head.setY(40);
                Line headLine = new Line(Gap+i*Width+Width/2,40,Gap+i*Width+Width/2,100);
                Line headLine1 = new Line(Gap+i*Width+Width/2,100,Gap+i*Width+Width/2-5,95);
                Line headLine2 = new Line(Gap+i*Width+Width/2,100,Gap+i*Width+Width/2+5,95);
                center.getChildren().addAll(head,headLine,headLine1,headLine2);
            }
            //tail箭头
            if(i == queue.getSize()-1){
                Text tail = new Text("tail");
                tail.setX(Gap+i*Width+Width/2+5);
                tail.setY(40);
                Line tailLine = new Line(Gap+i*Width+Width/2+10,40,Gap+i*Width+Width/2,100);
                Line tailLine1 = new Line(Gap+i*Width+Width/2,100,Gap+i*Width+Width/2-5,95);
                Line tailLine2 = new Line(Gap+i*Width+Width/2,100,Gap+i*Width+Width/2+5,95);
                center.getChildren().addAll(tail,tailLine,tailLine1,tailLine2);
            }
        }

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}

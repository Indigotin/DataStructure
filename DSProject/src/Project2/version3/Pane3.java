package Project2.version3;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;


public class Pane3 extends Application{

    static Text tips = new Text("您的输入有误");
    int[] num = new int[4];
    List<String> result;
    int count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        //Top
        StackPane stackPane = new StackPane();
        Text text = new Text("Input Four Numbers Between 1 To 13:");
        stackPane.getChildren().add(text);
        borderPane.setTop(stackPane);
        //Center  Bottom
        setCenterBottom(borderPane,primaryStage);

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("PointGameOf24");
        primaryStage.show();
    }

    private void setCenterBottom(BorderPane borderPane,Stage primaryStage){

        //Center
        HBox center = new HBox();
        TextArea[] textArea = new TextArea[4];
        for(int i=0;i<4;i++){
            textArea[i] = new TextArea();
            textArea[i].setPrefColumnCount(4);
            textArea[i].setPrefRowCount(4);
            center.getChildren().add(textArea[i]);
        }
        center.setSpacing(12);
        center.setAlignment(Pos.CENTER);
        borderPane.setCenter(center);
        //Bottom
        HBox bottom = new HBox();
        TextField textField = new TextField();
        Button Find = new Button("Find a Solution");
        bottom.getChildren().addAll(textField,Find);
        bottom.setSpacing(5);
        bottom.setAlignment(Pos.CENTER);
        borderPane.setBottom(bottom);
        //Find a Solution
        Find.setOnAction(e->{

            try{
                for(int i=0;i<4;i++){
                    num[i] = (Integer.parseInt(textArea[i].getText()));
                }
            }catch (NumberFormatException ex){
                tips.setText("请输入正确的数字！");
                tipsPage(primaryStage);
            }

            //判断数字是否在1-13之间
            for(int o:num){
                if(o <1 || o > 13){
                    tips.setText("请输入1-13的数字！");
                    tipsPage(primaryStage);
                }
            }

            PointGameOf24 judge = new PointGameOf24();
            result = judge.getResult(num);
            if(result.size() == 0){
                textField.setText("No Solution!");
            }
            else{
                textField.setText(result.get(count++));
                if(count >= result.size()){
                    count = 0;
                }
            }
        });
    }

    //提示界面
    public static void tipsPage(Stage primaryStage){

        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            Pane3 temp =new Pane3();
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

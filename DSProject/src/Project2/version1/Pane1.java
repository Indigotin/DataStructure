package Project2.version1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Pane1 extends Application{

    static Text tips = new Text("您的输入有误");
    TextField textField = new TextField();
    List<Integer> num;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        //Top
        StackPane stackPane = new StackPane();
        Button Refresh = new Button("Refresh");
        stackPane.getChildren().add(Refresh);
        borderPane.setTop(stackPane);
        //Center
        HBox center = new HBox();
        num = getCards(center);
        borderPane.setCenter(center);
        //Bottom
        HBox bottom = new HBox();
        Text text = new Text("Enter an expression:");
        Button Verity = new Button("Verity");
        bottom.getChildren().addAll(text,textField,Verity);
        bottom.setSpacing(5);
        bottom.setAlignment(Pos.CENTER);
        borderPane.setBottom(bottom);
        //Refresh界面
        Refresh.setOnAction(e->{
            RefreshStage(primaryStage);
        });

        //判断用户提交的表达式
        Verity.setOnAction(e->{
            judgeExpression(primaryStage);
        });

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("PointGameOf24");
        primaryStage.show();
    }

    //刷新Stage
    private void RefreshStage(Stage primaryStage){
        Pane1 temp =new Pane1();
        try {
            temp.start(primaryStage);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    public void judgeExpression(Stage primaryStage){

        String expression = textField.getText();

        if(expression.length() != 0){

            if(EvaluateExpression.checkExpression(expression)){

                if(isNumCorrect(num,expression)){

                    if(EvaluateExpression.evaluateExpression(expression,false) == 24){

                        tips.setText("corrent");
                    }
                    else{
                        tips.setText("Incorrent！");
                    }
                }
                else{
                    tips.setText("The numbers in the expression don't match the numbers in the set!");
                }

            }else{
                tips.setText("Please enter a legitimate expression!");
            }
        }
        else{
            tips.setText("Please enter a expression！");
        }
        tipsPage(primaryStage);
    }

    //获取四张牌
    private List<Integer> getCards(HBox hBox){
        List<Integer> num = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        for(int i=0;i<4;i++){
            int index = (int)(Math.random()*52+1);
            //不能出相同的牌
            if(temp.contains(index)){
                i--;
                continue;
            }
            temp.add(index);
            num.add(index % 13 == 0 ? 13: index % 13);
            ImageView imageView = new ImageView(new Image("Project2/cards/" +index+".png"));
            hBox.getChildren().add(imageView);
        }
        hBox.setSpacing(5);
        hBox.setAlignment(Pos.CENTER);
        return num;
    }

    //判断用户输入的数字是不是随机生成的数字
    public boolean isNumCorrect(List<Integer> list,String expression){

        StringTokenizer tokens = new StringTokenizer(expression, "()+-/*", true);
        while (tokens.hasMoreTokens()){
            String token = tokens.nextToken().trim();
            if (token.length() == 0)
                continue;
            else if(Character.isDigit(token.charAt(0))){
                for(int i=0;i<list.size();i++){
                    if(list.get(i) == Integer.parseInt(token)){
                        list.remove(i);
                        break;
                    }
                }
            }
        }
        if(list.size() != 0){
            return false;
        }
        return true;
    }

    //提示界面
    public static void tipsPage(Stage primaryStage){

        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            Pane1 temp =new Pane1();
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

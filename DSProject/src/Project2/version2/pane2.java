package Project2.version2;

import Project2.version1.EvaluateExpression;
import Project2.version3.PointGameOf24;
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

public class pane2 extends Application{
    static Text tips = new Text("您的输入有误");
    TextField textField = new TextField();
    int[] num = new int[4];
    List<Integer> numTemp;
    List<String> result;
    int count = 0;

    @Override
    public void start(Stage primaryStage) throws Exception {

        BorderPane borderPane = new BorderPane();
        //Top
        StackPane stackPane = new StackPane();
        HBox top = new HBox();
        TextField textField1 = new TextField();
        Button Find = new Button("Find a Solution");
        Button Refresh = new Button("Refresh");
        top.getChildren().addAll(Find,textField1,Refresh);
        top.setAlignment(Pos.CENTER);
        stackPane.getChildren().add(top);
        borderPane.setTop(stackPane);

        //Center
        HBox center = new HBox();
        numTemp = getCards(center);
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
        //Find a Solution
        Find.setOnAction(e->{

            try{
                for(int i=0;i<4;i++){
                    num[i] = numTemp.get(i);
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
                textField1.setText("No Solution!");
            }
            else{
                textField1.setText(result.get(count++));
                if(count >= result.size()){
                    count = 0;
                }
            }
        });

        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("PointGameOf24");
        primaryStage.show();
    }

    //刷新Stage
    private void RefreshStage(Stage primaryStage){
        pane2 temp =new pane2();
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

                if(isNumCorrect(numTemp,expression)){

                    if(EvaluateExpression.evaluateExpression(expression,false) == 24){

                        tips.setText("corrent");
                    }
                    else{
                        tips.setText("Incorrent result！");
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
            pane2 temp =new pane2();
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

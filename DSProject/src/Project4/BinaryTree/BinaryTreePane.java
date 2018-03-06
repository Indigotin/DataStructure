package Project4.BinaryTree;

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

//<E extends Comparable>
public class BinaryTreePane extends Application {

    private BinaryTree<Integer> binaryTree = new BinaryTree<>();
    private BorderPane borderPane = new BorderPane();
    private Pane pane = new Pane();
    private TextField keyField = new TextField();
    private double vGap = 50; // Gap between two levels in a tree
    private Text tips = new Text();

    @Override
    public void start(Stage primaryStage){

        Text EnterKey = new Text("Enter a key");
        Button Search = new Button("Search");
        Button Insert = new Button("Insert");
        Button Remove = new Button("Remove");
        HBox bottom = new HBox(EnterKey,keyField,Search,Insert,Remove);
        bottom.setAlignment(Pos.BOTTOM_RIGHT);
        bottom.setSpacing(5);

        //Center
        HBox hBox = new HBox(pane);
        hBox.setAlignment(Pos.CENTER);
        borderPane.setCenter(hBox);
        borderPane.setBottom(bottom);

        Search.setOnAction(e->{
            try{
                if(keyField.getText().length() == 0)
                    throw new NumberFormatException();
                pane.getChildren().removeAll(pane.getChildren());
                if(binaryTree.isExist(Integer.parseInt(keyField.getText())))
                    TreePrinting(binaryTree.getRoot(),Integer.parseInt(keyField.getText()));
                else {
                    tips.setText("您输入的数据不存在！");
                    tipsPage(primaryStage);
                }
            }
            catch (NumberFormatException ex){
                tips.setText("请输入有效数据！");
                tipsPage(primaryStage);
            }
        });

        Insert.setOnAction(e->{
            try{
                if(keyField.getText().length() == 0)
                    throw new NumberFormatException();
                pane.getChildren().removeAll(pane.getChildren());
                binaryTree.insert(Integer.parseInt(keyField.getText()));
                TreePrinting(binaryTree.getRoot(),null);
            } catch (NumberFormatException ex){
                tips.setText("请输入有效数据！");
                tipsPage(primaryStage);
            }
        });

        Remove.setOnAction(e->{
            try{
                if(keyField.getText().length() == 0)
                    throw new NumberFormatException();
                pane.getChildren().removeAll(pane.getChildren());
                if(binaryTree.delete(Integer.parseInt(keyField.getText())))
                    TreePrinting(binaryTree.getRoot(),null);
                else{
                    tips.setText("您输入的数据不存在！");
                    tipsPage(primaryStage);
                }
            }catch (NumberFormatException ex){
                tips.setText("请输入有效数据！");
                tipsPage(primaryStage);
            }
        });

        borderPane.setMinHeight(400);
        borderPane.setMinWidth(800);
        primaryStage.setScene(new Scene(borderPane));
        primaryStage.setTitle("BinaryTree");
        primaryStage.show();
    }

    public void TreePrinting(TreeNode root,Integer object) {
        if (root != null) {
            TreePrinting(root,borderPane.getWidth()/2,vGap,borderPane.getHeight()/2,object);
        }
    }

    private void TreePrinting(TreeNode root,double x, double y, double hGap,Integer object) {

        if (root.left != null) {
            pane.getChildren().add(new Line(x - hGap, y + vGap, x, y));
            TreePrinting(root.left, x - hGap, y + vGap, hGap / 2,object);
        }

        if (root.right != null) {
            pane.getChildren().add(new Line(x + hGap, y + vGap, x, y));
            TreePrinting(root.right, x + hGap, y + vGap, hGap / 2,object);
        }

        //显示节点
        Circle circle = new Circle(x, y, 15);
        circle.setFill(Color.YELLOW);
        circle.setStroke(Color.BLACK);
        Text text = new Text(root.data + "");
        if(object != null && root.data.equals(object)){
            text.setFill(Color.RED);
        }
        text.setX(x - 4);
        text.setY(y + 4);
        pane.getChildren().addAll(circle,text);
    }

    //提示界面
    public void tipsPage(Stage primaryStage){
        Button button = new Button("确定");
        VBox vBox = new VBox();
        vBox.getChildren().addAll(tips,button);
        vBox.setAlignment(Pos.CENTER);

        button.setOnAction(e->{
            BinaryTreePane temp = new BinaryTreePane();
            temp.binaryTree = binaryTree;
            try {
                temp.start(primaryStage);
                if(binaryTree.getRoot().data!=null)
                    temp.TreePrinting(temp.binaryTree.getRoot(),null);
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

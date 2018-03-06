package Project4.BinaryTree;

/**
 * Created by 77527 on 2017/8/8.
 */
public class TreeNode<E> {
    TreeNode<E> left;
    E data;
    TreeNode<E> right;
    public TreeNode(){
        left = null;
        data = null;
        right = null;
    }
    public TreeNode(E data){
        left = null;
        this.data = data;
        right = null;
    }
    public TreeNode(TreeNode<E> left,E data,TreeNode<E> right){
        this.left = left;
        this.data = data;
        this.right = right;
    }
}

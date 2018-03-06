package Project1;

import java.io.Serializable;

public class TreeNode<E> implements Comparable,Serializable{

    TreeNode<E> parent;
    E data;
    String coding;
    int weight;
    TreeNode<E> left;
    TreeNode<E> right;
    public TreeNode(){
        parent = null;
        left = null;
        data = null;
        right = null;
        weight = 0;
    }
    public TreeNode(int weight){
        parent = null;
        left = null;
        data = null;
        right = null;
        this.weight = weight;
    }
    public TreeNode(int weight,TreeNode left,TreeNode right){
        parent = null;
        this.left = left;
        data = null;
        this.right = right;
        this.weight = weight;
    }
    public TreeNode(E data,int weight){
        left = null;
        this.data = data;
        this.weight = weight;
        right = null;
        parent = null;
    }
    public TreeNode(TreeNode<E> left,E data,TreeNode<E> right){
        this.left = left;
        this.data = data;
        this.right = right;
    }

    @Override
    public int compareTo(Object o) {
        TreeNode temp = (TreeNode)o;
        if(this.weight > temp.weight) {
            return 1;
        }else if(this.weight < temp.weight){
            return -1;
        }
        return 0;
    }
}

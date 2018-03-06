package Project4.BinaryTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by 77527 on 2017/8/8.
 */
public class BinaryTree<E extends Comparable> {

    private TreeNode<E> root = new TreeNode<>();
    private boolean flag = false;  //元素是否存在
    private int count = 0;
    public BinaryTree(){}
    public BinaryTree(E[] list){
        for(int i=0;i<list.length;i++){
            insert(list[i]);
        }
    }

    public void insert(E data){
        //如果是空树
        if(root.data == null){
            root.data = data;
            System.out.print(root.data+" ");
            return;
        }

        TreeNode<E> current;
        TreeNode<E> parent;
        parent = current = root;

        while(current != null){
            //为了得到该插入点的父结点
            parent = current;
            if(data.compareTo(current.data) < 0)
                current = current.left;
            else
                current = current.right;
        }

        TreeNode<E> newNode = new TreeNode<>(data);
        if(data.compareTo(parent.data) < 0)
            parent.left = newNode;
        else
            parent.right = newNode;
        System.out.print(newNode.data+" ");
    }

    //先序遍历
    public void preorder(){
        preorder(root);
    }
    public void preorder(TreeNode<E> temp){
        if(temp != null){
            System.out.print(temp.data+" ");
            preorder(temp.left);
            preorder(temp.right);
        }
    }

    //中序遍历
    public void inorder(){
        inorder(root);
    }
    public void inorder(TreeNode<E> temp){
        if(temp != null){
            inorder(temp.left);
            System.out.print(temp.data+" ");
            inorder(temp.right);
        }
    }
    public void nonRecursiveInorder(){

        Stack<TreeNode<E>> stack = new Stack();
        List<TreeNode<E>> VisitedList = new ArrayList();

        if(root.data == null)
            return;

        stack.push(root);
        while (!stack.empty()){
            TreeNode<E> temp = stack.peek();
            if (temp.left != null && !VisitedList.contains(temp.left)){
                stack.push(temp.left);
            }else{
                stack.pop();
                VisitedList.add(temp);
                if(temp.right != null){
                    stack.push(temp.right);
                }
            }
        }
        for(int i=0;i<VisitedList.size();i++){
            System.out.print(VisitedList.get(i).data+" ");
        }
    }


    //后序遍历
    public void postorder(){
        postorder(root);
    }
    public void postorder(TreeNode<E> temp){
        if(temp != null){
            postorder(temp.left);
            postorder(temp.right);
            System.out.print(temp.data+" ");
        }
    }

    public boolean isExisttemp(E data,TreeNode<E> temp){
        if(temp != null)
        {
            if(data.compareTo(temp.data) == 0){
                flag = true;
            }
            isExisttemp(data,temp.left);
            isExisttemp(data,temp.right);
        }
        return flag;
    }
    public boolean isExist(E data){
        boolean F = isExisttemp(data,root);
        flag = false;//还原
        return F;
    }

    public boolean delete(E data){
        TreeNode<E> current;
        TreeNode<E> parent;
        parent = current = root;
        while(current != null){

            if(data.compareTo(current.data) < 0){
                parent = current;
                current = current.left;
            }
            else if(data.compareTo(current.data) == 0)
                break;

            else{
                parent = current;
                current = current.right;
            }
        }
        if(current == null){
            return false;
        }

        //应该删除的节点没有左孩子
        if(current.left == null){

            if(parent.left == current){//parent.left.data == current.data
                parent.left = current.right;
            }
            else{
                parent.right = current.right;
            }
            return true;
        }
        //有左孩子
        else{
            TreeNode<E> rightMost;
            TreeNode<E> parentOfRightMost;
            parentOfRightMost = current;
            rightMost = current.left;
            while(rightMost.right != null){
                parentOfRightMost = rightMost;
                rightMost = rightMost.right;
            }
            //在要删除结点的左子树中找到一个最小(最右的替换需要删除的结点
            current.data = rightMost.data;
            if(parentOfRightMost.right == rightMost){

                parentOfRightMost.right = rightMost.left;
            }
            else
                parentOfRightMost.left = rightMost.left;
            return true;
        }

    }
    public int size(){
        getSize(root);
        return count;
    }
    private void getSize(TreeNode<E> temp){
        if(temp != null){
            count++;
            getSize(temp.left);
            getSize(temp.right);
        }
    }
    public TreeNode getRoot(){
        return root;
    }
    public static void main(String[] args){
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.insert(56);
        tree.insert(30);
        tree.insert(78);
        tree.insert(99);
        tree.insert(76);
        tree.insert(8);
        System.out.println();
        tree.inorder();
    }
}


package Project1;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class HuffmanCoding {

    private File hfmtree = new File("hfmtree");
    private File codefile = new File("codefile");
    private File textfile = new File("textfile");
    private PriorityQueue<TreeNode> priorityQueue = new PriorityQueue<>();
    private TreeNode HuffmanTreeRoot;
    private List<TreeNode> list = new ArrayList<>();
    private String str;

    public void Initialization(String str) throws IOException {
        this.str = str;
        int j;
        //计算权值
        for(int i=0;i<str.length();i++) {
            for (j=0;j<list.size();j++) {

                if (list.get(j).data.equals(str.charAt(i))) {
                    list.get(j).weight++;
                    break;
                }
            }
            if (j == list.size()) {
                list.add(new TreeNode(str.charAt(i), 1));
            }
        }

        //根据权值构建优先队列
        for(int i=0;i<list.size();i++){
            priorityQueue.add(list.get(i));
            //System.out.print("data = "+list.get(i).data+" weight = "+list.get(i).weight+"  ");
        }
        //System.out.println();

        //构建哈夫曼树
        while(priorityQueue.size() != 1){
            TreeNode temp1 = priorityQueue.poll();
            TreeNode temp2 = priorityQueue.poll();
            priorityQueue.add(new TreeNode(temp1.weight+temp2.weight,temp1,temp2));
        }
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(hfmtree,false));
        HuffmanTreeRoot = priorityQueue.poll();
        //编码(每个字母对应的编码
        String coding = "";
        addCoding(HuffmanTreeRoot,coding);
        output.writeObject(HuffmanTreeRoot);
    }


    public void addCoding(TreeNode temp,String coding){
        if(temp != null){
            if(temp.data != null){
                temp.coding = coding;
            }
            addCoding(temp.left,coding+"0");
            addCoding(temp.right,coding+"1");
        }
    }

    public void toList(TreeNode temp) {
        if (temp != null) {
            list.add(temp);
            toList(temp.left);
            toList(temp.right);
        }
    }

    public TreeNode getHuffmanTreeRoot() throws IOException, ClassNotFoundException {

        if(HuffmanTreeRoot == null){
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(hfmtree));
            HuffmanTreeRoot = (TreeNode)input.readObject();
        }
        return HuffmanTreeRoot;
    }

    public String Coding() throws IOException, ClassNotFoundException {

        if(HuffmanTreeRoot == null){
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(hfmtree));
            HuffmanTreeRoot = (TreeNode)input.readObject();
        }

        list.clear();
        toList(HuffmanTreeRoot);
        /*for (int i=0;i<list.size();i++){
            System.out.println("data = "+list.get(i).data+" weight = "+list.get(i).weight+" coding = "+list.get(i).coding);
        }*/
        String result = "";
        DataOutputStream output = new DataOutputStream(new FileOutputStream(codefile));
        for(int i=0;i<str.length();i++){
            for(int j=0;j<list.size();j++){

                if(list.get(j).data != null){
                    if(list.get(j).data.equals(str.charAt(i))){
                        output.writeUTF(list.get(j).coding);
                        System.out.print(list.get(j).coding);
                        result += list.get(j).coding;
                        break;
                    }
                }
            }
        }
        System.out.println();
        return result;
    }

    public String Decoding(String binCoding) throws IOException, ClassNotFoundException {

        //输入判断
        if(!isRight(binCoding)){
            return "请输入二进制数字！!";
        }
        //读取文件
        /*String binCoding = new String();
        String temp;
        DataInputStream input = new DataInputStream(new FileInputStream(codefile));
        DataOutputStream output = new DataOutputStream(new FileOutputStream(textfile));
        temp = input.readUTF();
        try{
            while(temp != null){
                binCoding+=temp;
                temp = input.readUTF();
            }
        }catch (EOFException ex){
            //System.out.println(str);
        }*/
        DataOutputStream output = new DataOutputStream(new FileOutputStream(textfile));
        //利用建好的哈夫曼树
        list.clear();
        if(HuffmanTreeRoot == null){
            ObjectInputStream treeInput = new ObjectInputStream(new FileInputStream(hfmtree));
            HuffmanTreeRoot = (TreeNode)treeInput.readObject();
        }
        toList(HuffmanTreeRoot);
        /*for (int i=0;i<list.size();i++){
            System.out.println("data = "+list.get(i).data+" weight = "+list.get(i).weight+" coding = "+list.get(i).coding);
        }*/
        String result = "";
        //DeCoding
        while(binCoding.length() != 0){

            int i;
            for(i=0;i<list.size();i++){
                if(list.get(i).data != null){
                    if(binCoding.startsWith(list.get(i).coding)){
                        result += list.get(i).data;
                        System.out.print(list.get(i).data);
                        output.writeUTF(String.valueOf(list.get(i).data));
                        binCoding = binCoding.substring(list.get(i).coding.length());
                        break;
                    }
                }
            }
            if( i == list.size()){
                break;
            }
        }
        System.out.println();
        return result;
    }
    private boolean isRight(String binStr){
        for(int i=0;i<binStr.length();i++){
            if(binStr.charAt(i) != '0' && binStr.charAt(i) != '1')
                return false;
        }
        return true;
    }

}

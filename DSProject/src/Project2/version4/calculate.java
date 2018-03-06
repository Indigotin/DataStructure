package Project2.version4;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import Project2.version3.PointGameOf24;

public class calculate {

    public static void main(String[] args) throws IOException {

        //储存不同花色的牌
        int[] num = new int[53];
        //从1820种中无解的情况
        int[][] NoSolution = new int[459][4];

        //初始化牌面数字
        for (int i = 1; i <= 52; i++) {
            num[i] = i % 13 == 0 ? 13 : i % 13;
        }
        //不同花色
        List<List<Integer>> listOfDiffColor = new ArrayList<>();
        //相同花色
        List<List<Integer>> listOfSameColor = new ArrayList<>();
        //不同花色
        int[][] newListOfDiffColor = new int[1820][4];
        //相同花色
        int[][] newlistOfSameColor = new int[270725][4];
        //所有解方案
        List<String> result;
        //所有不花色重复的组合
        for (int i = 1; i <= 13; i++) {
            for (int j = i; j <= 13; j++) {
                for (int k = j; k <= 13; k++) {
                    for (int l = k; l <= 13; l++) {
                        List<Integer> temp = new ArrayList<>();
                        temp.add(i);
                        temp.add(j);
                        temp.add(k);
                        temp.add(l);
                        listOfDiffColor.add(temp);
                    }
                }
            }
        }

        //替换牌面数字
        for(int i=0;i<listOfDiffColor.size();i++){
            for(int j=0;j<listOfDiffColor.get(i).size();j++){
                int index = listOfDiffColor.get(i).get(j);
                listOfDiffColor.get(i).remove(j);
                listOfDiffColor.get(i).add(j,num[index]);
            }
            Collections.sort(listOfDiffColor.get(i));
        }

        //ToArray
        for(int i=0;i<listOfDiffColor.size();i++){
            for(int j=0;j<listOfDiffColor.get(i).size();j++){
                newListOfDiffColor[i][j] = listOfDiffColor.get(i).get(j);
            }
        }



        //输出到文件
        //ObjectOutputStream OutPut = new ObjectOutputStream(new FileOutputStream("NoSolution.txt"));


        //判断无解的情况
        PointGameOf24 test = new PointGameOf24();
        int NoSolutionCountOfDiff = 0;
        for(int i=0;i<newListOfDiffColor.length;i++){
            /*for(int j=0;j<list.get(i).size();j++){
                System.out.print(list1[i][j]+" ");
            }
            System.out.println();*/
            result = test.getResult(newListOfDiffColor[i]);
            if(result.size() != 0){
                /*for(String temp:result){
                    System.out.println(temp);
                }
                System.out.println();*/
            }else{

                for(int j=0;j<listOfDiffColor.get(i).size();j++){
                    System.out.print(newListOfDiffColor[i][j]+" ");
                }
                System.out.println("No Solution");

                //输出到文件
                /*for(int index=0;index<newListOfDiffColor.length;index++){
                    for(int index1=0;index1<newListOfDiffColor[index].length;index1++){
                        OutPut.write(newListOfDiffColor[index][index1]);
                    }
                }*/

                NoSolution[NoSolutionCountOfDiff] = newListOfDiffColor[i];
                NoSolutionCountOfDiff++;
            }
        }
        //System.out.println(NoSolutionCountOfDiff);

        //考虑花色的所有组合
        for (int i = 1; i <= 52; i++) {
            for (int j = i+1; j <= 52; j++) {
                for (int k = j+1; k <= 52; k++) {
                    for (int l = k+1; l <= 52; l++) {
                        List<Integer> temp = new ArrayList<>();
                        temp.add(i);
                        temp.add(j);
                        temp.add(k);
                        temp.add(l);
                        listOfSameColor.add(temp);
                    }
                }
            }
        }

        //替换牌面数字
        for(int i=0;i<listOfSameColor.size();i++){
            for(int j=0;j<listOfSameColor.get(i).size();j++){
                int index = listOfSameColor.get(i).get(j);
                listOfSameColor.get(i).remove(j);
                listOfSameColor.get(i).add(j,num[index]);

            }
            Collections.sort(listOfSameColor.get(i));
        }

        //ToArray
        for(int i=0;i<listOfSameColor.size();i++) {
            for (int j = 0; j < listOfSameColor.get(i).size(); j++) {
                newlistOfSameColor[i][j] = listOfSameColor.get(i).get(j);
            }
        }


        //从文件读入
        /*File file = new File("NoSolution.txt");
        //文件存在
        if(file.exists()){

            ObjectInputStream InPut = new ObjectInputStream(new FileInputStream(file));
            for(int index=0;index<newListOfDiffColor.length;index++){
                for(int index1=0;index1<newListOfDiffColor[index].length;index1++){
                    newListOfDiffColor[index][index1] = InPut.readInt();
                }
            }
        }
        else{

        }*/



        int NoSolutionOfSame = 0;
        for(int i=0;i<newlistOfSameColor.length;i++) {
            for (int j = 0; j < NoSolution.length; j++) {
                if (Arrays.equals(newlistOfSameColor[i], NoSolution[j])) {
                    NoSolutionOfSame++;
                    break;
                }
            }
        }

        System.out.println("Total number of combinations is "+newlistOfSameColor.length);
        System.out.println("Total number of combinations with solutions is "+(newlistOfSameColor.length-NoSolutionOfSame));
        double probability = ((double)(newlistOfSameColor.length-NoSolutionOfSame))/((double)newlistOfSameColor.length);
        System.out.println("The solution ratio is "+probability);

        // 3 3 8 8 特殊情况
        /*int specialCount = 0;
        int[] special = {3,3,8,8};
        for(int i=0;i<newlistOfSameColor.length;i++){

            if(Arrays.equals(newlistOfSameColor[i],special)){
                specialCount++;
            }
        }
        System.out.println("specialCount = "+specialCount);*/
    }
}
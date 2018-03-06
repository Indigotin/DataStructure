package Project3;

import java.util.Scanner;

public class SixteenTail {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        String s = input.nextLine();
        char[] initialNode = s.toCharArray();
        SixteenTailModel model = new SixteenTailModel();
        java.util.List<Integer> path = model.getShortestPath(SixteenTailModel.getIndex(initialNode));

        for (int i = 0; i < path.size(); i++){
            SixteenTailModel.printNode(SixteenTailModel.getNode(path.get(i).intValue()));
        }
    }
}

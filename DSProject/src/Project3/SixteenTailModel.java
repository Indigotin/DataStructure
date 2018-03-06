package Project3;

import Project4.Graph.version1.AbstractGraph;
import Project4.Graph.version2.UnweightedGraph;

import java.util.ArrayList;
import java.util.List;

public class SixteenTailModel {

    public final static int NUMBER_OF_NODES = 65536;
    protected AbstractGraph<Integer>.Tree tree;

    public SixteenTailModel(){

        List<AbstractGraph.Edge> edges = getEdges();
        UnweightedGraph<Integer> graph = new UnweightedGraph<>(edges, NUMBER_OF_NODES);
        tree = graph.bfs(NUMBER_OF_NODES-1);
    }

    private List<AbstractGraph.Edge> getEdges() {

        List<AbstractGraph.Edge> edges = new ArrayList<>();

        for (int u = 0; u < NUMBER_OF_NODES; u++) {
            for (int k = 0; k < 16; k++) {

                char[] node = getNode(u);
                //是H 反面才可以进行翻转
                if (node[k] == 'H') {
                    int v = getFlippedNode(node, k);
                    //添加边
                    edges.add(new AbstractGraph.Edge(v, u));
                }
            }
        }
        return edges;
    }

    public static int getFlippedNode(char[] node, int position) {
        int row = position / 4;
        int column = position % 4;

        /*flipACell(node, row, column);
        flipACell(node, row - 1, column);
        flipACell(node, row + 1, column);
        flipACell(node, row, column - 1);
        flipACell(node, row, column + 1);*/
        flipACell(node, row, column);
        flipACell(node, row - 1, column);
        flipACell(node, row + 1, column);
        flipACell(node, row, column - 1);
        flipACell(node, row, column + 1);
        flipACell(node, row - 1, column - 1);
        flipACell(node, row + 1, column - 1);
        flipACell(node, row - 1, column + 1);
        flipACell(node, row + 1, column + 1);

        return getIndex(node);
    }

    public static int getIndex(char[] node) {

        int result = 0;
        for (int i = 0; i < 16; i++){
            if (node[i] == 'T')
                result = result * 2 + 1;
            else
                result = result * 2 + 0;
        }
        return result;
    }

    public static void flipACell(char[] node, int row, int column) {
        if (row >= 0 && row <= 3 && column >= 0 && column <= 3) {
            if (node[row * 4 + column] == 'H'){
                node[row * 4 + column] = 'T';
            }
            else{
                node[row * 4 + column] = 'H';
            }
        }
    }

    public static char[] getNode(int index) {

        char[] node = new char[16];

        for (int i = 0; i < 16; i++) {
            int digit = index % 2;
            if (digit == 0){
                node[15 - i] = 'H';
            }
            else{
                node[15 - i] = 'T';
            }
            index = index / 2;
        }
        return node;
    }

    public List<Integer> getShortestPath(int nodeIndex) {
        return tree.getPath(nodeIndex);
    }

    public static void printNode(char[] node) {
        /*for (int i = 0; i < 16; i++){
            if (i % 4 != 3){
                System.out.print(node[i]);
            }
            else{
                System.out.println(node[i]);
            }
        }
        System.out.println();*/
    }
}

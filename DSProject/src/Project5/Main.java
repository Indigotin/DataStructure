package Project5;

import Project4.Graph.version1.AbstractGraph;

import java.util.List;

public class Main {
    public static void main(String[] args){

        CrossRiverModel model;
        List<List<Position>> solution;
        List<Position> vertices = CrossRiverModel.creatVertice();
        List<AbstractGraph.Edge> edges = CrossRiverModel.creatEdge(vertices);
        model = new CrossRiverModel(edges,vertices);
        solution = model.getSolution();
        for(int i=0;i<solution.size();i++){
            for(int j=0;j<solution.get(i).size();j++){
                    System.out.println(solution.get(i).get(j).getSouth()+solution.get(i).get(j).getNorth());
            }
            System.out.println();
        }
    }
}

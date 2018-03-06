package Project4.Graph.version2;

import Project4.Graph.version1.AbstractGraph;

import java.util.*;

public class UnweightedGraph<V> extends AbstractGraph<V> {

  public UnweightedGraph(int[][] edges, V[] vertices) {
    super(edges, vertices);
  }

  public UnweightedGraph(List<Edge> edges, List<V> vertices) {
    super(edges, vertices);
  }

  public UnweightedGraph(List<Edge> edges, int numberOfVertices) {
    super(edges, numberOfVertices);
  }

  public UnweightedGraph(int[][] edges, int numberOfVertices) {
    super(edges, numberOfVertices);
  }
}

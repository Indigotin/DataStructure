package Project4.Graph.version1;

import java.util.*;

public abstract class AbstractGraph<V> implements Graph<V> {
  protected List<V> vertices;
  protected List<List<Integer>> neighbors;

  protected AbstractGraph(int[][] edges, V[] vertices) {
    this.vertices = new ArrayList<V>();
    for (int i = 0; i < vertices.length; i++)
      this.vertices.add(vertices[i]);
    
    createAdjacencyLists(edges, vertices.length);
  }

  protected AbstractGraph(List<Edge> edges, List<V> vertices) {
    this.vertices = vertices;
    createAdjacencyLists(edges, vertices.size());
  }

  protected AbstractGraph(List<Edge> edges, int numberOfVertices) {
    vertices = new ArrayList<V>();
    for (int i = 0; i < numberOfVertices; i++) {
      vertices.add((V)(new Integer(i)));
    }
    createAdjacencyLists(edges, numberOfVertices);
  }
  protected AbstractGraph(int[][] edges, int numberOfVertices) {
    vertices = new ArrayList<V>();
    for (int i = 0; i < numberOfVertices; i++) {
      vertices.add((V)(new Integer(i)));
    }
    createAdjacencyLists(edges, numberOfVertices);
  }

  private void createAdjacencyLists(
      int[][] edges, int numberOfVertices) {

    neighbors = new ArrayList<List<Integer>>();
    for (int i = 0; i < numberOfVertices; i++) {
      neighbors.add(new ArrayList<Integer>());
    }

    for (int i = 0; i < edges.length; i++) {
      int u = edges[i][0];
      int v = edges[i][1];
      neighbors.get(u).add(v);
    }
  }

  private void createAdjacencyLists(
      List<Edge> edges, int numberOfVertices) {

    neighbors = new ArrayList<List<Integer>>();
    for (int i = 0; i < numberOfVertices; i++) {
      neighbors.add(new ArrayList<Integer>());
    }

    for (Edge edge: edges) {
      neighbors.get(edge.u).add(edge.v);
    }
  }

  public int getSize() {
    return vertices.size();
  }

  public List<V> getVertices() {
    return vertices;
  }

  public V getVertex(int index) {
    return vertices.get(index);
  }

  public int getIndex(V v) {
    return vertices.indexOf(v);
  }

  public List<Integer> getNeighbors(int index) {
    return neighbors.get(index);
  }

  public int getDegree(int v) {
    return neighbors.get(v).size();
  }

  public int[][] getAdjacencyMatrix() {
    int[][] adjacencyMatrix = new int[getSize()][getSize()];

    for (int i = 0; i < neighbors.size(); i++) {
      for (int j = 0; j < neighbors.get(i).size(); j++) {
        int v = neighbors.get(i).get(j);
        adjacencyMatrix[i][v] = 1;
      }
    }

    return adjacencyMatrix;
  }

  public void printAdjacencyMatrix() {
    int[][] adjacencyMatrix = getAdjacencyMatrix();
    for (int i = 0; i < adjacencyMatrix.length; i++) {
      for (int j = 0; j < adjacencyMatrix[0].length; j++) {
        System.out.print(adjacencyMatrix[i][j] + " ");
      }

      System.out.println();
    }
  }

  public void printEdges() {
    for (int u = 0; u < neighbors.size(); u++) {
      System.out.print("Vertex " + u + ": ");
      for (int j = 0; j < neighbors.get(u).size(); j++) {
        System.out.print("(" + u + ", " +
          neighbors.get(u).get(j) + ") ");
      }
      System.out.println();
    }
  }

  public static class Edge {
    public int u;
    public int v;

    public Edge(int u, int v) {
      this.u = u;
      this.v = v;
    }
  }

  public Tree dfs(int v) {
    List<Integer> searchOrders = new ArrayList<Integer>();
    int[] parent = new int[vertices.size()];
    for (int i = 0; i < parent.length; i++)
      parent[i] = -1;

    boolean[] isVisited = new boolean[vertices.size()];

    dfs(v, parent, searchOrders, isVisited);

    return new Tree(v, parent, searchOrders);
  }

  private void dfs(int v, int[] parent, List<Integer> searchOrders,
      boolean[] isVisited) {

    searchOrders.add(v);
    isVisited[v] = true;

    for (int i : neighbors.get(v)) {
      if (!isVisited[i]) {
        parent[i] = v;
        dfs(i, parent, searchOrders, isVisited);
      }
    }
  }

  public Tree bfs(int v) {
    List<Integer> searchOrders = new ArrayList<Integer>();
    int[] parent = new int[vertices.size()];
    for (int i = 0; i < parent.length; i++)
      parent[i] = -1;

    LinkedList<Integer> queue = new LinkedList<Integer>();

    boolean[] isVisited = new boolean[vertices.size()];
    queue.offer(v);
    isVisited[v] = true;

    while (!queue.isEmpty()) {
      int u = queue.poll();
      searchOrders.add(u);
      for (int w : neighbors.get(u)) {
        if (!isVisited[w]) {
          queue.offer(w);
          parent[w] = u;
          isVisited[w] = true;
        }
      }
    }

    return new Tree(v, parent, searchOrders);
  }

  public class Tree {
    private int root;
    private int[] parent;
    private List<Integer> searchOrders;

    public Tree(int root, int[] parent, List<Integer> searchOrders) {
      this.root = root;
      this.parent = parent;
      this.searchOrders = searchOrders;
    }

    public Tree(int root, int[] parent) {
      this.root = root;
      this.parent = parent;
    }

    public int getRoot() {
      return root;
    }

    public int getParent(int v) {
      return parent[v];
    }

    public List<Integer> getSearchOrders() {
      return searchOrders;
    }

    public int getNumberOfVerticesFound() {
      return searchOrders.size();
    }

    /*public List<V> getPath(int index) {
      ArrayList<V> path = new ArrayList<V>();

      do {
        path.add(vertices.get(index));
        index = parent[index];
      }
      while (index != -1);

      return path;
    }*/
    public List<Integer> getPath(int index) {
      ArrayList<Integer> path = new ArrayList<Integer>();

      do {
        path.add(index);
        index = parent[index];
      }
      while (index != -1);

      return path;
    }

    /*public void printPath(int index) {
      List<V> path = getPath(index);
      System.out.print("A path from " + vertices.get(root) + " to " +
        vertices.get(index) + ": ");
      for (int i = path.size() - 1; i >= 0; i--)
        System.out.print(path.get(i) + " ");
    }*/

    public void printTree() {
      System.out.println("Root is: " + vertices.get(root));
      System.out.print("Edges: ");
      for (int i = 0; i < parent.length; i++) {
        if (parent[i] != -1) {

          System.out.print("(" + vertices.get(parent[i]) + ", " +
            vertices.get(i) + ") ");
        }
      }
      System.out.println();
    }
  }

  public List<Integer> getHamiltonianPath(V vertex) {
    return getHamiltonianPath(getIndex(vertex));
  }

  public List<Integer> getHamiltonianPath(int v) {

    int[] next = new int[getSize()];       
    for (int i = 0; i < next.length; i++)
      next[i] = -1;
    
    boolean[] isVisited = new boolean[getSize()]; 

    List<Integer> result = null;

    for (int i = 0; i < getSize(); i++)
      reorderNeigborsBasedOnDegree(neighbors.get(i));
    
    if (getHamiltonianPath(v, next, isVisited)) {
      result = new ArrayList<Integer>();
      int vertex = v;
      while (vertex != -1) {
        result.add(vertex);
        vertex = next[vertex];
      }
    }
    
    return result;
  }

  private void reorderNeigborsBasedOnDegree(List<Integer> list) {
    for (int i = list.size() - 1; i >= 1; i--) {

      int currentMaxDegree = getDegree(list.get(0));
      int currentMaxIndex = 0;

      for (int j = 1; j <= i; j++) {
        if (currentMaxDegree < getDegree(list.get(j))) { 
          currentMaxDegree = getDegree(list.get(j));
          currentMaxIndex = j;
        }
      }

      if (currentMaxIndex != i) {
        int temp = list.get(currentMaxIndex);
        list.set(currentMaxIndex, list.get(i));
        list.set(i, temp);
      }
    }
  }

  private boolean allVisited(boolean[] isVisited) {
    boolean result = true;
    
    for (int i = 0; i < getSize(); i++) 
      result = result && isVisited[i];
    
    return result;
  }

  private boolean getHamiltonianPath(int v, int[] next,
      boolean[] isVisited) {
    isVisited[v] = true;

    if (allVisited(isVisited)) 
      return true;
      
    for (int i = 0; i < neighbors.get(v).size(); i++) {
      int u = neighbors.get(v).get(i);
      if (!isVisited[u] && 
          getHamiltonianPath(u, next, isVisited)) {
        next[v] = u;
        return true; 
      }
    }

    isVisited[v] = false;
    return false; // No Hamiltonian path exists from vertex v
  }

  public void addVertex(V vertex) {
    vertices.add(vertex);
    neighbors.add(new ArrayList<Integer>());
  }

  public void addEdge(int u, int v) {
    neighbors.get(u).add(v);
    neighbors.get(v).add(u);
  }
}

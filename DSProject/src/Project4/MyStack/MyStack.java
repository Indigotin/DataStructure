package Project4.MyStack;

import java.util.ArrayList;

public class MyStack {

  private ArrayList list = new ArrayList();
  public MyStack(){}

  public boolean isEmpty() {
    return list.isEmpty();
  }

  public int getSize() {
    return list.size();
  }

  public Object peek() {
    return list.get(getSize() - 1);
  }

  public Object pop() {
    Object o = list.get(getSize() - 1);
    list.remove(getSize() - 1);
    return o;
  }

  public void push(Object o) {
    list.add(o);
  }

  public int search(Object o) {
    return list.lastIndexOf(o);
  }

  public ArrayList getList() {
    return list;
  }

  public String toString() {
    return "stack: " + list.toString();
  }
}

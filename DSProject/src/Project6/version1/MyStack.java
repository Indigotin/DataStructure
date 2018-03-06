package Project6.version1;

import java.util.LinkedList;

public class MyStack<E> {

    private LinkedList<E> stack;

    public MyStack(){
        stack = new LinkedList<>();
    }

    public boolean isEmpty(){
        if (stack.size() == 0){
            return true;
        }
        return  false;
    }
    public E pop(){
        E temp = stack.getLast();
        stack.removeLast();
        return temp;
    }

    public E peek(){
        return stack.getLast();
    }

    public boolean push(E object){
        return stack.add(object);
    }

    public int size(){
        return  stack.size();
    }

}

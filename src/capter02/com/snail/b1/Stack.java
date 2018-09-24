package capter02.com.snail.b1;

import java.util.Arrays;
import java.util.EmptyStackException;

/**
 * 内存泄漏：如果一个栈是先增长，然后再收缩，那么从栈中弹出的对象将不会被当做垃圾回收，即使使用栈的程序不再引用这些对象，它们也不会被回收
 * 因为栈中维护着对这些对象的过期引用，过期引用是指永远不会被解除的引用
 */
public class Stack {

    private Object[] elements;
    private int size = 0;
    private static final int DEFAULT_INITIAL_CAPACITY = 16;

    public Stack(){
        elements = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public void push(Object e){
        ensureCapacity();
        elements[size++] = e;
    }

    public Object pop(){
        if(size==0)
            throw new EmptyStackException();
        return elements[--size];
    }

    private void ensureCapacity(){
        if(elements.length==size){
            elements = Arrays.copyOf(elements,2 * size+1);
        }
    }
}

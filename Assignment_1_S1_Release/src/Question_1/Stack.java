/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_1;

/**
 *
 * @author xhu
 */
public class Stack <E extends Comparable<E>>{
    
    LinkedList<E> stack = new LinkedList();
    
    
    public void push(E data)
    {
        stack.addHead(data); // getting 1st element from linked list
    }
    
    public E pop()
    {
        Node<E> removeTop = stack.removeFromHead(); // removing top of the stack
        return removeTop.data;
    }
    
    public void printStack()
    {
        stack.printLinkedList(); // printing stack from linked list
    }
    
    public int getSize()
    {
        return stack.size; // getting size from linked list
    }
}

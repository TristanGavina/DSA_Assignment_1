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
public class Queue <E extends Comparable<E>>{
    
    private LinkedList<E> queue = new LinkedList();
    
    public void enqueue(E data)
    {
        queue.add(data); //getting queue from linked list
    }
    
    public E dequeue()
    {
        Node<E> removeFirst = queue.removeFromHead(); //removing 1st element in queue
        return removeFirst.data;
    }
    
    public void printQueue()
    {
        queue.printLinkedList(); // printing queue from linked list
    }
    
    public int getSize()
    {
        return queue.size; // getting size from linked list
    }
}

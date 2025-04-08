/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Question_1;

/**
 *
 * @author xhu
 * @param <E>
 */
public class Node <E extends Comparable<E>> { 
    public E data;
    public Node<E> next;
    
    public boolean equals(Node node)
    {
        return node.data.equals(this.data);        
    }
    
    public int compareTo(Node<E> node)
    {
        return this.data.compareTo(node.data);
    }
    
    public Node(E data){ //constructor for LinkedList.java
        this.data = data;
        this.next = null;
    }
    
    public Node(){ //default constructor for LinkedListTest.java
        this.next = null;
    }
}

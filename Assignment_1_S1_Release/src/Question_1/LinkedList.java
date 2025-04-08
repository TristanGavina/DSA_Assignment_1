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
public class LinkedList <E extends Comparable<E>>{
    
    public int size = 0;
    public Node<E> head;
    
    public void addHead(E data)
    {
        Node<E> newNode = new Node<>(data); //newNode.next = null
        newNode.next = head;
        head = newNode;
        size++;
    }
    
    public Node<E> getHead()
    {
        
        return head;
    }
    
    public void add(E data)
    {
        Node<E> newNode = new Node<>(data);
        head = add(head, newNode); // call recursive method
        size++;
    }
    
    private Node<E> add(Node<E> head, Node<E> node)
    {
        if(head == null){// checking if list is empty
            return node; //inster and make new node has head 
        }
        head.next = add(head.next, node); //adding and moving to next node
        return head;
    }
    
    public void printLinkedList()
    {
        printLinkedList(head); // calls recursive function
    }
    
    private void printLinkedList(Node<E> node)
    {
        if(node == null){
            return;
        }
        System.out.println(node.data); // print nodes
        printLinkedList(node.next); // iterate through nodes to print
    }
    
    public boolean contains(E data)
    {
        Node<E> currentNode = new Node<>(data);
        return contains(head, currentNode); // calling recursive function
    }
    
    private boolean contains(Node<E> head, Node<E> node)
    {
        if(head == null){ 
            return false; // checks if head is null
        }
        if(head.data.equals(node.data)){
            return true; // checks if head's data contains same as node's data
        }
        return contains(head.next, node); // checking each node starting from head
    }
    
    public void remove(E data)
    {
        Node<E> objectFromBody = new Node<>(data);
        head = removeFromBody(head, objectFromBody); // calls recursive remove from body function 
        size--; // decrease size when object removed
    }
    
    public void remove(int position)
    {
        head = removeByIndex(head, position);
        size--; // decrease size
    }
    
    private Node removeByIndex(Node<E> head, int position)
    {
        if(position == 0){
            return head.next; //remove head when position/index is 0 and make next node as head
        }
        head.next = removeByIndex(head.next, position - 1);
        return head;
    }
    
    private Node<E> removeFromBody(Node<E> head, Node<E> node)
    {
        if(head == null){
            return node; //returns node when head is empty
        }
        if(head.data.equals(node.data)){
            head = head.next;
            return head;
        }
        head.next = removeFromBody(head.next, node);
        return head;
    }
    
    public Node<E> removeFromHead()
    {
        Node<E> removeHeadNode = head;
        head = head.next; // head will move to next node when removed
        size--; // size decrease when head removed
        return removeHeadNode; //will remove head
    } 
    
    
    public Node<E> removeFromTail()
    {
        head = removeFromTail(head);
        size--;
        return head;  
    }
    
    private Node<E> removeFromTail(Node node)
    {
        if(node.next == null){ // finding tail/last node is null
            return null; // removing tail/last node
        }
        node.next = removeFromTail(node.next); //updating new tail using recursive function 
        return node;
    }
    
    public void addInOrder(E data)
    {
        Node<E> addOrder = new Node<>(data);
        head = addInOrder(head, addOrder);
        size++;
    }
    
    private Node<E> addInOrder(Node<E> currentNode, Node<E> newNode)
    {
        if(currentNode == null){
            return newNode; 
        }
        if(newNode.data.compareTo(currentNode.data) <= 0){ //make sure newNode is placed first before currentNode
            newNode.next = currentNode; 
            return newNode;
        }
        
        currentNode.next = addInOrder(currentNode.next, newNode);
        return currentNode;
    }
    
    public Node<E> getNode(int index)
    {
        
        return getNode(index, head); // caling recursive function
    }
    
    private Node<E> getNode(int index, Node<E> head)
    {
        if(index == 0){ 
            return head; //make index 0 head node
        }
        return getNode(index - 1, head.next); //recursive function
    }
    
    public E getData(int index)
    {
        return getData(index, head); // calling recursive function
    }
    
    private E getData(int index, Node<E> head)
    {
        if(index == 0){
            return head.data; //finding node at index
        }
        return getData(index - 1, head.next);
    }    
}

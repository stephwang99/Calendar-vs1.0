/**
 * LinkedList.java - An implementation of a Linked List structure to be extended into a Priority Queue
 *
 * @author Ryan Ly
 * @version 1.00 2016/12/14
 */
public class LinkedList {

     Node head;
     int length;

     //Default constructor
    public LinkedList(){
        this.head = null;
        this.length = 0;
    }

    public LinkedList(Node head){
        this.head = head;
        this.length = 1;
    }

    //Checks if the list is empty
    public boolean isEmpty(){
        if(head == null){
            return true;
        } else {
            return false;
        }
    }

    //Adds a cargo to the specified index
    public void add(Object cargo, int index){
        Node node = head;

        //Cannot add to a non existant index (i.e. can't add to index 100 of length 10 list)
        if(index > length || index < 0){
            return;
        }

        //Handles adding to the beginning of the list
        if(index == 0){
            //Sets new node's next to be previous head, then sets new node as head
            Node a = new Node(cargo, head);
            head = a;
            length++;
            return;
        }
        //Iterates to desired node
        for(int i = 0; i < index - 1;i++){
            node = node.next;
        }
        //If the node is not the last node (i.e between 1 and length)
        if(index != length){
            //Inserts the cargo into right spot, sets next to the node that was previously there
            node.next = new Node(cargo, node.next);
            length++;
            //Handles adding to end of list
        } else if(index == length){
            //Same as above, but sets next to null
            node.next = new Node(cargo, null);
            length++;
        }
    }

    //Adds cargo to last index (calls add())
    public void addLast(Object cargo){
        add(cargo, length);
    }

    //Removes the node from the specified index
    public void remove(int index){
        //Iterates to node at target index
        Node node = head;
        for(int i = 0;i < index - 1;i++){
            node = node.next;
        }
        //Sets previous node's next to target node's next (skips target node)
        node.next = node.next.next;
    }

    public int getLength(){
        return length;
    }

    public Node getHead(){
        return head;
    }

    //Returns the node at the specified index
    public Node findNode(int index){
        Node node = head;
        for(int i = 0;i < index;i++){
            node = node.next;
        }

        return node;
    }

    //Takes a cargo, find and returns the index at which it is located
    public int findCargo(Object cargo){
        for(int i = 0;i < length;i++){
            if(findNode(i).cargo.equals(cargo)){
                return i;
            }
        }

        return -1;
    }

    public String toString(){
        String output = "";
        Node node = head;
        for(int i = 0;i < length;i++){
            output += node;
            node = node.next;
        }

        return output;
    }

}

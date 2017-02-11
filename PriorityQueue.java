/**
 * PriorityQueue.java - extension of Linked List that uses FIFO, and allows for sorting based on the objects contained
 *
 * @author Ryan Ly
 * @version 1.00 2016/12/14
 */

public class PriorityQueue extends LinkedList{

    private Node last;

    //Default constructor
    public PriorityQueue(){
        super();
        this.last = null;
    }

    public PriorityQueue(Node first, Node last){
        super(first);
        this.last = last;
    }

    public PriorityQueue(Object cargo){
        super(new Node(cargo, null));
        this.last = head;
    }

    public Node getLast(){
        return this.last;
    }

    //Adds a node with specified cargo to specified index
    public void add(Object cargo, int index){
        Node node = head;

        //Cannot add to a non existant index (i.e. can't add to index 100 of length 10 list)
        if(index > length || index < 0){
            return;
        }

        //Handles adding to the beginning of the list
        if(index == 0){
            //Sets new node's next to be previous head, then sets new node as head
            head = new Node(cargo, head);
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
            this.last = node.next;
            length++;
        }
    }

    //Adds to the end of the list
    public void addLast(Object cargo){
        add(cargo, length);
        this.last = findNode(length);
    }

    public void remove(int index){
        //If index does no exist
        if(index > length || index < 0){
            return;
        }

        Node node = head;
        for(int i = 0;i < index - 1;i++){
            node = node.next;
        }
        //Removes last object
        if(index == length){
            node.next = null;
            last = node;
        //Removes the first index
        } else if(index == 0){
            head = node.next;
        } else {
            node.next = node.next.next;
        }
        length--;
    }

}

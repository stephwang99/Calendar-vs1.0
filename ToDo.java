/**
 * ToDo.java - uses PriorityQueue in order to hold tasks into a todo list
 *
 * @author Ryan Ly
 * @version 1.00 2016/12/14
 */

import java.io.*;
import java.util.Scanner;

public class ToDo extends PriorityQueue {


    //Default constructor
    public ToDo(){
        super();
    }

    public ToDo(Task task){
        super(task);
    }

    //Creates a list with tasks from array
    public ToDo(Task[] task) {
        super(task[0]);
        for (int i = 1; i < task.length; i++) {
            addLast(task[i]);
        }
    }

    //Sorts the ToDo list
    public void sort(){
        //Takes tasks in todo list and uts them into a heap
        Object[] tasks = new Object[this.length];
        for(int i = 0;i < this.length;i++){
            tasks[i] = findNode(i).cargo;
        }
        Heap heap = new Heap(tasks);
        //Heap sorts the heap
        heap.sort();

        //Removes everything from list
        int originalLength = length;
        for(int i = 0;i < originalLength;i++){
            remove(0);
        }
        //Readds everything to list from heap, sorted
        for(int i = 0; i < heap.getSize();i++){
            add(heap.getCargo(i), i);
        }
    }

    //Swaps two objects in list
    private void swap(int index1,int index2){
        //Gets reference for the nodes in each index
        Node first = findNode(index1);
        Node second = findNode(index2);
        //Sets each node's .next to the other's .next
        if(second.next == null) {
            //If the second on is the end
            first.next = null;
        } else {
            first.next = second.next;
        }
        second.next = first;

        //If first node is beginning of list
        if(index1 == 0) {
            head = second;
        } else {
            findNode(index1 - 1).next = second;
        }
    }

    //Calculates priority of task using formula:  importance ^ (days in list / 4)
    private void calcPriority(Task task){
        task.setPriority((int)(Math.pow(task.getImportance(),task.getDaysSinceAdded()/4.0)));
    }

    //Calculates priority for every item in list
    public void refreshPriority(){
        //Loops through and calls calcPriority for each item
            for(int i = 0;i < this.length;i++){
                calcPriority((Task)findNode(i).cargo);
        }
    }

    //Pushes the highest priority task
    public Task pushTask(){
        //Recalculates each item's priority
        refreshPriority();
        //Heap sorts the liost
        sort();
        //removes and returns highest item (head)
        Task out = (Task)head.cargo;
        this.remove(0);
        return out;
    }

    //Reads the file holding toDo list info
    public void readFile(String file){
        try{
            Scanner in = new Scanner(new FileReader(file));
            //reads until reaches 'end' line
            while(true){
                //Reads each line
                String line = in.nextLine();
                //Adds a new task using each set of 3 lines (line 1 = name, line 2 = importance, line 3 = date added)
                if(!line.equals("end")){
                    this.addLast(new Task(line, Integer.parseInt(in.nextLine()), sDate.parseString(in.nextLine())));
                } else { //end of file
                    break;
                }
            }
            //Calculates each item's priority on read
            refreshPriority();
        } catch (FileNotFoundException e){
            System.out.println("File not found.");
        }
    }

    //Writes to toDo file
    public void writeFile(String file){
        try{
            PrintWriter out = new PrintWriter(new FileWriter(file));
            //Writes in order of item's: name, importance, date added
            for(int i = 0;i < length;i++){
                out.println(((Task)this.findNode(i).cargo).getName());
                out.println(((Task)this.findNode(i).cargo).getImportance());
                out.println(((Task)this.findNode(i).cargo).getDateAdded());
            }
            //Prints end at the end of file
            out.println("end");
            //Closes the file
            out.close();
        } catch (IOException e){
            System.out.println("Error writing to file.");
        }
    }
}

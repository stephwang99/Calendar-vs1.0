
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class GUICalendar extends JFrame implements ActionListener{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static JLabel lblMonthandYear, lblText, lblTodoHeader;
	static JTextArea txtTodo;
    JButton btnPrev, btnNext, btnAdd, btnToDoAdd, btnPushToDo, btnRemove;
    static JTable tblCal;
    Container pane;
    static DefaultTableModel defCal;
    static JScrollPane sCal;
    JPanel pnlCal;
    static int currentMonth, currentYear, currentDay, month, day, year;
    static int selectedRow, selectedCol;
    static boolean selectedCell = false;
    static FileWriter fw = null;
    static String [] info = new String [1000000];
	static int counting=0;
	static Timer notificationTimer;
	//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	ToDo toDoList = new ToDo();
	private final String TO_DO_FILE = "todo.txt";
	static JComboBox<String> cbDropdown;
	//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    public GUICalendar(){   		
    	
        String [] week = {"Sun", "Mon", "Tues", "Wed", "Thurs", "Fri", "Sat"};
        
        currentMonth = 0;
        currentYear = 2017;
        currentDay = 0;
        
        //Setting layout
        this.setSize(900,500);
        this.setTitle("ICS4UR vs 1.0");
        pane = this.getContentPane();
        pane.setLayout(null);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setResizable(false);
        
        //Creating necessary objects eg. JLabel, JButton
        lblText = new JLabel();
        txtTodo = new JTextArea();
        lblTodoHeader = new JLabel();
        lblMonthandYear = new JLabel();
        notificationTimer = new Timer(60000, this);
		notificationTimer.setInitialDelay(0);
		notificationTimer.start();
		notificationTimer.setActionCommand("timer");

		btnPrev = new JButton("<");
        btnNext = new JButton(">");
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		btnToDoAdd = new JButton("Add task");
		btnPushToDo = new JButton("Give me a task!");
		btnRemove = new JButton("Remove");
		cbDropdown = new JComboBox<>();
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
        btnAdd = new JButton("+");
        btnAdd.setBackground(Color.PINK);
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		btnToDoAdd.setBackground(Color.LIGHT_GRAY);
		btnPushToDo.setBackground(Color.LIGHT_GRAY);
		btnPrev.setBackground(Color.PINK);
		btnNext.setBackground(Color.PINK);
		btnRemove.setBackground(Color.LIGHT_GRAY);
		//txtTodo.setBackground(tblCal.getBackground());
		cbDropdown.setBackground(Color.WHITE);
		txtTodo.setEditable(false); //Makes TextArea uneditable
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		
        defCal = new DefaultTableModel(){
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int col){
                return false;
            }
        };
        
        tblCal = new JTable(defCal);
        sCal = new JScrollPane(tblCal);
        pnlCal = new JPanel(null);

        pnlCal.setBorder(BorderFactory.createTitledBorder("calendar"));
        
        //Allowing buttons to function
        btnPrev.addActionListener(this);
        btnNext.addActionListener(this);
        btnAdd.addActionListener(this);
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		btnToDoAdd.addActionListener(this);
		btnPushToDo.addActionListener(this);
		btnRemove.addActionListener(this);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

		//Adding elements to the panel
        pane.add(pnlCal);
        pnlCal.add(lblMonthandYear);
        pnlCal.add(lblText);
        pnlCal.add(txtTodo);
        pnlCal.add(lblTodoHeader);
        pnlCal.add(btnPrev);
        pnlCal.add(btnNext);
        pnlCal.add(btnAdd);
        pnlCal.add(sCal);
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		pnlCal.add(btnToDoAdd);
		pnlCal.add(btnPushToDo);
		pnlCal.add(cbDropdown);
		pnlCal.add(btnRemove);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		
		//Setting bounds to objects (formatting)
        this.setVisible(true);
        pnlCal.setBounds(0, 0,880, 460);
        lblMonthandYear.setBounds(190, 25, 100, 25);
        lblTodoHeader.setBounds(510, 225, 400, 25);
        lblTodoHeader.setText("To Do");
        lblTodoHeader.setVerticalAlignment(JLabel.TOP);
        txtTodo.setBounds(510, 250, 300, 125);
        txtTodo.setText("empty");
        lblText.setBounds(510, 25, 400, 200);
        lblText.setText("empty");
        btnPrev.setBounds(10, 25, 50, 25);
        btnNext.setBounds(420, 25, 50, 25);
        btnAdd.setBounds(410, 390, 50, 50);
        tblCal.setBounds(10, 120, 460, 375);
        sCal.setBounds(10,65,460, 385);
		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		btnToDoAdd.setBounds(500, 390, 100, 25);
		btnPushToDo.setBounds(700, 390, 125, 25);
		cbDropdown.setBounds(550, 420, 150, 25);
		btnRemove.setBounds(710,420,85,25);
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
		
		//Adding the week to the header
        for(int i = 0; i < week.length; i++){
            defCal.addColumn(week[i]);
        }
        
        //More formattng for JTable
        tblCal.getParent().setBackground(tblCal.getBackground());
        tblCal.getTableHeader().setResizingAllowed(false);

        //Allowing selection
        tblCal.setColumnSelectionAllowed(true);
        tblCal.setRowSelectionAllowed(true);
        tblCal.setCellSelectionEnabled(true);
        tblCal.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        //Formatting
        tblCal.setRowHeight(60);
        defCal.setColumnCount(7);
        defCal.setRowCount(6);
    	
        //Method that refreshes the JTable and JLabels
        changing(currentMonth, currentYear);

		//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
		//Reads the file for ToDo list, then writes it to the TextArea/ComboBox and the ToDo list
		toDoList.readFile(TO_DO_FILE);
		writeToDo(toDoList);

		//Changes close operation to write ToDo list to the file
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				toDoList.writeFile(TO_DO_FILE);
				System.exit(0);
			}
		});
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    }
    
    //Method used to read the memory text file
	public static void reading(){
		try{
			String list="";
			BufferedReader in;
			in = new BufferedReader(new FileReader("memory.txt"));
			String content = in.readLine();
			//Checking if the memory is empty or not
			if(content!= null){
				while(content!=null){
					//Portion for selected Cells
					//Outputs information onto JLabel for that specific date/cell
					if(selectedCell){
						if(Integer.parseInt(content)== currentYear){
							content = in.readLine();
							if(Integer.parseInt(content)==currentMonth){
								content = in.readLine();
								if(Integer.parseInt(content)==selectedRow){
									content = in.readLine();
									if(Integer.parseInt(content)==selectedCol){
										list += "<br>" + in.readLine();
										list += "<br>"+in.readLine();
										lblText.setText("<html>"+list+"</html");
										content = in.readLine();
									}
									//If the date does not match, move on to the next event/reminder
									else{
										content = in.readLine();
										content = in.readLine();
										content = in.readLine();
									}
								}
								else{
									content = in.readLine();
									content = in.readLine();
									content = in.readLine();
									content = in.readLine();
								}
							}
							else{
								content = in.readLine();
								content = in.readLine();
								content = in.readLine();
								content = in.readLine();
								content = in.readLine();
							}
						}
						else{
							content = in.readLine();
							content = in.readLine();
							content = in.readLine();
							content = in.readLine();
							content = in.readLine();
							content = in.readLine();
						}
					}
					//Refresh calendar in general
					//If there is a day with an event or reminder, it will bold the date
					else{
						if(Integer.parseInt(content) == currentYear){
							content = in.readLine();
							if(Integer.parseInt(content) == currentMonth){
								int row = Integer.parseInt(in.readLine());
								int col = Integer.parseInt(in.readLine());
								String name = in.readLine();
								name += "<br>"+in.readLine();
								defCal.setValueAt("<html><b>"+defCal.getValueAt(row,col)+"<b></html>",row,col);
								lblText.setText("<html>"+name+"</html>");
								content = in.readLine();
							}
							else{
								content=null;
							}
						}
						else{
							content=null;
						}
					}

				}
				in.close();
			}

			in.close();
		}
		catch(FileNotFoundException e){
			System.out.println("Error: Cannot open file for reading.");
		}
		catch (EOFException e){
			System.out.println("Error: EOF encountered, file may be corrupt.");
		}
		catch(IOException e){
			System.out.println("Error: Cannot read from file.");
		}


	}
   
	//Method to help rewrite over the file
	//Reads through the file and saves everything into an array.
	public static void reReading(){
    	try{
    	BufferedReader in;
    	in = new BufferedReader(new FileReader("memory.txt"));
    	counting = 0;
    	String content = in.readLine();
    	int i = 0;
    	while(content!=null){
    		info[i]=content;
    		content = in.readLine();
    		i++;
    		//counting variable to know how much the array was filled.
    		counting++;
    	}
    	in.close();
    			
    	}
    	catch(FileNotFoundException e){
    		System.out.println("Error: Cannot open file for reading.");
    	}
    	catch (EOFException e){
    		System.out.println("Error: EOF encountered, file may be corrupt.");
    	}
    	catch(IOException e){
    		System.out.println("Error: Cannot read from file.");
    	}
    	
    	
    }

    public ArrayList readFile(){
		ArrayList<String> fileContent = new ArrayList<>();
		try {
			BufferedReader in;
			in = new BufferedReader(new FileReader("memory.txt"));
			String content = in.readLine();
			while(content != null){
				fileContent.add(content);
				content = in.readLine();
			}
		} catch (FileNotFoundException e){
			System.out.println("File not found.");
		} catch (IOException e ){
			System.out.println("Error with file.");
		}
		return fileContent;
	}

    //Action listeners for buttons
    public void actionPerformed(ActionEvent e){
    	//Going back
    	if(e.getActionCommand().equals("<")){
    		//Check to see if the month is January, if it is, change the current year backward by one
    		if(currentMonth == 0){
    			currentMonth = 11;
    			currentYear -= 1;
    		}
    		else{
    			currentMonth -= 1;
    		}
    		selectedCell = false;
    		changing(currentMonth, currentYear);
    	}
    	//Going forward
    	else if(e.getActionCommand().equals(">")){
    		//Check to see if the month is December, if it is, change the current year forward
    		if(currentMonth == 11){
    			currentMonth = 0;
    			currentYear += 1;
    		}
    		else{
    			currentMonth+=1;
    		}
    		selectedCell = false;
    		changing(currentMonth, currentYear);
			//>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
			//Handles the add task button
		}
    	else if (e.getActionCommand().equals("Add task")){
    		boolean done = false;
			String name = "";
			//Loops until a valid input is inputted
			while(!done) {
				//name of event is input of a jOptionPane
				name = JOptionPane.showInputDialog("Item Name");
				done = true;
				//Makes sure the input isn't cancelled
				if(name != null){
					//makes sure input isn't blank or just space
					if(name.trim().equals("")) {
						JOptionPane.showMessageDialog(null, "Invalid input.");
						done = false;
					}
				}
			}
			if(name != null) {
				//Option dialog with option (low, med, high) for importance of the task
				//Option dialog return 0-2, so add 2 to give 2-4
				String[] option = {"Low","Medium","High"};
				int importance = (JOptionPane.showOptionDialog(null,
					"Pick the importance of the task.",
					"Importance",
					JOptionPane.DEFAULT_OPTION,
					JOptionPane.PLAIN_MESSAGE,
					null,
					option,
					option[0]) + 2);
				//Adds the task to the end of the list
				toDoList.addLast(new Task(name, importance));
			}
			//Refreshes the text area and the combo box
			writeToDo(toDoList);
		} else  if(e.getActionCommand().equals("Give me a task!")){ //Handles giving the user a task
			//If the todo list is not empty, push the highest priority task
			if(toDoList.length > 0) {
				JOptionPane.showMessageDialog(null, "Task for you to do!\n" + toDoList.pushTask().getName());
			} else {
				JOptionPane.showMessageDialog(null, "You have no tasks left to do!");
			}
			//Refresh the text area and comob box
			writeToDo(toDoList);
		}
		else if(e.getActionCommand().equals("timer")){ //Handles the timer ticking every minute for notifications
			    //Reads events file (memory.txt)
				ArrayList<String> fileContent = readFile();
				//Reads each event (each event is 6 lines long)
				for (int i = 0; i < fileContent.size() / 6; i++) {
					//Date is the 5th line in the event
					String dates = fileContent.get(i * 6 + 5);
					//Name is the 4th line in the event
					String name = fileContent.get(i * 6 + 4);
					//If the event contains a 'to', it is an event
					if (dates.contains("to")) {
						int index1 = 0;
						int index2 = 0;
						//Splits the date into the to and from date
						for (int j = 0; j < dates.length(); j++) {
							if (dates.charAt(j) == ':') {
								index1 = j + 2;
								index2 = j + 7;
								break;
							}
						}
						//to date is index 1 to 2 after the colon
						sDate from = sDate.parseString(dates.substring(1, index1 + 1));
						//from date starts 7 indices away from first colon
						sDate to = sDate.parseString(dates.substring(index2));

						//Creates a new event, then checks if it is time to show a notification
						Event evt = new Event(from, to, name);
						if (Notification.pushNotification(evt) != null) { //null means not time
							//Prints the message returned by notification if it is time
							JOptionPane.showMessageDialog(null, Notification.pushNotification(evt));
						}
					}
					else {
						//New reminder with date and name from file
						Reminder remind = new Reminder(sDate.parseString(dates.substring(1)), name);
						if (Notification.pushNotification(remind) != null) { //null means not time
							//Prints message returned by notification if it is time
							JOptionPane.showMessageDialog(null, Notification.pushNotification(remind));
						}
					}
				}
		}
		else if(e.getActionCommand().equals("Remove")){ //Handles removing from the todo list using the combobox
			//Items in combobox are the same order as textarea
			//Removes the item in the index selected in combobox
			toDoList.remove(cbDropdown.getSelectedIndex());
			//Refreshes combo box/textarea
			writeToDo(toDoList);
		}
		//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    	//If the add button is clicked
    	else{
			try{
				//Check to see if there is a value, if there is nothing, you cannot add to an empty cell
				if(defCal.getValueAt(selectedRow, selectedCol)==null){
					JOptionPane.showMessageDialog(pnlCal, "Selected invalid cell.");

				}
				//If the cell is a valid day then keep going
				else{
					//Current day will be the selected cell
					currentDay = (int) defCal.getValueAt(selectedRow, selectedCol);
					Schedule temp = null;
					//Asking user what they would like to add
					String [] choices = {"Event", "Reminder", "Cancel"};
					int choice =(JOptionPane.showOptionDialog(null, "Choose what you would like to add", "Adding", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]));
					//They chose to add an event
					if(choice == 0){
						String name;
						String from;
						String to;
						//Asking for information using JOption Pane
						name = JOptionPane.showInputDialog("Event Name");
						from = JOptionPane.showInputDialog("From: (00:00 format)");
						to = JOptionPane.showInputDialog("To: (00:00 format)");
						//Checking to see if the time that was inputted was valid or not
						if(checkTime(from)&&checkTime(to)){
							sDate sday = new sDate(currentYear, currentMonth+1, currentDay, sDate.gethrs(from), sDate.getmin(from));
							sDate sday1 = new sDate(currentYear, currentMonth+1, currentDay, sDate.gethrs(to), sDate.getmin(to));
							//Creates the dates for that event as well as the event itself.
							temp = new Event (sday, sday1, name);
							//Method that is saving text file into array.
							reReading();
							//Now writing back into the file.
							BufferedWriter out = new BufferedWriter(new FileWriter("memory.txt"));
							//Putting everything from array back into file.
							for(int i = 0; i < counting; i++){
								out.append(info[i]);
								out.newLine();
							}
							//Adding the new information the user gave.
							out.append(Integer.toString(currentYear));
							out.newLine();
							out.append("" + currentMonth);
							out.newLine();
							out.append("" + selectedRow);
							out.newLine();
							out.append("" + selectedCol);
							out.newLine();
							out.append(temp.toString());
							out.newLine();
							out.close();
							defCal.setValueAt("<html><b>"+defCal.getValueAt(selectedRow,selectedCol)+"</html>",selectedRow,selectedCol);
						}
						//If invalid time, message diaolog telling uer.
						else{
							JOptionPane.showMessageDialog(pnlCal, "Invalid Time");
						}
					}
					//Choosing to add a reminder
					else if(choice == 1){
						//Asking for information
						String name = JOptionPane.showInputDialog("Reminder Name");
						String time = JOptionPane.showInputDialog("Time: (00:00 format)");
						//Checking if time is valid.
						if(checkTime(time)){
							sDate sday = new sDate(currentYear, currentMonth+1, currentDay, sDate.gethrs(time), sDate.getmin(time));
							temp = new Reminder(sday, name);
							reReading();
							BufferedWriter out = new BufferedWriter(new FileWriter("memory.txt"));
							//Writing into the text file.
							for(int i = 0; i < counting; i++){
								out.append(info[i]);
								out.newLine();
							}

							out.append(Integer.toString(currentYear));
							out.newLine();
							out.append("" + currentMonth);
							out.newLine();
							out.append("" + selectedRow);
							out.newLine();
							out.append("" + selectedCol);
							out.newLine();
							out.append(temp.toString());
							out.newLine();
							out.close();
							defCal.setValueAt("<html><b>"+defCal.getValueAt(selectedRow,selectedCol)+"</html>",selectedRow,selectedCol);
						}
						else{
							JOptionPane.showMessageDialog(pnlCal, "Invalid Time");
						}

					}

				}
			}

			catch(FileNotFoundException e1){
				System.out.println ("Error: Cannot open file for writing");
			}
			catch (IOException e1){
				System.out.println ("Error: Cannot write to file");
			}
        	
        	}
    	
    }
    
    //Method used to refresh the JTable when going through different months and years.
    public static void changing(int month, int year){
    	
        String [] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        int numDays, chosenMonth;
        lblMonthandYear.setText(months[month] + " " + Integer.toString(year));
        
        //Setting the values into the JTable (the values of the days)
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++){
                defCal.setValueAt(null, i, j);
            }
        }
        
        //Checking whether a month has 30 or 31 days (exception with February)
        if(month == 1){
        	if((year%4)==0){
        		numDays = 29;
        	}
        	else{
        		numDays = 28;
        	}
        }
        else if(month <= 6){
        	if((month%2)==0){
        		numDays = 31;
        	}
        	else{
        		numDays = 30;
        	}
        	
        }
        else if(month > 6){
        	if((month%2)==0){
        		numDays = 30;
        	}
        	else{
        		numDays = 31;
        	}
        }
        else{
        	numDays = 0;
        }
        
        //Using the Gregorian Calendar (standard calendar) to compare
        //Figuring out when the first day starts in the month.
        GregorianCalendar temp = new GregorianCalendar(year,month,1);
        chosenMonth = temp.get(GregorianCalendar.DAY_OF_WEEK);
        System.out.println(chosenMonth);
        
        //Once that day is found, add the rest of the days to the following cells based on number of rows and column.
        for(int i = 1; i <= numDays; i++){
        	int row = new Integer((i+chosenMonth-2)/7);
        	int col = (i+chosenMonth-2)%7;
        	defCal.setValueAt(i, row, col);
        }
        
        //Renderer
        tblCal.setDefaultRenderer(tblCal.getColumnClass(0), new tblCalRenderer());
        //Method that reads the text file and inputs all information that is in there.
        reading();

    }

    //>>>>>>>>>>>>>>>
	//Refreshes the textarea and combobox for todolist
	public static void writeToDo(ToDo list){
    	//Clears combobox
    	cbDropdown.removeAllItems();
    	//If the list is empty, then display a message in the textarea
    	if(list.length == 0){
    		txtTodo.setText("No tasks to do!");
    		return;
		}
    	String output = "";
    	//Iterate through the list, adding each to the text area and the combo box
    	for(int i = 0;i < list.length;i++){
    		output += (((Task)(list.findNode(i)).cargo).getName() + "\n");
    		cbDropdown.addItem(((Task)(list.findNode(i)).cargo).getName());
		}
		//Outputs the text area text
		txtTodo.setText(output);
	}
    
	//Renderer method
    static class tblCalRenderer extends DefaultTableCellRenderer{
 
		private static final long serialVersionUID = 1L;

		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col){
    	super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    	if(hasFocus){
    		//If the cell is focused, change the cell color
    		setBackground(Color.cyan);
    		//Variable to tell us which row and column is selected
    		selectedRow = table.getSelectedRow();
    		selectedCol = table.getSelectedColumn();
    		selectedCell = true;
    		lblText.setText("empty");
    		//Checking if there is any information for that cell
    		reading();
    		
    	}
    	else if(isSelected){
    		setBackground(table.getBackground());
    	}
    	else{
    		setBackground(table.getBackground());
    	}

    	return this;
    	}
    }
    
    //Method to check if time that was inputted is valid
	public static boolean checkTime(String time){
		try{
			if(sDate.gethrs(time)>24||sDate.gethrs(time)<0){
				return false;
			}
			else{
				if(sDate.getmin(time)>59||sDate.getmin(time)<0){
					return false;
				}
				else{
					return true;
				}
			}

		}
		catch(Exception e1){
			return false;
		}
	}

	
    public static void main (String [] args){
        GUICalendar hello = new GUICalendar();
    }


}
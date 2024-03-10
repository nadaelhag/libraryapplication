import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.*;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.*;

@SuppressWarnings("serial")
public class SearchBorrow extends JFrame{

	private JTable table;
	private JTextField searchField;
	private int item;
	private static int user;
	int lastSelectedRow = -1;
	boolean dialogShown = false;
	private final String[] column = {"Title", "Region", "Emirates", "ItemID"};

	public static void main(String[] args) {
		ReservationCleanup.main(new String[] {});
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchBorrow window = new SearchBorrow(user);
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static String capitalizeWords(String input) {
	    String[] words = input.split(" ");
	    
	    StringBuilder output = new StringBuilder();
	    for (String word : words) {
	        if (!word.isEmpty()) {
	            output.append(Character.toUpperCase(word.charAt(0)));
	            if (word.length() > 1) {
	                output.append(word.substring(1));
	            }
	            output.append(" ");
	        }
	    }
	    
	    return output.toString().trim();
	}

	public SearchBorrow(int libraryID) {
		initialize(libraryID);
	}
	

	public static String[][] saveitems() {
		String[][] sort = null;
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", " ");
        String query = "SELECT title, region, emirates, itemID FROM items";
        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, 
        		ResultSet.CONCUR_UPDATABLE);
        ResultSet rs = stmt.executeQuery();
        int numRows = 0;
        while (rs.next()) {
            numRows++;
        }
        rs.beforeFirst(); 
        int numCols = rs.getMetaData().getColumnCount();
        sort = new String[numRows][numCols];
        int i = 0;
        while (rs.next()) {
            for (int j = 1; j <= numCols; j++) {
                sort[i][j-1] = rs.getString(j);
            }
            i++;
        }
    } catch (SQLException e) {
        System.err.println("Error retrieving data from database: " + e.getMessage());
    }
	return sort; 
    }
	

	private void initialize(int libraryID) {
		getContentPane().setBackground(new Color(255, 255, 255));
		setBackground(new Color(255, 255, 255));
		getContentPane().setFont(new Font("Georgia", Font.PLAIN, 16));
		setBounds(100, 100, 1002, 602);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Georgia", Font.PLAIN, 13));
		scrollPane.setBackground(new Color(255, 255, 255));
		scrollPane.setBounds(10, 225, 968, 332);
		getContentPane().add(scrollPane);
		
		//setting row equal to the database table items
		String row[][]= saveitems() ;
		
		//table display
		DefaultTableModel model1 = new DefaultTableModel(row, column);
		table = new JTable();
		table.setShowVerticalLines(false);
		table.setRowHeight(25);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setGridColor(new Color(255, 255, 255));
		table.setFont(new Font("Georgia", Font.PLAIN, 13));
		table.setSelectionBackground(new Color(235, 241, 103));
		table.getTableHeader().setFont(new Font("Georgia", Font.PLAIN, 13));
		table.getTableHeader().setOpaque(true); 
		table.getTableHeader().setBackground(new Color(116, 107, 222));  //this is the background color
		table.getTableHeader().setForeground(new Color(255, 255, 255));
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionBackground(new Color(255, 255, 153));
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
	        public Component getTableCellRendererComponent(JTable table, 
	                Object value, boolean isSelected, boolean hasFocus,
	                int row, int column) {
	        	setHorizontalAlignment(JLabel.CENTER);
	            Component c = super.getTableCellRendererComponent(table, 
	                value, isSelected, hasFocus, row, column);
	            if (isSelected) {
	            	   c.setBackground(table.getSelectionBackground());
	            	} else {
	            	   c.setBackground(row % 2 == 0 ? Color.white : new Color(205, 205, 205));
	            	}
	            return c;
	        };
	    });
		
		//to display item details
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting() && table.getSelectedRow() != -1) {
		            //int selectedRow = table.getSelectedRow();
		            int option = JOptionPane.showConfirmDialog(null, "Would you like to see further details on the selected item?", "Item Details", JOptionPane.YES_NO_OPTION);
		            if (option == JOptionPane.YES_OPTION) {
		            	Object value = table.getValueAt(table.getSelectedRow(), 3);
		                item = Integer.parseInt(value.toString());
		            	itemsDetails i = new itemsDetails(item);
						i.setVisible(true);
						i.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		            }
		        }
		    }
		});

		table.setModel(model1);
		scrollPane.setViewportView(table);
		
		searchField = new JTextField();
		searchField.setFont(new Font("Georgia", Font.PLAIN, 16));
		searchField.setBounds(10, 178, 715, 36);
		getContentPane().add(searchField);
		searchField.setColumns(10);
		
		JRadioButton titleRadioBtn = new JRadioButton("Title");
		titleRadioBtn.setBackground(new Color(255, 255, 255));
		titleRadioBtn.setFont(new Font("Georgia", Font.PLAIN, 16));
		titleRadioBtn.setBounds(178, 141, 72, 23);
		getContentPane().add(titleRadioBtn);
		
		JRadioButton authorRadioBtn = new JRadioButton("Author");
		authorRadioBtn.setBackground(new Color(255, 255, 255));
		authorRadioBtn.setFont(new Font("Georgia", Font.PLAIN, 16));
		authorRadioBtn.setBounds(84, 141, 82, 23);
		getContentPane().add(authorRadioBtn);
		
		JRadioButton typeRadioBtn = new JRadioButton("Type");
		typeRadioBtn.setBackground(new Color(255, 255, 255));
		typeRadioBtn.setFont(new Font("Georgia", Font.PLAIN, 16));
		typeRadioBtn.setBounds(10, 141, 72, 23);
		getContentPane().add(typeRadioBtn);
		
		JRadioButton yearRadioBtn = new JRadioButton("Year");
		yearRadioBtn.setBackground(new Color(255, 255, 255));
		yearRadioBtn.setFont(new Font("Georgia", Font.PLAIN, 16));
		yearRadioBtn.setBounds(252, 141, 59, 23);
		getContentPane().add(yearRadioBtn);
		
		//group buttons so only one can be chosen 
		ButtonGroup radioBtnGroup = new ButtonGroup();
		radioBtnGroup.add(titleRadioBtn);
		radioBtnGroup.add(authorRadioBtn);
		radioBtnGroup.add(typeRadioBtn);
		radioBtnGroup.add(yearRadioBtn);
		
		JButton searchBTN = new JButton("Search");
		searchBTN.setForeground(new Color(255, 255, 255));
		searchBTN.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		searchBTN.setBackground(new Color(133, 138, 151));
		searchBTN.setFont(new Font("Georgia", Font.PLAIN, 16));
		searchBTN.setBounds(735, 178, 243, 36);
		getContentPane().add(searchBTN);
		searchBTN.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchTerm = searchField.getText().trim();
			    if (searchTerm.isEmpty()) {
			    	//JOptionPane.showMessageDialog(null,"Please enter a title, author, type, or year in the search box.");
		            searchField.requestFocus();
			    }
			    String searchColumn;
			    if (typeRadioBtn.isSelected()) {
			      searchColumn = "type";
			    } else if (titleRadioBtn.isSelected()) {
			      searchColumn = "title";
			    } else if (authorRadioBtn.isSelected()) {
			      searchColumn = "author";
			    } else if (yearRadioBtn.isSelected()) {
			      searchColumn = "year";
			    } else {
			      return;
			    }
			    try {
			        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", " ");
			        String query = "SELECT * FROM items WHERE " + searchColumn + " LIKE ?";
			        PreparedStatement stmt = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_SENSITIVE, 
			        		ResultSet.CONCUR_UPDATABLE);
			        if (searchColumn == "type" || searchColumn == "title" || searchColumn == "author") {
			        	stmt.setString(1, "%" + capitalizeWords(searchTerm) + "%");
			        }
			        else {
			        	stmt.setString(1, "%" + searchTerm + "%");
			        }
			        
			        ResultSet rs = stmt.executeQuery();
			        
		            model1.setRowCount(0);
		            while (rs.next()) {
		              String title = rs.getString("title");
		              String region = rs.getString("region");
		              String emirates = rs.getString("emirates");
		              String id = rs.getString("itemID");
		              Object[] row = {title, region, emirates, id};
		              model1.addRow(row);}
			    } catch (SQLException f) {
			        System.err.println("Error retrieving data from database: " + f.getMessage());
			    }
			}});
		
		//borrow interface
		JButton borrow = new JButton("Borrow");
		borrow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				display b = new display(libraryID);
				b.setVisible(true);
				b.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		borrow.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		borrow.setForeground(new Color(255, 255, 255));
		borrow.setBackground(new Color(133, 138, 151));
		borrow.setFont(new Font("Georgia", Font.PLAIN, 16));
		borrow.setBounds(31, 41, 135, 49);
		getContentPane().add(borrow);
		
		//reserve interface
		JButton reserve = new JButton("Extend");
		reserve.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		reserve.setForeground(new Color(255, 255, 255));
		reserve.setBackground(new Color(133, 138, 151));
		reserve.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				extend e6 = new extend(libraryID);
				e6.setVisible(true);
				e6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		reserve.setFont(new Font("Georgia", Font.PLAIN, 16));
		reserve.setBounds(222, 41, 135, 49);
		getContentPane().add(reserve);
		
		//request an item and send to admin
		JButton notifications = new JButton("Request");
		notifications.setForeground(new Color(255, 255, 255));
		notifications.setBackground(new Color(133, 138, 151));
		notifications.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		notifications.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				request r = new request(libraryID);
				r.setVisible(true);
				r.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		notifications.setFont(new Font("Georgia", Font.PLAIN, 16));
		notifications.setBounds(424, 41, 135, 49);
		getContentPane().add(notifications);
		
		//transactions of the current user
		JButton transactions = new JButton("Transactions");
		transactions.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		transactions.setBackground(new Color(133, 138, 151));
		transactions.setForeground(new Color(255, 255, 255));
		transactions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				transactions t = new transactions(libraryID);
				t.setVisible(true);
				t.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		transactions.setFont(new Font("Georgia", Font.PLAIN, 16));
		transactions.setBounds(624, 41, 135, 49);
		getContentPane().add(transactions);
		
		//logout
		JButton logout = new JButton("Logout");
		logout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				login i = new login ();
				i.setVisible(true);
				dispose();
			}
		});
		logout.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		logout.setForeground(new Color(255, 255, 255));
		logout.setBackground(new Color(133, 138, 151));
		logout.setFont(new Font("Georgia", Font.PLAIN, 16));
		logout.setBounds(815, 41, 135, 49);
		getContentPane().add(logout);
		
		JLabel lblNewLabel = new JLabel("Choose the keyword and enter the word:");
		lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblNewLabel.setBounds(10, 104, 336, 30);
		getContentPane().add(lblNewLabel);
		
	}
}

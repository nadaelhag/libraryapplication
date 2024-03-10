import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.*;

import javax.mail.MessagingException;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.mail.EmailException;


public class requests extends JFrame {
	private static final long serialVersionUID = 1L;
	private JTable table;
    private final String[] column = {"Type","Title", "Author","Year", "Value","Region", "Emirates"};
    
    public static void main(String[] args) {
        requests req = new requests();
        req.setVisible(true);
    }
    
    public static String[][] saveitems() {
		String[][] sort = null;
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", " ");
        String query = "SELECT type, title, author, year, value, region, emirates FROM requests";
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
    
    public requests() {
		initialize();
	}
    
	@SuppressWarnings("serial")
	private void initialize() {
    getContentPane().setBackground(new Color(255, 255, 255));
	setBackground(new Color(255, 255, 255));
	getContentPane().setFont(new Font("Georgia", Font.PLAIN, 16));
	setBounds(100, 100, 1002, 386);
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	getContentPane().setLayout(null);
	setLocationRelativeTo(null);
	
	JScrollPane scrollPane = new JScrollPane();
	scrollPane.setFont(new Font("Georgia", Font.PLAIN, 13));
	scrollPane.setBackground(new Color(255, 255, 255));
	scrollPane.setBounds(10, 11, 968, 332);
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
	            int selectedRow = table.getSelectedRow();
	            String type = table.getValueAt(selectedRow, 0).toString();
	            String title = table.getValueAt(selectedRow, 1).toString();
	            String author = table.getValueAt(selectedRow, 2).toString();
	            Object yearObj = table.getValueAt(selectedRow, 3);
	            int year = Integer.parseInt(yearObj.toString());
	            Object valueObj1 = table.getValueAt(selectedRow, 4);
	            int value = Integer.parseInt(yearObj.toString());
				String region = table.getValueAt(selectedRow, 5).toString();
				String emirates = table.getValueAt(selectedRow, 6).toString();
	            
	            String email = "";
	            String subject = "";
	            String body = "";
	            int libraryID = 0;
	            int option = JOptionPane.showConfirmDialog(null, "Would you like to accept or reject this request?", "Item Details", JOptionPane.YES_NO_OPTION);
	            if (option== JOptionPane.YES_OPTION) {
                    // User clicked "Accept"
                    String message = "Request accepted";
                    JOptionPane.showMessageDialog(requests.this, message, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    //DefaultTableModel model = (DefaultTableModel) table.getModel();
                    //model.removeRow(selectedRow);

                    // Append a new record to the "transactions" table in the database
                    try {
                    	Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", " ");
                        PreparedStatement selectStmt = conn.prepareStatement("SELECT libraryID FROM requests WHERE title = ? AND region = ? AND emirates = ?");
                        selectStmt.setString(1, title);
                        selectStmt.setString(2, region);
                        selectStmt.setString(3, emirates);

                        ResultSet rs = selectStmt.executeQuery();
                        if (rs.next()) {
                            libraryID = rs.getInt("libraryID");
                         } 
                        
                        //add to items
                        PreparedStatement stmt = conn.prepareStatement("INSERT INTO items (type, title, author, year, value, region, emirates) VALUES (?, ?, ?, ?, ?, ? , ?)");
                        stmt.setString(1, type);
                        stmt.setString(2, title);
                        stmt.setString(3, author);
                        stmt.setInt(4, year);
                        stmt.setInt(5, value);
                        stmt.setString(6,  region);
                        stmt.setString(7,  emirates);
                        stmt.executeUpdate();
                        
                        //add to transactions table
                        PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO transactions (userID, transaction) VALUES (?, ?)");
                        stmt1.setInt(1, libraryID);
                        stmt1.setString(2, "Your request for item \""+title+"\" has been accepted");
                        stmt1.executeUpdate();
                        
                        //delete from requests
                        PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM requests WHERE libraryID = ? AND title = ? AND region = ? AND emirates = ?");
	                    deleteStmt.setInt(1, libraryID);
	                    deleteStmt.setString(1, title);
	                    deleteStmt.setString(1, region);
	                    deleteStmt.setString(1, emirates);
	                    deleteStmt.executeUpdate();
                        
                      //find email
    				    String query = "SELECT email FROM users WHERE libraryID = ?";
    		            PreparedStatement statement = conn.prepareStatement(query);
    		            statement.setInt(1, libraryID);
    		            ResultSet resultSet = statement.executeQuery();
    		            if (resultSet.next()) {
    		                 email = resultSet.getString("email");
    		            } 
    		            //send message
    				    try {
    				    	body = "Request Status";
    				    	subject = "Your request for item \""+title+"\" has been accepted";
    			            sendEmail.send(email, subject, body);
    			        } catch (EmailException | MessagingException e3) {
    			            e3.printStackTrace();
    			        }
                    } catch(Exception ex) {
                        ex.printStackTrace();
                    }
                } else if (option == JOptionPane.NO_OPTION) {
                    // User clicked "Reject"
                    String message = "Request rejected";
                    JOptionPane.showMessageDialog(requests.this, message, "Confirmation", JOptionPane.INFORMATION_MESSAGE);
                    
                    Connection conn;
					try {
						conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", " ");
	                    PreparedStatement selectStmt = conn.prepareStatement("SELECT libraryID FROM requests WHERE title = ? AND region = ? AND emirates = ?");
	                    selectStmt.setString(1, title);
	                    selectStmt.setString(2, region);
	                    selectStmt.setString(3, emirates);

	                    ResultSet rs = selectStmt.executeQuery();
	                    if (rs.next()) {
	                        libraryID = rs.getInt("libraryID");
	                     } 
	                    
	                    //delete from requests
	                    PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM requests WHERE libraryID = ? AND title = ? AND region = ? AND emirates = ?");
	                    deleteStmt.setInt(1, libraryID);
	                    deleteStmt.setString(1, title);
	                    deleteStmt.setString(1, region);
	                    deleteStmt.setString(1, emirates);
	                    deleteStmt.executeUpdate();
	                    
	                  //add to transactions table
                        PreparedStatement stmt1 = conn.prepareStatement("INSERT INTO transactions (userID, transaction) VALUES (?, ?)");
                        stmt1.setInt(1, libraryID);
                        stmt1.setString(2, "Your request for "+type+" \""+title+"\" has been rejected");
                        stmt1.executeUpdate();
	                    
	                  //find email
					    String query = "SELECT email FROM users WHERE libraryID = ?";
			            PreparedStatement statement = conn.prepareStatement(query);
			            statement.setInt(1, libraryID);
			            ResultSet resultSet = statement.executeQuery();
			            if (resultSet.next()) {
			                 email = resultSet.getString("email");
			            } 
	                    try {
					    	body = "Request Status";
					    	subject = "Your request for "+type+" \""+title+"\" has been rejected";
				            sendEmail.send(email, subject, body);
				        } catch (EmailException | MessagingException e3) {
				            e3.printStackTrace();
				        }
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                } else {

                    table.clearSelection();
                    table.setSelectionBackground(Color.WHITE);
                }

	            }
	        }
	    
	});

	table.setModel(model1);
	scrollPane.setViewportView(table);}
    }
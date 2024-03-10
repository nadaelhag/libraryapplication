import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.mail.EmailException;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.mail.MessagingException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

public class BorrowItem extends JFrame {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static int user;
	private JTextField itemIDField;
	private JTextField title;
	private JLabel lblNewLabel_1;
	private JTextField branch;
	private JLabel lblNewLabel_4;
	private JTextField period;
	private JButton btnCheckAvailability;
	private JLabel lblNewLabel_5;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
    String dateStr = now.format(formatter);
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BorrowItem frame = new BorrowItem(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public BorrowItem(int libraryID) {
		setBackground(new Color(255, 255, 255));
		initialize(libraryID);
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
	
	public static int value(int itemID) {
		int itemValue = 0;
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", "");
			PreparedStatement stmt = conn.prepareStatement("SELECT value FROM items WHERE itemID = ?");
			stmt.setInt(1, itemID);
			ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	             itemValue = rs.getInt("value");
	        }
			
		}catch (SQLException e4) {
		    e4.printStackTrace();
		}
		return itemValue;
	}
	
	public static String avail(int itemID) {
		String avail = "";
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", "");
			PreparedStatement stmt = conn.prepareStatement("SELECT available FROM items WHERE itemID = ?");
			stmt.setInt(1, itemID);
			ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	             avail = rs.getString("available");
	        }
			
		}catch (SQLException e4) {
		    e4.printStackTrace();
		}
		return avail;
	}
	
	public static String reserved(int itemID) {
		String reserve = "";
		try {
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", "");
			PreparedStatement stmt = conn.prepareStatement("SELECT reserved FROM items WHERE itemID = ?");
			stmt.setInt(1, itemID);
			ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	             reserve = rs.getString("reserved");
	        }
			
		}catch (SQLException e4) {
		    e4.printStackTrace();
		}
		return reserve;
	}
	
	

	private void initialize(int libraryID) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 559, 353);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Item ID:");
		lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 46, 61, 42);
		contentPane.add(lblNewLabel);
		
		itemIDField = new JTextField();
		itemIDField.setFont(new Font("Georgia", Font.PLAIN, 16));
		itemIDField.setColumns(10);
		itemIDField.setBackground(Color.LIGHT_GRAY);
		itemIDField.setBounds(81, 47, 448, 42);
		contentPane.add(itemIDField);
		
		JLabel lblNewLabel_2 = new JLabel("Title:");
		lblNewLabel_2.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(20, 99, 49, 42);
		contentPane.add(lblNewLabel_2);
		
		title = new JTextField();
		title.setFont(new Font("Georgia", Font.PLAIN, 16));
		title.setColumns(10);
		title.setBackground(Color.LIGHT_GRAY);
		title.setBounds(81, 99, 448, 43);
		contentPane.add(title);
		
		JLabel lblNewLabel_3 = new JLabel("days");
		lblNewLabel_3.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(357, 201, 49, 42);
		contentPane.add(lblNewLabel_3);
		
		lblNewLabel_1 = new JLabel("Branch:");
		lblNewLabel_1.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(16, 148, 68, 42);
		contentPane.add(lblNewLabel_1);
		
		branch = new JTextField();
		branch.setFont(new Font("Georgia", Font.PLAIN, 16));
		branch.setColumns(10);
		branch.setBackground(Color.LIGHT_GRAY);
		branch.setBounds(81, 148, 448, 43);
		contentPane.add(branch);
		
		lblNewLabel_4 = new JLabel("Borrowing Period:");
		lblNewLabel_4.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(10, 201, 132, 42);
		contentPane.add(lblNewLabel_4);
		
		period = new JTextField();
		period.setFont(new Font("Georgia", Font.PLAIN, 16));
		period.setColumns(10);
		period.setBackground(Color.LIGHT_GRAY);
		period.setBounds(147, 201, 200, 43);
		contentPane.add(period);
		
		btnCheckAvailability = new JButton("Check Availability");
		btnCheckAvailability.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int itemID = Integer.parseInt(itemIDField.getText().trim()); 
				String title1 = capitalizeWords(title.getText());
				String branch1 = capitalizeWords(branch.getText()); 
				int days = Integer.parseInt(period.getText());
				int max = 0;
				int check = 0;
				int value = 0;
				String avail = "";
				String reserve = "";
				String email = "";
				
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", "");
					PreparedStatement stmt = conn.prepareStatement("SELECT * FROM items WHERE itemID = ? AND title = ? AND region = ?");
					stmt.setInt(1, itemID);
					stmt.setString(2, title1);
					stmt.setString(3, branch1);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
					     value = value(itemID);
					     avail = avail(itemID);
					     reserve = reserved(itemID);
					    
					    if (avail.equals("No") || reserve.equals("Yes")) {
					    	JOptionPane.showMessageDialog(null, "The item is currently unavailable."); //would you like to be notified when it becomes available
					    }
					    else {
					    	if( value >= 500 ) {
					    		JOptionPane.showMessageDialog(null, "As per library regulations, this item can only be consulted during library hours."); 
					    	}
					    	else if (value>=250 && value<500){
					    		max = 3;
					    	}
					    	else {
					    		max = 30;
					    	}
					    }
					} else {
						JOptionPane.showMessageDialog(null, "Please make sure all entered details are correct");
					}
					
				if (max == 3) {
					if (days > 3) {
						JOptionPane.showMessageDialog(null, "According to library regulations, this item can only borrowed for a period of 3 days.");
					}
					else {
						check = 1;
					}
				}
				else if (max == 30) {
					if (days > 30) {
						JOptionPane.showMessageDialog(null, "According to library regulations, this item can only borrowed for a period of 30 days.");
					}
					else {
						check = 1;
					}
				}
				
				if(check == 1) {
					//change available status
					PreparedStatement updateStmt = conn
							.prepareStatement("UPDATE items SET available = 'No' WHERE itemID = ?");
					updateStmt.setInt(1, itemID);
					updateStmt.executeUpdate();
					
					//add to borrow table
					PreparedStatement insertStmt = conn.prepareStatement(
						    "INSERT INTO borrow (itemID, userID, days, date_created, duedate) VALUES (?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL ? DAY))"
						);
						insertStmt.setInt(1, itemID);
						insertStmt.setInt(2, libraryID);
						insertStmt.setInt(3, days);
						insertStmt.setInt(4, days);
						insertStmt.executeUpdate();
	               
					//show message
					JOptionPane.showMessageDialog(null, "You have successfully borrowed this item.");
					dispose();
					
					//put in transactions table
					String insertQuery = "INSERT INTO transactions (userId, transaction) VALUES (?, ?)";
				    PreparedStatement stmt1 = conn.prepareStatement(insertQuery);
				    stmt1.setInt(1, libraryID); // 
				    stmt1.setString(2, "Item \""+title1+"\" was borrowed on "+dateStr); 
				    stmt1.executeUpdate(); 
				    
				    //send confirmation message
				    String subject = "Confirmation message";
				    String body = "You have successfully borrowed item #"+itemID+"\n"+
				    		"ItemID: #"+itemID+"\n"
			        		+"Title: \""+title1+"\"\n"
			        		+"Value: "+value+" AED"+"\n"
			        		+"Branch: "+branch1+"\n"
			        +"Borrowing period: "+days+" days\n";
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
			            sendEmail.send(email, subject, body);
			        } catch (EmailException | MessagingException e3) {
			            e3.printStackTrace();
			        }
				}
				}catch (SQLException e4) {
				    e4.printStackTrace();
				}
			}
		});
		btnCheckAvailability.setForeground(Color.WHITE);
		btnCheckAvailability.setFont(new Font("Georgia", Font.PLAIN, 16));
		btnCheckAvailability.setBackground(new Color(116, 107, 222));
		btnCheckAvailability.setBounds(194, 270, 164, 35);
		contentPane.add(btnCheckAvailability);
		
		lblNewLabel_5 = new JLabel("Please enter the item details");
		lblNewLabel_5.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblNewLabel_5.setBounds(10, 0, 251, 42);
		contentPane.add(lblNewLabel_5);
	}
}

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

@SuppressWarnings("serial")
public class BookItem extends JFrame {

	private JPanel contentPane;
	private static int user;
	private JButton check;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
    String dateStr = now.format(formatter);
    String title, author, region, emirates;
    int year;
    double value;
    private JLabel lblNewLabel_4;
    private JLabel lblNewLabel_1;
    private JTextField itemIDField;
    private JLabel lblNewLabel_2;
    private JTextField title12;
    private JLabel lblNewLabel_3;
    private JTextField branch12;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					BookItem frame = new BookItem(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public BookItem(int libraryID) {
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
		setTitle("Reserve");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 557, 295);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		itemIDField = new JTextField();
		itemIDField.setFont(new Font("Georgia", Font.PLAIN, 16));
		itemIDField.setColumns(10);
		itemIDField.setBackground(Color.LIGHT_GRAY);
		itemIDField.setBounds(81, 38, 448, 42);
		contentPane.add(itemIDField);
		
		check = new JButton("Check Availability");
		check.setForeground(new Color(255, 255, 255));
		check.setBackground(new Color(116, 107, 222));
		check.setFont(new Font("Georgia", Font.PLAIN, 18));
		check.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int itemID = Integer.parseInt(itemIDField.getText().trim()); 
				String title1 = capitalizeWords(title12.getText());
				String branch1 = capitalizeWords(branch12.getText());
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
				     avail = avail(itemID);
				     reserve = reserved(itemID);
				    
				    if (avail.equals("No") || reserve.equals("Yes")) {
				    	JOptionPane.showMessageDialog(null, "The item is currently unavailable."); //would you like to be notified when it becomes available
				    }
				    else {
				    	PreparedStatement updateStmt = conn
								.prepareStatement("UPDATE items SET reserved = 'Yes' WHERE itemID = ?");
						updateStmt.setInt(1, itemID);
						updateStmt.executeUpdate();
						
						//add to borrow table
						PreparedStatement insertStmt = conn.prepareStatement(
							    "INSERT INTO reserve (itemID, userID, days, date_created, duedate) VALUES (?, ?, ?, NOW(), DATE_ADD(NOW(), INTERVAL ? DAY))"
							);
							insertStmt.setInt(1, itemID);
							insertStmt.setInt(2, libraryID);
							insertStmt.setInt(3, 3);
							insertStmt.setInt(4, 3);
							insertStmt.executeUpdate();
		               
						//show message
						JOptionPane.showMessageDialog(null, "You have successfully booked this item.");
						dispose();
						
						//put in transactions table
						String insertQuery = "INSERT INTO transactions (userID, transaction) VALUES (?, ?)";
					    PreparedStatement stmt1 = conn.prepareStatement(insertQuery);
					    stmt1.setInt(1, libraryID); // 
					    stmt1.setString(2, "Item \""+title1+"\" was booked on "+dateStr); 
					    stmt1.executeUpdate(); 
					    
					    //send confirmation message
					    String subject = "Confirmation message";
					    String body = "You have successfully booked item #"+itemID+"\n"+
					    		"ItemID: #"+itemID+"\n"
				        		+"Title: \""+title1+"\"\n"
				        		+"Branch: "+branch1+"\n"
				        +"Booking period: 3 days\n";
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
				} else {
					JOptionPane.showMessageDialog(null, "Please make sure all entered details are correct");
				}
				}catch (SQLException e4) {
				    e4.printStackTrace();
				}
			}
		});
		check.setBounds(184, 209, 185, 38);
		contentPane.add(check);
		
		lblNewLabel_4 = new JLabel("Please enter the item details");
		lblNewLabel_4.setFont(new Font("Georgia", Font.PLAIN, 18));
		lblNewLabel_4.setBounds(10, 0, 251, 42);
		contentPane.add(lblNewLabel_4);
		
		lblNewLabel_1 = new JLabel("Item ID:");
		lblNewLabel_1.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 37, 61, 42);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("Title:");
		lblNewLabel_2.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(20, 90, 49, 42);
		contentPane.add(lblNewLabel_2);
		
		title12 = new JTextField();
		title12.setFont(new Font("Georgia", Font.PLAIN, 16));
		title12.setColumns(10);
		title12.setBackground(Color.LIGHT_GRAY);
		title12.setBounds(81, 90, 448, 43);
		contentPane.add(title12);
		
		lblNewLabel_3 = new JLabel("Branch:");
		lblNewLabel_3.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(16, 139, 68, 42);
		contentPane.add(lblNewLabel_3);
		
		branch12 = new JTextField();
		branch12.setFont(new Font("Georgia", Font.PLAIN, 16));
		branch12.setColumns(10);
		branch12.setBackground(Color.LIGHT_GRAY);
		branch12.setBounds(81, 139, 448, 43);
		contentPane.add(branch12);
		
	}
}

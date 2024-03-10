import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.mail.EmailException;

import java.awt.Color;
import javax.swing.JLabel;
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
public class returns extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
    String dateStr = now.format(formatter);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					returns frame = new returns();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public returns() {
		setBackground(new Color(255, 255, 255));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 182);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblEnterItemId = new JLabel("Enter item ID:");
		lblEnterItemId.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblEnterItemId.setBounds(10, 21, 103, 42);
		contentPane.add(lblEnterItemId);
		
		textField = new JTextField();
		textField.setFont(new Font("Georgia", Font.PLAIN, 16));
		textField.setColumns(10);
		textField.setBackground(Color.LIGHT_GRAY);
		textField.setBounds(123, 22, 290, 42);
		contentPane.add(textField);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int itemID = Integer.parseInt(textField.getText().trim());
				int userID = 0;
				String email = "";
				String subject = "";
				String body = "";
				try {
					Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", "");
					 PreparedStatement stmt = conn.prepareStatement("SELECT userID FROM borrow WHERE itemID = ?");
				        stmt.setInt(1, itemID);
				        ResultSet rs = stmt.executeQuery();
				        if (rs.next()) {
				          userID = rs.getInt("userID");
				        }
				        PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM borrow WHERE itemID = ?");
				        deleteStmt.setInt(1, itemID);
				        deleteStmt.executeUpdate();
				        PreparedStatement updateStmt = conn.prepareStatement("UPDATE items SET available = 'Yes' WHERE itemID = ?");
				        updateStmt.setInt(1, itemID);
				        updateStmt.executeUpdate();
				        
				      //put in transactions table
						String insertQuery = "INSERT INTO transactions (userId, transaction) VALUES (?, ?)";
					    PreparedStatement stmt1 = conn.prepareStatement(insertQuery);
					    stmt1.setInt(1, userID); // 
					    stmt1.setString(2, "Item #"+itemID+" was returned on "+dateStr); 
					    stmt1.executeUpdate(); 
					    
					    //send email
				        String query = "SELECT email FROM users WHERE libraryID = ?";
			            PreparedStatement statement = conn.prepareStatement(query);
			            statement.setInt(1, userID);
			            ResultSet resultSet = statement.executeQuery();
			            if (resultSet.next()) {
			                 email = resultSet.getString("email");
			            } 
			            //send message
					    try {
					    	subject = "Confirmation Message";
					    	body = "You have successfully returned item #"+itemID+"\nThank you for reading with us!";
				            sendEmail.send(email, subject, body);
				        } catch (EmailException | MessagingException e3) {
				            e3.printStackTrace();
				        }
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		btnEnter.setForeground(Color.WHITE);
		btnEnter.setFont(new Font("Georgia", Font.PLAIN, 16));
		btnEnter.setBackground(new Color(116, 107, 222));
		btnEnter.setBounds(137, 99, 164, 35);
		contentPane.add(btnEnter);
	}
}

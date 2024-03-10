import java.awt.EventQueue;

import javax.mail.MessagingException;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.mail.EmailException;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class extend extends JFrame {

	private JPanel contentPane;
	private JTextField itemID;
	private static int user;
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	LocalDateTime now = LocalDateTime.now();
    String dateStr = now.format(formatter);

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					extend frame = new extend(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public extend(int libraryID) {
		initialize(libraryID);
	}

	private void initialize(int libraryID) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 654, 212);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_4 = new JLabel("Is this a borrowing or booking extension?");
		lblNewLabel_4.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(10, 13, 305, 31);
		contentPane.add(lblNewLabel_4);
		
		JRadioButton borrowing = new JRadioButton("Borrowing");
		borrowing.setBackground(new Color(255, 255, 255));
		borrowing.setFont(new Font("Georgia", Font.PLAIN, 16));
		borrowing.setBounds(320, 17, 111, 23);
		contentPane.add(borrowing);
		
		JRadioButton booking = new JRadioButton("Booking");
		booking.setBackground(new Color(255, 255, 255));
		booking.setFont(new Font("Georgia", Font.PLAIN, 16));
		booking.setBounds(484, 17, 111, 23);
		contentPane.add(booking);
		
		ButtonGroup radioBtnGroup = new ButtonGroup();
		radioBtnGroup.add(borrowing);
		radioBtnGroup.add(booking);
		
		JLabel lblNewLabel_4_1 = new JLabel("Please enter the item ID:");
		lblNewLabel_4_1.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_4_1.setBounds(10, 58, 195, 43);
		contentPane.add(lblNewLabel_4_1);
		
		itemID = new JTextField();
		itemID.setFont(new Font("Georgia", Font.PLAIN, 16));
		itemID.setColumns(10);
		itemID.setBackground(Color.LIGHT_GRAY);
		itemID.setBounds(201, 58, 433, 43);
		contentPane.add(itemID);
		
		JButton btnEnter = new JButton("Enter");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(borrowing.isSelected()) {
					try {
						int item = Integer.parseInt(itemID.getText().trim());
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", "");
						PreparedStatement stmt = conn.prepareStatement("SELECT * FROM borrow WHERE userID = ? AND itemID = ?");
				        stmt.setInt(1, libraryID);
				        stmt.setInt(2, item);

				        ResultSet rs = stmt.executeQuery();
				        if (rs.next()) {
				            boolean isExtended = rs.getBoolean("extended");
				            Timestamp dateCreated = rs.getTimestamp("date_created");
				            long currentTime = System.currentTimeMillis();

				            if (!isExtended && (currentTime - dateCreated.getTime()) >= (3 * 24 * 60 * 60 * 1000)) {
				            	PreparedStatement updateStmt = conn.prepareStatement("UPDATE borrow SET extended = 1, days = 3, date_created = now(), duedate = DATE_ADD(NOW(), INTERVAL ? DAY) WHERE userID = ? AND itemID = ?");
				            	updateStmt.setInt(1, 3);
				            	updateStmt.setInt(2, libraryID);
				            	updateStmt.setInt(3, item);
				            	updateStmt.executeUpdate();
				                JOptionPane.showMessageDialog(null,"Reservation extended successfully.");
				                
				                //save in transactions history
				                String insertQuery = "INSERT INTO transactions (userId, transaction) VALUES (?, ?)";
							    PreparedStatement stmt1 = conn.prepareStatement(insertQuery);
							    stmt1.setInt(1, libraryID); 
							    stmt1.setString(2, "You extended your reservation of item #"+item+" on "+dateStr); 
							    stmt1.executeUpdate();
							    
							  //email information
							    String email = null;
							    String subject = "Successful extension";
						        String body = "You extended your reservation of item #"+item;
						        
						        //to send an email
						        String query11 = "SELECT email FROM users WHERE libraryID = ?";
					            PreparedStatement statement1 = conn.prepareStatement(query11);
					            statement1.setInt(1, libraryID);
					            ResultSet resultSet1 = statement1.executeQuery();
					            //find email
					            if (resultSet1.next()) {
					                 email = resultSet1.getString("email");
					            } else {
					                System.out.println("No user found with libraryID: " + libraryID);
					            }
					            //send email
						        try {
						            sendEmail.send(email, subject, body);
						        } catch (EmailException | MessagingException e3) {
						            e3.printStackTrace();
						        }
							    setVisible(false);
				            } else if (isExtended) {
				            	JOptionPane.showMessageDialog(null,"Borrowing period has already been extended.");
				            } else {
				            	JOptionPane.showMessageDialog(null,"Cannot extend borrowing period until the third day.");
				                System.out.println(currentTime - dateCreated.getTime());
				            }
				        } else {
				        	JOptionPane.showMessageDialog(null,"You do not currently have this item borrowed.");
				        }
				    } catch (SQLException e1) {
				        e1.printStackTrace();
				    }
				}
				else if(booking.isSelected()) {
					try {
						int item = Integer.parseInt(btnEnter.getText().trim());
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", "");
						PreparedStatement stmt = conn.prepareStatement("SELECT * FROM reserve WHERE userID = ? AND itemID = ?");
				        stmt.setInt(1, libraryID);
				        stmt.setInt(2, item);

				        ResultSet rs = stmt.executeQuery();
				        if (rs.next()) {
				            boolean isExtended = rs.getBoolean("extended");
				            Timestamp dateCreated = rs.getTimestamp("date_created");
				            long currentTime = System.currentTimeMillis();

				            if (!isExtended && (currentTime - dateCreated.getTime()) >= (3 * 24 * 60 * 60 * 1000)) {
				            	PreparedStatement updateStmt = conn.prepareStatement("UPDATE reserve SET extended = 1, days = 3, date_created = now(), duedate = DATE_ADD(NOW(), INTERVAL ? DAY) WHERE userID = ? AND itemID = ?");
				            	updateStmt.setInt(1, 3);
				            	updateStmt.setInt(2, libraryID);
				            	updateStmt.setInt(3, item);
				            	updateStmt.executeUpdate();
				                JOptionPane.showMessageDialog(null,"Reservation extended successfully.");
				                
				                //save in transactions history
				                String insertQuery = "INSERT INTO transactions (userId, transaction) VALUES (?, ?)";
							    PreparedStatement stmt1 = conn.prepareStatement(insertQuery);
							    stmt1.setInt(1, libraryID); 
							    stmt1.setString(2, "You extended your reservation of item #"+item+" on "+dateStr); 
							    stmt1.executeUpdate();
							    
							  //email information
							    String email = null;
							    String subject = "Successful extension";
						        String body = "You extended your reservation of item #"+item;
						        
						        //to send an email
						        String query11 = "SELECT email FROM users WHERE libraryID = ?";
					            PreparedStatement statement1 = conn.prepareStatement(query11);
					            statement1.setInt(1, libraryID);
					            ResultSet resultSet1 = statement1.executeQuery();
					            //find email
					            if (resultSet1.next()) {
					                 email = resultSet1.getString("email");
					            } else {
					                System.out.println("No user found with libraryID: " + libraryID);
					            }
					            //send email
						        try {
						            sendEmail.send(email, subject, body);
						        } catch (EmailException | MessagingException e3) {
						            e3.printStackTrace();
						        }
							    setVisible(false);
				            } else if (isExtended) {
				            	JOptionPane.showMessageDialog(null,"Booking has already been extended.");
				            } else {
				            	JOptionPane.showMessageDialog(null,"Cannot extend reservation until the third day.");
				                System.out.println(currentTime - dateCreated.getTime());
				            }
				        } else {
				        	JOptionPane.showMessageDialog(null,"You do not currently have this item booked.");
				        }
				    } catch (SQLException e1) {
				        e1.printStackTrace();
				    }
				}
			}
		});
		btnEnter.setForeground(Color.WHITE);
		btnEnter.setFont(new Font("Georgia", Font.PLAIN, 16));
		btnEnter.setBackground(new Color(116, 107, 222));
		btnEnter.setBounds(227, 129, 164, 35);
		contentPane.add(btnEnter);
	}
}

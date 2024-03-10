import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JTextField;

import org.apache.commons.mail.EmailException;

import javax.mail.MessagingException;
import javax.swing.JButton;

public class signup extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField emiratesID;
	private JTextField username;
	private JTextField password;
	private JTextField emailField;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					signup window = new signup();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public signup() {
		initialize();
	}


	private void initialize() {
		getContentPane().setFont(new Font("Georgia", Font.PLAIN, 16));
		getContentPane().setBackground(new Color(255, 255, 255));
		setBackground(new Color(255, 255, 255));
		setBounds(100, 100, 677, 631);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("SIGN UP FORUM");
		lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 24));
		lblNewLabel.setBounds(233, 34, 193, 39);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("EmiratesID");
		lblNewLabel_1.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(132, 97, 127, 30);
		getContentPane().add(lblNewLabel_1);
		
		emiratesID = new JTextField();
		emiratesID.setForeground(new Color(0, 0, 0));
		emiratesID.setBackground(new Color(229, 232, 237));
		emiratesID.setFont(new Font("Georgia", Font.PLAIN, 20));
		emiratesID.setBounds(132, 138, 391, 57);
		getContentPane().add(emiratesID);
		emiratesID.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Username");
		lblNewLabel_2.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_2.setBounds(132, 206, 116, 30);
		getContentPane().add(lblNewLabel_2);
		
		username = new JTextField();
		username.setFont(new Font("Georgia", Font.PLAIN, 20));
		username.setForeground(new Color(0, 0, 0));
		username.setBackground(new Color(229, 232, 237));
		username.setBounds(132, 247, 391, 57);
		getContentPane().add(username);
		username.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Password");
		lblNewLabel_3.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_3.setBounds(132, 315, 95, 30);
		getContentPane().add(lblNewLabel_3);
		
		password = new JTextField();
		password.setForeground(new Color(0, 0, 0));
		password.setFont(new Font("Georgia", Font.PLAIN, 20));
		password.setBackground(new Color(229, 232, 237));
		password.setBounds(132, 350, 392, 57);
		getContentPane().add(password);
		password.setColumns(10);
		
		JButton enterbutton = new JButton("ENTER");
		enterbutton.setForeground(new Color(255, 255, 255));
		enterbutton.setBackground(new Color(116, 107, 222));
		enterbutton.setFont(new Font("Georgia", Font.PLAIN, 24));
		enterbutton.setBounds(233, 540, 193, 30);
		getContentPane().add(enterbutton);
		
		JLabel lblNewLabel_3_1 = new JLabel("Email");
		lblNewLabel_3_1.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_3_1.setBounds(132, 418, 95, 30);
		getContentPane().add(lblNewLabel_3_1);
		
		emailField = new JTextField();
		emailField.setForeground(Color.BLACK);
		emailField.setFont(new Font("Georgia", Font.PLAIN, 20));
		emailField.setColumns(10);
		emailField.setBackground(new Color(229, 232, 237));
		emailField.setBounds(132, 458, 392, 57);
		getContentPane().add(emailField);
		enterbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//save id, username, pass, and email
				String id = emiratesID.getText().trim();
				String uname = username.getText().trim();
				String pass = password.getText().trim();
				String email = emailField.getText().trim();
				
				//if any fields are empty
				if(id.equals("") || uname.equals("") || pass.equals("") || email.equals("")) {
					JOptionPane.showMessageDialog(null, "Please fill in all fields.");
				}
				//if id is not 15 digits
				else if(id.length() != 15 ){
					JOptionPane.showMessageDialog(null, "Please make sure your emiratesID is 15 digits.");
				}
				//check if all emirateID characters are digits
				else if(!check(id)) {
					JOptionPane.showMessageDialog(null, "Please enter a valid emiratesID.");
				}
				//if email entered is not accurate
				else if(!email.contains("@") || !email.contains(".com")) {
					JOptionPane.showMessageDialog(null, "Please enter a valid email.");
				}
				else {
					try {
						//check if username exists
						Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/libraryapp", "root", "");
						PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
						ps.setString(1, uname);
						ResultSet rs = ps.executeQuery();
						if(rs.next()) {
							JOptionPane.showMessageDialog(null, "The entered username already exists. Please choose another username.");}
						
						//check if emiratesID exists
						 else {
							ps = conn.prepareStatement("SELECT * FROM users WHERE emiratesID = ?");
							ps.setString(1, id);
							rs = ps.executeQuery();
							if(rs.next()) {
								JOptionPane.showMessageDialog(null, "The entered Emirates ID already exists.");}
							
							//insert information into users table
							 else {
								ps = conn.prepareStatement("INSERT INTO users (username, password, emiratesID, email) VALUES (?, ?, ?, ?)");
								ps.setString(1, uname);
								ps.setString(2, pass);
								ps.setString(3, id);
								ps.setString(4, email);
								ps.executeUpdate();}
							PreparedStatement stmt1;
							
							
							//libraryID is incremental so get it from the table
							stmt1 = conn.prepareStatement("SELECT libraryID FROM users WHERE emiratesID = ?");
					        stmt1.setString(1, id);
					        ResultSet rs1 = stmt1.executeQuery();
					        if (rs1.next()) {
					            int unique = rs1.getInt("libraryID");
							JOptionPane.showMessageDialog(null, "You have successfully signed up. Your library ID will be sent via email.");
					        String subject = "Library ID Number";
					        String body = "Thank you for registering.\nYour library ID number is "+unique;
							try {
					            sendEmail.send(email, subject, body);
					        } catch (EmailException | MessagingException e3) {
					            e3.printStackTrace();
					        }
					        }
					        
							//open login page
					        login l = new login();
							setVisible(false);
							l.setVisible(true);
						}
					}
					catch (SQLException e1) {
						e1.printStackTrace();
						}
				}
								
	}
});
	}
	public boolean check(String s) {
		for (int i = 0; i < s.length(); i++) {
	        if (!Character.isDigit(s.charAt(i))) {
	            return false;
	        }
	    }
	    return true;}
}
	

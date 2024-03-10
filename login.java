import java.awt.EventQueue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

@SuppressWarnings("serial")
public class login extends JFrame{

	private JTextField username;
	private JPasswordField password;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					login window = new login();
					window.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public login() {
		initialize();
	}
	
	private void initialize() {
		setBounds(new Rectangle(632, 230, 596, 452));
		getContentPane().setBackground(new Color(255, 255, 255));
		setBackground(new Color(255, 255, 255));
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel sign_in_label = new JLabel("SIGN IN TO YOUR ACCOUNT");
		sign_in_label.setHorizontalAlignment(SwingConstants.CENTER);
		sign_in_label.setFont(new Font("Georgia", Font.PLAIN, 24));
		sign_in_label.setBounds(127, 29, 345, 36);
		getContentPane().add(sign_in_label);
		
		username = new JTextField();
		username.setForeground(new Color(0, 0, 0));
		username.setFont(new Font("Georgia", Font.PLAIN, 20));
		username.setBackground(new Color(229, 232, 237));
		username.setBounds(127, 118, 345, 45);
		getContentPane().add(username);
		username.setColumns(10);
		
		JButton signupbtn = new JButton("Don't have an account? Sign up here");
		signupbtn.setToolTipText("");
		signupbtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				signup s = new signup();
				s.setVisible(true);
				
			}
		});
		signupbtn.setHorizontalAlignment(SwingConstants.LEFT);
		signupbtn.setBorder(null);
		signupbtn.setForeground(new Color(0, 0, 0));
		signupbtn.setBackground(new Color(255, 255, 255));
		signupbtn.setFont(new Font("Georgia", Font.ITALIC, 17));
		signupbtn.setBounds(127, 323, 345, 23);
		getContentPane().add(signupbtn);
		
		JLabel username_text = new JLabel("Username");
		username_text.setFont(new Font("Georgia", Font.PLAIN, 16));
		username_text.setBounds(127, 93, 88, 14);
		getContentPane().add(username_text);
		
		JLabel password_text = new JLabel("Password");
		password_text.setFont(new Font("Georgia", Font.PLAIN, 16));
		password_text.setBounds(127, 170, 88, 14);
		getContentPane().add(password_text);
		
		password = new JPasswordField();
		password.setForeground(new Color(0, 0, 0));
		password.setBackground(new Color(229, 232, 237));
		password.setFont(new Font("Georgia", Font.PLAIN, 20));
		password.setBounds(127, 195, 345, 45);
		getContentPane().add(password);
		
		JButton sign_in = new JButton("SIGN IN");
		sign_in.setForeground(new Color(255, 255, 255));
		sign_in.setFont(new Font("Georgia", Font.PLAIN, 24));
		sign_in.setBackground(new Color(116, 107, 222));
		sign_in.setBounds(127, 276, 345, 36);
		getContentPane().add(sign_in);
		sign_in.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username1 = username.getText();
				@SuppressWarnings("deprecation")
				String password1 = password.getText();
		      
				//check if fields are empty
		        if(username1.equals("")) {
		            JOptionPane.showMessageDialog(null,"Please enter username");
		            username.requestFocus();}
		        else if(password1.equals("")){
		            JOptionPane.showMessageDialog(null,"Please enter password");
		            password.requestFocus();}
		        
		        //check if user name and password exists
		        else {
		        	try {
		                Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/libraryapp", "root", "");
		                String query = "SELECT * FROM users WHERE username = ? AND password = ?";
		                PreparedStatement stmt = conn.prepareStatement(query);
		                stmt.setString(1, username1);
		                stmt.setString(2, password1);
		                ResultSet rs = stmt.executeQuery();
		                if (rs.next()) {
		                    int lib = rs.getInt("libraryID");
		                    if(username1.equals("admin") && password1.equals("admin1234")) {
		                        // Open admin interface
		                        admin1 a = new admin1();
		                        setVisible(false);
		                        
		                    //open user interface
		                    } else {
		                        SearchBorrow s = new SearchBorrow(lib);
		                        setVisible(false);
		                        s.setVisible(true);}
		                    
		                //user name and password entered are wrong
		                } else {
		                    JOptionPane.showMessageDialog(null, "Invalid username or password");
		                }
		                conn.close();

		            } catch (SQLException ex) {
		                ex.printStackTrace();
		            }
		        }
	}
});
	}	
}

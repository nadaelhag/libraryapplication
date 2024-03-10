import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class display extends JFrame {

	private JPanel contentPane;
	private static int user;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					display frame = new display(user);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public display(int libraryID) {
		initialize(libraryID);
	}


	private void initialize(int libraryID) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 498, 350);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("* Please note that borrowing period will depend on the value and ");
		lblNewLabel_1.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 46, 519, 31);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_4 = new JLabel("availability of the item");
		lblNewLabel_4.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel_4.setBounds(20, 74, 166, 31);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel = new JLabel("* Booking will be automatically canceled after 3 days, but it can  ");
		lblNewLabel.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 102, 464, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblCanBeExtended = new JLabel("can be extended for another 3 days if the extension is made on  ");
		lblCanBeExtended.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblCanBeExtended.setBounds(20, 130, 454, 31);
		contentPane.add(lblCanBeExtended);
		
		JLabel lblTheThirdDay = new JLabel("the third day before cancellation");
		lblTheThirdDay.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblTheThirdDay.setBounds(20, 157, 454, 31);
		contentPane.add(lblTheThirdDay);
		
		JLabel lblBeforeBorrowingA = new JLabel("* Some items of very high value can only be consulted in the ");
		lblBeforeBorrowingA.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblBeforeBorrowingA.setBounds(10, 185, 464, 31);
		contentPane.add(lblBeforeBorrowingA);
		
		JButton btnBorrowItem = new JButton("Borrow");
		btnBorrowItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BorrowItem b = new BorrowItem(libraryID);
				setVisible(false);
				b.setVisible(true);
			}
		});
		btnBorrowItem.setForeground(Color.WHITE);
		btnBorrowItem.setFont(new Font("Georgia", Font.PLAIN, 16));
		btnBorrowItem.setBackground(new Color(116, 107, 222));
		btnBorrowItem.setBounds(67, 267, 119, 35);
		contentPane.add(btnBorrowItem);
		
		JLabel lblBranch = new JLabel("branch during the opening hours");
		lblBranch.setFont(new Font("Georgia", Font.PLAIN, 16));
		lblBranch.setBounds(20, 215, 454, 31);
		contentPane.add(lblBranch);
		
		JLabel lblNewLabel_2 = new JLabel("Library Rules");
		lblNewLabel_2.setFont(new Font("Georgia", Font.PLAIN, 21));
		lblNewLabel_2.setBounds(179, 7, 132, 28);
		contentPane.add(lblNewLabel_2);
		
		JButton btnBook = new JButton("Book");
		btnBook.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BookItem b1  = new BookItem(libraryID);
				setVisible(false);
				b1.setVisible(true);
				
			}
		});
		btnBook.setForeground(Color.WHITE);
		btnBook.setFont(new Font("Georgia", Font.PLAIN, 16));
		btnBook.setBackground(new Color(116, 107, 222));
		btnBook.setBounds(285, 267, 119, 35);
		contentPane.add(btnBook);
	}
}

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
// this class serves as entrance point for registered users into Drone Post system
// it also allows for new users to sign up
public class LoginForm extends JFrame implements ActionListener{
	protected JTextField userNameField = new JTextField(10);
	protected JPasswordField passwordField = new JPasswordField(10);
	protected JLabel userNameLabel = new JLabel("User Name:");
	protected JLabel passwordLabel = new JLabel("Password:");
	protected JButton signIn = new JButton("Sign In");
	protected JButton signNewUser = new JButton("Sign New User");
	
	public LoginForm() {
		super("Log In to DronePost");
		setLayout(new BorderLayout());
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(userNameLabel);
		p1.add(userNameField);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(passwordLabel);
		p2.add(passwordField);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(signIn);
		p3.add(signNewUser);
		
		add(p1, BorderLayout.NORTH);
		add(p2, BorderLayout.CENTER);
		add(p3, BorderLayout.SOUTH);
		
		pack();
		signIn.addActionListener(this);
		signNewUser.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ev) {
		String arg = ev.getActionCommand();
		if (arg.equals("Sign New User")) {
			RegistrationForm rf = new RegistrationForm();
			rf.setVisible(true);
		}
		if (arg.equals("Sign In")) {
			String userName = userNameField.getText();
			String password = String.valueOf(passwordField.getPassword());
			//sign in to system based on user name and password authentication 
			if (DataBase.isUserExists(userName, password)) {
				userOperationSelection ups = new userOperationSelection(UsersManagement.getUser(userName)); 
				ups.setVisible(true);
			}
			else {
				UserNotExists une = new UserNotExists();
				une.setVisible(true);
				userNameField.setText("");
				passwordField.setText("");
			}
		}
		
	}
	
	
}

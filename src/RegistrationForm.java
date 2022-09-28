import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.*;
// new users registration form
public class RegistrationForm extends Frame implements ActionListener{
	protected JTextField userName = new JTextField(10);
	protected JPasswordField password = new JPasswordField(10);
	protected JTextField fname = new JTextField(10);
	protected JTextField lname = new JTextField(10);
	protected JTextField city = new JTextField(10);
	protected JTextField street = new JTextField(10);
	protected JTextField homeNo = new JTextField(10);
	protected JTextField phone = new JTextField(10);
	protected JTextField email = new JTextField(10);
	protected JLabel userNameLabel = new JLabel("User Name:");
	protected JLabel passwordLabel = new JLabel("Password:");
	protected JLabel fnameLabel = new JLabel("First Name:");
	protected JLabel lnameLabel = new JLabel("Last Name:");
	protected JLabel streetLabel = new JLabel("Street:");
	protected JLabel homeNoLabel = new JLabel("Home No:");
	protected JLabel cityLabel = new JLabel("City:");
	protected JLabel phoneLabel = new JLabel("Phone:");
	protected JLabel emailLabel = new JLabel("Email:");
	protected JLabel packageLabel = new JLabel("Select your prefered package:");
	protected JRadioButton package1 = new JRadioButton("50 deliveries (99$)");
	protected JRadioButton package2 = new JRadioButton("150 deliveries (179$)");
	protected JButton clear = new JButton("Clear");
	protected JButton apply = new JButton("Apply");
	
	public RegistrationForm() {
		super("User Registration Form");
		setLayout(new GridLayout(7,1));
		JPanel p = new JPanel(new FlowLayout());
		p.add(userNameLabel);
		p.add(userName);
		p.add(passwordLabel);
		p.add(password);
		
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(fnameLabel);
		p1.add(fname);
		p1.add(lnameLabel);
		p1.add(lname);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(streetLabel);
		p2.add(street);
		p2.add(homeNoLabel);
		p2.add(homeNo);
		p2.add(cityLabel);
		p2.add(city);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(phoneLabel);
		p3.add(phone);
		p3.add(emailLabel);
		p3.add(email);
		
		JPanel p4 = new JPanel(new FlowLayout());
		p4.add(package1);
		p4.add(package2);
		
		JPanel p5 = new JPanel(new GridLayout(2,1));
		p5.add(packageLabel);
		p5.add(p4);
		
		JPanel p6 = new JPanel(new FlowLayout());
		p6.add(clear);
		p6.add(apply);
		
		add(p); add(p1); add(p2); add(p3); add(p5); add(p4); add(p6);
		ButtonGroup buttonGroup = new ButtonGroup();
		buttonGroup.add(package1);
		buttonGroup.add(package2);

		pack();
		
		clear.addActionListener(this);
		apply.addActionListener(this);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent ev){System.exit(0);}
		});
	}
	
	public void actionPerformed(ActionEvent ev) {
		String arg = ev.getActionCommand();
		String localUserName, localPassword, localFname, localLname, localStreet,
		localHomeNo, localCity, localPhone, localEmail;
		int credit = 0;
		if (arg.equals("Clear")) {
			userName.setText("");
			password.setText("");
			fname.setText("");
			lname.setText("");
			street.setText("");
			homeNo.setText("");
			city.setText("");
			phone.setText("");
			email.setText("");
		}
		if (arg.equals("Apply")) {
			localUserName = userName.getText();
			localPassword = String.valueOf(password.getPassword());
			localFname = fname.getText();
			localLname = lname.getText();
			localStreet = street.getText();
			localHomeNo = homeNo.getText();
			localCity = city.getText();
			localPhone = phone.getText();
			localEmail = email.getText();
			if (package1.isSelected())
				credit = 50;
			else if (package2.isSelected())
				credit = 150;
			User u = new User(localUserName, localPassword, localFname, localLname,  //construction of new user upon form input
					localCity, localStreet, localHomeNo, localPhone, localEmail, credit);
			try {
				isUserExists(localUserName);   //user name should be unique
				isBlankString(localUserName); //no empty fields allowed
				isBlankString(localPassword);
				isBlankString(localFname);
				isBlankString(localLname);
				isBlankString(localStreet);
				isBlankString(localHomeNo);
				isBlankString(localCity);
				isBlankString(localPhone);
				UsersManagement.users.add(u); //new user is added to users ArrayList
				DataBase.insertUserToDataBase(u); //new user is added to DB
			}
			catch (Exception e) {System.out.println(e);};
			userName.setText("");
			password.setText("");
			fname.setText("");
			lname.setText("");
			street.setText("");
			homeNo.setText("");
			city.setText("");
			phone.setText("");
			email.setText("");
			setVisible(false);
			dispose();
		}	
	}
	private void isUserExists(String UserName) throws Exception {
		if (DataBase.isUserNameExists(UserName))
			throw new Exception("User Name exists in DB");
	}

	private void isBlankString(String str) throws Exception {
		if (str.isBlank())
			throw new Exception("No empty fields allowed");
	}
	
}

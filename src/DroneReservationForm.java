import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;

import javax.swing.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
//this form acts as the drone reservation form
//logged on user (sender) may request a delivery directed to another registered user
public class DroneReservationForm extends JFrame implements ActionListener{
	protected JTextField senderUserName = new JTextField(10);
	protected JTextField senderName = new JTextField(20);
	protected JTextField senderAddress = new JTextField(30);
	protected JLabel senderUnameLabel = new JLabel("Sender User Name: ");
	protected JLabel senderNameLabel = new JLabel("Sender Name: ");
	protected JLabel senderAddressLabel = new JLabel("Origin Address: ");
	
	protected JTextField recieverUserName = new JTextField(10);
	protected JTextField recieverName = new JTextField(20);
	protected JTextField recieverAddress = new JTextField(30);
	protected JLabel recieverUnameLabel = new JLabel("reciever User Name (please press enter): ");
	protected JLabel recieverNameLabel = new JLabel("reciever Name: ");
	protected JLabel recieverAddressLabel = new JLabel("Destination Address: ");
	
	protected JTextField droneId = new JTextField(10);
	protected JLabel droneLabel = new JLabel("Drone Assigned: ");

	protected JButton apply = new JButton("Submit Delivery");
	protected JButton clear = new JButton("Clear");
	private User user;
	private Drone drone;
	
	public DroneReservationForm(User user) {
		super("New Reservation Form");
		this.user = user;
		this.drone = DronesAssignment.drones.peek();
		setFormLayout();
		setFields();
				
		apply.addActionListener(this);
		clear.addActionListener(this);
		recieverUserName.addActionListener(this);
	}
//user balance is updated after new order submission
	private void updateUserCredit() {
		user.setBalance(user.getBalance()-1);
		UsersManagement.updateCredit(user, user.getBalance());
		try {
			DataBase.updateUserCredit(user);
		} catch (Exception e) {System.out.println(e);}
	}
	private void droneAssign() {
		this.drone = DronesAssignment.drones.remove(); //retrieve and remove next in queue, and assign into drone
		drone.setStatus(false); //change drone status to unavailable
		DronesAssignment.unavailableDrones.add(drone); //add drone to unavailable drones ArrayList
		try {
			DataBase.updateDroneStatus(drone); //update drone in DB with its new status -> not available
		} catch (Exception e) {System.out.println(e);}
	}
	private void setFields() {
		senderUserName.setEditable(false);
		senderName.setEditable(false);
		senderAddress.setEditable(false);
		recieverName.setEditable(false);
		recieverAddress.setEditable(false);
		droneId.setEditable(false);
		
		senderUserName.setText(user.getUserName().toString());
		senderName.setText(user.getName());
		Drone d = DronesAssignment.drones.element(); //set drone id into text field, as assigned drone for mission
		droneId.setText(String.valueOf(d.getDroneId()));
	}
	
	private void setFormLayout() {
		setLayout(new BorderLayout());
		JPanel p1 = new JPanel(new FlowLayout());
		p1.add(senderUnameLabel);
		p1.add(senderUserName);
		p1.add(senderNameLabel);
		p1.add(senderName);
		
		JPanel p2 = new JPanel(new FlowLayout());
		p2.add(recieverUnameLabel);
		p2.add(recieverUserName);
		p2.add(recieverNameLabel);
		p2.add(recieverName);
		
		JPanel p1p2 = new JPanel(new BorderLayout());
		p1p2.add(p1, BorderLayout.NORTH);
		p1p2.add(p2, BorderLayout.SOUTH);
		
		JPanel p3 = new JPanel(new FlowLayout());
		p3.add(senderAddressLabel);
		p3.add(senderAddress);
		senderAddress.setText(user.getAddress());
		
		JPanel p4 = new JPanel(new FlowLayout());
		p4.add(recieverAddressLabel);
		p4.add(recieverAddress);
		
		JPanel p3p4 = new JPanel(new BorderLayout());
		p3p4.add(p3, BorderLayout.NORTH);
		p3p4.add(p4, BorderLayout.SOUTH);
		
		JPanel p5 = new JPanel(new FlowLayout());
		p5.add(droneLabel);
		p5.add(droneId);
		
		JPanel p6 = new JPanel(new FlowLayout());
		p6.add(apply);
		p6.add(clear);
		
		JPanel p5p6 = new JPanel(new BorderLayout());
		p5p6.add(p5, BorderLayout.NORTH);
		p5p6.add(p6, BorderLayout.SOUTH);
		
		add(p1p2, BorderLayout.NORTH);
		add(p3p4, BorderLayout.CENTER);
		add(p5p6, BorderLayout.SOUTH);
		pack();
	}
	@Override
	public void actionPerformed(ActionEvent ev) {
		String arg = ev.getActionCommand();
		if (arg.equals(recieverUserName.getText())) {
			try {
				isRecieverExists(arg); //verify if reciever user name exists in DB, if not send appropriate message to drone
				isRecieverLegal(senderUserName.getText(), arg); //verify if reciever user name differ from sender, if not send appropriate message to drone
				recieverName.setText(UsersManagement.getUser(arg).getName()); //set reciever name by user name
				recieverAddress.setText(UsersManagement.getUser(arg).getAddress()); //set reciever address by user name
			} catch (Exception e) {System.out.println(e);}
		}
		
		if (arg.equals("Clear")) {
			recieverUserName.setText("");
			recieverName.setText("");
			recieverAddress.setText("");
		}
		if (arg.equals("Submit Delivery")) {
			droneAssign();
			updateUserCredit();
			User reciever = UsersManagement.getUser(recieverUserName.getText());
			setNewOrder(user, reciever, drone, new Date()); //new order construction, add order to DB, add order to ArrayList, update orderId for drone
			drone.setMessage("Delivery in Proccess");
			try {
				DataBase.updateDroneMessage(drone);
			} catch (Exception e) {System.out.println(e);}
			setVisible(false);
			dispose();
		}
	}
	
	private void setNewOrder(User sender, User reciever, Drone drone, Date date) {
		Order order = new Order(sender, reciever, drone, date);
		OrderManagement.orders.add(order);
		try {
			DataBase.insertOrderToDataBase(order);
			updateOrderIdForDrone(order.getOrderId(), drone);
		} catch (Exception e) {System.out.println(e);}
	}
	private void updateOrderIdForDrone(int orderId, Drone drone) {
		DronesAssignment.updateOrderForDrone(orderId, drone);
		try {
			DataBase.updateOrderForDrone(drone);
		} catch (Exception e) {System.out.println(e);}
	}
	private void isRecieverLegal(String sender, String reciever) throws Exception{
		if (reciever.equals(sender)) {
			drone.setMessage("Delivery cannot be assigned to sender");
			try {
				DataBase.updateDroneMessage(drone);
			} catch (Exception e) {System.out.println(e);}
			throw new Exception ("Delivery cannot be assigned to sender");
		}
	}
	private void isRecieverExists(String arg) throws Exception {
		if (!DataBase.isUserNameExists(arg)) {
			drone.setMessage("Destination is not available");
			try {
				DataBase.updateDroneMessage(drone);
			} catch (Exception e) {System.out.println(e);}
			throw new Exception ("Destination is not available");
		}
	}
	

}

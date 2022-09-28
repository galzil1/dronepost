import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.swing.*;
//this form acts as the drone arrival form
//logged on user (reciever) may report that delivery has arrived
public class DroneArrivalForm extends JFrame implements ActionListener{
	protected JTextField recieverName = new JTextField(10);
	protected JTextField recieverAddress = new JTextField(20);
	protected JTextField droneID = new JTextField(10);
	protected JLabel LabelRecieverName = new JLabel("Reciever Name:");
	protected JLabel LabelRecieverAddress = new JLabel("Reciever Address:");
	protected JLabel LabelDroneId = new JLabel("droneId:");
	protected JButton apply = new JButton("Report Arrival");
	
	private User user;
	private Drone drone;
	
	public DroneArrivalForm(User user) {
		super("Arrival Form");
		this.user = user;
		this.drone = OrderManagement.getDroneByUserFromOrder(user);
		setFormLayout();
		droneID.setText(String.valueOf(drone.getDroneId()));/////
		setTextFields();
		
		apply.addActionListener(this);
	}

	private void setTextFields() {
		recieverName.setEditable(false);
		recieverAddress.setEditable(false);
		droneID.setEditable(false);
		recieverName.setText(user.getName());
		recieverAddress.setText(user.getAddress());
		//droneID.setText(String.valueOf(dronee.getDroneId()));
	}

	private void setFormLayout() {
		setLayout(new BorderLayout());
		JPanel p1 = new JPanel(new FlowLayout());
		JPanel p2 = new JPanel(new FlowLayout());
		JPanel p1p2 = new JPanel(new BorderLayout());
		JPanel p3 = new JPanel(new FlowLayout());
		JPanel p4 = new JPanel(new FlowLayout());
		p1.add(LabelRecieverName);
		p1.add(recieverName);
		p2.add(LabelRecieverAddress);
		p2.add(recieverAddress);
		p3.add(LabelDroneId);
		p3.add(droneID);
		p4.add(apply);
		p1p2.add(p1, BorderLayout.NORTH);
		p1p2.add(p2, BorderLayout.SOUTH);
		add(p1p2, BorderLayout.NORTH);
		add(p3, BorderLayout.CENTER);
		add(p4, BorderLayout.SOUTH);
		pack();
	}

	@Override
	public void actionPerformed(ActionEvent ev) {
		String arg = ev.getActionCommand();
		if (arg.equals("Report Arrival")) {
			DronesAssignment.unavailableDrones.remove(drone); //drone removed from unavailable drones ArrayList
			drone.setStatus(true); //change drone status to available
			drone.setOrderId(-1); 
			drone.setMessage("Delivery Completed Successfully");
			
			try { //update drone to DB
				DataBase.updateDroneStatus(drone);
				DataBase.updateOrderForDrone(drone);
				DataBase.updateDroneMessage(drone);
			} catch (Exception e) {System.out.println(e);}
			
			Date arrivalDate = new Date();
			long diffMill = Math.abs(arrivalDate.getTime() - OrderManagement.getOrder(drone).getDate().getTime()); //delta time [msec] of delivery from arrival to submission
			long diff = TimeUnit.MINUTES.convert(diffMill, TimeUnit.MILLISECONDS); //delta time [min]
			
			if (drone.getTime()+diff < 180) { //if overall flight period is legal (<3 hours) update time and battery
				drone.setTime(drone.getTime()+diff);
				try {
					DataBase.updateDroneTime(drone);
					DataBase.updateDroneBattery(drone);
				} catch (Exception e) {System.out.println(e);}
			}
			else { //if drone exceeds 3 hours limitation -> return for charging
				drone.setTime(0);
				drone.setBattery(100);
				try {
					DataBase.updateDroneTime(drone);
					DataBase.updateDroneBattery(drone);
					System.out.println("Drone reached flight limit. returned to base for charging");
				} catch (Exception e) {System.out.println(e);}
			}
			
			DronesAssignment.drones.add(drone);
			OrderManagement.removeOrderByDrone(drone); //remove order from ArrayList. orders ArrayList handles only active (not completed) orders 
			setVisible(false);
			dispose();
		}
		
	}

}

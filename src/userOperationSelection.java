import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import java.sql.SQLOutput;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import java.util.*;
//main screen for logged in user to select required action
public class userOperationSelection extends JFrame implements ActionListener{
	protected JButton droneReservation = new JButton("Drone Reservation");
	protected JButton droneArrival = new JButton("Drone Arrival");
	protected JButton ordersHistory = new JButton("Orders History");
	private User user;
	
	public userOperationSelection(User user) {
		super("select service option");
		this.user = user;
		setLayout(new FlowLayout());
		add(droneReservation);
		add(droneArrival);
		add(ordersHistory);
		pack();
		disableArrivalButton();
		disableReservationButton();
		droneReservation.addActionListener(this);
		droneArrival.addActionListener(this);
		ordersHistory.addActionListener(this);
	}
	//Arrival button selection should be disabled if no active order assigned to user
	private void disableArrivalButton() {
		if (!OrderManagement.isOrderForReciever(user))
				droneArrival.setEnabled(false);
	}
	//Reservation button selection should be disabled if drones queue is empty -> no available drones
	private void disableReservationButton() {
		if (DronesAssignment.drones.isEmpty())
			droneReservation.setEnabled(false);
	}

	public void actionPerformed(ActionEvent ev) {
		String arg = ev.getActionCommand();
		if (arg.equals("Drone Reservation")) {
			DroneReservationForm drf = new DroneReservationForm(user);
			drf.setVisible(true);
			setVisible(false);
			dispose();
		}
		else if (arg.equals("Drone Arrival")) {
			DroneArrivalForm daf = new DroneArrivalForm(user);
			daf.setVisible(true);
			setVisible(false);
			dispose();
		}
		else if (arg.equals("Orders History"))
			DataBase.presentOrdersHistory();
}
}
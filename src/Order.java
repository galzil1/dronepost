import java.time.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Order {
	Random rand = new Random();
	private int orderId;
	private User sender;
	private User reciever;
	private Drone drone;
	private Date orderDate;
	public Order (User sender, User reciever, Drone drone, Date orderDate) {
		this.orderId = rand.nextInt(100);
		this.sender = sender;
		this.reciever = reciever;
		this.drone = drone;
		this.orderDate = orderDate;
	}
	public int getOrderId() {
		return this.orderId;
	}
	public Date getDate() {
		return orderDate;
	}
	public String getOrderDate() {
		return String.valueOf(orderDate);
	}
	public User getSender() {
		return sender;
	}
	public User getReciever() {
		return reciever;
	}
	public Drone getDrone() {
		return drone;
	}
	public String toString() {
		return ("Order ID: "+orderId+" | "+"sender name: "+sender.getName()+" | "+
				"reciever name: "+reciever.getName()+" | "+"Drone ID: "+drone.getDroneId()+
				" | "+"order date: "+orderDate);
	}
}

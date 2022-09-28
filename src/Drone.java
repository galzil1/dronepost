import java.util.concurrent.TimeUnit;

public class Drone {
	private int droneId;
	private boolean status;
	private int orderId, battery;
	private long time;
	private Message message;
	public Drone(int droneId) {
		this.droneId = droneId;
		this.status = true;
		this.orderId = -1;
		this.battery = 100;
		this.time = 0;
		this.message = new Message();
	}
	public int getDroneId() {
		return this.droneId;
	}

	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public int getBattery() {
		return battery;
	}
	public void setBattery(int battery) {
		this.battery = battery;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public int getOrderId() {
		return this.orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public String getMessage() {
		return this.message.getContent();
	}
	public void setMessage(String message) {
		this.message.setContent(message);
	}
	public String toString() {
		return ("DroneId: " + droneId+" | "+"status: "+status+" | "+"OrderId: "+ orderId+" | "+
				"battery: "+battery+" | "+"time: "+time+" | "+"message: "+message.getContent());
	}
}

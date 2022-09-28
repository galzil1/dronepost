import java.util.*;
//drones repository management class. 
//data structure for available drones chosen to be deque (extends queue) allowing FIFO method
//data structure for unavailable drones is ArrayList
public class DronesAssignment {
	public static Deque<Drone> drones = new ArrayDeque<Drone>();
	public static ArrayList<Drone> unavailableDrones = new ArrayList();
	private static int numOfDrones = 10;
	private static Drone[] dronesArr = new Drone[numOfDrones];

///////////constructor: create new queue of available drones and update DB/////////	
	public DronesAssignment() {
		for (int i = 0; i < numOfDrones; i++) {
			dronesArr[i] = new Drone(i+1);
			drones.add(dronesArr[i]);
			try {
				DataBase.addDroneToDB(dronesArr[i]);
			} catch (Exception e) {System.out.println(e);}
		}
	}
/////////////////print drones queue//////////////////////////////
	public static void printDrones() {
		Iterator<Drone> it = drones.iterator();
		while (it.hasNext()) {
			System.out.println(it.next());
		}
	}
//////////print unavailable drones array list///////////////
	public static void printUnavailDrones() {
		for (int i = 0; i < unavailableDrones.size(); i++)
			System.out.println(unavailableDrones.get(i));
	}
//////////get drone id and return drone from available drones queue/////////////
	public static Drone getDrone(int droneId) {
		for (int i = 0; i < drones.size(); i++) {
			Drone d = drones.remove();
			if (d.getDroneId() == droneId)
				return d;
		}
		return null;
	}
/////////////update order id for unavailable drone///////////////
	public static void updateOrderForDrone(int orderId, Drone drone) {
		for (int i = 0; i < unavailableDrones.size(); i++) {
			if (unavailableDrones.get(i).getDroneId() == drone.getDroneId())
				unavailableDrones.get(i).setOrderId(orderId);
		}
	}
/////////////update message for unavailable drone///////////////
	public static void updateMessageForDrone(String message, Drone drone) {
		for (int i = 0; i < unavailableDrones.size(); i++) {
			if (unavailableDrones.get(i).getDroneId() == drone.getDroneId())
				unavailableDrones.get(i).setMessage(message);
		}
}
}

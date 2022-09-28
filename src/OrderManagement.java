import java.time.*;
import java.util.*;
//orders repository management class. 
//data structure for active orders chosen to be ArrayList
//active order = order that was not reported as arrived
public class OrderManagement {
	public static ArrayList<Order> orders = new ArrayList();
	
	public static void printOrders() {
		for (int i=0; i < orders.size(); i++) {
			System.out.println(orders.get(i));
		}
	}
//////////scan orders for dedicated reciever and returned the assigned drone////////
	public static Drone getDroneByUserFromOrder(User user) {
		for(int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getReciever().getUserName().equals(user.getUserName())) {
				return orders.get(i).getDrone();
			}
		}
		return null;
	}
///////check if order exists per reciever////////////
	public static boolean isOrderForReciever(User reciever) {
		for(int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getReciever().getUserName().equals(reciever.getUserName()))
				return true;
		}
		return false;
	}
//////////////get order by drone//////////////
	public static Order getOrder(Drone drone) {
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getDrone().getDroneId() == drone.getDroneId())
				return orders.get(i);
		}
		return null;
	}

	public static void removeOrderByDrone (Drone drone) {
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getDrone().getDroneId() == drone.getDroneId())
				orders.remove(i);
		}
	}

}

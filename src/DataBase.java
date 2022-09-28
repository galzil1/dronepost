import java.util.*;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import java.sql.*;

public class DataBase {

	public static boolean insertUserToDataBase(User user) {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = "insert into user (user_name, password, name, address, phone, email, credit)"
			        + "values (?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, user.getUserName());
			preparedStmt.setString(2, user.getPassword());
			preparedStmt.setString(3, user.getName());
			preparedStmt.setString(4, user.getAddress());
			preparedStmt.setString(5, user.getPhone());
			preparedStmt.setString(6, user.getEmail());
			preparedStmt.setInt(7, user.getBalance());
			preparedStmt.execute();
			conn.close();
			System.out.println("user updated in data base...");
			return true;
		} catch (Exception e) {System.out.println(e); return false;}
	}
/////////////check if user is already registered (check by user name & password)/////////
	public static boolean isUserExists(String userName, String password) {
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String query;
		try {
			conn = connClass.getConn();
			query = "SELECT * from user";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (rs.getString("user_name").equals(userName) && rs.getString("password").equals(password)) {
					conn.close();
					return true;
				}
			}
			conn.close();
		} catch (Exception e) {System.out.println(e);}
		return false;
	}
//////////check if user name exists in DB////////////////
	public static boolean isUserNameExists(String userName) {
		Connection conn;
		Statement stmt;
		ResultSet rs;
		String query;
		try {
			conn = connClass.getConn();
			query = "SELECT * from user";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (rs.getString("user_name").equals(userName)) {
					conn.close();
					return true;
				}
			}
			conn.close();
		} catch (Exception e) {System.out.println(e);}
		return false;
	}

public static boolean updateUserCredit(User user) {
	Connection conn;
	String query;
	try {
		conn = connClass.getConn();
		query = "UPDATE user SET credit = ? WHERE user_name = ?";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.setInt(1, user.getBalance());
		preparedStmt.setString(2, user.getUserName());
		preparedStmt.execute();
		conn.close();
		System.out.println("user credit updated...");
		return true;
	} catch (Exception e) {System.out.println(e);}
	return false;
}
/////////////initialize users DB////////////////////
	public static void deleteUsers() {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = "DELETE from user";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.execute();
			conn.close();
			System.out.println("Users DB deleted...");
		} catch (Exception e) {System.out.println(e);}
	}
/////////////initialize drones DB////////////////////
	public static void deleteDrones() {
	Connection conn;
	String query;
	try {
		conn = connClass.getConn();
		query = "DELETE from drone";
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		preparedStmt.execute();
		conn.close();
		System.out.println("Drones DB deleted...");
		} catch (Exception e) {System.out.println(e);}
	}	

	public static boolean addDroneToDB(Drone drone) {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = "insert into drone (droneId, status, orderId, battery, time_min, message)"
					+ "values(?,?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, drone.getDroneId());
			preparedStmt.setBoolean(2, drone.getStatus());
			preparedStmt.setInt(3, drone.getOrderId());
			preparedStmt.setInt(4, drone.getBattery());
			preparedStmt.setLong(5, drone.getTime());
			preparedStmt.setString(6, drone.getMessage());
			preparedStmt.execute();
			conn.close();
			return true;
		} catch (Exception e) {System.out.println(e);}
		return false;
	}

	public static boolean updateDroneStatus(Drone drone) {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = "UPDATE drone SET status = ? WHERE droneId = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setBoolean(1, drone.getStatus());
			preparedStmt.setInt(2, drone.getDroneId());
			preparedStmt.execute();
			conn.close();
			return true;
		} catch (Exception e) {System.out.println(e);}
		return false;
	}

	public static boolean insertOrderToDataBase(Order order) {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = " insert into orders (orderId, sender_name, sender_address, reciever_name, reciever_address, droneId, order_date)"
			        + " values (?,?,?,?,?,?,?)";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, order.getOrderId());
			preparedStmt.setString(2, order.getSender().getName());
			preparedStmt.setString(3, order.getSender().getAddress());
			preparedStmt.setString(4, order.getReciever().getName());
			preparedStmt.setString(5, order.getReciever().getAddress());
			preparedStmt.setInt(6, order.getDrone().getDroneId());
			preparedStmt.setString(7, order.getOrderDate());
			preparedStmt.execute();
			conn.close();
			System.out.println("order updated in data base...");
			return true;
		} catch (Exception e) {System.out.println(e); return false;}
	}
	
////////////update orderId field for drone//////////////
	public static boolean updateOrderForDrone(Drone drone) {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = "UPDATE drone SET orderId = ? WHERE droneId = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, drone.getOrderId());
			preparedStmt.setInt(2, drone.getDroneId());
			preparedStmt.execute();
			conn.close();
			return true;
		}	catch (Exception e) {System.out.println(e);}
		return false;
	}

	public static boolean updateDroneMessage(Drone drone) {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = "UPDATE drone SET message = ? WHERE droneId = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setString(1, drone.getMessage());
			preparedStmt.setInt(2, drone.getDroneId());
			preparedStmt.execute();
			conn.close();
			return true;
		}	catch (Exception e) {System.out.println(e);}
		return false;
	}

	public static boolean updateDroneTime(Drone drone) {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = "UPDATE drone SET time_min = ? WHERE droneId = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setLong(1, drone.getTime());
			preparedStmt.setInt(2, drone.getDroneId());
			preparedStmt.execute();
			conn.close();
			return true;
		}	catch (Exception e) {System.out.println(e);}
		return false;
	}

	public static boolean updateDroneBattery(Drone drone) {
		Connection conn;
		String query;
		try {
			conn = connClass.getConn();
			query = "UPDATE drone SET battery = ? WHERE droneId = ?";
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			preparedStmt.setInt(1, (int) (drone.getBattery() - drone.getTime()*0.55)); //battery status is calculated by total air time
			preparedStmt.setInt(2, drone.getDroneId());
			preparedStmt.execute();
			conn.close();
			return true;
		}	catch (Exception e) {System.out.println(e);}
		return false;
	}

	public static void presentOrdersHistory() {
		Connection conn;
		String query;
		Statement st;
		ResultSet rs;
		try 
		  {
			conn = connClass.getConn();
			query = "SELECT * FROM orders";
		    st = conn.createStatement();
		    rs = st.executeQuery(query);
		    String columns[] = { "orderId", "sender_name", "sender_address", "reciever_name", "reciever_address", "droneId", "order_date" };
		    String data[][] = new String[250][7];
		    int i = 0;
		    while (rs.next()) {
		        String orderId = rs.getString("orderId");
		        String senderName = rs.getString("sender_name");
		        String senderAddress = rs.getString("sender_address");
		        String recieverName = rs.getString("reciever_name");
		        String recieverAddress = rs.getString("reciever_address");
		        String droneId = rs.getString("droneId");
		        String orderDate = rs.getString("order_date");
		        data[i][0] = orderId;
		        data[i][1] = senderName;
		        data[i][2] = senderAddress;
		        data[i][3] = recieverName;
		        data[i][4] = recieverAddress;
		        data[i][5] = droneId;
		        data[i][6] = orderDate;
		        i++;
		    }
		  JTable table = new JTable(data, columns);
		  table.setShowGrid(true);
		  table.setShowVerticalLines(true);
		  TableColumnModel tcm = table.getColumnModel();
		  tcm.getColumn(6).setPreferredWidth(300);
	  	  JScrollPane pane = new JScrollPane(table);
		  JFrame f = new JFrame("Orders History from Database");
		  JPanel panel = new JPanel();
	      panel.add(pane);
	      f.add(panel);
	      f.setSize(500,500);
	      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      f.setVisible(true);
		  f.setTitle("Orders History from Database");
		  f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      f.pack();
		  }	catch(SQLException e) {e.printStackTrace();} 
			catch (ClassNotFoundException e) {e.printStackTrace();}
	}
}

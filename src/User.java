import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;

public class User {
	private String userName, password, fname, lname, city, street, homeNo, phone, email;

	private int balance;
	
	public User(String userName, String password, String fname, String lname, 
			String city, String street, String homeNo, String phone, String email, int balance) {
		this.userName = userName;
		this.password = password;
		this.fname = fname;
		this.lname = lname;
		this.city = city;
		this.street = street;
		this.homeNo = homeNo;
		this.phone = phone;
		this.email = email;
		this.balance = balance;
	}
	public int getBalance() {
		return balance;
	}
	public String getPassword() {
		return password;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public String getUserName() {
		return userName;
	}
	public String getName() {
		return fname+" "+lname;
	}
	public String getAddress() {
		return city + ", " + street + " " + homeNo;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	public String toString() {
		return ("user name: "+userName+ "|"+" password: "+password+"|"+" fname: "+fname+"|"+" lname: "+lname+"|"+" address: "+
				street+", "+homeNo+", "+city+"|"+" phone: "+phone+"|"+" email: "+email+"|"+ 
				" deliveries balance: "+ balance);
	}
	
}

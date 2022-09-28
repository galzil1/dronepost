import java.util.*;
//users repository management class. 
//data structure for registered users chosen to be ArrayList

public class UsersManagement {
	public static ArrayList<User> users = new ArrayList();

	public static void printUsers() {
		for (int i=0; i<users.size(); i++) {
			System.out.println(users.get(i));
		}
	}
//////////////get user from array list//////////////
	public static User getUser(String userName) {
		for (int i = 0; i < users.size(); i++) {
			if (((User) users.get(i)).getUserName().equals(userName))
				return (User) users.get(i);
		}
		return null;
	}
//////////////update user credit at array list//////////////
	public static void updateCredit(User user, int credit) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getUserName().equals(user.getUserName()))
				users.get(i).setBalance(credit);
		}
	}
	
}
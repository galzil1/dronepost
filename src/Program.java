
public class Program {

	public static void main(String[] args) {
		DataBase.deleteUsers();  //initialize users DB
		DataBase.deleteDrones(); //initialize drones DB
		LoginForm lf = new LoginForm();
		lf.setVisible(true);
		DronesAssignment d = new DronesAssignment(); //construct new batch of drones, add to DB, add to queue of drones
		}

}

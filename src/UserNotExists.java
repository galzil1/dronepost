import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class UserNotExists extends Frame implements ActionListener{
	protected JButton notExists = new JButton("Wrong user name or password");
	
	public UserNotExists() {
		super("Error");
		setLayout(new FlowLayout());
		add(notExists);
		pack();
		notExists.addActionListener(this);
	}
	
	public void actionPerformed(ActionEvent ev) {
		String arg = ev.getActionCommand();
		if (arg.equals("Wrong user name or password")) {
			setVisible(false);
			dispose();
		}
	}
}

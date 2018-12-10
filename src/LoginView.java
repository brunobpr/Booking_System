import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SpringLayout.Constraints;
import javax.swing.WindowConstants;

public class LoginView extends JFrame{
	private Controller controller;	
	private JPanel main = new JPanel();
	private JLabel jl = new JLabel();
	private JTextField emailTF = new JTextField(20);
	private JPasswordField passwordTF = new JPasswordField(20);
	private ImageIcon icon = new ImageIcon();
	
	public LoginView (Controller control) {
		controller = control;
		createLoginFrame();
	}
	
	private void createLoginFrame() {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JButton loginButton = new JButton("Login");
		JButton signupButton = new JButton("Signup");
		Font font = new Font("verdana", Font.BOLD, 14);
		//Setting frame state
		setSize(800, 600);
		setVisible(true);
		setTitle("Login");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setLayout(gbl);
		add(jl = new JLabel(icon = new ImageIcon("icon.png")));
		add(main, c);
		add(jl = new JLabel(icon = new ImageIcon("icon.png")));
		main.setLayout(gbl);
		main.setBackground(Color.ORANGE);
		c.gridx=1;
		c.gridy=1;
		c.ipady = 20;
		main.add(jl = new JLabel("Enter your email:"), c);
		jl.setFont(font);
		c.gridx=1;
		c.gridy=2;
		c.ipady = 0;
		main.add(emailTF, c);
		c.gridx=1;
		c.gridy=3;
		c.ipady = 20;
		main.add(jl = new JLabel("Enter your password:"), c);
		jl.setFont(font);
		c.gridx=1;
		c.gridy=4;
		c.ipady = 0;
		main.add(passwordTF, c);
		c.gridx=1;
		c.gridy=5;
		c.ipady = 20;
		main.add(jl = new JLabel("Signup or Login"), c);
		c.gridx=0;
		c.gridy=5;
		c.ipady = 0;
		add(signupButton, c);
		c.gridx=2;
		c.gridy=5;
		add(loginButton, c);
		//Adding ActionListeners and Setting Action Commands to the buttons
		loginButton.setActionCommand("login");
		signupButton.setActionCommand("signup");
		loginButton.addActionListener(controller);
		signupButton.addActionListener(controller);
		validate();
		repaint();
	}

	 JTextField getEmailTF() {
		return emailTF;
	}

	public String getPasswordTF() {
		
		char[] c =  passwordTF.getPassword();
		String password = new String(c);
		return password;
	}

	public void setEmailTF(JTextField emailTF) {
		this.emailTF = emailTF;
	}

	public void setPasswordTF(JPasswordField passwordTF) {
		this.passwordTF = passwordTF;
	}
	
	
	
}

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class SignupView extends JFrame {
	private Controller controller;
	static JPanel customer = new JPanel();
	static JPanel provider = new JPanel();
	private JPanel footer = new JPanel();
	private JLabel jl;
	private JTextField firstName;
	private JTextField lastName;
	private JTextField email;
	private JTextField phone;
	private JTextField address;
	private JComboBox<String> city;
	private JComboBox<String> area;
	private JPasswordField password;
	private JPasswordField confPass;
	private Font font = new Font("verdana", Font.BOLD, 14);
	private JLabel hiden = new JLabel(" ");
	private JButton signupButton = new JButton("Signup");

	public SignupView(Controller control) {
		controller = control;
		createFrame();
	}

	public void createFrame() {
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JButton backButton = new JButton("Back");
		JButton customerButton = new JButton("Customer");
		JButton providerButton = new JButton("Provider");
		// Setting frame state
		setTitle("Signup");
		setSize(800, 600);
		setVisible(true);
		setLayout(gbl);
		// Customer panel and Provider panel must only be shown when the user choose one
		// option;
		add(customer);
		customer.setVisible(false);
		add(provider);
		provider.setVisible(false);
		c.gridx = 0;
		c.gridy = 1;
		add(footer, c);
		footer.add(backButton);
		footer.add(customerButton);
		footer.add(providerButton);
		footer.add(signupButton);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		// Adding ActionListeners and Setting Action Commands to the buttons
		backButton.addActionListener(controller);
		customerButton.addActionListener(controller);
		providerButton.addActionListener(controller);
		
		backButton.setActionCommand("backS");
		customerButton.setActionCommand("customer");
		providerButton.setActionCommand("provider");
	}

	public void createCustomer() {
		signupButton.setActionCommand("create");
		signupButton.addActionListener(controller);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		firstName = new JTextField(20);
		lastName = new JTextField(20);
		email = new JTextField(20);
		phone = new JTextField(20);
		password = new JPasswordField(20);
		confPass = new JPasswordField(20);
		customer.removeAll();
		provider.setVisible(false);
		provider.removeAll();
		customer.setVisible(true);
		customer.setLayout(gbl);
		customer.setBackground(Color.ORANGE);
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 20;
		c.ipadx = 5;
		customer.add(jl = new JLabel("You are signing up as a customer account!"), c);
		jl.setFont(font);
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 0;
		customer.add(jl = new JLabel("First name:"), c);
		c.gridx = 1;
		c.gridy = 1;
		customer.add(firstName, c);
		c.gridx = 0;
		c.gridy = 2;
		customer.add(jl = new JLabel("Last name:"), c);
		c.gridx = 1;
		c.gridy = 2;
		customer.add(lastName, c);
		c.gridx = 0;
		c.gridy = 3;
		customer.add(jl = new JLabel("Phone:"), c);
		c.gridx = 1;
		c.gridy = 3;
		customer.add(phone, c);
		c.gridx = 0;
		c.gridy = 4;
		customer.add(jl = new JLabel("Email:"), c);
		c.gridx = 1;
		c.gridy = 4;
		customer.add(email, c);
		c.gridx = 0;
		c.gridy = 5;
		customer.add(jl = new JLabel("Password:"), c);
		c.gridx = 1;
		c.gridy = 5;
		customer.add(password, c);
		c.gridx = 0;
		c.gridy = 6;
		customer.add(jl = new JLabel(" Confirm password:"), c);
		c.gridx = 1;
		c.gridy = 6;
		customer.add(confPass, c);
		c.gridx = 1;
		c.gridy = 7;
		provider.add(hiden, c);
	}

	public void createProvider() {
		signupButton.setActionCommand("create");
		signupButton.addActionListener(controller);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		String[] cities = new String[] { "Dublin", "Limerick", "Cork", "Galway", "Meath", "Waterford", "Wexford"};
		String[] areas = new String[] {"Outside Dublin", "D1", "D2", "D3", "D4", "D5", "D6", "D7", "D8", "D9", "D10", "D11", "D12",
				"D14", "D15", "D16" };
		provider.removeAll();
		customer.setVisible(false);
		customer.removeAll();
		provider.setVisible(true);
		provider.setBackground(Color.ORANGE);
		provider.setLayout(gbl);
		firstName = new JTextField(20);
		lastName = new JTextField(20);
		email = new JTextField(20);
		phone = new JTextField(20);
		address = new JTextField(20);
		password = new JPasswordField(20);
		confPass = new JPasswordField(20);
		city = new JComboBox<String>(cities);
		area = new JComboBox<String>(areas);
		c.gridx = 1;
		c.gridy = 0;
		c.ipady = 20;
		c.ipadx = 5;
		provider.add(jl = new JLabel("You are signing up as a provider account!"), c);
		jl.setFont(font);
		c.gridx = 0;
		c.gridy = 1;
		c.ipady = 0;
		provider.add(jl = new JLabel("First name:"), c);
		c.gridx = 1;
		c.gridy = 1;
		provider.add(firstName, c);
		c.gridx = 0;
		c.gridy = 2;
		provider.add(jl = new JLabel("Last name:"), c);
		c.gridx = 1;
		c.gridy = 2;
		provider.add(lastName, c);
		c.gridx = 0;
		c.gridy = 3;
		provider.add(jl = new JLabel("Phone:"), c);
		c.gridx = 1;
		c.gridy = 3;
		provider.add(phone, c);
		c.gridx = 0;
		c.gridy = 4;
		provider.add(jl = new JLabel("Email:"), c);
		c.gridx = 1;
		c.gridy = 4;
		provider.add(email, c);
		c.gridx = 0;
		c.gridy = 5;
		provider.add(jl = new JLabel("City:"), c);
		c.gridx = 1;
		c.gridy = 5;
		provider.add(city, c);
		c.gridx = 0;
		c.gridy = 6;
		provider.add(jl = new JLabel("Area:"), c);
		c.gridx = 1;
		c.gridy = 6;
		provider.add(area, c);
		c.gridx = 0;
		c.gridy = 7;
		provider.add(jl = new JLabel("Address:"), c);
		c.gridx = 1;
		c.gridy = 7;
		provider.add(address, c);
		c.gridx = 0;
		c.gridy = 8;
		provider.add(jl = new JLabel("Password:"), c);
		c.gridx = 1;
		c.gridy = 8;
		provider.add(password, c);
		c.gridx = 0;
		c.gridy = 9;
		provider.add(jl = new JLabel(" Confirm password:"), c);
		c.gridx = 1;
		c.gridy = 9;
		provider.add(confPass, c);
		c.gridx = 1;
		c.gridy = 9;
	}

	public JTextField getFirstName() {
		return firstName;
	}

	public JTextField getLastName() {
		return lastName;
	}

	public JTextField getEmail() {
		return email;
	}

	public JTextField getPhone() {
		return phone;
	}

	public JTextField getAddress() {
		return address;
	}

	public String getCity() {
		String a = (String) city.getSelectedItem();
		return a;
	}

	public String getArea() {
		String a = (String) area.getSelectedItem();
		return a;
	}

	public String getPassword() {
		char[] c = password.getPassword();
		String password = new String(c);
		return password;
	}

	public String getConfPass() {
		char[] c = confPass.getPassword();
		String password = new String(c);
		return password;
	}

	public void setFirstName(JTextField firstName) {
		this.firstName = firstName;
	}

	public void setLastName(JTextField lastName) {
		this.lastName = lastName;
	}

	public void setEmail(JTextField email) {
		this.email = email;
	}

	public void setPhone(JTextField phone) {
		this.phone = phone;
	}

	public void setAddress(JTextField address) {
		this.address = address;
	}

	public void setCity(JComboBox<String> city) {
		this.city = city;
	}

	public void setArea(JComboBox<String> area) {
		this.area = area;
	}

	public void setPassword(JPasswordField password) {
		this.password = password;
	}

	public void setConfPass(JPasswordField confPass) {
		this.confPass = confPass;
	}

	public JLabel getHiden() {
		return hiden;
	}

}

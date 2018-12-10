import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;

public class Admin extends JFrame {
	private Controller controller;
	private JLabel jl;
	private JTextField nameTF;
	private JPasswordField passwordTF;
	private JTextField emailTF;
	static JPanel bookingsPanel = new JPanel();
	static JPanel providersPanel = new JPanel();
	static JPanel newAdPanel = new JPanel();
	static JTable providers;
	static JScrollPane jsp;

	public Admin(Controller control) {
		controller = control;
	}

	public void createFrame() {
		jsp = new JScrollPane();
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JButton bookings = new JButton("Check Bookings");
		JButton pending = new JButton("Pending Providers");
		JButton logout = new JButton("Logout");
		JButton newAdmin = new JButton("Create New Admin");
		JPanel adminInfo = new JPanel();
		// Setting frame state
		setTitle("Admin");
		setSize(800, 600);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		adminInfo.setSize(new Dimension(200, 600));
		add(adminInfo, BorderLayout.NORTH);

		// User info is a Panel on the left hand side of the frame, it shows the user
		// information and holds a menu with buttons
		// Beginning of the Info Panel
		// -----------------------------------------------------------------------------------------------
		adminInfo.setLayout(gbl);
		c.gridx = 1;
		c.gridy = 0;
		adminInfo.add(bookings, c);
		c.gridx = 2;
		// adminInfo.add(users, c);
		c.gridx = 3;
		adminInfo.add(pending, c);
		c.gridx = 4;
		adminInfo.add(newAdmin, c);
		c.gridx = 5;
		adminInfo.add(logout, c);
		c.gridx = 0;
		c.gridy = 0;
		// End of the Panel
		// ------------------------------------------------------------------
		// Setting buttons, size, action listeners, action commands
		bookings.setPreferredSize(new Dimension(250, 40));
		pending.setPreferredSize(new Dimension(250, 40));
		newAdmin.setPreferredSize(new Dimension(250, 40));
		logout.setPreferredSize(new Dimension(250, 40));

		bookings.addActionListener(controller);
		pending.addActionListener(controller);
		newAdmin.addActionListener(controller);
		logout.addActionListener(controller);

		bookings.setActionCommand("all bookings");
		pending.setActionCommand("pending");
		newAdmin.setActionCommand("new admin");
		logout.setActionCommand("logout");

		validate();
		repaint();
	}

	public void bookings(JTable bookings) {
		add(bookingsPanel);
		bookingsPanel.setVisible(true);
		providersPanel.setVisible(false);
		newAdPanel.setVisible(false);
		JTable bookingsTable = bookings;
		jsp = new JScrollPane(bookingsTable);
		bookingsTable.setBorder(BorderFactory.createLineBorder(Color.black));
		bookingsTable.setShowGrid(true);
		bookingsTable.setShowHorizontalLines(true);
		bookingsTable.setShowVerticalLines(true);
		jsp.setPreferredSize(new Dimension(700, 550));
		bookingsPanel.add(jsp);
		jsp.repaint();
		validate();
		repaint();
	}

	public void showPending(JTable pendingProviders) {
		JButton save = new JButton("Save");
		save.addActionListener(controller);
		save.setActionCommand("save pending");
		add(providersPanel);
		bookingsPanel.setVisible(false);
		providersPanel.setVisible(true);
		newAdPanel.setVisible(false);
		providers = pendingProviders;
		jsp = new JScrollPane(pendingProviders);
		providers.setBorder(BorderFactory.createLineBorder(Color.black));
		providers.setShowGrid(true);
		providers.setShowHorizontalLines(true);
		providers.setShowVerticalLines(true);
		providersPanel.add(jl = new JLabel(
				"Approve or Reject each provider by setting the value of the column status and click "));
		providersPanel.add(save);
		providersPanel.add(jsp);
		jsp.setPreferredSize(new Dimension(800, 550));
		jsp.repaint();
		jsp.validate();
		validate();
		repaint();
	}

	public void newAdmin() {
		add(newAdPanel);
		bookingsPanel.setVisible(true);
		providersPanel.setVisible(false);
		newAdPanel.setVisible(true);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		newAdPanel.setLayout(gbl);
		JButton submit = new JButton("Submit");
		submit.addActionListener(controller);
		submit.setActionCommand("submit");
		nameTF = new JTextField(20);
		passwordTF = new JPasswordField(20);
		emailTF = new JTextField(20);
		newAdPanel.add(jl = new JLabel("Name:"), c);
		c.gridx = 1;
		newAdPanel.add(nameTF, c);
		c.gridx = 0;
		c.gridy = 1;
		newAdPanel.add(jl = new JLabel("Email:"), c);
		c.gridx = 1;
		c.gridy = 1;
		newAdPanel.add(emailTF, c);
		c.gridx = 0;
		c.gridy = 2;
		newAdPanel.add(jl = new JLabel("Password:"),c);
		c.gridx = 1;
		c.gridy = 2;
		newAdPanel.add(passwordTF, c);
		c.gridx = 0;
		c.gridy = 3;
		c.gridwidth = 2;
		newAdPanel.add(submit, c);
		validate();
		repaint();
	}

	public JTextField getNameTF() {
		return nameTF;
	}

	public JPasswordField getPasswordTF() {
		return passwordTF;
	}

	public JTextField getEmailTF() {
		return emailTF;
	}

	public void setNameTF(JTextField nameTF) {
		this.nameTF = nameTF;
	}

	public void setPasswordTF(JPasswordField passwordTF) {
		this.passwordTF = passwordTF;
	}

	public void setEmailTF(JTextField emailTF) {
		this.emailTF = emailTF;
	}

}

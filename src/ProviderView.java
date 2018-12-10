import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class ProviderView extends JFrame {
	private Controller controller;
	private JLabel jl;
	private ImageIcon icon = new ImageIcon();
	private Font font = new Font("verdana", Font.BOLD, 14);
	private JComboBox<String> days;
	private JTextArea timesSelected;
	private Provider provider;
	static JRadioButton confirmButton;
	static JRadioButton rejectButton;
	static JRadioButton completedButton;
	static JRadioButton missedButton;
	JScrollPane jspProvider = new JScrollPane();;
	JPanel availPanel = new JPanel();
	JPanel bookingsPanel = new JPanel();
	JPanel feedbackPanel = new JPanel();
	JPanel settingPanel = new JPanel();

	public ProviderView(Controller control) {
		controller = control;
	}

	public void createFrame(Provider prov) {
		provider = prov;
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JButton booking = new JButton("Manage Bookings");
		JButton availability = new JButton("Set Availability");
		JButton feedback = new JButton("Check Feedbacks");
		JButton logout = new JButton("Logout");
		JButton settings = new JButton("Account Settings");
		JPanel userInfo = new JPanel();
		// Setting frame state
		setTitle("Provider");
		setSize(800, 600);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		userInfo.setSize(new Dimension(200, 600));
		add(userInfo, BorderLayout.WEST);
		// Only the providers are approved they will be able to use the system
		// User info is a Panel on the left hand side of the frame
		// information and holds a menu with buttons
		// Beginning of the Info Panel
		// -----------------------------------------------------------------------------------------------
		if (provider.getStatus().equals("Approved")) {
			userInfo.setLayout(gbl);
			userInfo.setBackground(Color.ORANGE);
			userInfo.add(jl = new JLabel(icon = new ImageIcon("provider.png")));
			c.gridx = 0;
			c.gridy = 1;
			userInfo.add(jl = new JLabel(
					"    Welcome, " + provider.getFirst_name() + " " + provider.getLast_name() + ".    "), c);
			jl.setFont(font);
			c.gridy = 2;
			userInfo.add(jl = new JLabel("Phone: " + provider.getPhone()), c);
			c.gridy = 3;
			c.ipady = 30;
			userInfo.add(jl = new JLabel("Email: " + provider.getEmail()), c);
			c.gridy = 4;
			c.ipady = 0;
			userInfo.add(availability, c);
			c.gridy = 5;
			userInfo.add(booking, c);
			c.gridy = 6;
			userInfo.add(feedback, c);
			c.gridy = 7;
			// userInfo.add(settings, c);
			c.gridy = 8;
			userInfo.add(logout, c);
			c.gridy = 9;
			userInfo.add(jl = new JLabel("Bruno Pereira Ribeiro, 2017138 \n CCT Dublin"), c);
			jl.setFont(font = new Font("verdana", Font.ITALIC, 8));
		} else {
			// Otherwise, they will be ask to wait and logout button
			setLayout(gbl);
			setForeground(Color.ORANGE);
			c.gridx = 0;
			c.gridy = 0;
			userInfo.add(jl = new JLabel(
					"    Welcome, " + provider.getFirst_name() + " " + provider.getLast_name() + ".    "), c);
			jl.setFont(font);
			c.gridy = 2;
			userInfo.add(jl = new JLabel("Phone: " + provider.getPhone()), c);
			c.gridy = 3;
			c.ipady = 30;
			userInfo.add(jl = new JLabel("Email: " + provider.getEmail()), c);
			c.gridy = 4;
			c.ipady = 0;
			add(jl = new JLabel("Status of the registration: " + provider.getStatus()), c);
			jl.setFont(font);
			if (provider.getStatus().equals("Pending")) {
				c.gridx = 0;
				c.gridy = 5;
				add(jl = new JLabel("Thank you for joining us,"), c);
				c.gridy = 6;
				add(jl = new JLabel("In order to make a reliable booking system,"), c);
				c.gridy = 7;
				add(jl = new JLabel("one administrator must approve your registration."), c);
				c.gridy = 8;
				add(jl = new JLabel("We will be pleased to approve you as soon as possible."), c);
				c.gridy = 9;
			} else {
				c.gridy = 5;
				add(jl = new JLabel("Thank your, for your patience."), c);
				c.gridy = 6;
				add(jl = new JLabel("Unfortunately we could not accept your registration! "), c);
			}
			c.gridy = 9;
			add(logout, c);
		}
		// End of the Info Panel
		// ------------------------------------------------------------------
		// Setting buttons, size, action listeners, action commands
		availability.setPreferredSize(new Dimension(250, 40));
		booking.setPreferredSize(new Dimension(250, 40));
		feedback.setPreferredSize(new Dimension(250, 40));
		settings.setPreferredSize(new Dimension(250, 40));
		logout.setPreferredSize(new Dimension(250, 40));

		availability.addActionListener(controller);
		feedback.addActionListener(controller);
		booking.addActionListener(controller);
		settings.addActionListener(controller);
		logout.addActionListener(controller);

		availability.setActionCommand("availability");
		booking.setActionCommand("manageBooking");
		feedback.setActionCommand("feedback");
		settings.setActionCommand("settings");
		logout.setActionCommand("logout");
	}

	public void setAvailability(List<String> available, int index, List<String> unavailable) {
		// Making sure that only one panel will be shown
		add(availPanel);
		availPanel.setVisible(true);
		bookingsPanel.setVisible(false);
		feedbackPanel.setVisible(false);
		settingPanel.setVisible(false);
		String[] dates = new String[15];
		// Creating three panels
		JPanel AvailableSlotsPanel = new JPanel();
		JPanel UnavailableSlotsPanel = new JPanel();
		JPanel footer = new JPanel();
		JButton save = new JButton("Save");
		JButton reset = new JButton("Reset");
		Calendar today = Calendar.getInstance();
		// Setting the time slots from todays date as it is not possible to manage the
		// past slots
		Date date = today.getTime();
		// Formating the date
		DateFormat day = new SimpleDateFormat("EEEE");
		DateFormat df = new SimpleDateFormat("dd");
		DateFormat month = new SimpleDateFormat("MM");
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		availPanel.setLayout(gbl);
		for (int i = 0; i < 14; i++) {
			// The formated date will be shown as ' Tuesday | 23/04 '
			dates[i] = day.format(date) + " | " + df.format(date) + "/" + month.format(date);
			today.add(Calendar.DAY_OF_YEAR, 1);
			date = today.getTime();
		}
		// Adding the formated date do a combobox and setting actionlistener to it
		days = new JComboBox<String>(dates);
		days.setSelectedIndex(index);
		days.addActionListener(controller);
		days.setActionCommand("updateSlots");
		AvailableSlotsPanel.setLayout(new GridLayout(6, 1));
		UnavailableSlotsPanel.setLayout(new GridLayout(6, 1));
		// Loop to create jbuttons in the form of time slots
		for (int i = 0; i < available.size(); i++) {
			JButton timeSlotsJB = new JButton(available.get(i));
			AvailableSlotsPanel.add(timeSlotsJB);
			timeSlotsJB.addActionListener(controller);
			timeSlotsJB.setActionCommand("lock");
		}
		for (int i = 0; i < unavailable.size(); i++) {
			JButton timeSlotsJB = new JButton(unavailable.get(i));
			UnavailableSlotsPanel.add(timeSlotsJB);
			timeSlotsJB.addActionListener(controller);
			timeSlotsJB.setActionCommand("unlock");
		}
		// Text area will hold all the alterations made in the availability
		// It will be hidden, but for each TIME button pressed, a new line will be
		// insert into the text field;
		// For example the item selected in the JComboBox is Tuesday | 23/04
		// And the button pressed is 16:30
		// A new line is added to the text areas as "
		timesSelected = new JTextArea(100, 200);
		timesSelected.setEditable(false);
		timesSelected.setMinimumSize(new Dimension(20, 20));
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		availPanel.add(days, c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 0;
		availPanel.add(jl = new JLabel("Available Slots"), c);
		jl.setFont(font = new Font("Verdana", Font.BOLD, 12));
		c.gridx = 0;
		c.gridy = 2;
		availPanel.add(AvailableSlotsPanel, c);
		c.gridx = 0;
		c.gridy = 3;
		availPanel.add(jl = new JLabel("Unavailable Slots"), c);
		jl.setFont(font = new Font("Verdana", Font.BOLD, 12));
		c.gridx = 0;
		c.gridy = 4;
		availPanel.add(UnavailableSlotsPanel, c);
		c.gridx = 0;
		c.gridy = 5;
		c.gridwidth = 1;
		// availPanel.add(jsp, c);
		c.gridx = 0;
		c.gridy = 6;
		c.weightx = 100;
		availPanel.add(footer, c);
		footer.add(reset);
		footer.add(save);

		reset.setActionCommand("reset");
		reset.addActionListener(controller);
		save.addActionListener(controller);
		save.setActionCommand("save");
		validate();
		repaint();
	}

	/**
	 * @param bookings
	 *            bookings[c][0] = Customer's first Name bookings[c][1] = Customer's
	 *            last Name bookings[c][2] = time of the booking bookings[c][3] =
	 *            day bookings[c][4] = date bookings[c][5] = customer's phone
	 *            bookings[c][6] = status
	 */
	public void manageBookings(String[][] bookings) {
		add(bookingsPanel);
		availPanel.setVisible(false);
		bookingsPanel.setVisible(true);
		feedbackPanel.setVisible(false);
		settingPanel.setVisible(false);
		String[] status = { "Confirm", "Reject", "Completed", "Missed" };
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		bookingsPanel.setLayout(gbl);
		confirmButton = new JRadioButton(status[0]);
		confirmButton.setSelected(true);
		rejectButton = new JRadioButton(status[1]);
		completedButton = new JRadioButton(status[2]);
		missedButton = new JRadioButton(status[3]);
		ButtonGroup group = new ButtonGroup();
		group.add(confirmButton);
		group.add(rejectButton);
		group.add(missedButton);
		group.add(completedButton);
		jspProvider = new JScrollPane(bookingsPanel);
		jspProvider.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspProvider.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jspProvider);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		bookingsPanel.add(jl = new JLabel("Choose one status and click on the booking."), c);
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		bookingsPanel.add(jl = new JLabel("Pending Bookings"), c);
		jl.setFont(font = new Font("Verdana", Font.BOLD, 15));
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		bookingsPanel.add(confirmButton, c);
		c.gridx = 1;
		c.gridy = 2;
		bookingsPanel.add(rejectButton, c);
		for (int i = 0; i <= bookings.length - 1; i++) {
			if (bookings[i][6].equals("Pending")) {
				JButton jb = new JButton("No:" + bookings[i][7] + " | " + bookings[i][0] + " " + bookings[i][1] + " on "
						+ bookings[i][4] + " at " + bookings[i][2]);
				c.gridx = 0;
				c.gridy = 4 + i;
				c.gridwidth = 2;
				bookingsPanel.add(jb, c);
				jb.setPreferredSize(new Dimension(350, 60));
				jb.addActionListener(controller);
				jb.setActionCommand("booking chosen");
			}
		}
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		c.gridwidth = 2;
		bookingsPanel.add(jl = new JLabel("Confirmed Bookings"), c);
		jl.setFont(font = new Font("Verdana", Font.BOLD, 15));
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = c.gridy + 1;
		bookingsPanel.add(completedButton, c);
		c.gridx = 1;
		c.gridy = c.gridy;
		bookingsPanel.add(missedButton, c);
		for (int l = 0; l <= bookings.length - 1; l++) {
			if (bookings[l][6].equals("Confirmed")) {
				JButton jb = new JButton("No:" + bookings[l][7] + " | " + bookings[l][0] + " " + bookings[l][1] + " on "
						+ bookings[l][4] + " at " + bookings[l][2]);
				c.gridx = 0;
				c.gridy = c.gridy + 1 + l;
				c.gridwidth = 2;
				bookingsPanel.add(jb, c);
				jb.setPreferredSize(new Dimension(350, 60));
				jb.addActionListener(controller);
				jb.setActionCommand("booking chosen");
			}
		}
		validate();
		repaint();
	}

	/**
	 * @param reviews
	 *            a list of string containing all feedbacks from the database
	 */
	public void showFeedbackPanel(List<String> reviews) {
		add(feedbackPanel);
		feedbackPanel.setLayout(new BoxLayout(feedbackPanel, BoxLayout.Y_AXIS));
		availPanel.setVisible(false);
		bookingsPanel.setVisible(true);
		feedbackPanel.setVisible(true);
		settingPanel.setVisible(false);
		jspProvider = new JScrollPane(feedbackPanel);
		jspProvider.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		jspProvider.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(jspProvider);
		if (reviews.size() == 0) {
			feedbackPanel.add(jl = new JLabel("You have 0 feedback"));
			jl.setFont(font = new Font("Verdana", Font.BOLD, 15));
		} else if (reviews.size() == 1) {
			feedbackPanel.add(jl = new JLabel("You have 1 feedback"));
			jl.setFont(font = new Font("Verdana", Font.BOLD, 15));
		} else {
			feedbackPanel.add(jl = new JLabel("You have " + reviews.size() + " feedbacks"));
			jl.setFont(font = new Font("Verdana", Font.BOLD, 15));
		}
		for (int i = 0; i <= reviews.size() - 1; i++) {
			feedbackPanel.add(jl = new JLabel("No. " + (i + 1)));
			jl.setFont(font = new Font("Verdana", Font.BOLD, 15));
			jl.setFont(font);
			feedbackPanel.add(jl = new JLabel("'" + reviews.get(i) + "'"));
			jl.setFont(font = new Font("Verdana", Font.ITALIC, 15));
		}
		validate();
		repaint();
	}

	public JTextArea getTimesSelected() {
		return timesSelected;
	}

	public void setTimesSelected(String date) {
		this.timesSelected.setText(date + "\n" + this.timesSelected.getText());
	}

	public void setDays(JComboBox<String> days) {
		this.days = days;
	}

	public void setDays(int index) {
		this.days.setSelectedIndex(index);
	}

	public JComboBox<String> getDays() {
		return this.days;
	}

	public void resetTimesSelected() {
		this.timesSelected.setText("");
	}

}

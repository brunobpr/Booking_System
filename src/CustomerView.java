import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * @author Bruno Ribeiro 
 *
 */
public class CustomerView extends JFrame {
	private Controller controller;
	private JLabel jl;
	private ImageIcon icon = new ImageIcon();
	private JTextField nameTF = new JTextField(20);
	private JComboBox<String> areas;
	private JComboBox<String> days;
	private JTextArea reviewTF = new JTextArea(10, 30);
	private JComboBox<Object> reviewProviderCB;
	private Customer customer;
	JPanel reviewPanel = new JPanel();
	JPanel settingPanel = new JPanel();
	private Font font = new Font("verdana", Font.BOLD, 14);
	private JPanel timeSlots;
	JPanel bookingPanel = new JPanel();
	JPanel findPanel = new JPanel();
	JPanel providers = new JPanel();
	JScrollPane jcp = new JScrollPane();

	public CustomerView(Controller control) {
		controller = control;
	}

	/**
	 * @param cust a Object of type Customer holding the customer's info.
	 */
	public void createFrame(Customer cust) {
		customer = cust;
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JButton booking = new JButton("Upcoming appointments");
		JButton find = new JButton("Find a Barber or Hairdresser");
		JButton review = new JButton("Make a review");
		JButton logout = new JButton("Logout");
		JButton settings = new JButton("Account Settings");
		JPanel userInfo = new JPanel();
		// Setting frame state
		setTitle("Customer");
		setSize(800, 600);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setVisible(true);
		userInfo.setSize(new Dimension(200, 600));
		add(userInfo, BorderLayout.WEST);

		// User info is a Panel on the left hand side of the frame, it shows the user
		// information and holds a menu with buttons
		// Beginning of the Info Panel
		// -----------------------------------------------------------------------------------------------
		userInfo.setLayout(gbl);
		userInfo.setBackground(Color.ORANGE);
		userInfo.add(jl = new JLabel(icon = new ImageIcon("user.png")));
		c.gridx = 0;
		c.gridy = 1;
		userInfo.add(jl = new JLabel("    Welcome, " + customer.getFirst_name() + " " + customer.getLast_name()+ ".    "), c);
		jl.setFont(font);
		c.gridx = 0;
		c.gridy = 2;
		userInfo.add(jl = new JLabel("Phone: " + customer.getPhone()), c);
		c.gridx = 0;
		c.gridy = 3;
		c.ipady = 30;
		userInfo.add(jl = new JLabel("Email: " + customer.getEmail()), c);
		c.gridx = 0;
		c.gridy = 4;
		c.ipady = 0;
		userInfo.add(find, c);
		c.gridx = 0;
		c.gridy = 5;
		userInfo.add(booking, c);
		c.gridx = 0;
		c.gridy = 6;
		userInfo.add(review, c);
		c.gridx = 0;
		c.gridy = 7;
		// userInfo.add(settings, c);
		c.gridx = 0;
		c.gridy = 8;
		userInfo.add(logout, c);
		c.gridx = 0;
		c.gridy = 9;
		userInfo.add(jl = new JLabel("Bruno Pereira Ribeiro, 2017138 \n CCT Dublin"), c);
		jl.setFont(font = new Font("verdana", Font.ITALIC, 8));
		// End of the Info Panel
		// ------------------------------------------------------------------
		// Setting buttons, size, action listeners, action commands
		find.setPreferredSize(new Dimension(250, 40));
		booking.setPreferredSize(new Dimension(250, 40));
		review.setPreferredSize(new Dimension(250, 40));
		settings.setPreferredSize(new Dimension(250, 40));
		logout.setPreferredSize(new Dimension(250, 40));

		find.addActionListener(controller);
		review.addActionListener(controller);
		booking.addActionListener(controller);
		settings.addActionListener(controller);
		logout.addActionListener(controller);

		find.setActionCommand("find");
		booking.setActionCommand("booking");
		review.setActionCommand("review");
		settings.setActionCommand("settings");
		logout.setActionCommand("logout");

		validate();
		repaint();
	}

	/**
	 * This method will create a panel with multiple panels inside of it, the number
	 * of panels inserted into the booking Panel will be based in how many bookings
	 * the customer has
	 * 
	 * @param bookings[]
	 *            bookings[x][0] first name of the provider bookings[x][1] = last
	 *            name of the provider bookings[x][2] = time of the booking, HH:MM
	 *            bookings[c][3] = day of the week of the booking; TUESDAY
	 *            bookings[c][4] = date of the booking, DD/MM bookings[c][5] =
	 *            status of the booking bookings[c][6] = address of the provider;
	 *            bookings[c][7] = city of the provider bookings[c][8] = reference
	 *            number of the booking;
	 */
	public void showBookingPanel(String[][] bookings) {
		add(bookingPanel);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		// Making sure that only the requested panel is shown to the user
		reviewPanel.setVisible(false);
		settingPanel.setVisible(false);
		bookingPanel.setVisible(true);
		findPanel.setVisible(false);
		jcp = new JScrollPane(bookingPanel);
		bookingPanel.setLayout(new GridLayout(bookings.length, 1));
		jcp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		jcp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		// This loop creates panels to add information of the upcoming bookings, its
		// size is defined with the number of bookings
		// So this panel has a dynamic size
		for (int i = 0; i <= bookings.length - 1; i++) {
			JButton cancel = new JButton("Cancel Booking No." + bookings[i][8]);
			JPanel panel = new JPanel();
			panel.setLayout(gbl);
			c.gridx = 0;
			c.gridy = 0;
			panel.add(jl = new JLabel(bookings[i][0] + " " + bookings[i][1]), c);
			jl.setFont(font = new Font("Verdana", Font.BOLD, 14));
			c.gridx = 0;
			c.gridy = 1;
			panel.add(jl = new JLabel(bookings[i][2]), c);
			jl.setFont(font = new Font("Verdana", Font.BOLD, 26));
			c.gridx = 0;
			c.gridy = 2;
			panel.add(jl = new JLabel(bookings[i][3]), c);
			jl.setFont(font = new Font("Verdana", Font.BOLD, 20));
			c.gridx = 0;
			c.gridy = 3;
			panel.add(jl = new JLabel(bookings[i][4]), c);
			jl.setFont(font = new Font("Verdana", Font.BOLD, 14));
			panel.setPreferredSize(new Dimension(150, 150));
			c.gridx = 0;
			c.gridy = 4;
			panel.add(jl = new JLabel(bookings[i][6] + ", " + bookings[i][7] + "."), c);
			jl.setFont(font = new Font("Verdana", Font.BOLD, 14));
			panel.setPreferredSize(new Dimension(150, 150));
			c.gridx = 0;
			c.gridy = 5;
			panel.add(cancel, c);
			cancel.addActionListener(controller);
			cancel.setActionCommand("cancel");
			bookingPanel.add(panel);
			// If status of the booking is CONFIRMED a green border will be added.
			if (bookings[i][5].equals("Confirmed")) {
				panel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 4));
			} else {
				panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}
		}
		add(jcp);
		validate();
		repaint();
	}

	// ---------------------------------Making a booking step by step ---
	// First the customer must search one providers by typing the name or by
	// selecting an area
	// Step one search a provider
	/**
	 * @param areaList
	 *            contains areas where providers are available
	 */
	public void showFindPanel(String[] areaList) {
		add(findPanel);
		GridBagLayout gbl = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		JPanel search = new JPanel();
		JButton searchButton = new JButton("Search");
		areas = new JComboBox<String>(areaList);
		// Making sure that only the requested panel is shown to the user
		reviewPanel.setVisible(false);
		settingPanel.setVisible(false);
		bookingPanel.setVisible(false);
		findPanel.setVisible(true);
		findPanel.add(search, BorderLayout.NORTH);
		search.setLayout(gbl);
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 1;
		search.add(jl = new JLabel("Search for a Barber or Hairdresser!"));
		jl.setFont(font = new Font("Verdana", Font.BOLD, 16));
		c.gridx = 0;
		c.gridy = 1;
		search.add(nameTF, c);
		c.gridx = 1;
		c.gridy = 1;
		search.add(areas, c);
		c.gridx = 3;
		c.gridy = 1;
		search.add(searchButton, c);
		searchButton.addActionListener(controller);
		searchButton.setActionCommand("search");
		validate();
		repaint();
	}

	/**
	 * Step 2 It will be displayed all the results in jbuttons
	 * 
	 * @param searchProvider
	 *            it a list of providers that match with the search done.
	 */
	public void showProviders(List<String> searchProvider) {
		providers = new JPanel();
		// Making sure that only the requested panel is shown to the user
		findPanel.setVisible(true);
		findPanel.add(providers, BorderLayout.NORTH);
		providers.setLayout(new BoxLayout(providers, BoxLayout.Y_AXIS));
		if (searchProvider.isEmpty()) {
			providers.add(new JLabel("0 Barbers or Hairddresser was found. Try again!"));
		} else {
			for (int i = 0; i <= searchProvider.size() - 1; i++) {
				JButton provList = new JButton(searchProvider.get(i));
				providers.add(provList);
				provList.setMinimumSize(new Dimension(500, 500));
				provList.addActionListener(controller);
				provList.setActionCommand("provider selected");
			}
		}
		validate();
		repaint();
	}

	/**
	 * @param buttonName
	 *            is the string which hold info of the provider selected, it will be
	 *            store in a
	 */
	public void showDates(String buttonName) {
		JPanel datesPanel = new JPanel();
		timeSlots = new JPanel();
		String[] dates = new String[15];
		JButton next = new JButton("Next");
		// Making sure that only the requested panel is shown to the user
		datesPanel.setVisible(true);
		Calendar today = Calendar.getInstance();
		Date date = today.getTime();
		DateFormat day = new SimpleDateFormat("EEEE");
		DateFormat df = new SimpleDateFormat("dd");
		DateFormat month = new SimpleDateFormat("MM");
		for (int i = 0; i < 14; i++) {
			dates[i] = day.format(date) + " | " + df.format(date) + "/" + month.format(date);
			today.add(Calendar.DAY_OF_YEAR, 1);
			date = today.getTime();
		}
		days = new JComboBox<String>(dates);
		datesPanel.add(days);
		datesPanel.add(next);
		findPanel.add(timeSlots);
		findPanel.add(datesPanel, BorderLayout.NORTH);
		next.addActionListener(controller);
		next.setActionCommand("show times");
		revalidate();
		repaint();
	}

	public void showTimeSlots(List<String> slots) {
		findPanel.add(jl = new JLabel("Select one time available!"));
		jl.setFont(font = new Font("Verdana", Font.BOLD, 16));
		for (int i = 0; i < slots.size() - 1; i++) {
			JButton timeSlotsJB = new JButton(slots.get(i));
			timeSlots.add(timeSlotsJB);
			timeSlotsJB.addActionListener(controller);
			timeSlotsJB.setActionCommand("make booking");
		}
		timeSlots.setLayout(new GridLayout(6, 4));
		timeSlots.setVisible(true);
		findPanel.add(timeSlots);
		validate();
		repaint();
	}

	/**
	 * This method will create a panel containing a TextArea and a Button The
	 * customers see all providers that they've had a booking completed Writes a
	 * review and click in the button submit.
	 * 
	 * @param bookings[][]
	 *            booking information
	 * 
	 */
	public void showReviewPanel(String[][] bookings) {
		
		add(reviewPanel);
		reviewPanel.setVisible(true);
		settingPanel.setVisible(false);
		bookingPanel.setVisible(false);
		findPanel.setVisible(false);
		JButton submit = new JButton("Submit");
		String[] providers = new String[bookings.length];
		if (bookings[0][5].isEmpty()) {
			reviewPanel.add(jl = new JLabel("You must complete an appointment before review it."));
			jl.setFont(font = new Font("Verdana", Font.BOLD, 16));
		} else {
			for (int i = 0; i < providers.length - 1; i++) {
				if (bookings[i][5].equals("Completed"))
					providers[i] = bookings[i][0] + " " + bookings[i][1] + ", " + bookings[i][7] + "|" + bookings[i][4];
			}
		reviewPanel.add(jl = new JLabel("Select one of your past bookings to review: "));
		jl.setFont(font = new Font("Verdana", Font.BOLD, 16));
		reviewProviderCB = new JComboBox<Object>(providers);
		reviewPanel.add(reviewProviderCB);
		reviewPanel.add(reviewTF);
		reviewPanel.add(submit);
		submit.setActionCommand("submit review");
		submit.addActionListener(controller);
		}
		validate();
		repaint();
	}

	public JTextField getNameTF() {
		return nameTF;
	}

	public void setNameTF(JTextField nameTF) {
		this.nameTF = nameTF;
	}

	public JComboBox<String> getAreas() {
		return areas;
	}

	public void setAreas(JComboBox<String> areas) {
		this.areas = areas;
	}

	public JComboBox<String> getDays() {
		return days;
	}

	public void setDays(JComboBox<String> days) {
		this.days = days;
	}

	public JTextArea getReviewTF() {
		return reviewTF;
	}

	public void setReviewTF(JTextArea reviewTF) {
		this.reviewTF = reviewTF;
	}

	public JComboBox<Object> getReviewProviderCB() {
		return reviewProviderCB;
	}

	public void setReviewProviderCB(JComboBox<Object> reviewProviderCB) {
		this.reviewProviderCB = reviewProviderCB;
	}

}

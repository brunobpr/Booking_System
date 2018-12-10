import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;

import org.apache.commons.codec.digest.DigestUtils;

public class Model {
	String dbServer = "jdbc:mysql://localhost/BrunoRibeiro_2017138";
	String user = "root";
	String password = "";
	ResultSet rs = null;
	Connection conn;
	Statement stmt;
	String[] bookingInfo = new String[5];
	Provider provider = new Provider();
	Customer customer = new Customer();
	public Model() {

		try {
			conn = DriverManager. getConnection(dbServer, user, password);

			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @param email
	 *            holds the email entered in the login
	 * @return array filled with the customers information
	 */
	public Customer getCustomerInfo() {
		String[] info = new String[10];
		String query = "SELECT * FROM customers WHERE email_cust='" + customer.getEmail() + "';";
		try {
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				customer.setFirst_name(rs.getString("first_name"));
				customer.setLast_name(rs.getString("last_name"));
				customer.setPhone(rs.getString("phone"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}

	/**
	 * @param password
	 *            string extracted from a JPasswordField and converted into a string
	 * @param email
	 *            email entered in a JTextField
	 * @return a integer o value 1, 2, 3 or 0.
	 * @return 1 if the email is found in the CUSTOMERS table. 2 if the email is
	 *         found in the PROVIDERS table. 3 if the email belongs to a
	 *         administrator. 0 if the email is not found.
	 */
	public int login(String password, String email) {
		int selected = 0;
		String hashPassword = DigestUtils.md5Hex(password);
		String query = "SELECT * FROM customers";
		// The program must differentiate the type of user logging in
		try {
			rs = stmt.executeQuery(query);
			System.out.println(query);
			while (rs.next()) {
				if (email.equals(rs.getString("email_cust")) && hashPassword.equals(rs.getString("password"))) {
					selected = 1;
					customer.setEmail(rs.getString("email_cust"));
				}
			}
			query = "SELECT * FROM providers";
			rs = stmt.executeQuery(query);
			System.out.println(query);
			while (rs.next()) {
				if (email.equals(rs.getString("email_prov")) && hashPassword.equals(rs.getString("password"))) {
					selected = 2;
					provider.setEmail(rs.getString("email_prov"));
				
				}
			}
			query = "SELECT * FROM admin";
			rs = stmt.executeQuery(query);
			System.out.println(query);
			while (rs.next()) {
				if (email.equals(rs.getString("email")) && hashPassword.equals(rs.getString("password"))) {
					selected = 3;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return selected;

	}

	/**
	 * The signup() method INSERTS the information provided INTO the database
	 * 
	 * @param firstName
	 *            Customer's first name
	 * @param lastName
	 *            Customer's last name
	 * @param phone
	 *            Customer's phone number
	 * @param email
	 *            Customer's email address
	 * @param password
	 *            Customer's password
	 */
	public String signup(String firstName, String lastName, String phone, String email, String password) {
		String hashPassword = DigestUtils.md5Hex(password);
		String query = "SELECT * FROM customers";
		String message = "";
		Boolean repitedEntry = true;
		Validation validation = new Validation();

		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				if (email.equals(rs.getString("email_cust"))) {
					message = "User already exists!";
					repitedEntry = false;
				}
			}
			if (repitedEntry) {
				query = "INSERT INTO `customers` (`first_name`, `last_name`, `email_cust`, `phone`, `password`) VALUES "
						+ "('" + firstName + "', " + "'" + lastName + "'," + " '" + email + "', '" + phone + "', '"
						+ hashPassword + "')";
				System.out.println(query);
				stmt.executeUpdate(query);
				message = "User registered!";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;
	}

	/**
	 * * The signup() method INSERTS the information provided INTO the database
	 * 
	 * @param firstName
	 *            Provider's first name
	 * @param lastName
	 *            Provider's last name
	 * @param phone
	 *            Provider's phone number
	 * @param email
	 *            Provider's email address
	 * @param password
	 *            Provider's password
	 * @param city
	 *            Provider's city
	 * @param area
	 *            Provider's area
	 * @param address
	 *            Provider's address
	 */
	public String signup(String firstName, String lastName, String phone, String email, String password, String city,
			String area, String address) {
		String hashPassword = DigestUtils.md5Hex(password);
		String query = "SELECT * FROM providers";
		String message = "";
		Boolean repitedEntry = true;
		try {
			rs = stmt.executeQuery(query);
			System.out.println(query);
			while (rs.next()) {
				if (email.equals(rs.getString("email_prov"))) {
					message = "User already exists!";
					repitedEntry = false;
				}
			}
			if (repitedEntry) {
				query = "INSERT INTO `providers` (`first_name`, `last_name`, `phone`, `email_prov`, `password`, `city`, `area`, `address`, `status`) VALUES "
						+ "('" + firstName + "', " + "'" + lastName + "', " + "'" + phone + "', " + "'" + email + "', "
						+ "'" + hashPassword + "', " + "'" + city + "', " + "'" + area + "', " + "'" + address
						+ "', 'Pending')";
				System.out.println(query);
				stmt.executeUpdate(query);
				message = "User registered!";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;

	}

	/**
	 * @param p1
	 *            Password that will be inserted into the database
	 * @param p2
	 *            Password to confirm that the user typed the right password this
	 *            method will return a true value if the two passwords are equal
	 */
	public static boolean comparePassword(String p1, String p2) {
		if (p1.equals(p2)) {
			return true;
		}
		return false;
	}

	// -----------------------------BookingPanel------CostumerView---------

	/**
	 * @param email
	 *            is a string which contains the email of the current customer
	 * @return
	 */
	public String[][] getCustomerBookings() {
		int count = 0;
		// Couting how many bookings the customer has to define the size of the list
		String query = "SELECT COUNT(email_cust) AS 'total' FROM bookings WHERE email_cust='" + customer.getEmail() + "';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// eg the user has 3 bookings, so the 2D array will be create with the size
		// [3][10]
		String[][] bookings = new String[count][10];
		query = "SELECT *, providers.first_name, providers.last_name, providers.address FROM bookings INNER JOIN providers ON providers.email_prov=bookings.email_prov WHERE email_cust='"
				+ customer.getEmail() + "' ORDER BY date;";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			int c = 0;
			while (rs.next()) {
				// All the infomation need to show the bookings will be stored in the array
				bookings[c][0] = rs.getString("first_name");
				bookings[c][1] = rs.getString("last_name");
				bookings[c][2] = rs.getString("time");
				Date date = rs.getDate("date");
				DateFormat day = new SimpleDateFormat("EEEE");
				DateFormat df = new SimpleDateFormat("dd");
				DateFormat month = new SimpleDateFormat("MM");
				// Formarting dates to TUESDAY, DD/MM.
				bookings[c][3] = day.format(date); // TUESDAY
				bookings[c][4] = df.format(date) + "/" + month.format(date); // dd/mm
				bookings[c][5] = rs.getString("status");
				bookings[c][6] = rs.getString("address");
				bookings[c][7] = rs.getString("city");
				bookings[c][8] = rs.getString("ref_num");
				c++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookings;
	}

	/**
	 * @param buttonName
	 *            the name of the cancel button contains the reference number of the
	 *            booking
	 */
	public void cancel(String buttonName) {
		// This reference number is used to cancel the booking chosen
		String refNum = buttonName.substring(buttonName.indexOf(".") + 1);
		String query = "DELETE FROM bookings WHERE ref_num='" + refNum + "';";
		try {
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -------------------FindPanel------CostumerView-------Making a booking--
	/**
	 * @param text
	 *            the string entered in the textfield in the find a baber panel
	 * @param location
	 *            the location chosen by the user
	 * @return a List of strings which are going to fill JButtons with search result
	 */
	public List<String> searchProvider(String text, String location) {
		List<String> providers = new ArrayList<>();
		String text2 = null;
		String text1 = null;
		String areas = location;
		// The user can enter full names, so the program must check that
		if (text.contains(" ")) {
			text1 = text.substring(0, text.indexOf(" "));
			text2 = text.substring(text.lastIndexOf(" ") + 1);
		} else {
			text1 = text;
			text2 = text;
		}
		// Only approved providers can receive bookings, therefore only approved
		// providers can be shown
		String query = "SELECT * FROM providers WHERE status='approved' AND first_name IN('" + text1 + "', '" + text2
				+ "');";
		// The program will first see if the textfield is empty, it is the first
		// parameter of the search
		// If is empty the location selected is used in the search
		if (!text.isEmpty()) {
			try {
				System.out.println(query);
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					providers.add(rs.getString("first_name") + " " + rs.getString("last_name") + " | "
							+ rs.getString("address") + ", " + rs.getString("city"));
				
				}
				// The program also allows the user to enter last_name + first_name, rather than
				// first_name + last_name.
				query = "SELECT * FROM providers WHERE status='approved' AND last_name IN('" + text1 + "', '" + text2
						+ "');";
				System.out.println(query);
				rs = stmt.executeQuery(query);

				while (rs.next()) {
					providers.add(rs.getString("first_name") + " " + rs.getString("last_name") + " | "
							+ rs.getString("address") + ", " + rs.getString("city"));
				}
				// If the user enter the name of the city, it is also checked in the database
				query = "SELECT * FROM providers WHERE city IN('" + text1 + "', '" + text2 + "');";
				System.out.println(query);
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					providers.add(rs.getString("first_name") + " " + rs.getString("last_name") + " | "
							+ rs.getString("address") + ", " + rs.getString("city"));
				
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else { // The user did not enter any text, so it may want to search using the areas
					// shown
			query = "SELECT * FROM providers WHERE status='approved' AND area='" + areas + "';";
			try {
				System.out.println(query);
				rs = stmt.executeQuery(query);
				while (rs.next()) {
					providers.add(rs.getString("first_name") + " " + rs.getString("last_name") + " | "
							+ rs.getString("address") + ", " + rs.getString("city"));
					bookingInfo[1] = rs.getString("email_prov");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return providers;

	}

	// This method fills up a JComboBox with all the areas where there is at least
	// one provider
	/**
	 * @return a array of string of locations with providers
	 */
	public String[] getLocation() {
		int count = 0;
		int i = 0;
		String[] areas = null;
		String query = "SELECT COUNT(area) AS 'total' FROM providers WHERE status='approved';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("total");
			}
			areas = new String[count];
			query = "SELECT DISTINCT(area) FROM providers WHERE status='approved';";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				areas[i] = rs.getString("area");
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return areas;
	}

	/**
	 * @param date
	 *            the date selected by the user
	 * @return a list of time available, it fill buttons with time slots
	 */
	public List<String> showTimeSlots(String date) {
		String day = date.substring(date.indexOf("|") + 2, date.lastIndexOf("/"));
		String month = date.substring(date.lastIndexOf("/") + 1);
		String query = "SELECT * FROM bookings WHERE date='2018" + "-" + month + "-" + day + "' OR date='2019" + "-"
				+ month + "-" + day + "';";
		List<String> hours = new ArrayList<>();
		for (int i = 8; i <= 20; i++) {
			hours.add(String.valueOf(i) + ":00");
			hours.add(String.valueOf(i) + ":30");
		}
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				for (int i = 0; i <= hours.size() - 1; i++) {
					if (rs.getString("time").equals(hours.get(i))) {
						hours.remove(i);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		bookingInfo[2] = "2018-" + month + "-" + day;
		return hours;
	}

	/**
	 * @param time
	 *            the hour selected by the user to make a booking
	 */
	public void makeBooking(String time) {
		bookingInfo[3] = time;
		String query = "INSERT INTO `bookings`(`email_cust`, `email_prov`, `time`, `date`, `status`) VALUES('"
				+ customer.getEmail() + "', '" + bookingInfo[1] + "', '" + bookingInfo[3] + "', '" + bookingInfo[2]
				+ "', 'Pending');";
		try {
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// -----------------------------ReviewPanel------CostumerView-------

	public void makeReview(String review, String provInfo) {
		// Splitting the information of the provider to name and last name
		String name = provInfo.substring(0, provInfo.indexOf(" "));
		String surname = provInfo.substring(provInfo.indexOf(" ") + 1, provInfo.indexOf(","));
		// Fetching the email of the provider
		String query = "SELECT * FROM providers WHERE first_name='" + name + "' AND last_name='" + surname + "';";
		String emailProv = "";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				emailProv = rs.getString("email_prov");
			} // INSERT DONE
			query = "INSERT INTO `reviews`(`email_prov`, `email_cust`, `review`,`date` ) VALUES('" + emailProv + "', '"
					+ customer.getEmail() + "', '" + review + "', CURRENT_TIMESTAMP);";
			System.out.println(query);

			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// ----------------------------------------Provider View--------------------

	public Provider getProviderInfo() {
		String[] info = new String[10];
		String query = "SELECT * FROM providers WHERE email_prov='" + provider.getEmail() + "';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				provider.setFirst_name(rs.getString("first_name"));
				provider.setLast_name(rs.getString("last_name"));
				provider.setPhone(rs.getString("phone"));
				provider.setCity(rs.getString("city"));
				provider.setArea(rs.getString("area"));
				provider.setAddress(rs.getString("address"));
				provider.setStatus(rs.getString("status"));
			}
			query = "DELETE FROM bookings WHERE status='Locked' AND date<CURRENT_TIMESTAMP;";
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return provider;
	}

	public void setAvailability(String timesSelected) {
		String[] lines = timesSelected.split("\r\n|\r|\n");
		String[] day = new String[lines.length];
		String[] month = new String[lines.length];
		String[] time = new String[lines.length];
		String[] status = new String[lines.length];
		for (int i = 0; i <= lines.length - 1; i++) {
			day[i] = lines[i].substring(lines[i].indexOf("|") + 2, lines[i].lastIndexOf("/"));
			month[i] = lines[i].substring(lines[i].lastIndexOf("/") + 1, lines[i].lastIndexOf("|"));
			time[i] = lines[i].substring(lines[i].lastIndexOf("|") + 1);
			status[i] = lines[i].substring(0, lines[i].indexOf(" "));
			if (status[i].equals("Unavailable")) {
				String query = "INSERT INTO `bookings`(`email_cust`, `email_prov`, `time`, `date`, `status`) VALUES('"
						+ "unavailable" + "', '" + provider.getEmail() + "', '" + time[i] + "', '2018-" + month[i] + "-" + day[i]
						+ "', 'Locked');";
				System.out.println(query);
				try {
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (status[i].equals("Available")) {
				String query = "DELETE FROM bookings WHERE time='" + time[i] + "' AND email_prov='" + provider.getEmail()
						+ "' AND date='2018-" + month[i] + "-" + day[i] + "';";
				try {
					System.out.println(query);
					stmt.executeUpdate(query);
					query = "DELETE FROM bookings WHERE status='Locked' AND date<CURRENT_TIMESTAMP;";
					System.out.println(query);
					stmt.executeUpdate(query);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	public List<String> checkUnlockedSlots(String date) {
		String day = date.substring(date.indexOf("|") + 2, date.lastIndexOf("/"));
		String month = date.substring(date.lastIndexOf("/") + 1);
		List<String> unlocked = new ArrayList<>();
		String query = "SELECT * FROM bookings WHERE email_prov='" + provider.getEmail() + "' AND date='2018" + "-" + month + "-"
				+ day + "' OR date='2019" + "-" + month + "-" + day + "';";
		for (int i = 8; i <= 20; i++) {
			unlocked.add(String.valueOf(i) + ":00");
			unlocked.add(String.valueOf(i) + ":30");
		}
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				for (int i = 0; i <= unlocked.size() - 1; i++) {
					if (rs.getString("time").equals(unlocked.get(i))) {
						unlocked.remove(i);
					}
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return unlocked;
	}

	public List<String> checkLockedSlots(String date) {
		String day = date.substring(date.indexOf("|") + 2, date.lastIndexOf("/"));
		String month = date.substring(date.lastIndexOf("/") + 1);
		List<String> locked = new ArrayList<>();
		String query = "SELECT * FROM bookings WHERE email_prov='" + provider.getEmail() + "' AND status='locked' AND date='2018"
				+ "-" + month + "-" + day + "' OR date='2019" + "-" + month + "-" + day + "';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				locked.add(rs.getString("time"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return locked;
	}

	public String[][] getProviderBookings() {
		int count = 0;
		// Couting how many bookings the customer has to define the size of the list
		String query = "SELECT COUNT(email_prov) AS 'total' FROM bookings WHERE email_prov='" + provider.getEmail() + "';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] bookings = new String[count][10];
		query = "SELECT *, customers.first_name, customers.last_name, customers.phone FROM bookings INNER JOIN customers ON customers.email_cust=bookings.email_cust WHERE email_prov='"
				+ provider.getEmail() + "' ORDER BY date;";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			int c = 0;
			while (rs.next()) {
				// All the infomation needed to show the bookings will be stored in the array
				bookings[c][0] = rs.getString("first_name");
				bookings[c][1] = rs.getString("last_name");
				bookings[c][2] = rs.getString("time");
				Date date = rs.getDate("date");
				DateFormat day = new SimpleDateFormat("EEEE");
				DateFormat df = new SimpleDateFormat("dd");
				DateFormat month = new SimpleDateFormat("MM");
				// Formarting dates to TUESDAY, DD/MM.
				bookings[c][3] = day.format(date); // TUESDAY
				bookings[c][4] = df.format(date) + "/" + month.format(date); // dd/mm
				bookings[c][5] = rs.getString("phone");
				bookings[c][6] = rs.getString("status");
				bookings[c][7] = rs.getString("ref_num");
				c++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bookings;
	}

	public void manageBookings(String buttonName, String status) {
		Calendar today = Calendar.getInstance();
		Date date = today.getTime();
		String number = buttonName.substring(buttonName.indexOf(":") + 1, buttonName.indexOf("|") - 1);
		String query = "UPDATE `bookings` SET `status`='" + status + "',`ap_date`=CURRENT_TIMESTAMP WHERE ref_num='"
				+ number + "';";
		try {
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getReviews() {
		List<String> reviews = new ArrayList<>();
		String query = "SELECT * FROM reviews WHERE email_prov='" + provider.getEmail() + "';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				reviews.add(rs.getString("review"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return reviews;
	}

	// -----------------------------------------------------ADMIN------------------------

	public JTable bookings() {
		int row = 0;
		int count = 0;
		String query = "SELECT COUNT(email_prov) AS 'total' FROM bookings WHERE NOT bookings.status='locked';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				count = rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] dates= new String[count][6];
		String[] columnNames = {"Customer", "Provider","Time","Date", "Last Update", "Status"};
		
		query = "SELECT *, customers.first_name, customers.last_name, providers.first_name, providers.last_name FROM ((bookings INNER JOIN customers ON customers.email_cust=bookings.email_cust) INNER JOIN providers ON providers.email_prov=bookings.email_prov) WHERE NOT bookings.status='locked';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				dates[row][0]  =  rs.getString("customers.first_name") + " " +  rs.getString("customers.last_name");
				dates[row][1] =  rs.getString("providers.first_name") + " " +  rs.getString("providers.last_name");
				dates[row][2] = rs.getString("time");
				dates[row][3] = rs.getString("date");
				dates[row][4] = rs.getString("ap_date");
				dates[row][5] = rs.getString("status");
				row++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JTable bookings = new JTable(dates, columnNames);
		return bookings;
	}
	
	public JTable users() {
		int row = 0;
		int count = 0;
		String query = "SELECT COUNT(email_prov) AS 'total' FROM providers;";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				count += rs.getInt("total");
			}
			query = "SELECT COUNT(email_cust) AS 'total' FROM customers;";
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				count += rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] dates= new String[count][6];
		String[] columnNames = {"Type", "Name","Phone","Email", "No. of Bookings"};
		
		query = "SELECT COUNT(email_cust) AS 'count', customers.first_name, customers.last_name, customers.email_cust, customers.phone FROM bookings INNER JOIN customers ON customers.email_cust=bookings.email_cust;";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				dates[row][0] = "Customer";
				dates[row][1] = rs.getString("customers.first_name") + " " + rs.getString("customers.last_name");;
				dates[row][2] = rs.getString("customers.phone");
				dates[row][3] = rs.getString("customers.email_cust");
				dates[row][4] = rs.getString("count");
				System.out.println(rs.getString("customers.first_name"));
				row++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JTable bookings = new JTable(dates, columnNames);
		return bookings;
	}
	
	public JTable getPending() {
		int row = 0;
		int count = 0;
		String query = "SELECT COUNT(email_prov) AS 'total' FROM providers WHERE status='Pending';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				count += rs.getInt("total");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[][] pending = new String[count][7];
		String[] columnNames = {"First Name", "Secong Name","Phone","Email", "Address", "City", "Status"};
		String[] status = {"Rejected", "Approved"};
		JComboBox<String> statusCB = new JComboBox<String>(status);
		statusCB.setSelectedItem(0);
		query = "SELECT * FROM providers WHERE status='Pending';";
		try {
			System.out.println(query);
			rs = stmt.executeQuery(query);
			while (rs.next()) {
				pending[row][0] = rs.getString("first_name");
				pending[row][1] = rs.getString("last_name");
				pending[row][2] = rs.getString("phone");
				pending[row][3] = rs.getString("email_prov");
				pending[row][4] = rs.getString("address");
				pending[row][5] = rs.getString("city");
				row++;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		JTable pendingTable = new JTable(pending, columnNames);
		pendingTable.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(statusCB));
		return pendingTable;
	}

	public void setStatus(String status, String email) {
		String query = "UPDATE `providers` SET `status`='" + status +"' WHERE email_prov='"+email+"';";
		try {
			System.out.println(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String createNewAdmin(String name, String password, String email) {
		String hashPassword = DigestUtils.md5Hex(password);
		String query = "SELECT * FROM admin";
		String message = "";
		Boolean repitedEntry = true;
		try {
			rs = stmt.executeQuery(query);
			System.out.println(query);
			while (rs.next()) {
				if (email.equals(rs.getString("email"))) {
					message = "Admin already exists!";
					repitedEntry = false;
				}
			}
			if (repitedEntry) {
				query = "INSERT INTO `admin`(`password`, `name`, `email`) VALUES ('"+hashPassword+"', '"+name+"', '"+email+"');";

				System.out.println(query);
				stmt.executeUpdate(query);
				message = "Admin registered!";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return message;

	}
	
}

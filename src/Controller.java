import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.Provider;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;

public class Controller implements ActionListener {
	LoginView loginView;
	SignupView signupView;
	CustomerView customerView;
	ProviderView providerView;
	Admin admin;
	Model model;
	Validation validation = new Validation();

	public Controller() {
		loginView = new LoginView(this);
		model = new Model();
		customerView = new CustomerView(this);
		providerView = new ProviderView(this);
		admin = new Admin(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// LoginView
		// ------------------------------------------------------------------------------------------------------------

		// Login button is in the LoginView menu
		if (e.getActionCommand().equals("login")) {
			loginView.dispose();
			// Those if statements will redirect the user to its respective dashboard based
			// in the email entered in the LoginView
			// If the request sent to Model returns a:
			// 1, it fetched a CUSTOMER e-mail
			// 2, it fetched a PROVIDER e-mail
			// 3, it fetched a ADMIN e-mail
			// 0, the email entered is not in the database and a popup window asks the
			// user to try again
			if (model.login(loginView.getPasswordTF(), loginView.getEmailTF().getText()) == 1) {
				customerView = new CustomerView(this);
				customerView.createFrame(model.getCustomerInfo());
			}
			if (model.login(loginView.getPasswordTF(), loginView.getEmailTF().getText()) == 2) {
				providerView = new ProviderView(this);
				providerView.createFrame(model.getProviderInfo());
			}
			if (model.login(loginView.getPasswordTF(), loginView.getEmailTF().getText()) == 3) {
				admin = new Admin(this);
				admin.createFrame();
			}
			if (model.login(loginView.getPasswordTF(), loginView.getEmailTF().getText()) == 0) {
				JOptionPane.showMessageDialog(loginView, "Email and/or password are incorrect, try again or signup!", "error",
						JOptionPane.PLAIN_MESSAGE);
				loginView = new LoginView(this);
			}
		}

		//// SignupView
		//// ----------------------------------------------------------------------------------------

		if (e.getActionCommand().equals("signup")) {
			loginView.dispose();
			signupView = new SignupView(this);
		}
		if (e.getActionCommand().equals("customer")) {
			loginView.dispose();
			signupView.createCustomer();
		}
		if (e.getActionCommand().equals("provider")) {
			loginView.dispose();
			signupView.createProvider();
		}
		if (e.getActionCommand().equals("backS")) {
			loginView.dispose();
			signupView.dispose();
			signupView.removeAll();
			loginView = new LoginView(this);
		}

		if (e.getActionCommand().equals("create")) {
			loginView.dispose();
			// After the user entered its information, the system checks if the two
			// passwords entered match
			if (signupView.getFirstName().getText().isEmpty() || signupView.getLastName().getText().isEmpty()
					|| signupView.getPhone().getText().isEmpty() || signupView.getEmail().getText().isEmpty()
					|| signupView.getPassword().isEmpty()) {
				// If any field is empty, a pop up message will appear
				JOptionPane.showMessageDialog(loginView, "Please, complete all the fields", "Error",
						JOptionPane.PLAIN_MESSAGE);
				// If they are not empty, the system will validate them

			} else {
				if (Model.comparePassword(signupView.getPassword(), signupView.getConfPass())) {
					// If so, the user will be create, based in which panel the information was
					// inserted
					if (validation.checkLetter(signupView.getFirstName().getText())
							&& validation.checkLetter(signupView.getLastName().getText())) {
						System.out.println("Letter");
						if (validation.checkPassword(signupView.getPassword())) {
							System.out.println("pass");
							if (validation.checkEmail(signupView.getEmail().getText())) {
								System.out.println("email");
								if (SignupView.customer.isVisible()) {
									// To avoid empty entry a null validation is done
									// So the system check for null values and it only allows the user to register
									// after completing all the form

									// The method model.signup() returns a string containing a message, two possible
									// messages can be displayed
									String message = model.signup(signupView.getFirstName().getText(),
											signupView.getLastName().getText(), signupView.getPhone().getText(),
											signupView.getEmail().getText(), signupView.getPassword());
									JOptionPane.showMessageDialog(loginView, message, "Message",
											JOptionPane.PLAIN_MESSAGE);
									signupView.dispose();
									loginView = new LoginView(this);
									// If the customer panel is not visible, it means that the provider's one is
									// visible
								} else {
									if (signupView.getCity().isEmpty() || signupView.getArea().isEmpty()
											|| signupView.getAddress().getText().isEmpty()) {
										JOptionPane.showMessageDialog(loginView, "Please, complete all the fields",
												"Error", JOptionPane.PLAIN_MESSAGE);
									} else {
										String message = model.signup(signupView.getFirstName().getText(),
												signupView.getLastName().getText(), signupView.getPhone().getText(),
												signupView.getEmail().getText(), signupView.getPassword(),
												signupView.getCity(), signupView.getArea(),
												signupView.getAddress().getText());
										JOptionPane.showMessageDialog(loginView, message, "Message",
												JOptionPane.PLAIN_MESSAGE);
										signupView.dispose();
										loginView = new LoginView(this);

									}
								}
							} else {// Email is not valid
								JOptionPane.showMessageDialog(loginView, "This is not a valid e-mail!", "Error",
										JOptionPane.PLAIN_MESSAGE);
							}
						} else { // Password is not valid
							JOptionPane.showMessageDialog(signupView,
									"Your password must have from 8 to 12 characters, contain an uppercase, a lowercase and a special character!",
									"Error", JOptionPane.PLAIN_MESSAGE);
						}
					}
				} else {// The two passwords entered is not equal, therefore the user can't be created.
					JOptionPane.showMessageDialog(loginView, "Passwords don't match! Try again.", "Error",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}

		// ----------------------------------------------------------CustomerView
		// ------------------------------------------------------------------------------------------------------------------------------------------------------
		// ----------------Find Panel-------------------------------Making a booking
		// step by step
		// Step 1 - Search a provider
		// When the button find is clicked, it add a textfield and all the available
		// areas into a JComboBox
		if (customerView.isVisible()) {
			customerView.bookingPanel.removeAll();
			customerView.findPanel.removeAll();
			customerView.reviewPanel.removeAll();
			customerView.remove(customerView.jcp);
			customerView.repaint();
			customerView.validate();
		}
		if (e.getActionCommand().equals("find")) {
			customerView.bookingPanel.setVisible(true);
			customerView.showFindPanel(model.getLocation());
		}

		// Step 2 - Select a provider
		if (e.getActionCommand().equals("search")) {
			customerView.providers.setVisible(false);
			customerView.showProviders(model.searchProvider(customerView.getNameTF().getText(),
					(String) customerView.getAreas().getSelectedItem()));
		}
		// Step 3 - Select a date
		if (e.getActionCommand().equals("provider selected")) {
			JButton button = (JButton) e.getSource();
			String buttonName = button.getText();
			customerView.findPanel.removeAll();
			customerView.showDates(buttonName);
			customerView.providers.removeAll();
		}
		// Step 4 - Select a time available
		if (e.getActionCommand().equals("show times")) {
			customerView.showTimeSlots(model.showTimeSlots(customerView.getDays().getSelectedItem().toString()));
		}
		// Step 5 - The booking is made
		if (e.getActionCommand().equals("make booking")) {
			JButton button = (JButton) e.getSource();
			button.setForeground(Color.GREEN);
			String buttonName = button.getText();
			button.setEnabled(false);
			model.makeBooking(buttonName);
			button.setText("Pending!");
			JOptionPane.showMessageDialog(loginView, "Your booking will be processed!", "Booking status: Pending",
					JOptionPane.PLAIN_MESSAGE);

		}

		// -------------------------------Bookings Panel-----------------------------
		if (e.getActionCommand().equals("booking")) {
			customerView.showBookingPanel(model.getCustomerBookings());
		}
		// deletes the booking selected from the database
		if (e.getActionCommand().equals("cancel")) {
			JButton button = (JButton) e.getSource();
			String buttonName = button.getText();
			button.setText("Cancelled");
			button.setForeground(Color.RED);
			JOptionPane.showMessageDialog(loginView, "Your booking is cancelled!", "Booking status: Cancelled",
					JOptionPane.PLAIN_MESSAGE);
			model.cancel(buttonName);

		}
		// -------------------------------Review
		// Panel-----------------------------------
		// Gets all the bookings from the database, it uses the same method of the
		// bookingPanel
		if (e.getActionCommand().equals("review")) {

			customerView.showReviewPanel(model.getCustomerBookings());

		}
		// Makes a INSERT INTO the table review in the database
		if (e.getActionCommand().equals("submit review")) {
			model.makeReview(customerView.getReviewTF().getText(),
					customerView.getReviewProviderCB().getSelectedItem().toString());
			customerView.reviewPanel.removeAll();
		}
		// Removes all the elements of the screen, dispose the frame and open a new
		// loginView.
		if (e.getActionCommand().equals("logout")) {
			customerView.removeAll();
			customerView.dispose();
			providerView.removeAll();
			providerView.dispose();
			admin.removeAll();
			admin.dispose();
			loginView = new LoginView(this);
		}

		// ------------------------Providers
		// View--------------------------------------------------------------------------------------
		if (providerView.isVisible()) {
			providerView.bookingsPanel.removeAll();
			providerView.feedbackPanel.removeAll();
			providerView.remove(providerView.jspProvider);
			providerView.repaint();
			providerView.validate();
		}
		//Sends to the today's date, 
		if (e.getActionCommand().equals("availability")) {
			Calendar today = Calendar.getInstance();
			List<String> dates = new ArrayList<>();
			providerView.availPanel.removeAll();
			providerView.setAvailability(dates, 1, dates);
		}

		if (e.getActionCommand().equals("lock")) {
			JButton button = (JButton) e.getSource();
			String buttonName = button.getText();
			button.setForeground(Color.RED);
			providerView.setTimesSelected(
					"Unavailable " + providerView.getDays().getSelectedItem().toString() + "|" + buttonName);
		}

		if (e.getActionCommand().equals("unlock")) {
			JButton button = (JButton) e.getSource();
			String buttonName = button.getText();
			button.setForeground(Color.GREEN);
			providerView.setTimesSelected(
					"Available " + providerView.getDays().getSelectedItem().toString() + "|" + buttonName);
		}

		if (e.getActionCommand().equals("reset")) {
			providerView.resetTimesSelected();
			providerView.availPanel.removeAll();
			Calendar today = Calendar.getInstance();
			List<String> dates = new ArrayList<>();
			providerView.setAvailability(dates, 1, dates);
		}

		if (e.getActionCommand().equals("save")) {
			if (!providerView.getTimesSelected().getText().isEmpty()) {
				model.setAvailability(providerView.getTimesSelected().getText());
				JOptionPane.showMessageDialog(providerView, "Your time slots were saved!", "Time slots updated",
						JOptionPane.PLAIN_MESSAGE);

			}
		}
		if (e.getActionCommand().equals("updateSlots")) {
			int index = providerView.getDays().getSelectedIndex();
			providerView.availPanel.removeAll();
			providerView.setAvailability(
					model.checkUnlockedSlots(providerView.getDays().getSelectedItem().toString()),
					index, model.checkLockedSlots(providerView.getDays().getSelectedItem().toString()));
		}

		if (e.getActionCommand().equals("manageBooking")) {
			providerView.manageBookings(model.getProviderBookings());
		}

		if (e.getActionCommand().equals("booking chosen")) {
			JButton button = (JButton) e.getSource();
			String buttonName = button.getText();
			if (ProviderView.confirmButton.isSelected()) {
				model.manageBookings(buttonName, "Confirmed");
			}
			if (ProviderView.rejectButton.isSelected()) {
				model.manageBookings(buttonName, "Rejected");
			}
			if (ProviderView.completedButton.isSelected()) {
				model.manageBookings(buttonName, "Completed");
			}
			if (ProviderView.missedButton.isSelected()) {
				model.manageBookings(buttonName, "Missed");
			}
		}

		if (e.getActionCommand().equals("feedback")) {
			providerView.showFeedbackPanel(model.getReviews());
		}

		
		//-------------------Admin------------------
		if(admin.isVisible()) {
			admin.remove(Admin.providersPanel);
			admin.remove(Admin.bookingsPanel);
			admin.remove(Admin.newAdPanel);
			Admin.providersPanel.removeAll();
			Admin.bookingsPanel.removeAll();
			Admin.newAdPanel.removeAll();
		}
		if (e.getActionCommand().equals("all bookings")) {
			admin.bookings(model.bookings());
		}

		if (e.getActionCommand().equals("pending")) {
			admin.showPending(model.getPending());
		}

		if (e.getActionCommand().equals("save pending")) {
			int nRow = Admin.providers.getModel().getRowCount();
			for (int i = 0; i < nRow; i++) {
				model.setStatus(Admin.providers.getModel().getValueAt(i, 6).toString(),
						Admin.providers.getModel().getValueAt(i, 3).toString());
			}
		}
		
		if (e.getActionCommand().equals("new admin")) {
			admin.newAdmin();
		}
		if (e.getActionCommand().equals("submit")) {
			String message = model.createNewAdmin(admin.getNameTF().getText(), admin.getPasswordTF().getPassword().toString(), admin.getEmailTF().getText());
			JOptionPane.showMessageDialog(admin, message, "Message",
					JOptionPane.PLAIN_MESSAGE);
		}
		
		System.out.println("Button clicked: " + e.getActionCommand());

	}
}

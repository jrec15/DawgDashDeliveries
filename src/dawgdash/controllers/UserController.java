package dawgdashdeliveries.controllers;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserController
 */
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UserController() {
    	super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBHelper helper = new DBHelper();
		ServletContext ctx = this.getServletContext();
		HttpSession session = request.getSession();
		
		// -----------------------------------------+
		// admin attempts to access scheduling page |
		// admin.jsp                                |
		// -----------------------------------------+
		if(request.getParameter("task").equals("SCHEDULE")) {
			// redirect to scheduling.jsp
			if(session.getAttribute("role").equals("admin")) {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/scheduling.jsp");
				ArrayList<User> workers = helper.getWorkers();
				request.setAttribute("workers", workers);
				dispatcher.forward(request, response);
			}
			else {
				request.setAttribute("access_error", "access_error");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/scheduling.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		// -----------------------------------------------------+
		// admin attempts to access account administration page |
		// admin.jsp                                            |
		// -----------------------------------------------------+
		if(request.getParameter("task").equals("ADMINISTRATION")) {
			// redirect to account_administration.jsp
			if(session.getAttribute("role").equals("admin")) {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
				ArrayList<User> users = helper.getAllUsers();
				request.setAttribute("users", users);
				dispatcher.forward(request, response);
			}
			else {
				request.setAttribute("access_error", "access_error");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		// --------------------------------------------------------------+
		// user attempts to return to home page after submitting payment |
		// confirmation.jsp                                              |
		// --------------------------------------------------------------+
		if(request.getParameter("task").equals("GO_HOME")) {
			// redirect to appropriate login view
			if(session.getAttribute("role").equals("customer")) {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/welcome.jsp");
				dispatcher.forward(request, response);
			}
			else if(session.getAttribute("role").equals("worker")) {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_pending_deliveries.jsp");
				User user = session.getAttribute("user");
				int userId = user.getId();
				ArrayList<Delivery> pendingDeliveries = helper.getPendingWorkerDeliveriesFor(userId);
				request.setAttribute("pending_deliveries", pendingDeliveries);
				dispatcher.forward(request, response);
			}
			else if(session.getAttribute("role").equals("admin")) {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/admin.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		// ------------------------------------------------+
		// admin attempts to view individual schedule page |
		// scheduling.jsp                                  |
		// ------------------------------------------------+
		if(request.getParameter("task").equals("INDIVIDUAL_SCHEDULE")) {
			// make sure user is admin
			if(session.getAttribute("role").equals("admin")) {
				// redirect to individual_schedule.jsp
				int workerId = Integer.parseInt(request.getParameter("worker_id"));
				Schedule schedule = helper.getScheduleForId(workerId);
				request.setAttribute("schedule", schedule);
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/individual_schedule.jsp");
				dispatcher.forward(request, response);
			}
			else {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/individual_schedule.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		// --------------------------------------+
		// customer requests modify account page |
		// welcome.jsp                           |
		// --------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_MODIFY_ACCOUNT")) {
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
			dispatcher.forward(request, response);
		}

		// --------------------------------------------+
		// worker attempts to view modify account page |
		// worker_pending_deliveries.jsp               |
		// --------------------------------------------+
		if(request.getParameter("task").equals("WORKER_MODIFY_ACCOUNT")) {
			// redirect to worker_modify_account.jsp
			if(session.getAttribute("role").equals("worker")) {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
				dispatcher.forward(request, response);
			}
			else {
				request.setAttribute("access_error", "access_error");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
				dispatcher.forward(request, response);
			}
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBHelper helper = new DBHelper();
		ServletContext ctx = this.getServletContext();
		HttpSession session = request.getSession();
		
		// -------------------------------------------+
		// admin attempts to change worker's password |
		// account_administration.jsp                 |
		// -------------------------------------------+
		if(request.getParameter("task").equals("ADMIN_CHANGE_PASSWORD")) {
			// if invalid password change, redirect to account_administration.jsp with error message
			
			// make sure user is admin
			if(session.getAttribute("role").equals("admin")) {
				User user = session.getAttribute("user");
				int id = user.getId();
				String adminPassword = helper.getPasswordFor(id);
				String providedAdminPassword = request.getParameter("admin_password");
				// make sure admin password is correct
				if(helper.hash(providedAdminPassword).equals(adminPassword)) {
					String newPassword = request.getParameter("new_password");
					String verifyPassword = request.getParameter("verify_password");
					// make sure new password is at least 8 characters
					if(newPassword.length() >= 8) {
						// make sure new password matches verification
						if(newPassword.equals(verifyPassword)) {
							int account = Integer.parseInt(request.getParameter("account"));
							helper.updatePassword(account, helper.hash(newPassword));
							RequestDispatcher dispatcher = ctx.getRequestDispatcher("/admin.jsp");
							dispatcher.forward(request, response);
						}
						else {
							request.setAttribute("password_error", "Error: passwords do not match");
							RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
							dispatcher.forward(request, response);
							return;
						}
					}
					else {
						request.setAttribute("password_error", "Error: password must be at least 8 characters");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
						dispatcher.forward(request, response);
						return;
					}
					
				}
				else {
					request.setAttribute("password_error", "Error: incorrect admin password");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
					dispatcher.forward(request, response);
					return;
				}
				
			}
			else {
				request.setAttribute("access_error", "access_error");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
		}
		
		// ------------------------------------+
		// admin attempts to create new worker |
		// account_administration.jsp          |
		// ------------------------------------+
		if(request.getParameter("task").equals("ADMIN_CREATE_USER")) {
			// if valid user creation, redirect to admin.jsp
			if(session.getAttribute("role").equals("admin")) {
				User user = session.getAttribute("user");
				int id = user.getId();
				String adminPassword = helper.getPasswordFor(id);
				String providedAdminPassword = request.getParameter("admin_password");
				// make sure admin password is correct
				if(helper.hash(providedAdminPassword).equals(adminPassword)) {
					String newPassword = request.getParameter("worker_password");
					String verifyPassword = request.getParameter("verify_worker_password");
					// make sure new password is at least 8 characters
					if(newPassword.length() >= 8) {
						if(newPassword.equals(verifyPassword)) {
							String name = request.getParameter("worker_name");
							if(name.length() >= 8) {
								String email = request.getParameter("worker_email");
								if(helper.availableEmail(email)) {
									String username = request.getParameter("worker_username");
									if(username.length() >= 8) {
										if(helper.availableUsername(username)) {
											String transportation = request.getParameter("transportation");
											if(!transportation.equals("none")) {
												int transportationValue = -1;
												if(transportation.equals("bike")) {
													transportationValue = 1;
												}
												else if(transportation.equals("car")) {
													transportationValue = 2;
												}
												else if(transportation.equals("truck")) {
													transportationValue = 3;
												}
												helper.createWorker(name, email, username, newPassword, transportationValue);
											}
											else {
												request.setAttribute("new_worker_error", "Error: must select transportation type");
												RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
												dispatcher.forward(request, response);
												return;
											}
										}
										else {
											request.setAttribute("new_worker_error", "Error: username already exists");
											RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
											dispatcher.forward(request, response);
											return;
										}
									}
									else {
										request.setAttribute("new_worker_error", "Error: email address already exists");
										RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
										dispatcher.forward(request, response);
										return;
									}
								}
								else {
									request.setAttribute("new_worker_error", "Error: email address already exists");
									RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
									dispatcher.forward(request, response);
									return;
								}
							}
							else {
								request.setAttribute("new_worker_error", "Error: name too short");
								RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
								dispatcher.forward(request, response);
								return;
							}
							
						}
						else {
							request.setAttribute("new_worker_error", "Error: passwords do not match");
							RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
							dispatcher.forward(request, response);
							return;
						}
					}
					else {
						request.setAttribute("new_worker_error", "Error: password must be at least 8 characters");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
				else {
					request.setAttribute("new_worker_error", "Error: incorrect admin password");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
			else {
				request.setAttribute("access_error", "access_error");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/account_administration.jsp");
				dispatcher.forward(request, response);
				return;
			}
			// else redirect to account_administration.jsp with user creation error message
		}
		
		// ---------------------------------------------+
		// user attempts to create new customer account |
		// create_account.jsp                           |
		// ---------------------------------------------+
		if(request.getParameter("task").equals("CREATE_CUSTOMER_ACCOUNT")) {
			// if valid submission, redirect to index.jsp
			// else redirect to create_account.jsp with error message
			// TODO change parameters in create_account.jsp
			String name = request.getParameter("name");
			if(name.length() >= 8) {
				String username = request.getParameter("username");
				if(username.length() >= 8) {
					if(helper.availableUsername(username)) {
						String email = request.getParameter("email");
						if(email.length() >= 8) {
							if(helper.availableEmail(email)) {
								String address = request.getParameter("address");
								if(address.length() >= 16) {
									String password = request.getParameter("password");
									String verifyPassword = request.getParameter("verify_password");
									if(password.length() >= 8) {
										if(password.equals(verifyPassword)) {
											helper.createCustomer(name, email, username, password, address);
											RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
											dispatcher.forward(request, response);
											return;
										}
										else {
											request.setAttribute("error", "Error: provided passwords do not match");
											RequestDispatcher dispatcher = ctx.getRequestDispatcher("/create_account.jsp");
											dispatcher.forward(request, response);
											return;
										}
									}
									else {
										request.setAttribute("error", "Error: password too short");
										RequestDispatcher dispatcher = ctx.getRequestDispatcher("/create_account.jsp");
										dispatcher.forward(request, response);
										return;
									}
								}
								else {
									request.setAttribute("error", "Error: address too short");
									RequestDispatcher dispatcher = ctx.getRequestDispatcher("/create_account.jsp");
									dispatcher.forward(request, response);
									return;
								}
							}
							else {
								request.setAttribute("error", "Error: email address already exists");
								RequestDispatcher dispatcher = ctx.getRequestDispatcher("/create_account.jsp");
								dispatcher.forward(request, response);
								return;
							}
						}
						else {
							request.setAttribute("error", "Error: email address too short");
							RequestDispatcher dispatcher = ctx.getRequestDispatcher("/create_account.jsp");
							dispatcher.forward(request, response);
							return;
						}
					}
					else {
						request.setAttribute("error", "Error: username already exists");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/create_account.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
				else {
					request.setAttribute("error", "Error: username too short");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/create_account.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
			else {
				request.setAttribute("error", "Error: name too short");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/create_account.jsp");
				dispatcher.forward(request, response);
				return;
			}
										
		}
		
		// -------------------------------------+
		// customer attempts to change password |
		// customer_modify_account.jsp          |
		// -------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_CHANGE_PASSWORD")) {
			// if valid change, redirect to welcome.jsp with confirmation message
			// else redirect to customer_modify_account.jsp with error message
			User user = session.getAttribute("user");
			int userId = user.getId();
			String currPassword = request.getParameter("old_password");
			if(helper.hash(currPassword).equals(helper.getPasswordFor(userId))) {
				String newPassword = request.getParameter("new_password");
				String verifyPassword = request.getParameter("verify_password");
				if(newPassword.length() >= 8) {
					if(newPassword.equals(verifyPassword)) {
						helper.changePassword(userId, newPassword);
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/welcome.jsp");
						dispatcher.forward(request, response);
						return;
					}
					else {
						request.setAttribute("password_error", "Error: passwords do not match");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
				else {
					request.setAttribute("password_error", "Error: password too short");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
			else {
				request.setAttribute("password_error", "Error: current password does not match");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
				dispatcher.forward(request, response);
				return;
			}
			
		}
		
		// ------------------------------------------+
		// customer attempts to change email address |
		// customer_modify_account.jsp               |
		// ------------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_CHANGE_EMAIL")) {
			// if valid change, redirect to welcome.jsp with confirmation message
			// else redirect to customer_modify_account.jsp with error message
			User user = session.getAttribute("user");
			int userId = user.getId();
			String password = request.getParameter("password");
			if(helper.hash(password).equals(helper.getPasswordFor(userId))) {
				String newEmail = request.getParameter("new_email");
				String verifyEmail = request.getParameter("verify_email");
				if(newEmail.length() >= 8) {
					if(newEmail.equals(verifyEmail)) {
						helper.updateEmail(userId, newEmail);
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/welcome.jsp");
						dispatcher.forward(request, response);
						return;
					}
					else {
						request.setAttribute("email_error", "Error: email addresses do not match");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
				else {
					request.setAttribute("email_error", "Error: email address too short");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
			else {
				request.setAttribute("email_error", "Error: current password is not correct");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		
		// --------------------------------------------------+
		// customer attempts to change default address |
		// customer_modify_account.jsp                       |
		// --------------------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_CHANGE_ADDRESS")) {
			// if valid change, redirect to welcome.jsp with confirmation message
			// else redirect to customer_modify_account.jsp with error message
			User user = session.getAttribute("user");
			int userId = user.getId();
			String password = request.getParameter("password");
			if(helper.hash(password).equals(helper.getPasswordFor(userId))) {
				String address = request.getParameter("new_address");
				String verifyAddress = request.getParameter("verify_address");
				if(address.length() >= 16) {
					if(address.equals(verifyAddress)) {
						helper.updateAddress(userId, address);
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/welcome.jsp");
						dispatcher.forward(request, response);
						return;
					}
					else {
						request.setAttribute("address_error", "Error: addresses do not match");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
				else {
					request.setAttribute("address_error", "Error: address too short");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
			else {
				request.setAttribute("address_error", "Error: current password is not correct");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_modify_account.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		
		// -----------------------+
		// User attempts to login |
		// index.jsp              |
		// -----------------------+
		if(request.getParameter("task").equals("LOGIN")) {
			String loginId = request.getParameter("loginId");
			String password = request.getParameter("password");
			String role = helper.attemptLogin(loginId, password);
			
			// if admin login, redirect to admin.jsp
			if(role.equals("admin")) {
				int userId = helper.getIdForLoginId(loginId);
				User user = helper.getUser(userId);
				session.setAttribute("user", user);
				session.setAttribute("role", "admin");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/admin.jsp");
				dispatcher.forward(request, response);
			}
			// if worker login, redirect to worker_pending_deliveries.jsp
			else if(role.equals("worker")) {
				int userId = helper.getIdForLoginId(loginId);
				User user = helper.getUser(userId);
				session.setAttribute("user", user);
				session.setAttribute("role", "worker");
				ArrayList<Delivery> pendingDeliveries = helper.getPendingWorkerDeliveriesFor(userId);
				request.setAttribute("pending_deliveries", pendingDeliveries);
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_pending_deliveries.jsp");
				dispatcher.forward(request, response);
			}
			// if customer login, redirect to welcome.jsp
			else if(role.equals("customer")) {
				int userId = helper.getIdForLoginId(loginId);
				User user = helper.getUser(userId);
				session.setAttribute("user", user);
				session.setAttribute("role", "customer");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/welcome.jsp");
				dispatcher.forward(request, response);
			}
			// else redirect to index.jsp with error message
			else if(role.equals("loginIdError")) {
				request.setAttribute("error", "Unable to match username or password");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}
			else {
				request.setAttribute("error", "Unknown error occurred");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}

		}
		
		// -----------------------------------------+
		// admin attempts to modify worker schedule |
		// individual_schedule.jsp                  |
		// -----------------------------------------+
		if(request.getParameter("task").equals("CHANGE_WORKER_SCHEDULE")) {
			// TODO add error section in individual_schedule.jsp and modify schedule formats, modify DBHelper to take Array of shifts as argument to change schedule
			// if valid schedule change, redirect to admin.jsp
			// else redirect to individual_schedule.jsp with error message
			if(request.getParameter("role").equals("admin")) {
				int userId = Integer.parseInt(request.getParameter("worker_id"));
				String[] shiftParameters = new String[7];
				shiftParameters[0] = request.getParameter("sunday_shift");
				shiftParameters[1] = request.getParameter("monday_shift");
				shiftParameters[2] = request.getParameter("tuesday_shift");
				shiftParameters[3] = request.getParameter("wednesday_shift");
				shiftParameters[4] = request.getParameter("thursday_shift");
				shiftParameters[5] = request.getParameter("friday_shift");
				shiftParameters[6] = request.getParameter("saturday_shift");
				for(String shift : shiftParameters) {
					if(shift.equals("none")) {
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/individual_schedule.jsp");
						request.setAttribute("error", "Error: must select shift or specify worker is off for all days");
						request.setAttribute("schedule", request.getAttribute("schedule"));
						dispatcher.forward(request, response);
						return;
					}
				}
				helper.updateSchedule(userId, shiftParameters);
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("admin.jsp");
				dispatcher.forward(request, response);
				return;
			}
			else {
				request.setAttribute("access_error", "access_error");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("admin.jsp");
				dispatcher.forward(request, response);
				return;
			}
			// create schedule object to pass to DBHelper
			
		}
		
		// -----------------------------------+
		// worker attempts to change password |
		// worker_modify_account.jsp          |
		// -----------------------------------+
		if(request.getParameter("task").equals("WORKER_CHANGE_PASSWORD")) {
			// if valid password change, redirect to worker_pending_deliveries.jsp
			// else redirect to worker_modify_account.jsp with error message
			User user = session.getAttribute("user");
			int userId = user.getId();
			String currPassword = request.getParameter("old_password");
			if(helper.hash(currPassword).equals(helper.getPasswordFor(userId))) {
				String newPassword = request.getParameter("new_password");
				String verifyPassword = request.getParameter("verify_password");
				if(newPassword.length() >= 8) {
					if(newPassword.equals(verifyPassword)) {
						helper.changePassword(userId, newPassword);
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_pending_deliveries.jsp");
						dispatcher.forward(request, response);
						return;
					}
					else {
						request.setAttribute("password_error", "Error: passwords do not match");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
				else {
					request.setAttribute("password_error", "Error: password too short");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
			else {
				request.setAttribute("password_error", "Error: current password does not match");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		
		// ----------------------------------------+
		// worker attempts to change email address |
		// worker_modify_account.jsp               |
		// ----------------------------------------+
		if(request.getParameter("task").equals("WORKER_CHANGE_EMAIL")) {
			// if valid email change, redirect to worker_pending_deliveries.jsp
			// else redirect to worker_modify_account.jsp with error message
			User user = session.getAttribute("user");
			int userId = user.getId();
			String password = request.getParameter("password");
			if(helper.hash(password).equals(helper.getPasswordFor(userId))) {
				String newEmail = request.getParameter("new_email");
				String verifyEmail = request.getParameter("verify_email");
				if(newEmail.length() >= 8) {
					if(newEmail.equals(verifyEmail)) {
						helper.updateEmail(userId, newEmail);
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_pending_deliveries.jsp");
						dispatcher.forward(request, response);
						return;
					}
					else {
						request.setAttribute("email_error", "Error: email addresses do not match");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
				else {
					request.setAttribute("email_error", "Error: email address too short");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
			else {
				request.setAttribute("email_error", "Error: current password is not correct");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		
		// ----------------------------------------------+
		// worker attempts to change transportation type |
		// worker_modify_account.jsp                     |
		// ----------------------------------------------+
		if(request.getParameter("task").equals("WORKER_CHANGE_VEHICLE")) {
			// if valid transportation change, redirect to worker_pending_deliveries.jsp
			// else redirect to worker_modify_account.jsp with error message
			User user = session.getAttribute("user");
			int userId = user.getId();
			String password = request.getParameter("password");
			if(helper.hash(password).equals(helper.getPasswordFor(userId))) {
				String transportation = request.getParameter("vehicle");
				if(!transportation.equals("none")) {
					int transportationValue = -1;
					if(transportation.equals("bike")) {
						transportationValue = 1;
					}
					else if(transportation.equals("car")) {
						transportationValue = 2;
					}
					else if(transportation.equals("truck")) {
						transportationValue = 3;
					}
					helper.updateTransportation(userId, transportationValue);
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_pending_deliveries.jsp");
					dispatcher.forward(request, response);
					return;
				}
				else {
					request.setAttribute("vehicle_error", "Error: must select vehicle type");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
					dispatcher.forward(request, response);
					return;
				}
				
			}
			else {
				request.setAttribute("vehicle_error", "Error: current password is not correct");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_modify_account.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		
	}

}

package dawgdash.controllers;

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
			// make sure admin password is correct
			// make sure new password is at least 8 characters
			// make sure new password matches verification
			
			// else redirect to admin.jsp (successful password change)
		}
		
		// ------------------------------------+
		// admin attempts to create new worker |
		// account_administration.jsp          |
		// ------------------------------------+
		if(request.getParameter("task").equals("ADMIN_CREATE_USER")) {
			// if valid user creation, redirect to admin.jsp
			// else redirect to account_administration.jsp with user creation error message
		}
		
		// ---------------------------------------------+
		// user attempts to create new customer account |
		// create_account.jsp                           |
		// ---------------------------------------------+
		if(request.getParameter("task").equals("CREATE_CUSTOMER_ACCOUNT")) {
			// if valid submission, redirect to index.jsp
			// else redirect to create_account.jsp with error message
		}
		
		// -------------------------------------+
		// customer attempts to change password |
		// customer_modify_account.jsp          |
		// -------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_CHANGE_PASSWORD")) {
			// if valid change, redirect to welcome.jsp with confirmation message
			// else redirect to customer_modify_account.jsp with error message
		}
		
		// ------------------------------------------+
		// customer attempts to change email address |
		// customer_modify_account.jsp               |
		// ------------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_CHANGE_EMAIL")) {
			// if valid change, redirect to welcome.jsp with confirmation message
			// else redirect to customer_modify_account.jsp with error message
		}
		
		// --------------------------------------------------+
		// customer attempts to change default email address |
		// customer_modify_account.jsp                       |
		// --------------------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_CHANGE_ADDRESS")) {
			// if valid change, redirect to welcome.jsp with confirmation message
			// else redirect to customer_modify_account.jsp with error message
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
			// if valid schedule change, redirect to admin.jsp
			// else redirect to individual_schedule.jsp with error message
		}
		
		// -----------------------------------+
		// worker attempts to change password |
		// worker_modify_account.jsp          |
		// -----------------------------------+
		if(request.getParameter("task").equals("WORKER_CHANGE_PASSWORD")) {
			// if valid password change, redirect to worker_pending_deliveries.jsp
			// else redirect to worker_modify_account.jsp with error message
		}
		
		// ----------------------------------------+
		// worker attempts to change email address |
		// worker_modify_account.jsp               |
		// ----------------------------------------+
		if(request.getParameter("task").equals("WORKER_CHANGE_EMAIL")) {
			// if valid email change, redirect to worker_pending_deliveries.jsp
			// else redirect to worker_modify_account.jsp with error message
		}
		
		// ----------------------------------------------+
		// worker attempts to change transportation type |
		// worker_modify_account.jsp                     |
		// ----------------------------------------------+
		if(request.getParameter("task").equals("WORKER_CHANGE_VEHICLE")) {
			// if valid transportation change, redirect to worker_pending_deliveries.jsp
			// else redirect to worker_modify_account.jsp with error message
		}
		
	}

}

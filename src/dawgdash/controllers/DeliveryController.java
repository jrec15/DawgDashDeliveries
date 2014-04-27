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
 * Servlet implementation class LoginController
 */
public class DeliveryController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeliveryController() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DBHelper helper = new DBHelper();
		ServletContext ctx = this.getServletContext();
		HttpSession session = request.getSession();
		
		// --------------------------------+
		// user requests checkout page     |
		// customer_pending_deliveries.jsp |
		// --------------------------------+
		if(request.getParameter("task").equals("CHECKOUT")) {
			// redirect to checkout.jsp
		}
		
		// --------------------------+
		// user requests an estimate |
		// index.jsp
		// --------------------------+
		if(request.getParameter("task").equals("ESTIMATE")) {
			// if valid, redirect to index.jsp with estimate
			// else redirect to index.jsp with error
		}
		
		// ----------------------------------------------+
		// customer attempts to view a previous delivery |
		// previous_deliveries.jsp                       |
		// ----------------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_PAST_DELIVERY")) {
			// redirect to customer_delivery.jsp 
		}
		
		// ---------------------------------+
		// worker views individual delivery |
		// worker_pending_deliveries.jsp    |
		// ---------------------------------+
		if(request.getParameter("task").equals("VIEW_DELIVERY")) {
			// redirect to worker_delivery.jsp
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//DBHelper helper = new DBHelper();
		ServletContext ctx = this.getServletContext();
		HttpSession session = request.getSession();
		
		// -------------------------+
		// customer submits payment |
		// checkout.jsp             |
		// -------------------------+
		if(request.getParameter("task").equals("SUBMIT_PAYMENT")) {
			// if valid submission, redirect to welcome.jsp
			// else redirect to checkout.jsp with error message
		}
		
		// ---------------------------------------------+
		// customer attempts to cancel pending delivery |
		// customer_pending_deliveries.jsp              |
		// ---------------------------------------------+
		if(request.getParameter("task").equals("CANCEL_DELIVERY")) {
			// if more deliveries, redirect to customer_pending_deliveries.jsp
			// else redirect to welcome.jsp
		}
		
		// -----------------------------------------+
		// customer attempts to schedule a delivery |
		// schedule_delivery.jsp                    |
		// -----------------------------------------+
		if(request.getParameter("task").equals("SCHEDULE_DELIVERY")) {
			// if valid submission, redirect to customer_pending_deliveries.jsp
			// else redirect to schedule_delivery.jsp with error message
		}

		// ---------------------------+
		// worker comments a delivery |
		// worker_delivery.jsp        |
		// ---------------------------+
		if(request.getParameter("task").equals("WORKER_COMMENT_DELIVERY")) {
			// if valid submission, redirect to worker_pending_deliveries.jsp
			// else redirect to worker_delivery.jsp
		}
		
		// ---------------------------------------+
		// worker marks delivery as uncompletable |
		// worker_delivery.jsp                    |
		// ---------------------------------------+
		if(request.getParameter("task").equals("UNCOMPLETABLE")) {
			// redirect to worker_pending_deliveries.jsp
		}
	}

}

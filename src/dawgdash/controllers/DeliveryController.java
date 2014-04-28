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
		DBHelper helper = new DBHelper();
		ServletContext ctx = this.getServletContext();
		HttpSession session = request.getSession();
		
		// --------------------------------+
		// user requests checkout page     |
		// customer_pending_deliveries.jsp |
		// --------------------------------+
		if(request.getParameter("task").equals("CHECKOUT")) {
			// redirect to checkout.jsp
			// TODO change param value in checkout.jsp
			User user = session.getAttribute("user");
			int userId = user.getId();
			ArrayList<Delivery> deliveries = helper.getPendingCustomerDeliveriesFor(userId);
			int priceInt = 0;
			for(Delivery delivery : deliveries) {
				priceInt += delivery.getPrice();
			}
			String price = Integer.toString(priceInt);
			request.setAttribute("price", price);
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/checkout.jsp");
			dispatcher.forward(request, response);
		}
		
		// --------------------------+
		// user requests an estimate |
		// index.jsp
		// --------------------------+
		if(request.getParameter("task").equals("ESTIMATE")) {
			// if valid, redirect to index.jsp with estimate
			// else redirect to index.jsp with error
			// TODO change param names in index.jsp for estimates and error section
			try {
				String size = request.getParameter("item_size");
				int quantity = Integer.parseInt(request.getParameter("item_size"));
				int priceInt;
				String price;
				if(size.equals("not_selected")) {
					request.setAttribute("estimate_error", "Error: must select package size");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
				else if(size.equals("letter")) {
					price = "5";
					request.setAttribute("price", price);
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
				else if(size.equals("small")) {
					priceInt = 5 + 1 * quantity;
					price = Integer.toString(priceInt);
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
				else if(size.equals("large")) {
					priceInt = 5 + 2 * quantity;
					price = Integer.toString(priceInt);
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
					dispatcher.forward(request, response);
				}
			}
			catch(NumberFormatException e) {
				request.setAttribute("estimate_error", "Error: must enter integer with only numeric characters");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/index.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		// ----------------------------------------------+
		// customer attempts to view a previous delivery |
		// previous_deliveries.jsp                       |
		// ----------------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_PAST_DELIVERY")) {
			// redirect to customer_delivery.jsp 
			User user = session.getAttribute("user");
			int userId = user.getId();
			int deliveryId = Integer.parseInt(request.getParameter("delivery_id"));
			Delivery delivery = helper.getDelivery(deliveryId);
			request.setAttribute("delivery", delivery);
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_delivery.jsp");
			dispatcher.forward(request, response);
		}
		
		// -----------------------------------------+
		// customer requests schedule delivery page |
		// welcome.jsp                              |
		// -----------------------------------------+
		if(request.getParameter("task").equals("SCHEDULE_DELIVERY")) {
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/schedule_delivery.jsp");
			dispatcher.forward(request, response);
		}
		
		// ------------------------------------------+
		// customer requests pending deliveries page |
		// welcome.jsp                               |
		// ------------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_PENDING_DELIVERIES")) {
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/customer_pending_deliveries.jsp");
			User user = session.getAttribute("user");
			int userId = user.getId();
			ArrayList<Delivery> deliveries = helper.getPendingCustomerDeliveriesFor(userId);
			request.setAttribute("pending_deliveries", deliveries);
			dispatcher.forward(request, response);
		}
		
		// ---------------------------------------+
		// customer requests past deliveries page |
		// welcome.jsp                            |
		// ---------------------------------------+
		if(request.getParameter("task").equals("CUSTOMER_PAST_DELIVERIES")) {
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/previous_deliveries.jsp");
			User user = session.getAttribute("user");
			int userId = user.getId();
			ArrayList<Delivery> deliveries = helper.getPreviousCustomerDeliveriesFor(userId);
			request.setAttribute("past_deliveries", deliveries);
			dispatcher.forward(request, response);
		}
		
		// ---------------------------------+
		// worker views individual delivery |
		// worker_pending_deliveries.jsp    |
		// ---------------------------------+
		if(request.getParameter("task").equals("VIEW_DELIVERY")) {
			// redirect to worker_delivery.jsp
			User user = session.getAttribute("user");
			int userId = user.getId();
			int deliveryId = Integer.parseInt(request.getParameter("delivery_id"));
			Delivery delivery = helper.getDelivery(deliveryId);
			request.setAttribute("delivery", delivery);
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_delivery.jsp");
			dispatcher.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBHelper helper = new DBHelper();
		ServletContext ctx = this.getServletContext();
		HttpSession session = request.getSession();
		
		// -------------------------+
		// customer submits payment |
		// checkout.jsp             |
		// -------------------------+
		if(request.getParameter("task").equals("SUBMIT_PAYMENT")) {
			// if valid submission, redirect to welcome.jsp
			// else redirect to checkout.jsp with error message
			String ccNumber = request.getParameter("credit_card");
			if(ccNumber.length() == 15 && ccNumber.length() == 16) {
				int userId = Integer.parseInt(request.getParameter("user_id"));
				helper.makeAllDeliveriesInProgress(userId);
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/welcome.jsp");
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("error", "Error: Invalid credit card number");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/checkout.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}
		
		// ---------------------------------------------+
		// customer attempts to cancel pending delivery |
		// customer_pending_deliveries.jsp              |
		// ---------------------------------------------+
		if(request.getParameter("task").equals("CANCEL_DELIVERY")) {
			// if more deliveries, redirect to customer_pending_deliveries.jsp
			// else redirect to welcome.jsp
			User user = session.getAttribute("user");
			int userId = user.getId();
			int deliveryId = Integer.parseInt(request.getParameter("delivery_id"));
			helper.cancelDelivery(deliveryId);
			ArrayList<Delivery> deliveries = helper.getPendingCustomerDeliveriesFor(userId);
			if(deliveries.empty()) {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("welcome.jsp");
				dispatcher.forward(request, response);
			}
			else {
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("customer_pending_deliveries.jsp");
				request.setAttribute("pending_deliveries", deliveries);
				dispatcher.forward(request, response);
			}
		}
		
		// -----------------------------------------+
		// customer attempts to schedule a delivery |
		// schedule_delivery.jsp                    |
		// -----------------------------------------+
		if(request.getParameter("task").equals("SCHEDULE_DELIVERY")) {
			// if valid submission, redirect to customer_pending_deliveries.jsp
			// else redirect to schedule_delivery.jsp with error message
			User user = session.getAttribute("user");
			int userId = user.getId();
			String pickupAddress = request.getParameter("pickup_address");
			try {
				if(pickupAddress.length() >= 16 || pickupAddress.length() == 0) {
					if(pickupAddress.length() == 0) {
						pickupAddress = helper.getDefaultAddress(userId);
					}
					String destinationAddress = request.getParameter("destination_address");
					if(destinationAddress.length() >= 16) {
						int quantity = Integer.parseInt(request.getParameter("quantity"));
						String size = request.getParameter("size");
						if(!size.equals("none")) {
							int sizeInt = 0;
							if(size.equals("letter")) {
								sizeInt = 1;
							}
							else if(size.equals("small")) {
								sizeInt = 2;
							}
							else {
								sizeInt = 3;
							}
							String description = request.getParameter("description");
							if(description.length() >= 8) {
								String instruction = request.getParameter("instructions");
								String pickupTime = request.getParameter("time");
								if(!pickupTime.equals("none")) {
									helper.scheduleDelivery(userId, pickupAddress, destinationAddress, quantity, sizeInt, description, instruction, pickupTime);
								}
								else {
									request.setAttribute("error", "Error: must select pickup time");
									RequestDispatcher dispatcher = ctx.getRequestDispatcher("schedule_delivery.jsp");
									dispatcher.forward(request, response);
									return;
								}
							}
							else {
								request.setAttribute("error", "Error: delivery description too short");
								RequestDispatcher dispatcher = ctx.getRequestDispatcher("schedule_delivery.jsp");
								dispatcher.forward(request, response);
								return;
							}
						}
						else {
							request.setAttribute("error", "Error: Must select largest package size");
							RequestDispatcher dispatcher = ctx.getRequestDispatcher("schedule_delivery.jsp");
							dispatcher.forward(request, response);
							return;
						}
					}
					else {
						request.setAttribute("error", "Error: Invalid destination address");
						RequestDispatcher dispatcher = ctx.getRequestDispatcher("schedule_delivery.jsp");
						dispatcher.forward(request, response);
						return;
					}
				}
				else {
					request.setAttribute("error", "Error: Invalid pickup address");
					RequestDispatcher dispatcher = ctx.getRequestDispatcher("schedule_delivery.jsp");
					dispatcher.forward(request, response);
					return;
				}
			}
			catch (NumberFormatException e) {
				request.setAttribute("error", "Error: must enter integer with only numberic characters");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/schedule_delivery.jsp");
				dispatcher.forward(request, response);
			}
		}

		// ---------------------------+
		// worker comments a delivery |
		// worker_delivery.jsp        |
		// ---------------------------+
		if(request.getParameter("task").equals("WORKER_COMMENT_DELIVERY")) {
			// if valid submission, redirect to worker_pending_deliveries.jsp
			// else redirect to worker_delivery.jsp
			String comment = request.getParameter("comment");
			if(comment.length() >= 8) {
				int deliveryId = Integer.parseInt(request.getParameter("delivery_id"));
				helper.addWorkerComment(deliveryId, comment);
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_pending_deliveries.jsp");
				dispatcher.forward(request, response);
			} else {
				request.setAttribute("error", "Error: comment too short");
				RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_delivery.jsp");
				dispatcher.forward(request, response);
			}
		}
		
		// ---------------------------------------+
		// worker marks delivery as uncompletable |
		// worker_delivery.jsp                    |
		// ---------------------------------------+
		if(request.getParameter("task").equals("UNCOMPLETABLE")) {
			// redirect to worker_pending_deliveries.jsp
			int deliveryId = Integer.parseInt(request.getParameter("delivery_id"));
			helper.markUncompletable(deliveryId);
			RequestDispatcher dispatcher = ctx.getRequestDispatcher("/worker_pending_deliveries.jsp");
			dispatcher.forward(request, response);
		}
	}

}

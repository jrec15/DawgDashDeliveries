package dawgdash.controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EchoServlet
 */
public class EchoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EchoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Diagnostic</title></head>");
		out.println("<body>");
		out.println("<h1>Request Parameters</h1>");
        out.println("<table><tr><th>Name</th><th>Value</th></tr>");
        out.println("<tr>");
        Iterable<String> paramList = Collections.list(request.getParameterNames());
        for(String param : paramList) {
            out.println("<td>" + param + "</td>");
            out.println("<td>" + request.getParameter(param) + "</td>");
            out.println("</tr>");
        }               
        out.println("</table>");
        out.println("</body></html>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>Diagnostic</title></head>");
		out.println("<body>");
		out.println("<h1>Request Parameters</h1>");
        out.println("<table><tr><th>Name</th><th>Value</th></tr>");
        out.println("<tr>");
        Iterable<String> paramList = Collections.list(request.getParameterNames());
        for(String param : paramList) {
            out.println("<td>" + param + "</td>");
            out.println("<td>" + request.getParameter(param) + "</td>");
            out.println("</tr>");
        }               
        out.println("</table>");
        out.println("</body></html>");

	}

}

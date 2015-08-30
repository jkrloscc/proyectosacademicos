package controller;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

/**
 * Servlet implementation class ForgotServlet
 */

public class ForgotServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger ();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForgotServlet() {
        super();
       
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		request.setAttribute("usermail", request.getParameter("usermail"));
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/forgot.jsp");
		view.forward(request,response);
		
		
			
		}	

}

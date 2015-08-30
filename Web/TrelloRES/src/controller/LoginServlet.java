package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import model.Board;
import model.User;
import dao.BoardDAO;
import dao.JDBCBoardDAOImpl;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;

/**
 * Servlet implementation class LoginServlet
 */

public class LoginServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger ();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
   
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
		view.forward(request,response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("Método POST de LoginServlet");
	
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");		
		
		logger.info("credentials: "+username+" - "+password);
		
		User user = userDao.get(username);
		
		if ((user != null) 
				&& (user.getPassword().equals(password)))
	    {
			HttpSession session = request.getSession();
			session.setAttribute("user",user);
			response.sendRedirect("BoardServlet");
		} 
		else {
			request.setAttribute("messages","Wrong username or password!!");
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/login.jsp");
			view.forward(request,response);
		}	
	}
		
	

}

package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;

/**
 * Servlet implementation class SingUpServlet
 */

public class SingUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SingUpServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		UserDAO userDao = new JDBCUserDAOImpl();
		userDao.setConnection(conn);

		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String usermail = request.getParameter("usermail");
		String password = request.getParameter("password");
		String password2 = request.getParameter("password2");		


		if(!password.equals (password2)){

			request.setAttribute("message1", "Passwords don´t match!");

			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/singup.jsp");
			view.forward(request,response);

		}else{
		
		
		if (!userDao.getAllUsername(username)){
			
			User newUser = new User();
			
			newUser.setName(name);
			newUser.setUsername(username);
			newUser.setEmail(usermail);
			newUser.setPassword(password);
			
			userDao.add(newUser);
			
			response.sendRedirect("LoginServlet");

		}
		else{
			request.setAttribute("message1", "This username already exists. Please try another");
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/singup.jsp");
			view.forward(request,response);
		}

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

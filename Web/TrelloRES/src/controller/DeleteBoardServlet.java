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

import model.Board;
import model.User;
import dao.BoardDAO;
import dao.JDBCBoardDAOImpl;

/**
 * Servlet implementation class DeleteBoardServlet
 */

public class DeleteBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger ();


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteBoardServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		String userName = user.getName();
		
		if (user == null)
			response.sendRedirect("LoginServlet");

		else {
		String id = request.getParameter("boardId");
		String boardName = request.getParameter("boardName");


		request.setAttribute("userName", userName);
		request.setAttribute("boardId", id);
		request.setAttribute("boardName", boardName);

		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/deleteboards.jsp");
		view.forward(request,response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		BoardDAO boardDao = new JDBCBoardDAOImpl();
		boardDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();

		String id = request.getParameter("boardId");

		if (user==null) 
			response.sendRedirect("LoginServlet");
		else{
			if (id != null) {
			
				long bid = Long.parseLong(id); 

				boardDao.delete(bid);
			}

			response.sendRedirect("BoardServlet");
		}

	}	

}

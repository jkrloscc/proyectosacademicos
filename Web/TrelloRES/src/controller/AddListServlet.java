package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.User;
import dao.CardDAO;
import dao.JDBCCardDAOImpl;
import dao.JDBCListDAOImpl;
import dao.ListDAO;

/**
 * Servlet implementation class AddListServlet
 */


public class AddListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddListServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn= (Connection) getServletContext().getAttribute("dbConn");
		ListDAO listDao =new JDBCListDAOImpl();
		listDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();

		if (user==null) 
			response.sendRedirect("LoginServlet");
		else{

			String boardId = request.getParameter("boardId");
			String nameNewList = request.getParameter("nameNewList");
			String boardName = request.getParameter("boardName");


			long id = 0;

			if (boardId != null) 
				id = Long.parseLong(boardId); 


			List<model.List>  lists = listDao.getAllByBoard(id);
			if (lists != null){

				request.setAttribute("listsList",lists);
				request.setAttribute("nameNewList",nameNewList);
				request.setAttribute("boardId", id);
				request.setAttribute("boardName", boardName);
				request.setAttribute("userName", userName);
			}
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/addlists.jsp");
			view.forward(request,response);
		}	


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");

		ListDAO listDao =new JDBCListDAOImpl();
		listDao.setConnection(conn);


		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		String nameNewList = request.getParameter("nameNewList");
		String boardId = request.getParameter("boardId");

		if (user == null)
			response.sendRedirect("LoginServlet");


		else {

			long id = Long.parseLong(boardId); 

			model.List newList = new model.List ();

			newList.setName(nameNewList);
			newList.setBoard(id);
			listDao.add(newList);

			response.sendRedirect("BoardServlet");
		}

	}

}

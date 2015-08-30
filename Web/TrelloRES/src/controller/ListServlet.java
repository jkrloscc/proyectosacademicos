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
import model.Card;
import model.User;
import dao.BoardDAO;
import dao.CardDAO;
import dao.JDBCBoardDAOImpl;
import dao.JDBCCardDAOImpl;
import dao.JDBCListDAOImpl;
import dao.ListDAO;

/**
 * Servlet implementation class ListServlet
 */

public class ListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger ();


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn= (Connection) getServletContext().getAttribute("dbConn");
		
		BoardDAO boardDao =new JDBCBoardDAOImpl();
		boardDao.setConnection(conn);
		ListDAO listDao =new JDBCListDAOImpl();
		listDao.setConnection(conn);
		CardDAO cardDao =new JDBCCardDAOImpl();
		cardDao.setConnection(conn);


		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();

		String boardid = request.getParameter("boardId");

	/*	long id = 0; FIXME
		
		if (boardid != null) 
			id = Long.parseLong(boardid); 
		
		long userId = user.getId();

		Board aux = boardDao.get(id);
			
		if (user==null || (aux.getOwner()!=userId)) 
			response.sendRedirect("LoginServlet"); */
		
		if (user==null) 
			response.sendRedirect("LoginServlet");
		
		else{
						
			long id = 0; 
			
			if (boardid != null) 
				id = Long.parseLong(boardid); 
			
			String boardname = request.getParameter("boardName");
					
			List<model.List>  lists = listDao.getAllByBoard(id);
			if (lists != null){
				
				request.setAttribute("listsList",lists);
				request.setAttribute("boardId", id);
				request.setAttribute("boardName", boardname);
				request.setAttribute("userName", userName);
			}
			
			List<Card>  cards = cardDao.getAll();

			if (cards != null)
				request.setAttribute("cardsList", cards);


			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/lists.jsp");
			view.forward(request,response);
				
		}	
	}




	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

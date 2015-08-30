package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Card;
import model.User;
import dao.BoardDAO;
import dao.CardDAO;
import dao.JDBCBoardDAOImpl;
import dao.JDBCCardDAOImpl;
import dao.JDBCListDAOImpl;
import dao.ListDAO;

/**
 * Servlet implementation class SetCardServlet
 */

public class SetCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetCardServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn= (Connection) getServletContext().getAttribute("dbConn");

		CardDAO cardDao =new JDBCCardDAOImpl();
		cardDao.setConnection(conn);


		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");	

		String userName = user.getName();
		
		if (user == null)
			response.sendRedirect("LoginServlet");

		else {
			
			String cardId = request.getParameter("cardId");
			String newCardName = request.getParameter("newCardName");

			
			long cid = Long.parseLong(cardId); 
			
			Card newCard = cardDao.get(cid);

			newCard.setName(newCardName);
			
			cardDao.save(newCard);

			response.sendRedirect("BoardServlet");
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		
		Connection conn= (Connection) getServletContext().getAttribute("dbConn");

		CardDAO cardDao =new JDBCCardDAOImpl();
		cardDao.setConnection(conn);


		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");	

		String userName = user.getName();
		
		String newListId = request.getParameter("newListId");
		String cardId = request.getParameter("cardId");
		String listBoard = request.getParameter("listBoard");


		if (user == null)
			response.sendRedirect("LoginServlet");

		else {

			long id = Long.parseLong(newListId); 
			long cid = Long.parseLong(cardId); 
			
			Card newCard = cardDao.get(cid);

			newCard.setList(id);
			
			cardDao.save(newCard);

			response.sendRedirect("BoardServlet");
		}
	}

}

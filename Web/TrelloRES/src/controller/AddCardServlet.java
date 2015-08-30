package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Card;
import model.User;
import dao.CardDAO;
import dao.JDBCCardDAOImpl;
import dao.JDBCListDAOImpl;
import dao.ListDAO;

/**
 * Servlet implementation class AddCardServlet
 */


public class AddCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCardServlet() {
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
		CardDAO cardDao =new JDBCCardDAOImpl();
		cardDao.setConnection(conn);


		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();

		if (user==null) 
			response.sendRedirect("LoginServlet");
		else{

			String listId = request.getParameter("listId");
			String listName = request.getParameter("listName");
			String boardId = request.getParameter("boardId");
			String boardName = request.getParameter("boardName");


			long id = 0;

			if (boardId != null) 
				id = Long.parseLong(boardId); 


			List<model.List>  lists = listDao.getAllByBoard(id);
			
			if (lists != null){

				request.setAttribute("listsList",lists);
				request.setAttribute("listName",listName);
				request.setAttribute("listId", listId);
				request.setAttribute("boardId", boardId);
				request.setAttribute("boardName", boardName);
				request.setAttribute("userName", userName);
				
			List<Card>  cards = cardDao.getAll();

				if (cards != null)
					request.setAttribute("cardsList", cards);		
			}		
			
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/addcards.jsp");
			view.forward(request,response);
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

		String nameNewCard = request.getParameter("nameNewCard");
		String listId = request.getParameter("listId");

		if (user == null)
			response.sendRedirect("LoginServlet");

		else {

			long id = Long.parseLong(listId); 
			
			Card newCard = new Card ();

			newCard.setName(nameNewCard);
			newCard.setList(id);
			
			cardDao.add(newCard);

			response.sendRedirect("BoardServlet");
		}

	}



}

package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
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
 * Servlet implementation class CardServlet
 */

public class CardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger ();


	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CardServlet() {
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
		String listName = request.getParameter("listName");
		String listId= request.getParameter("listId");
		String cardId= request.getParameter("cardId");


		if (user==null) 
			response.sendRedirect("LoginServlet");
		else{
			
			ArrayList<model.List> listsList = new ArrayList<model.List>();

			long idUser = user.getId();
			List<Board> boards = boardDao.getAllByUser(idUser);

			Iterator <Board> iter = boards.iterator();
			while (iter.hasNext()){

				Board boardAux = (Board) iter.next();		
				List<model.List>  lists = listDao.getAllByBoard(boardAux.getId());	
				Iterator <model.List> iter2 = lists.iterator();
				while (iter2.hasNext()){

					model.List listAux = (model.List) iter2.next();
					listsList.add(listAux);

				}
			}


			if (listsList != null){

				request.setAttribute("listsList",listsList);
				request.setAttribute("boardsList",boards);

			}

			long cid = Long.parseLong(cardId); 

			Card card = cardDao.get(cid);

			request.setAttribute("userName", userName);
			request.setAttribute("listName", listName);
			request.setAttribute("listId", listId);
			request.setAttribute("card", card);
		}

		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/cards.jsp");
		view.forward(request,response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}

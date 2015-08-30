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

import model.Board;
import model.Card;
import model.User;
import dao.*;

/**
 * Servlet implementation class SetBoardServlet
 */

public class SetBoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SetBoardServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		ListDAO listDao = new JDBCListDAOImpl();
		listDao.setConnection(conn);
		CardDAO cardDao =new JDBCCardDAOImpl();
		cardDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();
		String boardName = request.getParameter("boardName");
		String boardId = request.getParameter("boardId");

		if (user == null)
			response.sendRedirect("LoginServlet");
		else {
			long id = 0;
			if (boardId != null)
				id = Long.parseLong(boardId);

			List<model.List> lists = listDao.getAllByBoard(id);
			if (lists != null) {

				request.setAttribute("listsList", lists);
				request.setAttribute("boardId", boardId);
				request.setAttribute("boardName", boardName);
				request.setAttribute("userName", userName);
			}
			
			List<Card>  cards = cardDao.getAll();

			if (cards != null)
				request.setAttribute("cardsList", cards);

			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/setboards.jsp");
			view.forward(request, response);

		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		BoardDAO boardDao = new JDBCBoardDAOImpl();
		boardDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();
		String newBoardName = request.getParameter("newBoardName");
		String boardId = request.getParameter("boardId");


		if (user == null)
			response.sendRedirect("LoginServlet");
		else {

			long idUser = user.getId();

			long bid = Long.parseLong(boardId); 

			Board newBoard =boardDao.get(bid);
			newBoard.setOwner(idUser);
			newBoard.setName(newBoardName);
			boardDao.save(newBoard);

			List<Board> boards = boardDao.getAllByUser(idUser);

			request.setAttribute("boardList", boards);
			request.setAttribute("userName", userName);

			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/boards.jsp");
			view.forward(request, response);

		}
		
	
	

	}

}

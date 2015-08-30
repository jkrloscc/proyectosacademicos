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
import dao.BoardDAO;
import dao.CardDAO;
import dao.JDBCBoardDAOImpl;
import dao.JDBCCardDAOImpl;
import dao.JDBCListDAOImpl;
import dao.ListDAO;

/**
 * Servlet implementation class SetListServlet
 */

public class SetListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CardDAO cardDao =new JDBCCardDAOImpl();
		cardDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		String userName = user.getName();
		String boardName = request.getParameter("boardName");
		String boardId = request.getParameter("boardId");
		String listName = request.getParameter("listName");
		String listId = request.getParameter("listId");

		if (user == null)
			response.sendRedirect("LoginServlet");
		
		else {
			
			long id = 0;
			if (listId != null)
				id = Long.parseLong(listId);
			
			List<Card>  cards = cardDao.getAllByList(id);

			if (cards != null){
				
			request.setAttribute("cardsList", cards);
			request.setAttribute("listId", listId);
			request.setAttribute("listName", listName);
			request.setAttribute("boardId", boardId);
			request.setAttribute("boardName", boardName);
			request.setAttribute("userName", userName);
			}
			
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/setlists.jsp");
			view.forward(request, response);	

			}
	
	}

	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		
		ListDAO listDao =new JDBCListDAOImpl();
		listDao.setConnection(conn);
		CardDAO cardDao =new JDBCCardDAOImpl();
		cardDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();

		String newlistName = request.getParameter("newListName");
		String listId = request.getParameter("listId");
		String boardName = request.getParameter("boardName");
		String boardId = request.getParameter("boardId");
		
		if (user == null)
			response.sendRedirect("LoginServlet");
		

		else {
								
			long lid = Long.parseLong(listId); 

			model.List newList = listDao.get(lid);
			
			newList.setName(newlistName);
			listDao.save(newList);

			List<model.List> listsList = listDao.getAllByBoard(newList.getBoard());
			request.setAttribute("listsList", listsList);
			request.setAttribute("boardId", boardId);
			request.setAttribute("boardName", boardName);
		
			List<Card>  cards = cardDao.getAllByList(lid );

			if (cards != null){
		
			request.setAttribute("cardsList", cards);
			request.setAttribute("listId", listId);
			request.setAttribute("userName", userName);
			}
			
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/lists.jsp");
			view.forward(request, response);	

			}

		}
	

}

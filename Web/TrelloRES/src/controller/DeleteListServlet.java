package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BoardDAO;
import dao.JDBCBoardDAOImpl;
import dao.JDBCListDAOImpl;
import dao.ListDAO;
import model.User;

/**
 * Servlet implementation class DeleteListServlet
 */

public class DeleteListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		String userName = user.getName();
		
		if (user==null) 
			response.sendRedirect("LoginServlet");
		else{
		String boardId = request.getParameter("boardId");
		String boardName = request.getParameter("boardName");
		String listId = request.getParameter("listId");
		String listName = request.getParameter("listName");


		request.setAttribute("userName", userName);
		request.setAttribute("boardId", boardId);
		request.setAttribute("boardName", boardName);
		request.setAttribute("listId", listId);
		request.setAttribute("listName", listName);
		
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/deletelists.jsp");
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
	
		
		String userName = user.getName();
		String boardId = request.getParameter("boardId");
		String boardName = request.getParameter("boardName");//Mostrar opcional
		String listId = request.getParameter("listId");
		String listName = request.getParameter("listName"); //Mostrar opcional

		if (user==null) 
			response.sendRedirect("LoginServlet");
		else{
			if (listId != null) {
			
				long lid = Long.parseLong(listId); 

				listDao.delete(lid);
			}

			response.sendRedirect("ListServlet");
		}

	}	
	

}

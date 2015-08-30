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
 * Servlet implementation class DeleteCardServlet
 */

public class DeleteCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteCardServlet() {
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
			
		String cardName = request.getParameter("cardName");
		String cardId = request.getParameter("cardId");
		String boardName = request.getParameter("boardName");
		String boardId = request.getParameter("boardId");

		request.setAttribute("cardName", cardName);
		request.setAttribute("cardId", cardId);
		request.setAttribute("boardName", boardName);
		request.setAttribute("boardId", boardId);
		request.setAttribute("userName", userName);
		
		RequestDispatcher view = request.getRequestDispatcher("WEB-INF/deletecards.jsp");
		view.forward(request,response);
		}

	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Connection conn = (Connection) getServletContext().getAttribute("dbConn");
		CardDAO cardDao =new JDBCCardDAOImpl();
		cardDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
			
		String userName = user.getName();

			if (user==null) 
				response.sendRedirect("LoginServlet");
			else{
				
				String cardId = request.getParameter("cardId");
			
				if (cardId != null) {		
					long cid = Long.parseLong(cardId); 
					cardDao.delete(cid);
				}			
				response.sendRedirect("BoardServlet");
			}
	}	
}

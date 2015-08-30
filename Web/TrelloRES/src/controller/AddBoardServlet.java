package controller;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Board;
import model.User;
import dao.BoardDAO;
import dao.JDBCBoardDAOImpl;

/**
 * Servlet implementation class AddBoardServlet
 */

public class AddBoardServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger ();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddBoardServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("Método GET de AddBoardServlet");
		Connection conn= (Connection) getServletContext().getAttribute("dbConn");
		BoardDAO boardDao =new JDBCBoardDAOImpl();
		boardDao.setConnection(conn);


		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();

		
		if (user==null) 
			response.sendRedirect("LoginServlet");
		else{
			long idUser = user.getId();
			List<Board> boards = boardDao.getAllByUser(idUser);
			request.setAttribute("boardList", boards);
			request.setAttribute("userName", userName);
			request.setAttribute("boardOwner", idUser);
			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/Addboards.jsp");
			view.forward(request,response);
		}	

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		logger.info("Método POST de AddBoardServlet");
		Connection conn= (Connection) getServletContext().getAttribute("dbConn");
		BoardDAO boardDao =new JDBCBoardDAOImpl();
		boardDao.setConnection(conn);

		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String userName = user.getName();
		String boardName = request.getParameter("boardName");


		
		if (user==null) 
			response.sendRedirect("LoginServlet");
		else{
			
			long  idUser = user.getId();
			
			Board newBoard = new Board();
			newBoard.setOwner(idUser);
			newBoard.setName(boardName);			
			boardDao.add(newBoard);
			
			response.sendRedirect("BoardServlet");

					
		}	
				
	}

	
}

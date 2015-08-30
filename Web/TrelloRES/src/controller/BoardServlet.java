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
import model.User;
import dao.BoardDAO;
import dao.JDBCBoardDAOImpl;


/**
 * Servlet implementation class BoardServlet
 */

public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getAnonymousLogger ();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		logger.info("Método GET de BoardServlet");
		Connection conn= (Connection) getServletContext().getAttribute("dbConn");
		BoardDAO boardDao =new JDBCBoardDAOImpl();
		boardDao.setConnection(conn);

		/* En cada petición debemos comprobar que existe en sesión el objeto user.
		 * Si no existe, se debe redirigir a LoginServlet	*/
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

			RequestDispatcher view = request.getRequestDispatcher("WEB-INF/boards.jsp");
			view.forward(request,response);
		}	


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



	}

}

package controller;

import java.io.IOException;
import java.sql.Connection;

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

/**
 * Servlet implementation class SetDescriptionCardServlet
 */


public class SetDescriptionCardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetDescriptionCardServlet() {
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
			String cardDescription = request.getParameter("cardDescription");
			
			long cid = Long.parseLong(cardId); 
			
			Card newCard = cardDao.get(cid);
			newCard.setDescription(cardDescription);
			cardDao.save(newCard);

			response.sendRedirect("BoardServlet");
		}

	}

}

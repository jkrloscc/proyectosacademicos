package resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Board;
import model.User;
import dao.JDBCBoardDAOImpl;
import dao.BoardDAO;
import dao.JDBCListDAOImpl;
import dao.ListDAO;
import exceptions.CustomBadRequestException;

@Path("/boards")
public class BoardResource {

	// Variables de contexto de JSON

	@Context
	ServletContext sc;

	@Context
	UriInfo uriInfo;

	@Context
	HttpServletRequest request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Board> getBoardsJSON() {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		BoardDAO boardDao = new JDBCBoardDAOImpl();
		boardDao.setConnection(conn);

		User user = (User) request.getSession().getAttribute("user");
		List<Board> boards = new ArrayList<Board>();

		if (user != null)

			boards = boardDao.getAllByUser(user.getId());
		else
			System.out.println("usuario no existe");

		return boards;
	}

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<model.List> getListsJSON(@PathParam("id") long id) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		ListDAO listDao = new JDBCListDAOImpl();
		listDao.setConnection(conn);

		User user = (User) request.getSession().getAttribute("user");
		List<model.List> lists = new ArrayList<model.List>();

		if (user != null)
			lists = listDao.getAllByBoard(id);

		return lists;

	}

}

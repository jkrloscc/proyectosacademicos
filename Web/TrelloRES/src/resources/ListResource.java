package resources;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import model.Card;
import model.User;
import dao.CardDAO;
import dao.JDBCCardDAOImpl;
import dao.ListDAO;
import dao.JDBCListDAOImpl;
import exceptions.CustomBadRequestException;

@Path("/lists")
public class ListResource {

	// Variables de contexto de JSON

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<model.List> getListsJSON(@PathParam("id") long id) {

		System.out.println("Metodo get");
		Connection conn = (Connection) sc.getAttribute("dbConn");
		ListDAO listDao = new JDBCListDAOImpl();
		listDao.setConnection(conn);

		List<model.List> lists = listDao.getAllByBoard(id);
		return lists;
	}

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Card> getCardsJSON(@PathParam("id") long id) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CardDAO cardDao = new JDBCCardDAOImpl();
		cardDao.setConnection(conn);

		User user = (User) request.getSession().getAttribute("user");
		List<Card> cards = new ArrayList<Card>();

		if (user != null)

			cards = cardDao.getAllByList(id);

		return cards;

	}

}

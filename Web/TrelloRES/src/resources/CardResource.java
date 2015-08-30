package resources;

import java.sql.Connection;
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
import dao.CardDAO;
import dao.JDBCCardDAOImpl;
import exceptions.CustomBadRequestException;
import exceptions.CustomNotFoundException;

@Path("/cards")
public class CardResource {

	// Variables de contexto de JSON

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Card> getCardsJSON() {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CardDAO cardDao = new JDBCCardDAOImpl();
		cardDao.setConnection(conn);

		List<Card> cards = cardDao.getAll();
		return cards;
	}

	@GET
	@Path("/{id: [0-9]+}")
	@Produces(MediaType.APPLICATION_JSON)
	public Card getCardJSON(@PathParam("id") long id) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		CardDAO cardDao = new JDBCCardDAOImpl();
		cardDao.setConnection(conn);

		Card card = cardDao.get(id);

		if (card == null)
			throw new CustomNotFoundException("Card, " + id + ", is not found");

		return card;
	}

}

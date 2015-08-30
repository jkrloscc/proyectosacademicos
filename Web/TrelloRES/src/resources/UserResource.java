package resources;

import java.sql.Connection;
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
import model.User;
import dao.JDBCUserDAOImpl;
import dao.UserDAO;
import exceptions.CustomBadRequestException;

@Path("/login")
public class UserResource {

	@Context
	ServletContext sc;
	@Context
	UriInfo uriInfo;
	@Context
	HttpServletRequest request; // Contexto para guardar la sesion.

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String authUsersJSON(@QueryParam("username") String name,
			@QueryParam("password") String password) {

		Connection conn = (Connection) sc.getAttribute("dbConn");
		UserDAO UserDao = new JDBCUserDAOImpl();
		UserDao.setConnection(conn);
		String correct = "{\"ok\":\"false\"}";

		User user = UserDao.get(name);

		if (user != null && user.getPassword().equals(password)) {
			correct = "{\"ok\":\"true\"}";
			request.getSession().setAttribute("user", user);

		}

		return correct;
	}

}
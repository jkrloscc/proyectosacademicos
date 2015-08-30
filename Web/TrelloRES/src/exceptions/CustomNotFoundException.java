package exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.Responses;

public class CustomNotFoundException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -402752772426499120L;

	public CustomNotFoundException() {
		super(Responses.notFound().build());
	}

	public CustomNotFoundException(String message) {
		super(Response
				.status(Responses.NOT_FOUND)
				.entity("{\"status\" : \"404\", \"userMessage\" : \""+message+"\"}")
				.type("application/json")
				.build());
	}
}

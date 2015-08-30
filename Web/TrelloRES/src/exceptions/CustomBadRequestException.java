package exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import com.sun.jersey.api.Responses;

public class CustomBadRequestException extends WebApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -402752772426499120L;

	public CustomBadRequestException() {
		super(Responses.clientError().build());
	}

	public CustomBadRequestException(String message) {
		super(Response
				.status(Responses.CLIENT_ERROR)
				.entity("{\"status\" : \"400\", \"userMessage\" : \""+message+"\"}")
				.type("application/json")
				.build());
	}
}

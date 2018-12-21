package entity;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class ApiExceptionMapper implements ExceptionMapper<ApiException> {

	
	public Response toResponse(ApiException e) {
		return Response.status(e.getStatus())
				.entity(e.getMessage())
				.type(MediaType.APPLICATION_JSON)
				.build();
	}

}

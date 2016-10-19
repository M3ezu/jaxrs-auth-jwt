package fi.hupiukot.ties456.jaxrs.auth.jwt.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import fi.hupiukot.ties456.jaxrs.auth.jwt.JWTToken;


/**
*
* return the JWT token in response header
*/
@Path("token")
public class AuthResource {
	
	@Context
    SecurityContext sctx;

    @GET
    @Produces("text/plain")
    public Response auth() { 
    	System.out.println("Authenticated user: " + sctx.getUserPrincipal().getName());
    	
    	String authenticatedUser = sctx.getUserPrincipal().getName();
    	
    	Response response = Response.ok(authenticatedUser + " authenticated")
                .header("jwt", JWTToken.buildJWT(authenticatedUser)) // GET JWT HERE
                .build();

        return response;
    }

}

package fi.hupiukot.ties456.jaxrs.auth.jwt.filters;

import java.io.IOException;

import javax.annotation.Priority;
import java.security.Principal;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import fi.hupiukot.ties456.jaxrs.auth.jwt.RsaKeyProducer;

@Priority(Priorities.AUTHENTICATION)
public class JWTAuthFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("request filter...");
		
		String authHeader = requestContext.getHeaderString("Authorization");
		
		//consume JWT i.e. execute signature validation
        if (authHeader.startsWith("Bearer")) {
            try {
                System.out.println("JWT based Auth... verify signature");
                System.out.println("JWT:\n" + authHeader.split(" ")[1]);
                final String subject = validate(authHeader.split(" ")[1]);
                final SecurityContext securityContext = requestContext.getSecurityContext();
                if (subject != null) {
                    requestContext.setSecurityContext(new SecurityContext() {
                        @Override
                        public Principal getUserPrincipal() {
                            return new Principal() {
                                @Override
                                public String getName() {
                                    System.out.println("Principal - " + subject);
                                    return subject;
                                }
                            };
                        }

                        @Override
                        public boolean isUserInRole(String role) {
                            return securityContext.isUserInRole(role);
                        }

                        @Override
                        public boolean isSecure() {
                            return securityContext.isSecure();
                        }

                        @Override
                        public String getAuthenticationScheme() {
                            return securityContext.getAuthenticationScheme();
                        }
                    });
                }
            } catch (InvalidJwtException ex) {
                System.out.println("JWT failed");

                requestContext.setProperty("auth-failed", true);
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());

            }

        } else {
            System.out.println("No JWT");
            requestContext.setProperty("auth-failed", true);
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
		
	}
	
	  private String validate(String jwt) throws InvalidJwtException {
	        String subject = null;
	        RsaJsonWebKey rsa = RsaKeyProducer.produce();

	        System.out.println("RSA hash code... " + rsa.hashCode());

	        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
	                .setRequireSubject() // the JWT must have a subject claim
	                .setVerificationKey(rsa.getKey()) // verify the signature with the public key
	                .build(); // create the JwtConsumer instance

	        try {
	            //  Validate the JWT and process it to the Claims
	            JwtClaims claims = jwtConsumer.processToClaims(jwt);
	            subject = (String) claims.getClaimValue("sub");
	            System.out.println("JWT validation ok! " + claims);
	        } catch (InvalidJwtException e) {
	            e.printStackTrace(); //on purpose
	            throw e;
	        }

	        return subject;
	    }

}

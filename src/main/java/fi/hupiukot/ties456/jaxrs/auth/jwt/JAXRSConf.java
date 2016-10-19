package fi.hupiukot.ties456.jaxrs.auth.jwt;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import fi.hupiukot.ties456.jaxrs.auth.jwt.filters.JWTAuthFilter;
import fi.hupiukot.ties456.jaxrs.auth.jwt.filters.JWTResponseFilter;
import fi.hupiukot.ties456.jaxrs.auth.jwt.resources.MyResource;
import fi.hupiukot.ties456.jaxrs.auth.jwt.resources.OrderResource;

/**
*
* to /resources/
*/
@ApplicationPath("resources")
public class JAXRSConf extends Application {
	
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> luokat = new HashSet();
        luokat.add(OrderResource.class); // Not working!
        luokat.add(JWTAuthFilter.class);
        luokat.add(JWTResponseFilter.class); 
        luokat.add(MyResource.class); // Works, tested!

        return luokat;
    }
	
}

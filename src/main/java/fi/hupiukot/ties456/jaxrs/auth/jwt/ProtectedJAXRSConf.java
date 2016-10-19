package fi.hupiukot.ties456.jaxrs.auth.jwt;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import fi.hupiukot.ties456.jaxrs.auth.jwt.resources.AuthResource;

/**
*
* specific to /auth/token
*/
@ApplicationPath("auth")
public class ProtectedJAXRSConf extends Application {

	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> luokat = new HashSet();
        luokat.add(AuthResource.class);

        return luokat;
    }
}

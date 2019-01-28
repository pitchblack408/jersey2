package pitchblack408.jersey;

//import java.util.HashSet;
//import java.util.Set;
//import javax.ws.rs.ApplicationPath;
//import javax.ws.rs.core.Application;
//import mmartin.jersey.services.HelloWorldResource;
//
//@ApplicationPath("/v1")
//public class MyApplication extends Application {
//    @Override
//    public Set<Class<?>> getClasses() {
//        Set<Class<?>> s = new HashSet<Class<?>>();
//        s.add(HelloWorldResource.class);
//        return s;
//    }
//}


import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/v1")
public class MyApplication extends ResourceConfig {
    public MyApplication() {
        // Define the package which contains the service classes.
        packages("mmartin.jersey.services");
    }
}




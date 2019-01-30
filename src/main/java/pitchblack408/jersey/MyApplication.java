/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
        packages("pitchblack408.jersey.services", "pitchblack408.jersey.security.filter");
    }
}




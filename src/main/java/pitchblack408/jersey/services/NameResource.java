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


package pitchblack408.jersey.services;

import javax.ws.rs.core.Response.Status;

import pitchblack408.jersey.security.annotation.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

 
@Path("name")
@Produces(MediaType.APPLICATION_JSON)
public class NameResource {
	
  @GET
  @Secured
  @Path("{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getNameById(@PathParam("id") Long id) {
      // This method is not annotated with @Secured
      // The authentication filter won't be executed before invoking this method
	  String name = "{\"first_name\":\"John\",\"last_name\":\"Smith\"}";
	  return Response.status(Status.OK).entity(name).build();
  }	
	
}
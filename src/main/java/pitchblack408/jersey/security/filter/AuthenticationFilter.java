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

package pitchblack408.jersey.security.filter;

import javax.ws.rs.ext.Provider;


import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

//import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;

import pitchblack408.jersey.security.annotation.Secured;
import pitchblack408.jersey.security.exceptions.JwtScopeValidationException;




//@Secured(value = { "" })
@Secured
@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {
	
	//JWK_SET_URL Should be grabbed from properties file
	private static final String JWK_SET_URL= "https://xxxxxxx/.well-known/openid-configuration/jwks";
	
	//The following is defined with the provided from the identity server
    private static final String REALM = "com.org";
    //The following is defined with the provided from the identity server
    //You can have multiple scopes identified, but for simplicity we are just using one scope here.
    private static final String RESOURCE1_SCOPE ="com.org.app";

    //Don't change this
    private static final String AUTHENTICATION_SCHEME = "Bearer";

    
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        // Get the Authorization header from the request
        String authorizationHeader =
                requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);

        // Validate the Authorization header
        if (!isTokenBasedAuthentication(authorizationHeader)) {
            abortWithUnauthorized(requestContext);
            return;
        }

        // Extract the token from the Authorization header
        String token = authorizationHeader
                            .substring(AUTHENTICATION_SCHEME.length()).trim();

        try {

            // Validate the token
            validateTokenAndScopes(token);

        } catch (Exception e) {
            abortWithUnauthorized(requestContext);
        }
    }

    private boolean isTokenBasedAuthentication(String authorizationHeader) {

        // Check if the Authorization header is valid
        // It must not be null and must be prefixed with "Bearer" plus a whitespace
        // The authentication scheme comparison must be case-insensitive
        return authorizationHeader != null && authorizationHeader.toLowerCase()
                    .startsWith(AUTHENTICATION_SCHEME.toLowerCase() + " ");
    }

    private void abortWithUnauthorized(ContainerRequestContext requestContext) {

        // Abort the filter chain with a 401 status code response
        // The WWW-Authenticate header is sent along with the response
        requestContext.abortWith(
                Response.status(Response.Status.UNAUTHORIZED)
                        .header(HttpHeaders.WWW_AUTHENTICATE, 
                                AUTHENTICATION_SCHEME + " realm=\"" + REALM + "\"")
                        .build());
    }

    private void validateTokenAndScopes(String token) throws JwtException, JwtScopeValidationException {
        // Check if the token was issued by the server and if it's not expired
        // Throw an Exception if the token is invalid
    	
    	//TODO Probably want to cache this Object because, NimbusJwtDecoderJwkSupport will using a rest template client to get the object
    	NimbusJwtDecoderJwkSupport nimbusJwtDecoderJwkSupport = new NimbusJwtDecoderJwkSupport(JWK_SET_URL);
    	//Decoding will perform validation on timestamp and signature
		Jwt decodedToken = nimbusJwtDecoderJwkSupport.decode(token);
		String scopesStr =  decodedToken.getClaims().get("scope").toString();
		String[] splitResult = scopesStr.replace("[", "").replace("]", "").replace("\"", "").split(",");
		if(splitResult.length<1)
			throw new JwtScopeValidationException("Internal error failed to parse scopes from JWT.");
		List<String> scopeList=Arrays.asList(splitResult);
		
		//Check if scope(s) exists
		String result = scopeList.stream().filter(scope -> RESOURCE1_SCOPE.equals(scope)).findAny().orElse(null);
		if (result== null)
			throw new JwtScopeValidationException("Scope \""+RESOURCE1_SCOPE+"\" was not found in JWT.");
		
		//Since I know that there is only one scope being used, I omitted some work
    
    	
    }
}
# jersey2
This is an example of jersey2 application behaving as a oauth2 resource server.
The application is using some of the internal spring libraries to decode JWT tokens and with openid-configuration JWKS. 

## Jersey2 and Jetty
This project can tested on Jetty using the "Run Jetty Run" Eclipse plugin.

Steps after installing the plugin from the eclipse market place:

1. mvn clean
2. mvn package
3. On project in eclipse right click -> maven -> update project
3. On the project right click and run as  Run Jetty
4. Stop the process from running on the console
5. Go to Run Configurations and edit the Jetty application configuration and edit the context to: / 
6. Now rerun with Run as: Run Jetty

For more info:
https://github.com/xzer/run-jetty-run/wiki/GettingStarted
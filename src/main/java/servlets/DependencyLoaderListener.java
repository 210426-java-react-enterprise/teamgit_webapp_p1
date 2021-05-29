package servlets;

import controllers.*;
import dispatchers.*;
import io.jsonwebtoken.*;
import repos.*;
import security.*;
import services.*;

import javax.servlet.*;

public class DependencyLoaderListener implements ServletContextListener {

    //WHat if there are bunch of dependencies that we need to bring in at once
    //WHat if they are scattered around the application?
    //You abstract that static try block in your ConnectionFactory so that
    //it loads postgresql when the context is initialized

    //YOU NEED TO INFORM TOMCAT THAT THIS EXISTS IN THE web.xml FILE
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Repo repo = new Repo();

        UserService userService = new UserService(repo);
        JwtConfig jwtConfig = new JwtConfig();
        JwtService jwtService = new JwtService(jwtConfig);
        UserController userController = new UserController(userService, jwtConfig, jwtService);
        TransactionController transactionController = new TransactionController(repo);
        Dispatcher dispatcher = new Dispatcher(userController, transactionController);

        Servlet servlet = new Servlet(dispatcher);

        ServletContext context = sce.getServletContext();
        context.addServlet("Servlet", servlet).addMapping("*.data");




    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

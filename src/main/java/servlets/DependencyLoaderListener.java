package servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class DependencyLoaderListener implements ServletContextListener {

    //WHat if there are bunch of dependencies that we need to bring in at once
    //WHat if they are scattered around the application?
    //You abstract that static try block in your ConnectionFactory so that
    //it loads postgresql when the context is initialized

    //YOU NEED TO INFORM TOMCAT THAT THIS EXISTS IN THE web.xml FILE
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}

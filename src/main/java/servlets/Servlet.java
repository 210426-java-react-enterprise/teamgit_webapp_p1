package servlets;

import controllers.*;
import dispatchers.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

public class Servlet extends HttpServlet {

    private Connection conn;
    private UserController controller;
    private Dispatcher dispatcher;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
    }

    //Used to get connection once the applicationProperties are passed in
    private void connect(Map<String, String> applicationProperties){
        //this.conn = ConnectionFactory.getInstance().getConnection(applicationProperties);
    }


}

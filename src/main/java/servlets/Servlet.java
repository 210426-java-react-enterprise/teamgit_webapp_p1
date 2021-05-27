package servlets;

import controllers.*;
import dispatchers.*;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;


public class Servlet extends HttpServlet {

    private Dispatcher dispatcher;

    public Servlet(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatcher.dataDispatch(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatcher.dataDispatch(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatcher.dataDispatch(req, resp);
    }



}

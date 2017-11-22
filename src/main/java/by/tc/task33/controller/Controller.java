package by.tc.task33.controller;

import by.tc.task33.controller.command.Command;
import by.tc.task33.service.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.tc.task33.controller.command.ControlConst.ERROR_ATTRIBUTE;
import static by.tc.task33.controller.command.PageUrl.ERROR_PAGE_URL;

public class Controller extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html";
    private static final String COMMAND = "command";
    private static final String PARSING = "Parsing";

    private final CommandDirector director = new CommandDirector();
    private static final long serialVersionUID = 1L;

    public Controller() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType(CONTENT_TYPE);

        String parserType = request.getParameter(COMMAND).toUpperCase();
        Command command = director.getCommand(parserType);

        try {
            command.execute(request, response);
        } catch (ServiceException e) {
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE_URL);
            dispatcher.forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
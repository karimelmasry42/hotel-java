package apa.karim.apaproject.controllers;

import java.io.*;

import apa.karim.apaproject.models.Database;
import apa.karim.apaproject.models.Room;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "IndexController", value = "/index")
public class IndexController extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        // if the guest reached page by submitting "form to book room", redirect to book
        if (request.getParameter("roomID") != null) {
            // room of choice
            Room room = Database.getRoom(Long.parseLong(request.getParameter("roomID")));
            session.setAttribute("room", room);
            // if guest not logged int, log in first
            if (session.getAttribute("guest") == null)
                response.sendRedirect("/login");
                // else book directly
            else
                response.sendRedirect("/book");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/index.jsp").include(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }
}
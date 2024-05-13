package apa.karim.apaproject.controllers;


import apa.karim.apaproject.models.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "AccountController", value = "/account")
public class AccountController extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        // if user tried to view account while logged out, redirect to login
        if (session.getAttribute("guest") == null) {
            // set request attribute to redirect to account after login
            session.setAttribute("account", true);
            response.sendRedirect("login");
            return;
        }
        // if the user clicked cancel on a booking, cancel it
        if (request.getParameter("cancelBookingID") != null)
            Database.cancelBooking(Long.parseLong(request.getParameter("cancelBookingID")));

        request.getRequestDispatcher("/WEB-INF/account.jsp").include(request, response);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        processRequest(req, resp);
    }
}
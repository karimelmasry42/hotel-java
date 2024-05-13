package apa.karim.apaproject.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LogOutController", value = "/logout")
public class LogOutController extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();

        // remove session attributes
        session.invalidate();
        // remove login cookie
        for (Cookie cookie : request.getCookies()) {
            String cookieName = cookie.getName();
            if (cookieName.equals("guestEmail")) {
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
        // redirect to index
        response.sendRedirect("/index");
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
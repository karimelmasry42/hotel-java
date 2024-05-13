package apa.karim.apaproject.controllers;

import apa.karim.apaproject.models.Database;
import apa.karim.apaproject.models.Guest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "LoginController", value = "/login")
public class LoginController extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        // if user is logged in, redirect to index
        if (session.getAttribute("guest") != null) {
            request.getRequestDispatcher("/index").forward(request, response);
            return;
        }
        // if the user reached login controller by submitting login form, attempt login
        // else display login page only
        if (request.getParameter("login") != null) {
            // attempt login
            int login = Database.login(new Guest("", request.getParameter("guestEmail"), request.getParameter("password")));
            switch (login) {
                // guestEmail not registered
                case 0:
                    request.setAttribute("loginError", "<p>Email not registered. <a href='signUp.jsp'>Register</a></p>");
                    break;
                // guestEmail correct but wrong password
                case 1:
                    request.setAttribute("loginError", "<p>Incorrect password</p>");
                    break;
                // guestEmail and password correct
                case 2:
                    String guestEmail = request.getParameter("guestEmail");
                    // store Guest object in session
                    session.setAttribute("guest", Database.getGuest(guestEmail));
                    // if the guest checked "remember me", store guest guestEmail in cookie for 30 days
                    if (request.getParameter("rememberMe") != null) {
                        Cookie cookie = new Cookie("guestEmail", guestEmail);
                        cookie.setMaxAge(30 * 60 * 60 * 24);
                        response.addCookie(cookie);
                    }
                    // if the user reached login screen while attempting to book a room, forward to book
                    if (session.getAttribute("room") != null) {
                        request.getRequestDispatcher("/book").forward(request, response);
                    }
                    // else if the user reached login screen while attempting to view account, forward to account
                    else if (session.getAttribute("account") != null) {
                        session.removeAttribute("account");
                        request.getRequestDispatcher("/account").forward(request, response);
                    }
                    // else forward to index
                    else
                        request.getRequestDispatcher("/index").forward(request, response);
                    return;
            }
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
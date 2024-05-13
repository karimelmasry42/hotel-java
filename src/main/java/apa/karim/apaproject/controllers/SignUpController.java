package apa.karim.apaproject.controllers;

import apa.karim.apaproject.models.Database;
import apa.karim.apaproject.models.Guest;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "SignUpController", value = "/signup")
public class SignUpController extends HttpServlet {
    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        // if user is logged in, redirect to index
        if (session.getAttribute("guest") != null) {
            response.sendRedirect("/index");
            return;
        }
        // if the guest reached signup page by submitting signup form, attempt signup
        // else display signup page
        if (request.getParameter("signup") != null) {
            String guestEmail = request.getParameter("email");
            Guest g = new Guest(request.getParameter("name"), guestEmail, request.getParameter("password"));
            // if guest email not previously registered, register guest
            if (Database.storeGuest(g)) {
                // store guest object in session
                session.setAttribute("guest", g);
                // if the guest checked "remember me", store guest email in cookie for 30 days
                if (request.getParameter("rememberMe") != null) {
                    Cookie cookie = new Cookie("guestEmail", guestEmail);
                    cookie.setMaxAge(30 * 60 * 60 * 24);
                    response.addCookie(cookie);
                }
                // if the user reached signup screen while attempting to book a room, redirect to booking page
                if (session.getAttribute("room") != null) {
                    response.sendRedirect("book.jsp");
                }
                // else if the user reached signup screen while attempting to view account, redirect to account page
                else if (session.getAttribute("account") != null) {
                    session.removeAttribute("account");
                    response.sendRedirect("account.jsp");
                }
                // else redirect home
                else
                    response.sendRedirect("index.jsp");
                return;
                // else if email previously registered display message and offer login
            } else
                request.setAttribute("signUpError", "<span style='color: red;'>This email is taken.</span> <a href='logIn.jsp'>Log in</a>");
        }
        request.getRequestDispatcher("/WEB-INF/views/signup.jsp").include(request, response);
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
package apa.karim.apaproject.controllers;


import apa.karim.apaproject.models.Booking;
import apa.karim.apaproject.models.Database;
import apa.karim.apaproject.models.Guest;
import apa.karim.apaproject.models.Room;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Date;
import java.util.Objects;

@WebServlet(name = "BookController", value = "/book")
public class BookController extends HttpServlet {

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        HttpSession session = request.getSession();
        // Objects to be displayed or used
        Guest guest = (Guest) session.getAttribute("guest");
        Room room = (Room) session.getAttribute("room");
        // if user reached page while logged out, login then come back
        if (guest == null) {
            response.sendRedirect("/login");
            return;
        }

        // if the user reached page by submitting form, attempt to store booking
        if (request.getParameter("confirmBooking") != null) {
            // if check-in and check-out are on the same date, warn guest
            if (Objects.equals(request.getParameter("checkIn"), request.getParameter("checkOut")))
                request.setAttribute("datesError", true);
                // else store booking
            else {
                Database.storeBooking(new Booking(guest.getGuestEmail(), room.getRoomID(),
                        Date.valueOf(request.getParameter("checkIn")), Date.valueOf(request.getParameter("checkOut"))));
                session.removeAttribute("room");
                response.sendRedirect("/account");
                return;
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/book.jsp").include(request, response);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    }
}
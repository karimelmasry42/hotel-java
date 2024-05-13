package apa.karim.apaproject.models;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class RememberGuest {
    public static void remember(HttpServletRequest request, HttpSession session) {
        for (Cookie cookie : request.getCookies()) {
            String cookieName = cookie.getName();
            if (cookieName.equals("guestEmail")) {
                session.setAttribute("guest", Database.getGuest(cookie.getValue()));
                break;
            }
        }
    }
}
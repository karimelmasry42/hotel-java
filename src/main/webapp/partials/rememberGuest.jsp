<jsp:useBean id="db" class="apa.karim.apaproject.Database"/>
<%
    for (Cookie cookie : request.getCookies()) {
        String cookieName = cookie.getName();
        if (cookieName.equals("guest_email")) {
            session.setAttribute("guest", db.getGuest(cookie.getValue()));
            break;
        }
    }
%>
<%
    // remove session attributes
    session.invalidate();
    // remove login cookie
    for (Cookie cookie : request.getCookies()) {
        String cookieName = cookie.getName();
        if (cookieName.equals("guest_email")) {
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            break;
        }
    }
    // redirect to index
    response.sendRedirect("index.jsp");
%>
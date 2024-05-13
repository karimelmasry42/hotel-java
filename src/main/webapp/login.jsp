<%@ page import="apa.karim.apaproject.Guest" %>
<jsp:useBean id="db" class="apa.karim.apaproject.Database" scope="application"/>
<jsp:include page="partials/head.jsp"/>
<jsp:include page="partials/navbar.jsp"/>
<%
    // if user is logged in, redirect to index
    if (session.getAttribute("guest") != null) {
        response.sendRedirect("index.jsp");
        return;
    }
    // if the user was trying to book, send message
    if (session.getAttribute("room") != null) {
%>
<p>Please log in before booking</p>
<%
    }
    // if the user was trying to view account, send message
    if (session.getAttribute("account") != null) {
%>
<p>Please log in to view account</p>
<%
    }
    // if the user reached login controller by submitting login form, attempt login
    // else display login page only
    if (request.getParameter("login") != null) {
        // attempt login
        int login = db.login(new Guest("", request.getParameter("email"), request.getParameter("password")));
        switch (login) {
            // email not registered
            case 0:
                out.print("<p>Email not registered. <a href='signup.jsp'>Register</a></p>");
                break;
            // email correct but wrong password
            case 1:
                out.print("<p>Incorrect password</p>");
                break;
            // email and password correct
            case 2:
                String guestEmail = request.getParameter("email");
                // store Guest object in session
                session.setAttribute("guest", db.getGuest(guestEmail));
                // if the guest checked "remember me", store guest email in cookie for 30 days
                if (request.getParameter("remember_me") != null) {
                    Cookie cookie = new Cookie("guest_email", guestEmail);
                    cookie.setMaxAge(30 * 60 * 60 * 24);
                    response.addCookie(cookie);
                }
                // if the user reached login screen while attempting to book a room, redirect to booking page
                if (session.getAttribute("room") != null) {
                    response.sendRedirect("book.jsp");
                }
                // else if the user reached login screen while attempting to view account, redirect to account page
                else if (session.getAttribute("account") != null) {
                    session.removeAttribute("account");
                    response.sendRedirect("account.jsp");
                }
                // else redirect home
                else
                    response.sendRedirect("index.jsp");
                return;
        }
    }
%>
<main>
    <form action="login.jsp" method="post" class="login">
        <div>
            <label for="email">Email: </label> <input type="email" name="email" id="email">
        </div>
        <div>
            <label for="password">Password: </label> <input type="password" name="password" id="password">
        </div>
        <button type="submit" name="login">Log in</button>
        <div>
            <input type="checkbox" id="remember_me" name="remember_me">
            <label for="remember_me"> Keep me logged in for 30 days</label><br>
        </div>
    </form>
    <form action="signup.jsp" method="post">
        <button type="submit">Create New Account</button>
    </form>
</main>
<jsp:include page="partials/footer.jsp"/>
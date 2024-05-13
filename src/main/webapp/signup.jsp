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
<p>Please sign up before booking</p>
<%
    }
    // if the user was trying to view account, send message
    if (session.getAttribute("account") != null) {
%>
<p>Please sign up to view account</p>
<%
    }

    // if the guest reached signup page by submitting signup form, attempt signup
    // else display signup page
    if (request.getParameter("signup") != null) {
        String guestEmail = request.getParameter("email");
        Guest g = new Guest(request.getParameter("name"), guestEmail, request.getParameter("password"));
        // if guest email not previously registered, register guest
        if (db.storeGuest(g)) {
            // store guest object in session
            session.setAttribute("guest", g);
            // if the guest checked "remember me", store guest email in cookie for 30 days
            if (request.getParameter("remember_me") != null) {
                Cookie cookie = new Cookie("guest_email", guestEmail);
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
            // else display message and offer login
        } else
            out.print("<span style='color: red;'>This email is taken.</span> <a href='login.jsp'>Log in</a>");
    }
%>
<main>
    <form action="signup.jsp" method="post" class="signup">
        <div>
            <label for="name">Name: </label><input type="text" name="name" id="name">
        </div>
        <div>
            <label for="email">Email: </label><input type="email" name="email" id="email">
        </div>
        <div>
            <label for="password">Password: </label><input type="password" name="password" id="password">
        </div>
        <div>
            <button type="submit" name="signup">Sign up</button>
        </div>
        <input type="checkbox" id="remember_me" name="remember_me">
        <label for="remember_me"> Keep me logged in for 30 days</label><br>

    </form>
    <form action="login.jsp" method="get">
        <button type="submit">Log in</button>
    </form>
</main>

<jsp:include page="partials/footer.jsp"/>
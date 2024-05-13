<jsp:include page="/WEB-INF/partials/head.jsp">
    <jsp:param name="title" value="Log in"/>
</jsp:include>
<jsp:include page="/WEB-INF/partials/navbar.jsp"/>
<%
    // if the user was trying to book, send message
    if (session.getAttribute("room") != null)
        out.print("<p>Please log in before booking</p>");
        // if the user was trying to view account, send message
    else if (session.getAttribute("account") != null)
        out.print("<p>Please log in to view account</p>");

    // login error message
    if (request.getAttribute("loginError") != null)
        out.print(request.getAttribute("loginError"));
%>
<main>
    <form action="login" method="post" class="login">
        <div>
            <label for="guestEmail">Email: </label> <input type="email" name="guestEmail" id="guestEmail">
        </div>
        <div>
            <label for="password">Password: </label> <input type="password" name="password" id="password">
        </div>
        <button type="submit" name="login">Log in</button>
        <div>
            <input type="checkbox" id="rememberMe" name="rememberMe">
            <label for="rememberMe"> Stay logged in for 30 days</label><br>
        </div>
    </form>
    <form action="signup" method="post">
        <button type="submit">Create New Account</button>
    </form>
</main>
<jsp:include page="/WEB-INF/partials/footer.jsp"/>
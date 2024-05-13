<jsp:include page="partials/head.jsp">
    <jsp:param name="title" value="Sign up"/>
</jsp:include>
<jsp:include page="partials/navbar.jsp"/>
<%
    // if the user was trying to book, send message
    if (session.getAttribute("room") != null)
        out.print("<p>Please sign up before booking</p>");
    // else if the user was trying to view account, send message
    else if (session.getAttribute("account") != null)
        out.print("<p>Please sign up to view account</p>");

    // if email previously registered display message and offer login
    if(request.getAttribute("signUpError") != null)
        out.print(request.getAttribute("signUpError"));
%>
<main>
    <form action="signup" method="post" class="signup">
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
        <input type="checkbox" id="rememberMe" name="rememberMe">
        <label for="rememberMe"> Keep me logged in for 30 days</label><br>

    </form>
    <form action="login" method="get">
        <button type="submit">Log in</button>
    </form>
</main>

<jsp:include page="partials/footer.jsp"/>
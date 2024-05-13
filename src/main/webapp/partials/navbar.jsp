<nav>
    <a style="font-weight: bold" href="index.jsp#">HOTELS</a>
    <a href="index.jsp#rooms">Book</a>
    <a href="account.jsp">Account</a>
    <%
        if (session.getAttribute("guest") != null)
            out.print("<a href='logout.jsp'>Logout</a>");
        else
            out.print("<a href='login.jsp'>Login</a>");
    %>
</nav>
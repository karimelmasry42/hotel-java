<nav>
    <a style="font-weight: bold" href="/index#">HOTELS</a>
    <a href="/index#rooms">Book</a>
    <a href="/account">Account</a>
    <%
        if (session.getAttribute("guest") != null)
            out.print("<a href='/logout'>Log out</a>");
        else
            out.print("<a href='/login'>Log in</a>");
    %>
</nav>
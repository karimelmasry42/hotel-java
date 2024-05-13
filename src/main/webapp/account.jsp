<%@ page import="apa.karim.apaproject.Guest" %>
<%@ page import="apa.karim.apaproject.Booking" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="apa.karim.apaproject.Room" %>
<%@ page import="java.util.Objects" %>
<jsp:useBean id="db" class="apa.karim.apaproject.Database" scope="application"/>
<jsp:include page="partials/rememberGuest.jsp"/>
<%
    // if user tried to view account while logged out, redirect to login
    if (session.getAttribute("guest") == null) {
        // set request attribute to redirect to account after login
        session.setAttribute("account", true);
        response.sendRedirect("login.jsp");
        return;
    }
    // if the user clicked cancel on a booking, cancel it
    if (request.getParameter("cancel_booking_id") != null)
        db.cancelBooking(Long.parseLong(request.getParameter("cancel_booking_id")));
    // get objects to be displayed
    Guest guest = (Guest) session.getAttribute("guest");
    ArrayList<Booking> bookings = db.getBookings(guest);
%>
<jsp:include page="partials/head.jsp"/>
<jsp:include page="partials/navbar.jsp"/>
<main>
    <%--    display account info    --%>
    <div class="account-info">
        <h1>Account Information</h1>
        <p><b>Name: </b><%=guest.getName()%>
        </p>
        <p><b>Email: </b> <%=guest.getEmail()%> </b></p>
    </div>
    <div class="bookings">
        <h1>Bookings</h1>
        <%
            // display booking info for each booking
            for (Booking booking : bookings) {
                Room room = db.getRoom(booking.getRoom_id());
        %>
        <div class="booking">
            <img src="./assets/room-<%=booking.getRoom_id()%>.jpg" alt="Room"/>
            <h2><%=room.getName()%>
            </h2>
            <p><b>Check-in: </b> <%=booking.getCheck_in()%>
            </p>
            <p><b>Check-out: </b> <%=booking.getCheck_out()%>
            </p>
            <p><b>Price: </b> EGP<%=booking.getPrice()%>
            </p>
            <p>
                <b>Status: </b><%=booking.getStatus()%>
            </p>
            <%
                // if booking is not cancelled, display cancel button
                if (!Objects.equals(booking.getStatus(), "Cancelled")) {
            %>
            <form action="account.jsp" method="post">
                <button type="submit" name="cancel_booking_id" value="<%=booking.getBooking_id()%>"
                        style="background: red"> Cancel
                </button>
            </form>
            <%}%>
        </div>
        <% } %>
    </div>
</main>
<jsp:include page="partials/footer.jsp"/>
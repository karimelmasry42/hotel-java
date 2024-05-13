<%@ page import="apa.karim.apaproject.models.Guest" %>
<%@ page import="apa.karim.apaproject.models.Booking" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="apa.karim.apaproject.models.Room" %>
<%@ page import="java.util.Objects" %>
<%@ page import="apa.karim.apaproject.models.Database" %>
<%
    // get objects to be displayed
    Guest guest = (Guest) session.getAttribute("guest");
    ArrayList<Booking> bookings = Database.getBookings(guest);
%>
<jsp:include page="partials/head.jsp">
    <jsp:param name="title" value="Account"/>
</jsp:include>
<jsp:include page="partials/navbar.jsp"/>
<main>
    <%-- display account info --%>
    <div class="account-info">
        <h1>Account Information</h1>
        <p><b>Name: </b><%=guest.getName()%>
        </p>
        <p><b>Email: </b> <%=guest.getGuestEmail()%> </b></p>
    </div>
    <%-- display bookings --%>
    <div class="bookings">
        <h1>Bookings</h1>
        <%
            // display booking info for each booking
            for (Booking booking : bookings) {
                Room room = Database.getRoom(booking.getRoomID());
                assert room != null;%>
        <div class="booking">
            <img src="../assets/room-<%=booking.getRoomID()%>.jpg" alt="Room"/>
            <h2><%=room.getName()%>
            </h2>
            <p><b>Check-in: </b> <%=booking.getCheckIn()%>
            </p>
            <p><b>Check-out: </b> <%=booking.getCheckOut()%>
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
            <form action="account" method="post">
                <button type="submit" name="cancelBookingID" value="<%=booking.getBookingID()%>"
                        style="background: red"> Cancel
                </button>
            </form>
            <%}%>
        </div>
        <% } %>
    </div>
</main>
<jsp:include page="partials/footer.jsp"/>
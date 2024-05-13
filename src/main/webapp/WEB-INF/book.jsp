<%@ page import="apa.karim.apaproject.models.Guest" %>
<%@ page import="apa.karim.apaproject.models.Room" %>
<%@ page import="apa.karim.apaproject.models.Booking" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.text.Format" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="apa.karim.apaproject.models.Database" %>
<jsp:include page="partials/head.jsp">
    <jsp:param name="title" value="Book"/>
</jsp:include>
<jsp:include page="partials/navbar.jsp"/>
<%
    // objects to be displayed or used
    Guest guest = (Guest) session.getAttribute("guest");
    Room room = (Room) session.getAttribute("room");

    // today's date String: for min check-in and check-out date
    Format formatter = new SimpleDateFormat("yyyy-MM-dd");
    String date = formatter.format(new java.util.Date());

%>
<main>
    <div class="room">
        <img src="../assets/room-<%= room.getRoomID()%>.jpg" alt="Room"/>
        <h2><%=room.getName()%>
        </h2>
        <p><%=room.getDescription()%>
        </p>
        <div style="display:inline-block;">
            <b>Price: </b> EGP<%=room.getPrice()%>/night
            <b>Capacity: </b> <%=room.getCapacity()%> person(s)
        </div>
    </div>
    <%
        // if check-in and check-out are on the same date, warn guest
        if (request.getAttribute("dateError") != null)
            out.print("<p style='color: red;'>Check-in and check-out dates cannot be the same</p><br>");
    %>
    <form action="book" method="post" class="book">
        <label for="checkIn">Check-in: </label>
        <input type="date" name="checkIn" id="checkIn"
               min="<%=date%>" required>
        <label for="checkOut">Check-out: </label>
        <input type="date" name="checkOut" id="checkOut"
               min="<%=date%>" required>
        <button type="submit" name="confirmBooking">Confirm Booking</button>
    </form>
</main>
<jsp:include page="partials/footer.jsp"/>
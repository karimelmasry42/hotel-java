<%@ page import="apa.karim.apaproject.Guest" %>
<%@ page import="apa.karim.apaproject.Room" %>
<%@ page import="apa.karim.apaproject.Booking" %>
<%@ page import="java.util.Objects" %>
<%@ page import="java.sql.Date" %>
<%@ page import="java.text.Format" %>
<%@ page import="java.text.SimpleDateFormat" %>
<jsp:useBean id="db" class="apa.karim.apaproject.Database" scope="application"/>
<jsp:include page="partials/head.jsp"/>
<jsp:include page="partials/navbar.jsp"/>
<%
    // Objects to be displayed or used
    Guest guest = (Guest) session.getAttribute("guest");
    Room room = (Room) session.getAttribute("room");

    // if user reached page while logged out, login then come back
    if (guest == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // today's date String: min check-in and check-out date
    Format formatter = new SimpleDateFormat("yyyy-MM-dd");
    String date = formatter.format(new java.util.Date());

    // if the user reached page by submitting form, attempt to store booking
    if (request.getParameter("confirm_booking") != null) {
        // if check-in and check-out are on the same date, warn guest
        if (Objects.equals(request.getParameter("check_in"), request.getParameter("check_out")))
            out.print("<p style='color: red;'>Check-in and check-out dates cannot be the same</p><br>");
            // else store booking
        else {
            db.storeBooking(new Booking(guest.getEmail(), room.getRoom_id(),
                    Date.valueOf(request.getParameter("check_in")), Date.valueOf(request.getParameter("check_out"))));
            session.removeAttribute("room");
            request.getRequestDispatcher("account.jsp").forward(request, response);
            return;
        }
    }
%>
<main>
    <div class="room">
        <img src="./assets/room-<%= room.getRoom_id()%>.jpg" alt="Room"/>
        <h2><%=room.getName()%>
        </h2>
        <p><%=room.getDescription()%>
        </p>
        <div style="display:inline-block;">
            <b>Price: </b> EGP<%=room.getPrice()%>/night
            <b>Capacity: </b> <%=room.getCapacity()%> person(s)
        </div>
    </div>
    <form action="book.jsp" method="post" class="book">
        <label for="check_in">Check-in: </label>
        <input type="date" name="check_in" id="check_in"
               min="<%=date%>" required>
        <label for="check_out">Check-out: </label>
        <input type="date" name="check_out" id="check_out"
               min="<%=date%>" required>
        <button type="submit" name="confirm_booking">Confirm Booking</button>
    </form>
</main>
<jsp:include page="partials/footer.jsp"/>
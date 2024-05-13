<%@ page import="apa.karim.apaproject.Room" %>
<%@ page import="java.util.ArrayList" %>
<jsp:useBean id="db" class="apa.karim.apaproject.Database" scope="application"/>
<%
    // if the guest reached page by submitting "form to book room", forward to book
    if (request.getParameter("room_id") != null) {
        // room of choice
        Room room = db.getRoom(Long.parseLong(request.getParameter("room_id")));
        session.setAttribute("room", room);
        // if guest not logged int, log in first
        if (session.getAttribute("guest") == null)
            response.sendRedirect("login.jsp");
            // else book directly
        else
            response.sendRedirect("book.jsp");
        return;
    }
%>
<jsp:include page="partials/head.jsp"/>
<jsp:include page="partials/navbar.jsp"/>
<main>
    <div class="about-us">
        <video src="assets/egypt.mp4" controls autoplay loop></video>
        <h1>About Us</h1>
        <p style="padding: 16px;">
            We are a hotel located in the heart of Cairo. We are known for exceptional service and luxurious rooms.
            We have been in business for over 20 years and have served thousands of customers.
            Our hotels are perfect for both business and leisure travelers. We offer a
            variety of amenities including free Wi-Fi, room service, and a fitness center.
            Our staff is friendly and professional and will go above and beyond to make sure your stay is perfect.
            We look forward to welcoming you to our hotel!
        </p>
    </div>
    <div class="rooms" id="rooms">
        <h1>Rooms</h1>
        <% ArrayList<Room> rooms = db.getRooms();
            for (Room room : rooms) {%>
        <div class="room">
            <img src="./assets/room-<%= room.getRoom_id() %>.jpg" alt="Room"/>
            <h2><%= room.getName() %>
            </h2>
            <p><%= room.getDescription() %>
            </p>
            <div style="display:inline-block;">
                <b>Price: </b> EGP <%= room.getPrice() %>/night
                <b>Capacity: </b> <%= room.getCapacity() %> person(s)
            </div>
            <%--  form to book room  --%>
            <form action="index.jsp" method="post">
                <button type="submit" name="room_id" value="<%= room.getRoom_id() %>">Book</button>
            </form>
        </div>
        <% } %>
    </div>
</main>
<jsp:include page="partials/footer.jsp"/>
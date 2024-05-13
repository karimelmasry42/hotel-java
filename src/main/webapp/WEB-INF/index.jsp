<%@ page import="apa.karim.apaproject.models.Room" %>
<%@ page import="apa.karim.apaproject.models.Database" %>
<jsp:include page="partials/head.jsp">
    <jsp:param name="title" value="Home"/>
</jsp:include>
<jsp:include page="partials/navbar.jsp"/>
<main>
    <div class="about-us">
        <video src="../assets/egypt.mp4" controls autoplay loop></video>
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
        <%
            for (Room room : Database.getRooms()) {
        %>
        <div class="room">
            <img src="../assets/room-<%= room.getRoomID() %>.jpg" alt="Room"/>
            <h2><%= room.getName() %>
            </h2>
            <p><%= room.getDescription() %>
            </p>
            <div style="display:inline-block;">
                <b>Price: </b> EGP <%= room.getPrice() %>/night
                <b>Capacity: </b> <%= room.getCapacity() %> person(s)
            </div>
            <%--  form to book room  --%>
            <form action="index" method="post">
                <button type="submit" name="roomID" value="<%= room.getRoomID() %>">Book</button>
            </form>
        </div>
        <% } %>
    </div>
</main>
<jsp:include page="partials/footer.jsp"/>
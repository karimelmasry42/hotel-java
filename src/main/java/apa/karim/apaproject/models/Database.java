package apa.karim.apaproject.models;

import java.sql.*;
import java.time.Period;
import java.util.ArrayList;

public class Database {
    private static final String connStr = "jdbc:mysql://root@127.0.0.1/web_project_db?statusColor=686B6F&env=local&name=web-project-db-connection&tLSMode=0&usePrivateKey=false&safeModeLevel=0&advancedSafeModeLevel=0&driverVersion=0&lazyload=true&useSSL=false";

    // ------------ INIT -------------
    private static Connection initConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connStr);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    // ----------- END INIT -----------

    // ----------- GET LISTS ---------------
    // returns list of all rooms
    public static ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        try (
                Connection conn = Database.initConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from rooms;")
        ) {
            while (rs.next()) {
                int id = rs.getInt("roomID");
                String name = rs.getString("name");
                String description = rs.getString("description");
                int price = rs.getInt("price");
                int capacity = rs.getInt("capacity");
                Room r = new Room(id, name, description, price, capacity);
                rooms.add(r);
            }
        } catch (SQLException e) {
            System.out.println("Could not get rooms: " + e.getMessage());
        }
        return rooms;
    }

    // returns list of all bookings made by guest g
    public static ArrayList<Booking> getBookings(Guest g) {
        ArrayList<Booking> bookings = new ArrayList<>();
        try (
                Connection conn = Database.initConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "select * from bookings where guestEmail = ?");
        ) {
            ps.setString(1, g.getGuestEmail());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int bookingID = rs.getInt("bookingID");
                    int roomID = rs.getInt("roomID");
                    int price = rs.getInt("price");
                    String status = rs.getString("status");
                    Date checkIn = rs.getDate("checkIn");
                    Date checkOut = rs.getDate("checkOut");
                    Booking b = new Booking(bookingID, g.getGuestEmail(), roomID, status, price, checkIn, checkOut);
                    bookings.add(b);
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not get bookings for guest " + g.getGuestEmail() + ": " + e.getMessage());
        }
        return bookings;
    }
    // --------- END GET LISTS ----------

    // ---------- GET WITH PK ---------
    // returns Room object; null if it can't get object
    public static Room getRoom(long roomID) {
        try (
                Connection conn = Database.initConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "select roomID, name, description, price, capacity " +
                                "from rooms where roomID= ?");
        ) {
            ps.setLong(1, roomID);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return null;
                Room r = new Room();
                r.setRoomID(rs.getLong(1));
                r.setName(rs.getString(2));
                r.setDescription(rs.getString(3));
                r.setPrice(rs.getInt(4));
                r.setCapacity(rs.getInt(5));
                return r;
            }
        } catch (SQLException e) {
            System.out.println("Could not get room " + roomID + ": " + e.getMessage());
        }
        return null;
    }

    // returns Guest object; null if it can't get object
    public static Guest getGuest(String guestEmail) {
        try (
                Connection conn = Database.initConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "select name, password from guests where guestEmail= ?");
        ) {
            ps.setString(1, guestEmail);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return null;
                Guest g = new Guest();
                g.setName(rs.getString(1));
                g.setGuestEmail(guestEmail);
                g.setPassword(rs.getString(2));
                return g;
            }
        } catch (SQLException e) {
            System.out.println("Could not get guest " + guestEmail + ": " + e.getMessage());
        }
        return null;
    }

    // returns Booking object; null if it can't get object
    public static Booking getBooking(long bookingID) {
        try (
                Connection conn = Database.initConnection();
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(
                        "select * from bookings where bookingID= " + bookingID)
        ) {
            if (!rs.next())
                return null;
            Booking b = new Booking();
            b.setBookingID(bookingID);
            b.setGuestEmail(rs.getString("guestEmail"));
            b.setRoomID(rs.getLong("roomID"));
            b.setPrice(rs.getInt("price"));
            b.setStatus(rs.getString("status"));
            b.setCheckIn(rs.getDate("checkIn"));
            b.setCheckOut(rs.getDate("checkOut"));
            return b;
        } catch (SQLException e) {
            System.out.println("Could not get booking: " + bookingID + ": " + e.getMessage());
        }
        return null;
    }
    // ----------- END GET WITH PK ----------

    // -------------- UPDATE -----------
    // returns true if update successful; false otherwise
    public static boolean updateGuest(Guest g) {
        try (
                Connection conn = Database.initConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "update guests set name = ?, password = ?  where guestEmail = ?;")
        ) {
            ps.setString(1, g.getName());
            ps.setString(2, g.getPassword());
            ps.setString(3, g.getGuestEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Could not update guest " + g.getGuestEmail() + ": " + e.getMessage());
        }
        return false;
    }

    // returns true if update successful; false otherwise
    public static boolean cancelBooking(long bookingID) {
        try (
                Connection conn = Database.initConnection();
                Statement s = conn.createStatement()
        ) {
            return s.executeUpdate("update bookings set status = 'Cancelled'where bookingID = " + bookingID) > 0;
        } catch (SQLException e) {
            System.out.println("Could not cancel booking " + bookingID + ": " + e.getMessage());
        }
        return false;
    }
    // ------------ END UPDATE -----------

    // -------------- STORE -------------
    // returns true if successfully stored, false otherwise
    public static boolean storeGuest(Guest g) {
        try (
                Connection conn = Database.initConnection();
                PreparedStatement ps = conn.prepareStatement(
                        "insert into guests (name, guestEmail, password) values (?, ?, ?)")
        ) {
            ps.setString(1, g.getName());
            ps.setString(2, g.getGuestEmail());
            ps.setString(3, g.getPassword());
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            return false;
        } catch (SQLException e) {
            System.out.println("Could not store guest " + g.getGuestEmail() + ": " + e.getMessage());
        }
        return false;
    }

    // returns bookingID if successful, -1 if failed
    public static boolean storeBooking(Booking b) {
        try (
                Connection conn = Database.initConnection();
                PreparedStatement s = conn.prepareStatement("SELECT price FROM rooms WHERE roomID = ?");
                PreparedStatement ps = conn.prepareStatement(
                        "insert into bookings (guestEmail, roomID, price, status, checkIn, checkOut) " +
                                "values (?, ?, ?, ?, ?, ?);")
        ) {
            int price = 0;
            s.setLong(1, b.getRoomID());
            try (ResultSet rs = s.executeQuery()) {
                rs.next();
                price = rs.getInt(1);
            } catch (Exception e) {
                System.out.println("Could not get price for room " + b.getRoomID() + ": " + e.getMessage());
            }
            Period period = Period.between(b.getCheckOut().toLocalDate(), b.getCheckIn().toLocalDate());
            int days = Math.abs(period.getDays());

            price *= days;
            ps.setString(1, b.getGuestEmail());
            ps.setLong(2, b.getRoomID());
            ps.setInt(3, price);
            ps.setString(4, "Paid");
            ps.setDate(5, b.getCheckIn());
            ps.setDate(6, b.getCheckOut());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Could not store booking for guest " + b.getGuestEmail() + ": " + e.getMessage());
        }
        return false;
    }
    // ------------ END STORE -----------

    // ------ LOGIN ------
    // returns Guest object; null if it can't get object
    public static int login(Guest g) {
        try (
                Connection conn = Database.initConnection();
                PreparedStatement ps = conn.prepareStatement("select password from guests where guestEmail = ?")
        ) {
            ps.setString(1, g.getGuestEmail());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return 0;
                if (!rs.getString(1).equals(g.getPassword()))
                    return 1;
                return 2;
            }
        } catch (SQLException e) {
            System.out.println("Could not get guest " + g.getGuestEmail() + ": " + e.getMessage());
        }
        return 0;
    }
    // ------ END LOGIN ------
}

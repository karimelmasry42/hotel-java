package apa.karim.apaproject;

import java.sql.*;
import java.time.Period;
import java.util.ArrayList;

public class Database {
    private final String conStr = "jdbc:mysql://root@127.0.0.1/web_project_db?statusColor=686B6F&env=local&name=web-project-db-connection&tLSMode=0&usePrivateKey=false&safeModeLevel=0&advancedSafeModeLevel=0&driverVersion=0&lazyload=true&useSSL=false";

    // ------------ INIT -------------
    public Database() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // ----------- END INIT -----------

    // ----------- GET LISTS ---------------
    // returns list of all rooms
    public ArrayList<Room> getRooms() {
        ArrayList<Room> rooms = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(conStr);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select * from rooms;")
        ) {
            while (rs.next()) {
                int id = rs.getInt("room_id");
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
    public ArrayList<Booking> getBookings(Guest g) {
        ArrayList<Booking> bookings = new ArrayList<>();
        try (
                Connection conn = DriverManager.getConnection(conStr);
                PreparedStatement ps = conn.prepareStatement(
                        "select * from bookings where guest_email= ?");
        ) {
            ps.setString(1, g.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int booking_id = rs.getInt("booking_id");
                    int room_id = rs.getInt("room_id");
                    int price = rs.getInt("price");
                    String status = rs.getString("status");
                    Date check_in = rs.getDate("check_in");
                    Date check_out = rs.getDate("check_out");
                    Booking b = new Booking(booking_id, g.getEmail(), room_id, status, price, check_in, check_out);
                    bookings.add(b);
                }
            }
        } catch (SQLException e) {
            System.out.println("Could not get bookings for guest " + g.getEmail() + ": " + e.getMessage());
        }
        return bookings;
    }
    // --------- END GET LISTS ----------

    // ---------- GET WITH PK ---------
    // returns Room object; null if it can't get object
    public Room getRoom(long room_id) {
        try (
                Connection conn = DriverManager.getConnection(conStr);
                PreparedStatement ps = conn.prepareStatement(
                        "select room_id, name, description, price, capacity " +
                                "from rooms where room_id= ?");
        ) {
            ps.setLong(1, room_id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return null;
                Room r = new Room();
                r.setRoom_id(rs.getLong(1));
                r.setName(rs.getString(2));
                r.setDescription(rs.getString(3));
                r.setPrice(rs.getInt(4));
                r.setCapacity(rs.getInt(5));
                return r;
            }
        } catch (SQLException e) {
            System.out.println("Could not get room " + room_id + ": " + e.getMessage());
        }
        return null;
    }

    // returns Guest object; null if it can't get object
    public Guest getGuest(String guest_email) {
        try (
                Connection conn = DriverManager.getConnection(conStr);
                PreparedStatement ps = conn.prepareStatement(
                        "select name, password from guests where email= ?");
        ) {
            ps.setString(1, guest_email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return null;
                Guest g = new Guest();
                g.setName(rs.getString(1));
                g.setEmail(guest_email);
                g.setPassword(rs.getString(2));
                return g;
            }
        } catch (SQLException e) {
            System.out.println("Could not get guest " + guest_email + ": " + e.getMessage());
        }
        return null;
    }

    // returns Booking object; null if it can't get object
    public Booking getBooking(long booking_id) {
        try (
                Connection conn = DriverManager.getConnection(conStr);
                Statement s = conn.createStatement();
                ResultSet rs = s.executeQuery(
                        "select * from bookings where booking_id= " + booking_id)
        ) {
            if (!rs.next())
                return null;
            Booking b = new Booking();
            b.setBooking_id(booking_id);
            b.setGuest_email(rs.getString("guest_email"));
            b.setRoom_id(rs.getLong("room_id"));
            b.setPrice(rs.getInt("price"));
            b.setStatus(rs.getString("status"));
            b.setCheck_in(rs.getDate("check_in"));
            b.setCheck_out(rs.getDate("check_out"));
            return b;
        } catch (SQLException e) {
            System.out.println("Could not get booking: " + booking_id + ": " + e.getMessage());
        }
        return null;
    }
    // ----------- END GET WITH PK ----------

    // -------------- UPDATE -----------
    // returns true if update successful; false otherwise
    public boolean updateGuest(Guest g) {
        try (
                Connection conn = DriverManager.getConnection(conStr);
                PreparedStatement ps = conn.prepareStatement(
                        "update guests set name = ?, password = ?  where email = ?;")
        ) {
            ps.setString(1, g.getName());
            ps.setString(2, g.getPassword());
            ps.setString(3, g.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Could not update guest " + g.getEmail() + ": " + e.getMessage());
        }
        return false;
    }

    // returns true if update successful; false otherwise
    public boolean cancelBooking(long booking_id) {
        try (
                Connection conn = DriverManager.getConnection(conStr);
                Statement s = conn.createStatement()
        ) {
            return s.executeUpdate("update bookings set status = 'Cancelled'where booking_id = " + booking_id) > 0;
        } catch (SQLException e) {
            System.out.println("Could not cancel booking " + booking_id + ": " + e.getMessage());
        }
        return false;
    }
    // ------------ END UPDATE -----------

    // -------------- STORE -------------
    // returns true if successfully stored, false otherwise
    public boolean storeGuest(Guest g) {
        try (
                Connection conn = DriverManager.getConnection(conStr);
                PreparedStatement ps = conn.prepareStatement(
                        "insert into guests (name, email, password) values (?, ?, ?)")
        ) {
            ps.setString(1, g.getName());
            ps.setString(2, g.getEmail());
            ps.setString(3, g.getPassword());
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Could not store guest " + g.getEmail() + ": " + e.getMessage());
        }
        return false;
    }

    // returns booking_id if successful, -1 if failed
    public boolean storeBooking(Booking b) {
        try (
                Connection conn = DriverManager.getConnection(conStr);
                PreparedStatement s = conn.prepareStatement("SELECT price FROM rooms WHERE room_id = ?");
                PreparedStatement ps = conn.prepareStatement(
                        "insert into bookings (guest_email, room_id, price, status, check_in, check_out) " +
                                "values (?, ?, ?, ?, ?, ?);")
        ) {
            int price = 0;
            s.setLong(1, b.getRoom_id());
            try (ResultSet rs = s.executeQuery()) {
                rs.next();
                price = rs.getInt(1);
            }catch(Exception e) {
                System.out.println("Could not get price for room " + b.getRoom_id() + ": " + e.getMessage());
            }
            Period period = Period.between(b.getCheck_out().toLocalDate(), b.getCheck_in().toLocalDate());
            int days = Math.abs(period.getDays());

            price *= days;
            ps.setString(1, b.getGuest_email());
            ps.setLong(2, b.getRoom_id());
            ps.setInt(3, price);
            ps.setString(4, "Paid");
            ps.setDate(5, b.getCheck_in());
            ps.setDate(6, b.getCheck_out());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Could not store booking for guest " + b.getGuest_email() + ": " + e.getMessage());
        }
        return false;
    }
    // ------------ END STORE -----------

    // ------ LOGIN ------
    // returns Guest object; null if it can't get object
    public int login(Guest g) {
        try (
                Connection conn = DriverManager.getConnection(conStr);
                PreparedStatement ps = conn.prepareStatement("select password from guests where email = ?")
        ) {
            ps.setString(1, g.getEmail());
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next())
                    return 0;
                if (!rs.getString(1).equals(g.getPassword()))
                    return 1;
                return 2;
            }
        } catch (SQLException e) {
            System.out.println("Could not get guest " + g.getEmail() + ": " + e.getMessage());
        }
        return 0;
    }
    // ------ END LOGIN ------
}

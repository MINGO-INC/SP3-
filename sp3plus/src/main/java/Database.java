import javax.xml.namespace.QName;
import java.sql.*;
import java.util.Scanner;

public class Database {

    // database URL
    static final String DB_URL = "jdbc:mysql://localhost/my_streaming1";

    //  Database credentials
    static final String USER = "root";
    static final String PASS = "61468723Aa!";


    public void readData() {

        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            //STEP 1: Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            //STEP 2: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            //STEP 3: Execute a query
            System.out.println("Creating statement...");
            String sql = "SELECT * from my_streaming1.movies";
            stmt = conn.prepareStatement(sql);

            ResultSet rs = stmt.executeQuery(sql);

            //STEP 4: Extract data from result set
            while (rs.next()) {
                String name = rs.getString("name");
                int releaseYear = rs.getInt("year");
                String genre = rs.getString("genre");
                double rating = rs.getDouble("rating");


            }
            //STEP 5: Clean-up environment
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }


    }
    public void register(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connecting to database...");

            String sql = "INSERT INTO my_streaming1.user (name, password) VALUES (?, ?)";
                stmt = conn.prepareStatement(sql);

                stmt.setString(1, username);
                stmt.setString(2, password);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("User data saved successfully.");
                } else {
                    System.out.println("Failed to save user data.");
                }

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
    public void checkLogin(String username, String password){
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");


            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            System.out.println("Creating statement...");
            String sql = "SELECT * FROM my_streaming1.user WHERE name = ? AND password = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1,username);
            stmt.setString(2,password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful.");
            } else {
                System.out.println("Invalid username or password.");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {

            se.printStackTrace();
        } catch (Exception e) {

            e.printStackTrace();
        } finally {

            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    public void readGenre(String genre) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String result = "";

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connecting to database...");

            String sql = "SELECT * FROM my_streaming1.movies WHERE genre LIKE ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + genre + "%"); // Brug LIKE for at finde delvise matches

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int releaseYear = rs.getInt("year");
                String movieGenre = rs.getString("genre");
                double rating = rs.getDouble("rating");

                result = name + ", " + releaseYear + ", " + movieGenre + ", " + rating;
                System.out.println(result);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    public void findMovieByTitle(String input) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String result = "";

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connecting to database...");

            String sql = "SELECT * FROM my_streaming1.movies WHERE name= ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, input);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int releaseYear = rs.getInt("year");
                String movieGenre = rs.getString("genre");
                double rating = rs.getDouble("rating");

                result = name + ", " + releaseYear + ", " + movieGenre + ", " + rating;
                System.out.println(result);
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

}

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {

    Scanner scanner = new Scanner(System.in);

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

                System.out.println(name + ", " + releaseYear + ", " + genre + ", " + rating);

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

            int userLogin = stmt.executeUpdate();

            if (userLogin > 0) {
                System.out.println("User data saved successfully.");
            } else {
                System.out.println("Failed to save user data.");
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void readString() {
        System.out.println("Enter  username");
        String username = scanner.nextLine();

        System.out.println("Enter password");
        String password = scanner.nextLine();
        checkLogin(username, password);

    }

    public void checkLogin(String username, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");


            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);


            System.out.println("Creating statement...");
            String sql = "SELECT * FROM my_streaming1.user WHERE name = ? AND password = ?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Login successful.");
            } else {
                System.out.println("Invalid username or password.");
                readString();
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

                String result = name + ", " + releaseYear + ", " + movieGenre + ", " + rating;
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

        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connecting to database...");

            String sql = "SELECT * FROM my_streaming1.movies WHERE name= ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, input);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");

                String result = "you are now watching "+ name;
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

    public void findMovieByID(String input) {
        Scanner scan = new Scanner(System.in);
        Connection conn = null;
        PreparedStatement stmt = null;
        int movieID = 0;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            String sql = "SELECT movieID FROM my_streaming1.movies WHERE name = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, input);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                movieID = rs.getInt("movieID");

                findMovieByTitle(input);
                System.out.println("Movie ID for " + input + ": " + movieID);

                System.out.println("Enter your username");
                String input1 = scan.nextLine();
                savedMovieInWatchedList(input1, movieID);
            }
            rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException se2) {
                se2.printStackTrace();
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }


    public void savedMovieInWatchedList(String username, int movieID) {

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement findUserId = conn.prepareStatement("SELECT userID FROM my_streaming1.user WHERE name = ?");
             PreparedStatement insertMovie = conn.prepareStatement("INSERT INTO my_streaming1.watched_movies (userId, movieID) VALUES (?, ?)")) {

            findUserId.setString(1, username);
            ResultSet resultSet = findUserId.executeQuery();

            int userId = 0;
            if (resultSet.next()) {
                userId = resultSet.getInt("userID");
            }
            insertMovie.setInt(1, userId);
            insertMovie.setInt(2, movieID);

            insertMovie.executeUpdate();


            System.out.println("Movie saved in watched_list for user: " + username);

        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    public void printMovieFromList() {
        Connection conn = null;
        PreparedStatement stmt = null;
        ArrayList<String> movieArray = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Enter your username: ");
            String username = scanner.nextLine();


            String moviesSql = "SELECT * FROM my_streaming1.movies " +
                    "JOIN watched_movies ON movies.movieID = my_streaming1.watched_movies.movieID " +
                    "JOIN my_streaming1.user ON my_streaming1.user.userID = my_streaming1.watched_movies.userID " +
                    "WHERE my_streaming1.user.name = ?";

            stmt = conn.prepareStatement(moviesSql);
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                int releaseYear = rs.getInt("year");
                String genre = rs.getString("genre");
                double rating = rs.getDouble("rating");

                String movieInfo = name + ", " + releaseYear + ", " + genre + ", " + rating;
                movieArray.add(movieInfo);
            }

            if (movieArray.isEmpty()) {
                System.out.println("No movies found for username: " + username);
            } else {
                System.out.println("Movies for the user with username " + username + ":");
                for (String movieInfo : movieArray) {
                    System.out.println(movieInfo);
                }
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
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StreamingService {
    private static FileIO fileIO = new FileIO();
    private static Database database = new Database();

    private static ArrayList<Media> movieData = fileIO.readFile("/Users/mingo/Documents/GitHub/SP3/SP3NewVersion/src/main/java/100bedstefilm.txt", "movie");
    private static ArrayList<Media> seriesData = fileIO.readFile("/Users/mingo/Documents/GitHub/SP3/SP3NewVersion/src/main/java/100bedsteserier.txt", "series");
    private static String fileName = "/Users/mingo/Documents/GitHub/SP3/SP3NewVersion/src/main/java/user.txt";// til login

    public static void ConsoleLogin() {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter  username");
                    String username1 = scanner.nextLine();

                    System.out.println("Enter password");
                    String password1 = scanner.nextLine();
                    database.checkLogin(username1, password1);
                    displayMenu();
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    database.register(username, password);
                    // register(scanner);
                    break;
                case 3:
                    System.out.println("Exiting the program. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();


        if (checkLogin(username, password)) {
            System.out.println("Login successful!");
            displayMenu();
        } else {
            System.out.println("Invalid username or password");
        }
    }

    private static void register(Scanner scanner) {
        System.out.print("Enter a new username: ");
        String username = scanner.nextLine();

        System.out.print("Enter a new password: ");
        String password = scanner.nextLine();

        saveRegistration(username, password);

        System.out.println("Registration successful!");
    }

    private static boolean checkLogin(String username, String password) {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                String savedUsername = parts[0];
                String savedPassword = parts[1];

                if (username.equals(savedUsername) && password.equals(savedPassword)) {
                    return true;
                }
            }
        } catch (IOException e) {
        }

        return false;
    }

    private static void saveRegistration(String username, String password) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(username + "," + password + "\n");
        } catch (IOException e) {
        }
    }

    private static void displayMenu() {
        User user = new User("");
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("1. see all media");
            System.out.println("2. Pick a movie");
            System.out.println("3. Pick a series");
            System.out.println("4. Search for a genre");
            System.out.println("5. Search for a titel");
            System.out.println("6. Show watch list");
            System.out.println("7. Show saved media");
            System.out.println("8. logout");
            System.out.print("Choose an option: ");
            int choice = scan.nextInt();
            scan.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("all movies");
                    database.readData();
                    for (Media series : seriesData) {
                        System.out.println(series.toString());
                    }
                    break;
                case 2:
                    for (Media movie : movieData) {
                        System.out.println(movie.getTitle());
                    }
                    // addMediaToList(scan,user);
                    break;
                case 3:
                    for (Media series : seriesData) {
                        System.out.println(series.getTitle());
                    }
                    // addMediaToList(scan,user);
                    break;
                case 4:
                    System.out.println("name the genre you want to search for");
                    String getInput = scan.nextLine();
                    database.readGenre(getInput);
                case 5:
                    database.readData();
                    System.out.println("enter the name of the movie you want to watch");
                    String input=scan.nextLine();
                    database.findMovieByTitle(input);
                    database.findMovieByID(input);
                   // String searchedGenre = database.readGenre(getInput);
                    //System.out.println(searchedGenre);
                    break;
                case 6:
                    database.movieArrayPrint();
                   // System.out.println(user.watchList);

                    break;
                case 7:
                    System.out.println(user.saveMedia);
                    //removeMediaFromSavedList(scan,user);
                    break;
                case 8:

                    ConsoleLogin();
                    break;
                default:
                    System.out.println("Invalid choice. try again.");
            }

        }
    }


 /*   private static void addMediaToList(Scanner scanner, User user) {
        System.out.print("Enter the title of the movie or series you want to add: ");
        String selectedTitle = scanner.nextLine();

        Media selectedMovie = findMovieByTitle(selectedTitle);
        Media selectedSeries = findSeriesByTitle(selectedTitle);

        if (selectedMovie != null || selectedSeries != null) {
            System.out.println("Choose an option: ");
            System.out.println("1. Play movie");
            System.out.println("2. Add to Watch List");
            System.out.println("3. Add to Saved Media List");
            System.out.print("Enter the number of the list: ");

            int listChoice = scanner.nextInt();
            scanner.nextLine();

            switch (listChoice) {
                case 1:
                    if(selectedMovie !=null) {
                        System.out.println("you are now watching: " +selectedMovie.getTitle());
                        user.addMediaToWatchList(selectedMovie);
                    }
                    else if(selectedSeries !=null){
                        System.out.println("you are now watching: "+selectedSeries.getTitle());
                        user.addMediaToWatchList(selectedSeries);
                    }
                    break;
                case 2:
                    if (selectedMovie != null) {
                        user.addMediaToWatchList(selectedMovie);
                        System.out.println("Added '" + selectedMovie.getTitle() + "' to your watch list.");
                    } else if (selectedSeries != null) {
                        user.addMediaToWatchList(selectedSeries);
                        System.out.println("Added '" + selectedSeries.getTitle() + "' to your watch list.");
                    }
                    break;
                case 3:
                    if (selectedMovie != null) {
                        user.saveMedia(selectedMovie);
                        System.out.println("Added '" + selectedMovie.getTitle() + "' to your saved media list.");
                    } else if (selectedSeries != null) {
                        user.saveMedia(selectedSeries);
                        System.out.println("Added '" + selectedSeries.getTitle() + "' to your saved media list.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Media not added to any list.");
            }
        } else {
            System.out.println("Media not found.");
        }
    }*/



    /*
    private static Media findMovieByTitle(String title) {
        for (Media movie : movieData) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
    return null;
    }*/

/*
    private static Media findMovieByTitle(String title) {
        try (Connection connection = DriverManager.getConnection("", "your_username", "your_password")) {
            String query = "SELECT * FROM movies WHERE title = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, title);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        // Create and return a Media object based on the database result
                        String movieTitle = resultSet.getString("title");
                        String genre = resultSet.getString("genre");
                        // Add other fields as needed

                        // Return a Media object (you may need to create a constructor for Media)
                        return new Media(movieTitle, genre);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return null;
*/

   /* private static Media findSeriesByTitle(String title) {
        for (Media series : seriesData) {
            if (series.getTitle().equalsIgnoreCase(title)) {
                return series;
            }
        }
        return null;
    }*/

  /* private static void removeMediaFromSavedList(Scanner scanner, User user){
        System.out.println("Do you want to remove a media from watch list? (y/n)");
        String input1 = scanner.nextLine();

        if(input1.equalsIgnoreCase("y")){
            System.out.println("Enter the title of the media to remove: ");
            String input = scanner.nextLine();

            Media seriesToRemove = findSeriesByTitle(input);
            Media moviesToRemove = findMovieByTitle(input);

            if(user.getSaveMedia().contains(seriesToRemove) || user.getSaveMedia().contains(moviesToRemove)){
            user.removeMedia(seriesToRemove,moviesToRemove);
                System.out.println(input + " has now been removed from your list");
            } else{
                System.out.println("Media not found in your list");
                displayMenu();

            }
        }
    }*/
    }


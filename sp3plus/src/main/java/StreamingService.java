import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StreamingService {
    private static FileIO fileIO = new FileIO();
    private static Database database=new Database();

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
                    login(scanner);
                    break;
                case 2:
                    register(scanner);
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
        try (FileWriter writer = new FileWriter(fileName,true)) {
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
            System.out.println("5. Show watch list");
            System.out.println("6. Show saved media");
            System.out.println("7. logout");
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
                    addMediaToList(scan,user);
                    break;
                case 3:
                    for (Media series : seriesData) {
                        System.out.println(series.getTitle());
                    }
                    addMediaToList(scan,user);
                    break;
                case 4:
                    searchForGenre(scan);
                    break;
                case 5:
                    System.out.println(user.watchList);

                    break;
                case 6:
                    System.out.println(user.saveMedia);
                    removeMediaFromSavedList(scan,user);
                    break;
                case 7:
                    ConsoleLogin();
                    break;
                default:
                    System.out.println("Invalid choice. try again.");
            }

        }
    }


    private static void addMediaToList(Scanner scanner, User user) {
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
    }
    private static Media findMovieByTitle(String title) {
        for (Media movie : movieData) {
            if (movie.getTitle().equalsIgnoreCase(title)) {
                return movie;
            }
        }
    return null;
    }

    private static Media findSeriesByTitle(String title) {
        for (Media series : seriesData) {
            if (series.getTitle().equalsIgnoreCase(title)) {
                return series;
            }
        }
        return null;
    }

    private static void removeMediaFromSavedList(Scanner scanner, User user){
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
    }


    private static void searchForGenre(Scanner scanner) {
        boolean found = false;
        System.out.println("Enter the genre you want to browse through");
        String searchedGenre = scanner.nextLine();

        System.out.println("Media in " + searchedGenre + " :");
        for (Media movie : movieData) {
            for (String s : movie.getGenres()) {
                if (s.trim().equalsIgnoreCase(searchedGenre.trim())) {
                    System.out.println(movie.toString());
                    found = true;
                }
            }
        }

        for (Media series : seriesData) {
            for (String s : series.getGenres()) {
                if (s.trim().equalsIgnoreCase(searchedGenre.trim())) {
                    System.out.println(series.toString());
                    found = true;
                }
            }
        }

            if (!found) {
                System.out.println("Nothing found in the searched genre");
            } else {
                System.out.println("Media search for '" + searchedGenre + "' completed.");
            }

        }
    }

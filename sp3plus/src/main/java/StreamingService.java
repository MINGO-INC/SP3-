import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class StreamingService {
    private static FileIO fileIO = new FileIO();
    private static Database database = new Database();

    private static ArrayList<Media> movieData = fileIO.readFile("/Users/mingo/Documents/GitHub/SP3/SP3NewVersion/src/main/java/100bedstefilm.txt", "movie");
    private static ArrayList<Media> seriesData = fileIO.readFile("/Users/mingo/Documents/GitHub/SP3/SP3NewVersion/src/main/java/100bedsteserier.txt", "series");
    private static String fileName = "/Users/mingo/Documents/GitHub/SP3/SP3NewVersion/src/main/java/user.txt";// til login

    public static void consoleLogin() {

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
                    database.readString();
                    displayMenu();
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();

                    System.out.print("Enter password: ");
                    String password = scanner.nextLine();

                    database.register(username, password);
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
                    break;
                case 3:
                    for (Media series : seriesData) {
                        System.out.println(series.getTitle());
                    }
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
                    break;
                case 6:
                    database.printMovieFromList();

                    break;
                case 7:
                    System.out.println(user.saveMedia);
                    break;
                case 8:

                    consoleLogin();
                    break;
                default:
                    System.out.println("Invalid choice. try again.");
            }

        }
    }

    }


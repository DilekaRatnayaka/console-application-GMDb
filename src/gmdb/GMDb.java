package gmdb;

import java.util.Date;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.Query;
import Connector.Controller;
import pojo.User;
import pojo.Admin;
import pojo.Movie;
import pojo.Purchase;
import pojo.Review;
import pojo.Genre;

public class GMDb {

    private User loggedInUser;  // Track the logged-in user
    private Admin loggedInAdmin;  // Track the logged-in admin

    public static void main(String[] args) {
        GMDb app = new GMDb();
        app.start();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to GMDb!");

        while (true) {
            if (loggedInUser == null && loggedInAdmin == null) {
                // User not logged in, show login/register options
                System.out.println("Please choose an option:");
                System.out.println("(1) Register");
                System.out.println("(2) Login");
                System.out.println("(3) Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                if (choice == 1) {
                    System.out.println("Are you registering as a (1) User or (2) Admin?");
                    int role = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline

                    if (role == 1) {
                        userRegistration();
                    } else if (role == 2) {
                        adminRegistration();
                    } else {
                        System.out.println("Invalid choice.");
                    }

                } else if (choice == 2) {
                    login();  // Login logic here

                } else if (choice == 3) {
                    System.out.println("Exiting GMDb...");
                    break;
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } else if (loggedInUser != null) {
                // User is logged in, show logged-in options
                System.out.println("Welcome, " + loggedInUser.getUsername() + "!");
                System.out.println("Please choose an option:");
                System.out.println("(1) Search Movies");
                System.out.println("(2) See All Available Movies");
                System.out.println("(3) Purchase a Movie");
                System.out.println("(4) Review a Purchased Movie");
                System.out.println("(4) Logout");

                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                if (choice == 1) {
                    searchMovies();  // Search movies functionality

                } else if (choice == 2) {
                    showAllMovies();  // Display all available movies

                } else if (choice == 3) {
                    purchaseMovie();  // Purchase movie functionality

                } else if (choice == 4) {
                    reviewPurchasedMovie();  // Review a purchased movie

                } else if (choice == 5) {
                    loggedInUser = null;  // Log out the user
                    System.out.println("You have been logged out.");
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            } else if (loggedInAdmin != null) {
                // Admin is logged in, show admin-specific options
                System.out.println("Welcome, Admin " + loggedInAdmin.getUsername() + "!");
                System.out.println("Please choose an option:");
                System.out.println("(1) View All Registered Users");
                System.out.println("(2) View Purchased Movies by a User");
                System.out.println("(3) Insert New Movie");  
                System.out.println("(4) Update Movie");
                System.out.println("(5) Delete Movie");
                System.out.println("(6) View All Movies"); 
                System.out.println("(7) Logout");
                
                int choice = scanner.nextInt();
                scanner.nextLine();  // Consume the newline character

                if (choice == 1) {
                    viewAllUsers();  // View all registered users

                } else if (choice == 2) {
                    viewUserPurchases();  // View movies purchased by a specific user

                } else if (choice == 3) {
                    insertNewMovie();  // Insert new movie logic
                } else if (choice == 4) {
                    updateMovie();  // Update movie logic
                } else if (choice == 5) {
                    deleteMovie();  // Delete movie logic
                } else if (choice == 6) {
                    viewAllMovies();  // View all movies logic
                } else if (choice == 7) {
                    loggedInAdmin = null;  // Logout
                    System.out.println("You have been logged out.");
                } else {
                    System.out.println("Invalid option. Please try again.");
                }
            }
        }

        scanner.close();
    }

    // User registration
    public void userRegistration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);

        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(newUser);

        tx.commit();
        session.close();

        System.out.println("User registered successfully!");
    }

    // Admin registration
    public void adminRegistration() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        Admin newAdmin = new Admin();
        newAdmin.setUsername(username);
        newAdmin.setEmail(email);
        newAdmin.setPassword(password);

        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();

        session.save(newAdmin);

        tx.commit();
        session.close();

        System.out.println("Admin registered successfully!");
    }

    // Login logic for both User and Admin
    public void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Are you logging in as a (1) User or (2) Admin?");
        int role = scanner.nextInt();
        scanner.nextLine();  // Consume the newline

        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();

        if (role == 1) {
            loginUser(email, password);
        } else if (role == 2) {
            loginAdmin(email, password);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    // User login method
    public void loginUser(String email, String password) {
        Session session = Controller.getSessionFactory().openSession();
        User user = (User) session.createQuery("FROM User WHERE email = :email AND password = :password")
                                  .setParameter("email", email)
                                  .setParameter("password", password)
                                  .uniqueResult();
        session.close();

        if (user != null) {
            loggedInUser = user;
            System.out.println("User logged in successfully!");
        } else {
            System.out.println("Invalid user credentials.");
        }
    }

    // Admin login method
    public void loginAdmin(String email, String password) {
        Session session = Controller.getSessionFactory().openSession();
        Admin admin = (Admin) session.createQuery("FROM Admin WHERE email = :email AND password = :password")
                                     .setParameter("email", email)
                                     .setParameter("password", password)
                                     .uniqueResult();
        session.close();

        if (admin != null) {
            loggedInAdmin = admin;
            System.out.println("Admin logged in successfully!");
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    // Method to search for movies and show details with average rating
    public void searchMovies() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the name of the movie you want to search for:");
        String movieName = scanner.nextLine();

        Session session = Controller.getSessionFactory().openSession();
        
        // Query to find movies matching the search criteria
        Query query = session.createQuery("FROM Movie WHERE movie_name LIKE :movieName");
        query.setParameter("movieName", "%" + movieName + "%");
        List<Movie> movies = query.list();

        if (movies.isEmpty()) {
            System.out.println("No movies found with the given name.");
            session.close();
            return;
        }

        // Display movie details and calculate average rating for each found movie
        for (Movie movie : movies) {
            System.out.println("\nMovie ID: " + movie.getMovieId());
            System.out.println("Movie Name: " + movie.getMovieName());
            System.out.println("Director: " + movie.getDirector());
            System.out.println("Actors: " + movie.getActors());
            System.out.println("Runtime: " + movie.getRuntime() + " minutes");

            // Fetch reviews and calculate the average rating for the movie
            Query reviewQuery = session.createQuery("FROM Review WHERE movieId = :movieId");
            reviewQuery.setParameter("movieId", movie.getMovieId());
            List<Review> reviews = reviewQuery.list();

            if (reviews.isEmpty()) {
                System.out.println("Average Rating: Not rated yet.");
            } else {
                double totalRating = 0;
                for (Review review : reviews) {
                    totalRating += review.getRating();
                }
                double averageRating = totalRating / reviews.size();
                System.out.printf("Average Rating: %.2f / 10\n", averageRating);
            }
        }

        session.close();
    }

    // Method to display all available movies in the database (user functionality)
    public void showAllMovies() {
        if (loggedInUser == null) {
            System.out.println("Please log in to view available movies.");
            return;  // Exit method if not logged in
        }

        Session session = Controller.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Movie");
        List<Movie> movies = query.list();
        session.close();

        if (movies.isEmpty()) {
            System.out.println("No movies available in the database.");
        } else {
            System.out.println("Available movies:");
            for (Movie movie : movies) {
                System.out.println("ID: " + movie.getMovieId() + ", Name: " + movie.getMovieName() +
                                   ", Director: " + movie.getDirector() + ", Actors: " + movie.getActors() +
                                   ", Runtime: " + movie.getRuntime() + " mins");
            }
        }
    }

    // Method to allow a user to purchase a movie (user functionality)
    public void purchaseMovie() {
        if (loggedInUser == null) {
            System.out.println("Please log in to purchase movies.");
            return;  // Exit method if not logged in
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the movie you want to purchase:");
        int movieId = scanner.nextInt();

        Session session = Controller.getSessionFactory().openSession();
        Movie movie = (Movie) session.get(Movie.class, movieId);

        if (movie == null) {
            System.out.println("Movie not found with ID: " + movieId);
        } else {
            Purchase purchase = new Purchase();
            purchase.setUserId(loggedInUser.getUserId());
            purchase.setMovieId(movieId);
            purchase.setPurchaseDate(new Date());

            Transaction tx = session.beginTransaction();
            session.save(purchase);
            tx.commit();
            session.close();

            System.out.println("Movie purchased successfully: " + movie.getMovieName());
        }
    }
    
    // Method to allow a user to review a purchased movie
    public void reviewPurchasedMovie() {
        if (loggedInUser == null) {
            System.out.println("Please log in to review a movie.");
            return;  // Exit method if not logged in
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the movie you want to review:");
        int movieId = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        // Check if the user has purchased this movie
        Session session = Controller.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Purchase WHERE userId = :userId AND movieId = :movieId");
        query.setParameter("userId", loggedInUser.getUserId());
        query.setParameter("movieId", movieId);
        List<Purchase> purchases = query.list();

        if (purchases.isEmpty()) {
            System.out.println("You can only review movies that you have purchased.");
            session.close();
            return;
        }

        // Proceed to add a review since the user has purchased this movie
        System.out.println("Enter your rating for the movie (1-10):");
        int rating = scanner.nextInt();
        scanner.nextLine();  // Consume the newline character

        System.out.println("Enter your review description:");
        String reviewDescription = scanner.nextLine();

        Review review = new Review();
        review.setUserId(loggedInUser.getUserId());
        review.setMovieId(movieId);
        review.setRating(rating);
        review.setReviewDescription(reviewDescription);
        review.setReviewDate(new Date());

        Transaction tx = session.beginTransaction();
        session.save(review);
        tx.commit();
        session.close();

        System.out.println("Thank you! Your review has been submitted.");
    }

    // Method to view all registered users (admin functionality)
    public void viewAllUsers() {
        if (loggedInAdmin == null) {
            System.out.println("Please log in as an admin to view all users.");
            return;  // Exit method if not logged in as admin
        }

        Session session = Controller.getSessionFactory().openSession();
        Query query = session.createQuery("FROM User");
        List<User> users = query.list();
        session.close();

        if (users.isEmpty()) {
            System.out.println("No users found in the system.");
        } else {
            System.out.println("Registered users:");
            for (User user : users) {
                System.out.println("ID: " + user.getUserId() + ", Username: " + user.getUsername() +
                                   ", Email: " + user.getEmail());
            }
        }
    }

    // Method to view purchased movies by a specific user (admin functionality)
    public void viewUserPurchases() {
        if (loggedInAdmin == null) {
            System.out.println("Please log in as an admin to view user purchases.");
            return;  // Exit method if not logged in as admin
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the user ID to view their purchases:");
        int userId = scanner.nextInt();

        Session session = Controller.getSessionFactory().openSession();
        Query userQuery = session.createQuery("FROM User WHERE userId = :userId");
        userQuery.setParameter("userId", userId);
        User user = (User) userQuery.uniqueResult();

        if (user == null) {
            System.out.println("No user found with ID: " + userId);
        } else {
            Query purchaseQuery = session.createQuery("FROM Purchase WHERE userId = :userId");
            purchaseQuery.setParameter("userId", userId);
            List<Purchase> purchases = purchaseQuery.list();

            if (purchases.isEmpty()) {
                System.out.println("No purchases found for user: " + user.getUsername());
            } else {
                System.out.println("Movies purchased by " + user.getUsername() + ":");
                for (Purchase purchase : purchases) {
                    Movie movie = (Movie) session.get(Movie.class, purchase.getMovieId());
                    System.out.println("Movie ID: " + movie.getMovieId() + ", Name: " + movie.getMovieName());
                }
            }
        }

        session.close();
    }
    
    // Method to insert a new movie
    public void insertNewMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter movie name:");
        String movieName = scanner.nextLine();
        System.out.println("Enter director:");
        String director = scanner.nextLine();
        System.out.println("Enter actors (comma separated):");
        String actors = scanner.nextLine();
        System.out.println("Enter runtime (in minutes):");
        int runtime = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        
        // Insert the movie into the Movie table
        Movie newMovie = new Movie();
        newMovie.setMovieName(movieName);
        newMovie.setDirector(director);
        newMovie.setActors(actors);
        newMovie.setRuntime(runtime);

        Session session = Controller.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(newMovie);
        tx.commit();

        System.out.println("Movie added successfully with ID: " + newMovie.getMovieId());

        // Insert the genres for the movie
        System.out.println("Enter genres for the movie (comma separated):");
        String genresInput = scanner.nextLine();
        String[] genreNames = genresInput.split(",");
        
        for (String genreName : genreNames) {
            genreName = genreName.trim();
            
            // Check if the genre already exists in the Genre table
            Query query = session.createQuery("FROM Genre WHERE genreName = :genreName");
            query.setParameter("genreName", genreName);
            Genre genre = (Genre) query.uniqueResult();
            
            if (genre == null) {
                // If genre doesn't exist, create a new one
                genre = new Genre();
                genre.setGenreName(genreName);
                session.save(genre);
                System.out.println("New genre added: " + genreName);
            }

            // Insert into Movie_Genre table (link movie and genre)
            Query movieGenreQuery = session.createSQLQuery("INSERT INTO Movie_Genre (movie_id, genre_id) VALUES (:movieId, :genreId)");
            movieGenreQuery.setParameter("movieId", newMovie.getMovieId());
            movieGenreQuery.setParameter("genreId", genre.getGenreId());
            movieGenreQuery.executeUpdate();
        }
        
        session.close();
        System.out.println("Movie and genres added successfully.");
    }
    
    // Method to update an existing movie
    public void updateMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the movie you want to update:");
        int movieId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Session session = Controller.getSessionFactory().openSession();
        Movie movie = (Movie) session.get(Movie.class, movieId);

        if (movie != null) {
            System.out.println("Current details of the movie:");
            System.out.println("Name: " + movie.getMovieName());
            System.out.println("Director: " + movie.getDirector());
            System.out.println("Actors: " + movie.getActors());
            System.out.println("Runtime: " + movie.getRuntime() + " mins");

            System.out.println("Enter new name (or press Enter to keep current):");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                movie.setMovieName(newName);
            }

            System.out.println("Enter new director (or press Enter to keep current):");
            String newDirector = scanner.nextLine();
            if (!newDirector.trim().isEmpty()) {
                movie.setDirector(newDirector);
            }

            System.out.println("Enter new actors (or press Enter to keep current):");
            String newActors = scanner.nextLine();
            if (!newActors.trim().isEmpty()) {
                movie.setActors(newActors);
            }

            System.out.println("Enter new runtime (or press Enter to keep current):");
            String runtimeInput = scanner.nextLine();
            if (!runtimeInput.trim().isEmpty()) {
                movie.setRuntime(Integer.parseInt(runtimeInput));
            }

            Transaction tx = session.beginTransaction();
            session.update(movie);
            tx.commit();
            session.close();

            System.out.println("Movie updated successfully.");
        } else {
            session.close();
            System.out.println("Movie not found.");
        }
    }

    // Method to delete an existing movie
    public void deleteMovie() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the movie you want to delete:");
        int movieId = scanner.nextInt();
        scanner.nextLine();  // Consume newline

        Session session = Controller.getSessionFactory().openSession();
        Movie movie = (Movie) session.get(Movie.class, movieId);

        if (movie != null) {
            Transaction tx = session.beginTransaction();
            session.delete(movie);
            tx.commit();
            session.close();

            System.out.println("Movie deleted successfully.");
        } else {
            session.close();
            System.out.println("Movie not found.");
        }
    }
    
    // Method to view all available movies
    public void viewAllMovies() {
        Session session = Controller.getSessionFactory().openSession();
        Query query = session.createQuery("FROM Movie");
        List<Movie> movies = query.list();
        session.close();

        if (movies.isEmpty()) {
            System.out.println("No movies found.");
        } else {
            System.out.println("Available movies:");
            for (Movie movie : movies) {
                System.out.println("ID: " + movie.getMovieId() +
                                   ", Name: " + movie.getMovieName() +
                                   ", Director: " + movie.getDirector() +
                                   ", Runtime: " + movie.getRuntime() + " mins");
            }
        }
    }
}






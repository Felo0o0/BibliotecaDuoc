package com.duoc.library.main;

import com.duoc.library.model.*;
import com.duoc.library.service.*;
import com.duoc.library.util.*;
import com.duoc.library.exception.*;
import java.io.IOException;
import java.util.*;

public class Main {
    private static LibraryService libraryService = new LibraryService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            System.out.println("=== LIBRARY MANAGEMENT SYSTEM ===");
            System.out.flush(); // Forzar salida
            
            loadInitialData();
            showMainMenu();
            
        } catch (Exception e) {
            System.err.println("Critical error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void loadInitialData() {
        try {
            System.out.println("Loading initial data...");
            
            // Load sample books
            libraryService.addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));
            libraryService.addBook(new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman"));
            libraryService.addBook(new Book("978-0321356680", "Effective Unit Testing", "Lasse Koskela"));
            
            // Load sample users
            libraryService.addUser(new User("U001", "John Doe", "john.doe@email.com"));
            libraryService.addUser(new User("U002", "Jane Smith", "jane.smith@email.com"));
            
            System.out.println("Initial data loaded successfully.");
            System.out.flush();
            
        } catch (Exception e) {
            System.err.println("Error loading initial data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showMainMenu() {
        while (true) {
            try {
                System.out.println("\n=== MAIN MENU ===");
                System.out.println("1. Book Management");
                System.out.println("2. User Management");
                System.out.println("3. Loan Management");
                System.out.println("4. Reports");
                System.out.println("5. File Operations");
                System.out.println("0. Exit");
                System.out.print("Select an option: ");
                System.out.flush();

                String input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Please enter a valid option.");
                    continue;
                }

                int option = Integer.parseInt(input.trim());

                switch (option) {
                    case 1: showBookMenu(); break;
                    case 2: showUserMenu(); break;
                    case 3: showLoanMenu(); break;
                    case 4: showReportsMenu(); break;
                    case 5: showFileMenu(); break;
                    case 0: 
                        System.out.println("Thank you for using the Library Management System!");
                        return;
                    default: 
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid number.");
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void showBookMenu() {
        while (true) {
            try {
                System.out.println("\n=== BOOK MANAGEMENT ===");
                System.out.println("1. Add Book");
                System.out.println("2. Search Book by ISBN");
                System.out.println("3. Search Books by Title");
                System.out.println("4. List All Books");
                System.out.println("0. Back to Main Menu");
                System.out.print("Select an option: ");
                System.out.flush();

                String input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Please enter a valid option.");
                    continue;
                }

                int option = Integer.parseInt(input.trim());

                switch (option) {
                    case 1: addBook(); break;
                    case 2: searchBookByIsbn(); break;
                    case 3: searchBooksByTitle(); break;
                    case 4: listAllBooks(); break;
                    case 0: return;
                    default: System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid number.");
            } catch (Exception e) {
                System.err.println("Unexpected error: " + e.getMessage());
            }
        }
    }

    private static void addBook() {
        try {
            System.out.print("Enter ISBN: ");
            System.out.flush();
            String isbn = scanner.nextLine();
            
            System.out.print("Enter Title: ");
            System.out.flush();
            String title = scanner.nextLine();
            
            System.out.print("Enter Author: ");
            System.out.flush();
            String author = scanner.nextLine();

            Book book = new Book(isbn, title, author);
            libraryService.addBook(book);
            System.out.println("Book added successfully: " + book);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error adding book: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void searchBookByIsbn() {
        try {
            System.out.print("Enter ISBN: ");
            System.out.flush();
            String isbn = scanner.nextLine();
            
            Book book = libraryService.findBookByIsbn(isbn);
            if (book != null) {
                System.out.println("Book found: " + book);
            } else {
                System.out.println("Book with ISBN " + isbn + " not found.");
            }
            
        } catch (Exception e) {
            System.err.println("Error searching book: " + e.getMessage());
        }
    }

    private static void searchBooksByTitle() {
        try {
            System.out.print("Enter title (partial search): ");
            System.out.flush();
            String title = scanner.nextLine();
            
            List<Book> books = libraryService.searchBooksByTitle(title);
            if (books.isEmpty()) {
                System.out.println("No books found with title containing: " + title);
            } else {
                System.out.println("Books found:");
                books.forEach(System.out::println);
            }
            
        } catch (Exception e) {
            System.err.println("Error searching books: " + e.getMessage());
        }
    }

    private static void listAllBooks() {
        try {
            List<Book> books = libraryService.getAllBooks();
            if (books.isEmpty()) {
                System.out.println("No books in the library.");
            } else {
                System.out.println("All books in the library:");
                books.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error listing books: " + e.getMessage());
        }
    }

    // ... resto de métodos con el mismo patrón de System.out.flush()
    
    private static void showUserMenu() {
        while (true) {
            try {
                System.out.println("\n=== USER MANAGEMENT ===");
                System.out.println("1. Add User");
                System.out.println("2. Find User by ID");
                System.out.println("3. List All Users");
                System.out.println("0. Back to Main Menu");
                System.out.print("Select an option: ");
                System.out.flush();

                String input = scanner.nextLine();
                int option = Integer.parseInt(input.trim());

                switch (option) {
                    case 1: addUser(); break;
                    case 2: findUser(); break;
                    case 3: listAllUsers(); break;
                    case 0: return;
                    default: System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid number.");
            }
        }
    }

    private static void addUser() {
        try {
            System.out.print("Enter User ID: ");
            System.out.flush();
            String id = scanner.nextLine();
            
            System.out.print("Enter Name: ");
            System.out.flush();
            String name = scanner.nextLine();
            
            System.out.print("Enter Email: ");
            System.out.flush();
            String email = scanner.nextLine();

            User user = new User(id, name, email);
            libraryService.addUser(user);
            System.out.println("User added successfully: " + user);
            
        } catch (InvalidUserException e) {
            System.err.println("Error adding user: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void findUser() {
        try {
            System.out.print("Enter User ID: ");
            System.out.flush();
            String id = scanner.nextLine();
            
            User user = libraryService.findUserById(id);
            System.out.println("User found: " + user);
            
        } catch (InvalidUserException e) {
            System.err.println("Error finding user: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void listAllUsers() {
        try {
            Collection<User> users = libraryService.getAllUsers();
            if (users.isEmpty()) {
                System.out.println("No users registered.");
            } else {
                System.out.println("All registered users:");
                users.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error listing users: " + e.getMessage());
        }
    }

    private static void showLoanMenu() {
        while (true) {
            try {
                System.out.println("\n=== LOAN MANAGEMENT ===");
                System.out.println("1. Loan Book");
                System.out.println("2. Return Book");
                System.out.println("3. View User Loans");
                System.out.println("4. View Active Loans");
                System.out.println("5. View Overdue Loans");
                System.out.println("0. Back to Main Menu");
                System.out.print("Select an option: ");
                System.out.flush();

                String input = scanner.nextLine();
                int option = Integer.parseInt(input.trim());

                switch (option) {
                    case 1: loanBook(); break;
                    case 2: returnBook(); break;
                    case 3: viewUserLoans(); break;
                    case 4: viewActiveLoans(); break;
                    case 5: viewOverdueLoans(); break;
                    case 0: return;
                    default: System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid number.");
            }
        }
    }

    private static void loanBook() {
        try {
            System.out.print("Enter User ID: ");
            System.out.flush();
            String userId = scanner.nextLine();
            
            System.out.print("Enter Book ISBN: ");
            System.out.flush();
            String isbn = scanner.nextLine();

            Loan loan = libraryService.loanBook(userId, isbn);
            System.out.println("Book loaned successfully: " + loan);
            
        } catch (InvalidUserException | BookNotFoundException | BookAlreadyLoanedException e) {
            System.err.println("Error processing loan: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void returnBook() {
        try {
            System.out.print("Enter Loan ID: ");
            System.out.flush();
            String loanId = scanner.nextLine();

            libraryService.returnBook(loanId);
            System.out.println("Book returned successfully.");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error returning book: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void viewUserLoans() {
        try {
            System.out.print("Enter User ID: ");
            System.out.flush();
            String userId = scanner.nextLine();
            
            List<Loan> loans = libraryService.getUserLoans(userId);
            if (loans.isEmpty()) {
                System.out.println("No loans found for user: " + userId);
            } else {
                System.out.println("Loans for user " + userId + ":");
                loans.forEach(System.out::println);
            }
            
        } catch (InvalidUserException e) {
            System.err.println("Error viewing user loans: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void viewActiveLoans() {
        try {
            List<Loan> loans = libraryService.getActiveLoans();
            if (loans.isEmpty()) {
                System.out.println("No active loans.");
            } else {
                System.out.println("Active loans:");
                loans.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error viewing active loans: " + e.getMessage());
        }
    }

    private static void viewOverdueLoans() {
        try {
            List<Loan> loans = libraryService.getOverdueLoans();
            if (loans.isEmpty()) {
                System.out.println("No overdue loans.");
            } else {
                System.out.println("Overdue loans:");
                loans.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error viewing overdue loans: " + e.getMessage());
        }
    }

    private static void showReportsMenu() {
        System.out.println("\n=== REPORTS ===");
        System.out.println("Available Books: " + libraryService.getAllBooks().stream().filter(Book::isAvailable).count());
        System.out.println("Loaned Books: " + libraryService.getAllBooks().stream().filter(book -> !book.isAvailable()).count());
        System.out.println("Total Users: " + libraryService.getAllUsers().size());
        System.out.println("Active Loans: " + libraryService.getActiveLoans().size());
        System.out.println("Overdue Loans: " + libraryService.getOverdueLoans().size());
        System.out.flush();
    }

    private static void showFileMenu() {
        while (true) {
            try {
                System.out.println("\n=== FILE OPERATIONS ===");
                System.out.println("1. Load Books from CSV");
                System.out.println("2. Load Users from CSV");
                System.out.println("3. Export Books to CSV");
                System.out.println("4. Export Users to CSV");
                System.out.println("5. Export Loans to CSV");
                System.out.println("0. Back to Main Menu");
                System.out.print("Select an option: ");
                System.out.flush();

                String input = scanner.nextLine();
                int option = Integer.parseInt(input.trim());

                switch (option) {
                    case 1: loadBooksFromCSV(); break;
                    case 2: loadUsersFromCSV(); break;
                    case 3: exportBooksToCSV(); break;
                    case 4: exportUsersToCSV(); break;
                    case 5: exportLoansToCSV(); break;
                    case 0: return;
                    default: System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Please enter a valid number.");
            }
        }
    }

    private static void loadBooksFromCSV() {
        try {
            System.out.print("Enter CSV filename: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            List<Book> books = CSVReader.readBooksFromCSV(filename);
            for (Book book : books) {
                try {
                    libraryService.addBook(book);
                } catch (IllegalArgumentException e) {
                    System.err.println("Skipping duplicate book: " + book.getIsbn());
                }
            }
            System.out.println("Books loaded successfully from " + filename);
            
        } catch (FileNotFoundException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Data format error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void loadUsersFromCSV() {
        try {
            System.out.print("Enter CSV filename: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            List<User> users = CSVReader.readUsersFromCSV(filename);
            for (User user : users) {
                try {
                    libraryService.addUser(user);
                } catch (InvalidUserException e) {
                    System.err.println("Skipping duplicate user: " + user.getId());
                }
            }
            System.out.println("Users loaded successfully from " + filename);
            
        } catch (FileNotFoundException e) {
            System.err.println("File error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("IO error: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Data format error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void exportBooksToCSV() {
        try {
            System.out.print("Enter output filename: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            com.duoc.library.util.FileWriter.writeBooksToCSV(libraryService.getAllBooks(), filename);
            System.out.println("Books exported successfully to " + filename);
            
        } catch (IOException e) {
            System.err.println("Error exporting books: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void exportUsersToCSV() {
        try {
            System.out.print("Enter output filename: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            com.duoc.library.util.FileWriter.writeUsersToCSV(new ArrayList<>(libraryService.getAllUsers()), filename);
            System.out.println("Users exported successfully to " + filename);
            
        } catch (IOException e) {
            System.err.println("Error exporting users: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void exportLoansToCSV() {
        try {
            System.out.print("Enter output filename: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            com.duoc.library.util.FileWriter.writeLoansToCSV(libraryService.getActiveLoans(), filename);
            System.out.println("Loans exported successfully to " + filename);
            
        } catch (IOException e) {
            System.err.println("Error exporting loans: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}
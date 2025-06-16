/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

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
            System.out.println("=== SISTEMA DE GESTION DE BIBLIOTECA ===");
            System.out.flush();
            
            loadInitialData();
            showMainMenu();
            
        } catch (Exception e) {
            System.err.println("Error critico: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }

    private static void loadInitialData() {
        try {
            System.out.println("Cargando datos iniciales...");
            
            // Load sample books
            libraryService.addBook(new Book("978-0134685991", "Effective Java", "Joshua Bloch"));
            libraryService.addBook(new Book("978-0596009205", "Head First Design Patterns", "Eric Freeman"));
            libraryService.addBook(new Book("978-0321356680", "Effective Unit Testing", "Lasse Koskela"));
            
            // Load sample users
            libraryService.addUser(new User("U001", "Juan Perez", "juan.perez@email.com"));
            libraryService.addUser(new User("U002", "Maria Silva", "maria.silva@email.com"));
            
            System.out.println("Datos iniciales cargados exitosamente.");
            System.out.flush();
            
        } catch (Exception e) {
            System.err.println("Error cargando datos iniciales: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void showMainMenu() {
        while (true) {
            try {
                System.out.println("\n=== MENU PRINCIPAL ===");
                System.out.println("1. Gestion de Libros");
                System.out.println("2. Gestion de Usuarios");
                System.out.println("3. Gestion de Prestamos");
                System.out.println("4. Reportes");
                System.out.println("5. Operaciones de Archivos");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");
                System.out.flush();

                String input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Por favor ingrese una opcion valida.");
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
                        System.out.println("Gracias por usar el Sistema de Gestion de Biblioteca!");
                        return;
                    default: 
                        System.out.println("Opcion invalida. Por favor intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Por favor ingrese un numero valido.");
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static void showBookMenu() {
        while (true) {
            try {
                System.out.println("\n=== GESTION DE LIBROS ===");
                System.out.println("1. Agregar Libro");
                System.out.println("2. Buscar Libro por ISBN");
                System.out.println("3. Buscar Libros por Titulo");
                System.out.println("4. Listar Todos los Libros");
                System.out.println("0. Volver al Menu Principal");
                System.out.print("Seleccione una opcion: ");
                System.out.flush();

                String input = scanner.nextLine();
                if (input == null || input.trim().isEmpty()) {
                    System.out.println("Por favor ingrese una opcion valida.");
                    continue;
                }

                int option = Integer.parseInt(input.trim());

                switch (option) {
                    case 1: addBook(); break;
                    case 2: searchBookByIsbn(); break;
                    case 3: searchBooksByTitle(); break;
                    case 4: listAllBooks(); break;
                    case 0: return;
                    default: System.out.println("Opcion invalida. Por favor intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Por favor ingrese un numero valido.");
            } catch (Exception e) {
                System.err.println("Error inesperado: " + e.getMessage());
            }
        }
    }

    private static void addBook() {
        try {
            System.out.print("Ingrese ISBN: ");
            System.out.flush();
            String isbn = scanner.nextLine();
            
            System.out.print("Ingrese Titulo: ");
            System.out.flush();
            String title = scanner.nextLine();
            
            System.out.print("Ingrese Autor: ");
            System.out.flush();
            String author = scanner.nextLine();

            Book book = new Book(isbn, title, author);
            libraryService.addBook(book);
            System.out.println("Libro agregado exitosamente: " + book);
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error agregando libro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void searchBookByIsbn() {
        try {
            System.out.print("Ingrese ISBN: ");
            System.out.flush();
            String isbn = scanner.nextLine();
            
            Book book = libraryService.findBookByIsbn(isbn);
            if (book != null) {
                System.out.println("Libro encontrado: " + book);
            } else {
                System.out.println("Libro con ISBN " + isbn + " no encontrado.");
            }
            
        } catch (Exception e) {
            System.err.println("Error buscando libro: " + e.getMessage());
        }
    }

    private static void searchBooksByTitle() {
        try {
            System.out.print("Ingrese titulo (busqueda parcial): ");
            System.out.flush();
            String title = scanner.nextLine();
            
            List<Book> books = libraryService.searchBooksByTitle(title);
            if (books.isEmpty()) {
                System.out.println("No se encontraron libros con titulo que contenga: " + title);
            } else {
                System.out.println("Libros encontrados:");
                books.forEach(System.out::println);
            }
            
        } catch (Exception e) {
            System.err.println("Error buscando libros: " + e.getMessage());
        }
    }

    private static void listAllBooks() {
        try {
            List<Book> books = libraryService.getAllBooks();
            if (books.isEmpty()) {
                System.out.println("No hay libros en la biblioteca.");
            } else {
                System.out.println("Todos los libros en la biblioteca:");
                books.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error listando libros: " + e.getMessage());
        }
    }

    private static void showUserMenu() {
        while (true) {
            try {
                System.out.println("\n=== GESTION DE USUARIOS ===");
                System.out.println("1. Agregar Usuario");
                System.out.println("2. Buscar Usuario por ID");
                System.out.println("3. Listar Todos los Usuarios");
                System.out.println("0. Volver al Menu Principal");
                System.out.print("Seleccione una opcion: ");
                System.out.flush();

                String input = scanner.nextLine();
                int option = Integer.parseInt(input.trim());

                switch (option) {
                    case 1: addUser(); break;
                    case 2: findUser(); break;
                    case 3: listAllUsers(); break;
                    case 0: return;
                    default: System.out.println("Opcion invalida. Por favor intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Por favor ingrese un numero valido.");
            }
        }
    }

    private static void addUser() {
        try {
            System.out.print("Ingrese ID de Usuario: ");
            System.out.flush();
            String id = scanner.nextLine();
            
            System.out.print("Ingrese Nombre: ");
            System.out.flush();
            String name = scanner.nextLine();
            
            System.out.print("Ingrese Email: ");
            System.out.flush();
            String email = scanner.nextLine();

            User user = new User(id, name, email);
            libraryService.addUser(user);
            System.out.println("Usuario agregado exitosamente: " + user);
            
        } catch (InvalidUserException e) {
            System.err.println("Error agregando usuario: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void findUser() {
        try {
            System.out.print("Ingrese ID de Usuario: ");
            System.out.flush();
            String id = scanner.nextLine();
            
            User user = libraryService.findUserById(id);
            System.out.println("Usuario encontrado: " + user);
            
        } catch (InvalidUserException e) {
            System.err.println("Error buscando usuario: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void listAllUsers() {
        try {
            Collection<User> users = libraryService.getAllUsers();
            if (users.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
            } else {
                System.out.println("Todos los usuarios registrados:");
                users.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error listando usuarios: " + e.getMessage());
        }
    }

    private static void showLoanMenu() {
        while (true) {
            try {
                System.out.println("\n=== GESTION DE PRESTAMOS ===");
                System.out.println("1. Prestar Libro");
                System.out.println("2. Devolver Libro");
                System.out.println("3. Ver Prestamos de Usuario");
                System.out.println("4. Ver Prestamos Activos");
                System.out.println("5. Ver Prestamos Vencidos");
                System.out.println("0. Volver al Menu Principal");
                System.out.print("Seleccione una opcion: ");
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
                    default: System.out.println("Opcion invalida. Por favor intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Por favor ingrese un numero valido.");
            }
        }
    }

    private static void loanBook() {
        try {
            System.out.print("Ingrese ID de Usuario: ");
            System.out.flush();
            String userId = scanner.nextLine();
            
            System.out.print("Ingrese ISBN del Libro: ");
            System.out.flush();
            String isbn = scanner.nextLine();

            Loan loan = libraryService.loanBook(userId, isbn);
            System.out.println("Libro prestado exitosamente: " + loan);
            
        } catch (InvalidUserException | BookNotFoundException | BookAlreadyLoanedException e) {
            System.err.println("Error procesando prestamo: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void returnBook() {
        try {
            System.out.print("Ingrese ID del Prestamo: ");
            System.out.flush();
            String loanId = scanner.nextLine();

            libraryService.returnBook(loanId);
            System.out.println("Libro devuelto exitosamente.");
            
        } catch (IllegalArgumentException e) {
            System.err.println("Error devolviendo libro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void viewUserLoans() {
        try {
            System.out.print("Ingrese ID de Usuario: ");
            System.out.flush();
            String userId = scanner.nextLine();
            
            List<Loan> loans = libraryService.getUserLoans(userId);
            if (loans.isEmpty()) {
                System.out.println("No se encontraron prestamos para el usuario: " + userId);
            } else {
                System.out.println("Prestamos para el usuario " + userId + ":");
                loans.forEach(System.out::println);
            }
            
        } catch (InvalidUserException e) {
            System.err.println("Error viendo prestamos de usuario: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void viewActiveLoans() {
        try {
            List<Loan> loans = libraryService.getActiveLoans();
            if (loans.isEmpty()) {
                System.out.println("No hay prestamos activos.");
            } else {
                System.out.println("Prestamos activos:");
                loans.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error viendo prestamos activos: " + e.getMessage());
        }
    }

    private static void viewOverdueLoans() {
        try {
            List<Loan> loans = libraryService.getOverdueLoans();
            if (loans.isEmpty()) {
                System.out.println("No hay prestamos vencidos.");
            } else {
                System.out.println("Prestamos vencidos:");
                loans.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Error viendo prestamos vencidos: " + e.getMessage());
        }
    }

    private static void showReportsMenu() {
        System.out.println("\n=== REPORTES ===");
        System.out.println("Libros Disponibles: " + libraryService.getAllBooks().stream().filter(Book::isAvailable).count());
        System.out.println("Libros Prestados: " + libraryService.getAllBooks().stream().filter(book -> !book.isAvailable()).count());
        System.out.println("Total de Usuarios: " + libraryService.getAllUsers().size());
        System.out.println("Prestamos Activos: " + libraryService.getActiveLoans().size());
        System.out.println("Prestamos Vencidos: " + libraryService.getOverdueLoans().size());
        System.out.flush();
    }

    private static void showFileMenu() {
        while (true) {
            try {
                System.out.println("\n=== OPERACIONES DE ARCHIVOS ===");
                System.out.println("1. Cargar Libros desde CSV");
                System.out.println("2. Cargar Usuarios desde CSV");
                System.out.println("3. Exportar Libros a CSV");
                System.out.println("4. Exportar Usuarios a CSV");
                System.out.println("5. Exportar Prestamos a CSV");
                System.out.println("0. Volver al Menu Principal");
                System.out.print("Seleccione una opcion: ");
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
                    default: System.out.println("Opcion invalida. Por favor intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Por favor ingrese un numero valido.");
            }
        }
    }

    private static void loadBooksFromCSV() {
        try {
            System.out.print("Ingrese nombre del archivo CSV: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            List<Book> books = CSVReader.readBooksFromCSV(filename);
            for (Book book : books) {
                try {
                    libraryService.addBook(book);
                } catch (IllegalArgumentException e) {
                    System.err.println("Omitiendo libro duplicado: " + book.getIsbn());
                }
            }
            System.out.println("Libros cargados exitosamente desde " + filename);
            
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Error de archivo: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error de formato de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void loadUsersFromCSV() {
        try {
            System.out.print("Ingrese nombre del archivo CSV: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            List<User> users = CSVReader.readUsersFromCSV(filename);
            for (User user : users) {
                try {
                    libraryService.addUser(user);
                } catch (InvalidUserException e) {
                    System.err.println("Omitiendo usuario duplicado: " + user.getId());
                }
            }
            System.out.println("Usuarios cargados exitosamente desde " + filename);
            
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Error de archivo: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error de E/S: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Error de formato de datos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void exportBooksToCSV() {
        try {
            System.out.print("Ingrese nombre del archivo de salida: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            FileWriter.writeBooksToCSV(libraryService.getAllBooks(), filename);
            System.out.println("Libros exportados exitosamente a " + filename);
            
        } catch (IOException e) {
            System.err.println("Error exportando libros: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void exportUsersToCSV() {
        try {
            System.out.print("Ingrese nombre del archivo de salida: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            FileWriter.writeUsersToCSV(new ArrayList<>(libraryService.getAllUsers()), filename);
            System.out.println("Usuarios exportados exitosamente a " + filename);
            
        } catch (IOException e) {
            System.err.println("Error exportando usuarios: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }

    private static void exportLoansToCSV() {
        try {
            System.out.print("Ingrese nombre del archivo de salida: ");
            System.out.flush();
            String filename = scanner.nextLine();
            
            FileWriter.writeLoansToCSV(libraryService.getActiveLoans(), filename);
            System.out.println("Prestamos exportados exitosamente a " + filename);
            
        } catch (IOException e) {
            System.err.println("Error exportando prestamos: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado: " + e.getMessage());
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.service;

import com.duoc.library.model.*;
import com.duoc.library.exception.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Servicio principal para la gestión de operaciones de biblioteca.
 * 
 * Esta clase centraliza toda la lógica de negocio del sistema de gestión
 * de biblioteca, incluyendo la administración de libros, usuarios y préstamos.
 * Actúa como fachada para las operaciones complejas y mantiene la integridad
 * de los datos a través de validaciones y manejo de excepciones.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class LibraryService {
    
    /** Colección de libros indexados por ISBN para búsqueda rápida */
    private final Map<String, Book> books;
    
    /** Colección de usuarios indexados por ID para búsqueda rápida */
    private final Map<String, User> users;
    
    /** Lista de todos los préstamos realizados */
    private final List<Loan> loans;
    
    /** Índice de préstamos activos por ISBN para verificación rápida */
    private final Map<String, Loan> activeLoansByIsbn;
    
    /** Índice de préstamos por usuario para consultas rápidas */
    private final Map<String, List<Loan>> loansByUser;

    /**
     * Constructor que inicializa el servicio con colecciones vacías.
     * Crea todas las estructuras de datos necesarias para el funcionamiento
     * eficiente del sistema de biblioteca.
     */
    public LibraryService() {
        this.books = new HashMap<>();
        this.users = new HashMap<>();
        this.loans = new ArrayList<>();
        this.activeLoansByIsbn = new HashMap<>();
        this.loansByUser = new HashMap<>();
    }

    // ================================
    // GESTIÓN DE LIBROS
    // ================================

    /**
     * Agrega un nuevo libro al sistema de biblioteca.
     * 
     * @param book el libro a agregar. No puede ser null y debe ser válido.
     * @throws IllegalArgumentException si el libro es null, inválido, o ya existe
     */
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("El libro no puede ser null");
        }
        if (!ValidationService.isValidBook(book)) {
            throw new IllegalArgumentException("Datos del libro invalidos");
        }
        if (books.containsKey(book.getIsbn())) {
            throw new IllegalArgumentException("Ya existe un libro con ISBN: " + book.getIsbn());
        }
        
        books.put(book.getIsbn(), book);
    }

    /**
     * Busca un libro por su ISBN.
     * 
     * @param isbn el ISBN del libro a buscar
     * @return el libro encontrado, o null si no existe
     * @throws IllegalArgumentException si el ISBN es null o vacío
     */
    public Book findBookByIsbn(String isbn) {
        if (!ValidationService.isNotNullOrEmpty(isbn)) {
            throw new IllegalArgumentException("ISBN no puede ser null o vacio");
        }
        return books.get(isbn.trim());
    }

    /**
     * Busca libros cuyo título contenga el texto especificado.
     * La búsqueda es case-insensitive y busca coincidencias parciales.
     * 
     * @param titleFragment fragmento del título a buscar
     * @return lista de libros que coinciden con la búsqueda
     * @throws IllegalArgumentException si el fragmento es null o vacío
     */
    public List<Book> searchBooksByTitle(String titleFragment) {
        if (!ValidationService.isNotNullOrEmpty(titleFragment)) {
            throw new IllegalArgumentException("Fragmento de titulo no puede ser null o vacio");
        }
        
        String searchTerm = titleFragment.trim().toLowerCase();
        return books.values().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Busca libros por autor.
     * La búsqueda es case-insensitive y busca coincidencias parciales.
     * 
     * @param authorFragment fragmento del nombre del autor a buscar
     * @return lista de libros del autor especificado
     * @throws IllegalArgumentException si el fragmento es null o vacío
     */
    public List<Book> searchBooksByAuthor(String authorFragment) {
        if (!ValidationService.isNotNullOrEmpty(authorFragment)) {
            throw new IllegalArgumentException("Fragmento de autor no puede ser null o vacio");
        }
        
        String searchTerm = authorFragment.trim().toLowerCase();
        return books.values().stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los libros en el sistema.
     * 
     * @return lista de todos los libros
     */
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    /**
     * Obtiene todos los libros disponibles para préstamo.
     * 
     * @return lista de libros disponibles
     */
    public List<Book> getAvailableBooks() {
        return books.values().stream()
                .filter(Book::isAvailable)
                .collect(Collectors.toList());
    }

    /**
     * Elimina un libro del sistema.
     * Solo se puede eliminar si no está prestado actualmente.
     * 
     * @param isbn ISBN del libro a eliminar
     * @return true si se eliminó correctamente, false si no existía
     * @throws BookAlreadyLoanedException si el libro está prestado actualmente
     */
    public boolean removeBook(String isbn) throws BookAlreadyLoanedException {
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            return false;
        }
        
        if (!book.isAvailable()) {
            throw new BookAlreadyLoanedException(isbn, "No se puede eliminar un libro que esta prestado");
        }
        
        books.remove(isbn);
        return true;
    }

    // ================================
    // GESTIÓN DE USUARIOS
    // ================================

    /**
     * Agrega un nuevo usuario al sistema.
     * 
     * @param user el usuario a agregar. No puede ser null y debe ser válido.
     * @throws InvalidUserException si el usuario es null, inválido, o ya existe
     */
    public void addUser(User user) throws InvalidUserException {
        if (user == null) {
            throw new InvalidUserException(null, InvalidUserException.ErrorType.INVALID_DATA, 
                                         "El usuario no puede ser null");
        }
        if (!ValidationService.isValidUser(user)) {
            throw new InvalidUserException(user.getId(), InvalidUserException.ErrorType.INVALID_DATA,
                                         "Datos del usuario invalidos");
        }
        if (users.containsKey(user.getId())) {
            throw new InvalidUserException(user.getId(), InvalidUserException.ErrorType.USER_ALREADY_EXISTS);
        }
        
        users.put(user.getId(), user);
        loansByUser.put(user.getId(), new ArrayList<>());
    }

    /**
     * Busca un usuario por su ID.
     * 
     * @param userId el ID del usuario a buscar
     * @return el usuario encontrado
     * @throws InvalidUserException si el usuario no existe o el ID es inválido
     */
    public User findUserById(String userId) throws InvalidUserException {
        if (!ValidationService.isValidUserId(userId)) {
            throw new InvalidUserException(userId, InvalidUserException.ErrorType.INVALID_DATA,
                                         "ID de usuario invalido");
        }
        
        User user = users.get(userId.trim());
        if (user == null) {
            throw new InvalidUserException(userId, InvalidUserException.ErrorType.USER_NOT_FOUND);
        }
        
        return user;
    }

    /**
     * Obtiene todos los usuarios del sistema.
     * 
     * @return colección de todos los usuarios
     */
    public Collection<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    /**
     * Busca usuarios por nombre.
     * La búsqueda es case-insensitive y busca coincidencias parciales.
     * 
     * @param nameFragment fragmento del nombre a buscar
     * @return lista de usuarios que coinciden con la búsqueda
     */
    public List<User> searchUsersByName(String nameFragment) {
        if (!ValidationService.isNotNullOrEmpty(nameFragment)) {
            return new ArrayList<>();
        }
        
        String searchTerm = nameFragment.trim().toLowerCase();
        return users.values().stream()
                .filter(user -> user.getName().toLowerCase().contains(searchTerm))
                .collect(Collectors.toList());
    }

    /**
     * Elimina un usuario del sistema.
     * Solo se puede eliminar si no tiene préstamos activos.
     * 
     * @param userId ID del usuario a eliminar
     * @return true si se eliminó correctamente, false si no existía
     * @throws InvalidUserException si el usuario tiene préstamos activos
     */
    public boolean removeUser(String userId) throws InvalidUserException {
        User user = users.get(userId);
        if (user == null) {
            return false;
        }
        
        List<Loan> userLoans = getUserActiveLoans(userId);
        if (!userLoans.isEmpty()) {
            throw new InvalidUserException(userId, InvalidUserException.ErrorType.USER_HAS_ACTIVE_LOANS);
        }
        
        users.remove(userId);
        loansByUser.remove(userId);
        return true;
    }

    // ================================
    // GESTIÓN DE PRÉSTAMOS
    // ================================

    /**
     * Procesa el préstamo de un libro a un usuario.
     * 
     * @param userId ID del usuario que solicita el préstamo
     * @param isbn ISBN del libro a prestar
     * @return el préstamo creado
     * @throws InvalidUserException si el usuario no existe o es inválido
     * @throws BookNotFoundException si el libro no existe
     * @throws BookAlreadyLoanedException si el libro ya está prestado
     */
    public Loan loanBook(String userId, String isbn) 
            throws InvalidUserException, BookNotFoundException, BookAlreadyLoanedException {
        
        // Validar usuario
        User user = findUserById(userId);
        
        // Validar libro
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        
        // Verificar disponibilidad
        if (!book.isAvailable()) {
            String currentBorrower = getCurrentBorrower(isbn);
            throw new BookAlreadyLoanedException(isbn, currentBorrower);
        }
        
        // Crear préstamo
        Loan loan = new Loan(user, book);
        
        // Actualizar estructuras de datos
        loans.add(loan);
        activeLoansByIsbn.put(isbn, loan);
        loansByUser.get(userId).add(loan);
        
        return loan;
    }

    /**
     * Procesa el préstamo de un libro con duración personalizada.
     * 
     * @param userId ID del usuario que solicita el préstamo
     * @param isbn ISBN del libro a prestar
     * @param loanDays duración del préstamo en días
     * @return el préstamo creado
     * @throws InvalidUserException si el usuario no existe o es inválido
     * @throws BookNotFoundException si el libro no existe
     * @throws BookAlreadyLoanedException si el libro ya está prestado
     * @throws IllegalArgumentException si los días de préstamo son inválidos
     */
    public Loan loanBook(String userId, String isbn, int loanDays) 
            throws InvalidUserException, BookNotFoundException, BookAlreadyLoanedException {
        
        if (!ValidationService.isValidLoanDays(loanDays)) {
            throw new IllegalArgumentException("Dias de prestamo invalidos: " + loanDays);
        }
        
        // Validar usuario
        User user = findUserById(userId);
        
        // Validar libro
        Book book = findBookByIsbn(isbn);
        if (book == null) {
            throw new BookNotFoundException(isbn);
        }
        
        // Verificar disponibilidad
        if (!book.isAvailable()) {
            String currentBorrower = getCurrentBorrower(isbn);
            throw new BookAlreadyLoanedException(isbn, currentBorrower);
        }
        
        // Crear préstamo con duración personalizada
        Loan loan = new Loan(user, book, loanDays);
        
        // Actualizar estructuras de datos
        loans.add(loan);
        activeLoansByIsbn.put(isbn, loan);
        loansByUser.get(userId).add(loan);
        
        return loan;
    }

    /**
     * Procesa la devolución de un libro.
     * 
     * @param loanId ID del préstamo a procesar
     * @throws IllegalArgumentException si el préstamo no existe o ya fue devuelto
     */
    public void returnBook(String loanId) {
        if (!ValidationService.isNotNullOrEmpty(loanId)) {
            throw new IllegalArgumentException("ID de prestamo no puede ser null o vacio");
        }
        
        Loan loan = findLoanById(loanId.trim());
        if (loan == null) {
            throw new IllegalArgumentException("Prestamo con ID '" + loanId + "' no encontrado");
        }
        
        if (!loan.isActive()) {
            throw new IllegalArgumentException("El prestamo ya fue devuelto");
        }
        
        // Procesar devolución
        loan.returnBook();
        activeLoansByIsbn.remove(loan.getBook().getIsbn());
    }

    /**
     * Busca un préstamo por su ID.
     * 
     * @param loanId ID del préstamo a buscar
     * @return el préstamo encontrado, o null si no existe
     */
    public Loan findLoanById(String loanId) {
        return loans.stream()
                .filter(loan -> loan.getLoanId().equals(loanId))
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtiene todos los préstamos de un usuario específico.
     * 
     * @param userId ID del usuario
     * @return lista de préstamos del usuario
     * @throws InvalidUserException si el usuario no existe
     */
    public List<Loan> getUserLoans(String userId) throws InvalidUserException {
        if (!users.containsKey(userId)) {
            throw new InvalidUserException(userId, InvalidUserException.ErrorType.USER_NOT_FOUND);
        }
        return new ArrayList<>(loansByUser.getOrDefault(userId, new ArrayList<>()));
    }

    /**
     * Obtiene todos los préstamos activos de un usuario específico.
     * 
     * @param userId ID del usuario
     * @return lista de préstamos activos del usuario
     */
    public List<Loan> getUserActiveLoans(String userId) {
        return loansByUser.getOrDefault(userId, new ArrayList<>()).stream()
                .filter(Loan::isActive)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los préstamos activos en el sistema.
     * 
     * @return lista de préstamos activos
     */
    public List<Loan> getActiveLoans() {
        return loans.stream()
                .filter(Loan::isActive)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los préstamos vencidos en el sistema.
     * 
     * @return lista de préstamos vencidos
     */
    public List<Loan> getOverdueLoans() {
        return loans.stream()
                .filter(Loan::isOverdue)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene todos los préstamos realizados en el sistema.
     * 
     * @return lista de todos los préstamos
     */
    public List<Loan> getAllLoans() {
        return new ArrayList<>(loans);
    }

    // ================================
    // MÉTODOS DE UTILIDAD PRIVADOS
    // ================================

    /**
     * Obtiene el ID del usuario que actualmente tiene prestado un libro.
     * 
     * @param isbn ISBN del libro
     * @return ID del usuario que tiene el libro, o null si está disponible
     */
    private String getCurrentBorrower(String isbn) {
        Loan activeLoan = activeLoansByIsbn.get(isbn);
        return activeLoan != null ? activeLoan.getUser().getId() : null;
    }

    // ================================
    // MÉTODOS DE ESTADÍSTICAS
    // ================================

    /**
     * Obtiene estadísticas generales del sistema.
     * 
     * @return mapa con estadísticas del sistema
     */
    public Map<String, Object> getSystemStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalBooks", books.size());
        stats.put("availableBooks", getAvailableBooks().size());
        stats.put("loanedBooks", books.size() - getAvailableBooks().size());
        stats.put("totalUsers", users.size());
        stats.put("totalLoans", loans.size());
        stats.put("activeLoans", getActiveLoans().size());
        stats.put("overdueLoans", getOverdueLoans().size());
        
        return stats;
    }
}
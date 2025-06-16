/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.exception;

/**
 * Excepción personalizada que se lanza cuando se intenta prestar un libro
 * que ya está prestado a otro usuario.
 * 
 * Esta excepción ayuda a prevenir préstamos duplicados y mantener la
 * integridad del estado de los libros en el sistema.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class BookAlreadyLoanedException extends Exception {
    
    /** Número de versión para la serialización */
    private static final long serialVersionUID = 1L;
    
    /** ISBN del libro que ya está prestado */
    private final String isbn;
    
    /** ID del usuario que actualmente tiene el libro */
    private final String currentBorrowerId;

    /**
     * Constructor que crea una nueva excepción con un mensaje por defecto.
     * 
     * @param isbn ISBN del libro que ya está prestado
     */
    public BookAlreadyLoanedException(String isbn) {
        super("El libro con ISBN '" + isbn + "' ya esta prestado");
        this.isbn = isbn;
        this.currentBorrowerId = null;
    }

    /**
     * Constructor que crea una nueva excepción especificando el usuario actual.
     * 
     * @param isbn ISBN del libro que ya está prestado
     * @param currentBorrowerId ID del usuario que actualmente tiene el libro
     */
    public BookAlreadyLoanedException(String isbn, String currentBorrowerId) {
        super("El libro con ISBN '" + isbn + "' ya esta prestado al usuario '" + currentBorrowerId + "'");
        this.isbn = isbn;
        this.currentBorrowerId = currentBorrowerId;
    }

    /**
     * Constructor que crea una nueva excepción con un mensaje personalizado.
     * 
     * @param isbn ISBN del libro que ya está prestado
     * @param currentBorrowerId ID del usuario que actualmente tiene el libro
     * @param message mensaje personalizado de la excepción
     */
    public BookAlreadyLoanedException(String isbn, String currentBorrowerId, String message) {
        super(message);
        this.isbn = isbn;
        this.currentBorrowerId = currentBorrowerId;
    }

    /**
     * Obtiene el ISBN del libro que ya está prestado.
     * 
     * @return el ISBN del libro prestado
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Obtiene el ID del usuario que actualmente tiene el libro prestado.
     * 
     * @return el ID del usuario actual, o null si no se especificó
     */
    public String getCurrentBorrowerId() {
        return currentBorrowerId;
    }
}
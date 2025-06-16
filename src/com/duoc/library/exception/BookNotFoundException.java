/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.exception;

/**
 * Excepción personalizada que se lanza cuando se intenta acceder a un libro
 * que no existe en el sistema de gestión de biblioteca.
 * 
 * Esta excepción se utiliza específicamente para operaciones relacionadas
 * con búsqueda y manipulación de libros que no se encuentran en la colección.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class BookNotFoundException extends Exception {
    
    /** Número de versión para la serialización */
    private static final long serialVersionUID = 1L;
    
    /** ISBN del libro que no fue encontrado */
    private final String isbn;

    /**
     * Constructor que crea una nueva excepción con un mensaje por defecto.
     * 
     * @param isbn ISBN del libro que no fue encontrado
     */
    public BookNotFoundException(String isbn) {
        super("Libro con ISBN '" + isbn + "' no encontrado en la biblioteca");
        this.isbn = isbn;
    }

    /**
     * Constructor que crea una nueva excepción con un mensaje personalizado.
     * 
     * @param isbn ISBN del libro que no fue encontrado
     * @param message mensaje personalizado de la excepción
     */
    public BookNotFoundException(String isbn, String message) {
        super(message);
        this.isbn = isbn;
    }

    /**
     * Constructor que crea una nueva excepción con un mensaje personalizado y una causa.
     * 
     * @param isbn ISBN del libro que no fue encontrado
     * @param message mensaje personalizado de la excepción
     * @param cause causa original de la excepción
     */
    public BookNotFoundException(String isbn, String message, Throwable cause) {
        super(message, cause);
        this.isbn = isbn;
    }

    /**
     * Obtiene el ISBN del libro que no fue encontrado.
     * 
     * @return el ISBN del libro no encontrado
     */
    public String getIsbn() {
        return isbn;
    }
}
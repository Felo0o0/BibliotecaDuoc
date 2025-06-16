/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.model;

/**
 * Representa un libro en el sistema de gestión de biblioteca.
 * 
 * Esta clase encapsula toda la información relacionada con un libro,
 * incluyendo su identificación única (ISBN), título, autor y estado de disponibilidad.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class Book {
    
    /** Identificador único del libro (ISBN) */
    private String isbn;
    
    /** Título del libro */
    private String title;
    
    /** Autor del libro */
    private String author;
    
    /** Estado de disponibilidad del libro (true = disponible, false = prestado) */
    private boolean available;

    /**
     * Constructor que crea un nuevo libro con los datos especificados.
     * El libro se crea con estado disponible por defecto.
     * 
     * @param isbn Identificador único del libro (ISBN). No puede ser null ni vacío.
     * @param title Título del libro. No puede ser null ni vacío.
     * @param author Autor del libro. No puede ser null ni vacío.
     * @throws IllegalArgumentException si algún parámetro es null o vacío
     */
    public Book(String isbn, String title, String author) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN no puede ser null o vacio");
        }
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Titulo no puede ser null o vacio");
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Autor no puede ser null o vacio");
        }
        
        this.isbn = isbn.trim();
        this.title = title.trim();
        this.author = author.trim();
        this.available = true; // Por defecto disponible
    }

    /**
     * Obtiene el ISBN del libro.
     * 
     * @return el ISBN del libro
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * Establece el ISBN del libro.
     * 
     * @param isbn el nuevo ISBN del libro. No puede ser null ni vacío.
     * @throws IllegalArgumentException si el ISBN es null o vacío
     */
    public void setIsbn(String isbn) {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN no puede ser null o vacio");
        }
        this.isbn = isbn.trim();
    }

    /**
     * Obtiene el título del libro.
     * 
     * @return el título del libro
     */
    public String getTitle() {
        return title;
    }

    /**
     * Establece el título del libro.
     * 
     * @param title el nuevo título del libro. No puede ser null ni vacío.
     * @throws IllegalArgumentException si el título es null o vacío
     */
    public void setTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Titulo no puede ser null o vacio");
        }
        this.title = title.trim();
    }

    /**
     * Obtiene el autor del libro.
     * 
     * @return el autor del libro
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Establece el autor del libro.
     * 
     * @param author el nuevo autor del libro. No puede ser null ni vacío.
     * @throws IllegalArgumentException si el autor es null o vacío
     */
    public void setAuthor(String author) {
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Autor no puede ser null o vacio");
        }
        this.author = author.trim();
    }

    /**
     * Verifica si el libro está disponible para préstamo.
     * 
     * @return true si el libro está disponible, false si está prestado
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Establece la disponibilidad del libro.
     * 
     * @param available true si el libro está disponible, false si está prestado
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * Compara este libro con otro objeto para verificar igualdad.
     * Dos libros son iguales si tienen el mismo ISBN.
     * 
     * @param obj el objeto a comparar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.equals(book.isbn);
    }

    /**
     * Genera el código hash para este libro basado en su ISBN.
     * 
     * @return el código hash del libro
     */
    @Override
    public int hashCode() {
        return isbn.hashCode();
    }

    /**
     * Genera una representación en string del libro.
     * 
     * @return string que contiene la información del libro
     */
    @Override
    public String toString() {
        return String.format("Book{ISBN='%s', titulo='%s', autor='%s', disponible=%s}", 
                           isbn, title, author, available ? "Si" : "No");
    }
}
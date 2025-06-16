/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Representa un préstamo de libro en el sistema de gestión de biblioteca.
 * 
 * Esta clase gestiona la información de préstamos incluyendo el usuario,
 * el libro prestado, fechas de préstamo y devolución, y el estado del préstamo.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class Loan {
    
    /** Duración por defecto del préstamo en días */
    public static final int DEFAULT_LOAN_DAYS = 14;
    
    /** Formateador para mostrar fechas */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /** Identificador único del préstamo */
    private String loanId;
    
    /** Usuario que realiza el préstamo */
    private User user;
    
    /** Libro prestado */
    private Book book;
    
    /** Fecha en que se realizó el préstamo */
    private LocalDate loanDate;
    
    /** Fecha límite para devolver el libro */
    private LocalDate dueDate;
    
    /** Fecha en que se devolvió el libro (null si no se ha devuelto) */
    private LocalDate returnDate;
    
    /** Estado del préstamo (true = activo, false = devuelto) */
    private boolean active;

    /**
     * Constructor que crea un nuevo préstamo con duración por defecto.
     * 
     * @param user Usuario que realiza el préstamo. No puede ser null.
     * @param book Libro a prestar. No puede ser null.
     * @throws IllegalArgumentException si algún parámetro es null
     */
    public Loan(User user, Book book) {
        this(user, book, DEFAULT_LOAN_DAYS);
    }

    /**
     * Constructor que crea un nuevo préstamo con duración personalizada.
     * 
     * @param user Usuario que realiza el préstamo. No puede ser null.
     * @param book Libro a prestar. No puede ser null.
     * @param loanDays Duración del préstamo en días. Debe ser mayor a 0.
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public Loan(User user, Book book, int loanDays) {
        if (user == null) {
            throw new IllegalArgumentException("Usuario no puede ser null");
        }
        if (book == null) {
            throw new IllegalArgumentException("Libro no puede ser null");
        }
        if (loanDays <= 0) {
            throw new IllegalArgumentException("Dias de prestamo debe ser mayor a 0");
        }
        
        this.loanId = generateLoanId();
        this.user = user;
        this.book = book;
        this.loanDate = LocalDate.now();
        this.dueDate = loanDate.plusDays(loanDays);
        this.returnDate = null;
        this.active = true;
        
        // Marcar el libro como no disponible
        book.setAvailable(false);
    }

    /**
     * Genera un ID único para el préstamo.
     * 
     * @return ID único generado
     */
    private String generateLoanId() {
        return "LOAN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    /**
     * Obtiene el ID del préstamo.
     * 
     * @return el ID del préstamo
     */
    public String getLoanId() {
        return loanId;
    }

    /**
     * Obtiene el usuario asociado al préstamo.
     * 
     * @return el usuario del préstamo
     */
    public User getUser() {
        return user;
    }

    /**
     * Obtiene el libro asociado al préstamo.
     * 
     * @return el libro del préstamo
     */
    public Book getBook() {
        return book;
    }

    /**
     * Obtiene la fecha del préstamo.
     * 
     * @return la fecha del préstamo
     */
    public LocalDate getLoanDate() {
        return loanDate;
    }

    /**
     * Obtiene la fecha límite de devolución.
     * 
     * @return la fecha límite de devolución
     */
    public LocalDate getDueDate() {
        return dueDate;
    }

    /**
     * Obtiene la fecha de devolución del libro.
     * 
     * @return la fecha de devolución, o null si no se ha devuelto
     */
    public LocalDate getReturnDate() {
        return returnDate;
    }

    /**
     * Verifica si el préstamo está activo.
     * 
     * @return true si el préstamo está activo, false si fue devuelto
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Verifica si el préstamo está vencido.
     * 
     * @return true si la fecha actual es posterior a la fecha límite y el préstamo está activo
     */
    public boolean isOverdue() {
        return active && LocalDate.now().isAfter(dueDate);
    }

    /**
     * Calcula los días de retraso en la devolución.
     * 
     * @return número de días de retraso, o 0 si no hay retraso
     */
    public long getDaysOverdue() {
        if (!isOverdue()) {
            return 0;
        }
        return LocalDate.now().toEpochDay() - dueDate.toEpochDay();
    }

    /**
     * Procesa la devolución del libro.
     * Marca el préstamo como inactivo y el libro como disponible.
     * 
     * @throws IllegalStateException si el préstamo ya fue devuelto
     */
    public void returnBook() {
        if (!active) {
            throw new IllegalStateException("El prestamo ya fue devuelto");
        }
        
        this.returnDate = LocalDate.now();
        this.active = false;
        this.book.setAvailable(true);
    }

    /**
     * Compara este préstamo con otro objeto para verificar igualdad.
     * Dos préstamos son iguales si tienen el mismo ID.
     * 
     * @param obj el objeto a comparar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Loan loan = (Loan) obj;
        return loanId.equals(loan.loanId);
    }

    /**
     * Genera el código hash para este préstamo basado en su ID.
     * 
     * @return el código hash del préstamo
     */
    @Override
    public int hashCode() {
        return loanId.hashCode();
    }

    /**
     * Genera una representación en string del préstamo.
     * 
     * @return string que contiene la información del préstamo
     */
    @Override
    public String toString() {
        String status = active ? (isOverdue() ? "VENCIDO" : "ACTIVO") : "DEVUELTO";
        String returnInfo = returnDate != null ? 
            ", devuelto=" + returnDate.format(DATE_FORMATTER) : "";
        
        return String.format("Loan{ID='%s', usuario='%s', libro='%s', " +
                           "prestamo=%s, vencimiento=%s, estado=%s%s}",
                           loanId, user.getName(), book.getTitle(),
                           loanDate.format(DATE_FORMATTER),
                           dueDate.format(DATE_FORMATTER),
                           status, returnInfo);
    }
}
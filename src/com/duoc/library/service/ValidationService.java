/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.service;

import com.duoc.library.model.Book;
import com.duoc.library.model.User;
import java.util.regex.Pattern;

/**
 * Servicio de validación centralizado para el sistema de gestión de biblioteca.
 * 
 * Esta clase proporciona métodos estáticos para validar datos de entrada
 * y asegurar la integridad de la información antes de procesarla.
 * Incluye validaciones para libros, usuarios, emails, ISBNs y otros datos.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class ValidationService {
    
    /** Patrón regex para validar formato de email */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );
    
    /** Patrón regex para validar formato de ISBN (10 o 13 dígitos con posibles guiones) */
    private static final Pattern ISBN_PATTERN = Pattern.compile(
        "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$"
    );
    
    /** Longitud mínima para nombres y títulos */
    private static final int MIN_NAME_LENGTH = 2;
    
    /** Longitud máxima para nombres y títulos */
    private static final int MAX_NAME_LENGTH = 100;
    
    /** Longitud mínima para IDs de usuario */
    private static final int MIN_ID_LENGTH = 3;

    /**
     * Constructor privado para prevenir instanciación.
     * Esta clase solo contiene métodos estáticos.
     */
    private ValidationService() {
        // Utility class - no instances allowed
    }

    /**
     * Valida si un string no es null y no está vacío después de trim.
     * 
     * @param value el string a validar
     * @return true si el string es válido, false en caso contrario
     */
    public static boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Valida si un string tiene una longitud dentro del rango especificado.
     * 
     * @param value el string a validar
     * @param minLength longitud mínima permitida
     * @param maxLength longitud máxima permitida
     * @return true si la longitud está en el rango, false en caso contrario
     */
    public static boolean isValidLength(String value, int minLength, int maxLength) {
        if (!isNotNullOrEmpty(value)) {
            return false;
        }
        int length = value.trim().length();
        return length >= minLength && length <= maxLength;
    }

    /**
     * Valida si un email tiene formato válido.
     * 
     * @param email el email a validar
     * @return true si el formato es válido, false en caso contrario
     */
    public static boolean isValidEmail(String email) {
        if (!isNotNullOrEmpty(email)) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Valida si un ISBN tiene formato válido.
     * 
     * @param isbn el ISBN a validar
     * @return true si el formato es válido, false en caso contrario
     */
    public static boolean isValidIsbn(String isbn) {
        if (!isNotNullOrEmpty(isbn)) {
            return false;
        }
        // Remover espacios y guiones para validación básica de longitud
        String cleanIsbn = isbn.replaceAll("[\\s-]", "");
        return cleanIsbn.length() == 10 || cleanIsbn.length() == 13;
    }

    /**
     * Valida si un nombre (usuario o autor) es válido.
     * 
     * @param name el nombre a validar
     * @return true si el nombre es válido, false en caso contrario
     */
    public static boolean isValidName(String name) {
        return isValidLength(name, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    /**
     * Valida si un título de libro es válido.
     * 
     * @param title el título a validar
     * @return true si el título es válido, false en caso contrario
     */
    public static boolean isValidTitle(String title) {
        return isValidLength(title, MIN_NAME_LENGTH, MAX_NAME_LENGTH);
    }

    /**
     * Valida si un ID de usuario es válido.
     * 
     * @param userId el ID de usuario a validar
     * @return true si el ID es válido, false en caso contrario
     */
    public static boolean isValidUserId(String userId) {
        return isValidLength(userId, MIN_ID_LENGTH, MAX_NAME_LENGTH);
    }

    /**
     * Valida si un nombre de archivo es válido.
     * 
     * @param fileName el nombre de archivo a validar
     * @return true si el nombre es válido, false en caso contrario
     */
    public static boolean isValidFileName(String fileName) {
        if (!isNotNullOrEmpty(fileName)) {
            return false;
        }
        // Verificar que no contenga caracteres ilegales para nombres de archivo
        String illegalChars = "<>:\"/\\|?*";
        for (char c : illegalChars.toCharArray()) {
            if (fileName.indexOf(c) != -1) {
                return false;
            }
        }
        return fileName.trim().length() > 0;
    }

    /**
     * Valida completamente un objeto Book.
     * 
     * @param book el libro a validar
     * @return true si el libro es válido, false en caso contrario
     */
    public static boolean isValidBook(Book book) {
        if (book == null) {
            return false;
        }
        return isValidIsbn(book.getIsbn()) &&
               isValidTitle(book.getTitle()) &&
               isValidName(book.getAuthor());
    }

    /**
     * Valida completamente un objeto User.
     * 
     * @param user el usuario a validar
     * @return true si el usuario es válido, false en caso contrario
     */
    public static boolean isValidUser(User user) {
        if (user == null) {
            return false;
        }
        return isValidUserId(user.getId()) &&
               isValidName(user.getName()) &&
               isValidEmail(user.getEmail());
    }

    /**
     * Valida si un número de días de préstamo es válido.
     * 
     * @param days número de días a validar
     * @return true si el número de días es válido, false en caso contrario
     */
    public static boolean isValidLoanDays(int days) {
        return days > 0 && days <= 365; // Entre 1 día y 1 año
    }

    /**
     * Sanitiza un string removiendo caracteres especiales y espacios extra.
     * 
     * @param input el string a sanitizar
     * @return el string sanitizado, o null si el input era null
     */
    public static String sanitizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replaceAll("\\s+", " ");
    }

    /**
     * Valida si una extensión de archivo es permitida para operaciones CSV.
     * 
     * @param fileName el nombre del archivo a validar
     * @return true si la extensión es válida, false en caso contrario
     */
    public static boolean isValidCsvFileName(String fileName) {
        if (!isValidFileName(fileName)) {
            return false;
        }
        return fileName.toLowerCase().endsWith(".csv");
    }
}
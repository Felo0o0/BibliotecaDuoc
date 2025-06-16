/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.util;

import com.duoc.library.model.Book;
import com.duoc.library.model.User;
import com.duoc.library.service.ValidationService;
import java.io.*;
import java.util.*;

/**
 * Utilidad especializada para la lectura de archivos CSV.
 * 
 * Esta clase proporciona métodos estáticos para leer y parsear archivos CSV
 * convirtiendo los datos en objetos del dominio de la biblioteca.
 * Maneja validaciones de formato y errores de parsing de manera robusta.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class CSVReader {
    
    /** Separador de campos CSV por defecto */
    private static final String CSV_SEPARATOR = ",";
    
    /** Encoding por defecto para lectura de archivos */
    private static final String DEFAULT_ENCODING = "UTF-8";
    
    /** Número esperado de campos para registros de libros */
    private static final int BOOK_FIELD_COUNT = 3;
    
    /** Número esperado de campos para registros de usuarios */
    private static final int USER_FIELD_COUNT = 3;

    /**
     * Constructor privado para prevenir instanciación.
     * Esta clase solo contiene métodos estáticos.
     */
    private CSVReader() {
        // Utility class - no instances allowed
    }

    /**
     * Lee libros desde un archivo CSV.
     * 
     * Formato esperado del archivo: ISBN,Titulo,Autor
     * - Las líneas vacías son ignoradas
     * - Las líneas que comienzan con # son tratadas como comentarios
     * - Los espacios en blanco al inicio y final de cada campo son removidos
     * 
     * @param fileName nombre del archivo CSV a leer
     * @return lista de libros leídos desde el archivo
     * @throws FileNotFoundException si el archivo no existe
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si el formato del archivo es inválido
     */
    public static List<Book> readBooksFromCSV(String fileName) 
            throws FileNotFoundException, IOException {
        
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
        
        List<Book> books = new ArrayList<>();
        int lineNumber = 0;
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), DEFAULT_ENCODING))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Saltar líneas vacías y comentarios
                if (isEmptyOrComment(line)) {
                    continue;
                }
                
                try {
                    Book book = parseBookLine(line, lineNumber);
                    if (book != null) {
                        books.add(book);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.printf("Error en linea %d: %s%n", lineNumber, e.getMessage());
                    // Continuar procesando otras líneas
                }
            }
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Archivo no encontrado: " + fileName);
        } catch (IOException e) {
            throw new IOException("Error leyendo archivo " + fileName + ": " + e.getMessage(), e);
        }
        
        return books;
    }

    /**
     * Lee usuarios desde un archivo CSV.
     * 
     * Formato esperado del archivo: ID,Nombre,Email
     * - Las líneas vacías son ignoradas
     * - Las líneas que comienzan con # son tratadas como comentarios
     * - Los espacios en blanco al inicio y final de cada campo son removidos
     * 
     * @param fileName nombre del archivo CSV a leer
     * @return lista de usuarios leídos desde el archivo
     * @throws FileNotFoundException si el archivo no existe
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si el formato del archivo es inválido
     */
    public static List<User> readUsersFromCSV(String fileName) 
            throws FileNotFoundException, IOException {
        
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
        
        List<User> users = new ArrayList<>();
        int lineNumber = 0;
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), DEFAULT_ENCODING))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                
                // Saltar líneas vacías y comentarios
                if (isEmptyOrComment(line)) {
                    continue;
                }
                
                try {
                    User user = parseUserLine(line, lineNumber);
                    if (user != null) {
                        users.add(user);
                    }
                } catch (IllegalArgumentException e) {
                    System.err.printf("Error en linea %d: %s%n", lineNumber, e.getMessage());
                    // Continuar procesando otras líneas
                }
            }
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Archivo no encontrado: " + fileName);
        } catch (IOException e) {
            throw new IOException("Error leyendo archivo " + fileName + ": " + e.getMessage(), e);
        }
        
        return users;
    }

    /**
     * Lee datos genéricos desde un archivo CSV.
     * 
     * @param fileName nombre del archivo CSV a leer
     * @return lista de arrays de strings, cada array representa una línea
     * @throws FileNotFoundException si el archivo no existe
     * @throws IOException si ocurre un error de E/S
     */
    public static List<String[]> readGenericCSV(String fileName) 
            throws FileNotFoundException, IOException {
        
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
        
        List<String[]> records = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), DEFAULT_ENCODING))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                // Saltar líneas vacías y comentarios
                if (isEmptyOrComment(line)) {
                    continue;
                }
                
                String[] fields = parseCsvLine(line);
                records.add(fields);
            }
            
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Archivo no encontrado: " + fileName);
        } catch (IOException e) {
            throw new IOException("Error leyendo archivo " + fileName + ": " + e.getMessage(), e);
        }
        
        return records;
    }

    // ================================
    // MÉTODOS DE PARSING PRIVADOS
    // ================================

    /**
     * Parsea una línea CSV para crear un objeto Book.
     * 
     * @param line línea CSV a parsear
     * @param lineNumber número de línea para reportes de error
     * @return objeto Book creado, o null si la línea es inválida
     * @throws IllegalArgumentException si el formato es inválido
     */
    private static Book parseBookLine(String line, int lineNumber) {
        String[] fields = parseCsvLine(line);
        
        if (fields.length != BOOK_FIELD_COUNT) {
            throw new IllegalArgumentException(
                String.format("Numero incorrecto de campos. Esperado %d, encontrado %d", 
                             BOOK_FIELD_COUNT, fields.length));
        }
        
        String isbn = fields[0].trim();
        String title = fields[1].trim();
        String author = fields[2].trim();
        
        // Validar campos obligatorios
        if (isbn.isEmpty() || title.isEmpty() || author.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios (ISBN, Titulo, Autor)");
        }
        
        try {
            return new Book(isbn, title, author);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Datos de libro invalidos: " + e.getMessage());
        }
    }

    /**
     * Parsea una línea CSV para crear un objeto User.
     * 
     * @param line línea CSV a parsear
     * @param lineNumber número de línea para reportes de error
     * @return objeto User creado, o null si la línea es inválida
     * @throws IllegalArgumentException si el formato es inválido
     */
    private static User parseUserLine(String line, int lineNumber) {
        String[] fields = parseCsvLine(line);
        
        if (fields.length != USER_FIELD_COUNT) {
            throw new IllegalArgumentException(
                String.format("Numero incorrecto de campos. Esperado %d, encontrado %d", 
                             USER_FIELD_COUNT, fields.length));
        }
        
        String id = fields[0].trim();
        String name = fields[1].trim();
        String email = fields[2].trim();
        
        // Validar campos obligatorios
        if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios (ID, Nombre, Email)");
        }
        
        try {
            return new User(id, name, email);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Datos de usuario invalidos: " + e.getMessage());
        }
    }

    /**
     * Parsea una línea CSV separando los campos.
     * 
     * Este método maneja comillas simples y dobles, y campos que contienen
     * el separador CSV dentro de comillas.
     * 
     * @param line línea CSV a parsear
     * @return array de campos parseados
     */
    private static String[] parseCsvLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return new String[0];
        }
        
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean inQuotes = false;
        char quoteChar = '"';
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"' || c == '\'') {
                if (!inQuotes) {
                    inQuotes = true;
                    quoteChar = c;
                } else if (c == quoteChar) {
                    // Verificar si es una comilla escapada
                    if (i + 1 < line.length() && line.charAt(i + 1) == quoteChar) {
                        currentField.append(c);
                        i++; // Saltar la siguiente comilla
                    } else {
                        inQuotes = false;
                    }
                } else {
                    currentField.append(c);
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(currentField.toString());
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }
        
        // Agregar el último campo
        fields.add(currentField.toString());
        
        return fields.toArray(new String[0]);
    }

    /**
     * Verifica si una línea está vacía o es un comentario.
     * 
     * @param line línea a verificar
     * @return true si la línea está vacía o es un comentario
     */
    private static boolean isEmptyOrComment(String line) {
        if (line == null) {
            return true;
        }
        
        String trimmed = line.trim();
        return trimmed.isEmpty() || trimmed.startsWith("#");
    }

    // ================================
    // MÉTODOS DE UTILIDAD
    // ================================

    /**
     * Cuenta el número de líneas de datos válidas en un archivo CSV.
     * (Excluye líneas vacías y comentarios)
     * 
     * @param fileName nombre del archivo a contar
     * @return número de líneas de datos válidas
     * @throws IOException si ocurre un error de E/S
     */
    public static int countDataLines(String fileName) throws IOException {
        int count = 0;
        
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), DEFAULT_ENCODING))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (!isEmptyOrComment(line)) {
                    count++;
                }
            }
        }
        
        return count;
    }

    /**
     * Verifica si un archivo CSV tiene el formato esperado para libros.
     * 
     * @param fileName nombre del archivo a verificar
     * @return true si el formato es válido para libros
     * @throws IOException si ocurre un error de E/S
     */
    public static boolean isValidBookCsvFormat(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), DEFAULT_ENCODING))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (!isEmptyOrComment(line)) {
                    String[] fields = parseCsvLine(line);
                    return fields.length == BOOK_FIELD_COUNT;
                }
            }
        }
        return false;
    }

    /**
     * Verifica si un archivo CSV tiene el formato esperado para usuarios.
     * 
     * @param fileName nombre del archivo a verificar
     * @return true si el formato es válido para usuarios
     * @throws IOException si ocurre un error de E/S
     */
    public static boolean isValidUserCsvFormat(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), DEFAULT_ENCODING))) {
            
            String line;
            while ((line = reader.readLine()) != null) {
                if (!isEmptyOrComment(line)) {
                    String[] fields = parseCsvLine(line);
                    return fields.length == USER_FIELD_COUNT;
                }
            }
        }
        return false;
    }
}
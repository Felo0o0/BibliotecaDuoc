/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.util;

import com.duoc.library.model.Book;
import com.duoc.library.model.User;
import com.duoc.library.model.Loan;
import com.duoc.library.service.ValidationService;
import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Utilidad especializada para la escritura de archivos CSV.
 * 
 * Esta clase proporciona métodos estáticos para escribir objetos del dominio
 * de la biblioteca en archivos CSV con formato estándar.
 * Maneja la serialización de datos y el formato de salida de manera consistente.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class FileWriter {
    
    /** Separador de campos CSV */
    private static final String CSV_SEPARATOR = ",";
    
    /** Separador de líneas */
    private static final String LINE_SEPARATOR = System.lineSeparator();
    
    /** Encoding por defecto para escritura de archivos */
    private static final String DEFAULT_ENCODING = "UTF-8";
    
    /** Formateador de fechas para archivos CSV */
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    /** Carácter de comilla para campos que contienen el separador */
    private static final String QUOTE_CHAR = "\"";

    /**
     * Constructor privado para prevenir instanciación.
     * Esta clase solo contiene métodos estáticos.
     */
    private FileWriter() {
        // Utility class - no instances allowed
    }

    /**
     * Escribe una lista de libros a un archivo CSV.
     * 
     * Formato de salida: ISBN,Titulo,Autor,Disponible
     * - Incluye encabezados en la primera línea
     * - Los campos que contienen comas son entrecomillados
     * - La disponibilidad se muestra como "Si" o "No"
     * 
     * @param books lista de libros a escribir
     * @param fileName nombre del archivo de destino
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public static void writeBooksToCSV(List<Book> books, String fileName) throws IOException {
        if (books == null) {
            throw new IllegalArgumentException("La lista de libros no puede ser null");
        }
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
        
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), DEFAULT_ENCODING))) {
            
            // Escribir encabezados
            writer.write("ISBN,Titulo,Autor,Disponible");
            writer.write(LINE_SEPARATOR);
            
            // Escribir datos de libros
            for (Book book : books) {
                if (book != null) {
                    writeBookRecord(writer, book);
                }
            }
            
        } catch (IOException e) {
            throw new IOException("Error escribiendo archivo de libros " + fileName + ": " + e.getMessage(), e);
        }
    }

    /**
     * Escribe una lista de usuarios a un archivo CSV.
     * 
     * Formato de salida: ID,Nombre,Email
     * - Incluye encabezados en la primera línea
     * - Los campos que contienen comas son entrecomillados
     * 
     * @param users lista de usuarios a escribir
     * @param fileName nombre del archivo de destino
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public static void writeUsersToCSV(List<User> users, String fileName) throws IOException {
        if (users == null) {
            throw new IllegalArgumentException("La lista de usuarios no puede ser null");
        }
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
        
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), DEFAULT_ENCODING))) {
            
            // Escribir encabezados
            writer.write("ID,Nombre,Email");
            writer.write(LINE_SEPARATOR);
            
            // Escribir datos de usuarios
            for (User user : users) {
                if (user != null) {
                    writeUserRecord(writer, user);
                }
            }
            
        } catch (IOException e) {
            throw new IOException("Error escribiendo archivo de usuarios " + fileName + ": " + e.getMessage(), e);
        }
    }

    /**
     * Escribe una lista de préstamos a un archivo CSV.
     * 
     * Formato de salida: ID_Prestamo,Usuario_ID,Usuario_Nombre,ISBN,Titulo,Fecha_Prestamo,Fecha_Vencimiento,Fecha_Devolucion,Estado
     * - Incluye encabezados en la primera línea
     * - Las fechas se formatean como dd/MM/yyyy
     * - El estado puede ser "ACTIVO", "DEVUELTO", o "VENCIDO"
     * 
     * @param loans lista de préstamos a escribir
     * @param fileName nombre del archivo de destino
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public static void writeLoansToCSV(List<Loan> loans, String fileName) throws IOException {
        if (loans == null) {
            throw new IllegalArgumentException("La lista de prestamos no puede ser null");
        }
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
        
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), DEFAULT_ENCODING))) {
            
            // Escribir encabezados
            writer.write("ID_Prestamo,Usuario_ID,Usuario_Nombre,ISBN,Titulo,Fecha_Prestamo,Fecha_Vencimiento,Fecha_Devolucion,Estado");
            writer.write(LINE_SEPARATOR);
            
            // Escribir datos de préstamos
            for (Loan loan : loans) {
                if (loan != null) {
                    writeLoanRecord(writer, loan);
                }
            }
            
        } catch (IOException e) {
            throw new IOException("Error escribiendo archivo de prestamos " + fileName + ": " + e.getMessage(), e);
        }
    }

    /**
     * Escribe datos genéricos a un archivo CSV.
     * 
     * @param data lista de arrays de strings representando filas
     * @param headers array de encabezados (opcional, puede ser null)
     * @param fileName nombre del archivo de destino
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si los parámetros son inválidos
     */
    public static void writeGenericCSV(List<String[]> data, String[] headers, String fileName) 
            throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("Los datos no pueden ser null");
        }
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
        
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), DEFAULT_ENCODING))) {
            
            // Escribir encabezados si se proporcionan
            if (headers != null && headers.length > 0) {
                writer.write(formatCsvLine(headers));
                writer.write(LINE_SEPARATOR);
            }
            
            // Escribir datos
            for (String[] row : data) {
                if (row != null && row.length > 0) {
                    writer.write(formatCsvLine(row));
                    writer.write(LINE_SEPARATOR);
                }
            }
            
        } catch (IOException e) {
            throw new IOException("Error escribiendo archivo generico " + fileName + ": " + e.getMessage(), e);
        }
    }

    // ================================
    // MÉTODOS DE ESCRITURA PRIVADOS
    // ================================

    /**
     * Escribe un registro de libro al BufferedWriter.
     * 
     * @param writer escritor donde escribir el registro
     * @param book libro a escribir
     * @throws IOException si ocurre un error de E/S
     */
    private static void writeBookRecord(BufferedWriter writer, Book book) throws IOException {
        String[] fields = {
            book.getIsbn(),
            book.getTitle(),
            book.getAuthor(),
            book.isAvailable() ? "Si" : "No"
        };
        
        writer.write(formatCsvLine(fields));
        writer.write(LINE_SEPARATOR);
    }

    /**
     * Escribe un registro de usuario al BufferedWriter.
     * 
     * @param writer escritor donde escribir el registro
     * @param user usuario a escribir
     * @throws IOException si ocurre un error de E/S
     */
    private static void writeUserRecord(BufferedWriter writer, User user) throws IOException {
        String[] fields = {
            user.getId(),
            user.getName(),
            user.getEmail()
        };
        
        writer.write(formatCsvLine(fields));
        writer.write(LINE_SEPARATOR);
    }

    /**
     * Escribe un registro de préstamo al BufferedWriter.
     * 
     * @param writer escritor donde escribir el registro
     * @param loan préstamo a escribir
     * @throws IOException si ocurre un error de E/S
     */
    private static void writeLoanRecord(BufferedWriter writer, Loan loan) throws IOException {
        String status;
        if (!loan.isActive()) {
            status = "DEVUELTO";
        } else if (loan.isOverdue()) {
            status = "VENCIDO";
        } else {
            status = "ACTIVO";
        }
        
        String returnDateStr = loan.getReturnDate() != null ? 
            loan.getReturnDate().format(DATE_FORMATTER) : "";
        
        String[] fields = {
            loan.getLoanId(),
            loan.getUser().getId(),
            loan.getUser().getName(),
            loan.getBook().getIsbn(),
            loan.getBook().getTitle(),
            loan.getLoanDate().format(DATE_FORMATTER),
            loan.getDueDate().format(DATE_FORMATTER),
            returnDateStr,
            status
        };
        
        writer.write(formatCsvLine(fields));
        writer.write(LINE_SEPARATOR);
    }

    // ================================
    // MÉTODOS DE UTILIDAD
    // ================================

    /**
     * Formatea una línea CSV a partir de un array de campos.
     * 
     * Los campos que contienen comas, comillas o saltos de línea
     * son automáticamente entrecomillados.
     * 
     * @param fields array de campos a formatear
     * @return línea CSV formateada
     */
    private static String formatCsvLine(String[] fields) {
        if (fields == null || fields.length == 0) {
            return "";
        }
        
        StringBuilder line = new StringBuilder();
        
        for (int i = 0; i < fields.length; i++) {
            if (i > 0) {
                line.append(CSV_SEPARATOR);
            }
            
            String field = fields[i] != null ? fields[i] : "";
            line.append(escapeCsvField(field));
        }
        
        return line.toString();
    }

    /**
     * Escapa un campo CSV agregando comillas si es necesario.
     * 
     * @param field campo a escapar
     * @return campo escapado
     */
    private static String escapeCsvField(String field) {
        if (field == null) {
            return "";
        }
        
        // Verificar si el campo necesita ser entrecomillado
        boolean needsQuoting = field.contains(CSV_SEPARATOR) || 
                              field.contains(QUOTE_CHAR) || 
                              field.contains("\n") || 
                              field.contains("\r");
        
        if (needsQuoting) {
            // Escapar comillas duplicándolas
            String escaped = field.replace(QUOTE_CHAR, QUOTE_CHAR + QUOTE_CHAR);
            return QUOTE_CHAR + escaped + QUOTE_CHAR;
        }
        
        return field;
    }

    /**
     * Agrega datos a un archivo CSV existente (append mode).
     * 
     * @param data lista de arrays de strings representando filas
     * @param fileName nombre del archivo de destino
     * @throws IOException si ocurre un error de E/S
     */
    public static void appendToCSV(List<String[]> data, String fileName) throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("Los datos no pueden ser null");
        }
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
        
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(fileName, true), DEFAULT_ENCODING))) {
            
            for (String[] row : data) {
                if (row != null && row.length > 0) {
                    writer.write(formatCsvLine(row));
                    writer.write(LINE_SEPARATOR);
                }
            }
            
        } catch (IOException e) {
            throw new IOException("Error agregando datos al archivo " + fileName + ": " + e.getMessage(), e);
        }
    }

    /**
     * Verifica si un archivo existe y es escribible.
     * 
     * @param fileName nombre del archivo a verificar
     * @return true si el archivo es escribible o puede ser creado
     */
    public static boolean isWritable(String fileName) {
        try {
            File file = new File(fileName);
            if (file.exists()) {
                return file.canWrite();
            } else {
                // Verificar si el directorio padre existe y es escribible
                File parent = file.getParentFile();
                return parent == null || parent.canWrite();
            }
        } catch (Exception e) {
            return false;
        }
    }
}
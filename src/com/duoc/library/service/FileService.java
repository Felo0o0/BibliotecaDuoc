/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.service;

import com.duoc.library.model.*;
import com.duoc.library.util.*;
import com.duoc.library.exception.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Servicio especializado para operaciones de gestión de archivos en el sistema de biblioteca.
 * 
 * Esta clase proporciona una interfaz de alto nivel para operaciones de importación
 * y exportación de datos hacia y desde archivos CSV. Actúa como intermediario
 * entre el servicio principal de biblioteca y las utilidades de manejo de archivos.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class FileService {
    
    /** Servicio principal de biblioteca para operaciones de datos */
    private final LibraryService libraryService;

    /**
     * Constructor que inicializa el servicio con una instancia de LibraryService.
     * 
     * @param libraryService servicio principal de biblioteca a utilizar
     * @throws IllegalArgumentException si libraryService es null
     */
    public FileService(LibraryService libraryService) {
        if (libraryService == null) {
            throw new IllegalArgumentException("LibraryService no puede ser null");
        }
        this.libraryService = libraryService;
    }

    // ================================
    // OPERACIONES DE IMPORTACIÓN
    // ================================

    /**
     * Carga libros desde un archivo CSV al sistema de biblioteca.
     * 
     * Los libros duplicados (mismo ISBN) son omitidos automáticamente.
     * El archivo debe tener el formato: ISBN,Titulo,Autor
     * 
     * @param fileName nombre del archivo CSV a cargar
     * @return resultado de la operación con estadísticas
     * @throws FileNotFoundException si el archivo no existe
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si el nombre del archivo es inválido
     */
    public ImportResult loadBooksFromCSV(String fileName) 
            throws FileNotFoundException, IOException {
        
        validateFileName(fileName);
        
        try {
            List<Book> books = CSVReader.readBooksFromCSV(fileName);
            return processBooksImport(books, fileName);
            
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(fileName, e.getMessage());
        } catch (IOException e) {
            throw new IOException("Error leyendo archivo de libros: " + e.getMessage(), e);
        }
    }

    /**
     * Carga usuarios desde un archivo CSV al sistema de biblioteca.
     * 
     * Los usuarios duplicados (mismo ID) son omitidos automáticamente.
     * El archivo debe tener el formato: ID,Nombre,Email
     * 
     * @param fileName nombre del archivo CSV a cargar
     * @return resultado de la operación con estadísticas
     * @throws FileNotFoundException si el archivo no existe
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si el nombre del archivo es inválido
     */
    public ImportResult loadUsersFromCSV(String fileName) 
            throws FileNotFoundException, IOException {
        
        validateFileName(fileName);
        
        try {
            List<User> users = CSVReader.readUsersFromCSV(fileName);
            return processUsersImport(users, fileName);
            
        } catch (java.io.FileNotFoundException e) {
            throw new FileNotFoundException(fileName, e.getMessage());
        } catch (IOException e) {
            throw new IOException("Error leyendo archivo de usuarios: " + e.getMessage(), e);
        }
    }

    // ================================
    // OPERACIONES DE EXPORTACIÓN
    // ================================

    /**
     * Exporta todos los libros del sistema a un archivo CSV.
     * 
     * @param fileName nombre del archivo de destino
     * @return resultado de la operación con estadísticas
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si el nombre del archivo es inválido
     */
    public ExportResult exportBooksToCSV(String fileName) throws IOException {
        validateFileName(fileName);
        
        try {
            List<Book> books = libraryService.getAllBooks();
            FileWriter.writeBooksToCSV(books, fileName);
            
            return new ExportResult(fileName, books.size(), "Libros exportados exitosamente");
            
        } catch (IOException e) {
            throw new IOException("Error escribiendo archivo de libros: " + e.getMessage(), e);
        }
    }

    /**
     * Exporta todos los usuarios del sistema a un archivo CSV.
     * 
     * @param fileName nombre del archivo de destino
     * @return resultado de la operación con estadísticas
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si el nombre del archivo es inválido
     */
    public ExportResult exportUsersToCSV(String fileName) throws IOException {
        validateFileName(fileName);
        
        try {
            List<User> users = List.copyOf(libraryService.getAllUsers());
            FileWriter.writeUsersToCSV(users, fileName);
            
            return new ExportResult(fileName, users.size(), "Usuarios exportados exitosamente");
            
        } catch (IOException e) {
            throw new IOException("Error escribiendo archivo de usuarios: " + e.getMessage(), e);
        }
    }

    /**
     * Exporta todos los préstamos activos del sistema a un archivo CSV.
     * 
     * @param fileName nombre del archivo de destino
     * @return resultado de la operación con estadísticas
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si el nombre del archivo es inválido
     */
    public ExportResult exportActiveLoansToCSV(String fileName) throws IOException {
        validateFileName(fileName);
        
        try {
            List<Loan> loans = libraryService.getActiveLoans();
            FileWriter.writeLoansToCSV(loans, fileName);
            
            return new ExportResult(fileName, loans.size(), "Prestamos activos exportados exitosamente");
            
        } catch (IOException e) {
            throw new IOException("Error escribiendo archivo de prestamos: " + e.getMessage(), e);
        }
    }

    /**
     * Exporta todos los préstamos (activos e históricos) del sistema a un archivo CSV.
     * 
     * @param fileName nombre del archivo de destino
     * @return resultado de la operación con estadísticas
     * @throws IOException si ocurre un error de E/S
     * @throws IllegalArgumentException si el nombre del archivo es inválido
     */
    public ExportResult exportAllLoansToCSV(String fileName) throws IOException {
        validateFileName(fileName);
        
        try {
            List<Loan> loans = libraryService.getAllLoans();
            FileWriter.writeLoansToCSV(loans, fileName);
            
            return new ExportResult(fileName, loans.size(), "Todos los prestamos exportados exitosamente");
            
        } catch (IOException e) {
            throw new IOException("Error escribiendo archivo de prestamos: " + e.getMessage(), e);
        }
    }

    // ================================
    // MÉTODOS DE UTILIDAD PRIVADOS
    // ================================

    /**
     * Valida que el nombre del archivo sea válido para operaciones CSV.
     * 
     * @param fileName nombre del archivo a validar
     * @throws IllegalArgumentException si el nombre es inválido
     */
    private void validateFileName(String fileName) {
        if (!ValidationService.isValidCsvFileName(fileName)) {
            throw new IllegalArgumentException("Nombre de archivo CSV invalido: " + fileName);
        }
    }

    /**
     * Procesa la importación de libros y genera estadísticas.
     * 
     * @param books lista de libros a importar
     * @param fileName nombre del archivo origen
     * @return resultado de la importación
     */
    private ImportResult processBooksImport(List<Book> books, String fileName) {
        int imported = 0;
        int duplicates = 0;
        int errors = 0;
        
        for (Book book : books) {
            try {
                libraryService.addBook(book);
                imported++;
            } catch (IllegalArgumentException e) {
                if (e.getMessage().contains("Ya existe")) {
                    duplicates++;
                } else {
                    errors++;
                }
            }
        }
        
        return new ImportResult(fileName, books.size(), imported, duplicates, errors, "Libros");
    }

    /**
     * Procesa la importación de usuarios y genera estadísticas.
     * 
     * @param users lista de usuarios a importar
     * @param fileName nombre del archivo origen
     * @return resultado de la importación
     */
    private ImportResult processUsersImport(List<User> users, String fileName) {
        int imported = 0;
        int duplicates = 0;
        int errors = 0;
        
        for (User user : users) {
            try {
                libraryService.addUser(user);
                imported++;
            } catch (Exception e) {
                if (e.getMessage().contains("ya existe")) {
                    duplicates++;
                } else {
                    errors++;
                }
            }
        }
        
        return new ImportResult(fileName, users.size(), imported, duplicates, errors, "Usuarios");
    }

    // ================================
    // CLASES DE RESULTADO
    // ================================

    /**
     * Representa el resultado de una operación de importación.
     */
    public static class ImportResult {
        private final String fileName;
        private final int totalRecords;
        private final int importedRecords;
        private final int duplicateRecords;
        private final int errorRecords;
        private final String dataType;

        /**
         * Constructor para crear un resultado de importación.
         * 
         * @param fileName nombre del archivo procesado
         * @param totalRecords total de registros en el archivo
         * @param importedRecords registros importados exitosamente
         * @param duplicateRecords registros duplicados omitidos
         * @param errorRecords registros con errores
         * @param dataType tipo de datos procesados
         */
        public ImportResult(String fileName, int totalRecords, int importedRecords, 
                           int duplicateRecords, int errorRecords, String dataType) {
            this.fileName = fileName;
            this.totalRecords = totalRecords;
            this.importedRecords = importedRecords;
            this.duplicateRecords = duplicateRecords;
            this.errorRecords = errorRecords;
            this.dataType = dataType;
        }

        // Getters
        public String getFileName() { return fileName; }
        public int getTotalRecords() { return totalRecords; }
        public int getImportedRecords() { return importedRecords; }
        public int getDuplicateRecords() { return duplicateRecords; }
        public int getErrorRecords() { return errorRecords; }
        public String getDataType() { return dataType; }

        /**
         * Verifica si la importación fue completamente exitosa.
         * 
         * @return true si todos los registros fueron importados sin errores
         */
        public boolean isCompleteSuccess() {
            return importedRecords == totalRecords && errorRecords == 0;
        }

        @Override
        public String toString() {
            return String.format("%s importados desde '%s': %d/%d exitosos, %d duplicados, %d errores",
                    dataType, fileName, importedRecords, totalRecords, duplicateRecords, errorRecords);
        }
    }

    /**
     * Representa el resultado de una operación de exportación.
     */
    public static class ExportResult {
        private final String fileName;
        private final int exportedRecords;
        private final String message;

        /**
         * Constructor para crear un resultado de exportación.
         * 
         * @param fileName nombre del archivo generado
         * @param exportedRecords número de registros exportados
         * @param message mensaje descriptivo de la operación
         */
        public ExportResult(String fileName, int exportedRecords, String message) {
            this.fileName = fileName;
            this.exportedRecords = exportedRecords;
            this.message = message;
        }

        // Getters
        public String getFileName() { return fileName; }
        public int getExportedRecords() { return exportedRecords; }
        public String getMessage() { return message; }

        @Override
        public String toString() {
            return String.format("%s: %d registros exportados a '%s'", 
                    message, exportedRecords, fileName);
        }
    }
}
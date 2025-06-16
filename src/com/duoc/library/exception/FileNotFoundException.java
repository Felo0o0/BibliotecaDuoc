/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.exception;

/**
 * Excepción personalizada que se lanza cuando se intenta acceder a un archivo
 * que no existe en el sistema de gestión de biblioteca.
 * 
 * Esta excepción se utiliza específicamente para operaciones de E/S relacionadas
 * con archivos CSV, reportes y exportaciones de datos.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class FileNotFoundException extends Exception {
    
    /** Número de versión para la serialización */
    private static final long serialVersionUID = 1L;
    
    /** Nombre del archivo que no fue encontrado */
    private final String fileName;
    
    /** Ruta completa del archivo, si está disponible */
    private final String filePath;

    /**
     * Constructor que crea una nueva excepción con un mensaje por defecto.
     * 
     * @param fileName nombre del archivo que no fue encontrado
     */
    public FileNotFoundException(String fileName) {
        super("Archivo '" + fileName + "' no encontrado");
        this.fileName = fileName;
        this.filePath = null;
    }

    /**
     * Constructor que crea una nueva excepción especificando la ruta completa.
     * 
     * @param fileName nombre del archivo que no fue encontrado
     * @param filePath ruta completa del archivo
     */
    public FileNotFoundException(String fileName, String filePath) {
        super("Archivo '" + fileName + "' no encontrado en la ruta: " + filePath);
        this.fileName = fileName;
        this.filePath = filePath;
    }

    /**
     * Constructor que crea una nueva excepción con un mensaje personalizado.
     * 
     * @param fileName nombre del archivo que no fue encontrado
     * @param filePath ruta completa del archivo
     * @param message mensaje personalizado de la excepción
     */
    public FileNotFoundException(String fileName, String filePath, String message) {
        super(message);
        this.fileName = fileName;
        this.filePath = filePath;
    }

    /**
     * Constructor que crea una nueva excepción con mensaje y causa.
     * 
     * @param fileName nombre del archivo que no fue encontrado
     * @param filePath ruta completa del archivo
     * @param message mensaje personalizado de la excepción
     * @param cause causa original de la excepción
     */
    public FileNotFoundException(String fileName, String filePath, String message, Throwable cause) {
        super(message, cause);
        this.fileName = fileName;
        this.filePath = filePath;
    }

    /**
     * Obtiene el nombre del archivo que no fue encontrado.
     * 
     * @return el nombre del archivo
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Obtiene la ruta completa del archivo que no fue encontrado.
     * 
     * @return la ruta del archivo, o null si no se especificó
     */
    public String getFilePath() {
        return filePath;
    }
}
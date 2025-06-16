/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.exception;

/**
 * Excepción personalizada que se lanza cuando se detectan problemas
 * relacionados con usuarios en el sistema de gestión de biblioteca.
 * 
 * Esta excepción puede indicar usuarios no encontrados, datos de usuario
 * inválidos, o violaciones de reglas de negocio relacionadas con usuarios.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class InvalidUserException extends Exception {
    
    /** Número de versión para la serialización */
    private static final long serialVersionUID = 1L;
    
    /** ID del usuario que causó la excepción */
    private final String userId;
    
    /** Tipo de error específico */
    private final ErrorType errorType;

    /**
     * Enumeración que define los tipos de errores de usuario posibles.
     */
    public enum ErrorType {
        /** Usuario no encontrado en el sistema */
        USER_NOT_FOUND,
        /** Datos de usuario inválidos */
        INVALID_DATA,
        /** Usuario ya existe en el sistema */
        USER_ALREADY_EXISTS,
        /** Usuario tiene préstamos activos */
        USER_HAS_ACTIVE_LOANS,
        /** Formato de email inválido */
        INVALID_EMAIL_FORMAT
    }

    /**
     * Constructor que crea una nueva excepción con un mensaje por defecto.
     * 
     * @param userId ID del usuario que causó la excepción
     * @param errorType tipo de error específico
     */
    public InvalidUserException(String userId, ErrorType errorType) {
        super(generateMessage(userId, errorType));
        this.userId = userId;
        this.errorType = errorType;
    }

    /**
     * Constructor que crea una nueva excepción con un mensaje personalizado.
     * 
     * @param userId ID del usuario que causó la excepción
     * @param errorType tipo de error específico
     * @param message mensaje personalizado de la excepción
     */
    public InvalidUserException(String userId, ErrorType errorType, String message) {
        super(message);
        this.userId = userId;
        this.errorType = errorType;
    }

    /**
     * Constructor que crea una nueva excepción con mensaje y causa.
     * 
     * @param userId ID del usuario que causó la excepción
     * @param errorType tipo de error específico
     * @param message mensaje personalizado de la excepción
     * @param cause causa original de la excepción
     */
    public InvalidUserException(String userId, ErrorType errorType, String message, Throwable cause) {
        super(message, cause);
        this.userId = userId;
        this.errorType = errorType;
    }

    /**
     * Genera un mensaje de error apropiado basado en el tipo de error.
     * 
     * @param userId ID del usuario
     * @param errorType tipo de error
     * @return mensaje de error generado
     */
    private static String generateMessage(String userId, ErrorType errorType) {
        switch (errorType) {
            case USER_NOT_FOUND:
                return "Usuario con ID '" + userId + "' no encontrado";
            case INVALID_DATA:
                return "Datos invalidos para el usuario '" + userId + "'";
            case USER_ALREADY_EXISTS:
                return "El usuario con ID '" + userId + "' ya existe";
            case USER_HAS_ACTIVE_LOANS:
                return "El usuario '" + userId + "' tiene prestamos activos";
            case INVALID_EMAIL_FORMAT:
                return "Formato de email invalido para el usuario '" + userId + "'";
            default:
                return "Error de usuario para '" + userId + "'";
        }
    }

    /**
     * Obtiene el ID del usuario que causó la excepción.
     * 
     * @return el ID del usuario
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Obtiene el tipo de error específico.
     * 
     * @return el tipo de error
     */
    public ErrorType getErrorType() {
        return errorType;
    }
}
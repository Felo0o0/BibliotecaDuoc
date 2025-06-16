/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.model;

import java.util.regex.Pattern;

/**
 * Representa un usuario del sistema de gestión de biblioteca.
 * 
 * Esta clase encapsula toda la información relacionada con un usuario,
 * incluyendo su identificación única, nombre completo y correo electrónico.
 * Incluye validaciones para asegurar la integridad de los datos.
 * 
 * @author Sistema de Gestión de Biblioteca
 * @version 1.0
 * @since 1.0
 */
public class User {
    
    /** Patrón regex para validar formato de email */
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );
    
    /** Identificador único del usuario */
    private String id;
    
    /** Nombre completo del usuario */
    private String name;
    
    /** Correo electrónico del usuario */
    private String email;

    /**
     * Constructor que crea un nuevo usuario con los datos especificados.
     * 
     * @param id Identificador único del usuario. No puede ser null ni vacío.
     * @param name Nombre completo del usuario. No puede ser null ni vacío.
     * @param email Correo electrónico del usuario. Debe tener formato válido.
     * @throws IllegalArgumentException si algún parámetro es inválido
     */
    public User(String id, String name, String email) {
        setId(id);
        setName(name);
        setEmail(email);
    }

    /**
     * Obtiene el ID del usuario.
     * 
     * @return el ID del usuario
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el ID del usuario.
     * 
     * @param id el nuevo ID del usuario. No puede ser null ni vacío.
     * @throws IllegalArgumentException si el ID es null o vacío
     */
    public void setId(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("ID de usuario no puede ser null o vacio");
        }
        this.id = id.trim();
    }

    /**
     * Obtiene el nombre del usuario.
     * 
     * @return el nombre del usuario
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre del usuario.
     * 
     * @param name el nuevo nombre del usuario. No puede ser null ni vacío.
     * @throws IllegalArgumentException si el nombre es null o vacío
     */
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede ser null o vacio");
        }
        this.name = name.trim();
    }

    /**
     * Obtiene el email del usuario.
     * 
     * @return el email del usuario
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el email del usuario.
     * 
     * @param email el nuevo email del usuario. Debe tener formato válido.
     * @throws IllegalArgumentException si el email tiene formato inválido
     */
    public void setEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email no puede ser null o vacio");
        }
        if (!isValidEmail(email.trim())) {
            throw new IllegalArgumentException("Formato de email invalido: " + email);
        }
        this.email = email.trim().toLowerCase();
    }

    /**
     * Valida si un string tiene formato de email válido.
     * 
     * @param email el string a validar
     * @return true si el formato es válido, false en caso contrario
     */
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Compara este usuario con otro objeto para verificar igualdad.
     * Dos usuarios son iguales si tienen el mismo ID.
     * 
     * @param obj el objeto a comparar
     * @return true si los objetos son iguales, false en caso contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return id.equals(user.id);
    }

    /**
     * Genera el código hash para este usuario basado en su ID.
     * 
     * @return el código hash del usuario
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Genera una representación en string del usuario.
     * 
     * @return string que contiene la información del usuario
     */
    @Override
    public String toString() {
        return String.format("User{ID='%s', nombre='%s', email='%s'}", id, name, email);
    }
}
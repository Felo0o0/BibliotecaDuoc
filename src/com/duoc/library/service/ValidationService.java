/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.service;

import com.duoc.library.exception.InvalidUserException;

public class ValidationService {
    
    public static void validateUserId(String userId) throws InvalidUserException {
        if (userId == null || userId.trim().isEmpty()) {
            throw new InvalidUserException("User ID cannot be null or empty");
        }
        if (userId.length() < 3) {
            throw new InvalidUserException("User ID must have at least 3 characters");
        }
    }

    public static void validateBookIsbn(String isbn) throws IllegalArgumentException {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        if (isbn.length() < 5) {
            throw new IllegalArgumentException("ISBN must have at least 5 characters");
        }
    }

    public static void validateEmail(String email) throws InvalidUserException {
        if (email == null || email.trim().isEmpty()) {
            throw new InvalidUserException("Email cannot be null or empty");
        }
        if (!email.contains("@") || !email.contains(".")) {
            throw new InvalidUserException("Invalid email format");
        }
    }

    public static void validateString(String value, String fieldName) throws IllegalArgumentException {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
    }
}
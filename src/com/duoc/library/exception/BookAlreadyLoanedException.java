/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.exception;

public class BookAlreadyLoanedException extends Exception {
    public BookAlreadyLoanedException(String message) {
        super(message);
    }

    public BookAlreadyLoanedException(String message, Throwable cause) {
        super(message, cause);
    }
}
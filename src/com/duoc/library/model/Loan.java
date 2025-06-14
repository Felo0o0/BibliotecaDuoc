/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan {
    private String loanId;
    private String userId;
    private String bookIsbn;
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean returned;

    public Loan(String loanId, String userId, String bookIsbn) {
        this.loanId = loanId;
        this.userId = userId;
        this.bookIsbn = bookIsbn;
        this.loanDate = LocalDate.now();
        this.returnDate = loanDate.plusDays(14); // 14 days loan period
        this.returned = false;
    }

    // Getters
    public String getLoanId() { return loanId; }
    public String getUserId() { return userId; }
    public String getBookIsbn() { return bookIsbn; }
    public LocalDate getLoanDate() { return loanDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public boolean isReturned() { return returned; }

    // Setters
    public void setLoanId(String loanId) { this.loanId = loanId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setBookIsbn(String bookIsbn) { this.bookIsbn = bookIsbn; }
    public void setLoanDate(LocalDate loanDate) { this.loanDate = loanDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
    public void setReturned(boolean returned) { this.returned = returned; }

    public boolean isOverdue() {
        return !returned && LocalDate.now().isAfter(returnDate);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("Loan{ID='%s', User='%s', Book='%s', LoanDate='%s', ReturnDate='%s', Returned=%s}", 
                           loanId, userId, bookIsbn, 
                           loanDate.format(formatter), 
                           returnDate.format(formatter), 
                           returned);
    }
}
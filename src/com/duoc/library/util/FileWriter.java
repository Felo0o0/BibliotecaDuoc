/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.util;

import com.duoc.library.model.Book;
import com.duoc.library.model.User;
import com.duoc.library.model.Loan;
import com.duoc.library.service.FileService;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FileWriter {
    private static final String CSV_DELIMITER = ",";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void writeBooksToCSV(List<Book> books, String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("ISBN,Title,Author,Available\n");
        
        for (Book book : books) {
            content.append(book.getIsbn()).append(CSV_DELIMITER)
                   .append(book.getTitle()).append(CSV_DELIMITER)
                   .append(book.getAuthor()).append(CSV_DELIMITER)
                   .append(book.isAvailable()).append("\n");
        }
        
        FileService.writeToFile(filename, content.toString());
    }

    public static void writeUsersToCSV(List<User> users, String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("ID,Name,Email,Active\n");
        
        for (User user : users) {
            content.append(user.getId()).append(CSV_DELIMITER)
                   .append(user.getName()).append(CSV_DELIMITER)
                   .append(user.getEmail()).append(CSV_DELIMITER)
                   .append(user.isActive()).append("\n");
        }
        
        FileService.writeToFile(filename, content.toString());
    }

    public static void writeLoansToCSV(List<Loan> loans, String filename) throws IOException {
        StringBuilder content = new StringBuilder();
        content.append("LoanID,UserID,BookISBN,LoanDate,ReturnDate,Returned\n");
        
        for (Loan loan : loans) {
            content.append(loan.getLoanId()).append(CSV_DELIMITER)
                   .append(loan.getUserId()).append(CSV_DELIMITER)
                   .append(loan.getBookIsbn()).append(CSV_DELIMITER)
                   .append(loan.getLoanDate().format(DATE_FORMATTER)).append(CSV_DELIMITER)
                   .append(loan.getReturnDate().format(DATE_FORMATTER)).append(CSV_DELIMITER)
                   .append(loan.isReturned()).append("\n");
        }
        
        FileService.writeToFile(filename, content.toString());
    }

    public static void writeReportToFile(String report, String filename) throws IOException {
        FileService.writeToFile(filename, report);
    }
}
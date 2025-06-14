/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.util;

import com.duoc.library.model.Book;
import com.duoc.library.model.User;
import com.duoc.library.service.FileService;
import com.duoc.library.exception.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
    private static final String CSV_DELIMITER = ",";

    public static List<Book> readBooksFromCSV(String filename) 
            throws FileNotFoundException, IOException, IllegalArgumentException {
        
        List<String> lines = FileService.readAllLines(filename);
        List<Book> books = new ArrayList<>();
        
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            
            try {
                String[] parts = line.split(CSV_DELIMITER);
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Invalid CSV format at line " + (i + 1) + ": insufficient columns");
                }
                
                String isbn = parts[0].trim();
                String title = parts[1].trim();
                String author = parts[2].trim();
                
                if (isbn.isEmpty() || title.isEmpty() || author.isEmpty()) {
                    throw new IllegalArgumentException("Invalid CSV format at line " + (i + 1) + ": empty values not allowed");
                }
                
                books.add(new Book(isbn, title, author));
                
            } catch (Exception e) {
                throw new IllegalArgumentException("Error processing line " + (i + 1) + " in file " + filename + ": " + e.getMessage(), e);
            }
        }
        
        return books;
    }

    public static List<User> readUsersFromCSV(String filename) 
            throws FileNotFoundException, IOException, IllegalArgumentException {
        
        List<String> lines = FileService.readAllLines(filename);
        List<User> users = new ArrayList<>();
        
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i).trim();
            if (line.isEmpty()) continue;
            
            try {
                String[] parts = line.split(CSV_DELIMITER);
                if (parts.length < 3) {
                    throw new IllegalArgumentException("Invalid CSV format at line " + (i + 1) + ": insufficient columns");
                }
                
                String id = parts[0].trim();
                String name = parts[1].trim();
                String email = parts[2].trim();
                
                if (id.isEmpty() || name.isEmpty() || email.isEmpty()) {
                    throw new IllegalArgumentException("Invalid CSV format at line " + (i + 1) + ": empty values not allowed");
                }
                
                users.add(new User(id, name, email));
                
            } catch (Exception e) {
                throw new IllegalArgumentException("Error processing line " + (i + 1) + " in file " + filename + ": " + e.getMessage(), e);
            }
        }
        
        return users;
    }
}
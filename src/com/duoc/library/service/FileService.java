/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.service;

import com.duoc.library.exception.FileNotFoundException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    
    public static void validateFile(String filename) throws FileNotFoundException {
        if (filename == null || filename.trim().isEmpty()) {
            throw new FileNotFoundException("Filename cannot be null or empty");
        }
        
        File file = new File(filename);
        if (!file.exists()) {
            throw new FileNotFoundException("File not found: " + filename);
        }
        
        if (!file.canRead()) {
            throw new FileNotFoundException("Cannot read file: " + filename);
        }
    }

    public static void writeToFile(String filename, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println(content);
        } catch (IOException e) {
            throw new IOException("Error writing to file " + filename + ": " + e.getMessage(), e);
        }
    }

    public static void appendToFile(String filename, String content) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(content);
        } catch (IOException e) {
            throw new IOException("Error appending to file " + filename + ": " + e.getMessage(), e);
        }
    }

    public static List<String> readAllLines(String filename) throws FileNotFoundException, IOException {
        validateFile(filename);
        
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new IOException("Error reading file " + filename + ": " + e.getMessage(), e);
        }
        
        return lines;
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.duoc.library.service;

import com.duoc.library.model.Book;
import com.duoc.library.model.User;
import com.duoc.library.model.Loan;
import com.duoc.library.exception.*;
import java.util.*;

public class LibraryService {
    private ArrayList<Book> books;
    private HashMap<String, User> users;
    private ArrayList<Loan> loans;
    private int nextLoanId;

    public LibraryService() {
        this.books = new ArrayList<>();
        this.users = new HashMap<>();
        this.loans = new ArrayList<>();
        this.nextLoanId = 1;
    }

    // Book operations
    public void addBook(Book book) throws IllegalArgumentException {
        ValidationService.validateBookIsbn(book.getIsbn());
        ValidationService.validateString(book.getTitle(), "Book title");
        ValidationService.validateString(book.getAuthor(), "Book author");
        
        if (findBookByIsbn(book.getIsbn()) != null) {
            throw new IllegalArgumentException("Book with ISBN " + book.getIsbn() + " already exists");
        }
        books.add(book);
    }

    public Book findBookByIsbn(String isbn) {
        return books.stream()
                   .filter(book -> book.getIsbn().equals(isbn))
                   .findFirst()
                   .orElse(null);
    }

    public List<Book> searchBooksByTitle(String title) {
        return books.stream()
                   .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                   .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    // User operations
    public void addUser(User user) throws InvalidUserException {
        ValidationService.validateUserId(user.getId());
        ValidationService.validateString(user.getName(), "User name");
        ValidationService.validateEmail(user.getEmail());
        
        if (users.containsKey(user.getId())) {
            throw new InvalidUserException("User with ID " + user.getId() + " already exists");
        }
        users.put(user.getId(), user);
    }

    public User findUserById(String userId) throws InvalidUserException {
        ValidationService.validateUserId(userId);
        User user = users.get(userId);
        if (user == null) {
            throw new InvalidUserException("User with ID " + userId + " not found");
        }
        if (!user.isActive()) {
            throw new InvalidUserException("User with ID " + userId + " is not active");
        }
        return user;
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }

    // Loan operations
    public Loan loanBook(String userId, String isbn) 
            throws InvalidUserException, BookNotFoundException, BookAlreadyLoanedException {
        
        User user = findUserById(userId);
        Book book = findBookByIsbn(isbn);
        
        if (book == null) {
            throw new BookNotFoundException("Book with ISBN " + isbn + " not found");
        }
        
        if (!book.isAvailable()) {
            throw new BookAlreadyLoanedException("Book with ISBN " + isbn + " is already loaned");
        }

        // Check if user already has this book
        boolean userHasBook = loans.stream()
                                  .anyMatch(loan -> loan.getUserId().equals(userId) 
                                                 && loan.getBookIsbn().equals(isbn) 
                                                 && !loan.isReturned());
        if (userHasBook) {
            throw new BookAlreadyLoanedException("User already has this book on loan");
        }

        String loanId = String.valueOf(nextLoanId++);
        Loan loan = new Loan(loanId, userId, isbn);
        loans.add(loan);
        book.setAvailable(false);
        
        return loan;
    }

    public void returnBook(String loanId) throws IllegalArgumentException {
        ValidationService.validateString(loanId, "Loan ID");
        
        Loan loan = loans.stream()
                        .filter(l -> l.getLoanId().equals(loanId) && !l.isReturned())
                        .findFirst()
                        .orElse(null);
        
        if (loan == null) {
            throw new IllegalArgumentException("Active loan with ID " + loanId + " not found");
        }

        loan.setReturned(true);
        Book book = findBookByIsbn(loan.getBookIsbn());
        if (book != null) {
            book.setAvailable(true);
        }
    }

    public List<Loan> getUserLoans(String userId) throws InvalidUserException {
        ValidationService.validateUserId(userId);
        return loans.stream()
                   .filter(loan -> loan.getUserId().equals(userId))
                   .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Loan> getActiveLoans() {
        return loans.stream()
                   .filter(loan -> !loan.isReturned())
                   .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    public List<Loan> getOverdueLoans() {
        return loans.stream()
                   .filter(Loan::isOverdue)
                   .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
}
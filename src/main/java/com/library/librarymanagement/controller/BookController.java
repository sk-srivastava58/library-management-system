package com.library.librarymanagement.controller;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.service.BookService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'LIBRARIAN')")
    @PostMapping
    public Book addBook(@RequestBody Book book){
        return bookService.saveBook(book);
    }

    @GetMapping
    public List<Book> getBook(){
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBookByid(@PathVariable Long id){
        return bookService.getBookByid(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Long id, @RequestBody Book book){
        return bookService.updateBook(id,book);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable Long id){
        bookService.deleteBook(id);
        return "Book deleted successfully";
    }

    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @PutMapping("/issue/{id}")
    public String issueBook(@PathVariable Long id){
        bookService.issueBook(id);
        return "Book issue Successfully";
    }

    @PreAuthorize("hasAnyRole('ADMIN','LIBRARIAN')")
    @PutMapping("/return/{id}")
    public String returnBook(@PathVariable Long id){
        bookService.returnBook(id);
        return "Book returned successfully";
    }
}

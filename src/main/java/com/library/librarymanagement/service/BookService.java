package com.library.librarymanagement.service;

import com.library.librarymanagement.entity.Book;
import com.library.librarymanagement.exception.BadRequestException;
import com.library.librarymanagement.exception.ResourceNotFoundException;
import com.library.librarymanagement.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //save book
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    //get all book
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    // search book by id
    public Book getBookByid(Long id){
        return bookRepository
                .findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Book not found with id: " + id));
    }

    //update book detail
    public Book updateBook(Long id, Book updated){
        Book oldBook = getBookByid(id);

        oldBook.setTitle(updated.getTitle());
        oldBook.setAuthor(updated.getAuthor());
        oldBook.setAvailableCopies(updated.getAvailableCopies());
        oldBook.setTotalCopies(updated.getTotalCopies());
        oldBook.setStatus(updated.getStatus());

        return bookRepository.save(oldBook);
    }

    //delete book by id
    public void deleteBook(Long id){
        Book book = getBookByid(id);

        bookRepository.delete(book);
    }
    //
    public void issueBook(Long id){
        Book book = getBookByid(id);

        if(book.getAvailableCopies()<=0){
            throw new BadRequestException("Book not available");
        }

        book.setAvailableCopies(book.getAvailableCopies()-1);

        if(book.getAvailableCopies()==0){
            book.setStatus(Book.Status.UNAVAILABLE);
        }

        bookRepository.save(book);
    }

    public void returnBook(Long id){
        Book book = getBookByid(id);

        book.setAvailableCopies(book.getAvailableCopies()+1);

        if(book.getAvailableCopies()>0){
            book.setStatus(Book.Status.AVAILABLE);
        }

        bookRepository.save(book);
    }
}

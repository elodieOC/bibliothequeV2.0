package com.mbooks.microservicebooks.controller;

import com.mbooks.microservicebooks.dao.BookDao;
import com.mbooks.microservicebooks.dao.BorrowingDao;
import com.mbooks.microservicebooks.exceptions.NotFoundException;
import com.mbooks.microservicebooks.model.Book;
import com.mbooks.microservicebooks.model.Borrowing;
import com.mbooks.microservicebooks.utils.CheckDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * <h2>Controller for model Book</h2>
 */
@RestController
public class BookController {
    @Autowired
    private BookDao bookDao;
    @Autowired
    private BorrowingDao borrowingDao;

    Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * <p>Lists all books</p>
     * @return list
     */
    @GetMapping(value="/Livres")
    public List<Book> listBooks() {
        List<Book> books = bookDao.findAll();
        if(books.isEmpty()) throw new NotFoundException("Aucun livre n'est disponible");
        log.info("Récupération de la liste des produits");
        return books;
    }

    /**
     * <h2>Not needed for user application => for employees application</h2>
     * <p>adds a new book in db, checks if the date is valid before save</p>
     * @param book
     * @return responseEntity
     */
    @PostMapping(value = "/Livres")
    public ResponseEntity<Void> addBook(@Valid @RequestBody Book book) {
        String date = book.getReleaseDate();
        CheckDate.checkDate(date);

        Book bookAdded =  bookDao.save(book);
        if (bookAdded == null) {
            return ResponseEntity.noContent().build();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bookAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    /**
     * <p>shows details of a particular book by its id</p>
     * @param id
     * @return the book
     */
    @GetMapping(value = "/Livres/{id}")
    public Book showBook(@PathVariable Integer id) {
        //search for Optional not to get error
        Optional<Book> bookSearch = bookDao.findById(id);
        if(!bookSearch.isPresent()) {
            throw new NotFoundException("L'item avec l'id " + id + " est INTROUVABLE.");
        }
        //if optional is found, search for object book
        else {
            Book book = bookDao.findBookById(id);
            boolean availableOrNot = true;
            String bookRef = book.getRef();
            int count = 0;
            //list all borrowings with the same book ref
            List<Borrowing> borrowingsByBook = borrowingDao.findBorrowingByBook_Ref(bookRef);
            //if there is at least one borrowing with this book ref
            if (borrowingsByBook.size() > 0) {
                //count occurences where the book is not returned yet
                for (Borrowing borrowing : borrowingsByBook) {
                    if (borrowing.getReturned() == null) {
                        count++;
                    }
                }
                //if all borrowed books with this ref have not been returned, then not available
                if (count >= borrowingsByBook.size()) {
                    availableOrNot=false;
                }
            } else {
                availableOrNot=true;
            }
            book.setAvailable(availableOrNot);
            return book;
        }
    }
}

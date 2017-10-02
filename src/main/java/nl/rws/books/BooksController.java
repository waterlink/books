package nl.rws.books;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1")
public class BooksController {

    private BooksRepository repository;

    public BooksController(BooksRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/books")
    public ResponseEntity<BookListResponse> getListOfBooks() {
        Iterable<Book> books = this.repository.findAll();
        List<Book> bookList = new ArrayList<>();
        books.forEach(book -> bookList.add(book));
        return ResponseEntity.ok(new BookListResponse(bookList));
    }

}


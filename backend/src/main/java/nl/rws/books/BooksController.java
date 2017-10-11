package nl.rws.books;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class BooksController {

    private BooksRepository booksRepository;

    public BooksController(BooksRepository repository) {
        this.booksRepository = repository;
    }

    @GetMapping("/books")
    public ResponseEntity<BookListResponse> getListOfBooks() {
        List<Book> books = this.booksRepository.findAll();
        return ResponseEntity.ok(new BookListResponse(books));
    }

    @PostMapping("/books/{id}/borrow")
    public ResponseEntity<Book> borrow(
            @PathVariable String id,
            @RequestParam String memberId) throws Exception {

        Book book = booksRepository.findOne(id);

        if (book.getStatus().equals("available")) {
            book.setStatus("unavailable");
            book.setBorrowedBy(new Member(memberId));

            booksRepository.save(book);

            return ResponseEntity.ok(book);
        } else {
            throw new BookUnavailableException(book);
        }
    }

    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleBookUnavailableException(BookUnavailableException e) {
        ErrorResponse errorResponse = new ErrorResponse("BOOK_UNAVAILABLE", e.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/books/add")
    public ResponseEntity<Book> add(
            @RequestParam String id,
            @RequestParam String title,
            @RequestParam String status) throws Exception {

        Book book = new Book(id, title, status, null);

        return ResponseEntity.ok(booksRepository.save(book));
    }

    @PostMapping("/books/{id}/delete")
    public ResponseEntity<Book> delete(
            @PathVariable String id) throws Exception {

        Book book = booksRepository.findOne(id);

        if ((book != null) && (book.getStatus() == "available")) {
            booksRepository.delete(id);
        }

        return ResponseEntity.ok(book);
    }

    @PostMapping("/books/{id}/update")
    public ResponseEntity<Book> update(
            @PathVariable String id,
            @RequestParam String title) throws Exception {

        Book book = booksRepository.findOne(id);

        if ((book != null)) {
            book.setTitle(title);
            return ResponseEntity.ok(booksRepository.save(book));
        }

        return ResponseEntity.ok(book);
    }
}
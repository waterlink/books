package nl.rws.books;

import org.springframework.beans.factory.annotation.Autowired;
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
        }
        else {
            ResponseEntity responseEntity = new ResponseEntity("Book with id:" + book.getId() + " can not be borrowed", HttpStatus.BAD_REQUEST);
            return responseEntity;
        }
    }

    @PostMapping("/books/add")
    public ResponseEntity<Book> add(
            @RequestParam String id,
            @RequestParam String title,
            @RequestParam String status) throws Exception {

        Book book = new Book(id, title, status, null);

        Book savedBook = booksRepository.save(book);

        return ResponseEntity.ok(savedBook);
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
}
package nl.rws.books;

public class BookUnavailableException extends RuntimeException {
    public BookUnavailableException(Book book) {
        super("book with id " + book.getId() + " can not be borrowed");
    }
}

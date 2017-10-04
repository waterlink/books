package nl.rws.books;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BookTest {

    @Test
    public void newBookIsAvailable() throws Exception {
        final Book book = new Book();
        assertThat(book).hasFieldOrPropertyWithValue("status", "available");

        final Book bookWithTitle = new Book("some title");
        assertThat(bookWithTitle).hasFieldOrPropertyWithValue("status", "available");
    }
}
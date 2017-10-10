package nl.rws.books;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BooksRepositoryTest {

    @Autowired
    private BooksRepository booksRepository;

    @Before
    public void setUp() throws Exception {
        booksRepository.deleteAll();
    }

    @Test
    public void CanCreateAndFindBooks() throws Exception {
        Book book1 = new Book("Ivanhoe");
        Book book2 = new Book("Robin Hood");
        booksRepository.save(book1);
        booksRepository.save(book2);

        List<Book> books = booksRepository.findAll();

        assertThat(books.get(0).getTitle()).isEqualTo(book1.getTitle());
        assertThat(books.get(1).getTitle()).isEqualTo(book2.getTitle());
        assertThat(books.size()).isEqualTo(2);
    }
}
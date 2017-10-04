package nl.rws.books;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BooksControllerTest {

    private BooksRepository repository = Mockito.mock(BooksRepository.class);

    private BooksController subject = new BooksController(repository);

    private MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(subject)
            .build();

    @Test
    public void listEndpointPresentsListOfBooks() throws Exception {

        when(repository.findAll()).thenReturn(emptyList());

        mockMvc.perform(get("/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", equalTo(emptyList())));

    }

    @Test
    public void listEndpointPresentListOfBooksWhenThereAreBooks() throws Exception {

        when(repository.findAll())
                .thenReturn(asList(new Book("Ivanhoe"), new Book("Robin Hood")));

        mockMvc.perform(get("/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(2)))
                .andExpect(jsonPath("$.books[0].title", equalTo("Ivanhoe")))
                .andExpect(jsonPath("$.books[1].title", equalTo("Robin Hood")));

    }

    @Test
    public void borrowEndpointMakesBookUnavailable() throws Exception {
        final Book book = new Book(
                "9ea360bc-8198-4e6a-be0c-63670891e1e8",
                "Ivanhoe",
                "available",
                null
        );
        final String memberId = "c1091391-7de9-4658-8b79-3f44ea9544d0";

        when(repository.findOne(book.getId()))
                .thenReturn(book);

        mockMvc.perform(post("/v1/books/" + book.getId() + "/borrow")
                .param("memberId", memberId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(book.getId())))
                .andExpect(jsonPath("$.title", equalTo(book.getTitle())))
                .andExpect(jsonPath("$.status", equalTo("unavailable")))
                .andExpect(jsonPath("$.borrowedBy", equalTo(memberId)));

        final Book unavailableBook = new Book(
                book.getId(),
                book.getTitle(),
                "unavailable",
                new Member(memberId)
        );
        verify(repository).save(unavailableBook);
    }
}


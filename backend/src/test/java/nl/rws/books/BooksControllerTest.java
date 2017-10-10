package nl.rws.books;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.persistence.EntityManager;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class BooksControllerTest {

    private BooksRepository booksRepository = Mockito.mock(BooksRepository.class);

    private BooksController subject = new BooksController(booksRepository);

    private MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(subject)
            .build();

    @Test
    public void listEndpointPresentsListOfBooks() throws Exception {
        when(booksRepository.findAll()).thenReturn(emptyList());

        mockMvc.perform(get("/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", equalTo(emptyList())));
    }

    @Test
    public void listEndpointPresentListOfBooksWhenThereAreBooks() throws Exception {
        when(booksRepository.findAll())
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
        final Member member = new Member("c1091391-7de9-4658-8b79-3f44ea9544d0",
                "Jeroen");

        when(booksRepository.findOne(book.getId()))
                .thenReturn(book);

        mockMvc.perform(post("/v1/books/" + book.getId() + "/borrow")
                .param("memberId", member.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(book.getId())))
                .andExpect(jsonPath("$.title", equalTo(book.getTitle())))
                .andExpect(jsonPath("$.status", equalTo("unavailable")))
                .andExpect(jsonPath("$.borrowedBy", equalTo(member.getId())));

        final Book unavailableBook = new Book(
                book.getId(),
                book.getTitle(),
                "unavailable",
                new Member(member.getId())
        );
        verify(booksRepository).save(unavailableBook);
    }

    @Test
    public void borrowEndpointThrowsErrorWhenBookIsAlreadyUnavailable() throws Exception {
        final Member member = new Member("c1091391-7de9-4658-8b79-3f44ea9544d0",
                "Jeroen");

        final Book book = new Book(
                "9ea360bc-8198-4e6a-be0c-63670891e1e8",
                "Ivanhoe",
                "unavailable",
                member
        );

        when(booksRepository.findOne(book.getId()))
                .thenReturn(book);

        MvcResult result = mockMvc.perform(post("/v1/books/" + book.getId() + "/borrow")
                .param("memberId", member.getId()))
                .andExpect(status().isBadRequest())
                .andExpect(status().is4xxClientError())
                .andReturn();

        String content = result.getResponse().getContentAsString();
        assert(content).contains("can not be borrowed");

        final Book unavailableBook = new Book(
                book.getId(),
                book.getTitle(),
                "unavailable",
                new Member(member.getId())
        );
        verify(booksRepository).findOne(book.getId());
        verify(booksRepository, never()).save(unavailableBook);
    }

    @Test
    public void addEndpointAddsBook() throws Exception {
        final Book book = new Book(
                "9ea360bc-8198-4e6a-be0c-63670891e1e8",
                "Ivanhoe",
                "unavailable",
                null
        );

        when(booksRepository.save(book))
                .thenReturn(book);

        mockMvc.perform(post("/v1/books/add")
                .param("id",book.getId())
                .param("title", book.getTitle())
                .param("status", book.getStatus()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(book.getId())))
                .andExpect(jsonPath("$.title", equalTo(book.getTitle())))
                .andExpect(jsonPath("$.status", equalTo(book.getStatus())))
                .andExpect(jsonPath("$.borrowedBy", equalTo(book.getBorrowedBy())));

        verify(booksRepository).save(book);
    }

    @Test
    public void deleteEndpointDeletesBookThatIsPresent() throws Exception {
        final Book book = new Book(
                "9ea360bc-8198-4e6a-be0c-63670891e1e8",
                "Ivanhoe",
                "available",
                null
        );
        when(booksRepository.findOne(book.getId()))
                .thenReturn(book);

        mockMvc.perform(post("/v1/books/" + book.getId() + "/delete")
                .param("id",book.getId()))
                .andExpect(status().isOk());

        verify(booksRepository).delete(book.getId());
    }

    @Test
    public void deleteEndpointDoesNotDeleteBookWhenNotPresent() throws Exception {
        final Book book = new Book(
                "9ea360bc-8198-4e6a-be0c-63670891e1e8",
                "Ivanhoe",
                "available",
                null
        );
        when(booksRepository.findOne(book.getId()))
                .thenReturn(null);

        mockMvc.perform(post("/v1/books/" + book.getId() + "/delete")
                .param("id",book.getId()))
                .andExpect(status().isOk());

        verify(booksRepository, never()).delete(book.getId());
    }

    @Test
    public void deleteEndpointDoesNotDeleteBookWhenBookIsBorrowed() throws Exception {
        final Book book = new Book(
                "9ea360bc-8198-4e6a-be0c-63670891e1e8",
                "Ivanhoe",
                "unavailable",
                null
        );
        when(booksRepository.findOne(book.getId()))
                .thenReturn(book);

        mockMvc.perform(post("/v1/books/" + book.getId() + "/delete")
                .param("id",book.getId()))
                .andExpect(status().isOk());

        verify(booksRepository).findOne(book.getId());
        verify(booksRepository, never()).delete(book.getId());
    }

    @Test
    public void updateEndpointUpdatesBook() throws Exception {
        final Book book = new Book(
                "9ea360bc-8198-4e6a-be0c-63670891e1e8",
                "Ivanhoe",
                "available",
                null
        );

        final Book updatedBook = new Book(
                "9ea360bc-8198-4e6a-be0c-63670891e1e8",
                "Ivanhoe, the hero",
                "available",
                null
        );

        when(booksRepository.findOne(book.getId()))
                .thenReturn(book);
        when(booksRepository.save(book))
                .thenReturn(updatedBook);

        mockMvc.perform(post("/v1/books/" + book.getId() + "/update")
                .param("title", updatedBook.getTitle()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(updatedBook.getId())))
                .andExpect(jsonPath("$.title", equalTo(updatedBook.getTitle())))
                .andExpect(jsonPath("$.status", equalTo(updatedBook.getStatus())))
                .andExpect(jsonPath("$.borrowedBy", equalTo(updatedBook.getBorrowedBy())));

        verify(booksRepository).save(book);
        verify(booksRepository).findOne(book.getId());
    }

}
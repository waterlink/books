package nl.rws.books;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

        when(repository.findAll()).thenReturn(asList(new Book("Ivanhoe"), new Book("Robin Hood")));

        mockMvc.perform(get("/v1/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.books", hasSize(2)))
                .andExpect(jsonPath("$.books[0].title", equalTo("Ivanhoe")))
                .andExpect(jsonPath("$.books[1].title", equalTo("Robin Hood")));

    }
}

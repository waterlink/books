package nl.rws.books;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MembersControllerTest {

    private MembersRepository repository = Mockito.mock(MembersRepository.class);

    private MembersController subject = new MembersController(repository);

    private MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(subject)
            .build();

    @Test
    public void listEndpointPresentsListOfMembers() throws Exception {
        when(repository.findAll()).thenReturn(emptyList());

        mockMvc.perform(get("/v1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.members", equalTo(emptyList())));
    }

    @Test
    public void listEndpointPresentsListOfMembersWhenThereAreMembers() throws Exception {
        when(repository.findAll())
                .thenReturn(asList(
                        new Member("ceb9a708-aa9d-11e7-abc4-cec278b6b50a", "Oleksii"),
                        new Member("ceb9ab72-aa9d-11e7-abc4-cec278b6b50a", "Jeroen")));

        mockMvc.perform(get("/v1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.members", hasSize(2)))
                .andExpect(jsonPath("$.members[0].name", equalTo("Oleksii")))
                .andExpect(jsonPath("$.members[1].name", equalTo("Jeroen")));
    }
}
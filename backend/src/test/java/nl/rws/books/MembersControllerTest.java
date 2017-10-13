package nl.rws.books;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MembersControllerTest {

    private MembersRepository membersRepository = Mockito.mock(MembersRepository.class);

    private MembersController subject = new MembersController(membersRepository);

    private MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(subject)
            .build();

    @Test
    public void listEndpointPresentsListOfMembers() throws Exception {
        when(membersRepository.findAll()).thenReturn(emptyList());

        mockMvc.perform(get("/v1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.members", equalTo(emptyList())));
    }

    @Test
    public void listEndpointPresentsListOfMembersWhenThereAreMembers() throws Exception {
        when(membersRepository.findAll())
                .thenReturn(asList(
                        new Member("ceb9a708-aa9d-11e7-abc4-cec278b6b50a", "Oleksii"),
                        new Member("ceb9ab72-aa9d-11e7-abc4-cec278b6b50a", "Jeroen")));

        mockMvc.perform(get("/v1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.members", hasSize(2)))
                .andExpect(jsonPath("$.members[0].name", equalTo("Oleksii")))
                .andExpect(jsonPath("$.members[1].name", equalTo("Jeroen")));
    }

    @Test
    public void addEndpointAddsMember() throws Exception {
        final Member member = new Member(
                "ceb9a708-aa9d-11e7-abc4-cec278b6b50a",
                "Oleksii"
        );

        when(membersRepository.save(member))
                .thenReturn(member);

        mockMvc.perform(post("/v1/members/add")
                .param("id", member.getId())
                .param("name", member.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo(member.getId())))
                .andExpect(jsonPath("$.name", equalTo(member.getName())));

        verify(membersRepository).save(member);

    }

    @Test
    public void deleteEndpointDeletesMember() throws Exception {
        final Member member = new Member(
                "ceb9a708-aa9d-11e7-abc4-cec278b6b50a",
                "Oleksii"
        );
        when(membersRepository.findOne(member.getId()))
                .thenReturn(member);

        mockMvc.perform(post("/v1/members/" + member.getId() + "/delete")
                .param("id", member.getId()))
                .andExpect(status().isOk());

        verify(membersRepository).delete(member.getId());
    }

    @Test
    public void updateEndpointUpdatesMember() throws Exception {
        final Member member = new Member(
                "ceb9a708-aa9d-11e7-abc4-cec278b6b50a",
                "Oleksi"
        );

        final Member updatedMember = new Member(
                "ceb9a708-aa9d-11e7-abc4-cec278b6b50a",
                "Oleksii"
        );

        when(membersRepository.findOne(member.getId()))
                .thenReturn(member);
        when(membersRepository.save(member))
                .thenReturn(updatedMember);

        mockMvc.perform(post("/v1/members/" + member.getId() + "/update")
                .param("name", updatedMember.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.equalTo(updatedMember.getId())))
                .andExpect(jsonPath("$.name", Matchers.equalTo(updatedMember.getName())));

        verify(membersRepository).save(member);
        verify(membersRepository).findOne(member.getId());
    }
}
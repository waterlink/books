package nl.rws.books;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("fakeSso")
public class MembersRepositoryTest {

    @Autowired
    private MembersRepository membersRepository;

    @Before
    public void setUp() throws Exception {
        membersRepository.deleteAll();
    }

    @Test
    public void CanCreateAndFindMembers() {
        Member member1 = new Member("ceb9a708-aa9d-11e7-abc4-cec278b6b50a", "Oleksii");
        Member member2 = new Member("ceb9ab72-aa9d-11e7-abc4-cec278b6b50a", "Jeroen");

        membersRepository.save(member1);
        membersRepository.save(member2);

        List<Member> members = membersRepository.findAll();

        assertThat(members.get(0).getName()).isEqualTo(member1.getName());
        assertThat(members.get(1).getName()).isEqualTo(member2.getName());
        assertThat(members.size()).isEqualTo(2);
    }
}

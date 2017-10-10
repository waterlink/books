package nl.rws.books;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1")
public class MembersController {

    private MembersRepository membersRepository;

    public MembersController(MembersRepository repository) {
        this.membersRepository = repository;
    }

    @GetMapping("/members")
    public ResponseEntity<MemberListResponse> getListOfMembers() {
        List<Member> members = this.membersRepository.findAll();
        return ResponseEntity.ok(new MemberListResponse(members));
    }
}

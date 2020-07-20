package nl.rws.books;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/members/add")
    public ResponseEntity<Member> add(
            @RequestParam String id,
            @RequestParam String name) throws Exception {

        Member member = new Member(id, name);

        return ResponseEntity.ok(membersRepository.save(member));
    }

    @PostMapping("/members/{id}/delete")
    public ResponseEntity<Member> delete(
            @PathVariable String id) throws Exception {

        Member member = membersRepository.findOne(id);

        if (member != null) {
            membersRepository.delete(id);
        }

        return ResponseEntity.ok(member);
    }

    @PostMapping("/members/{id}/update")
    public ResponseEntity<Member> update(
            @PathVariable String id,
            @RequestParam String name) throws Exception {

        Member member = membersRepository.findOne(id);

        if ((member != null)) {
            member.setName(name);
            return ResponseEntity.ok(membersRepository.save(member));
        }

        return ResponseEntity.ok(member);
    }

}

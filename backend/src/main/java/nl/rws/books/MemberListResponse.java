package nl.rws.books;

import java.util.List;

public class MemberListResponse {

    private List<Member> members;

    public MemberListResponse(List<Member> members) {
        this.members = members;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }
}

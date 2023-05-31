package hello.hellospring.controller;

public class MemberForm {
    private String name; // createMemberForm.html의 key인 name이랑 맞아떨어지는 부분

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

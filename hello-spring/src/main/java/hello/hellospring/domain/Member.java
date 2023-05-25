package hello.hellospring.domain;

import jakarta.persistence.*;

@Entity // 이제부터 jpa가 관리하는 객체다!
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // jpa는 pk가 있어야함.
    private Long id;

//    @Column(name = "username")
    private String name;

//    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

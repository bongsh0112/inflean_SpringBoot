package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository { //interface가 interface받을땐 extends


    //JPQL select m from Member m where m.name = ? findByName(String name)만 보고 규칙을 따라서 저렇게 JPQL문을 만들어줌...ㄷㄷ
    @Override
    Optional<Member> findByName(String name);
}

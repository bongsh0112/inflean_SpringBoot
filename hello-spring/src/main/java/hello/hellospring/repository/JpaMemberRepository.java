package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em; // jpa는 entitymanager라는 걸로 모든게 동작함.

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // 여기서 jpa가 insert query 만들어서 다 집어넣고 id까지 멤버에다 setid까지
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();

        return result.stream().findAny(); // findbyname으로 하나만 찾으니까 stream().findAny()
    }

    @Override
    public List<Member> findAll() {
        return em.createQuery("select m from Member m", Member.class) // select의 대상이 재밌다. 얘는 멤버 엔티티 자체를 셀렉트해서 조회해버림. 이미 매핑되어있어서 그런듯.
                .getResultList();
    }
}

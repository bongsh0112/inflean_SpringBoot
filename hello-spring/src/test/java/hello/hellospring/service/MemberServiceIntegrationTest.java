package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional

class MemberServiceIntegrationTest {

    @Autowired MemberService memberService;
    @Autowired
    MemberRepository memberRepository; // test할때는 필드방식으로 오토와이어드 해도됨

    // afterEach가 필요했던 이유는 메모리의 데이터들을 지우기 위함이었는데 이제 db가 있으니까 필요없음

    @Test
//    @Commit  // 이걸 하면 밑 코드의 내용과 맞게 디비에 저장이 됨. 위에 Transactional때문에 계속 롤백돼서 안들어갔지만 여기서 커밋으로 디비에 넣으면 저장되는거임!
    void 회원가입() {
        //given : 뭔가가 주어짐
        Member member = new Member();
        member.setName("spring");

        //when : 실행시
        Long saveId = memberService.join(member);

        //then : 결과가 나와야함!
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());

    }

    //예외처리
    @Test
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setName("spring");

        Member member2 = new Member();
        member2.setName("spring");

        memberService.join(member1); // join시 계속 메모리 DB에 누적되기때문에 항상 클리어해줘야함.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
    }

}
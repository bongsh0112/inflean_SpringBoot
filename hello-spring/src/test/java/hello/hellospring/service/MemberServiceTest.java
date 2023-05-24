package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    //클리어해줘야하는데 memberservice밖에 없음

//    MemoryMemberRepository memberRepository = new MemoryMemberRepository();
    //main에 있는 그거 맞음 -> 다른 레포에있기때문에 인스턴스라던지 그런게 달라질 수 있다
    //memberservice.java에서 만든 레포와 완전 다른 레포임 ㅠ
    //같은 레포로 테스트하는게 맞지만 다른 레포로 테스트중인거 그래서 어떻게 하냐
    //memberservice에서 constructor만들어서 외부에 넣어주도록

    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void BeforeEach() {
        memberRepository = new MemoryMemberRepository();
        //여기서 만든 메모리멤버레포를
        memberService = new MemberService(memberRepository);
        //여기다가 넣어주니까 같은 메모리멤버레포를 쓰는게 됨
        //memberservice 차원에서 보면 외부에서 파라미터를 넣어준다. 이런게 뭐냐면 dependency ijection(DI)
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }


    @Test
    void 회원가입() {
        //given : 뭔가가 주어짐
        Member member = new Member();
        member.setName("hello");

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

        //when
//        memberService.join(member1);
//        try {
//            memberService.join(member2);
//            fail();
//        } catch (IllegalStateException e) {
//            Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.123");
//        }

        memberService.join(member1); // join시 계속 메모리 DB에 누적되기때문에 항상 클리어해줘야함.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));

        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        //then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}
package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//@Service - SpringConfig에서 관리하기때문에 어노테이션 필요없음
public class MemberService {

    // setter를 통해 들어오기
//    private MemberRepository memberRepository;
//
//    @Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//     생성자를 통해 들어오기
    private final MemberRepository memberRepository;
    public MemberService(MemberRepository memberRepository) { //직접 new로 생성하는게 아니라 외부에서 넣어주도록
        this.memberRepository = memberRepository;
    }

    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        memberRepository.save(member); // 검증 통과시 바로 저장
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {

        //        Optional<Member> result = memberRepository.findByName(member.getName());
        //        //result.orElseGet() : 값이 있으면 get하고 없으면 get하지않기
        //        result.ifPresent(m -> { //null이 아니라 어떤 값이 있으면 동작하기. Optional이라 가능한부분.
        //            throw new IllegalStateException("이미 존재하는 회원입니다.");
        //        });
        // 위 방식이 보기도 안좋고 Optional을 쓰는게 별로 좋지 않다. 어차피 findByName()하면 Optional이니까 ifPresent 바로 쓰는게 가능

        memberRepository.findByName(member.getName())
                        .ifPresent(m -> {
                            throw new IllegalStateException("이미 존재하는 회원입니다.");
                        });
    }

    // 전체 회원 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

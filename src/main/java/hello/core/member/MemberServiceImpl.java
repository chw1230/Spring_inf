package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    // 추상화에만 의존
    private final MemberRepository memberRepository;

    @Autowired // -> 의존 관계를 자동으로 등록해주기!
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
// MemberServiceImpl 입장에서 보면 의존관계를 외부에서 생성자를 통해서 외부에서 주입해주는 것 같다고 해서 DI 의존관계 주입, 의존성 주입이라고 부른다

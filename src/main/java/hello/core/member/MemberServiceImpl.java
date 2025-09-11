package hello.core.member;

public class MemberServiceImpl implements MemberService{

    // 추상화에만 의존
    private final MemberRepository memberRepository;

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
}
// MemberServiceImpl 입장에서 보면 의존관계를 외부에서 생성자를 통해서 외부에서 주입해주는 것 같다고 해서 DI 의존관계 주입, 의존성 주입이라고 부른다

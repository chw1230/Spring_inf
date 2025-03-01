package hello.core.member;

public class MemberServiceImpl implements MemberService {


    private final MemberRepository memberRepository = new MemoryMemberRepository(); // 구현 객체를 선택해주기

    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findByName(Long memberId) {
        return memberRepository.findById(memberId);
    }
}

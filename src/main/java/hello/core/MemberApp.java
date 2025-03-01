package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;

public class MemberApp {
    // 순수한 자바로만 만들기!!
    public static void main(String[] args) {
        MemberService memberService = new MemberServiceImpl();  // 여기서 구현체를 의존함 -> DIP 위반
        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findByName(1L);
        System.out.println("new member = " + member);
        System.out.println("findMember = " + findMember);
    }
}

package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    // 순수한 자바로만 만들기!!
    public static void main(String[] args) {
//        MemberService memberService = new MemberServiceImpl();  // 여기서 구현체를 의존함 -> DIP 위반

//        AppConfig appConfig = new AppConfig(); // AppConfig 이용하여  DIP 위반 해결하기!
//        MemberService memberService = appConfig.memberService(); // AppConfig 이용하여  DIP 위반 해결하기!

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class); // 꺼내오기!!

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findByName(1L);
        System.out.println("new member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}

package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {

    public static void main(String[] args) {
//        AppConfig appconfig = new AppConfig();
//        MemberService memberService = appconfig.memberService();

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class); // AppConfig 안에 있는 설정 정보를 가지고, 스프링 컨테이너를 관리하겠다!
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);// 이름과 타입으로 가져오기!

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member); // 회원가입

        Member findMember = memberService.findMember(1L);
        System.out.println("new member: " + member.getName());
        System.out.println("find member: " + findMember.getName());
    }
}

package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public MemberService memberService(){
        return new MemberServiceImpl(memberRepository()); // 생정자 주입
    }

    @Bean
    public static MemberRepository memberRepository() {
        return new MemoryMemberRepository();
    }

    @Bean
    public OrderService orderService(){
        return new OrderServiceImpl(discountPolicy(), memberRepository());
    }

    @Bean
    private static DiscountPolicy discountPolicy() {
        // 할인 정책 변경하기!!  FixDiscountPolicy -> RateDiscountPolic 로 변경
        // 다른 코드의 변화 없이 이 것만 변경해주면 됩니당
//        return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

    // 리펙토링!
    // 메서드로 만들어서 중복 제거가능!, 다른 구현체로 변경할 때 한 부분만 변경가능!
    // 역할과 구현 클래스가 한눈에 들어온다.  MemberRepository - MemoryMemberRepository 사용 / DiscountPolicy - FixDiscountPolicy 사용
    // 애플리케이션 전체 구성이 어떻게 되어있는지 빠르게 파악할 수 있다
}

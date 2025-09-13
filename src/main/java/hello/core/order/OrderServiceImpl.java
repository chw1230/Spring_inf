package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    // 추상화에만 의존 하게 됨 => DIP를 지키자!!
    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 추상과 구체를 모두 의존하고 있음 => DIP 위반
    private final DiscountPolicy discountPolicy; // 할인 정책 변경에 따른 위의 코드에서 현재 코드로 수정 => OCP 위반

    @Autowired // 여러 의존 관계 주입도 가능!
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 단일 책임 원칙!

        Member member = memberRepository.findById(memberId); // 멤버 정보 찾기
        int discountPrice = discountPolicy.discount(member, itemPrice); // 찾은 정보를 바탕으로 얼마 할인 받는지 알아오기

        return new Order(memberId, itemName, itemPrice, discountPrice); // 위의 정보를 바탕으로 주문 생성
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

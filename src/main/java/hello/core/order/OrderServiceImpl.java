package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    /*
    private MemberRepository memberRepository = new MemoryMemberRepository();

    // 할인 정책의 변화 -> 기존의 방식 OCP(클라이언트<OrderServiceImpl> 코드 수정), DIP(구현체 의존) 위반
    // private DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private DiscountPolicy discountPolicy = new RateDiscountPolicy();
     */
    // 해결방안 -> 인터페이스에만 의존하도록 설계와 코드를 변경했다.

    private DiscountPolicy discountPolicy; // 그런데 구현체가 없는데 어떻게 코드를 실행할 수 있을까 -> 당연히 오류남 NPE(null pointer exception)가 발생
    private MemberRepository memberRepository;
    // 그렇다면 누군가 외부에서 구현 객체를 대신 생성하고 주입!!!!!

    // 이것 처럼 생성자를 통해서 주입(연결)!!!!
    public OrderServiceImpl(DiscountPolicy discountPolicy, MemberRepository memberRepository) {
        this.discountPolicy = discountPolicy;
        this.memberRepository = memberRepository;
    }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice); // 할인에 대한 것은 discountPolicy로 넘겨버리기!

        return new Order(memberId, itemName, itemPrice, discountPrice); // id, name, 원래 가격, 할인 가격
    }
}

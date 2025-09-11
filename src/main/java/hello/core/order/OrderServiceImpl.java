package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();
    private final DiscountPolicy discountPolicy = new FixDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        // 단일 책임 원칙!

        Member member = memberRepository.findById(memberId); // 멤버 정보 찾기
        int discountPrice = discountPolicy.discount(member, itemPrice); // 찾은 정보를 바탕으로 얼마 할인 받는지 알아오기

        return new Order(memberId, itemName, itemPrice, discountPrice); // 위의 정보를 바탕으로 주문 생성
    }
}

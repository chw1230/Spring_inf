package hello.core.order;

import hello.core.annotation.MainDiscountPolicy;
import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
// @RequiredArgsConstructor // final 붙은 필드를 자동으로 생성자를 만들어 주는 어노테이션
public class OrderServiceImpl implements OrderService {

    // 추상화에만 의존 하게 됨 => DIP를 지키자!!
    private final MemberRepository memberRepository;
//    private final DiscountPolicy discountPolicy = new FixDiscountPolicy(); // 추상과 구체를 모두 의존하고 있음 => DIP 위반
    private final DiscountPolicy discountPolicy; // 할인 정책 변경에 따른 위의 코드에서 현재 코드로 수정 => OCP 위반

//    private MemberRepository memberRepository;
//    private DiscountPolicy discountPolicy;
//    @Autowired // 수정자 주입 (setter 주입)
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }
//
//    @Autowired // 수정자 주입 (setter 주입)
//    public void setDiscountPolicy(DiscountPolicy discountPolicy) {
//        this.discountPolicy = discountPolicy;
//    }

    @Autowired // 여러 의존 관계 주입도 가능! / 생성자를 통한 의존 관계 주입
    public OrderServiceImpl(MemberRepository memberRepository, /*@Qualifier("fixDiscountPolicy")*/ @MainDiscountPolicy DiscountPolicy discountPolicy) {
        // @Autowired의 신기한 기능
        // 1. 조회 대상 빈이 2개 이상일 때 => 파라미터 이름으로 빈 이름을 추가 매칭한다. / 우선적으로는 타입 매칭 그 다음 필드 명 , 파라미터 명으로 작성한다.
        // 2. @Qualifier(" ") 빈등록하는 곳에 붙여주고, 자동 주입하는 과정에서는 이렇게 사용한다. public OrderServiceImpl(MemberRepo ~  @Qualifier("fixDiscountPolicy") ~ Policy discountPolicy) { ~
        // 3. @Primary 우선순위를 정하는 방법!!!!으로 사용
        // 자 정리 : 메인 데이터베이스 커넥션을 획득하는 스프링 빈은 @primary 를 적용해서 조회하는 곳에서 @Qualifier 지정 없이 편리하게 조회하고,
        // 서브 데이터 베이스 커섹션 빈을 획득할 때는 @Qualifier를 지정해서 명시적으로 획득한다.
        // @Qualifier가 우선 순위가 높다!!!

        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    } // 오로지 생성자 주입 망식만 final을 사용할 수 있음!! => 항상 생성자 주입을 사용하도록 하기!! 무조건!!!

//    @Autowired // 일반 메서드 주입 -> 그냥 수정자 메서드 주입과 같음
//    public void init(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
//        this.memberRepository = memberRepository;
//        this.discountPolicy = discountPolicy;
//    }

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

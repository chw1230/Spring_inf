package hello.core.discount;

import hello.core.member.Grade;
import hello.core.member.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest {


    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다.")
    void vip_o() {
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        //when
        int discount = discountPolicy.discount(member, 10000); // vip등급에 10000원 상품이면 얼마할인되는 지
        //then
        Assertions.assertThat(discount).isEqualTo(1000); // 할인 금액이 1000원 인지
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다.")
    void vip_x() {
        //given
        Member member = new Member(2L, "memberBasic", Grade.BASIC);
        //when
        int discount = discountPolicy.discount(member, 10000); // BASIC등급에 10000원 상품이면 얼마할인되는 지
        //then
        Assertions.assertThat(discount).isEqualTo(1000); // 할인 금액이 1000원 인지
        // 실패하는 테스트 만들기
    }
}
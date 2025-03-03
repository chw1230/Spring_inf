package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {
    MemberService memberService;

    @BeforeEach // 테스트 실행 전에 무조건 실행하기
    public void beforeEach(){
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join(){
        // 테스트를 통한 검증 실시
        // given
        Member member = new Member(1L, "memberA", Grade.VIP);
        
        // when
        memberService.join(member);
        Member findMember = memberService.findByName(1L);

        // then
        Assertions.assertThat(member).isEqualTo(findMember);
    }

}

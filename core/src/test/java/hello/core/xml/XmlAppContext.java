package hello.core.xml;

import hello.core.member.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class XmlAppContext {

    @Test
    void xmlAppContext() {
        // 지금까지 했던 방식은 애노테이션 기반 자바 코드 설정이였고, 이 코드는 XML을 통한 설정! -> XML 파일을 보면 어노테이셔(@Bean)을 사용한 것과 유사한 모습임을 볼 수 있음!
        ApplicationContext ac = new GenericXmlApplicationContext("appConfig.xml");
        MemberService memberService = ac.getBean("memberService", MemberService.class);
        assertThat(memberService).isInstanceOf(MemberService.class);

    }
}

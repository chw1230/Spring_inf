package hello.core.autowired;

import hello.core.member.Member;
import io.micrometer.common.lang.Nullable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Optional;

public class AutoWiredTest {

    @Test
    void AutoWiredOption() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestBean.class);
    }

    static class TestBean {

        @Autowired(required = false) // 자동 주입할 대상이 어뵤으면 메서드 자체가 호출이 안됨
        public void setNoBean1(Member noBean1) {
            System.out.println("setNoBean1 = " + noBean1);
            // Member는 현재 빈으로 등록된 상태가 아니기에 false로 했을 때 의존관계가 없으므로 그냥 넘어가게 됨!
        }

        @Autowired // 지동 주입할 대상이 앖으면 null 입력된
        public void setNoBean2(@Nullable Member noBean2) {
            System.out.println("setNoBean2 = " + noBean2);
        }

        @Autowired(required = false) // 자동 주입 대상 없으면 Optional.empty 입력됨!
        public void setNoBean3(Optional<Member> noBean3) {
            System.out.println("setNoBean3 = " + noBean3);
        }
    }
}

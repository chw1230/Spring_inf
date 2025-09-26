package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

class StatefulServiceTest {
    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

        StatefulService statefulService1 = ac.getBean("statefulService", StatefulService.class);
        StatefulService statefulService2 = ac.getBean("statefulService", StatefulService.class);

        // ThreadA: A 사용자 10000 원 주문하고
        statefulService1.order("userA", 10000);
        // ThreadB: B 사용자 20000원 주문
        statefulService2.order("userB", 20000);

        // ThreadA: 사용자 A 주문 금액 조회해서 꺼내려 하는 사이에 B가 주문을 한 상황
        int price = statefulService1.getPrice();
        // 그러면 price의 변화가 원래는 있으면 안되지 정상적인 사고라면 하지만!

        // ThreadA: 사용자 A는 10000원을 기대했지만, 기대와 다르게 20000원 출력
        System.out.println("price = " + price);

        Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }
    // 뭐가 문제인 것일까?
    // StatefulService의 price 필드는 공유되는 필드임! 근데 특정 클라이언트에서 여러 곳에서 값을 변경하게 되면
    // 싱글톤에서 같은 객체를 참고하기 때문에 이상한 값으로 조회가 되게 되는 것!!
    // 그래서 공유 필드에 대해서는 조심해야한다!! 애초에 값을 변경할 수 있는 필드를 생성하지를 말아야함!! => 무상태 설계하기!

    static class TestConfig {
        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }
    }
}
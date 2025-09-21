package hello.core.scope;

import hello.core.AppConfig;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

public class PrototypeBeanFind {

    @Test
    public void prototypeBeanFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        System.out.println("find prototype bean1");
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class); // 스프링 컨테이너에서 조회할 때 스프링 빈이 생성된다!

        System.out.println("find prototype bean2");
        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);

        System.out.println("prototypeBean1 = " + prototypeBean1);
        System.out.println("prototypeBean2 = " + prototypeBean2);
        Assertions.assertThat(prototypeBean1).isNotSameAs(prototypeBean2); // 둘이 서로 달라야함! 요청할 때마다 새롭게 생성하기 때문에!

        // destroy도 호출이 안됨을 볼 수 있음 -> 스프링 컨테이너가 생성하고 관리하지 않음 생성에서 할 일 끝!

        ac.close();
    }


    @Scope("prototype")
    static class PrototypeBean { // new AnnotationConfigApplicationContext(AppConfig.class);여기에서 지정했기 떄문에 @component 없이도, 자동으로 설정되는 것!

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean init");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean destroy");
        }
    }
}

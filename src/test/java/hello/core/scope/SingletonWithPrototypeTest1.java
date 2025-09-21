package hello.core.scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.inject.Provider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import static org.assertj.core.api.Assertions.assertThat;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);

        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCnt();
        Assertions.assertThat(prototypeBean1.getCnt()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCnt();
        Assertions.assertThat(prototypeBean2.getCnt()).isEqualTo(1);
    }
    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int cnt1 = clientBean1.logic();
        assertThat(cnt1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int cnt2 = clientBean2.logic();
        assertThat(cnt2).isEqualTo(1);
    }


    @Scope("singleton")
    static class ClientBean {
        private final PrototypeBean prototypeBean; // 생성 시점에 주입

//        @Autowired
//        ApplicationContext ac; // 직접 필요한 의존 관계를 받아오는 기능을 함! , 직접 컨테테이너와 의존 관계를 연결해서 매번 새로운 객체를 받아오기!

        //        @Autowired
//        private ObjectProvider<PrototypeBean> prototypeBeanProvider; // 지정한 빈을 컨테이너에서 대신 찾아주는 DL 서비스를 제공하는 것 -> ObjectProvider
        @Autowired
        private Provider<PrototypeBean> provider;

        @Autowired
        public ClientBean(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        public int logic() {
//            PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
//            PrototypeBean prototypeBean = prototypeBeanProvider.getObject(); // 스프링 컨테이너에서 스프링 빈을 찾아서 반환!!
            PrototypeBean prototypeBean = provider.get();
            prototypeBean.addCnt();
            return prototypeBean.getCnt();
        }
    }

    @Scope("prototype")
    static class PrototypeBean {

        private int cnt = 0;

        public void addCnt() {
            cnt++;
        }

        public int getCnt() {
            return cnt;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean = " + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }
}

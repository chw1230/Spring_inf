package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class NetworkClient {


    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출,url = " + url); // 처음에 url이 없음 -> url = null로 나옴
        connet(); // 여기서 연결 메서드 호출
        call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출하는 메서드
    public void connet() {
        System.out.println("connet: " + url); // 호출되가지고 갔는디 null이라 그냥 출력함!
    }

    public void call(String msg) {
        System.out.println("call: " + url + ", msg = " + msg);
    }

    // 서비스 종료시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    @PostConstruct
    public void init() { // 의존 관계 주입이 끝나면 호출해 주겠다!
        System.out.println("NetWorkClient.init");
        connet();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetWorkClient.close");
        disconnect();
    }
    /* 최신 스츠링에서 권장하는 방식
    @PostConstruct, @PreDestroy -> 애노테이션 하나만 붙이변 되므로 매우 편리
    단점 : 외부라이브러에 사용하지 못하기 때문에 외부 라이브러리를 사용해야한다면 @Bean의 initMethod, destroyMethod를 사용하자!
     */
}
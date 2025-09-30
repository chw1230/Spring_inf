package hello.springmvc.basic.requestmapping;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

@Slf4j
@RestController
public class MappingController {

    /**
     * 기본 요청
     * 요청을 배열로 받을 수도 있음! -> {"/Hello-basic", "/Hello-Hello"}
     * HTTP의 메서드를 모두 허용!!
     *
     * @return String "OK"
     */
    @RequestMapping({"/Hello-basic", "/Hello-Hello"}) // /hello-basic URL 호출이 오면 해당 메서드가 실행이됨!
    public String HelloBasic() {
        log.info("Hello-basic");
        return "OK";
    }

    // 축약 형태의 애노테이션 사용하기
    @GetMapping(value = "/mapping-get-v2")
    public String mappingGetV2() {
        log.info("mapping-get-v2");
        return "ok";
    }

    // PathVariable( 경로 변수 )사용
    /**
     * PathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId -> @PathVariable String userId
     *
     * 많이 사용!!!!!!!!!!!!!
     */
    @GetMapping("/mapping/{userId}") // url의 값을 꺼내와서 사용!
    public String mappingGet(@PathVariable String userId) {
        log.info("mappingPath userId={}", userId);
        return "ok";
        /*
        http://localhost:8080/mapping/userA
        userId=userA
         */
    }

    /**
     * PathVariable 사용 다중
     *
     * 많이 사용!!!!!!!!!!!!!
     */
    @GetMapping("/mapping/users/{userId}/orders/{orderId}")
    public String mappingPath(@PathVariable String userId, @PathVariable Long
            orderId) {
        log.info("mappingPath userId={}, orderId={}", userId, orderId);
        return "ok";
        /*
        http://localhost:8080/mapping/users/userA/orders/100
        userId=userA, orderId=100
         */
    }

    /**
     * Content-Type 헤더 기반 추가 매핑 Media Type
     * consumes="application/json"
     * consumes="!application/json"
     * consumes="application/*"
     * consumes="*\/*"
     * MediaType.APPLICATION_JSON_VALUE
     *
     * consumes 안에의 값일 경우에만 올바른 결과가 나옴!
     */
    @PostMapping(value = "/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE) // "application/json" 냅다 이렇게 적지 말고
    public String mappingConsumes() {
        log.info("mappingConsumes");
        return "ok";
    }

    /**
     * Accept 헤더 기반 Media Type
     * produces = "text/html"
     * produces = "!text/html"
     * produces = "text/*"
     * produces = "*\/*"
     *
     * 요청의 Accept 헤더를 기반으로 미디어 타입을 매핑 ex> application/json 이라면 406(Not Accepted)를 반환
     */
    @PostMapping(value = "/mapping-produce", produces = MediaType.TEXT_HTML_VALUE)
    public String mappingProduces() {
        log.info("mappingProduces");
        return "ok";
    }
}
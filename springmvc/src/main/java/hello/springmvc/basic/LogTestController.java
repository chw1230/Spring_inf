package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController // 반환했을 때의 String이 그대로 반환이 되어 뷰 이름으로 인식됨! / 기존의 컨트롤러 반환 값으로 뷰를 찾는 것이 아니라, HTTP 메서지 바디에 바로 입력이 됨!
public class LogTestController {
//    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);
        log.trace("trace log={}", name);
        log.info("info log = {} " + name);

        return "OK";
    }
}

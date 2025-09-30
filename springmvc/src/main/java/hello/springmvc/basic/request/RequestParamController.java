package hello.springmvc.basic.request;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username = {}, age = {}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody // RestController 아닐 때 String을 반환하면 해당 String으로 이뤄진 뷰를 찾음! 그러니까 ResponseBody에 곧장 넣어버리라는 애노테이션
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge) {

        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {
        // 속성이름을 그대로 파라미터로 사용하기!
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * String, int 등의 단순 타입이면 @RequestParam 도 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     *  @RequestParam.required
     *  파라미터 필수 여부
     *  true -> 없으면 오류 => 400 error
     *  flase -> 없어도 오류가 안나요!
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * 기본값

     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param-default?username=
     * null 아니라 빈문자 인것!
     *
     * 그리고 required 도 사실 필요가 없음! 어차피 디폴트로 들어가니까!
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            @RequestParam(required = true, defaultValue = "guest") String username,
            @RequestParam(required = false, defaultValue = "-1") int age) {

        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam
     * 파라미터를 Map으로 조회하기
     * Map(key=value)
     *
     * 만약의 multiValueMap 이다 그러면 -> 하나의 키에 여러개의 value
     * 애매하게 이렇게 사용하는 경우 없다
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"),paramMap.get("age"));
        return "ok";
    }
}

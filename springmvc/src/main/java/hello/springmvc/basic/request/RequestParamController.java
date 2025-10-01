package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
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
     * @RequestParam.required 파라미터 필수 여부
     * true -> 없으면 오류 => 400 error
     * flase -> 없어도 오류가 안나요!
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
     * @RequestParam 기본값
     * <p>
     * 참고: defaultValue는 빈 문자의 경우에도 적용
     * /request-param-default?username=
     * null 아니라 빈문자 인것!
     * <p>
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
     * @RequestParam 파라미터를 Map으로 조회하기
     * Map(key=value)
     * <p>
     * 만약의 multiValueMap 이다 그러면 -> 하나의 키에 여러개의 value
     * 애매하게 이렇게 사용하는 경우 없다
     */
    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) {
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    /**
     * @ModelAttribute 사용
     * 참고: model.addAttribute(helloData) 코드도 함께 자동 적용됨, 뒤에 model을 설명할 때 자세히
     * <p>
     * HelloData 구조
     * private String username;
     * private int age;
     * 파라미터받고, 필요한 객체 만들고, 그 객체에 값을 넣는 일을 직접 하지 않아도 됨!
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
        // 작동 방식 -> Hello 객체를 생성, 요청 파라미터의 이름으로 Hello 객체의 프로퍼티(getxxx, setxxx)를 찾음
    }

    /**
     * @ModelAttribute 생략 가능
     * String, int 같은 단순 타입의 생략과 혼동가능 => @RequestParam 적용
     * argument resolver 로 지정해둔 타입 외에는 => @ModelAttribute 적용
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

}

package hello.servlet.web.springmvc.v1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller // 1. 컴포넌트 스탠의 대상이 되어 ->  자동 스프링 빈 등록 / 2. RequestMappingHandlerMapping의  매핑 대상이 된다!
public class SpringMemberFormControllerV1 {

    @RequestMapping("springmvc/v1/members/new-form") // 이 url이 호출되면 메서드가 호출이 된다!
    public ModelAndView process() {
        return new ModelAndView("new-form"); // 모델과 뷰 정보를 담아서 반환!
    }
}

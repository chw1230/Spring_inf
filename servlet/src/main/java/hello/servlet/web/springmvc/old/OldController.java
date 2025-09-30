package hello.servlet.web.springmvc.old;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

@Component("/springmvc/old-controller") // 스프링 빈 등록 -> url을 스프링 빈의 이름으로 하여 등록하기!
public class OldController implements Controller {

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("OldController.handleRequest");

        // 뷰 리졸버 없이 new ModelAndView("new-form"); 를 리턴하면 오류가 난다! -> 스프링 부트를 이요하자 스프링 부트는 application.properties에 저장된 설정벙보를 가져와서 자동 빈 등록한다!!
        return new ModelAndView("new-form");
    }
}
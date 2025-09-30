package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }


    // 매핑 정보를 통해서 어떤 컨트롤러를 사용할 지 찾기
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        String requestURI = request.getRequestURI(); // 요청에서 url 얻기

        ControllerV2 controller = controllerMap.get(requestURI); // 등록된 정보를 통해서 정보에 맞는 컨트롤러를 가지고 옴
        if (controller == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND); // 없음! 404
            return;
        }

        MyView view = controller.process(request, response); // 각자의 비즈니스 로직에 맞는 메서드가 실행됨! 실행의 결과로 뷰가 전달됨!
        view.render(request, response);
    }
}

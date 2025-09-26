package hello.core.web;

import hello.core.common.MyLogger;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class LogDemoController {

    private final LogDemoService logDemoService;
//    private final ObjectProvider<MyLogger> myLoggerProvider;
    // ObjectProvider를 이용해서 ObjectProvider.getObject를 호출하는 시점까지 빈의 생성을 지연할 수 있었음!!
    private final MyLogger myLogger;

    @RequestMapping("/log-demo")
    @ResponseBody
    public String logDemo(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
//        MyLogger myLogger = myLoggerProvider.getObject(); /
        myLogger.setRequestUrl(requestURI);

        myLogger.log("controller test");
        logDemoService.logic("testId");
        return "OK";
    }
}

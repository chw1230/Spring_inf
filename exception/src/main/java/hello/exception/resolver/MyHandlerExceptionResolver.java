package hello.exception.resolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Slf4j
public class MyHandlerExceptionResolver implements HandlerExceptionResolver {

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try { // ExceptionResolve가 예외를 잡아 먹어 버린것!!
            if (ex instanceof IllegalArgumentException) { // 예외가 IllegalArgumentException 이거라면
                log.info("IllegalArgumentException resolver to 400");
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage()); // 400에러를 반환하겠다!!!
                return new ModelAndView();
            }
        } catch (IOException e) {
            log.error("resolver ex", e);
        }
        return null;
        /*
        /ex 일땐 IllegalArgumentException가 아니기에 500error 발생
        /bad 일땐 IllegalArgumentException 에러라서 400error 발생!
         */
    }
}
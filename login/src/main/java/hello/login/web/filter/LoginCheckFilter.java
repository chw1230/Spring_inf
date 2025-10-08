package hello.login.web.filter;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.LogRecord;

@Slf4j
public class LoginCheckFilter implements Filter {
    // 경로중에 기본으로 들어와야 하는 경로들 저장하기 -> 인증과 무관하게 항상 허용
    private static final String[] whitelist = {"/", "/members/add", "/login", "/ logout", "/css/*"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        try {
            log.info("인증 체크 필터 시작 {}", requestURI);

            if (isLoginCheckPath(requestURI)) {
                log.info("인증 체크 로직 실행 {}", requestURI);
                HttpSession session = httpRequest.getSession(false);

                if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) { // 로그인이 안되거나, 회원이 없는 것으로 나타난다면?
                    log.info("미인증 사용자 요청 {}", requestURI);
                    //로그인으로 redirect(보내기)
                    httpResponse.sendRedirect("/login?redirectURL=" + requestURI); // '상품 관리 화면을 보려고 들어갔다가 로그인 화면으로 이동하면, 로그인 이후에 다시 상품 관리 화면으로 들어가는 것이 좋다' 이 예시를 생각하면 좋음
                    return; //여기가 중요!! 미인증 사용자는 다음으로 진행하지 않고 끝내기!!
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            throw e;
        } finally {
            log.info("인증 체크 필터 종료 {}", requestURI);
        }
    }

    /**
     * 화이트 리스트의 경우 인증 체크X
     */
    private boolean isLoginCheckPath(String requestURI) {
        return !PatternMatchUtils.simpleMatch(whitelist, requestURI); // 화이트 리스트인 경우에는 인증 하지 않기
    }
}

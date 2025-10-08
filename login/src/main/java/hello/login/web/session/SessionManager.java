package hello.login.web.session;

import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 세션 관리
 */
@Component
public class SessionManager {
    public static final String SESSION_COOKIE_NAME = "mySessionId";

    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    /**
     * 세션 생성
     */
    public void createSession(Object value, HttpServletResponse response) {
        //세션 id를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString(); // 세션 Id 생성
        sessionStore.put(sessionId, value); // 저장

        // 쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId); // 쿠키이름, 값
        response.addCookie(mySessionCookie);
    }

    /**
     * 세션 조회
     */
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME); // 세션 id 가  반환
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue()); // 세션id를 통해서 객체를 반환
    }

    /**
     * 세션 만료
     */
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) { // 세션 Id가 있으면
            sessionStore.remove(sessionCookie.getValue()); // 지워 버리기!
        }
    }

    // 쿠키를 찾는 메서드
    private Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) { // 쿠키가 없으면 null 반환
            return null;
        }

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny()
                .orElse(null);
        // 쿠키가 존재하면 찾아서 반환하디
    }
}
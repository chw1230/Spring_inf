package hello.login.domain.login;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
//
//        if (bindingResult.hasErrors()) { // 에러이면 다시 폼으로
//            return "login/loginForm";
//        }
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        log.info("login? {}", loginMember);
//
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); // 글로벌 오류
//            return "login/loginForm";
//        }
//
//        //로그인 성공 처리 TODO
//        // 로그인 성공 했을 때 쿠키를 반환하기 - 이때 시간을 주지 않으면 세션 쿠키로 생성 (세션 쿠키 - 브라우저 종료시에 모두 종료 되는 쿠키)
//        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId())); // 쿠키의 이름은 memberId이고 값에는 회원의 id가 들어감!
//        response.addCookie(idCookie);
//        return "redirect:/";
//        // 이렇게 쿠키를 설정해주면 -> 앞으로 웹 브라우저는 서버에 회원의 id를 지속적으로 전달해줌!
//    }

    @PostMapping("/login")
    public String loginV2(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {

        if (bindingResult.hasErrors()) { // 에러이면 다시 폼으로
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다."); // 글로벌 오류
            return "login/loginForm";
        }

        //로그인 성공 처리
        // sessionManager를 통해서 세션 생성하고, 회원 데이터에 보관하기!
        sessionManager.createSession(loginMember, response);

        return "redirect:/";
    }

//    @PostMapping("/logout")
//    public String logout(HttpServletResponse response) {
//        expireCookie(response, "memberId");
//        return "redirect:/";
//    }

//    @PostMapping("/logout")
//    public String logoutV2(HttpServletRequest request) {
//        sessionManager.expire(request);
//        return "redirect:/";
//
//    }

    @PostMapping("/login")
    public String loginV3(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
        log.info("login? {}", loginMember);
        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }
        //로그인 성공 처리
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0); // 종료 날짜를 0으로 지정하기 -> 기존에 있던 쿠키는 세션 쿠키라 웹 브라우저를 닫아야 사라졌는데 이제는 로그아웃을 눌려도 쿠키가 사라진 것을 볼 수 있음!
        response.addCookie(cookie);
    }
}
package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

    //    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
//    public String homeLoginV1(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {
//        // 받은 쿠키 확인하기
//        if (memberId == null) {
//            return "home";
//        }
//
//        //로그인
//        Member loginMember = memberRepository.findById(memberId);
//        if (loginMember == null) {
//            return "home";
//        }
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//    }

//    @GetMapping("/")
//    public String homeLoginV2(HttpServletRequest request, Model model) {
//        // 쿠키가 없으면 home 있으면 loginhome
//
//        // 세션 관리자에 저장된 회원 정보 조회하기!!
//        Member member = (Member) sessionManager.getSession(request);
//        if (member == null) {
//            return "home";
//        }
//
//        model.addAttribute("member", member);
//        return "loginHome";
//    }

//    @GetMapping("/")
//    public String homeLoginV3(HttpServletRequest request, Model model) {
//        // 세션이 존재하면 loginhome / 없으면 home
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return "/home";
//        }
//
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER); // 세션이 존재하면 세션으로 보관해둔 객체를 꺼내기
//        if (loginMember == null) { // 세션에 회원 객체가 없으면 home으로 가기
//            return "/home";
//        }
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//    }

    @GetMapping("/")
    public String homeLoginV3Spring(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {
        if (loginMember == null) { // 세션에 회원이 없으면 null
            return "/home";
        }
        model.addAttribute("member", loginMember); // 회원이 존재하면 loginHome 으로 이동
        return "loginHome";
    }
}
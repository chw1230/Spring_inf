package hello.servlet.web.frontcontroller.v4.controller;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import hello.servlet.web.frontcontroller.v4.ControllerV4;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        // param에서 값 꺼내고
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        // 비즈니스 로직 실행하고
        Member member = new Member(username, age);
        memberRepository.save(member);

        // 모델에 다가 값을 넣어주고
        model.put("member", member);
        return "save-result";
    }
}

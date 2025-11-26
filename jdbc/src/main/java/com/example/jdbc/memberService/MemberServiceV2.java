package com.example.jdbc.memberService;

import com.example.jdbc.domain.Member;
import com.example.jdbc.domain.repository.MemberRepositoryV1;
import com.example.jdbc.domain.repository.MemberRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 트랜잭션 - 파라미터 연동, 풀을 고려한 종료
 */
@Slf4j
@RequiredArgsConstructor
public class MemberServiceV2 {
    // 현재 코드의 문제점 : 서비스 에서 비즈니스 로직을 처리하는 것 보다 트랜잭션 관련 설정이 더 많음! -> 스프링을 사용해서 문제 해결하기
    private final DataSource dataSource;
    private final MemberRepositoryV2 memberRepository;

    public void accountTransfer(String fromId, String toId, int money) throws SQLException {
        Connection con = dataSource.getConnection(); // 커넥션 얻기

        try {
            con.setAutoCommit(false); // 트랜잭션 시작

            // 순수 비즈니스 로직
            bizLogic(con, fromId, toId, money);

            con.commit(); //성공시 커밋
        } catch (Exception e) {
            con.rollback(); //실패시 롤백
            throw new IllegalStateException(e);
        } finally {
            release(con);
        }
    }

    // 순수 비즈니스 로직
    private void bizLogic(Connection con, String fromId, String toId, int money) throws SQLException { // 동일한 커넥션을 가지고 로직을 수행!
        Member fromMember = memberRepository.findById(con, fromId);
        Member toMember = memberRepository.findById(con, toId);

        memberRepository.update(con, fromId, fromMember.getMoney() - money);
        validation(toMember);
        memberRepository.update(con, toId, toMember.getMoney() + money);
    }

    private void validation(Member toMember) {
        if (toMember.getMemberId().equals("ex")) {
            throw new IllegalStateException("이체중 예외 발생");
        }
    }

    private void release(Connection con) {
        if (con != null) {
            try {
                con.setAutoCommit(true); // 커넥션 풀 고려했을 때 기본 값인 오토 커밋으로 설정해서 풀에 반환해주기
                con.close();
            } catch (Exception e) {
                log.info("error", e);
            }
        }
    }
}

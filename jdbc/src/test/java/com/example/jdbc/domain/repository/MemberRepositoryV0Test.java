package com.example.jdbc.domain.repository;

import com.example.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 memberRepository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("memberV3", 10000); // 생성
        memberRepository.save(member); // 2번 실행하면 당연히 오류 남..

        // findById
        Member findMember = memberRepository.findById(member.getMemberId());
        log.info("findMember={}", findMember);
        assertThat(findMember).isEqualTo(member);

        // update: money: 10000 -> 20000
        memberRepository.update(member.getMemberId(), 20000);
        Member updateMember = memberRepository.findById(member.getMemberId());
        assertThat(updateMember.getMoney()).isEqualTo(20000);

        //delete
        memberRepository.delete(member.getMemberId());
        assertThatThrownBy(() -> memberRepository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class); // 예외에 대한 검증 하는 방법 -> assertThatThrownBy 사용
    }
}
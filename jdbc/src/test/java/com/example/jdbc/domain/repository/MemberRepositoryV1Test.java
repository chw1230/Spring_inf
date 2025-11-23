package com.example.jdbc.domain.repository;

import com.example.jdbc.domain.Member;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static com.example.jdbc.connection.ConnectionConst.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Slf4j
class MemberRepositoryV1Test {

    MemberRepositoryV1 memberRepository;

    @BeforeEach
    void beforeEach() throws Exception {
        // 기본 DriverManager - 항상 새로운 커넥션 획득하는 것을 볼 수 있음 -> 성능이 느림!!
        // DriverManagerDataSource dataSource = new DriverManagerDataSource(URL, USERNAME, PASSWORD);

        // 커넥션 풀링 사용하기!! : HikariProxyConnection -> JdbcConnection
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(URL);
        dataSource.setUsername(USERNAME);
        dataSource.setPassword(PASSWORD);
        memberRepository = new MemberRepositoryV1(dataSource);

        /*
        커넥션 풀 사용시 conn0 커넥션이 재사용 된 것을 확인할 수 있음! 커넥션을 사용하고 다시 돌려주는 것을 반복하면서 conn0 만 사용된 것을 볼 수 있음!
        DriverManagerDataSource -> HikariDataSource 로 변경해도 MemberRepositoryV1 의 코드는 전혀 변경할 필요 없었음!  MemberRepositoryV1 는 DataSource 인터페이스에만 의존하기 때문에!! 구현체를 변경해도 문제 없음!!
         */
    }
    @Test
    void crud() throws SQLException, InterruptedException {
        log.info("start");

        //save
        Member member = new Member("memberV10", 10000);
        memberRepository.save(member);

        //findById
        Member memberById = memberRepository.findById(member.getMemberId());
        assertThat(memberById).isNotNull();

        //update: money: 10000 -> 20000
        memberRepository.update(member.getMemberId(), 20000);
        Member updatedMember = memberRepository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        // delete
        memberRepository.delete(member.getMemberId());
        assertThatThrownBy(() -> memberRepository.findById(member.getMemberId()))
                .isInstanceOf(NoSuchElementException.class);
    }
}
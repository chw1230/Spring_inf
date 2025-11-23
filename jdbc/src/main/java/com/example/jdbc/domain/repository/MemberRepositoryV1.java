package com.example.jdbc.domain.repository;

import com.example.jdbc.connection.DBConnectionUtil;
import com.example.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/**
 * JDBC - DataSource 사용, JDBCUtils 사용
 */

@Slf4j
public class MemberRepositoryV1 {

    private final DataSource dataSource;

    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Member save(Member member) throws SQLException { //  생성
        String sql = "insert into member (member_id, money) values (?, ?)"; // 사용할 쿼리

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId()); // 타입에 맞게 넣기
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate(); // 실제 DB에 전달
            return member;
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public Member findById(String memberId) throws SQLException { // 조회
        String sql = "select * from member where member_id = ?";

        Connection con = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);

            rs = pstmt.executeQuery();// 조회 실행! 이후에 조회 결과를 반환
            if (rs.next()) {
                Member member = new Member();
                member.setMemberId(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId=" + memberId);
            }
        } catch (Exception e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, rs);
        }
    }

    public void update(String memberId, int money) throws SQLException { // 수정
        String sql = "update member set money=? where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            int resultSize = pstmt.executeUpdate(); // 쿼리를 실행하고 영향을 받은 row수를 반환
            log.info("resultSize={}", resultSize);
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException { // 삭제
        String sql = "delete from member where member_id=?";

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            con = getConnection();
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, memberId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(con, pstmt, null);
        }
    }

    // 닫지 않으면 커넥션이 끊어 지지 않고 계속 유지 되는 문제 발생(리소스 누수) -> 커넥션 부족 문제 발생
    private void close(Connection con, Statement stmt, ResultSet rs) {

        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
        // 밑의 코드를 위에서 모두 대체함!
//
//        if (rs != null) {
//            try {
//                rs.close();
//            } catch (SQLException e) {
//                log.info("error", e);
//            }
//        }
//
//        if (stmt != null) {
//            try {
//                stmt.close();
//            } catch (SQLException e) {
//                log.error("error", e);
//            }
//        }
//
//        // 위의 로직에서 에러가 나더라도 close 할 수 있도록 설정
//        if (con != null) {
//            try {
//                stmt.close();
//            } catch (SQLException e) {
//                log.error("error", e);
//            }
//        }
    }

    private Connection getConnection() throws SQLException {
//        return DBConnectionUtil.getConnection(); // 커넥션 얻기 -> V0에서 사용한 방법
        Connection con = dataSource.getConnection(); // dataSource에서 커넥션 얻기!!
        log.info("connection={}, clas={}", con,con.getClass());
        return con;
    }

}

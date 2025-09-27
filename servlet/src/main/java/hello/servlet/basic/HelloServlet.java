package hello.servlet.basic;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "helloServlet", urlPatterns = "/hello") // /hello 로 오면 이게 실행 되는 것!
public class HelloServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // HTTP 요청시 WAS는 req,res 객체를 새로 만들어서 서블릿 객체 호출
        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        // request를 편리하게 읽을 수 있음
        String username = request.getParameter("username");
        System.out.println("username = " + username);

        // 응답 메시지
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(("hello " + username));
    }
}

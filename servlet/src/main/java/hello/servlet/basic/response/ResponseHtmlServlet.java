package hello.servlet.basic.response;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-Type: text/html;charset=utf-8 , 타입, 인코딩 형식 먼저 설정하기!
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");


        PrintWriter writer = response.getWriter();
        // 서블릿 으로 직접  html을 반환하는 경우에는 직접 작성을 해야함!
        writer.println("<html>");
        writer.println("<body>");
        writer.println(" <div>할루?</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}

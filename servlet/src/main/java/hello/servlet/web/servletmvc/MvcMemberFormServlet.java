package hello.servlet.web.servletmvc;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

// 서블릿을 컨트롤러로 사용하고, JSP를 뷰로 사용해서 MVC 패턴을 적용하기!

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath); // RequestDispatcher -> 컨트롤러에서 뷰로 이동할 때 사용
        dispatcher.forward(request, response); // forward -> 다른 서블릿이나 jsp로 이동할 수 잇는 기능, 서버 내부에서 다시 호출이 발생 -> redirect와는 다름(클라이언트로 응답이 갔다가 클라이언트가 다시 redirect로 다시 요청, 클라이ㅇ너트가 인지를 할 수 잇음)
        // 하지만 포워드는 서버 내부에서 일어나느 호출이기 때문에 클라이언트에서 호출을 인지할 수 없음!
    }
}

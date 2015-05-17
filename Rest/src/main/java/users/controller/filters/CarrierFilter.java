package users.controller.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Відфільтровує запити від користувачів, що не відносяться до категорії перевізників.
 *
 * @author Горох Олександр Сергійович, гр. ІО-31, ФІОТ, НТУУ КПІ
 */
public class CarrierFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        System.out.println("Requested Resource::" + uri);

        HttpSession session = req.getSession(false);
        if (session.getAttribute("carrier") == null) {
            System.out.println(("Unauthorized carrier access request"));
            res.sendRedirect("/1111/home.jsp");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {}
}

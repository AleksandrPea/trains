package users.controller.filters;

import users.db.entities.Category;
import users.db.entities.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CarrierAuthenticationFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        System.out.println("Requested Resource::" + uri);

        HttpSession session = req.getSession(false);

        if (session.getAttribute("carrier") == null) {
            System.out.println(("Unauthorized carrier access request"));
            res.sendRedirect("/1111/home.jsp");
        } else {
            // pass the request along the filter chain
            chain.doFilter(request, response);
        }
    }

    public void destroy() {}
}

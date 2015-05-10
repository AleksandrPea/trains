package users.controller.filters;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/AuthenticationFilter")
public class AuthenticationFilter implements Filter {

    public void init(FilterConfig fConfig) throws ServletException {}

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        System.out.println("Requested Resource::"+uri);

        HttpSession session = req.getSession(false);

        if(session == null && !(uri.endsWith("html") || uri.endsWith("Login") || uri.endsWith("Register"))){
            System.out.println(("Unauthorized access request"));
            res.sendRedirect("/1111/login.html");
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {}
}
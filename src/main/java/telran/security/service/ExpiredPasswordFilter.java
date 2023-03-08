package telran.security.service;

import accounting.security.UserProfile;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

public class ExpiredPasswordFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if(checkEndPoint(req.getMethod(),req.getServletPath()) && auth != null){
            UserProfile user = (UserProfile) auth.getPrincipal();
            if(!user.isPasswordIsNotExpired()) {
                response.sendError(403, "Password expired");
                return;
            }
        }

        filterChain.doFilter(req,response);
    }

    private boolean checkEndPoint(String method, String path){
        return !(method.equalsIgnoreCase("PUT") && path.matches("/account/password"));
    }
}

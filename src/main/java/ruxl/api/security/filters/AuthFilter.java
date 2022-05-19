package ruxl.api.security.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import ruxl.api.exceptions.ApiException;
import ruxl.api.repositories.JWTRepo;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class AuthFilter extends GenericFilterBean {
    @Autowired
    private JWTRepo jwtRepository;

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        try {
            String access_token=req.getHeader("Authorization").split(" ")[1];
            boolean isValid=jwtRepository.validateJwtToken(access_token);
            if (!isValid)throw ApiException.generate401Exception("Unauthorized");

        }catch (Exception e){
            throw ApiException.generate401Exception("Unauthorized");
        }finally {
            filterChain.doFilter(servletRequest,servletResponse);
        }

    }
}

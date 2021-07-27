
package com.epam.esm.web.jwt;

import com.epam.esm.service.exception.JwtAuthException;
import com.epam.esm.service.jwt.JwtTokenProvider;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private MessageSource messageSource;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);
                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            } else {
                if (token != null) {
                    processAuthenticationError((HttpServletResponse) res, (HttpServletRequest) req, messageSource.getMessage(("token.not.valid"), new Object[]{}, req.getLocale()));
                    return;
                }
            }
            filterChain.doFilter(req, res);
        } catch (JwtAuthException e) {
            processAuthenticationError((HttpServletResponse) res, (HttpServletRequest) req, e.getMessage());
        }
    }

    private void processAuthenticationError(HttpServletResponse res, HttpServletRequest req,
                                            String e) throws IOException {
        res.setContentType("application/json; charset=UTF-8");
        res.setCharacterEncoding("UTF-8");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        JSONObject responseBody = new JSONObject();
        responseBody.put("message", messageSource.getMessage((e), new Object[]{}, req.getLocale()));
        res.getWriter().println(responseBody.toString());
    }
}